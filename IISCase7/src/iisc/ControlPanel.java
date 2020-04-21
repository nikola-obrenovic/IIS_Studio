package iisc;

import java.awt.Button;
import java.awt.Color;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.awt.SystemColor;
import java.awt.Dimension;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ControlPanel extends JPanel 
{
  IISFrameMain parent;
  private ImagePanel textboxBtn;
  private ImagePanel radioBtn;
  private ImagePanel checkBoxBtn;
  private ImagePanel comboBtn;
  private ImagePanel listBtn;
  //private ImagePanel popupBtn;
  private JLabel textboxLbl;
  private JLabel radioLbl;
  private JLabel checkBoxLbl;
  private JLabel comboLbl;
  private JLabel listLbl;
  private JLabel popupLbl;
  private JPanel selectPanel;
  private int mSelectedIndex;
  private int mWidth;
  private int mHeight;
  public int Dom_id;
  private int PR_id;
  private PropertyScrollPane textBoxSp;
  private PropertyPanel textBoxPP;
  private Property[] textBoxProperties;  
  private PropertyScrollPane radioSp;
  private PropertyPanel radioPP;
  private Property[] radioProperties;
  private PropertyScrollPane comboSp;
  private PropertyPanel comboPP;
  private Property[] comboProperties;
  private PropertyScrollPane whSp;
  private PropertyPanel whPP;
  private Property[] whProperties;
  private Connection conn;
  private JButton btnAddA;
  private JButton btnDelA;
  private JButton btnChangeA;
  private JButton btnUp;
  private JButton btnDown;
  private JPanel borderPanel;
  private ImageIcon upIcon;
  private ImageIcon downIcon;
  private JTable table;
  private DisplayValueTableModel datm;
  private JScrollPane tableScroll;
  private PropertyFactory pf;
  private JButton applyBtn;
  private ImageIcon iconCombo = new ImageIcon(IISFrameMain.class.getResource("icons/combo.gif"));
  private ImageIcon iconCheck = new ImageIcon(IISFrameMain.class.getResource("icons/checkbox.gif"));
  private ImageIcon iconRadio = new ImageIcon(IISFrameMain.class.getResource("icons/radio.gif"));
  private ImageIcon iconText = new ImageIcon(IISFrameMain.class.getResource("icons/textbox.gif"));
  private ImageIcon iconPopup = new ImageIcon(IISFrameMain.class.getResource("icons/popup.gif"));
  private ImageIcon iconList = new ImageIcon(IISFrameMain.class.getResource("icons/list.gif"));
  public Domain domFrm;
  private int nextVal;
  private boolean domExists;
  
  public ControlPanel()
  {
      super();
  }
  
  public ControlPanel(int p_Width, int p_Height, Connection p_Conn, IISFrameMain p_Parent, int _Dom_id, Domain _domFrm, int _PR_id)
  {
      conn = p_Conn;
      parent = p_Parent;
      mWidth = p_Width;
      mHeight = p_Height;
      Dom_id = _Dom_id;
      domFrm = _domFrm;
      PR_id = _PR_id;
      pf = new PropertyFactory(conn, Dom_id, this,"D");
      mSelectedIndex = pf.getDDType();
      domExists = pf.getDomExists();
      setLayout(null);  
      InitPanels();
      DrawPanels();
      AddButtons();  
      InitTable();
      SetLoveredBorder();
      nextVal = -1;
      repaint();
  }
  
  private void InitPanels()
  {
      borderPanel = new JPanel();
      selectPanel = new JPanel();
      textboxBtn = new ImagePanel(iconText, 5, 4);
      radioBtn = new ImagePanel(iconRadio, 5, 4);
      checkBoxBtn = new ImagePanel(iconCheck, 5, 4);
      comboBtn = new ImagePanel(iconCombo, 5, 4);
      listBtn = new ImagePanel(iconList, 5, 4);
      //popupBtn = new ImagePanel(iconPopup, 5, 4);
      
      
      textboxLbl = new JLabel("Textbox");
      radioLbl = new JLabel("Radio");
      checkBoxLbl = new JLabel("CheckBox");
      comboLbl = new JLabel("Combo");
      listLbl = new JLabel("List");
      popupLbl = new JLabel("Popup");
      
      /*textboxBtn.setBackground(Color.pink);
      radioBtn.setBackground(Color.pink);
      checkBoxBtn.setBackground(Color.pink);
      comboBtn.setBackground(Color.pink);
      listBtn.setBackground(Color.pink);
      popupBtn.setBackground(Color.pink);*/
      
      textboxBtn.setLayout(null);
      radioBtn.setLayout(null);
      checkBoxBtn.setLayout(null);
      comboBtn.setLayout(null);
      listBtn.setLayout(null);
      //popupBtn.setLayout(null);
      
      selectPanel.setLayout(null);
      selectPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
      
      borderPanel.setBorder(BorderFactory.createLineBorder(new Color(124, 124, 124), 1));
      borderPanel.setLayout(null);
      
      textBoxProperties = pf.GenerateTextBoxProperies();
      textBoxPP = new PropertyPanel(textBoxProperties,220);
      textBoxSp = new PropertyScrollPane(textBoxPP, 220, 134);
      textBoxSp.setLocation(175, 10);
      
      radioProperties = pf.GenerateRadioProperies();
      radioPP = new PropertyPanel(radioProperties, 220);
      radioSp = new PropertyScrollPane(radioPP, 220, 134);
      radioSp.setLocation(175, 10);
          
      comboProperties = pf.GenerateComboProperies();
      comboPP = new PropertyPanel(comboProperties,220);
      comboSp = new PropertyScrollPane(comboPP, 220, 134);
      comboSp.setLocation(175, 10);
      
      whProperties = pf.GenerateWHProperties();
      whPP = new PropertyPanel(whProperties,220);
      whSp = new PropertyScrollPane(whPP, 220, 134);
      whSp.setLocation(175, 10);
      
      textboxBtn.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseEntered(MouseEvent e)
        {
          textboxBtn_mouseEntered(e);
        }
        
        public void mouseExited(MouseEvent e)
        {
          textboxBtn_mouseExited(e);
        }
        
        public void mousePressed(MouseEvent e)
        {
          textboxBtn_mousePressed(e);
        }
        
        public void mouseReleased(MouseEvent e)
        {
          textboxBtn_mouseReleased(e);
        }
        
      });
      
      radioBtn.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseEntered(MouseEvent e)
        {
          radioBtn_mouseEntered(e);
        }
        
        public void mouseExited(MouseEvent e)
        {
          radioBtn_mouseExited(e);
        }
        
        public void mousePressed(MouseEvent e)
        {
          radioBtn_mousePressed(e);
        }
        
        public void mouseReleased(MouseEvent e)
        {
          radioBtn_mouseReleased(e);
        }
        
      });
      
      checkBoxBtn.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseEntered(MouseEvent e)
        {
          checkBoxBtn_mouseEntered(e);
        }
        
        public void mouseExited(MouseEvent e)
        {
          checkBoxBtn_mouseExited(e);
        }
        
        public void mousePressed(MouseEvent e)
        {
          checkBoxBtn_mousePressed(e);
        }
        
        public void mouseReleased(MouseEvent e)
        {
          checkBoxBtn_mouseReleased(e);
        }
        
      });
      
      comboBtn.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseEntered(MouseEvent e)
        {
          comboBtn_mouseEntered(e);
        }
        
        public void mouseExited(MouseEvent e)
        {
          comboBtn_mouseExited(e);
        }
        
        public void mousePressed(MouseEvent e)
        {
          comboBtn_mousePressed(e);
        }
        
        public void mouseReleased(MouseEvent e)
        {
          comboBtn_mouseReleased(e);
        }
        
      });
      
      listBtn.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseEntered(MouseEvent e)
        {
          listBtn_mouseEntered(e);
        }
        
        public void mouseExited(MouseEvent e)
        {
          listBtn_mouseExited(e);
        }
        
        public void mousePressed(MouseEvent e)
        {
          listBtn_mousePressed(e);
        }
        
        public void mouseReleased(MouseEvent e)
        {
          listBtn_mouseReleased(e);
        }
        
      });
      /*
      popupBtn.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseEntered(MouseEvent e)
        {
          popupBtn_mouseEntered(e);
        }
        
        public void mouseExited(MouseEvent e)
        {
          popupBtn_mouseExited(e);
        }
        
        public void mousePressed(MouseEvent e)
        {
          popupBtn_mousePressed(e);
        }
        
        public void mouseReleased(MouseEvent e)
        {
          popupBtn_mouseReleased(e);
        }
        
      });
      */
      
  }
  private void DrawPanels()
  {
      int step = 26;
      this.setSize(mWidth, mHeight);
      
      selectPanel.setBounds(10, 10 , 165, 134);
      borderPanel.setBounds(0, 0 , 405, 154);
      textboxBtn.setBounds(2, 2, 161, step);
      radioBtn.setBounds(2, 2 + step, 161, step);
      checkBoxBtn.setBounds(2, 2 + 2 * step, 161, step);
      comboBtn.setBounds(2, 2 + 3 * step, 161, step);
      listBtn.setBounds(2, 2 + 4 * step, 161, step);
      //popupBtn.setBounds(2, 2 + 5 * step, 161, step);
      
      textboxLbl.setBounds(30, 3, mWidth / 2 - 30, 20);
      radioLbl.setBounds(30, 3, mWidth / 2 - 30, 20);
      checkBoxLbl.setBounds(30, 3, mWidth / 2 - 30, 20);
      comboLbl.setBounds(30, 3, mWidth / 2 - 30, 20);
      listLbl.setBounds(30, 3, mWidth / 2 - 30, 20);
      popupLbl.setBounds(30, 3, mWidth / 2 - 30, 20);
      
      textboxBtn.add(textboxLbl);
      radioBtn.add(radioLbl);
      checkBoxBtn.add(checkBoxLbl);
      comboBtn.add(comboLbl);
      listBtn.add(listLbl);
      //popupBtn.add(popupLbl);
      
      //textboxBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
      selectPanel.add(textboxBtn);
      selectPanel.add(radioBtn);
      selectPanel.add(checkBoxBtn);
      selectPanel.add(comboBtn);
      selectPanel.add(listBtn);
      //selectPanel.add(popupBtn);
      borderPanel.add(selectPanel);
      add(borderPanel);
      
  }

  private void textboxBtn_mousePressed(MouseEvent e)
  {
      SetEmptyBorder();
      
      if (mSelectedIndex != 0) 
      {
          domFrm.btnApply.setEnabled(true);
          domFrm.btnSave.setEnabled(true);
          btnAddA.setEnabled(false);
          btnDelA.setEnabled(false);
          btnChangeA.setEnabled(false);
          btnUp.setEnabled(false);
          btnDown.setEnabled(false);
          table.setEnabled(false);
      }
      
      borderPanel.add(textBoxSp);
      repaint();
      mSelectedIndex = 0;
      textboxBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
      textboxBtn.setX(6);
      textboxBtn.setY(5);
      textboxLbl.setBounds(31, 4, mWidth / 2 - 30, 20);
      textboxBtn.repaint();
  }
  
  
  private void textboxBtn_mouseReleased(MouseEvent e)
  {
      textboxBtn.setX(5);
      textboxBtn.setY(4);
      textboxLbl.setBounds(30, 3, mWidth / 2 - 30, 20);
      textboxBtn.repaint();
  }
    
  private void radioBtn_mousePressed(MouseEvent e)
  {
      SetEmptyBorder();
      
      if (mSelectedIndex != 1)
      {
          domFrm.btnApply.setEnabled(true);
          domFrm.btnSave.setEnabled(true);
          btnAddA.setEnabled(true);
          btnDelA.setEnabled(true);
          btnChangeA.setEnabled(true);
          btnUp.setEnabled(true);
          btnDown.setEnabled(true);
          table.setEnabled(true);
      }
      
      borderPanel.add(radioSp);
      repaint();
      mSelectedIndex = 1;
      radioBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
      radioBtn.setX(6);
      radioBtn.setY(5);
      radioLbl.setBounds(31, 4, mWidth / 2 - 30, 20);
      radioBtn.repaint();
  }
  
  
  private void radioBtn_mouseReleased(MouseEvent e)
  {
      radioBtn.setX(5);
      radioBtn.setY(4);
      radioLbl.setBounds(30, 3, mWidth / 2 - 30, 20);
      radioBtn.repaint();     
  }
  
  private void checkBoxBtn_mousePressed(MouseEvent e)
  {
      SetEmptyBorder();
      
      if (mSelectedIndex != 2)
      {
          domFrm.btnApply.setEnabled(true);
          domFrm.btnSave.setEnabled(true);
          btnAddA.setEnabled(true);
          btnDelA.setEnabled(true);
          btnChangeA.setEnabled(true);
          btnUp.setEnabled(true);
          btnDown.setEnabled(true);
          table.setEnabled(true);
      }
      
      mSelectedIndex = 2;
      borderPanel.add(whSp);
      repaint();
      checkBoxBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
      checkBoxBtn.setX(6);
      checkBoxBtn.setY(5);
      checkBoxLbl.setBounds(31, 4, mWidth / 2 - 30, 20);
      checkBoxBtn.repaint();
  }
  
  private void checkBoxBtn_mouseReleased(MouseEvent e)
  {
      checkBoxBtn.setX(5);
      checkBoxBtn.setY(4);
      checkBoxLbl.setBounds(30, 3, mWidth / 2 - 30, 20);
      checkBoxBtn.repaint();
  }
  
  private void comboBtn_mousePressed(MouseEvent e)
  {
      SetEmptyBorder();
      
      if (mSelectedIndex != 3)
      {
          domFrm.btnApply.setEnabled(true);
          domFrm.btnSave.setEnabled(true);
          btnAddA.setEnabled(true);
          btnDelA.setEnabled(true);
          btnChangeA.setEnabled(true);
          btnUp.setEnabled(true);
          btnDown.setEnabled(true);
          table.setEnabled(true);
      }
      
      mSelectedIndex = 3;
      borderPanel.add(comboSp);
      repaint();
      comboBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
      comboBtn.setX(6);
      comboBtn.setY(5);
      comboLbl.setBounds(31, 4, mWidth / 2 - 30, 20);
      comboBtn.repaint();
  }
  
  private void comboBtn_mouseReleased(MouseEvent e)
  {
      comboBtn.setX(5);
      comboBtn.setY(4);
      comboLbl.setBounds(30, 3, mWidth / 2 - 30, 20);
      comboBtn.repaint();
  }
  
  private void listBtn_mousePressed(MouseEvent e)
  {
      SetEmptyBorder();
      
      if (mSelectedIndex != 4) 
      {
          domFrm.btnApply.setEnabled(true);
          domFrm.btnSave.setEnabled(true);
          btnAddA.setEnabled(true);
          btnDelA.setEnabled(true);
          btnChangeA.setEnabled(true);
          btnUp.setEnabled(true);
          btnDown.setEnabled(true);
          table.setEnabled(true);
      }
      
      mSelectedIndex = 4;
      borderPanel.add(whSp);
      repaint();
      listBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
      listBtn.setX(6);
      listBtn.setY(5);
      listLbl.setBounds(31, 4, mWidth / 2 - 30, 20);
      listBtn.repaint();
  }
  
  private void listBtn_mouseReleased(MouseEvent e)
  {
      listBtn.setX(5);
      listBtn.setY(4);
      listLbl.setBounds(30, 3, mWidth / 2 - 30, 20);
      listBtn.repaint();
  }
  /*
  private void popupBtn_mousePressed(MouseEvent e)
  {
      SetEmptyBorder();
      
      if ((mSelectedIndex != 5) && (!domFrm.btnApply.isEnabled()))
      {
          domFrm.btnApply.setEnabled(true);
          domFrm.btnSave.setEnabled(true);
      }
      
      mSelectedIndex = 5;
      borderPanel.add(whSp);
      repaint();
      popupBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
      popupBtn.setX(6);
      popupBtn.setY(5);
      popupLbl.setBounds(31, 4, mWidth / 2 - 30, 20);
      popupBtn.repaint();
  }
  
  private void popupBtn_mouseReleased(MouseEvent e)
  {
      popupBtn.setX(5);
      popupBtn.setY(4);
      popupLbl.setBounds(30, 3, mWidth / 2 - 30, 20);
      popupBtn.repaint();
  }
  
  */
  
  private void textboxBtn_mouseExited(MouseEvent e)
  {
      if (mSelectedIndex == 0)
      {
          return;
      }
      
      textboxBtn.setBorder(BorderFactory.createEmptyBorder());
  }
  
  private void radioBtn_mouseExited(MouseEvent e)
  {
      if (mSelectedIndex == 1)
      {
          return;
      }
      
      radioBtn.setBorder(BorderFactory.createEmptyBorder());
  }
  
  private void checkBoxBtn_mouseExited(MouseEvent e)
  {
      if (mSelectedIndex == 2)
      {
          return;
      }
      
      checkBoxBtn.setBorder(BorderFactory.createEmptyBorder());
  }
  
  private void comboBtn_mouseExited(MouseEvent e)
  {
      if (mSelectedIndex == 3)
      {
          return;
      }
      
      comboBtn.setBorder(BorderFactory.createEmptyBorder());
  }
  
  private void listBtn_mouseExited(MouseEvent e)
  {
      if (mSelectedIndex == 4)
      {
          return;
      }
      
      listBtn.setBorder(BorderFactory.createEmptyBorder());
  }
  /*
  private void popupBtn_mouseExited(MouseEvent e)
  {
      if (mSelectedIndex == 5)
      {
          return;
      }
      
      popupBtn.setBorder(BorderFactory.createEmptyBorder());
  }
  
  */
  private void textboxBtn_mouseEntered(MouseEvent e)
  {
      if (mSelectedIndex == 0)
      {
          return;
      }
      textboxBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
  }

  private void radioBtn_mouseEntered(MouseEvent e)
  {
      if (mSelectedIndex == 1)
      {
          return;
      }
      radioBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
  }
  
  private void checkBoxBtn_mouseEntered(MouseEvent e)
  {
      if (mSelectedIndex == 2)
      {
          return;
      }
      checkBoxBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
  }
  
  private void comboBtn_mouseEntered(MouseEvent e)
  {
      if (mSelectedIndex == 3)
      {
          return;
      }
      comboBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
  }
  
  private void listBtn_mouseEntered(MouseEvent e)
  {
      if (mSelectedIndex == 4)
      {
          return;
      }
      listBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
  }
  /*
  private void popupBtn_mouseEntered(MouseEvent e)
  {
      if (mSelectedIndex == 5)
      {
          return;
      }
      popupBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
  }
 */
  private void SetEmptyBorder()
  {
  
      switch(mSelectedIndex)
      {
        case 0:
          borderPanel.remove(textBoxSp);
          textboxBtn.setBorder(BorderFactory.createEmptyBorder());
          break; 
          
        case 1:
          borderPanel.remove(radioSp);
          radioBtn.setBorder(BorderFactory.createEmptyBorder());
          break; 
          
        case 2:
          borderPanel.remove(whSp);
          checkBoxBtn.setBorder(BorderFactory.createEmptyBorder());
          break; 
          
        case 3:
          borderPanel.remove(comboSp);
          comboBtn.setBorder(BorderFactory.createEmptyBorder());
          break; 
          
        case 4:
          borderPanel.remove(whSp);
          listBtn.setBorder(BorderFactory.createEmptyBorder());
          break; 
        /*  
        case 5:
          borderPanel.remove(whSp);
          popupBtn.setBorder(BorderFactory.createEmptyBorder());
          break; */
      }
  }
  
  private void SetLoveredBorder()
  {
  
      switch(mSelectedIndex)
      {
        case 0:
          textboxBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
          btnAddA.setEnabled(false);
          btnDelA.setEnabled(false);
          btnChangeA.setEnabled(false);
          btnUp.setEnabled(false);
          btnDown.setEnabled(false);
          table.setEnabled(false);
          borderPanel.add(textBoxSp);
          repaint();
          break; 
          
        case 1:
          radioBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
          btnAddA.setEnabled(true);
          btnDelA.setEnabled(true);
          btnChangeA.setEnabled(true);
          btnUp.setEnabled(true);
          btnDown.setEnabled(true);
          table.setEnabled(true);
          borderPanel.add(radioSp);
          repaint();
          break; 
          
        case 2:
          borderPanel.add(whSp);
          btnAddA.setEnabled(true);
          btnDelA.setEnabled(true);
          btnChangeA.setEnabled(true);
          btnUp.setEnabled(true);
          btnDown.setEnabled(true);
          table.setEnabled(true);
          checkBoxBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
          break; 
          
        case 3:
          borderPanel.add(comboSp);
          btnAddA.setEnabled(true);
          btnDelA.setEnabled(true);
          btnChangeA.setEnabled(true);
          btnUp.setEnabled(true);
          btnDown.setEnabled(true);
          table.setEnabled(true);
          comboBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
          break; 
          
        case 4:
          borderPanel.add(whSp);
          listBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
          btnAddA.setEnabled(true);
          btnDelA.setEnabled(true);
          btnChangeA.setEnabled(true);
          btnUp.setEnabled(true);
          btnDown.setEnabled(true);
          table.setEnabled(true);
          break; 
          /*
        case 5:
          popupBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
          borderPanel.add(comboSp);
          repaint();
          break; 
          */
      }
  }

  private void AddButtons()
  {
      btnAddA = new JButton();
      btnDelA = new JButton();
      btnChangeA = new JButton();
      btnAddA.setText("Add Value");
      btnAddA.setFont(new Font("SansSerif", 0, 11));
      btnAddA.setEnabled(true);
      btnAddA.setBounds(305, 165, 100, 25);
      btnAddA.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnAddA_actionPerformed(e);
          }
        });
      add(btnAddA, null);
      
      btnDelA.setText("Delete Value");
      btnDelA.setFont(new Font("SansSerif", 0, 11));
      btnDelA.setEnabled(true);
      btnDelA.setBounds(305, 196, 100, 25);
      btnDelA.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnDelA_actionPerformed(e);
          }
        });
      add(btnDelA, null);
      
      btnChangeA.setText("Edit Value");
      btnChangeA.setFont(new Font("SansSerif", 0, 11));
      btnChangeA.setEnabled(true);
      btnChangeA.setBounds(305, 226, 100, 25);
      btnChangeA.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnChangeA_actionPerformed(e);
          }
        });
      add(btnChangeA, null);
      
     
      btnUp = new JButton();
      upIcon = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
      btnUp.setIcon(upIcon);
      btnUp.setEnabled(true);
      btnUp.setBounds(0, 165, 25, 20);
      btnUp.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnUp_actionPerformed(e);
          }
        });
      add(btnUp, null);
     
      btnDown = new JButton();
      downIcon = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
      btnDown.setIcon(downIcon);
      btnDown.setEnabled(true);
      btnDown.setBounds(0, 229, 25, 20);
      btnDown.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnDown_actionPerformed(e);
          }
        });
      add(btnDown, null);
  }
  private void btnAddA_actionPerformed(ActionEvent e)
  {
      AddDisplayValue dialog = new AddDisplayValue("", "", "Add Value", parent);
      Settings.Center(dialog);
      dialog.setVisible(true);;
      
      if (dialog.action == dialog.SAVE)
      {
          domFrm.btnApply.setEnabled(true);
          domFrm.btnSave.setEnabled(true);
          DisplayAttValue value = new DisplayAttValue(-1, dialog.value, dialog.displayValue);
          datm.data.add(value);
          datm.fireTableDataChanged();
      }
      dialog.dispose();
      
  }
  
  private void btnDelA_actionPerformed(ActionEvent e)
  {
      int rowIndex = table.getSelectedRow();
      if ((rowIndex < 0) || (rowIndex >= table.getRowCount()))
      {
          return;
      }
      
      int action = JOptionPane.showConfirmDialog(this, "Confirm delete", "Display Attributes", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
      
      if (action == JOptionPane.OK_OPTION)
      {
          domFrm.btnApply.setEnabled(true);
          domFrm.btnSave.setEnabled(true);
          DisplayAttValue temp = (DisplayAttValue) datm.data.get(rowIndex);
          if (temp.id > -1)
          {
              datm.delData.add(temp);
          }
          datm.data.remove(rowIndex);
          int size = datm.data.size();
          
          for(int j = rowIndex; j < size; j++)
          {
              ((DisplayAttValue)datm.data.get(j)).operation = DisplayAttValue.DIRTY;
          }
          datm.fireTableDataChanged();
      }
  }
  
  private void btnChangeA_actionPerformed(ActionEvent e)
  {
      int rowIndex = table.getSelectedRow();
      if ((rowIndex < 0) || (rowIndex >= table.getRowCount()))
      {
          return;
      }
      
      AddDisplayValue dialog = new AddDisplayValue(((DisplayAttValue)datm.data.get(rowIndex)).value, ((DisplayAttValue)datm.data.get(rowIndex)).displayValue, "Edit Value", parent);
      Settings.Center(dialog);
      dialog.setVisible(true);;
      
      if (dialog.action == dialog.SAVE)
      {
          domFrm.btnApply.setEnabled(true);
          domFrm.btnSave.setEnabled(true);
          DisplayAttValue oldValue = (DisplayAttValue)datm.data.get(rowIndex);
          DisplayAttValue value = new DisplayAttValue(oldValue.id, dialog.value, dialog.displayValue);
          value.operation = DisplayAttValue.DIRTY;
          datm.data.setElementAt(value, rowIndex);
          datm.fireTableDataChanged();
      }
      dialog.dispose();
  }
  
  private void btnUp_actionPerformed(ActionEvent e)
  {
      int rowIndex = table.getSelectedRow();
      datm.IncrementRow(rowIndex);
      
      if (rowIndex > 0)
      {
          domFrm.btnApply.setEnabled(true);
          domFrm.btnSave.setEnabled(true);
          table.getSelectionModel().setSelectionInterval(rowIndex - 1,rowIndex - 1);
      }
      table.repaint();
  }
  
  private void btnDown_actionPerformed(ActionEvent e)
  {
      int rowIndex = table.getSelectedRow();
      datm.DecrementRow(rowIndex);
      
      if (rowIndex + 1 < datm.data.size())
      {
          domFrm.btnApply.setEnabled(true);
          domFrm.btnSave.setEnabled(true);
          table.getSelectionModel().setSelectionInterval(rowIndex + 1,rowIndex + 1);
      }
      
  }
  
  
  private void InitTable()
  {
      datm = new DisplayValueTableModel();
      table = new JTable(datm);
      tableScroll = new JScrollPane();
          
      Vector data = new Vector();
      DisplayAttValue value;
      
      try
      {
          Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
          ResultSet rs = statement.executeQuery("select DDV_id, DDV_seq, DDV_value, DDV_value_display from IISC_DOM_DISPLAY_VALUES where Dom_id =" + Dom_id + " order by DDV_seq");
          
          while(rs.next())
          {
              data.add(new DisplayAttValue(rs.getInt("DDV_id"), rs.getString("DDV_value"), rs.getString("DDV_value_display")));
          }          
          
      }
      catch(SQLException sqle)
      {
          System.out.println("Sql exception :" + sqle);
      }
      
      datm.populateData(data);
           
      table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      table.setRowSelectionAllowed(true);
      table.setGridColor(new Color(0,0,0));
      table.setBackground(Color.white);
      //table.setAutoResizeMode(0);
      table.getTableHeader().setReorderingAllowed(false);
      table.setAutoscrolls(true);
      table.getTableHeader().setResizingAllowed(true);
      //rowSMatt = table.getSelectionModel();
      table.setBorder(BorderFactory.createLineBorder(new Color(124, 124, 124), 1));
      //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      //table.setPreferredSize(new Dimension(300, 20));
      table.setPreferredScrollableViewportSize(new Dimension(500, 70));
      
      tableScroll.setBounds(30, 165, 270, 84);
      tableScroll.getViewport().add(table);
      add(tableScroll);
      
      TableColumn tc = table.getColumnModel().getColumn(0);
      //System.out.println("sirina" + tc.getPreferredWidth());
      //tc.setWidth(20);
      tc.setWidth(20);
      tc.setPreferredWidth(20);
      datm.fireTableStructureChanged();
      table.repaint();
  
  }
  
  public boolean Update()
  {
      try
      {
        Statement statement = conn.createStatement();
        ResultSet rs2;
        String sql, w = "100", h = "100";
        
        if (domExists)
        {
            if (pf.getDDType() != mSelectedIndex)
            {
                statement.execute("update IISC_DOMAIN_DISPLAY set DD_type=" + mSelectedIndex + " where Dom_id=" + Dom_id);
            }
              
            switch(mSelectedIndex)
            {
                case 0:
                
                  w = textBoxProperties[1].getValueTextArea().getText();
                  if (!IsNumber(w))
                  {
                      return false;
                  }
                  
                  h = textBoxProperties[0].getValueTextArea().getText();
                  if (!IsNumber(h))
                  {
                      return false;
                  }
                  statement.execute("update IISC_DOMAIN_DISPLAY set DD_width=" + w + ",DD_height=" + h + ", DD_text_multiline=" + ((JComboBox)textBoxProperties[2].getComponent()).getSelectedIndex() + ",DD_text_scroll=" + ((JComboBox)textBoxProperties[3].getComponent()).getSelectedIndex() + ",DD_input_align=" + ((JComboBox)textBoxProperties[4].getComponent()).getSelectedIndex() + " where Dom_id=" + Dom_id);
                  break;
                  
                case 1:
                  
                  w = radioProperties[1].getValueTextArea().getText();
                  if (!IsNumber(w))
                  {
                      return false;
                  }
                  
                  h = radioProperties[0].getValueTextArea().getText();
                  if (!IsNumber(h))
                  {
                      return false;
                  }
                  statement.execute("update IISC_DOMAIN_DISPLAY set DD_width=" + w + ",DD_height=" + h + ", DD_radio_orientation=" + ((JComboBox)radioProperties[2].getComponent()).getSelectedIndex() + ",DD_input_align=" + ((JComboBox)radioProperties[3].getComponent()).getSelectedIndex() + " where Dom_id=" + Dom_id);
                  
                  break;
                  
              case 3:
              
                  w = comboProperties[1].getValueTextArea().getText();
                  if (!IsNumber(w))
                  {
                      return false;
                  }
                  
                  h = comboProperties[0].getValueTextArea().getText();
                  if (!IsNumber(h))
                  {
                      return false;
                  }
                  statement.execute("update IISC_DOMAIN_DISPLAY set DD_width=" + w + ",DD_height=" + h + ", DD_combo_editable=" + ((JComboBox)comboProperties[2].getComponent()).getSelectedIndex() + ",DD_input_align=" + ((JComboBox)comboProperties[3].getComponent()).getSelectedIndex() + " where Dom_id=" + Dom_id);
                  break;
              
              case 2:
              case 4:
              //case 5:
                  w = whProperties[1].getValueTextArea().getText();
                  
                  if (!IsNumber(w))
                  {
                      return false;
                  }
                  
                  h = whProperties[0].getValueTextArea().getText();
                  if (!IsNumber(h))
                  {
                      return false;
                  }
                  statement.execute("update IISC_DOMAIN_DISPLAY set DD_width=" + w + ",DD_height=" + h + ",DD_input_align=" + ((JComboBox)whProperties[2].getComponent()).getSelectedIndex() + " where Dom_id=" + Dom_id);
                  break;
            }
            
            textBoxProperties[1].getValueTextArea().setText(w);
            textBoxProperties[0].getValueTextArea().setText(h);
            comboProperties[1].getValueTextArea().setText(w);
            comboProperties[0].getValueTextArea().setText(h);
            radioProperties[1].getValueTextArea().setText(w);
            radioProperties[0].getValueTextArea().setText(h);
            whProperties[1].getValueTextArea().setText(w);
            whProperties[0].getValueTextArea().setText(h);
            
            int size = datm.delData.size();
            DisplayAttValue value;
            int i;
            
            for(i = 0; i < size; i++)
            {
                value = (DisplayAttValue)datm.delData.get(i);
                
                if (value.id > -1)
                {
                    statement.execute("delete from IISC_DOM_DISPLAY_VALUES where DDV_id=" + value.id);     
                }
            }
            
            size = datm.data.size();
            
            for(i = 0; i < size; i++)
            {
                value = (DisplayAttValue)datm.data.get(i);
                
                if (value.id > -1)
                {
                    if (value.operation == DisplayAttValue.DIRTY)
                    {
                        sql = "update IISC_DOM_DISPLAY_VALUES set DDV_seq=" + i + ",DDV_value=" + value.value + ", DDV_value_display=" + value.displayValue + " where DDV_id=" + value.id;
                        
                        statement.execute("update IISC_DOM_DISPLAY_VALUES set DDV_seq=" + i + ",DDV_value='" + value.value + "', DDV_value_display='" + value.displayValue + "' where DDV_id=" + value.id);    
                        value.operation = DisplayAttValue.CLEAN;
                        datm.data.setElementAt(value,i);
                    }
                }
                else
                {
                  if (nextVal == -1)
                    {
                        try
                        {
                            rs2 = statement.executeQuery("select max(DDV_id) from IISC_DOM_DISPLAY_VALUES");
                            if (rs2.next())
                            {
                                nextVal = rs2.getInt(1);
                            }
                        }
                        catch(SQLException sqle)
                        {
                            nextVal = 0;
                        }
                    }
                    else
                    {
                        nextVal = nextVal + 1;
                    }
                    
                    statement.execute("insert into IISC_DOM_DISPLAY_VALUES(DDV_id, PR_id, Dom_id, DDV_seq, DDV_value,DDV_value_display) values(" + (nextVal + 1) + "," + PR_id + "," + Dom_id + "," + i + ",'" + value.value + "','" + value.displayValue + "')");
                    value.id = nextVal + 1;
                    value.operation = DisplayAttValue.CLEAN;
                    datm.data.setElementAt(value, i);
                }
      
            }
        }
        else
        {
            UpdateNewDomain();
        }
        
        return true;
      }
      catch(SQLException sqle)
      {
          return UpdateNewDomain();
      }
  }
  
  
  private boolean IsNumber(String str)
  {
      try
      {
          Integer.parseInt(str);
          return true;
      }
      catch(Exception e)
      {
          return false;
      }
  }
  public boolean UpdateNewDomain()
  {
      try
      {
          Statement statement = conn.createStatement();
          
          switch(mSelectedIndex)
          {
              case 0:
                
                  String w = textBoxProperties[1].getValueTextArea().getText();
                  if (!IsNumber(w))
                  {
                      return false;
                  }
                  
                  String h = textBoxProperties[0].getValueTextArea().getText();
                  if (!IsNumber(h))
                  {
                      return false;
                  }
                  
                  if (w.equals("0"))
                  {
                    w = "200";
                  }
                  
                  if ( h.equals("0"))
                  {
                    h = "20";
                  }
                  
                  statement.execute("insert into IISC_DOMAIN_DISPLAY(Dom_id,PR_id,DD_Type, DD_height, DD_width, DD_text_multiline,DD_text_scroll,DD_radio_orientation, DD_combo_editable, DD_input_align) values(" + Dom_id + "," + PR_id + ", " + mSelectedIndex + "," + h + "," + w + "," + ((JComboBox)textBoxProperties[2].getComponent()).getSelectedIndex() + "," + ((JComboBox)textBoxProperties[3].getComponent()).getSelectedIndex() + ",0,0," + ((JComboBox)textBoxProperties[4].getComponent()).getSelectedIndex()  + ")");
                  break;
              
              case 1:
                  w = radioProperties[1].getValueTextArea().getText();
                  if (!IsNumber(w))
                  {
                      return false;
                  }
                  
                  h = radioProperties[0].getValueTextArea().getText();
                  if (!IsNumber(h))
                  {
                      return false;
                  }
                  
                  if (w.equals("0"))
                  {
                    w = "200";
                  }
                  
                  if ( h.equals("0"))
                  {
                    h = "20";
                  }
                  
                  statement.execute("insert into IISC_DOMAIN_DISPLAY(Dom_id,PR_id,DD_Type, DD_height, DD_width, DD_text_multiline,DD_text_scroll,DD_radio_orientation, DD_combo_editable, DD_input_align) values(" + Dom_id + "," + PR_id + ", " + mSelectedIndex + "," + h + "," + w + ",0,0," + ((JComboBox)radioProperties[2].getComponent()).getSelectedIndex() + ",0," + ((JComboBox)radioProperties[3].getComponent()).getSelectedIndex()  + ")");
                  
                  break;
                  
              case 3:
                  w = comboProperties[1].getValueTextArea().getText();
                  if (!IsNumber(w))
                  {
                      return false;
                  }
                  
                  h = comboProperties[0].getValueTextArea().getText();
                  if (!IsNumber(h))
                  {
                      return false;
                  }
                  
                  if (w.equals("0"))
                  {
                    w = "200";
                  }
                  
                  if ( h.equals("0"))
                  {
                    h = "20";
                  }

                  statement.execute("insert into IISC_DOMAIN_DISPLAY(Dom_id,PR_id,DD_Type, DD_height, DD_width, DD_text_multiline,DD_text_scroll,DD_radio_orientation, DD_combo_editable, DD_input_align) values(" + Dom_id + "," + PR_id + ", " + mSelectedIndex + "," + h + "," + w + ",0,0,0," + ((JComboBox)comboProperties[2].getComponent()).getSelectedIndex() + "," + ((JComboBox)comboProperties[3].getComponent()).getSelectedIndex()  + ")");
                  
                  break;
                  
                  case 2:
                  case 4:
                  //case 5:
                      w = whProperties[1].getValueTextArea().getText();
                      if (!IsNumber(w))
                      {
                          return false;
                      }
                      
                      h = whProperties[0].getValueTextArea().getText();
                      if (!IsNumber(h))
                      {
                          return false;
                      }
                      
                      if (w.equals("0"))
                      {
                        w = "200";
                      }
                      
                      if ( h.equals("0"))
                      {
                        h = "20";
                      }

                      statement.execute("insert into IISC_DOMAIN_DISPLAY(Dom_id,PR_id,DD_Type,DD_height,DD_width,DD_text_multiline,DD_text_scroll,DD_radio_orientation,DD_combo_editable,DD_input_align) values(" + Dom_id + "," + PR_id + ", " + mSelectedIndex + "," + h + "," + w + ",0,0,0,0," + ((JComboBox)whProperties[2].getComponent()).getSelectedIndex()  + ")");
                      
                      break;
          }
          
          int size = datm.data.size();
          DisplayAttValue value;
          ResultSet rs2;
          
          try
          {
              rs2 = statement.executeQuery("select max(DDV_id) from IISC_DOM_DISPLAY_VALUES");
              if (rs2.next())
              {
                  nextVal = rs2.getInt(1);
              }
              else
              {
                  nextVal = nextVal + 1;
              }
          }
          catch(SQLException sqle)
          {
              nextVal = 0;
          }
                        
          for(int i = 0; i < size; i++)
          {
              value = (DisplayAttValue)datm.data.get(i);
              nextVal = nextVal + 1;
              statement.execute("insert into IISC_DOM_DISPLAY_VALUES(DDV_id, PR_id, Dom_id, DDV_seq, DDV_value,DDV_value_display) values(" + nextVal + "," + PR_id + "," + Dom_id + "," + i + ",'" + value.value + "','" + value.displayValue + "')");
              value.id = nextVal;
              value.operation = DisplayAttValue.CLEAN;
              datm.data.setElementAt(value, i);
          }
      
          domExists = true;
          return true;
      }
      catch(SQLException sqle)
      {
          //UpdateNewDomain();
          return false;
          //System.out.print("Sql exception :" + sqle.toString());
      }
  }
  
  /*************************************************************************/
  /*********                Klasa za table model                    ********/
  /*************************************************************************/
  private class DisplayValueTableModel extends AbstractTableModel
  {
      private String[] headers;
      private Vector data;
      private Vector delData = new Vector();
      
      public DisplayValueTableModel()
      {
          headers = new String[3];
          data = new Vector();
          headers[1] = "Value";
          headers[2] = "Display value";
          headers[0] = "Seq";
      }
      
      public int getColumnCount()
      {
          return 3;
      }
      
      public String getColumnName(int index)
      {
          return headers[index];
      }
      
      public int getRowCount()
      {
          return data.size();
      }
      
      public Object getValueAt(int x, int y)
      {
          DisplayAttValue value = (DisplayAttValue)data.get(x);
          
          if (y == 0)
          {
              return (Object)(Integer.toString(x + 1));
          }
          else
          {
              if (y == 1)
              { 
                  return (Object)value.getDisplayValue();
              } 
              else
              {
                  return (Object)value.getValue();
              }
          }
      }
      
      public void setValueAt(Object value, int row, int col) 
      {
          if (col == 0)
          {
              ((DisplayAttValue)data.elementAt(row)).setValue((String)value);
              fireTableCellUpdated(row, col);
          }
          else
          {
              ((DisplayAttValue)data.elementAt(row)).setDisplayValue((String)value);
              fireTableCellUpdated(row, col);
          }
      }
      
      public void IncrementRow(int rowIndex)
      {
          if (rowIndex == 0) 
          {
              return;
          }
          
          DisplayAttValue temp1 = (DisplayAttValue)data.get(rowIndex);
          DisplayAttValue temp2 = (DisplayAttValue)data.get(rowIndex - 1);
          
          temp1.operation = DisplayAttValue.DIRTY;
          temp2.operation = DisplayAttValue.DIRTY;
          
          data.set(rowIndex, temp2);
          data.set(rowIndex - 1, temp1);
          fireTableDataChanged();
      }
      
      public void DecrementRow(int rowIndex)
      {
          if (rowIndex + 1 == data.size()) 
          {
              return;
          }
          
          DisplayAttValue temp1 = (DisplayAttValue)data.get(rowIndex);
          DisplayAttValue temp2 = (DisplayAttValue)data.get(rowIndex + 1);
          
          temp1.operation = DisplayAttValue.DIRTY;
          temp2.operation = DisplayAttValue.DIRTY;
          
          data.set(rowIndex, temp2);
          data.set(rowIndex + 1, temp1);
          fireTableDataChanged();
      }
      
      public void populateData(Vector value)
      {
          /*int size = value.length;
          
          for( int i = 0; i < size; i++)
          {
              data.add((Object)value[i]);
          }
          */
          data = value;
          
          fireTableDataChanged();
      }
      
      public boolean isCellEditable(int row, int col) 
      {
          return false;
      }    
  }
  
  private class DisplayAttValue
  {
      private int id;
      private String value;
      private String displayValue;
      private int operation;
      public static final int DIRTY = 0;
      public static final int CLEAN = 1;
      
      
      public DisplayAttValue(int p_id, String p_Value, String p_DisplayValue)
      {
          id = p_id;
          value = p_Value;
          displayValue = p_DisplayValue;
          operation = CLEAN;
      }
      
      public void setValue(String p_Value)
      {
          value = p_Value;
      }
      
      public String getValue()
      {
          return value;
      }
      
      public void setDisplayValue(String p_DisplayValue)
      {
          displayValue = p_DisplayValue;
      }
      
      public String getDisplayValue()
      {
          return displayValue;
      }
  }
  
  /******************************************************************/
  /********       Forma za editovanje vrijednosti iz tabele    ******/
  /******************************************************************/
  private class AddDisplayValue extends JDialog
  {
      private String value;
      private String displayValue;
      private JLabel valueLbl = new JLabel("Value");
      private JLabel dispValueLbl = new JLabel("Display Value");
      private JTextField valueTxt = new JTextField("");
      private JTextField dispValueTxt = new JTextField("");
      private JButton saveBtn = new JButton("OK");
      private JButton cancelBtn = new JButton("Cancel");
      private int SAVE = 0;
      private int CANCEL = 1;
      private int action;
      
      
      public AddDisplayValue(String p_Value, String p_DisplayValue, String title, IISFrameMain parent)
      {
          super(parent, title, true);
          value = p_Value;
          displayValue = p_DisplayValue;
          
          setSize(285, 140);
          getContentPane().setLayout(null);
          setResizable(false);
          action = CANCEL;
          
          valueLbl.setBounds(10, 10, 80, 20);
          dispValueLbl.setBounds(10, 35, 80, 20);
          valueTxt.setBounds(90, 10, 180, 20);
          valueTxt.setText(value);
          dispValueTxt.setText(displayValue);
          dispValueTxt.setBounds(90, 35, 180, 20);
          saveBtn.setBounds(55, 75, 70, 25);
          cancelBtn.setBounds(160, 75, 70, 25);
          saveBtn.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              saveBtn_actionPerformed(e);
            }
          });
          
          cancelBtn.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              cancelBtn_actionPerformed(e);
            }
          });
         
          getContentPane().add(valueLbl);
          getContentPane().add(dispValueLbl);
          getContentPane().add(valueTxt);
          getContentPane().add(dispValueTxt);
          getContentPane().add(saveBtn);
          getContentPane().add(cancelBtn);
      }
      private void saveBtn_actionPerformed(ActionEvent e)
      {
          action = SAVE;
          value = valueTxt.getText();
          displayValue = dispValueTxt.getText();
          this.setVisible(false);
      }
      
      private void cancelBtn_actionPerformed(ActionEvent e)
      {
          action = CANCEL;
          this.setVisible(false);;
      }
  }



  public class ImagePanel extends JPanel implements ImageObserver
  {
  
    Image mImage;
    int x;
    int y;
  
    public ImagePanel(ImageIcon p_Icon, int p_X, int p_Y)
    {
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
}

