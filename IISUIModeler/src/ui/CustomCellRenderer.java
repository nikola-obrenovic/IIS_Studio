package ui;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.sql.*;
public class CustomCellRenderer extends		JLabel implements	TreeCellRenderer
{
   private ImageIcon[]	iiscImage ;
	private boolean			bSelected;
  private PTree			ptree;

public CustomCellRenderer(PTree tr)
{
    ptree=tr;
    iiscImage= new ImageIcon[10];	 
    iiscImage[0] = new ImageIcon(IISFrameMain.class.getResource("icons/graph.gif"));	 
    iiscImage[1] = new ImageIcon(IISFrameMain.class.getResource("icons/folder.gif")); 
    iiscImage[2] = new ImageIcon(IISFrameMain.class.getResource("icons/object.gif"));
    iiscImage[3] = new ImageIcon(IISFrameMain.class.getResource("icons/template.gif"));
    iiscImage[4] = new ImageIcon(IISFrameMain.class.getResource("icons/global.gif"));
    iiscImage[5] = new ImageIcon(IISFrameMain.class.getResource("icons/screen_f.gif"));
    iiscImage[6] = new ImageIcon(IISFrameMain.class.getResource("icons/panel.gif"));
    iiscImage[7] = new ImageIcon(IISFrameMain.class.getResource("icons/table.gif"));
    iiscImage[8] = new ImageIcon(IISFrameMain.class.getResource("icons/item_type.gif"));
    iiscImage[9] = new ImageIcon(IISFrameMain.class.getResource("icons/button.gif"));
}
public Component getTreeCellRendererComponent(JTree tree,
                    Object value, boolean bSelected, boolean bExpanded,
                    boolean bLeaf, int iRow, boolean bHasFocus){
		
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
    Object[] Path =  node.getUserObjectPath();
    String labelText = (String)node.getUserObject();
    this.bSelected = bSelected;
    if(!bSelected )
	setForeground( Color.black );
    else
	setForeground( Color.white );
    
    if(Path.length==1)
        setIcon(null);
    else if(Path.length==2)
        setIcon(iiscImage[1]);
    else if(Path.length==3)
        setIcon(iiscImage[3] );
    else if(labelText.equals("Global Options"))
        setIcon(iiscImage[4] );
    else if(labelText.equals("Screen Form"))
        setIcon(iiscImage[5] );
    else if(labelText.equals("Item Panel"))
        setIcon(iiscImage[6] );
    else if(labelText.equals("Table Panel"))
        setIcon(iiscImage[7] );
    else if(labelText.equals("Item Type"))
        setIcon(iiscImage[8] );
    else if(labelText.equals("Button Panel"))
        setIcon(iiscImage[9] );
    else  
        setIcon( iiscImage[2] );
    setText(labelText );
    setFont(new Font("SansSerif",0,11));
    return this;
}
public void paint( Graphics g )
	{
		Color		bColor;
		Icon		currentI = getIcon();
		// Set the correct background color
		bColor = bSelected ? SystemColor.textHighlight : Color.white;
		g.setColor( bColor );
		// Draw a rectangle in the background of the cell
		g.fillRect( 0, 0, getWidth() - 1, getHeight() - 1 );
		super.paint( g );
	}

}
