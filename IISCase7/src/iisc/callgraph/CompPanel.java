package iisc.callgraph;

import java.sql.*;
import java.util.Stack;
import java.util.Vector;
import javax.swing.*;
import iisc.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;


public class CompPanel extends JPanel
{   
    private FormPanel parent;
    private CallingGraph gr;
    public boolean expanded;
    private HeadPanel ePanel;
    private JTree cTree;
    private JScrollPane sp;
    public CrossPanel cp;
    
    public Vector tobVec;
    public Vector attVec;
    
    private JPopupMenu pop;
    private JMenuItem addMi;
    private JMenuItem editMi;
    private JMenuItem deleteMi;
    
    public CompPanel(FormPanel _parent)
    {
        super();  
        parent = _parent;
        gr = parent.parent;
        expanded = false;
        setLayout(null);
        setBackground(SystemColor.lightGray);
        
        //Inicijalizacija panela 
        ePanel = new HeadPanel();
        ePanel.setBounds(0, 0, parent.mWidth, parent.mCTCHeight);
        //ePanel.setBackground(SystemColor.lightGray);
        ePanel.setBorder(new LineBorder(Color.black, 1));
        ePanel.setLayout(null);
        add(ePanel);
        
        //Inicijalizacija dugmeta
        cp = new CrossPanel(this);
        ePanel.add(cp);
        add(ePanel);
        //Inicijalicija drveta 
        InitTree();
        //Inicilalizacija Menija
        InitMenu();
        //Inicijalizija scrolla
        sp = new JScrollPane(cTree);
        sp.setBounds(0, parent.mCTCHeight - 1, parent.mWidth, parent.mCTEHeight + 1);
        sp.setBorder(new LineBorder(Color.black, 1));
        sp.setBackground(Color.WHITE);
        add(sp);
        
        revalidate();
        
    }
    
    public void ReInitTree()
    {
        remove(sp);
        InitTree();
        //Ponovo napravi scroll pane i napravi drvo
        sp = new JScrollPane(cTree);
        sp.setBounds(0, parent.mCTCHeight - 1, parent.mWidth, parent.mCTEHeight + 1);
        sp.setBorder(new LineBorder(Color.black, 1));
        sp.setBackground(Color.WHITE);
        add(sp);
    }
    
    private void EditAction()
    {
        /*
        int index = list.getSelectedIndex();
                
        if (index < 0)
        {
            return;
        }
        
        ParForm pf = new ParForm((ParValue)parVec.get(index),"Edit parameter",parent.parent.parent,conn,PR_id);
        Settings.Center(pf);
        pf.setVisible(true);
        
        if (pf.action == pf.SAVE)
        {
            
            try
            {
                ParValue value = pf.getValue();
                Statement statement = conn.createStatement();
                statement.execute("update IISC_PARAMETER set Dom_id=" + value.Dom_id + ",Par_mnem='" + value.Par_mnem + "',Par_desc='" + value.Par_desc  + "',Par_def_value='" + value.Par_def_value + "' where Par_id =" + value.Par_id);
                listModel.setElementAt(value.Par_mnem, index);
                parVec.setElementAt(value, index);
            }
            catch(SQLException sqle)
            {
            }
            
        }
        pf.dispose();
        */
    }
    
    private void AddAction()
    {
        /*ParValue value = new ParValue(-1,-1,"","","","");
        ParForm dialog = new ParForm(value,"Add Parameter", parent.parent.parent, conn, PR_id);
        Settings.Center(dialog);
        dialog.show();
        
        if (dialog.action == dialog.SAVE)
        {
            try
            {
                int max = 0;
    
                try
                {
                    Statement statement2 = conn.createStatement();
                    ResultSet rs = statement2.executeQuery("select max(Par_id) from IISC_PARAMETER");
                    if (rs.next())
                    {
                        max = rs.getInt(1);
                    }
                    else
                    {
                        max = 0;
                    }
                    rs.close();
                    statement2.close();
                }
                catch(SQLException sqle)
                {
                    max = 0;
                }
                max = max + 1;
                
                value = dialog.getValue();
                Statement statement = conn.createStatement();
                statement.execute("insert into IISC_PARAMETER(Par_id,Dom_id,PR_id,Tf_id,Par_mnem,Par_desc,Par_def_value)  values(" + max + "," + value.Dom_id + "," + PR_id + "," + Tf_id + ",'" + value.Par_mnem + "','" + value.Par_desc + "','" + value.Par_def_value + "')");
                value.Par_id = max;
                listModel.addElement(value.Par_mnem);
                parVec.add(value);
            }
            catch(SQLException sqle)
            {
            }
        }
        dialog.dispose();
        */
    }
    
    private void DeleteAction()
    {
        /*
        int index = list.getSelectedIndex();
        
        if (index < 0)
        {
            return;
        }
        
        int action = JOptionPane.showConfirmDialog(this, "Confirm delete", "Parameter", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

        if (action == JOptionPane.OK_OPTION)
        {
            ParValue value = (ParValue)parVec.get(index);
            try
            {
                Statement statement = conn.createStatement();
                statement.execute("delete from IISC_PARAMETER where Par_id =" + value.Par_id);                            
                parVec.get(index);
                listModel.remove(index);
                
            }
            catch(SQLException sqle)
            {
            }
        }*/
    }
    
    private void InitMenu()
    {
        pop = new JPopupMenu();
        addMi = new JMenuItem("  Add");
        addMi.setIcon(gr.imageNew);
        addMi.setBackground(Color.white);
        editMi = new JMenuItem("  Edit");
        editMi.setIcon(gr.imageEdit);
        editMi.setBackground(Color.white);
        deleteMi = new JMenuItem("  Delete");
        deleteMi.setIcon(gr.imageErase);
        deleteMi.setBackground(Color.white);
        editMi.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                EditAction();
            }
        });
        
        deleteMi.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                DeleteAction();
            }
        });
        
        addMi.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                AddAction();
            }
        });
        
        pop.add(addMi);
        pop.add(editMi);
        pop.add(deleteMi);
    }
    public void InitTree()
    {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("pop", true);
        //root.add(new DefaultMutableTreeNode("miko"));
        
        Stack st = new Stack();
        Stack tobSt = new Stack();
        tobVec = new Vector();
        attVec = new Vector();
        InitAttributes();
        
        try
        {
            ResultSet rs;
            Statement statement = gr.conn.createStatement();
            rs = statement.executeQuery("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tf_id="+ parent.Tf_id + " order by TOB_superord");
            
            while(rs.next())
            {
                tobVec.add(new CompType(rs.getInt("Tob_id"), rs.getString("Tob_mnem"), rs.getInt("Tob_superord")));
            }
            
            root = new DefaultMutableTreeNode(parent.Tf_mnem, true);
            
            
            if (tobVec.size() > 0)
            {
                DefaultMutableTreeNode node;
                node = new DefaultMutableTreeNode(((CompType)tobVec.get(0)).Tob_mnem, true);
                root.add(node);
                st.push(node);
                tobSt.push(tobVec.get(0));
                int i = 0;
                DefaultMutableTreeNode compChildNode;
                DefaultMutableTreeNode childNode;
                DefaultMutableTreeNode attchildNode;
                CompType ct;
                i = 0;
                
                while(!st.isEmpty())
                {
                    node = (DefaultMutableTreeNode)st.pop();
                    ct = (CompType)tobSt.pop();
                    i = 0;
                    attchildNode = new DefaultMutableTreeNode("Attributes", true);
                    compChildNode = new DefaultMutableTreeNode("Component types", true);
                    AddAttributes(attchildNode,ct.Tob_id);
                    
                    if (attchildNode.getChildCount() > 0)
                    {
                        node.add(attchildNode);
                    }
                    
                    for(i = tobVec.size() - 1; i > 0; i--)
                    {
                        
                        if(ct.Tob_id == ((CompType)tobVec.get(i)).Tob_superord)
                        {
                            childNode = new DefaultMutableTreeNode(((CompType)tobVec.get(i)).Tob_mnem, true);
                            st.push(childNode);
                            tobSt.push(tobVec.get(i));
                            compChildNode.add(childNode);
                        }
                    }
                    
                    if(compChildNode.getChildCount() > 0)
                    {
                        node.add(compChildNode);
                    }
                }
            }
            cTree = new JTree(root);
            cTree.setCellRenderer(new CustomCellRenderer());
            
            cTree.addMouseListener(new MouseAdapter()
            {
                public void mouseClicked(MouseEvent e)
                {
                    if (e.getClickCount() > 1)
                    {
                        EditAction();
                    }
                    
                    if((e.getModifiers() & InputEvent.BUTTON3_MASK)== InputEvent.BUTTON3_MASK)
                    {
                        System.out.println("e.getX():" + e.getX() + "e.getY()" + e.getY());
                        try 
                        {
                            try{cTree.setSelectionPath(cTree.getPathForLocation (e.getX(), e.getY()));}catch(Exception exp){}
                            pop.show(cTree, e.getX() + 10, /*parent.y + parent.mHeight + parent.mCTCHeight +*/ e.getY());
                        }
                        catch(Exception ex)
                        {
                            System.out.println("E:x" + ex.toString());
                        }
                    }
                }
            });
        }
        catch(SQLException sqle)
        {
        }
        
        
    }
    
    public void InitAttributes()
    {
        try
        {
            ResultSet rs;
            Statement statement = gr.conn.createStatement();
            rs = statement.executeQuery("select * from IISC_ATT_TOB,IISC_ATTRIBUTE where Tf_id="+ parent.Tf_id + " and IISC_ATT_TOB.Att_id=IISC_ATTRIBUTE.Att_id");
            
            while(rs.next())
            {
                attVec.add(new Attribute(rs.getInt("Att_id"), rs.getString("Att_mnem"), rs.getInt("Tob_id")));
            }
        }
        catch(SQLException sqle)
        {
        }
        
    }
    public int AddAttributes(DefaultMutableTreeNode node, int Tob_id)
    {
        int i;
         
        DefaultMutableTreeNode childNode;
        
        for(i = 0; i < attVec.size(); i++)
        {
            if(Tob_id == ((Attribute)attVec.get(i)).Tob_id)
            {
                childNode = new DefaultMutableTreeNode(((Attribute)attVec.get(i)).Att_mnem, true);
                node.add(childNode);
            }
        }    
           
        return i;
    }
    
    public void ReAdjust()
    {
        ePanel.setBounds(0, 0, parent.mWidth, parent.mCTCHeight);
        this.sp.setBounds(0, parent.mCTCHeight - 1, parent.mWidth, parent.mCTEHeight + 1);
        revalidate();
    }
    public boolean getExpanded()
    {
        return expanded;
    }
    
    private void Expand()
    {
        gr.dirty = true;
        parent.dirty = true;
        expanded = true;
        parent.Readjust();   
    }
    
    private void Collapse()
    { 
        gr.dirty = true;
        parent.dirty = true;
        expanded = false;
        parent.Readjust();
    }
    
    private class Attribute
    {
        public int Att_id;
        public int Tob_id;
        public String Att_mnem;
        
        private Attribute(int _Att_id, String _Att_mnem, int _Tob_id)
        {
            Att_id = _Att_id;
            Tob_id = _Tob_id;
            Att_mnem = _Att_mnem;
        }
    }
    
    private class CompType
    {
        public int Tob_id;
        public String Tob_mnem;
        public int Tob_superord;
        
        private CompType(int _Tob_id, String _Tob_mnem, int _Tob_superord)
        {
           
            Tob_id = _Tob_id;
            Tob_mnem = _Tob_mnem;
            Tob_superord = _Tob_superord;
        }
    }
    
    public class CrossPanel extends JLabel implements MouseListener
    {
        private int state;
        private CompPanel cp;
        private boolean mDown = false;
        private BorderLayout brLayout = new BorderLayout();
        
        private CrossPanel(CompPanel _cp)
        {
            state = -1;    
            cp = _cp;
            setBackground(SystemColor.lightGray);
            
            setBorder(BorderFactory.createEmptyBorder());
            setLayout(brLayout);
            
            setIcon(gr.imageExpand);
            
            addMouseListener(this);
            
            
            setSize(15, 15);
            setBounds(5, 2, 15, 15);
            
        }
        public void SetState(int newState)
        {
            state = newState;
            if (state == 1)
            {
                  setIcon(gr.imageCollapse);
            }
            else
            {
                setIcon(gr.imageExpand);
            }
            this.repaint();
              
        }
        
        public void mousePressed(MouseEvent e)
        {
            state = state * (-1);
            mDown = true;
            setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            cp.parent.dirty = true;
            cp.parent.parent.dirty = true;
              
            if (state == 1)
            {
                cp.Expand();
                setIcon(gr.imageCollapse);
            }
            else
            {
                cp.Collapse();
                setIcon(gr.imageExpand);
            }
            
            this.repaint();
        }
        
        public void mouseReleased(MouseEvent e)
        {
            mDown = false;
            setBorder(BorderFactory.createEmptyBorder());
        }
        
        public void mouseClicked(MouseEvent e)
        {
            
        }
        
        public void mouseEntered(MouseEvent e)
        {
            if (mDown)
            {
                return;
            }
            
            setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        }
        
        public void mouseExited(MouseEvent e)
        {
            setBorder(BorderFactory.createEmptyBorder());
        }
    }
    
    
  
    private class CustomCellRenderer extends	JLabel implements	TreeCellRenderer
    {
        private ImageIcon[]	iiscImage;
        private boolean selected;
        private int iconIndex;
        
        public CustomCellRenderer()
        {
            // Ucitaj ikonice za drvo
            iiscImage= new ImageIcon[5];
            iiscImage[0] = new ImageIcon(IISFrameMain.class.getResource("icons/form.gif"));
            iiscImage[4] = new ImageIcon(IISFrameMain.class.getResource("icons/menu.gif"));
            iiscImage[1] = new ImageIcon(IISFrameMain.class.getResource("icons/folder.gif"));
            iiscImage[2] = new ImageIcon(IISFrameMain.class.getResource("icons/attribute.gif"));
            iiscImage[3] = new ImageIcon(IISFrameMain.class.getResource("icons/object.gif"));	 
        
        }
    
        public Component getTreeCellRendererComponent( JTree tree,
                                              Object value, boolean bSelected, boolean bExpanded,
                                              boolean bLeaf, int iRow, boolean bHasFocus )
        {
            // Find out which node we are rendering and get its text
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
            Object[]	Path =  node.getUserObjectPath();
            String	labelText = (String)node.getUserObject();
             
            setText( labelText );
            setFont(new Font("SansSerif",0,11));
            setForeground(Color.black);
            selected = bSelected;
            
            if( Path.length == 1 )
            {
                if(parent.type == 0)
                {
                    setIcon( iiscImage[0] );
                }
                else
                {
                    setIcon( iiscImage[4] );
                }
                return this;
            }
            
            if( Path.length == 2 )
            {
                setIcon( iiscImage[3] );
                return this;
            }
            
            if(Path[Path.length - 1].toString() == "Component types" || Path[Path.length -1].toString()=="Attributes")
            {
                setIcon( iiscImage[1] );
                return this;
            }
            
            if(Path[Path.length - 2].toString() == "Component types")
            {
                setIcon( iiscImage[3] );
                return this;
            }
            
            if(Path[Path.length - 2].toString()=="Attributes")
            {
                setIcon( iiscImage[2] );
                return this;
            }
            
            return this;
            
        }
        
        public void paint( Graphics g )
        {
            Color		bColor;
            
      
          // Set the correct background color
          if (selected)
          {
              bColor = SystemColor.textHighlight;
              setForeground(Color.white);
          }
          else
          {
              bColor = Color.white;
              setForeground(Color.black);
          }
          g.setColor(bColor);
          g.fillRect( 0, 0, getWidth() - 1, getHeight() - 1 );
      
          super.paint( g );
        }
    
    }
    
    private class HeadPanel extends JPanel
    {
        public HeadPanel()
        {
            super(null);
        }
        public void paint(Graphics g)
        {
        
            Graphics2D g2 = (Graphics2D)g;
        
            super.paint(g2);
            
             // Fill with a gradient.
            GradientPaint gp = new GradientPaint(0,0,Color.white,this.getWidth(), this.getHeight(),  parent.color, false);
            g2.setPaint(gp);
            g2.fillRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);
            
            g2.setColor(Color.black);
            g2.setFont(new Font("SansSerif",0,11));
            g2.drawString("Component Types",22, 14);
            
            super.paintChildren(g2);
        }
    }
}