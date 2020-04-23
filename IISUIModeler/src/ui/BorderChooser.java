package ui;

// FontChooser.java
// A font chooser that allows users to pick a font by name, size, style, and
// color.  The color selection is provided by a JColorChooser pane.  This
// dialog builds an AttributeSet suitable for use with JTextPane.
//
import ui.IISFrameMain;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class BorderChooser extends JDialog implements ActionListener {

  public JComboBox borderName;
  public JComboBox borderSize;
  JTextArea previewLabel;
  JPanel previewPanel;
  int attributes[]=new int[2];
  Border newBorder;

  public BorderChooser(IISFrameMain parent, JLabel lb) {
    super((Frame)parent, "Border Editor", true);
    setSize(300, 180);
    // Make sure that any way the user cancels the window does the right thing
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        closeAndCancel();
      }
    });
    this.setResizable(true);
    // Start the long process of setting up our interface
    Container c = getContentPane();
    
    JPanel fontPanel = new JPanel();
    borderName = new JComboBox();
    borderName.addItem("None");
    borderName.addItem("Line");
    borderName.addItem("Etched");
    borderName.addItem("Etched Raised");
    borderName.addItem("Etched Lowered");
    borderName.addItem("Bevel Raised");
    borderName.addItem("Bevel Lowered");
    borderName.addItem("Titled");
    borderName.setSelectedIndex(getBorderStyle(lb.getText()));
    borderName.setPreferredSize(new Dimension(130, 23));
    GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
    //borderName.setSelectedIndex(1);
    borderName.addActionListener(this);
    borderSize = new JComboBox();
    for(int i=0; i<=10; i++)
        borderSize.addItem(i);
    borderSize.setSelectedIndex(getBorderWidth(lb.getText()));
    borderSize.addActionListener(this);
    fontPanel.add(borderName);
    fontPanel.add(new JLabel(" Width: "));
    fontPanel.add(borderSize);
    c.add(fontPanel, BorderLayout.NORTH);
    previewPanel = new JPanel(new BorderLayout());
    previewLabel = new JTextArea("This is border preview");
    previewLabel.setAutoscrolls(true);
    previewLabel.setLineWrap(true);
    previewPanel.setLayout(null);
    previewPanel.add(previewLabel);
    previewLabel.setBounds(50,0,200,50);
    previewLabel.setMinimumSize(new Dimension(50, 20));
    previewLabel.setPreferredSize(new Dimension(50, 20));
    previewLabel.setMaximumSize(new Dimension(50, 20));
    // Add in the Ok and Cancel buttons for our dialog box
    JButton okButton = new JButton("OK");
    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        closeAndSave();
      }
    });
    JButton cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        closeAndCancel();
      }
    });

    JPanel controlPanel = new JPanel();
    controlPanel.add(okButton);
    controlPanel.add(cancelButton);
    previewPanel.add(controlPanel);
      controlPanel.setBounds(0,60,300,70);
    // Give the preview label room to grow.
    previewPanel.setMinimumSize(new Dimension(150, 100));
    previewPanel.setPreferredSize(new Dimension(150, 100));
    c.add(previewPanel, BorderLayout.SOUTH);
    changeBorder();
  }
  // Ok, something in the font changed, so figure that out and make a
  // new font for the preview label
  public void actionPerformed(ActionEvent ae) {
    changeBorder();
  }

  public void changeBorder(){
      // Check the name of the font
      attributes[0]=borderName.getSelectedIndex();
      attributes[1]=borderSize.getSelectedIndex();
      updatePreviewFont();
      newBorder=previewLabel.getBorder();
      }
  public int getBorderStyle(String s){
        int k=0;
        if(s.startsWith("None"))k=0;
        else if(s.startsWith("Line"))k=1;
        else if(s.startsWith("Etched Raised"))k=3;
        else if(s.startsWith("Etched Lowered"))k=4;
        else if(s.startsWith("Etched"))k=2;
        else if(s.startsWith("Bevel Raised"))k=5;
        else if(s.startsWith("Bevel Lowered"))k=6;
        else if(s.startsWith("Titled"))k=7;
        return k;
    }
  public int getBorderWidth(String s){
       int k=0;
       if(getBorderStyle(s)<=1)
       {
        String[] m=s.split(",");
        if(m.length>1)
        k=(new Integer(m[1].trim())).intValue();
       }
       return k;
    }   
  protected void updatePreviewFont() {
    if(attributes[0]>1)borderSize.setEnabled(false);
    else borderSize.setEnabled(true);
    Border b=BorderFactory.createBevelBorder(attributes[1]);
    if(attributes[0]==0)
        b=BorderFactory.createEmptyBorder(attributes[1],attributes[1],attributes[1],attributes[1]);
    else if(attributes[0]==1)
        b=BorderFactory.createLineBorder(Color.black, attributes[1]);
    else if(attributes[0]==2)
        b=BorderFactory.createEtchedBorder();
    else if(attributes[0]==3)
        b=BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
    else if(attributes[0]==4)
        b=BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    else if(attributes[0]==5)
        b=BorderFactory.createBevelBorder(BevelBorder.RAISED);
    else if(attributes[0]==6)
        b=BorderFactory.createBevelBorder(BevelBorder.LOWERED);
    else if(attributes[0]==7)
        b=BorderFactory.createTitledBorder("Title");

    previewLabel.setBorder(b);
  }

  // Get the appropriate color from our chooser and update previewLabel
  protected void updatePreviewColor() {
     // Manually force the label to repaint
    previewLabel.repaint();
  }
  public Border getNewBorder() { return newBorder; }
  public void closeAndSave() {
    // Save font & color information
    newBorder = previewLabel.getBorder();

    // Close the window
    setVisible(false);
  }

  public void closeAndCancel() {
    // Erase any font information and then close the window
    newBorder = null;
    setVisible(false);
  }
}

