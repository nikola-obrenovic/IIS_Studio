package iisc;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.sql.*;
public class CustomCellRenderer
		extends		JLabel
		implements	TreeCellRenderer
{
    private ImageIcon[] iiscImage ;
    private boolean bSelected;
    private PTree ptree;

    public CustomCellRenderer(PTree tr)
    {
		// Load the images
        ptree=tr;
        iiscImage= new ImageIcon[43];

        iiscImage[0] = new ImageIcon(IISFrameMain.class.getResource("icons/IISCase.gif"));
        iiscImage[1] = new ImageIcon(IISFrameMain.class.getResource("icons/forms.gif"));
        iiscImage[2] = new ImageIcon(IISFrameMain.class.getResource("icons/form.gif"));
        iiscImage[3] = new ImageIcon(IISFrameMain.class.getResource("icons/apps.gif"));
        iiscImage[4] = new ImageIcon(IISFrameMain.class.getResource("icons/app.gif"));
        iiscImage[5] = new ImageIcon(IISFrameMain.class.getResource("icons/report.gif"));	 
        iiscImage[6] = new ImageIcon(IISFrameMain.class.getResource("icons/scheme.gif"));	 
        iiscImage[7] = new ImageIcon(IISFrameMain.class.getResource("icons/graph.gif"));	 
        iiscImage[8] = new ImageIcon(IISFrameMain.class.getResource("icons/reports.gif"));	
        iiscImage[9] = new ImageIcon(IISFrameMain.class.getResource("icons/reportsf.gif"));	
        iiscImage[10] = new ImageIcon(IISFrameMain.class.getResource("icons/apptypes.gif"));	
        iiscImage[11] = new ImageIcon(IISFrameMain.class.getResource("icons/apptype.gif"));	
        iiscImage[12] = new ImageIcon(IISFrameMain.class.getResource("icons/item.gif"));	
        iiscImage[13] = new ImageIcon(IISFrameMain.class.getResource("icons/folder.gif"));
        iiscImage[14] = new ImageIcon(IISFrameMain.class.getResource("icons/folderf.gif"));
        iiscImage[15] = new ImageIcon(IISFrameMain.class.getResource("icons/schemes.gif"));	 
        iiscImage[16] = new ImageIcon(IISFrameMain.class.getResource("icons/attribute.gif"));	 
        iiscImage[17] = new ImageIcon(IISFrameMain.class.getResource("icons/function.gif"));	 
        iiscImage[18] = new ImageIcon(IISFrameMain.class.getResource("icons/domain.gif"));	 
        iiscImage[19] = new ImageIcon(IISFrameMain.class.getResource("icons/obsolutereport.gif"));	 
        iiscImage[20] = new ImageIcon(IISFrameMain.class.getResource("icons/object.gif"));	 
        iiscImage[21] = new ImageIcon(IISFrameMain.class.getResource("icons/inclusion.gif"));	 
        iiscImage[22] = new ImageIcon(IISFrameMain.class.getResource("icons/formsall.gif"));	 
        iiscImage[23] = new ImageIcon(IISFrameMain.class.getResource("icons/appchild.gif"));	 
        iiscImage[24] = new ImageIcon(IISFrameMain.class.getResource("icons/formref.gif"));	 
        iiscImage[25] = new ImageIcon(IISFrameMain.class.getResource("icons/formnodb.gif"));	 
        iiscImage[26] = new ImageIcon(IISFrameMain.class.getResource("icons/menu.gif"));	
        iiscImage[27] = new ImageIcon(IISFrameMain.class.getResource("icons/primitive.gif"));	
     
	    /*
	     * 
	     * slika JOVO+
	     * 
	     */
        iiscImage[28] = new ImageIcon(IISFrameMain.class.getResource("icons/param.gif"));
        iiscImage[29] = new ImageIcon(IISFrameMain.class.getResource("icons/programunit.gif"));
        iiscImage[30] = new ImageIcon(IISFrameMain.class.getResource("icons/package.gif"));
        iiscImage[31] = new ImageIcon(IISFrameMain.class.getResource("icons/events.gif"));
        iiscImage[32] = new ImageIcon(IISFrameMain.class.getResource("icons/functions2.gif"));
        iiscImage[33] = new ImageIcon(IISFrameMain.class.getResource("icons/appserv.gif"));
        iiscImage[34] = new ImageIcon(IISFrameMain.class.getResource("icons/client.gif"));
        iiscImage[35] = new ImageIcon(IISFrameMain.class.getResource("icons/database.gif"));
        iiscImage[36] = new ImageIcon(IISFrameMain.class.getResource("icons/exception.gif"));
        iiscImage[37] = new ImageIcon(IISFrameMain.class.getResource("icons/keyboard.gif"));
        iiscImage[38] = new ImageIcon(IISFrameMain.class.getResource("icons/mouse.gif"));
        iiscImage[39] = new ImageIcon(IISFrameMain.class.getResource("icons/trigger.gif"));     
	    /*
	     * 
	     * slika JOVO+
	     * 
	     */  
     
      iiscImage[40] = new ImageIcon(IISFrameMain.class.getResource("icons/inclusion.gif"));
      iiscImage[41] = new ImageIcon(IISFrameMain.class.getResource("icons/join.gif"));
      iiscImage[42] = new ImageIcon(IISFrameMain.class.getResource("icons/baic2.gif"));
	}

    private ImageIcon getFormIcon(String s)
    { 
        try
        {
            String str=new String();
            JDBCQuery query=new JDBCQuery(ptree.con);
            ResultSet rs,rs1;
            rs=query.select("select * from IISC_FORM_TYPE  where TF_mnem='"+ s + "' and PR_id="+ ptree.ID );
            if(rs.next())
            {
                int j=rs.getInt("Tf_use");
                query.Close();
                if(j==0)
                    return iiscImage[2];
    
                if(j==1)
                    return iiscImage[25];
    
                if(j==2)
                    return iiscImage[26];
            }
            else
                query.Close();
        } 
        catch(Exception e)
        {
            e.printStackTrace();
        }  
        
        return iiscImage[2];
    }
	
        
public Component getTreeCellRendererComponent( JTree tree,
					Object value, boolean bSelected, boolean bExpanded,
							boolean bLeaf, int iRow, boolean bHasFocus )
    {


		// Find out which node we are rendering and get its text
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        Object[]	Path =  node.getUserObjectPath();
        String	labelText = (String)node.getUserObject();

        this.bSelected = bSelected;
		
        // Set the correct foreground color
        if( !bSelected )
            setForeground( Color.black );
        else
            setForeground( Color.white );

        // Determine the correct icon to display

         /*System.out.println("path len: " + Path.length);
         for(int i=0; i< Path.length; i++)
            System.out.print(Path[i] + " / ");    
        System.out.println("**********");    */
        if( Path.length==1 )
            setIcon( iiscImage[0] );
    //jovo+
        else if(Path.length == 3 && Path[Path.length - 1].toString()=="Program Units")
	    setIcon( iiscImage[29] );
        else if(Path.length == 4 && Path[Path.length - 1].toString()=="Packages")
            setIcon( iiscImage[30] );            
        else if(Path.length == 4 && Path[Path.length - 1].toString()=="Functions")
	    setIcon( iiscImage[32] );
        else if(Path.length == 4 && Path[3].toString()=="Events")
            setIcon( iiscImage[31] );
        else if(Path.length == 5 && Path[Path.length - 1].toString()=="Client")
	    setIcon( iiscImage[34] );
        else if(Path.length == 5 && Path[Path.length - 1].toString()=="Application Server")
            setIcon( iiscImage[33] );
        else if(Path.length == 5 && Path[Path.length - 1].toString()=="DB Server")
            setIcon( iiscImage[35] );
        else if(Path.length == 6 && Path[Path.length - 1].toString()=="Exceptions" && Path[Path.length - 3].toString()=="Events")
            setIcon( iiscImage[36] );
        else if(Path.length == 6 && Path[Path.length - 1].toString()=="Keyboard Events" && Path[Path.length - 3].toString()=="Events")
            setIcon( iiscImage[37] );
        else if(Path.length == 6 && Path[Path.length - 1].toString()=="Mouse Events" && Path[Path.length - 3].toString()=="Events")
            setIcon( iiscImage[38] );            
        else if(Path.length == 6 && Path[Path.length - 1].toString()=="Triggers" && Path[Path.length - 3].toString()=="Events")
            setIcon( iiscImage[39] );            
        else if(Path.length == 6 && Path[Path.length - 3].toString()=="Functions")
            setIcon( iiscImage[28] );       
        else if(Path.length == 6 && Path[Path.length - 2].toString()=="DB Server"  )
            setIcon( iiscImage[35] );    
        else if(Path.length == 6 && Path[Path.length - 2].toString()=="Application Server"  )
            setIcon( iiscImage[33] );  
        else if(Path.length == 6 && Path[Path.length - 2].toString()=="Client"  )
            setIcon( iiscImage[34] );   
        else if(Path.length == 7 && Path[Path.length - 4].toString()=="Events")
            setIcon( iiscImage[17] );              
        else if(Path.length == 7 && Path[Path.length - 4].toString()=="Packages")
            setIcon( iiscImage[17] );              
            
            
            
        /*else if(Path.length == 7 && (Path[Path.length - 1].toString()=="Client" || Path[Path.length - 1].toString()=="DB Server" || Path[Path.length - 1].toString()=="Application Server" ))
	    setIcon( iiscImage[17] );*/





        /*else if(Path.length > 3 && Path[Path.length-3].toString()=="Functions")
	    setIcon( iiscImage[28] );
        else if(Path.length > 2 && Path[Path.length-2].toString()=="Functions")
	    setIcon( iiscImage[17] );
            */
    //jovo+
        else if(Path[Path.length -1].toString()=="Form Types")
            setIcon( iiscImage[22] );
        else if(Path[Path.length -1].toString()=="Owned" || Path[Path.length -1].toString()=="Referenced")
            setIcon( iiscImage[1] );
        else if(Path[Path.length -2].toString()=="Owned" )
            setIcon(getFormIcon(Path[Path.length -1].toString()));
        else if( Path[Path.length -2].toString()=="Referenced")
            setIcon( iiscImage[24] );
        else if(Path[Path.length -2].toString()=="Application Systems")
            setIcon( iiscImage[4] );
        else if(Path[Path.length -2].toString()=="Child Application Systems")
            setIcon( iiscImage[23] );
        else if(Path[Path.length -1].toString()=="Application Systems" || Path[Path.length -1].toString()=="Child Application Systems")
            setIcon( iiscImage[3] );
        else if(Path.length >3 && (Path[Path.length -3].toString()=="DB Schema collision reports" || Path[Path.length -3].toString()=="DB Schema Design reports" || Path[Path.length -3].toString()=="Repository reports" || Path[Path.length -3].toString()=="Reports " || Path[Path.length -3].toString()=="Reports  " || Path[Path.length -3].toString()=="Application generation reports"))
        {
            setIcon( iiscImage[19] );
            try
            { 
                ResultSet rs ;
                JDBCQuery query=new JDBCQuery(ptree.con);
 
                if(Path.length>5 &&  (Path[Path.length -3].toString()=="DB Schema collision reports" || Path[Path.length -3].toString()=="DB Schema Design reports"))
                {
                    rs=query.select("select * from IISC_COLLISION_LOG,IISC_APP_SYSTEM  where (IISC_COLLISION_LOG.CL_type=-AS_collision_detection-1 or ((IISC_COLLISION_LOG.CL_type=10  or IISC_COLLISION_LOG.CL_type=11) and CL_obsolete=0)) and  IISC_COLLISION_LOG.AS_id=IISC_APP_SYSTEM.AS_id and   IISC_APP_SYSTEM.PR_id=" + ptree.ID + "  and AS_name='"+ Path[Path.length -5].toString() + "' order by CL_id desc");
                    if(rs.next())
                    {
                        if(labelText.equals(rs.getString("CL_date")))
                        setIcon( iiscImage[5] );
                    } 
                    query.Close(); 
                }
                else
                {
                    rs=query.select("select * from IISC_COLLISION_LOG   where CL_date='"+ labelText +"' and CL_obsolete=0");
                    if(rs.next())
                    {
                        if(labelText.equals(rs.getString("CL_date")))
                        setIcon( iiscImage[5] );
                    } 
                    query.Close();
                }
            }
            catch(SQLException ae)
            {
                ae.printStackTrace();
            }
        }
        else if(Path[Path.length -2].toString()=="Relation Schemes")
            setIcon( iiscImage[6] );
        else if(Path[Path.length -2].toString()=="Join Dependencies")
            setIcon( iiscImage[41] );
        else if(Path[Path.length -1].toString()=="Relation Schemes")
            setIcon( iiscImage[15] );
        else if(Path[Path.length -1].toString()=="Join Dependencies")
            setIcon( iiscImage[13] );
        else if(Path[Path.length-1].toString()=="Closure Graph")
            setIcon( iiscImage[7] );
        else if(Path.length >3 &&  Path[Path.length-3].toString()=="Reports")
            setIcon( iiscImage[8] );
        else if(Path[Path.length-1].toString()=="Reports" || Path[Path.length-2].toString()=="Reports" || Path[Path.length-1].toString()=="Reports " || Path[Path.length-2].toString()=="Reports " || Path[Path.length-1].toString()=="Reports  " || Path[Path.length-2].toString()=="Reports  ")
            setIcon( iiscImage[9] ); 
        else if(Path[Path.length-1].toString()=="Application Types"  )
            setIcon( iiscImage[10] );
        else if(Path[Path.length-2].toString()=="Application Types" )
            setIcon( iiscImage[11] );
        else if(  Path[Path.length-2].toString()=="Fundamentals"  || Path[Path.length-1].toString()=="Component Types" || Path[Path.length-2].toString()=="Domains" || Path[Path.length-1].toString()=="Business Applications") 
            setIcon( iiscImage[13] );
        else if(  Path[Path.length-1].toString()=="Fundamentals" )
            setIcon( iiscImage[14] );
        else if(Path[Path.length-2].toString()=="Component Types")
            setIcon( iiscImage[20] );
        else if(Path[Path.length-2].toString()=="Attributes")
            setIcon( iiscImage[16] );
        else if(Path[Path.length-2].toString()=="Functions")
            setIcon( iiscImage[17] );
        else if(Path[Path.length-2].toString()=="User defined domains")
            setIcon( iiscImage[18] );
        else if(Path[Path.length-2].toString()=="Primitive domains")
            setIcon( iiscImage[27] );
        else if(Path[Path.length-2].toString()=="Inclusion Dependencies")
            setIcon( iiscImage[21] );
        else if(Path[Path.length -2].toString()== "Business Applications")
            setIcon( iiscImage[42] );
      

        setText( labelText );
        setFont(new Font("SansSerif",0,11));
    

        return this;
    }

	// This is a hack to paint the background.  Normally a JLabel can
	// paint its own background, but due to an apparent bug or
	// limitation in the TreeCellRenderer, the paint method is
	// required to handle this.

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
