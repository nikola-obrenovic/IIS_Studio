package iisc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.MatteBorder;
import java.sql.*;

public class Property 
{
  private String prompt;
  private String name;
  private String value;
  private String promptValue;
  private String type;
  private Component control;
  private Object[] promptItems;  
  private Object[] items;
  private PropertyTextBox nameTextArea;
  private JTextField valueTextArea;
  private JDialog dialog;
  private PropertyPanel parent;
  private boolean dontTach;
  private JPanel colorPanel;
  String tableName;
  Connection conn;
  int id;
  private boolean enabled;
  int rowNum;
  ControlPanel cp;
  
  public Property(String pName, String pPrompt, String pType, String pValue, String _tableName, boolean _enabled, ControlPanel _cp)
  {
      prompt = pPrompt;
      name = pName;
      type = pType;
      value = pValue;
      tableName = _tableName;
      enabled = _enabled;
      cp = _cp;
      
      initTextFileds();
      
      if (type.compareTo("text") == 0)
      {
          valueTextArea.setEnabled(enabled);
      }
      
      if (type.compareTo("color") == 0)
      {
          valueTextArea.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
          valueTextArea.setBackground(Color.white);
          valueTextArea.setEditable(false);
          //valueTextArea.setBorder((Border) new EmptyBorder(new Insets(2, 25 , 2, 1)));
          control = new JButton("Pick");
          colorPanel = new JPanel();
          colorPanel.setBorder(new LineBorder(Color.BLACK,1));
          colorPanel.setBounds(7, 5, 15, 9);
          colorPanel.setFocusable(false);
          colorPanel.setBackground(Color.red);
          valueTextArea.add(colorPanel, null);
          valueTextArea.setText("Color.red");
          
          ((JButton)control).addMouseListener(new java.awt.event.MouseAdapter()
          {
            public void mouseClicked(MouseEvent e)
            {
              control_mouseClicked(e);
            }
          });
          
          control.addFocusListener(new java.awt.event.FocusAdapter()
          {
              public void focusGained(FocusEvent e)
              {
                control_focusGained(e);
              }
          });
      }
  }
  
   public Property(String pPrompt, String pName, String pType, int selectedIndex, Object[] _items, Object[] _promptItems, String _tableName, boolean _enabled, ControlPanel _cp)
   {
      name = pName;
      type = pType;
      prompt = pPrompt;
      items = _items;
      promptItems = _promptItems;
      tableName = _tableName;
      enabled = _enabled;
      cp = _cp;
      
      initTextFileds();
      
      if ((type.compareTo("combo") == 0) || (type.compareTo("usagechild") == 0))
      {
          valueTextArea.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
          control = new JComboBox(promptItems);
          control.setEnabled(enabled);
          ((JComboBox)control).setSelectedIndex(selectedIndex);
          valueTextArea.setText(promptItems[selectedIndex].toString());
          
          control.addFocusListener(new java.awt.event.FocusAdapter()
          {
              public void focusLost(FocusEvent e)
              {
                control_focusLost(e);
              }
          });
          
          ((JComboBox)control).addActionListener(new ActionListener()
          {
             public void actionPerformed(ActionEvent e)
            {
              control_actionPerformed(e);
            }
          });
      }
      
      
  }
  
  public Property(String pPrompt, String pName, String pType, int selectedIndex, Object[] _items, Object[] _promptItems, String _tableName, boolean _enabled, int _rowNum, ControlPanel _cp)
  {
      name = pName;
      type = pType;
      prompt = pPrompt;
      items = _items;
      promptItems = _promptItems;
      tableName = _tableName;
      enabled = _enabled;
      rowNum = _rowNum;
      cp = _cp;
      
      initTextFileds();
      
      valueTextArea.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      control = new JComboBox(promptItems);
      control.setEnabled(enabled);
      ((JComboBox)control).setSelectedIndex(selectedIndex);
      valueTextArea.setText(promptItems[selectedIndex].toString());
      
      control.addFocusListener(new java.awt.event.FocusAdapter()
      {
          public void focusLost(FocusEvent e)
          {
            control_focusLost(e);
          }
      });
      ((JComboBox)control).addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          control_actionPerformed(e);
        }
      });
      
      
      
  }
  
  private void initTextFileds()
  {
      nameTextArea = new PropertyTextBox(prompt);
      nameTextArea.setBorder((Border) new EmptyBorder(new Insets(2, 4 , 0, 1)));
      nameTextArea.setEditable(false);
      nameTextArea.setBackground(Color.white);
      nameTextArea.addFocusListener(new java.awt.event.FocusAdapter()
      {
        public void focusGained(FocusEvent e)
        {
          nameTextArea_focusGained(e);
        }
        
        public void focusLost(FocusEvent e)
        {
          nameTextArea_focusLost(e);
        }
        
      });
      
      valueTextArea = new JTextField(value);
      valueTextArea.setVisible(true);
      valueTextArea.setBorder((Border) new EmptyBorder(new Insets(2, 4 , 0, 1)));
      valueTextArea.addFocusListener(new java.awt.event.FocusAdapter()
      {
        public void focusGained(FocusEvent e)
        {
          valueTextArea_focusGained(e);
        }
        
        public void focusLost(FocusEvent e)
        {
          valueTextArea_focusLost(e);
        }
        
      });
      
      valueTextArea.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyReleased(KeyEvent e)
        {
          valueTextArea_keyReleased(e);
        }
      });
  }
  public PropertyTextBox getNameTextArea()
  {
      return nameTextArea;
  }
  
  public JTextField getValueTextArea()
  {
      return valueTextArea;
  }
  
  public Component getComponent()
  {
      return control;
  }
  public void setParent(PropertyPanel _parent)
  {
      parent = _parent;
  }
  
  private void nameTextArea_focusGained(FocusEvent e)
  {
      //nameTextArea.setBorder(new LineBorder(Color.BLACK, 2, true));
      //valueTextArea.setBorder(new LineBorder(Color.BLACK, 2, true));
      nameTextArea.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
      nameTextArea.repaint();
      valueTextArea.setBackground(SystemColor.textHighlight);
      valueTextArea.setForeground(Color.WHITE);
      valueTextArea.repaint();
      
  }
  private void nameTextArea_focusLost(FocusEvent e)
  {
      //nameTextArea.setBorder(new LineBorder(Color.BLACK, 1));
      //valueTextArea.setBorder(new LineBorder(Color.BLACK, 1));
      nameTextArea.setBorder((Border) new EmptyBorder(new Insets(2, 4 , 0, 1)));
      nameTextArea.repaint();
      valueTextArea.setBackground(Color.white);
      valueTextArea.setForeground(Color.BLACK);
      nameTextArea.repaint();
      
  }
  
  private void control_focusLost(FocusEvent e)
  {
      nameTextArea.setBackground(Color.WHITE);
      nameTextArea.setForeground(Color.black);
      //valueTextArea.setBorder(new LineBorder(Color.BLACK, 1));
      //valueTextArea.setBackground(Color.white);
      if (type.compareTo("combo") == 0)
      {
          
          valueTextArea.setText(((JComboBox)control).getSelectedItem().toString());
          valueTextArea.removeAll();
          //parent.add(valueTextArea);
      }
      if (type.compareTo("usage") == 0)
      {
          String text = ((JComboBox)control).getSelectedItem().toString();
          valueTextArea.setText(text);
          valueTextArea.removeAll();
          
          //parent.add(valueTextArea);
      }
      if (type.compareTo("color") == 0)
      {
          //dontTach = false;
      }
  }
  
  private void valueTextArea_focusGained(FocusEvent e)
  {
      
      if ((type.compareTo("combo") == 0) || (type.compareTo("usage") == 0) || (type.compareTo("usagechild") == 0))
      {
          nameTextArea.setBackground(SystemColor.textHighlight);
          nameTextArea.setForeground(Color.white);
          nameTextArea.repaint();
          //control.setBounds(valueTextArea.getBounds());
          //parent.remove(valueTextArea);
          //valueTextArea.removeAll();
          control.setSize(valueTextArea.getWidth() , valueTextArea.getHeight() );
          control.setBounds(0, 0, valueTextArea.getWidth(), valueTextArea.getHeight());
          //((JComboBox)control).setPreferredSize(new Dimension(valueTextArea.getWidth(), valueTextArea.getHeight()));
          //control.invalidate();
          
          valueTextArea.add(control, null);
          valueTextArea.repaint();
         
          control.repaint();
          parent.repaint();
          
          if (enabled)
          {
              control.setFocusable(true);
              //control.setBackground(Color.white);
              control.requestFocus();
              ((JComboBox)control).setPopupVisible(true);
          //((JComboBox)control).setVisible(true);
          }
      }
      if (type.compareTo("color") == 0)
      {
           nameTextArea.setBackground(SystemColor.textHighlight);
           nameTextArea.setForeground(Color.white);
           //valueTextArea.requestFocus();
           control.setBounds(valueTextArea.getWidth() - 30, 0, 30, valueTextArea.getHeight());
           valueTextArea.add(control, null);
           control.setFocusable(false);
           //parent.repaint();
           valueTextArea.repaint();
          //control.setBounds(valueTextArea.getBounds());
          //parent.remove(valueTextArea);
          
      }
      
      nameTextArea.setBackground(SystemColor.textHighlight);
      nameTextArea.setForeground(Color.white);
      nameTextArea.repaint();
  }
    private void valueTextArea_focusLost(FocusEvent e)
    {
        
        
        if (type.compareTo("color") == 0)
        {
         //  control.setVisible(false);
            if (control.isFocusOwner())
                return;
                
            valueTextArea.remove(control);
            control.repaint();
            parent.repaint();
            valueTextArea.repaint();
            nameTextArea.setBackground(Color.white);
            return;
        }
        
        nameTextArea.setBackground(Color.white);
        nameTextArea.setForeground(Color.BLACK);
    }
    
    private void valueTextArea_keyReleased(KeyEvent e)
    {
    
        if (cp != null)
        {
            try
            {
                ((AttDisplayProperties)cp).parFrm.btnApply.setEnabled(true);
                ((AttDisplayProperties)cp).parFrm.btnSave.setEnabled(true);
            }
            catch(Exception ex)
            {
                try
                {
                    ((CtaDisplayProperties)cp).parFrm.btnApply.setEnabled(true);
                    ((CtaDisplayProperties)cp).parFrm.btnSave.setEnabled(true);
                }
                catch(Exception ex1)
                {
                    cp.domFrm.btnApply.setEnabled(true);
                    cp.domFrm.btnSave.setEnabled(true);
                }
            }
        }
    
    }
    private void control_mouseClicked(MouseEvent e)
    {
        
        if (type.compareTo("color") == 0)
        {
            JColorChooser tcc = new JColorChooser();
            Color color;
            color = tcc.showDialog(parent, "Choose color", Color.red);
            if (color != null)
            {
                colorPanel.setBackground(color);
                colorPanel.repaint();
                valueTextArea.setText(color.getRed() + "," +color.getGreen() + "," + color.getBlue());
            }
           
        }
    }
    private void control_focusGained(FocusEvent e)
    {
        if (type.compareTo("color") == 0)
        {
            //dontTach = true;
           // valueTextArea.requestFocus();
        }
    }
    private void control_actionPerformed(ActionEvent e)
    {
    
        if (type.compareTo("usage") == 0)
        {
            String text = ((JComboBox)control).getSelectedItem().toString();
              
            if (text.compareTo("menu") == 0)
            {
                parent.getProperties()[rowNum].control.setEnabled(false);
            }
            else
            {
                parent.getProperties()[rowNum].control.setEnabled(true);
            }
        }
        
        if (type.compareTo("combo") == 0)
        {
            if (cp != null)
            {
                if (valueTextArea.getText().compareTo(((JComboBox)control).getSelectedItem().toString()) != 0)
                {
                    try
                    {
                        ((AttDisplayProperties)cp).parFrm.btnApply.setEnabled(true);
                        ((AttDisplayProperties)cp).parFrm.btnSave.setEnabled(true);
                    }
                    catch(Exception ex)
                    {
                        try
                        {
                            ((CtaDisplayProperties)cp).parFrm.btnApply.setEnabled(true);
                            ((CtaDisplayProperties)cp).parFrm.btnSave.setEnabled(true);
                        }
                        catch(Exception ex1)
                        {
                            cp.domFrm.btnApply.setEnabled(true);
                            cp.domFrm.btnSave.setEnabled(true);
                        }
                    }
                }
             }
        }
    }
}