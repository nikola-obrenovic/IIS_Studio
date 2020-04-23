package ui;

// FontChooser.java
// A font chooser that allows users to pick a font by name, size, style, and
// color.  The color selection is provided by a JColorChooser pane.  This
// dialog builds an AttributeSet suitable for use with JTextPane.
//
import ui.IISFrameMain;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.text.html.HTMLEditorKit;

public class FontChooser extends JDialog implements ActionListener {

  JComboBox fontName;
  JCheckBox fontBold, fontUnderline, fontItalic;
  JComboBox fontSize;
  JTextPane previewLabel;
  SimpleAttributeSet attributes;
  Font newFont;
  boolean newUnderline;

  public FontChooser(IISFrameMain parent, JEditorPane lb) {
    super((Frame)parent, "Font Chooser", true);
    setSize(400, 150);
    attributes = new SimpleAttributeSet();

    // Make sure that any way the user cancels the window does the right thing
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        closeAndCancel();
      }
    });
    this.setResizable(false);
    // Start the long process of setting up our interface
    Container c = getContentPane();
    
    JPanel fontPanel = new JPanel();
    JScrollPane scPanel = new JScrollPane();
    fontName = new JComboBox();
    Font[] fonts=GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
    int j=0;
    for (int i=0;i<fonts.length;i++)
    {fontName.addItem(fonts[i].getFontName());
    if(fonts[i].getName().equals(lb.getFont().getName()))j=i;
    }
    fontName.setSelectedIndex(j);
    fontName.setPreferredSize(new Dimension(130, 23));
    GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
    //fontName.setSelectedIndex(1);
    fontName.addActionListener(this);
    fontSize = new JComboBox();
    for(int i=1; i<100; i++)
        fontSize.addItem(i);
    fontSize.setSelectedIndex(lb.getFont().getSize()-1);
    fontSize.addActionListener(this);
    fontBold = new JCheckBox("Bold");
    fontBold.setSelected(lb.getFont().isBold()?true:false);
    fontBold.addActionListener(this);
    fontUnderline = new JCheckBox("Underline");
    fontUnderline.addActionListener(this);
    fontUnderline.setSelected(lb.getText().contains("<u>"));
    fontItalic = new JCheckBox("Italic");
    fontItalic.addActionListener(this);
    fontItalic.setSelected(lb.getFont().isItalic()?true:false);
    fontPanel.add(fontName);
    fontPanel.add(new JLabel(" Size: "));
    fontPanel.add(fontSize);
    fontPanel.add(fontBold);
    fontPanel.add(fontItalic);
    fontPanel.add(fontUnderline);

    c.add(fontPanel, BorderLayout.NORTH);
    
    // Set up the color chooser panel and attach a change listener so that color
    // updates get reflected in our preview label.
 

    JPanel previewPanel = new JPanel(new BorderLayout());
  
    previewLabel = new JTextPane();
    previewLabel.setAutoscrolls(true);
    scPanel.getViewport().add(previewLabel,BorderLayout.CENTER);
    previewPanel.add(scPanel, BorderLayout.CENTER);

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
    previewPanel.add(controlPanel, BorderLayout.SOUTH);

    // Give the preview label room to grow.
    previewPanel.setMinimumSize(new Dimension(150, 70));
    previewPanel.setPreferredSize(new Dimension(150, 70));

    c.add(previewPanel, BorderLayout.SOUTH);
    previewLabel.setEditorKit(new HTMLEditorKit());
    changeFont();
  }
  // Ok, something in the font changed, so figure that out and make a
  // new font for the preview label
  public void actionPerformed(ActionEvent ae) {
    changeFont();
  }

  public void changeFont(){
      // Check the name of the font
      if (!StyleConstants.getFontFamily(attributes)
                         .equals(fontName.getSelectedItem())) {
        StyleConstants.setFontFamily(attributes, 
                                     (String)fontName.getSelectedItem());
      }
      // Check the font size (no error checking yet)
      if (StyleConstants.getFontSize(attributes) != 
                                     Integer.parseInt(fontSize.getSelectedItem().toString())) {
        StyleConstants.setFontSize(attributes, 
                                   Integer.parseInt(fontSize.getSelectedItem().toString()));
      }
      // Check to see if the font should be bold
      if (StyleConstants.isBold(attributes) != fontBold.isSelected()) {
        StyleConstants.setBold(attributes, fontBold.isSelected());
      }
      // Check to see if the font should be underline
      if (StyleConstants.isUnderline(attributes) != fontUnderline.isSelected()) {
        StyleConstants.setUnderline(attributes, fontUnderline.isSelected());
      }
      // Check to see if the font should be italic
      if (StyleConstants.isItalic(attributes) != fontItalic.isSelected()) {
        StyleConstants.setItalic(attributes, fontItalic.isSelected());
      }
      // and update our preview label
      updatePreviewFont();
  }
  // Get the appropriate font from our attributes object and update
  // the preview label
  protected void updatePreviewFont() {
    String name = StyleConstants.getFontFamily(attributes);
    boolean bold = StyleConstants.isBold(attributes);
    boolean ital = StyleConstants.isItalic(attributes);
    boolean under = StyleConstants.isUnderline(attributes);
    int size = StyleConstants.getFontSize(attributes);

    //Bold and italic don’t work properly in beta 4.
    Font f = new Font(name, (bold ? Font.BOLD : 0) +
                            (ital ? Font.ITALIC : 0), size);
    previewLabel.setFont(f);
    previewLabel.setText("<html>"+(fontUnderline.isSelected()?"<u>":"") +(f.isBold()?"<b>":"")+(f.isItalic()?"<i>":"")+ "<font style=\"font-family:"+f.getFontName() + ";font-size:"+f.getSize()+"pt;\">Here's a sample of this font.</font>" + (f.isItalic()?"</i>":"")+(f.isBold()?"</b>":"")+(fontUnderline.isSelected()?"</u>":"")+"</html>");
  }

  // Get the appropriate color from our chooser and update previewLabel
  protected void updatePreviewColor() {
     // Manually force the label to repaint
    previewLabel.repaint();
  }
  public Font getNewFont() { return newFont; }
  public boolean getNewUnderline() { return newUnderline; }
  public AttributeSet getAttributes() { return attributes; }

  public void closeAndSave() {
    // Save font & color information
    newFont = previewLabel.getFont();
    newUnderline = fontUnderline.isSelected();

    // Close the window
    setVisible(false);
  }

  public void closeAndCancel() {
    // Erase any font information and then close the window
    newFont = null;
    setVisible(false);
  }
}
