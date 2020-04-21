package iisc;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.JPanel;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.Scrollable;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;

public class PropertyPanel extends JPanel //implements Scrollable
{
  private Property[] properties;
  private int width;
  private int height;
  private static final int controlHeight = 17;
  
  public PropertyPanel(Property[] _properties, int _width)
  {
      super();
      
      if (_properties == null)
      {
          return;
      }
      
      properties = _properties;
      width = _width;
      
      
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
      this.setLayout(null);
      this.setBackground(SystemColor.textInactiveText);
      DrawControls();
    
    }
  private void DrawControls()
  {
      if (width % 2 != 0)
      {
          width = width - 1;
      }
      height = controlHeight * properties.length;
      
      this.setBounds(new Rectangle(0, 0 , width, height));
      this.setPreferredSize(new Dimension(width, height));
      int len = properties.length;
      int controlWidth = width / 2;
      PropertyTextBox name;
      JTextField value;
      
      for(int i = 0; i < len; i++)
      {
          properties[i].setParent(this);
          name = properties[i].getNameTextArea();
          value = properties[i].getValueTextArea();
          
          name.setBounds(new Rectangle(0, i * controlHeight, controlWidth - 1, controlHeight - 1));
          value.setBounds(new Rectangle(controlWidth , i * controlHeight , controlWidth , controlHeight  - 1));
          this.add(name, null);
          this.add(value, null);
      }
  }
  public void setWidth(int _width)
  {
      width = _width;
      this.removeAll();
      DrawControls();
  }
  
  public Property[] getProperties()
  {
      return properties;
  }
}