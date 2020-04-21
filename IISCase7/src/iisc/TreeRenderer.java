package iisc;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
public class TreeRenderer extends JLabel implements TreeCellRenderer
{
    private ImageIcon[] iiscImage ;
    private boolean bSelected;
    private PTree ptree;

    public TreeRenderer()
    {
        iiscImage= new ImageIcon[3];	 
        iiscImage[0] = new ImageIcon(IISFrameMain.class.getResource("icons/graph.gif"));	 
        iiscImage[1] = new ImageIcon(IISFrameMain.class.getResource("icons/folder.gif")); 
        iiscImage[2] = new ImageIcon(IISFrameMain.class.getResource("icons/template.gif"));
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
        else  
            setIcon( iiscImage[2] );
        setText(labelText );
        setFont(new Font("SansSerif",0,11));
        return this;
    }
    public void paint( Graphics g )
    {
            Color bColor;
            Icon currentI = getIcon();
            bColor = bSelected ? SystemColor.textHighlight : Color.white;
            g.setColor( bColor );
            g.fillRect( 0, 0, getWidth() - 1, getHeight() - 1 );
            super.paint( g );
    }

}
