package iisc;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


class CheckConstraintErrorListRender extends JLabel implements ListCellRenderer {
      //private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);
        private ImageIcon succes = new ImageIcon(IISFrameMain.class.getResource("icons/succes.gif"));
        private ImageIcon error2 = new ImageIcon(IISFrameMain.class.getResource("icons/error.gif"));
      public CheckConstraintErrorListRender() {
        super();
        //setOpaque(true);
        //setIconTextGap(12);
      }

      public Component getListCellRendererComponent(JList list, Object value,
          int index, boolean isSelected, boolean cellHasFocus) {
          
            if(value instanceof JLabel) {
                JLabel entry = (JLabel) value;
                //setText(entry.getTitle());
                //setIcon(entry.getImage());
                entry.setOpaque(false);
                if (isSelected) {
                  //entry.setBackground(Color.BLUE);
                  entry.setForeground(Color.RED);
                } else {
                  //entry.setBackground(Color.white);
                  entry.setForeground(Color.black);
                }
                if(entry.getText().compareTo("Succes") == 0)
                    entry.setIcon(succes);
                else
                    entry.setIcon(error2);
                    
                return entry;
            }                    
                return this;
      }
    }   