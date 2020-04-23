package ui;
import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;

import javax.swing.*;
import java.awt.Dimension;
import javax.swing.JTree;
import javax.swing.tree.*;
import javax.swing.BorderFactory;
import java.sql.*;
import java.awt.event.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.JToolBar;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.util.*;
import javax.swing.event.*;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class PTree extends JInternalFrame implements InternalFrameListener,TreeModelListener ,TreeSelectionListener 
{
  private ImageIcon leafIcon = new ImageIcon(PTree.class.getResource("icons/leaf.gif"));
  private ImageIcon imageCut = new ImageIcon(IISFrameMain.class.getResource("icons/cut.gif"));
  private ImageIcon imageClose = new ImageIcon(IISFrameMain.class.getResource("icons/close.gif"));
  private ImageIcon imageCopy = new ImageIcon(IISFrameMain.class.getResource("icons/copy.gif"));
  private ImageIcon imagePaste = new ImageIcon(IISFrameMain.class.getResource("icons/paste.gif"));
  private ImageIcon imageErase = new ImageIcon(IISFrameMain.class.getResource("icons/erase.gif"));
  private ImageIcon imageExpand = new ImageIcon(IISFrameMain.class.getResource("icons/expand.gif"));
  private ImageIcon imageCollapse = new ImageIcon(IISFrameMain.class.getResource("icons/collapse.gif"));
  private ImageIcon imageNew = new ImageIcon(IISFrameMain.class.getResource("icons/new.gif"));
  private ImageIcon imageEdit = new ImageIcon(IISFrameMain.class.getResource("icons/edit.gif"));
  private ImageIcon imageRefresh = new ImageIcon(IISFrameMain.class.getResource("icons/refresh.gif"));
  private ImageIcon imageIIST = new ImageIcon(IISFrameMain.class.getResource("icons/IIST.gif"));
  public Set WindowsManager =new HashSet();
  public JTree tree = new JTree();
  private JScrollPane scroll= new JScrollPane();
  public Connection con;
  private IISFrameMain owner;
 
  public String name;
  public int ID;
  private JToolBar jToolBar1 = new JToolBar();
  private JButton btnCopy = new JButton();
  private JButton btnCut = new JButton();
  private JButton btnPaste = new JButton();
  private JButton btnErase = new JButton();
  private JButton btnCollapse = new JButton();
  private JButton btnExpand = new JButton();
  private JButton btnNew = new JButton();
  private JButton btnEdit = new JButton();
  private JButton btnRefresh = new JButton();
  private JButton btnClose = new JButton();
  private Clipboard clipboard = new Clipboard();
  public PTree()
  {
    try
    {  
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }
  public PTree(Connection connection, IISFrameMain own)
  {
    try
    { owner=own;
      con=connection;
      jbInit();

    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(400, 373));
    this.setFrameIcon(imageIIST);
    this.setResizable(true);
    this.setMaximizable(true);
    this.setIconifiable(true);
    this.setClosable(true);
    this.setTitle("Templates" + " (User:"+ con.getMetaData().getUserName() + ", " +  con.getCatalog() + ")");
        this.addComponentListener(new ComponentAdapter() {
                    public void componentShown(ComponentEvent e) {
                        this_componentShown(e);
                    }
                });
        jToolBar1.setOrientation(1);
    jToolBar1.setFloatable(false);
    btnCopy.setMaximumSize(new Dimension(30, 30));
    btnCopy.setBackground(new Color(212, 208, 200));
    btnCopy.setPreferredSize(new Dimension(25, 22));
    btnCopy.setIcon(imageCopy);
    btnCopy.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnCopy.setRolloverEnabled(true);
    btnCopy.setToolTipText("Copy");
    btnCopy.setMinimumSize(new Dimension(21, 21));
    btnCopy.setFont(new Font("Dialog", 0, 1));
    btnCopy.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
    btnCut.setMaximumSize(new Dimension(30, 30));
    btnCut.setBackground(new Color(212, 208, 200));
    btnCut.setPreferredSize(new Dimension(25, 22));
    btnCut.setIcon(imageCut);
    btnCut.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnCut.setRolloverEnabled(true);
    btnCut.setToolTipText("Cut");
    btnCut.setMinimumSize(new Dimension(21, 21));
    btnCut.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
    btnPaste.setMaximumSize(new Dimension(30, 30));
    btnPaste.setBackground(new Color(212, 208, 200));
    btnPaste.setPreferredSize(new Dimension(25, 22));
    btnPaste.setIcon(imagePaste);
    btnPaste.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnPaste.setRolloverEnabled(true);
    btnPaste.setToolTipText("Paste");
    btnPaste.setMinimumSize(new Dimension(21, 21));
    btnPaste.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
    btnErase.setMaximumSize(new Dimension(30, 30));
    btnErase.setBackground(new Color(212, 208, 200));
    btnErase.setPreferredSize(new Dimension(25, 22));
    btnErase.setIcon(imageErase);
    btnErase.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnErase.setRolloverEnabled(true);
    btnErase.setToolTipText("Delete");
    btnErase.setMinimumSize(new Dimension(21, 21));
    btnErase.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
    btnCollapse.setMaximumSize(new Dimension(30, 30));
    btnCollapse.setBackground(new Color(212, 208, 200));
    btnCollapse.setPreferredSize(new Dimension(25, 22));
    btnCollapse.setIcon(imageCollapse);
    btnCollapse.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnCollapse.setRolloverEnabled(true);
    btnCollapse.setMinimumSize(new Dimension(21, 21));
    btnCollapse.setToolTipText("Collapse All");
    btnCollapse.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCollapse_actionPerformed(e);
        }
      });
    btnExpand.setMaximumSize(new Dimension(30, 30));
    btnExpand.setBackground(new Color(212, 208, 200));
    btnExpand.setPreferredSize(new Dimension(25, 22));
    btnExpand.setIcon(imageExpand);
    btnExpand.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnExpand.setRolloverEnabled(true);
    btnExpand.setMinimumSize(new Dimension(21, 21));
    btnExpand.setToolTipText("Expand All");
    btnExpand.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnExpand_actionPerformed(e);
        }
      });
    btnNew.setMaximumSize(new Dimension(30, 30));
    btnNew.setBackground(new Color(212, 208, 200));
    btnNew.setPreferredSize(new Dimension(25, 22));
    btnNew.setIcon(imageNew);
    btnNew.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnNew.setRolloverEnabled(true);
    btnNew.setToolTipText("New");
    btnNew.setMinimumSize(new Dimension(21, 21));
    btnNew.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
    btnEdit.setMaximumSize(new Dimension(30, 30));
    btnEdit.setBackground(new Color(212, 208, 200));
    btnEdit.setPreferredSize(new Dimension(25, 22));
    btnEdit.setIcon(imageEdit);
    btnEdit.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnEdit.setRolloverEnabled(true);
    btnEdit.setToolTipText("Edit");
    btnEdit.setMinimumSize(new Dimension(21, 21));
    btnEdit.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
    btnRefresh.setMaximumSize(new Dimension(30, 30));
    btnRefresh.setBackground(new Color(212, 208, 200));
    btnRefresh.setPreferredSize(new Dimension(25, 22));
    btnRefresh.setIcon(imageRefresh);
    btnRefresh.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnRefresh.setRolloverEnabled(true);
    btnRefresh.setToolTipText("Refresh");
    btnRefresh.setMinimumSize(new Dimension(21, 21));
    btnRefresh.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnNew1_actionPerformed(e);
        }
      });
    btnClose.setMaximumSize(new Dimension(30, 30));
    btnClose.setBackground(new Color(212, 208, 200));
    btnClose.setPreferredSize(new Dimension(25, 22));
    btnClose.setIcon(imageClose);
    btnClose.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnClose.setRolloverEnabled(true);
    btnClose.setToolTipText("Close Project");
    btnClose.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnClose_actionPerformed(e);
        }
      });
    tree.setBorder(javax.swing.BorderFactory.createLineBorder(Color.white, 10));
    scroll.setAutoscrolls(true);
    pravi_drvo();
    jToolBar1.add(btnClose, null);
    jToolBar1.add(btnRefresh, null);
    jToolBar1.add(btnNew, null);
    jToolBar1.add(btnEdit, null);
    jToolBar1.add(btnCopy, null);
    jToolBar1.add(btnCut, null);
    jToolBar1.add(btnPaste, null);
    jToolBar1.add(btnErase, null);
    jToolBar1.add(btnCollapse, null);
    jToolBar1.add(btnExpand, null);
    this.getContentPane().add(jToolBar1,BorderLayout.WEST);
    this.setBounds(0,0,400,owner.desktop.getHeight());
  }

public void pravi_drvo ()
{
ResultSet rs,rs1,rs2;
JDBCQuery query=new JDBCQuery(con);
JDBCQuery query1=new JDBCQuery(con);
Object[] templates=new Object[1];
try
{rs=query.select("select * from IIST_TEMPLATE_GROUP");
if(rs.next())
ID=rs.getInt(1);
query.Close();
rs=query.select("select count(*) from IIST_TEMPLATE_GROUP");
if(rs.next())
{
int i=rs.getInt(1)+1;
templates=new Object[i];
query.Close();
rs=query.select("select * from IIST_TEMPLATE_GROUP");
templates[0]="Templates";
int j=1;
while(rs.next())
{ 
    int tid=rs.getInt("TG_id");
    rs1=query1.select("select count(*) from IIST_TEMPLATE where TG_id="+ tid);
    int s=0;
    if(rs1.next())
    {
    s=rs1.getInt(1)+1;
    }
    query1.Close();
    Object[] arr=new Object[s];
    arr[0]=rs.getString("TG_name");
    rs1=query1.select("select * from IIST_TEMPLATE where TG_id="+ tid+ " order by T_name");
    int k=1;
    while(rs1.next())
    { 
    Object[] arrpi={"Item Panel","Item Type"};
    Object[] arrp={rs1.getString("T_name"),"Global Options", "Screen Form", "Table Panel", arrpi, "Button Panel"};
    arr[k] =arrp.clone();
    k++;
    }
    query1.Close();
    templates[j]=arr.clone();
    j++;   
}
}
query.Close();

}
catch (SQLException e) {
  JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
  }
DefaultMutableTreeNode root = processHierarchy(templates);
DefaultTreeModel trModel=new DefaultTreeModel(root);
trModel.addTreeModelListener(this);
tree = new JTree(trModel);
tree.addMouseListener(new MouseAdapter() {

public void mouseClicked (MouseEvent me) {

doMouseClicked(me);

}

});
tree.setFont(new Font("SansSerif", 0, 11));
tree.addTreeSelectionListener(this);
tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

tree.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
tree.setRowHeight(20);
tree.setShowsRootHandles(true);
tree.setCellRenderer(new CustomCellRenderer(this));
 
scroll = new JScrollPane(tree);
scroll.setBackground(new Color(212,208,200));
this.getContentPane().removeAll();
this.getContentPane().add(scroll, BorderLayout.CENTER);
this.setVisible(false);
this.getContentPane().add(jToolBar1,BorderLayout.WEST);
tree.setSelectionRow(0);
  this.setVisible(true);
  }
void doMouseClicked (MouseEvent me) {
  if((me.getModifiers() & InputEvent.BUTTON3_MASK)== InputEvent.BUTTON3_MASK)
  {  
      tree.setSelectionPath(tree.getPathForLocation (me.getX(), me.getY()));
      make_popup(me);
  }
  if(me.getClickCount()>1)
  {
  String node=new String();
  if(!tree.isSelectionEmpty())   
    node=tree.getSelectionPath().getLastPathComponent().toString();
  else 
    return;
  String nodep=new String();
  if(!tree.getSelectionPath().isDescendant(tree.getPathForRow(0)) && !tree.isSelectionEmpty())
    nodep=tree.getSelectionPath().getParentPath().getLastPathComponent().toString();
  String nodepp=new String();
  if(!tree.getSelectionPath().toString().equals(tree.getPathForRow(0).toString())) 
  if(!tree.getSelectionPath().getParentPath().isDescendant(tree.getPathForRow(0))&&!tree.isSelectionEmpty())
      nodepp=tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString();
  String nodeppp=new String();
  if(!tree.getSelectionPath().toString().equals(tree.getPathForRow(0).toString())) 
  if(!tree.getSelectionPath().getParentPath().isDescendant(tree.getPathForRow(0))&&!tree.isSelectionEmpty())
  if(!tree.getSelectionPath().getParentPath().getParentPath().isDescendant(tree.getPathForRow(0))&&!tree.isSelectionEmpty())
      nodeppp=tree.getSelectionPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString();
    String nodepppp=new String();
    if(!tree.getSelectionPath().toString().equals(tree.getPathForRow(0).toString())) 
    if(!tree.getSelectionPath().getParentPath().isDescendant(tree.getPathForRow(0))&&!tree.isSelectionEmpty())
    if(!tree.getSelectionPath().getParentPath().getParentPath().isDescendant(tree.getPathForRow(0))&&!tree.isSelectionEmpty())
    if(!tree.getSelectionPath().getParentPath().getParentPath().getParentPath().isDescendant(tree.getPathForRow(0))&&!tree.isSelectionEmpty())
        nodepppp=tree.getSelectionPath().getParentPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString();
        
  int i=-1, g=-1,as=-1;
  ResultSet rs;
  String action;
  JDBCQuery query=new JDBCQuery(con);
  try {
  if(nodepp.equals("Templates") || nodeppp.equals("Templates") || nodepppp.equals("Templates"))
  {
  if(nodepp.equals("Templates"))
    rs=query.select("select T_id, TG_id from IIST_TEMPLATE where T_name='" +tree.getSelectionPath().getLastPathComponent().toString() + "'");
  else  if(nodeppp.equals("Templates"))
    rs=query.select("select T_id, TG_id from IIST_TEMPLATE where T_name='" +tree.getSelectionPath().getParentPath().getLastPathComponent().toString() + "'");
  else 
      rs=query.select("select T_id, TG_id from IIST_TEMPLATE where T_name='" +tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString() + "'");
  if(rs.next())
  {
    i=rs.getInt(1);
    g=rs.getInt(2);
  }
  query.Close();
  UIWizard ui=new UIWizard(owner,"UI Wizard",false, con,i,g,this);
  Settings.Center(ui);
  Iterator it=WindowsManager.iterator();
  while(it.hasNext())
  {
    Object obj=(Object)it.next();
    Class cls=obj.getClass();
    if(cls==ui.getClass())
    { 
      ((UIWizard)obj).dispose();
    }
  }
  WindowsManager.add(ui);
  if(nodepppp.equals("Templates"))
      ui.jTabbedPane1.setSelectedIndex(4);
  else if(!nodepp.equals("Templates")) {
      if(node.equals("Global options")) 
          ui.jTabbedPane1.setSelectedIndex(0); 
      if(node.equals("Screen Form")) 
          ui.jTabbedPane1.setSelectedIndex(1); 
      if(node.equals("Table Panel")) 
          ui.jTabbedPane1.setSelectedIndex(2); 
      if(node.equals("Item Panel")) 
          ui.jTabbedPane1.setSelectedIndex(3); 
      if(node.equals("Button Panel")) 
          ui.jTabbedPane1.setSelectedIndex(5); 
  }
      ui.setVisible(true); 
  }
  }
  catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
  }
  }
}

 private DefaultMutableTreeNode processHierarchy(Object[] hierarchy) {
    DefaultMutableTreeNode node = new DefaultMutableTreeNode(hierarchy[0]);
    DefaultMutableTreeNode child;
    for(int i=1; i<hierarchy.length; i++) {
      Object nodeSpecifier = hierarchy[i];
      if (nodeSpecifier instanceof Object[])  // Ie node with children
        child = processHierarchy((Object[])nodeSpecifier);
      else
        child = new DefaultMutableTreeNode(nodeSpecifier); // Ie Leaf
      node.add(child);
    }
    return(node);
  }
  private void btnCopy_actionPerformed(ActionEvent e)
  {
  }

  private void btnCut_actionPerformed(ActionEvent e)
  {
  }

  private void btnPaste_actionPerformed(ActionEvent e)
  {
  }

  private void btnErase_actionPerformed(ActionEvent e)
  {
  }

  private void btnCollapse_actionPerformed(ActionEvent e)
  {
   if(tree.isSelectionEmpty())
  {   
  for (int i = 0; i < tree.getRowCount(); i++) 
    tree.collapseRow(i);
  }
  else
  collapse((DefaultMutableTreeNode) tree.getLastSelectedPathComponent());
  }
  
  private void collapse(DefaultMutableTreeNode node )
  {
  for(int i=0;i<node.getChildCount();i++)
  {
    collapse((DefaultMutableTreeNode )node.getChildAt(i)); 
  } 
  tree.collapsePath(new TreePath(node.getPath()));
  }
  
  private void btnExpand_actionPerformed(ActionEvent e)
  {
  if(tree.isSelectionEmpty())
  {
  for (int i = 0; i < tree.getRowCount(); i++) 
    tree.expandRow(i);
  }
  else
    expand((DefaultMutableTreeNode) tree.getLastSelectedPathComponent());
  }

  private void expand(DefaultMutableTreeNode node )
  {
  tree.expandPath(new TreePath(node.getPath()));
  for(int i=0;i<node.getChildCount();i++)
  {
  expand((DefaultMutableTreeNode )node.getChildAt(i));  
  } 
  }

  private void btnNew_actionPerformed(ActionEvent e)
  {
  }

  private void btnEdit_actionPerformed(ActionEvent e)
  {
  }


  private void btnClose_actionPerformed(ActionEvent e)
  {
  this.dispose();
  }

  private void btnRemove_actionPerformed(ActionEvent e)
  {
  }
  
  public void internalFrameClosing(InternalFrameEvent e) {
    }
public void internalFrameDeactivated(InternalFrameEvent e) {

    }
public void internalFrameActivated(InternalFrameEvent e) {

    }
public void internalFrameIconified(InternalFrameEvent e) {

    }
    public void internalFrameDeiconified(InternalFrameEvent e) {

    }
     public void internalFrameOpened(InternalFrameEvent e) {

    }
     public void internalFrameClosed(InternalFrameEvent e) {

    }
     public void treeNodesInserted(TreeModelEvent e) {
       // System.out.println("treeNodesInserted");//added
        }
        
        public void treeNodesRemoved(TreeModelEvent e) {
       // System.out.println("treeNodesRemoved");//added
        }
        
        public void treeStructureChanged(TreeModelEvent e) {
       // System.out.println("treeStructureChanged");//added
        }
    public void treeNodesChanged(TreeModelEvent e){
    }
  public void valueChanged (TreeSelectionEvent e) {
      setMenus();
  }
  public void setMenus()
  {
  String node=new String();
  if(!tree.isSelectionEmpty())   
    node=tree.getSelectionPath().getLastPathComponent().toString();
  else 
    return;
  String nodep=new String();
  if(!tree.getSelectionPath().isDescendant(tree.getPathForRow(0)) && !tree.isSelectionEmpty())
    nodep=tree.getSelectionPath().getParentPath().getLastPathComponent().toString();
  String nodepp=new String();
  if(!tree.getSelectionPath().toString().equals(tree.getPathForRow(0).toString())) 
  if(!tree.getSelectionPath().getParentPath().isDescendant(tree.getPathForRow(0))&&!tree.isSelectionEmpty())
      nodepp=tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString();
  if(node.equals("Templates"))
  {
    btnNew.setEnabled(true);
    btnEdit.setEnabled(false);
    btnCopy.setEnabled(false);
    btnCut.setEnabled(false);
    if(clipboard.isEmpty())
      btnPaste.setEnabled(false);
    else
      btnPaste.setEnabled(true);
    btnErase.setEnabled(false);
  }
  else if(nodep.equals("Templates"))
  {
    btnNew.setEnabled(true);
    btnEdit.setEnabled(true);
    btnCopy.setEnabled(false);
    btnCut.setEnabled(false);
    if(clipboard.isEmpty())
      btnPaste.setEnabled(false);
    else
      btnPaste.setEnabled(true);
    btnErase.setEnabled(true);
  }
  else if(nodepp.equals("Templates"))
  {
    btnNew.setEnabled(true);
    btnEdit.setEnabled(true);
    btnCopy.setEnabled(true);
    btnCut.setEnabled(true);
    if(clipboard.isEmpty())
      btnPaste.setEnabled(false);
    else
      btnPaste.setEnabled(true);
    btnErase.setEnabled(true);
  } 
  else
  {
    btnNew.setEnabled(false);
    btnEdit.setEnabled(true);
    btnCopy.setEnabled(false);
    btnCut.setEnabled(false);
    btnPaste.setEnabled(false);
    btnErase.setEnabled(false);
  }
  if(tree.getRowForPath(tree.getSelectionPath().getParentPath())==1) {
      btnNew.setEnabled(false);
      btnEdit.setEnabled(true);
      btnPaste.setEnabled(false);
      btnErase.setEnabled(false);
  }
  if(tree.getRowForPath(tree.getSelectionPath())==1) {
      btnNew.setEnabled(false);
      btnEdit.setEnabled(false);
      btnPaste.setEnabled(false);
      btnErase.setEnabled(false);
  }
  }
  private void popup_actionPerformed(ActionEvent e)
  {
  String node=new String();
  if(!tree.isSelectionEmpty())   
    node=tree.getSelectionPath().getLastPathComponent().toString();
  String nodep=new String();
  if(!tree.getSelectionPath().isDescendant(tree.getPathForRow(0)) && !tree.isSelectionEmpty())
    nodep=tree.getSelectionPath().getParentPath().getLastPathComponent().toString();
  String nodepp=new String();
  if(!tree.getSelectionPath().toString().equals(tree.getPathForRow(0).toString())) 
    if(!tree.getSelectionPath().getParentPath().isDescendant(tree.getPathForRow(0))&&!tree.isSelectionEmpty())
      nodepp=tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString();
  String nodeppp=new String();
  if(!tree.getSelectionPath().toString().equals(tree.getPathForRow(0).toString())) 
  if(!tree.getSelectionPath().getParentPath().isDescendant(tree.getPathForRow(0))&&!tree.isSelectionEmpty())
  if(!tree.getSelectionPath().getParentPath().getParentPath().isDescendant(tree.getPathForRow(0))&&!tree.isSelectionEmpty())
      nodeppp=tree.getSelectionPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString();
  String nodepppp=new String();
  if(!tree.getSelectionPath().toString().equals(tree.getPathForRow(0).toString())) 
  if(!tree.getSelectionPath().getParentPath().isDescendant(tree.getPathForRow(0))&&!tree.isSelectionEmpty())
  if(!tree.getSelectionPath().getParentPath().getParentPath().isDescendant(tree.getPathForRow(0))&&!tree.isSelectionEmpty())
  if(!tree.getSelectionPath().getParentPath().getParentPath().getParentPath().isDescendant(tree.getPathForRow(0))&&!tree.isSelectionEmpty())
      nodepppp=tree.getSelectionPath().getParentPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString();
  int i=-1;
  ResultSet rs;
  String action;
  JDBCQuery query=new JDBCQuery(con);
  if(e.getActionCommand().toString().equals(""))action=((JButton)e.getSource()).getToolTipText();
  else
  action=e.getActionCommand().toString(); 
  try {
  if (node.equals("Templates") || nodep.equals("Templates"))
  {
    if(action.equals("New") && node.equals("Templates"))
      {
      String group =JOptionPane.showInputDialog("Enter the name of template group:");    
      if(group != null)
      {
      rs=query.select("select *  from IIST_TEMPLATE_GROUP where TG_name like '%" + group + "%'");
      if(rs.next())
      {
        JOptionPane.showMessageDialog(null,"Name exists!", "Error", JOptionPane.ERROR_MESSAGE);
        query.Close();
      }
      else
      {
        query.Close();
        int gid=-1;
        rs=query.select("select max(TG_id) from IIST_TEMPLATE_GROUP");
        if(rs.next())
          gid=rs.getInt(1);
          query.Close();
        gid=gid+1;
        query.update("insert into IIST_TEMPLATE_GROUP(TG_id,TG_name) values(" + gid + ",'" + group + "')");
        pravi_drvo();
        select_node(group,"Templates");
      }
      //query.Close();
      }
      }
      else if(action.equals("New")&& nodep.equals("Templates"))
      {
        int tid=-1;
        rs=query.select("select TG_id from IIST_TEMPLATE_GROUP where TG_name like '%" + node + "%'");
        if(rs.next())
            tid=rs.getInt(1);
        query.Close();
        UIWizard ui=new UIWizard(owner,"UI Wizard",false, con,-1,tid,this);
        Settings.Center(ui);
        WindowsManager.add(ui);
        ui.setVisible(true); 
      }
      else if((action.equals("Rename") || action.equals("Edit")) && nodep.equals("Templates"))
      {
        String group =JOptionPane.showInputDialog("Enter new name for template group " + node + ":");
        int tid=-1;
        if(group!=null){
        rs=query.select("select TG_id from IIST_TEMPLATE_GROUP where TG_name like '%" + node + "%'");
        if(rs.next())
          tid=rs.getInt(1);
        query.Close();
        rs=query.select("select *  from IIST_TEMPLATE_GROUP where TG_name like '%" + node + "%' and TG_id<>" + tid);
        if(rs.next())
        {
          query.Close();
          JOptionPane.showMessageDialog(null,"Name exists!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            query.Close();
            query.update("update IIST_TEMPLATE_GROUP set TG_name='" + group + "' where TG_id=" + tid);
            pravi_drvo();
            select_node(group,"Templates");
        }
        }
      }
      else if(action.equals("Delete") && nodep.equals("Templates"))
      {
        if(JOptionPane.showConfirmDialog (null, "<html><center>Do you really want to delete this group and all its contents?","IIS*UIModeler",JOptionPane.YES_NO_OPTION)==0)
          {
            int tid=-1;
            String tname=new String();
            rs=query.select("select TG_id,TG_name from IIST_TEMPLATE_GROUP where TG_name like '%" + node + "%'");
            if(rs.next())
            {
              tid=rs.getInt(1);
              tname=rs.getString(2);
            }
            query.Close();
            query.update("delete from IIST_TEMPLATE where TG_id=" + tid);
            query.update("delete from IIST_TEMPLATE_GROUP where TG_id=" + tid);
            pravi_drvo();
          }
      }
      else if(action.equals("Paste") && nodep.equals("Templates") && !clipboard.isEmpty())
      {
            int tid=-1;
            String tname="";
            rs=query.select("select TG_id, Tg_name from IIST_TEMPLATE_GROUP where TG_name like '%" + node + "%'");
            if(rs.next())
            {
              tid=rs.getInt(1);
              tname=rs.getString(2);
            }
            query.Close();
            Template templ=new Template(clipboard.conn,clipboard.templateID);
            if(templ.group==tid)
              templ.name="Copy of "+ templ.name;
            else
              templ.group=tid;
            if(clipboard.cut==1)
            {
              templ.Delete(clipboard.conn);
            }
            templ.ID=-1;
            templ.Save(con);
            pravi_drvo();
            select_node(templ.name, tname);
            clipboard.clear();
      }
    }
    else if (nodepp.equals("Templates") || nodeppp.equals("Templates") || nodepppp.equals("Templates"))
    {
      if(action.equals("Delete"))
      {
        int tid=-1, tgid=-1;
        rs=query.select("select TG_id from IIST_TEMPLATE_GROUP where TG_name like '%" + nodep + "%'");
        if(rs.next())
            tid=rs.getInt(1);
        query.Close();
        rs=query.select("select T_id, TG_id from IIST_TEMPLATE where T_name like '%" + node + "%' and TG_id =" + tid + "");
        if(rs.next())
          tid=rs.getInt(1);
        query.Close();
        Template tmp= new Template(con, tid);
        tmp.Delete(con);
        pravi_drvo();
        select_node(nodep, "Templates");
      }
      else if(action.equals("Copy") || action.equals("Cut"))
      {
        int tid=-1;
        rs=query.select("select T_id from IIST_TEMPLATE where T_name like '%" + node + "%'");
        if(rs.next())
            tid=rs.getInt(1);
        query.Close();
        clipboard.conn=con;
        clipboard.templateID=tid;
        clipboard.cut=action.equals("Cut")?1:0;
      }
      else if(action.equals("New"))
      {
        int tid=-1;
        rs=query.select("select TG_id from IIST_TEMPLATE_GROUP where TG_name like '%" + nodep + "%'");
        if(rs.next())
            tid=rs.getInt(1);
        query.Close();
        UIWizard ui=new UIWizard(owner,"UI Wizard",false, con,-1,tid, this);
        Settings.Center(ui);
        WindowsManager.add(ui);
        ui.setVisible(true); 
      }
      else if(action.equals("Edit"))
      {
        int tgid=-1;
        if (nodepp.equals("Templates"))
          rs=query.select("select TG_id from IIST_TEMPLATE_GROUP where TG_name like '%" + nodep + "%'");
        else if (nodeppp.equals("Templates"))
          rs=query.select("select TG_id from IIST_TEMPLATE_GROUP where TG_name like '%" + nodepp + "%'");
        else
          rs=query.select("select TG_id from IIST_TEMPLATE_GROUP where TG_name like '%" + nodeppp + "%'");
        if(rs.next())
            tgid=rs.getInt(1);
        query.Close();
        String pr=node;
        int tid=-1;
        if (nodeppp.equals("Templates"))node=nodep;
        if (nodepppp.equals("Templates"))node=nodepp;
        rs=query.select("select T_id from IIST_TEMPLATE where T_name like '%" + node + "%' and TG_id="+tgid);
        if(rs.next())
            tid=rs.getInt(1);
        query.Close();
        UIWizard ui=new UIWizard(owner,"UI Wizard",false, con,tid,tgid, this);
        Settings.Center(ui);
        WindowsManager.add(ui);
        if(nodepppp.equals("Templates"))
            ui.jTabbedPane1.setSelectedIndex(4);
        else if(!nodepp.equals("Templates")) {
            if(pr.equals("Global options")) 
                ui.jTabbedPane1.setSelectedIndex(0); 
            if(pr.equals("Screen Form")) 
                ui.jTabbedPane1.setSelectedIndex(1); 
            if(pr.equals("Table Panel")) 
                ui.jTabbedPane1.setSelectedIndex(2); 
            if(pr.equals("Item Panel")) 
                ui.jTabbedPane1.setSelectedIndex(3); 
            if(pr.equals("Button Panel")) 
                ui.jTabbedPane1.setSelectedIndex(5); 
        }
        ui.setVisible(true); 
      }
      else if(action.equals("Paste") && !clipboard.isEmpty())
      {
            int tid=-1;
            String tname="";
            rs=query.select("select TG_id, Tg_name from IIST_TEMPLATE_GROUP where TG_name like '%" + nodep + "%'");
            if(rs.next())
            {
              tid=rs.getInt(1);
              tname=rs.getString(2);
            }
            query.Close();
            Template templ=new Template(clipboard.conn,clipboard.templateID);
            if(templ.group==tid)
              templ.name="Copy of "+ templ.name;
            else
              templ.group=tid;
            if(clipboard.cut==1)
            {
              templ.Delete(clipboard.conn);
            }
            templ.ID=-1;
            templ.Save(con);
            pravi_drvo();
            select_node(templ.name, tname);
            clipboard.clear();
      }
    }
  }
  catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
  }
  setMenus();
  }
 
  private void btnNew1_actionPerformed(ActionEvent e)
  {
  pravi_drvo();
  }
 
  public void make_popup(MouseEvent me) 
  {
 // if (tree.getSelectionPath().getLastPathComponent().toString().equals("Templates"))
  //  return;
  String node=new String();
  if(!tree.isSelectionEmpty())   
    node=tree.getSelectionPath().getLastPathComponent().toString();
  String nodep=new String();
  if(!tree.getSelectionPath().isDescendant(tree.getPathForRow(0)) && !tree.isSelectionEmpty())
    nodep=tree.getSelectionPath().getParentPath().getLastPathComponent().toString();
  String nodepp=new String();
  if(!tree.getSelectionPath().toString().equals(tree.getPathForRow(0).toString())) 
    if(!tree.getSelectionPath().getParentPath().isDescendant(tree.getPathForRow(0))&&!tree.isSelectionEmpty())
      nodepp=tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString();
  JMenuItem menuItem1 = new JMenuItem("New"); 
  JMenuItem menuItem2 = new JMenuItem("Rename"); 
  JMenuItem menuItem3 = new JMenuItem("Edit"); 
  JMenuItem menuItem4 = new JMenuItem("Delete"); 
  JMenuItem menuItem5 = new JMenuItem("Copy"); 
  JMenuItem menuItem6 = new JMenuItem("Cut"); 
  JMenuItem menuItem7 = new JMenuItem("Paste"); 
  if(tree.getRowForPath(tree.getSelectionPath())==1)
  {
      menuItem2.setEnabled(false);
      menuItem4.setEnabled(false);
      menuItem5.setEnabled(false);
      menuItem1.setEnabled(false);
      menuItem7.setEnabled(false);
  }
  // Constructor the pop-up menu 
  JPopupMenu popupMenu = new JPopupMenu();
  menuItem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
  menuItem2.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
  menuItem3.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
  menuItem4.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
  menuItem5.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
  menuItem6.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
  menuItem7.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });  
  if(tree.getRowForPath(tree.getSelectionPath().getParentPath())==1) {
      menuItem2.setEnabled(false);
      menuItem4.setEnabled(false);
      menuItem1.setEnabled(false);
      menuItem7.setEnabled(false);
  }
  if (nodepp.equals("Templates") || nodep.equals("Templates") || node.equals("Templates"))
    popupMenu.add(menuItem1);
  if (nodep.equals("Templates"))
  { 
    popupMenu.add(menuItem2);
    popupMenu.add(menuItem4);
    popupMenu.add(menuItem7);}
  else if (nodepp.equals("Templates"))
  {
  popupMenu.add(menuItem3);
  popupMenu.add(menuItem4);
  popupMenu.addSeparator();
  popupMenu.add(menuItem5);
  popupMenu.add(menuItem6);
  
  }
  else if(!node.equals("Templates"))
  {
  popupMenu.add(menuItem3);
  }
  popupMenu.setSelected(popupMenu);
  popupMenu.show(tree,me.getX()+10, me.getY());
  }
  
public void select_node (String s, String p, String p1)
{
  int i;
  tree.setVisible(false);
  tree.expandRow(0);
  for (i = 1; i < tree.getRowCount(); i++) {
    tree.expandRow(i);
    if((tree.getPathForRow(i).getLastPathComponent().toString().trim().equals(s.trim()) && tree.getPathForRow(i).getParentPath().getLastPathComponent().toString().trim().equals(p.trim()) && tree.getPathForRow(i).getParentPath().getParentPath().getLastPathComponent().toString().trim().equals(p1.trim())) || (tree.getPathForRow(i).getLastPathComponent().toString().equals(p) && tree.getPathForRow(i).getParentPath().getLastPathComponent().toString().equals(p1)  && s.trim().equals("")))break;
  }
  
  TreePath pt=tree.getPathForRow(i);
  tree.setSelectionPath(pt);
  for (int k = 1; k < tree.getRowCount(); k++) {
    boolean t=false;
    for (int j = 0; j < pt.getPathCount(); j++)
      if(pt.getPathComponent(j).toString().equals(tree.getPathForRow(k).getLastPathComponent().toString()))t=true;
    if(!t)
      tree.collapseRow(k);
  }
  tree.collapseRow(tree.getRowForPath(tree.getSelectionPath()));
  tree.setVisible(true);
}
public void select_node (String s, String p, String p1, String p2)
{
  int i;
  tree.setVisible(false);
  for (i = 0; i < tree.getRowCount(); i++) {
    tree.expandRow(i);
    if((tree.getPathForRow(i).getLastPathComponent().toString().trim().equals(s.trim()) && tree.getPathForRow(i).getParentPath().getLastPathComponent().toString().trim().equals(p.trim()) && tree.getPathForRow(i).getParentPath().getParentPath().getLastPathComponent().toString().trim().equals(p1.trim()) && tree.getPathForRow(i).getParentPath().getParentPath().getParentPath().getLastPathComponent().toString().trim().equals(p2.trim())) || (tree.getPathForRow(i).getLastPathComponent().toString().equals(p) && tree.getPathForRow(i).getParentPath().getLastPathComponent().toString().equals(p1) && tree.getPathForRow(i).getParentPath().getParentPath().getLastPathComponent().toString().equals(p2)  && s.trim().equals("")))break;
  }
  TreePath pt=tree.getPathForRow(i);
  tree.setSelectionPath(pt);
  for (int k = 1; k < tree.getRowCount(); k++) {
    boolean t=false;
    for (int j = 0; j < pt.getPathCount(); j++)
      if(pt.getPathComponent(j).toString().equals(tree.getPathForRow(k).getLastPathComponent().toString()))t=true;
    if(!t)
      tree.collapseRow(k);
  }
  tree.collapseRow(tree.getRowForPath(tree.getSelectionPath()));
  tree.setVisible(true);
}
public void select_node (String s, String p)
{
  int i;
  tree.setVisible(false);
  tree.expandRow(0);
  for (i = 1; i < tree.getRowCount(); i++) {
    tree.expandRow(i);
    if((tree.getPathForRow(i).getLastPathComponent().toString().equals(s) && tree.getPathForRow(i).getParentPath().getLastPathComponent().toString().equals(p)) || (tree.getPathForRow(i).getLastPathComponent().toString().equals(p) && s.trim().equals("")))break;
  }
  TreePath pt=tree.getPathForRow(i);
  tree.setSelectionPath(pt);
  for (int k = 1; k < tree.getRowCount(); k++) {
    boolean t=false;
    for (int j = 0; j < pt.getPathCount(); j++)
      if(pt.getPathComponent(j).toString().equals(tree.getPathForRow(k).getLastPathComponent().toString()))t=true;
    if(!t)
      tree.collapseRow(k);
  }
  tree.collapseRow(tree.getRowForPath(tree.getSelectionPath()));
  tree.setVisible(true);
}

public void insert (String s,String p,String p1)
{
  int i;
  this.setVisible(false);
  pravi_drvo();
  this.setVisible(true);
  select_node(s,p,p1);
}
public void insert (String s,String p,String p1,String p2)
{
  int i;
  this.setVisible(false);
  pravi_drvo();
  this.setVisible(true);
  select_node(s,p,p1,p2);
}
public void insert (String s,String p)
{
  int i;
  this.setVisible(false);
  pravi_drvo();
  this.setVisible(true);
  select_node(s,p);
}

public void removenode (String s,String p)
{
  DefaultMutableTreeNode child =new DefaultMutableTreeNode(s);
  int i;
  for (i = 0; i < tree.getRowCount(); i++) {
    if(tree.getPathForRow(i).getLastPathComponent().toString().equals(s) && tree.getPathForRow(i).getParentPath().getLastPathComponent().toString().equals(p))break;
  }
  TreePath pt=tree.getPathForRow(i);
  if (i<tree.getRowCount())
  {child=(DefaultMutableTreeNode)pt.getLastPathComponent();
  DefaultTreeModel model = (DefaultTreeModel)tree.getModel(); 
  model.removeNodeFromParent(child);}
  }

    private void this_componentShown(ComponentEvent e) {
    }
}
