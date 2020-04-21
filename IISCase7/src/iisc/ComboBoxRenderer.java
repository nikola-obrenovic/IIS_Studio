package iisc;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ComboBoxRenderer extends JLabel implements ListCellRenderer
{

  private ImageIcon logo = new ImageIcon(IISFrameMain.class.getResource("icons/ll.gif"));
  private ImageIcon question2 = new ImageIcon(IISFrameMain.class.getResource("icons/question2.gif"));
  private ImageIcon step = new ImageIcon(IISFrameMain.class.getResource("icons/step.gif"));
  //private ImageIcon declaration = new ImageIcon(IISFrameMain.class.getResource("icons/bl2.gif"));
  private ImageIcon sql = new ImageIcon(IISFrameMain.class.getResource("icons/pk.gif"));
  private ImageIcon next = new ImageIcon(IISFrameMain.class.getResource("icons/next.gif"));
  private ImageIcon type = new ImageIcon(IISFrameMain.class.getResource("icons/type.gif"));
  private ImageIcon constant = new ImageIcon(IISFrameMain.class.getResource("icons/constant.gif"));
  private ImageIcon cursor2 = new ImageIcon(IISFrameMain.class.getResource("icons/cursor2.gif"));
  private ImageIcon variable = new ImageIcon(IISFrameMain.class.getResource("icons/variable.gif"));
  private ImageIcon notes = new ImageIcon(IISFrameMain.class.getResource("icons/notes.gif"));

   public ComboBoxRenderer() {
        setOpaque(true);
        setHorizontalAlignment(LEFT);
        setVerticalAlignment(CENTER);
    }
    
    public Component getListCellRendererComponent(
                                       JList list,
                                       Object value,
                                       int index,
                                       boolean isSelected,
                                       boolean cellHasFocus) {
        //Get the selected index. (The index param isn't
        //always valid, so just use the value.)
        //int selectedIndex = ((Integer)value).intValue();
        if(value != null)
        { 
            JLabel alo = (JLabel)value;
            setText(alo.getText());
            if(alo.getText().equals("Step"))
              setIcon(step);
            else if(alo.getText().equals("Condition"))
              setIcon(question2);
            else if(alo.getText().equals("Begin"))
              setIcon(logo);
            else if(alo.getText().equals("SQL Expression"))
              setIcon(sql);
            else if(alo.getText().equals("Constant"))
              setIcon(constant);                            
            else if(alo.getText().equals("Variable"))
              setIcon(variable);              
            else if(alo.getText().equals("Assignment"))
              setIcon(logo);     
            else if(alo.getText().equals("Type"))
              setIcon(type);                   
            else if(alo.getText().equals("Function Call"))
              setIcon(next);               
            else if(alo.getText().equals("Cursor"))
              setIcon(cursor2);                  
            else if(alo.getText().equals("Note"))
              setIcon(notes);               
            else
              setIcon(logo);
        }

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }    
}