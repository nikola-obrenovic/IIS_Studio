package iisc;
import javax.swing.*;
import java.awt.Dimension;
import javax.swing.JTree;
import javax.swing.tree.*;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import java.sql.*;
import java.awt.event.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.JToolBar;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.DefaultButtonModel;
import java.awt.event.ActionListener;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.util.*;
import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

public class PTree extends JInternalFrame implements InternalFrameListener,TreeModelListener ,TreeSelectionListener 
{
private ImageIcon leafIcon = new ImageIcon(PTree.class.getResource("icons/leaf.gif"));
private ImageIcon imageCut = new ImageIcon(IISFrameMain.class.getResource("icons/cut.gif"));
private ImageIcon imageClose = new ImageIcon(IISFrameMain.class.getResource("icons/close.gif"));
private ImageIcon imageCopy = new ImageIcon(IISFrameMain.class.getResource("icons/copy.gif"));
private ImageIcon imagePaste = new ImageIcon(IISFrameMain.class.getResource("icons/paste.gif"));
private ImageIcon imageErase = new ImageIcon(IISFrameMain.class.getResource("icons/erase.gif"));
private ImageIcon imageUpp = new ImageIcon(IISFrameMain.class.getResource("icons/upp.gif"));
private ImageIcon imageUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
private ImageIcon imageDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
private ImageIcon imageDownn = new ImageIcon(IISFrameMain.class.getResource("icons/downn.gif"));
private ImageIcon imageExpand = new ImageIcon(IISFrameMain.class.getResource("icons/expand.gif"));
private ImageIcon imageCollapse = new ImageIcon(IISFrameMain.class.getResource("icons/collapse.gif"));
private ImageIcon imageNew = new ImageIcon(IISFrameMain.class.getResource("icons/new.gif"));
private ImageIcon imageEdit = new ImageIcon(IISFrameMain.class.getResource("icons/edit.gif"));
private ImageIcon imageSave = new ImageIcon(IISFrameMain.class.getResource("icons/save.gif"));
private ImageIcon imageRemove = new ImageIcon(IISFrameMain.class.getResource("icons/remove.gif"));
private ImageIcon imageNewProj = new ImageIcon(IISFrameMain.class.getResource("icons/newproj.gif"));
private ImageIcon imageRefresh = new ImageIcon(IISFrameMain.class.getResource("icons/refresh.gif"));
private ImageIcon imageIISCase = new ImageIcon(IISFrameMain.class.getResource("icons/IISCase.gif"));
private ImageIcon imageDB = new ImageIcon(IISFrameMain.class.getResource("icons/db.gif"));
private ImageIcon imageDBAnalysis = new ImageIcon(IISFrameMain.class.getResource("icons/dbAnalysis.gif"));
private ImageIcon imageXML = new ImageIcon(IISFrameMain.class.getResource("icons/xml.gif"));
private ImageIcon imageXSD = new ImageIcon(IISFrameMain.class.getResource("icons/xsd.gif"));
private ImageIcon imageRef = new ImageIcon(IISFrameMain.class.getResource("icons/ref.gif"));
private ImageIcon imageDeref = new ImageIcon(IISFrameMain.class.getResource("icons/deref.gif"));
public Set WindowsManager =new HashSet();
public JTree tree = new JTree();
private JScrollPane scroll= new JScrollPane();
public Connection con;
private IISFrameMain owner;
 
  public String name;
  public int ID;
  private JToolBar jToolBar1 = new JToolBar();
  private JButton btnClose = new JButton();
  private JButton btnCopy = new JButton();
  private JButton btnCut = new JButton();
  private JButton btnPaste = new JButton();
  private JButton btnErase = new JButton();
  private JButton btnCollapse = new JButton();
  private JButton btnExpand = new JButton();
  private JButton btnDownn = new JButton();
  private JButton btnDown = new JButton();
  private JButton btnUp = new JButton();
  private JButton btnUpp = new JButton();
  private JButton btnNew = new JButton();
  private JButton btnEdit = new JButton();
  private JButton btnRemove = new JButton();
  private JButton btnSave = new JButton();
  private JButton btnNewProj = new JButton();
  private JButton btnRefresh = new JButton();
    private JButton btnXML = new JButton();
  private JButton btnXML1 = new JButton();
    private JButton btnRef = new JButton();
  private JButton btnDereference = new JButton();
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
  public PTree(Connection connection, String n, IISFrameMain own)
  {
    try
    { owner=own;
      con=connection;
      name=n;
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception
  {
        ResultSet rs,rs1;
      JDBCQuery query=new JDBCQuery(con);
    if(name.equals("New"))
    {
      try
      {
      int i=1;
      while(true)
      {
      rs=query.select("select * from IISC_PROJECT where PR_name='New Project "+ i +"'");
      if(!rs.next())break;
      i++;
      query.Close();
      }
      query.Close();
      rs=query.select("select max(PR_id)+1 from IISC_PROJECT ");
      int id=0;
      if(rs.next())
      id=rs.getInt(1);
      query.Close();
      query.update("insert into IISC_PROJECT(PR_id,PR_name) values(" + id + ",'New Project "+ i +"')");
      name="New Project "+ i ;
      }
       catch(SQLException e)
    {
      e.printStackTrace();
    }
  }
  this.getContentPane().setBackground(new Color(212,208,200));
  this.setResizable(true);    
  this.setClosable(true); 
  this.setIconifiable(true); 
  this.setFrameIcon(imageIISCase);
  this.setMaximizable(true);
  this.setTitle(name + " (User:"+ con.getMetaData().getUserName() + ", " +  con.getCatalog() + ")");
  this.setBounds(0,0,owner.desktop.getWidth(),owner.desktop.getHeight());
    this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    this.setFont(new Font("Dialog", 0, 1));
    this.setSize(new Dimension(414, 450));
    this.addFocusListener(new FocusAdapter()
      {
        public void focusGained(FocusEvent e)
        {
          this_focusGained(e);
        }
      });
    this.addInternalFrameListener(new InternalFrameAdapter()
      {
        public void internalFrameClosing(InternalFrameEvent e)
        {
                        this_internalFrameClosing(e);
                    }
      });
    jToolBar1.setFont(new Font("Verdana", 0, 11));
    jToolBar1.setBackground(new Color(212,208,200));
    jToolBar1.setPreferredSize(new Dimension(30, 25));
    jToolBar1.setOrientation(1);
    jToolBar1.setFloatable(false);
    jToolBar1.setMinimumSize(new Dimension(30, 30));
    btnClose.setMaximumSize(new Dimension(30, 30));
    btnClose.setBackground(new Color(212,208,200));
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
    btnCopy.setMaximumSize(new Dimension(30, 30));
    btnCopy.setBackground(new Color(212,208,200));
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
          btnCopy_actionPerformed(e);
        }
      });
    btnCut.setMaximumSize(new Dimension(30, 30));
    btnCut.setBackground(new Color(212,208,200));
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
          btnCut_actionPerformed(e);
        }
      });
    btnPaste.setMaximumSize(new Dimension(30, 30));
    btnPaste.setBackground(new Color(212,208,200));
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
                        btnPaste_actionPerformed(e);
                    }
      });
    btnErase.setMaximumSize(new Dimension(30, 30));
    btnErase.setBackground(new Color(212,208,200));
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
                        btnErase_actionPerformed(e);
                    }
      });
    btnCollapse.setMaximumSize(new Dimension(30, 30));
    btnCollapse.setBackground(new Color(212,208,200));
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
    btnExpand.setBackground(new Color(212,208,200));
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
    btnDownn.setMaximumSize(new Dimension(30, 30));
    btnDownn.setBackground(new Color(212,208,200));
    btnDownn.setPreferredSize(new Dimension(25, 22));
    btnDownn.setIcon(imageDownn);
    btnDownn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnDownn.setRolloverEnabled(true);
    btnDownn.setModel(new DefaultButtonModel());
    btnDownn.setMinimumSize(new Dimension(21, 21));
    btnDownn.setToolTipText("Last");
    btnDownn.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnDownn_actionPerformed(e);
        }
      });
    btnDown.setMaximumSize(new Dimension(30, 30));
    btnDown.setBackground(new Color(212,208,200));
    btnDown.setPreferredSize(new Dimension(25, 22));
    btnDown.setIcon(imageDown);
    btnDown.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnDown.setRolloverEnabled(true);
    btnDown.setModel(new DefaultButtonModel());
    btnDown.setMinimumSize(new Dimension(21, 21));
    btnDown.setToolTipText("Down");
    btnDown.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnDown_actionPerformed(e);
        }
      });
    btnUp.setMaximumSize(new Dimension(30, 30));
    btnUp.setBackground(new Color(212,208,200));
    btnUp.setPreferredSize(new Dimension(25, 22));
    btnUp.setIcon(imageUp);
    btnUp.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnUp.setRolloverEnabled(true);
    btnUp.setModel(new DefaultButtonModel());
    btnUp.setMinimumSize(new Dimension(21, 21));
    btnUp.setToolTipText("Up");
    btnUp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnUp_actionPerformed(e);
        }
      });
    btnUpp.setMaximumSize(new Dimension(30, 30));
    btnUpp.setBackground(new Color(212,208,200));
    btnUpp.setPreferredSize(new Dimension(25, 22));
    btnUpp.setIcon(imageUpp);
    btnUpp.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnUpp.setRolloverEnabled(true);
    btnUpp.setMinimumSize(new Dimension(21, 21));
    btnUpp.setToolTipText("First");
    btnUpp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnUpp_actionPerformed(e);
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
          btnNew_actionPerformed(e);
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
          btnEdit_actionPerformed(e);
        }
      });
    btnRemove.setMaximumSize(new Dimension(30, 30));
    btnRemove.setBackground(new Color(212, 208, 200));
    btnRemove.setPreferredSize(new Dimension(25, 22));
    btnRemove.setIcon(imageRemove);
    btnRemove.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnRemove.setRolloverEnabled(true);
    btnRemove.setToolTipText("Remove Project");
    btnRemove.setMinimumSize(new Dimension(21, 21));
    btnRemove.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRemove_actionPerformed(e);
        }
      });
    btnSave.setMaximumSize(new Dimension(30, 30));
    btnSave.setBackground(new Color(212, 208, 200));
    btnSave.setPreferredSize(new Dimension(25, 22));
    btnSave.setIcon(imageSave);
    btnSave.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnSave.setRolloverEnabled(true);
    btnSave.setToolTipText("Rename Project");
    btnSave.setMinimumSize(new Dimension(21, 21));
    btnSave.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnSave_actionPerformed(e);
        }
      });
    btnNewProj.setMaximumSize(new Dimension(30, 30));
    btnNewProj.setBackground(new Color(212, 208, 200));
    btnNewProj.setPreferredSize(new Dimension(25, 22));
    btnNewProj.setIcon(imageNewProj);
    btnNewProj.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnNewProj.setRolloverEnabled(true);
    btnNewProj.setToolTipText("New Project");
    btnNewProj.setMinimumSize(new Dimension(21, 21));
    btnNewProj.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnNewProj_actionPerformed(e);
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
        btnXML.setMaximumSize(new Dimension(30, 30));
    btnXML.setBackground(new Color(212, 208, 200));
    btnXML.setPreferredSize(new Dimension(25, 22));
    btnXML.setIcon(imageXML);
    btnXML.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnXML.setRolloverEnabled(true);
    btnXML.setToolTipText("XML");
    btnXML.setMinimumSize(new Dimension(21, 21));
    btnXML.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnXML_actionPerformed(e);
        }
      });
    btnXML1.setMaximumSize(new Dimension(30, 30));
    btnXML1.setBackground(new Color(212, 208, 200));
    btnXML1.setPreferredSize(new Dimension(25, 22));
    btnXML1.setIcon(imageXSD);
    btnXML1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnXML1.setRolloverEnabled(true);
    btnXML1.setToolTipText("XML Schema");
    btnXML1.setMinimumSize(new Dimension(21, 21));
    btnXML1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnXML_actionPerformed(e);
        }
      });
        btnRef.setMaximumSize(new Dimension(30, 30));
    btnRef.setBackground(new Color(212, 208, 200));
    btnRef.setPreferredSize(new Dimension(25, 22));
    btnRef.setIcon(imageRef);
    btnRef.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnRef.setRolloverEnabled(true);
    btnRef.setToolTipText("Reference");
    btnRef.setMinimumSize(new Dimension(21, 21));
    btnRef.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
           popup2_actionPerformed(e);
        }
      });
    btnDereference.setMaximumSize(new Dimension(30, 30));
    btnDereference.setBackground(new Color(212, 208, 200));
    btnDereference.setPreferredSize(new Dimension(25, 22));
    btnDereference.setIcon(imageDeref);
    btnDereference.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    btnDereference.setRolloverEnabled(true);
    btnDereference.setToolTipText("Dereference");
    btnDereference.setMinimumSize(new Dimension(21, 21));
    btnDereference.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
           popup2_actionPerformed(e);
        }
      });
  pravi_drvo();

    jToolBar1.add(btnClose, null);
    jToolBar1.add(btnRefresh, null);
    jToolBar1.add(btnNewProj, null);
    jToolBar1.add(btnRemove, null);
    jToolBar1.add(btnSave, null);
    jToolBar1.add(btnNew, null);
    jToolBar1.add(btnEdit, null);
    jToolBar1.add(btnCopy, null);
    jToolBar1.add(btnCut, null);
    jToolBar1.add(btnPaste, null);
    jToolBar1.add(btnErase, null);
    jToolBar1.add(btnRef, null);
    jToolBar1.add(btnDereference, null);
        jToolBar1.add(btnXML, null);
    jToolBar1.add(btnXML1, null);
    jToolBar1.add(btnUpp, null);
    jToolBar1.add(btnUp, null);
    jToolBar1.add(btnDown, null);
    jToolBar1.add(btnDownn, null);
    jToolBar1.add(btnExpand, null);
    jToolBar1.add(btnCollapse, null);
    this.getContentPane().add(jToolBar1,BorderLayout.WEST);
    this.setBounds(0,0,400,owner.desktop.getHeight());
    setMenus();}


public void pravi_drvo ()
{
ResultSet rs,rs1,rs2;
JDBCQuery query=new JDBCQuery(con);
JDBCQuery query1=new JDBCQuery(con);
JDBCQuery query2=new JDBCQuery(con);

Object[] atributes=new Object[1];
Object[] domains=new Object[1];
Object[] ptdomains=new Object[1];
Object[] functions=new Object[1];
Object[] appsystem=new Object[1];
Object[] apptype=new Object[1];
Object[] edit=new Object[1];
Object[] declaration=new Object[1];
Object[] graph=new Object[1];
Object[] dependency=new Object[1];
Object[] collision=new Object[1];
Object[] log=new Object[1];
Object[] repos=new Object[1];
Object[] applog=new Object[1];
Object[] repos1=new Object[1];
Object[] repos2=new Object[1];
      // JOVO+
        Object[] programunits=new Object[4];
      Object[] packages=new Object[4];
      Object[] events=new Object[4];
      Object[] db=new Object[5];
      Object[] as=new Object[5];
      Object[] c=new Object[5];
      Object[] eDb=new Object[3];
      Object[] eas=new Object[4];
      Object[] ec=new Object[4];
      Object[] packarray = new Object[1];
      Object[] emouse=new Object[9];
      Object[] ekeyb=new Object[4];
      Object[] etrigger=new Object[4];
      etrigger[0]= "Triggers";
      etrigger[1]= "Before Insert";
      etrigger[2]= "Before Delete";
      etrigger[3]= "Before Update";
      ekeyb[0] = "Keyboard Events";
      ekeyb[1] = "Key Pressed";
      ekeyb[2] = "Key Released";
      ekeyb[3] = "Key Typed";
      emouse[0] = "Mouse Events";
      emouse[1] = "Clicked";
      emouse[2] = "Dragged";
      emouse[3] = "Entered";
      emouse[4] = "Exited";
      emouse[5] = "Moved";
      emouse[6] = "Pressed";
      emouse[7] = "Released";
      emouse[8] = "Wheel Moved";
      packarray[0]="";
      packages[0]="Packages";
      packages[1]="DB Server";
      packages[2]="Application Server";
      packages[3]="Client";
      eDb[0]="DB Server";
      eDb[1]=etrigger;
      eDb[2]="Exceptions";
      eas[0]="Application Server";
      eas[1]=ekeyb;
      eas[2]=emouse;
      eas[3]="Exceptions";
      ec[0]="Client";
      ec[1]=ekeyb;
      ec[2]=emouse;
      ec[3]="Exceptions";
      events[0]="Events";
      events[1]=eDb;
      events[2]=eas;
      events[3]=ec;
      programunits[0]="Program Units";
      programunits[1]=packages;
      programunits[2]=functions;
      programunits[3]=events;
      // JOVO+ end
Object[] ba = new Object[1];
try
{rs=query.select("select * from IISC_PROJECT where PR_name='"+ name +"'");
if(rs.next())
ID=rs.getInt(1);
query.Close();
atributes[0]="Attributes";
domains[0]="User defined domains";
ptdomains[0]="Primitive domains";
functions[0]="Functions";
appsystem[0]="Application Systems";
apptype[0]="Application Types";
graph[0]="Closure Graph";
edit[0]="Edit";
rs=query.select("select count(*) from IISC_ATTRIBUTE where PR_id=" + ID + "");
if(rs!=null && name!="New")
{
rs.next();
int i=rs.getInt(1)+1;
atributes=new Object[i];
atributes[0]="Attributes";
query.Close();
rs=query.select("select * from IISC_ATTRIBUTE where PR_id=" + ID + " order by Att_mnem ");
for(int j=1; j<i && rs.next(); j++)
{
atributes[j]=rs.getString("Att_mnem");
}
query.Close();
}
else
query1.Close();
rs=query.select("select count(*) from IISC_APP_SYSTEM_TYPE ");
 if(rs!=null  && name!="New")
{
rs.next();
int i=rs.getInt(1)+1;
apptype=new Object[i];
apptype[0]="Application Types";
query.Close();
rs=query.select("select * from IISC_APP_SYSTEM_TYPE order by AS_type");
for(int j=1; j<i && rs.next(); j++)
{
apptype[j]=rs.getString("As_type");
}
query.Close();
}
else
query1.Close();
rs=query.select("select count(*) from IISC_APP_SYSTEM where PR_id=" + ID + "");
 if(rs!=null  && name!="New")
{
rs.next();
int i=rs.getInt(1)+1;
appsystem=new Object[i];
appsystem[0]="Application Systems";
query.Close();
rs=query.select("select * from IISC_APP_SYSTEM,IISC_APP_SYSTEM_TYPE  where PR_id=" + ID + " and IISC_APP_SYSTEM.AS_type_id=IISC_APP_SYSTEM_TYPE.AS_type_id "  + "  and PR_id="+ID+  " order by AS_name");
for(int j=1; j<i && rs.next(); j++)
{
Object[] forms=new Object[1];
forms[0]="Owned";
int f=rs.getInt("AS_id");
rs1=query1.select("select count(*) from IISC_TF_APPSYS where AS_id=" + f + "");
 if(rs1.next())
 {
   int k=rs1.getInt(1)+1;
   query1.Close();
   forms=new Object[k];
   forms[0]="Owned";
   rs1=query1.select("select * from IISC_TF_APPSYS ,IISC_FORM_TYPE  where IISC_FORM_TYPE.PR_id=" + ID + " and IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_TF_APPSYS.AS_id="+ f +" order by TF_mnem");
   for(int d=1; d<k && rs1.next(); d++)
{

Object[] object=new Object[1];
object[0]="Component Types";
int fs=rs1.getInt("TF_id");
rs2=query2.select("select count(*) from IISC_COMPONENT_TYPE_OBJECT_TYPE where TF_id=" + fs + ""  + "  and PR_id="+ID);
 if(rs2.next())
 {
   int h=rs2.getInt(1)+1;
   query2.Close();
   object=new Object[h];
   object[0]="Component Types";
   rs2=query2.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE  where PR_id=" + ID + " and  IISC_COMPONENT_TYPE_OBJECT_TYPE.Tf_id="+ fs +" order by Tob_mnem");
   for(int g=1; g<h && rs2.next(); g++)
{
object[g]= new Object[] {rs2.getString("Tob_mnem")};
}
 }
query2.Close();
forms[d]= new Object[] {rs1.getString("TF_mnem"),object};
}
query1.Close();
 }
Object[] contain=new Object[1];
contain[0]="Child Application Systems";
rs1=query1.select("select count(*) from IISC_APP_SYSTEM_CONTAIN where AS_id=" + f + ""  + "  and PR_id="+ID);
 if(rs1.next())
 {
   int k=rs1.getInt(1)+1;
   query1.Close();
   contain=new Object[k];
   contain[0]="Child Application Systems";
   rs1=query1.select("select * from IISC_APP_SYSTEM_CONTAIN,IISC_APP_SYSTEM  where IISC_APP_SYSTEM.PR_id=" + ID + " and IISC_APP_SYSTEM.AS_id=IISC_APP_SYSTEM_CONTAIN.AS_id_con and IISC_APP_SYSTEM_CONTAIN.AS_id="+ f +" order by AS_name");
   for(int d=1; d<k && rs1.next(); d++)
{
contain[d]= new Object[] {rs1.getString("AS_name")};
}
 }
query1.Close();

Object[] scheme=new Object[1];
scheme[0]="Relation Schemes";
rs1=query1.select("select count(*) from IISC_RELATION_SCHEME where PR_id=" + ID + " and AS_id=" + f + "");
 if(rs1.next())
 {
   int k=rs1.getInt(1)+1;
   query1.Close();
   scheme=new Object[k];
   scheme[0]="Relation Schemes";
   rs1=query1.select("select * from IISC_RELATION_SCHEME where PR_id=" + ID + " and AS_id=" + f + " order by RS_name");
   for(int d=1; d<k && rs1.next(); d++)
{
scheme[d]= new Object[] {rs1.getString("RS_name")};
}
 }
query1.Close();

Object[] join=new Object[1];
join[0]="Join Dependencies";
rs1=query1.select("select count(*) from IISC_JOIN_DEPENDENCY where PR_id=" + ID + " and AS_id=" + f + "");
if(rs1.next())
{
    int k=rs1.getInt(1)+1;
    query1.Close();
    join=new Object[k];
    join[0]="Join Dependencies";
    rs1=query1.select("select * from IISC_JOIN_DEPENDENCY where PR_id=" + ID + " and AS_id=" + f + " order by JD_name");
    for(int d=1; d<k && rs1.next(); d++)
        join[d]= new Object[] {rs1.getString("JD_name")};
}
query1.Close();
/**/
 ba[0]="Business Applications";
 rs1=query1.select("select count(*) from IISC_BUSINESS_APPLICATION where PR_id=" + ID + " and As_id=" + f);
  if(rs1.next())
  {
    int h=rs1.getInt(1)+1;
    query1.Close();
    ba=new Object[h];
    ba[0]="Business Applications";
    rs1=query1.select("select * from IISC_BUSINESS_APPLICATION  where PR_id=" + ID + " and As_id=" + f);
    for(int g=1; g<h && rs1.next(); g++)
 {
 ba[g]= new Object[] {rs1.getString("BA_mnem")};
 }
  }
 query1.Close();

Object[] refer=new Object[1];
refer[0]="Referenced";
rs1=query1.select("select count(*) from IISC_APP_SYS_REFERENCE where AS_id=" + f + "");
 if(rs1.next())
 {
   int k=rs1.getInt(1)+1;
   query1.Close();
   refer=new Object[k];
   refer[0]="Referenced";
   rs1=query1.select("select * from IISC_APP_SYS_REFERENCE,IISC_FORM_TYPE  where IISC_FORM_TYPE.PR_id=" + ID + " and IISC_FORM_TYPE.TF_id=IISC_APP_SYS_REFERENCE.TF_id and IISC_APP_SYS_REFERENCE.AS_id="+ f +" order by TF_mnem");
   for(int d=1; d<k && rs1.next(); d++)
{
refer[d]= new Object[] {rs1.getString("TF_mnem")};
}
 }
query1.Close();
int g=1;
rs1=query1.select("select count(*) from IISC_LOG_TYPE where  CLT_id<10 ");
 if(rs1.next())
 g=g+rs1.getInt(1);
query1.Close();
collision=new Object[g];
rs1=query1.select("select  * from IISC_LOG_TYPE where  CLT_id<10");
int z=1;
while(rs1.next())
{ String tp=rs1.getString("CLT_name"); 
 g=0;
int type=rs1.getInt("CLT_id");
 rs2=query2.select("select count(*) from IISC_COLLISION_LOG where  PR_id=" + ID + " and CL_type="+ type +  " and CL_type<10 and AS_id="+f);
 if(rs2.next())
 g=rs2.getInt(1);
 query2.Close();

 Object[] obj=new Object[1];  
  obj[0]=tp; 
 if(g>0)
 {g++;
  obj=new Object[g];  
  obj[0]=tp;  
 rs2=query2.select("select * from IISC_COLLISION_LOG where  PR_id=" + ID + " and CL_type="+ type + "  and CL_type<10 and AS_id="+f+ " order by CL_date desc");
 g=1;
 while(rs2.next())
 { obj[g]=rs2.getString("CL_date");
 g++;
 }
 query2.Close();

 }
 collision[z]=obj;
 z++; 
 
}
collision[0]="DB Schema collision reports";
g=1;
rs1=query1.select("select count(*) from IISC_LOG_TYPE where CLT_id>=10 and CLT_id<20");
 if(rs1.next())
 g=g+rs1.getInt(1);
query1.Close();
log=new Object[g];
rs1=query1.select("select  * from IISC_LOG_TYPE  where CLT_id>=10   and CLT_id<20  ");
z=1;
while(rs1.next())
{ String tp=rs1.getString("CLT_name"); 
 g=0;
int type=rs1.getInt("CLT_id");
 rs2=query2.select("select count(*) from IISC_COLLISION_LOG where  PR_id=" + ID + " and CL_type="+ type +  "  and AS_id="+f);
 if(rs2.next())
 g=rs2.getInt(1);
 query2.Close();

 Object[] obj=new Object[1];  
  obj[0]=tp; 
 if(g>0)
 {g++;
  obj=new Object[g];  
  obj[0]=tp;  
 rs2=query2.select("select * from IISC_COLLISION_LOG where  PR_id=" + ID + " and CL_type="+ type + "  and AS_id="+f+ " order by CL_date desc");
 g=1;
 while(rs2.next())
 { obj[g]=rs2.getString("CL_date");
 g++;
 }
 query2.Close();

 }
 log[z]=obj;
 z++; 
 
}
log[0]="DB Schema Design reports";


g=1;
rs1=query1.select("select count(*) from IISC_LOG_TYPE where CLT_id>=20 and CLT_id<30");
 if(rs1.next())
 g=g+rs1.getInt(1);
query1.Close();
repos=new Object[g];
rs1=query1.select("select  * from IISC_LOG_TYPE  where CLT_id>=20   and CLT_id<30  ");
z=1;
while(rs1.next())
{ String tp=rs1.getString("CLT_name"); 
 g=0;
int type=rs1.getInt("CLT_id");
 rs2=query2.select("select count(*) from IISC_COLLISION_LOG where  PR_id=" + ID + " and CL_type="+ type +  "  and AS_id="+f);
 if(rs2.next())
 g=rs2.getInt(1);
 query2.Close();

 Object[] obj=new Object[1];  
  obj[0]=tp; 
 if(g>0)
 {g++;
  obj=new Object[g];  
  obj[0]=tp;  
 rs2=query2.select("select * from IISC_COLLISION_LOG where  PR_id=" + ID + " and CL_type="+ type + "  and AS_id="+f+ " order by CL_date desc");
 g=1;
 while(rs2.next())
 { obj[g]=rs2.getString("CL_date");
 g++;
 }
 query2.Close();

 }
 repos[z]=obj;
 z++; 
 
}
repos[0]="Repository reports";

    g=1;
    rs1=query1.select("select count(*) from IISC_LOG_TYPE where CLT_id>=50 and CLT_id<60");
     if(rs1.next())
     g=g+rs1.getInt(1);
    query1.Close();
    applog=new Object[g];
    rs1=query1.select("select  * from IISC_LOG_TYPE  where CLT_id>=50   and CLT_id<60  ");
    z=1;
    while(rs1.next())
    { String tp=rs1.getString("CLT_name"); 
     g=0;
    int type=rs1.getInt("CLT_id");
     rs2=query2.select("select count(*) from IISC_COLLISION_LOG where  PR_id=" + ID + " and CL_type="+ type +  "  and AS_id="+f);
     if(rs2.next())
     g=rs2.getInt(1);
     query2.Close();

     Object[] obj=new Object[1];  
      obj[0]=tp; 
     if(g>0)
     {g++;
      obj=new Object[g];  
      obj[0]=tp;  
     rs2=query2.select("select * from IISC_COLLISION_LOG where  PR_id=" + ID + " and CL_type="+ type + "  and AS_id="+f+ " order by CL_date desc");
     g=1;
     while(rs2.next())
     { obj[g]=rs2.getString("CL_date");
     g++;
     }
     query2.Close();

     }
     applog[z]=obj;
     z++; 
     
    }
    applog[0]="Application generation reports";
Object[] col= new Object[]{"Reports",log, collision};
query1.Close();
boolean can1=false,can2=false,can3=false,can4=false;
rs1=query1.select("select count(*) from IISC_COLLISION_LOG  where  PR_id=" + ID + "  and AS_id="+ f +" and CL_type<10 ");
if(rs1.next())
if(rs1.getInt(1)>0)
can1=true;
query1.Close();
rs1=query1.select("select count(*) from IISC_COLLISION_LOG  where  PR_id=" + ID + "  and AS_id="+ f +" and CL_type>=10  and CL_type<20 ");
if(rs1.next())
if(rs1.getInt(1)>0)
can2=true;
query1.Close();
rs1=query1.select("select count(*) from IISC_COLLISION_LOG  where  PR_id=" + ID + "  and AS_id="+ f +" and CL_type>=20  and CL_type<30 ");
if(rs1.next())
if(rs1.getInt(1)>0)
can3=true;
query1.Close();
rs1=query1.select("select count(*) from IISC_COLLISION_LOG  where  PR_id=" + ID + "  and AS_id="+ f +" and CL_type>=50  and CL_type<60 ");
if(rs1.next())
if(rs1.getInt(1)>0)
can4=true;
query1.Close();


if(!can1 && can2)
col= new Object[]{"Reports",log, repos};
else if(!can2 && can1)
col= new Object[]{"Reports",collision, repos};
else if(can1 && can2)
col= new Object[]{"Reports",log, collision, repos};
else if(can1 && can2 )
col= new Object[]{"Reports",log, collision, repos};
else
col= new Object[]{"Reports",repos};
if(can4) {
    Object[] pom=new Object[col.length+1];
    int k;
    for(k=0;k<col.length;k++)
        pom[k]=col[k];
    pom[k]=applog;
    col=pom;
}
Object[] formes= new Object[]{"Form Types",forms,refer};
boolean can=false;
rs1=query1.select("select count(*) from IISC_COLLISION_LOG  where  PR_id=" + ID + "  and AS_id="+ f +"  ");
if(rs1.next())
if(rs1.getInt(1)>0)
can=true;
query1.Close();
    
    
rs1=query1.select("select * from IISC_GRAPH_CLOSURE  where  PR_id=" + ID + "  and AS_id="+ f +"  ");
if(rs1.next())
{
if(can)
appsystem[j]= new Object[] {rs.getString("As_name"),contain,formes,scheme,join,graph, ba,col};
else
appsystem[j]= new Object[] {rs.getString("As_name"),contain,formes,scheme,join,graph, ba,col};
}
else
{if(can)
appsystem[j]= new Object[] {rs.getString("As_name"),contain,formes,scheme,join,ba,col};
else
appsystem[j]= new Object[] {rs.getString("As_name"),contain,formes,scheme,join,ba,col};
}
query1.Close();
}
query.Close();
}

rs=query.select("select count(*) from IISC_DOMAIN where PR_id=" + ID + "");
 if(rs!=null  && name!="New")
{
rs.next();
int i=rs.getInt(1)+1;
domains=new Object[i];
domains[0]="User defined domains";
query.Close();
rs=query.select("select * from IISC_DOMAIN  where PR_id=" + ID + " order by Dom_mnem");
for(int j=1; j<i && rs.next(); j++)
{
domains[j]=rs.getString("Dom_mnem");
}
query.Close();
}
else
query.Close();

rs=query.select("select count(*) from IISC_PRIMITIVE_TYPE");
 if(rs!=null  && name!="New")
{
rs.next();
int i=rs.getInt(1)+1;
ptdomains=new Object[i];
ptdomains[0]="Primitive domains";
query.Close();
rs=query.select("select * from IISC_PRIMITIVE_TYPE order by PT_mnemonic");
for(int j=1; j<i && rs.next(); j++)
{
ptdomains[j]=rs.getString("PT_mnemonic");
}
query.Close();
}
else
query.Close();






    /*
     * 
     * 
     *                paketa db JOVO+
     *                start
     * 
     * 
     */
        rs=query.select("select count(*) from IISC_PACKAGE where PR_id=" + ID + " and Pack_type = 1");
        if(rs!=null  && name!="New")
        {
            rs.next();
            int i=rs.getInt(1)+1;
            db=new Object[i];
            db[0]="DB Server";
            query.Close();
            rs=query.select("select * from IISC_PACKAGE  where PR_id=" + ID + " and Pack_type = 1 order by Pack_name");
            
            for(int j=1; j<i && rs.next(); j++)
            {
                    String idp = rs.getString("Pack_id");
                   
                    rs1=query2.select("select count(*) from IISC_PACKAGE,IISC_PACK_FUN where IISC_PACK_FUN.Pack_id = "+idp+ " and IISC_PACKAGE.Pack_id = IISC_PACK_FUN.Pack_id");                
                    if (rs1.next())
                    {
                        packarray = new Object[rs1.getInt(1)+1];
                        packarray[0] = rs.getString("Pack_name");
                        query2.Close();
                        rs1=query2.select("select * from IISC_PACK_FUN,IISC_FUNCTION where Pack_id = "+idp+ " and IISC_PACK_FUN.Fun_id = IISC_FUNCTION.Fun_id");
                        //System.out.println("select * from IISC_PACK_FUN,IISC_FUNCTION where Pack_id = "+idp+ " and IISC_PACK_FUN.Fun_id = IISC_FUNCTION.Fun_id");
                        int tmpi=1;
                        while(rs1.next())
                        {
                            packarray[tmpi++]=rs1.getString("Fun_name");
                        }
                        query2.Close();
                    }
                    
                    db[j] = packarray;
            }
            packages[1]=db;
        }
        
        query.Close();
        rs=query.select("select count(*) from IISC_PACKAGE where PR_id=" + ID + " and Pack_type = 2");
        if(rs!=null  && name!="New")
        {
            rs.next();
            int i=rs.getInt(1)+1;
            as=new Object[i];
            as[0]="Application Server";
            query.Close();
            rs=query.select("select * from IISC_PACKAGE  where PR_id=" + ID + " and Pack_type = 2 order by Pack_name");
            
            for(int j=1; j<i && rs.next(); j++)
            {
                    String idp = rs.getString("Pack_id");
                   
                    rs1=query2.select("select count(*) from IISC_PACKAGE,IISC_PACK_FUN where IISC_PACK_FUN.Pack_id = "+idp+ " and IISC_PACKAGE.Pack_id = IISC_PACK_FUN.Pack_id");                
                    if (rs1.next())
                    {
                        packarray = new Object[rs1.getInt(1)+1];
                        packarray[0] = rs.getString("Pack_name");
                        query2.Close();
                        rs1=query2.select("select * from IISC_PACK_FUN,IISC_FUNCTION where Pack_id = "+idp+ " and IISC_PACK_FUN.Fun_id = IISC_FUNCTION.Fun_id");
                        
                        int tmpi=1;
                        while(rs1.next())
                        {
                            packarray[tmpi++]=rs1.getString("Fun_name");
                        }
                        query2.Close();
                    }
                    
                    as[j] = packarray;                
            }
            packages[2]=as;
        }
        
        query.Close();
        
        rs=query.select("select count(*) from IISC_PACKAGE where PR_id=" + ID + " and Pack_type = 3");
        if(rs!=null  && name!="New")
        {
            rs.next();
            int i=rs.getInt(1)+1;
            c=new Object[i];
            c[0]="Client";
            query.Close();
            rs=query.select("select * from IISC_PACKAGE  where PR_id=" + ID + " and Pack_type = 3 order by Pack_name");
            
            for(int j=1; j<i && rs.next(); j++)
            {
                    String idp = rs.getString("Pack_id");
                   
                    rs1=query2.select("select count(*) from IISC_PACKAGE,IISC_PACK_FUN where IISC_PACK_FUN.Pack_id = "+idp+ " and IISC_PACKAGE.Pack_id = IISC_PACK_FUN.Pack_id");                
                    if (rs1.next())
                    {
                        packarray = new Object[rs1.getInt(1)+1];
                        packarray[0] = rs.getString("Pack_name");
                        query2.Close();
                        rs1=query2.select("select * from IISC_PACK_FUN,IISC_FUNCTION where Pack_id = "+idp+ " and IISC_PACK_FUN.Fun_id = IISC_FUNCTION.Fun_id");
                        
                        int tmpi=1;
                        while(rs1.next())
                        {
                            packarray[tmpi++]=rs1.getString("Fun_name");
                        }
                        query2.Close();
                    }
                    
                    c[j] = packarray;                  
            }
            packages[3]=c;
        }
        
        query.Close();    
    /*
     * 
     * 
     *                paketa db JOVO+
     *                end
     * 
     * 
     */

    /*
     * 
     * 
     *                FUNKCIJE JOVO+
     *                start
     * 
     * 
     */
        rs=query.select("select count(*) from IISC_FUNCTION where PR_id=" + ID + "");
        if(rs!=null  && name!="New")
        {
            rs.next();
            int i=rs.getInt(1)+1;
            functions=new Object[i];
            functions[0]="Functions";
            query.Close();
            rs=query.select("select * from IISC_FUNCTION  where PR_id=" + ID + " order by Fun_name");
            
            for(int j=1; j<i && rs.next(); j++)
            {
                String Fun_id = rs.getString("Fun_id");
                rs1=query.select("select count(*) from IISC_FUN_PARAM where Fun_id = "+Fun_id+" ");
                
                    rs1.next();
                    int ii=rs1.getInt(1)+1;
                    Object params[] = new Object[ii];
                    params[0]=rs.getString("Fun_name");
                    //System.out.println("select * from IISC_FUN_PARAM where PR_id=" + ID + " AND Fun_id = "+Fun_id+" order by Param_seq ");
                    //System.out.println("select F.*,D.Dom_mnem from IISC_FUN_PARAM F,IISC_DOMAIN D where F.Dom_id = D.Dom_id AND F.PR_id = D.PR_id AND F.PR_id= " + ID + " AND F.Fun_id = "+Fun_id+" order by F.Param_seq");
                   // rs1=query2.select("select * from IISC_FUN_PARAM where PR_id=" + ID + " AND Fun_id = "+Fun_id+" order by Param_seq ");
                   rs1=query2.select("select F.*,D.Dom_mnem from IISC_FUN_PARAM F,IISC_DOMAIN D where F.Dom_id = D.Dom_id AND F.Fun_id = "+Fun_id+" order by F.Param_seq");
                    
                    params[0] = params[0]+" ( ";
                    int itemp = 1;
                    
                    String inoutarray[] = new String[3];
                    inoutarray[0] = "In";
                    inoutarray[1] = "Out";
                    inoutarray[2] = "In/Out";
                    
                    for(int j1=1; j1<ii && rs1.next(); j1++)
                    {
                        String domtemp= rs1.getString("Dom_mnem");
                        params[j1] = "( " + domtemp + " )   " + rs1.getString("Param_name") + "   [ " + inoutarray[rs1.getInt("inout")] + " ]";
                        params[0] = (String)params[0] + domtemp + " , ";  
                        itemp = 2;
                    }
                    params[0] = ((String)params[0]).substring(0,((String)params[0]).length()-itemp) + " )";
                    //params[0] = params[0]+")";
                    functions[j] = params;
                    query2.Close();
                    //functions[j] =new Object[ii+1];
                    //functions[j][0]=rs.getString("Fun_name");
                    //{ rs.getString("Fun_name"), params };
            }
            programunits[2]=functions;
        }
        
        query.Close();

    /*
     * 
     * 
     *                FUNKCIJE JOVO+
     *                end
     * 
     * 
     */


// stari kod za funkcije
/*
rs=query.select("select count(*) from IISC_FUNCTION where PR_id=" + ID + "");
 if(rs!=null  && name!="New")
{
rs.next();
int i=rs.getInt(1)+1;
functions=new Object[i];
functions[0]="Functions";
query.Close();
rs=query.select("select * from IISC_FUNCTION  where PR_id=" + ID + " order by Fun_name");
for(int j=1; j<i && rs.next(); j++)
{
functions[j]=rs.getString("Fun_name");
}
query.Close();
}
else
query.Close();
*/

















rs=query.select("select count(*) from IISC_INCLUSION_DEPENDENCY where PR_id=" + ID + "");
 if(rs!=null  && name!="New")
{
rs.next();
int i=rs.getInt(1)+1;
dependency=new Object[i];
dependency[0]="Inclusion Dependencies";
query.Close();
rs=query.select("select * from IISC_INCLUSION_DEPENDENCY  where PR_id=" + ID + " order by ID_name");
for(int j=1; j<i && rs.next(); j++)
{
dependency[j]=rs.getString("ID_name");
}
query.Close();
}
else
query.Close();

int g=1;
rs1=query1.select("select count(*) from IISC_LOG_TYPE where CLT_id>=30 and CLT_id<40");
 if(rs1.next())
 g=g+rs1.getInt(1);
query1.Close();
repos1=new Object[g];
rs1=query1.select("select  * from IISC_LOG_TYPE  where CLT_id>=30   and CLT_id<40  ");
int z=1;
while(rs1.next())
{ String tp=rs1.getString("CLT_name"); 
 g=0;
int type=rs1.getInt("CLT_id");
 rs2=query2.select("select count(*) from IISC_COLLISION_LOG where  PR_id=" + ID + " and CL_type="+ type +  "  and AS_id=-1 ");
 if(rs2.next())
 g=rs2.getInt(1);
 query2.Close();

 Object[] obj=new Object[1];  
  obj[0]=tp; 
 if(g>0)
 {g++;
  obj=new Object[g];  
  obj[0]=tp;  
 rs2=query2.select("select * from IISC_COLLISION_LOG where  PR_id=" + ID + " and CL_type="+ type + "  and AS_id=-1 order by CL_date desc");
 g=1;
 while(rs2.next())
 { obj[g]=rs2.getString("CL_date");
 g++;
 }
 query2.Close();

 }
 repos1[z]=obj;
 z++; 
 
}
repos1[0]="Reports ";

g=1;
rs1=query1.select("select count(*) from IISC_LOG_TYPE where CLT_id>=40 and CLT_id<50");
 if(rs1.next())
 g=g+rs1.getInt(1);
query1.Close();
repos2=new Object[g];
rs1=query1.select("select  * from IISC_LOG_TYPE  where CLT_id>=40   and CLT_id<50  ");
z=1;
while(rs1.next())
{ String tp=rs1.getString("CLT_name"); 
 g=0;
int type=rs1.getInt("CLT_id");
 rs2=query2.select("select count(*) from IISC_COLLISION_LOG where  PR_id=" + ID + " and CL_type="+ type +  "  and AS_id=-1");
 if(rs2.next())
 g=rs2.getInt(1);
 query2.Close();

 Object[] obj=new Object[1];  
  obj[0]=tp; 
 if(g>0)
 {g++;
  obj=new Object[g];  
  obj[0]=tp;  
 rs2=query2.select("select * from IISC_COLLISION_LOG where  PR_id=" + ID + " and CL_type="+ type + "  and AS_id=-1 order by CL_date desc");
 g=1;
 while(rs2.next())
 { obj[g]=rs2.getString("CL_date");
 g++;
 }
 query2.Close();

 }
 repos2[z]=obj;
 z++; 
 
}
repos2[0]="Reports  ";


}

catch (SQLException e) {
  JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
  }
Object[] hierarchy =
      { name,
        apptype, 
        appsystem,
        new Object[] { "Fundamentals",
        atributes,
        new Object[] { "Domains",domains,ptdomains},
        programunits,//jovo+
        dependency,
        repos1
        },
        repos2
        };
        
DefaultMutableTreeNode root = processHierarchy(hierarchy);
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
this.setVisible(true);
this.getContentPane().add(jToolBar1,BorderLayout.WEST);
tree.setSelectionRow(0);
  this.setVisible(true);
  }
  
  public void valueChanged (TreeSelectionEvent e) {

setMenus();

}
  public void treeNodesChanged(TreeModelEvent e){
  if(tree.getRowForPath(tree.getSelectionPath())==0)
  {
    try
    {
    ResultSet rs,rs1;
    JDBCQuery query=new JDBCQuery(con);
    boolean can=true;
    if(tree.getLastSelectedPathComponent().toString().trim().equals(""))
     {JOptionPane.showMessageDialog(null, "Bad name!", "Error", JOptionPane.ERROR_MESSAGE);
    can=false;
     }
    else
    {
     rs1=query.select("Select * from IISC_PROJECT where PR_name='"+  tree.getLastSelectedPathComponent().toString().trim() +"' and PR_id<>" + ID);
    if(!rs1.next())
    {
    query.Close();
    query.update("update IISC_PROJECT set PR_name='"+  tree.getLastSelectedPathComponent().toString().trim() +"' where PR_id=" + ID);
    name=  tree.getLastSelectedPathComponent().toString();
    setTitle(  name + " (" + con.getCatalog() + ")");
    }
    else
    {
    JOptionPane.showMessageDialog(null, "Name exists!", "Error", JOptionPane.ERROR_MESSAGE);
    query.Close();  
    can=false;
    }
    }
    if(!can)
    {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
    node.setUserObject("name");
    this.setVisible(false);
    tree.repaint();
    this.setVisible(true);
    }
    }
    catch(SQLException ex)
    {
      ex.printStackTrace();
    }
  }
  pravi_drvo();
  tree.setEditable(false);
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


public void select_node (String s, String p, String p1)
{
int i;
//System.out.println(s+ " " + p + " " + p1);
tree.setVisible(false);
  tree.expandRow(0);
for (i = 1; i < tree.getRowCount(); i++) {
  tree.expandRow(i);
  if((tree.getPathForRow(i).getLastPathComponent().toString().trim().equals(s.trim()) && tree.getPathForRow(i).getParentPath().getLastPathComponent().toString().trim().equals(p.trim()) && tree.getPathForRow(i).getParentPath().getParentPath().getLastPathComponent().toString().trim().equals(p1.trim())) || (tree.getPathForRow(i).getLastPathComponent().toString().equals(p) && tree.getPathForRow(i).getParentPath().getLastPathComponent().toString().equals(p1)  && s.trim().equals("")))break;
//System.out.println(tree.getPathForRow(i).toString());
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
//System.out.println(s+ " " + p + " " + p1);
tree.setVisible(false);
for (i = 0; i < tree.getRowCount(); i++) {
  tree.expandRow(i);
  if((tree.getPathForRow(i).getLastPathComponent().toString().trim().equals(s.trim()) && tree.getPathForRow(i).getParentPath().getLastPathComponent().toString().trim().equals(p.trim()) && tree.getPathForRow(i).getParentPath().getParentPath().getLastPathComponent().toString().trim().equals(p1.trim()) && tree.getPathForRow(i).getParentPath().getParentPath().getParentPath().getLastPathComponent().toString().trim().equals(p2.trim())) || (tree.getPathForRow(i).getLastPathComponent().toString().equals(p) && tree.getPathForRow(i).getParentPath().getLastPathComponent().toString().equals(p1) && tree.getPathForRow(i).getParentPath().getParentPath().getLastPathComponent().toString().equals(p2)  && s.trim().equals("")))break;
//System.out.println(tree.getPathForRow(i).toString());
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

public void change (String s, String p,String s1,String p1)
{

int i;
this.setVisible(false);
  pravi_drvo();
  this.setVisible(true);
select_node(s1,p,p1);
/*
for (i = 0; i < tree.getRowCount(); i++) {
  if(tree.getPathForRow(i).getLastPathComponent().toString().equals(s) && tree.getPathForRow(i).getParentPath().getLastPathComponent().toString().equals(p)&& tree.getPathForRow(i).getParentPath().getParentPath().getLastPathComponent().toString().equals(p1))break;
  }
TreePath pt=tree.getPathForRow(i);
DefaultTreeModel model = (DefaultTreeModel)tree.getModel(); 
model.valueForPathChanged(pt,s1);*/
}
public void change (String s, String p,String p2,String s1,String p1)
{

int i;
this.setVisible(false);
  pravi_drvo();
  this.setVisible(true);
select_node(s1,p,p2,p1);
/*
for (i = 0; i < tree.getRowCount(); i++) {
  if(tree.getPathForRow(i).getLastPathComponent().toString().equals(s) && tree.getPathForRow(i).getParentPath().getLastPathComponent().toString().equals(p)&& tree.getPathForRow(i).getParentPath().getParentPath().getLastPathComponent().toString().equals(p1))break;
  }
TreePath pt=tree.getPathForRow(i);
DefaultTreeModel model = (DefaultTreeModel)tree.getModel(); 
model.valueForPathChanged(pt,s1);*/
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
/*
DefaultMutableTreeNode child =new DefaultMutableTreeNode(s);
int i;
for (i = 0; i < tree.getRowCount(); i++) {
  if(tree.getPathForRow(i).getLastPathComponent().toString().equals(p))break;
  }
TreePath pt=tree.getPathForRow(i);
DefaultTreeModel model = (DefaultTreeModel)tree.getModel(); 
model.insertNodeInto(child,(DefaultMutableTreeNode)pt.getLastPathComponent(),0);*/
}

public void change (String s, String p,String s1)
{
int i;
this.setVisible(false);
  pravi_drvo();
  
select_node(s1,p);
this.setVisible(true);
/*for (i = 0; i < tree.getRowCount(); i++) {
  if(tree.getPathForRow(i).getLastPathComponent().toString().equals(s) && tree.getPathForRow(i).getParentPath().getLastPathComponent().toString().equals(p))break;
  }
TreePath pt=tree.getPathForRow(i);
DefaultTreeModel model = (DefaultTreeModel)tree.getModel(); 
model.valueForPathChanged(pt,s1);*/
}
public void removenode (String s,String p, String p1)
{
    this.setVisible(false);
    pravi_drvo();
    select_node(p,p1);
    this.setVisible(true);
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
 
    private void setMenus() 
    {
        try{
            ((JMenu)owner.mb.getMenu(1)).setEnabled(true); 
            ((JMenu)owner.mb.getMenu(2)).setEnabled(true); 
            
            String node=new String();
            if(!tree.isSelectionEmpty())   
                node=tree.getSelectionPath().getLastPathComponent().toString();
            String nodep=new String();
            if(!tree.isSelectionEmpty() && !tree.getSelectionPath().isDescendant(tree.getPathForRow(0)) )
                nodep=tree.getSelectionPath().getParentPath().getLastPathComponent().toString();
            String nodepp=new String();
            if(!tree.isSelectionEmpty() && !tree.getSelectionPath().toString().equals(tree.getPathForRow(0).toString())) 
                if(!tree.getSelectionPath().getParentPath().isDescendant(tree.getPathForRow(0))&&!tree.isSelectionEmpty())
                    nodepp=tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString();
            if(node.equals("Relation Schemes") || nodep.equals("Application Systems")){
                if(!node.equals("Relation Schemes")){
                    owner.mb.getMenu(2).getMenuComponent(4).setEnabled(true);
                    owner.buttonDB.setEnabled(true);
                    owner.buttonDBSchemaAnalysis.setEnabled(true);
                    owner.buttonAppGenerator.setEnabled(true);
                    owner.buttonSQLGenerator.setEnabled(true);
                    owner.mb.getMenu(2).getMenuComponent(7).setEnabled(true);
                }
                else {
                    owner.buttonDB.setEnabled(false);
                    owner.buttonDBSchemaAnalysis.setEnabled(false);
                    owner.buttonAppGenerator.setEnabled(false);
                    owner.buttonSQLGenerator.setEnabled(true);
                    owner.mb.getMenu(2).getMenuComponent(7).setEnabled(false);
                }
                owner.mb.getMenu(2).getMenuComponent(5).setEnabled(true);
                owner.mb.getMenu(2).getMenuComponent(6).setEnabled(true);
                owner.mb.getMenu(2).getMenuComponent(8).setEnabled(true);
                btnXML.setEnabled(true);
                btnXML1.setEnabled(true);
            }
            else{ 
                owner.mb.getMenu(2).getMenuComponent(6).setEnabled(false);
                owner.mb.getMenu(2).getMenuComponent(5).setEnabled(false);
                owner.mb.getMenu(2).getMenuComponent(4).setEnabled(false);
                owner.mb.getMenu(2).getMenuComponent(7).setEnabled(false);
                owner.mb.getMenu(2).getMenuComponent(8).setEnabled(false);
                owner.buttonDB.setEnabled(false);
                owner.buttonDBSchemaAnalysis.setEnabled(false);
                owner.buttonAppGenerator.setEnabled(false);
                owner.buttonSQLGenerator.setEnabled(false);
                btnXML.setEnabled(false);
                btnXML1.setEnabled(false);
            }
            if(node.equals("") || node.equals("Fundamentals")|| node.equals("Domains")  || node.equals("Reports") || node.equals("Form Types") || node.equals("Closure Graph") || tree.getSelectionPath().isDescendant(tree.getPathForRow(0)) || node.equals("Component Types") || nodep.equals("Component Types") || node.equals("Relation Schemes") || nodep.equals("Relation Schemes") ||  node.equals("Child Application Systems")  ||  nodep.equals("Child Application Systems")
                || nodepp.equals("Functions") 
                    || node.equals("Events")
                    || nodep.equals("Events")
                    || nodepp.equals("Events")){
                btnNew.setEnabled(false);
                btnEdit.setEnabled(false);
                btnErase.setEnabled(false);
                btnCopy.setEnabled(false);
                btnCut.setEnabled(false);
                btnPaste.setEnabled(false);
            }
            else if(node.equals("Functions")  || node.equals("Application Types")  || node.equals("Application Systems")  || node.equals("Owned") || node.equals("Relation Schemes") || node.equals("Attributes")  || node.equals("Inclusion Dependencies") || node.equals("User defined domains") || node.equals("Primitive domains") ){
                btnNew.setEnabled(true);
                btnEdit.setEnabled(false);
                btnErase.setEnabled(false);
                btnCopy.setEnabled(false);
                btnCut.setEnabled(false);
                btnPaste.setEnabled(true);
            }
            else if(node.equals("Packages")  ){
                btnNew.setEnabled(true);
                btnEdit.setEnabled(false);
                btnErase.setEnabled(false);
                btnCopy.setEnabled(false);
                btnCut.setEnabled(false);
                btnPaste.setEnabled(false);
            }            
            else if(node.equals("DB Server") || node.equals("Application Server") || node.equals("Client") ){
                btnNew.setEnabled(true);
                btnEdit.setEnabled(false);
                btnErase.setEnabled(false);
                btnCopy.setEnabled(false);
                btnCut.setEnabled(false);
                btnPaste.setEnabled(true);
            }   
            else if(nodepp.equals("Packages")){
                btnNew.setEnabled(true);
                btnEdit.setEnabled(true);
                btnErase.setEnabled(true);
                btnCopy.setEnabled(true);
                btnCut.setEnabled(true);
                btnPaste.setEnabled(true);
            }
            else if((nodepp.equals("DB Server") || nodepp.equals("Application Server") || nodepp.equals("Client") )
            && tree.getSelectionPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Packages") ){
                btnNew.setEnabled(false);
                btnEdit.setEnabled(false);
                btnErase.setEnabled(false);
                btnCopy.setEnabled(false);
                btnCut.setEnabled(false);
                btnPaste.setEnabled(false);
            }            
            else{
                btnNew.setEnabled(true);
                btnEdit.setEnabled(true);
                btnErase.setEnabled(true);
                btnCopy.setEnabled(true);
                btnCut.setEnabled(true);
                btnPaste.setEnabled(true);
            }
            boolean can=false;
            try{
                int id=-1;
                ResultSet rs,rs1;
                JDBCQuery query=new JDBCQuery(con);
                JDBCQuery query1=new JDBCQuery(con);
//*************************************************************                
//*************************************************************
//************************************************************* 
    //ovu liniju je jovo dodao zbog exceptiona koji se javljao na click 
                //System.out.println("Ovdje se javlja greska prilikom klika na drvo");
                if(!tree.isSelectionEmpty() &&  tree.getSelectionPath().getParentPath() != null){
                
//*************************************************************                
//*************************************************************
//*************************************************************                 
                
                    rs=query.select("select * from IISC_APP_SYSTEM where AS_name='"+ tree.getSelectionPath().getParentPath().getLastPathComponent().toString() +"' and PR_id="+ ID);
                    if(rs.next())id=rs.getInt(1);
                        query.Close();
                    rs=query.select("select count(*) from IISC_APP_SYSTEM  where AS_id<>"+ id +" and   PR_id="+ ID +"");
                    rs.next();
                    int j=rs.getInt(1);
                    query.Close();
                    String[] said=query.selectArray("select * from IISC_APP_SYSTEM where AS_id<>"+ id +" and   PR_id="+ID,j,1);
                    query.Close();
                    String[] sa=query.selectArray("select * from IISC_APP_SYSTEM where AS_id<>"+ id +" and   PR_id="+ID,j,3);
                    query.Close();
                    for (int i=1;i<sa.length;i++){
                        if(!isCircle(id,(new Integer(said[i])).intValue()) && !isCircle((new Integer(said[i])).intValue(), id))
                            can=true;
                    }
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            if((node.equals("Child Application Systems")||node.equals("Referenced"))&& can)
                btnRef.setEnabled(true);
            else
                btnRef.setEnabled(false);
            if(nodep.equals("Child Application Systems")||nodep.equals("Referenced"))
                btnDereference.setEnabled(true);
            else
                btnDereference.setEnabled(false);
            if(nodepp.equals("DB Schema collision reports")||nodepp.equals("DB Schema Design reports")||nodepp.equals("Repository reports")||nodepp.equals("Reports ")||nodepp.equals("Reports  ")||nodepp.equals("Application generation reports")){
                btnNew.setEnabled(false);
                btnCopy.setEnabled(false);
                btnCut.setEnabled(false);
                btnPaste.setEnabled(false);
            }
            if(nodep.equals("Reports ") || node.equals("Reports ") || nodep.equals("Reports  ") || node.equals("Reports  ") || nodep.equals("DB Schema collision reports") || node.equals("DB Schema collision reports") || nodep.equals("DB Schema Design reports") || node.equals("DB Schema Design reports") || nodep.equals("Repository reports") || node.equals("Repository reports") ||nodep.equals("Application generation reports") ||node.equals("Application generation reports")){
                btnNew.setEnabled(false);
                btnCopy.setEnabled(false);
                btnCut.setEnabled(false);
                btnPaste.setEnabled(false);
                btnErase.setEnabled(false);
            }
            if(nodep.equals("Application Systems")){
                owner.mb.getMenu(2).getMenuComponent(3).setEnabled(true);
                owner.mb.getMenu(2).getMenuComponent(2).setEnabled(true);  
            }
            else{
                owner.mb.getMenu(2).getMenuComponent(3).setEnabled(false);
                owner.mb.getMenu(2).getMenuComponent(2).setEnabled(false); 
                if(node.equals("Relation Schemes") || nodep.equals("Relation Schemes"))
                    owner.mb.getMenu(2).getMenuComponent(3).setEnabled(true);
                if(node.equals("Owned")||nodep.equals("Owned")){
                    owner.mb.getMenu(2).getMenuComponent(2).setEnabled(true);
                }
                if(nodep.equals("Owned")){
                    owner.mb.getMenu(2).getMenuComponent(7).setEnabled(true);
                    owner.buttonAppGenerator.setEnabled(true);
                }
                else{
                    owner.mb.getMenu(2).getMenuComponent(7).setEnabled(false);
                    owner.buttonAppGenerator.setEnabled(false);
                }
            }
            boolean can1=false,can2=false; 
            try{
                int id=-1;
                ResultSet rs;
                JDBCQuery query=new JDBCQuery(con);
                String qstr="";
                if( node.equals("Relation Schemes") )
                    qstr="select AS_id from IISC_APP_SYSTEM where AS_name='"+ tree.getSelectionPath().getParentPath().getLastPathComponent().toString() +"' and PR_id="+ ID;
                if( nodep.equals("Application Systems") )
                    qstr="select AS_id from IISC_APP_SYSTEM where AS_name='"+ tree.getSelectionPath().getLastPathComponent().toString() +"' and PR_id="+ ID;   
                if(!qstr.equals("")){
                    rs=query.select(qstr);
                if(rs.next())id=rs.getInt(1);
                    query.Close();}
                FunDepSet Sinth=new FunDepSet(con ,id);
                String str=new String();
                Iterator it=Sinth.getSystems(id).iterator();
                while(it.hasNext())
                    str=str+" or  AS_id="+it.next().toString();
                rs=query.select("select * from IISC_TF_APPSYS,IISC_FORM_TYPE    where IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id and IISC_FORM_TYPE.Tf_use=0 and (AS_id="+id +  str+")" );
                if(rs.next())
                    can1=true;
                query.Close();
                rs=query.select("select * from IISC_APP_SYS_REFERENCE,IISC_FORM_TYPE    where IISC_APP_SYS_REFERENCE.TF_id=IISC_FORM_TYPE.TF_id and IISC_FORM_TYPE.Tf_use=0 and  IISC_APP_SYS_REFERENCE.AS_id="+id +" " );
                if(rs.next())
                    can1=true;
                query.Close();
                rs=query.select("select * from IISC_APP_SYSTEM_CONTAIN where AS_id="+ id +" and   PR_id="+ ID +"");
                if(rs.next())
                    can2=true;
                query.Close();
                rs=query.select("select * from IISC_APP_SYS_REFERENCE,IISC_FORM_TYPE    where IISC_APP_SYS_REFERENCE.TF_id=IISC_FORM_TYPE.TF_id and IISC_FORM_TYPE.Tf_use=0 and  IISC_APP_SYS_REFERENCE.AS_id="+id +" " );
                if(rs.next())
                    can2=true;
                query.Close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            owner.mb.getMenu(2).getMenuComponent(4).setEnabled(can1);
            if(!can1)
                can2=false;
            owner.mb.getMenu(2).getMenuComponent(5).setEnabled(can2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    void doMouseClicked (MouseEvent me) {
        try{
//*******************************************************************            
//*******************************************************************
//*******************************************************************            
            //ovu liniju je jovo dodao zbog exceptiona koji se javljao na click 
           // System.out.println("Ovdje se javlja greska prilikom klika na drvo ");
            if(tree.isSelectionEmpty() || tree.getRowForLocation(me.getX(), me.getY())==-1 || tree.getPathForLocation(me.getX(), me.getY()) == null || (tree.getPathForLocation (me.getX(), me.getY())).getParentPath() == null){
                return;
            }
//*******************************************************************            
//*******************************************************************
//*******************************************************************        
            setMenus();
            if(tree.getRowForLocation(me.getX(), me.getY())==0){
                tree.setEditable(true);
            }
            else
                tree.setEditable(false);
        
            if(tree.getRowForLocation(me.getX(), me.getY())==0 & (me.getModifiers() & InputEvent.BUTTON3_MASK)== InputEvent.BUTTON3_MASK)
                tree.startEditingAtPath(tree.getPathForRow(0));
            //make_popup6(me);
            //System.out.println(""+tree.getRowForLocation(me.getX(), me.getY()) );
            //System.out.println(""+ (!tree.getPathForLocation(me.getX(), me.getY()).getParentPath().getLastPathComponent().toString().trim().equals("Owned")));
            //System.out.println(""+ );
            //System.out.println(""+ );
            //System.out.println(""+ );
            //System.out.println(""+ );
            
            
            if( tree.getRowForLocation(me.getX(), me.getY())!=0 && !tree.getPathForLocation(me.getX(), me.getY()).getParentPath().getLastPathComponent().toString().trim().equals("Owned"))
                tree.setSelectionPath(tree.getPathForLocation (me.getX(), me.getY()));
            String nodep=new String();
            if(!(tree.getPathForLocation (me.getX(), me.getY())).isDescendant(tree.getPathForRow(0)))
                nodep=(tree.getPathForLocation (me.getX(), me.getY())).getParentPath().getLastPathComponent().toString();
            String nodepp=new String();
            if(!(tree.getPathForLocation (me.getX(), me.getY())).getParentPath().isDescendant(tree.getPathForRow(0)))
                nodepp=(tree.getPathForLocation (me.getX(), me.getY())).getParentPath().getParentPath().getLastPathComponent().toString();
            String node=(tree.getPathForLocation (me.getX(), me.getY())).getLastPathComponent().toString();

            if(me.getClickCount()>1){
                TreePath tp = tree.getPathForLocation (me.getX(), me.getY());
                int i=-1,as=-1;
                ResultSet rs,rs1;
                String action;
                JDBCQuery query=new JDBCQuery(con);
                JDBCQuery query2=new JDBCQuery(con);
                try {
                    if (tree.getSelectionPath().getParentPath() !=null){
                        if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Business Applications")){
                            iisc.callgraph.CallingGraph cgrph = new iisc.callgraph.CallingGraph(200, 300, owner, con, ID, this, tree.getSelectionPath().getLastPathComponent().toString());
                            owner.desktop.add(cgrph, BorderLayout.CENTER);
                            try {
                                cgrph.setSelected(true);
                            } 
                            catch (java.beans.PropertyVetoException ed){
                                //ed.printStackTrace();
                            }
                            //cgrph.requestFocus();
                            //cgrph.show();
                        }
                    }
                    if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems")){
                        rs=query.select("select * from IISC_APP_SYSTEM where AS_name='" +tree.getSelectionPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
                        if(rs.next())
                            i=rs.getInt(1);
                        query.Close();
                    }
                    
                    if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Attributes")){
                        rs=query.select("select * from IISC_Attribute where Att_mnem='" + tree.getSelectionPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
                        if(rs.next())
                            i=rs.getInt(1);
                        query.Close();
                    }
  
                    if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Inclusion Dependencies")){
                        rs=query.select("select * from IISC_Inclusion_Dependency where ID_name='" + tree.getSelectionPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
                        if(rs.next())
                            i=rs.getInt(1);
                        query.Close();
                    }
  
					  if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Join Dependencies"))
					  {
							rs=query.select("select JD_id from IISC_JOIN_DEPENDENCY,IISC_APP_SYSTEM where IISC_JOIN_DEPENDENCY.AS_id=IISC_APP_SYSTEM.AS_id and JD_name='" + tree.getSelectionPath().getLastPathComponent().toString() + "'  and IISC_JOIN_DEPENDENCY.PR_id="+ID+ " and AS_name='" + tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString() + "'");
							if(rs.next())
							i=rs.getInt(1);
							query.Close();
					  } 
                  //jovo+ dupli klik na pakete
                  //System.out.println(tree.getSelectionPath().getPath()[tree.getSelectionPath().getPath().length -3].toString());
                  if( tree.getSelectionPath().getPath()[tree.getSelectionPath().getPath().length -3].toString().equals("Packages")){
                      //System.out.println("Usao u packages");
                      int pack_type = 0;
                      if( tree.getSelectionPath().getPath()[tree.getSelectionPath().getPath().length -2].toString().equals("Application Server"))
                        pack_type =2;
                      else if( tree.getSelectionPath().getPath()[tree.getSelectionPath().getPath().length -2].toString().equals("Client"))
                        pack_type = 3;
                      else if( tree.getSelectionPath().getPath()[tree.getSelectionPath().getPath().length -2].toString().equals("DB Server"))
                        pack_type =1;
                      
                      
                      i = -1;
                      rs=query.select("select * from IISC_PACKAGE where Pack_name='" + tree.getSelectionPath().getLastPathComponent().toString() + "'  and PR_id="+ID+" and Pack_type = "+pack_type);
                      if (rs.next())
                      {
                        i=rs.getInt("Pack_id");
                      }
                      query.Close();
                  }
      
                  // provjerava putanju RODITELJA i ako je zadnji Function ulazi
                  // akcija 1    - dupli klik na ime funkcije (uzima id funkcije)
                  
                    //System.out.println(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Functions"));
                    if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Functions")){
                        /*
                         * if(rs.next())
                         *    i=rs.getInt(1);
                         */
                        //System.out.println("select * from IISC_Function where Fun_name='" + tree.getSelectionPath().getLastPathComponent().toString().split(" ")[0] + "'  and PR_id="+ID); 
                        rs=query.select("select * from IISC_Function where Fun_name='" + tree.getSelectionPath().getLastPathComponent().toString().split(" ")[0] + "'  and PR_id="+ID);
                        i=-1;
                        while(rs.next()){
                              String funnamecmb = rs.getString("Fun_name");
                              int Fun_id = rs.getInt("Fun_id");//getString("Fun_id");
            
                              rs1=query2.select("select F.*,D.Dom_mnem from IISC_FUN_PARAM F,IISC_DOMAIN D where F.Dom_id = D.Dom_id AND F.PR_id = D.PR_id AND F.PR_id= " + ID + " AND F.Fun_id = "+Fun_id+" order by F.Param_seq");
                                
                              funnamecmb = funnamecmb + " ( ";
                              int itemp = 1;
                              while(rs1.next()){
                                  String domtemp= rs1.getString("Dom_mnem");
                                  funnamecmb = funnamecmb + domtemp + " , ";  
                                  itemp = 2;
                              }
                              funnamecmb = funnamecmb.substring(0,funnamecmb.length()-itemp) + " )";
                              //System.out.println(funnamecmb+":");
                              //System.out.println(tree.getSelectionPath().getLastPathComponent().toString()+":");
                              if( tree.getSelectionPath().getLastPathComponent().toString().equals(funnamecmb) ) {
                                  i=Fun_id;
                                  break;
                              }
                              query2.Close();
                        }  
                        query.Close();
                    }
      

  
  
                    //stari kod za funkciju
                    /*
                    if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Functions"))
                    {rs=query.select("select * from IISC_Function where Fun_name='" + tree.getSelectionPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
                    if(rs.next())
                    i=rs.getInt(1);
                    query.Close();
                    }
                    */
                    if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("User defined domains")){
                        rs=query.select("select * from IISC_Domain where Dom_mnem='" + tree.getSelectionPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
                        if(rs.next())
                            i=rs.getInt(1);
                        query.Close();
                    }
                    if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Primitive domains")){
                        rs=query.select("select * from IISC_PRIMITIVE_TYPE where PT_mnemonic='" + tree.getSelectionPath().getLastPathComponent().toString() + "' ");
                        if(rs.next())
                            i=rs.getInt(1);
                        query.Close();
                    }
                    if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Types")){
                        rs=query.select("select * from IISC_App_System_Type where AS_type='" + tree.getSelectionPath().getLastPathComponent().toString() + "'");
                        if(rs.next())
                            i=rs.getInt(1);
                        query.Close();
                    }
    
      
                    if( tree.getSelectionPath().getLastPathComponent().toString().equals("Closure Graph")){
                        rs=query.select("select * from IISC_APP_SYSTEM where AS_name='" +tree.getSelectionPath().getParentPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
                        if(rs.next())
                            i=rs.getInt(1);
                        query.Close();
                    }
                    
                    if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Relation Schemes")){  
                        if( tree.getSelectionPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Application Systems")){
                            rs=query.select("select AS_id from IISC_APP_SYSTEM where AS_name='" +tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
                            if(rs.next())
                            as=rs.getInt(1);
                            query.Close();
                        }
                        rs=query.select("select * from IISC_RELATION_SCHEME where RS_name='" + tree.getSelectionPath().getLastPathComponent().toString() + "' and AS_id="+ as +"  and PR_id="+ID);
                        if(rs.next())
                            i=rs.getInt(1);
                        query.Close();
                    } 
         
                    if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Owned") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Referenced")){
                        if( tree.getSelectionPath().getParentPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Application Systems")){
                            rs=query.select("select AS_id from IISC_APP_SYSTEM where AS_name='" +tree.getSelectionPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
                            if(rs.next())
                            as=rs.getInt(1);
                            query.Close();
                        }
                        rs=query.select("select * from IISC_FORM_TYPE,IISC_TF_APPSYS where TF_mnem='" + tree.getSelectionPath().getLastPathComponent().toString() + "' and IISC_FORM_TYPE.Tf_id=IISC_TF_APPSYS.Tf_id  and IISC_TF_APPSYS.PR_id="+ID);
                        if(rs.next())
                            i=rs.getInt(1);
                        query.Close();
                    }
                    if (tp.getLastPathComponent().toString().equals("Closure Graph")){  
                        GraphDraw gr=new GraphDraw(owner,tree.getSelectionPath().getParentPath().getLastPathComponent().toString(),con,i);
                        Settings.Center(gr);
                        WindowsManager.add(gr);
                        gr.setVisible(true); 
                    }
					if (tp.getLastPathComponent().toString().equals("Application generation reports")|| tp.getLastPathComponent().toString().equals("DB Schema collision reports")|| tp.getLastPathComponent().toString().equals("DB Schema Design reports") || tp.getLastPathComponent().toString().equals("Repository reports") || tp.getLastPathComponent().toString().equals("Reports ") || tp.getLastPathComponent().toString().equals("Reports  "))
					{ 
					  if (tp.getLastPathComponent().toString().equals("Application generation reports")|| tp.getLastPathComponent().toString().equals("DB Schema collision reports")|| tp.getLastPathComponent().toString().equals("DB Schema Design reports") || tp.getLastPathComponent().toString().equals("Repository reports") ){
                        rs=query.select("select AS_id from IISC_APP_SYSTEM where AS_name='" +tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
                        if(rs.next())
                            as=rs.getInt(1);
                        query.Close();}
                        Collision rep=new Collision(owner,"",false,con,as,this);
                        Settings.Center(rep);
                        String q="";
                        if (tree.getSelectionPath().getLastPathComponent().toString().equals("DB Schema collision reports"))
                            q= " and CLT_id<10 ";
                        else if(tree.getSelectionPath().getLastPathComponent().toString().equals("DB Schema Design reports"))
                            q= " and CLT_id>=10 and CLT_id<20 ";
                        else if(tree.getSelectionPath().getLastPathComponent().toString().equals("Repository reports"))
                            q= " and CLT_id>=20 and CLT_id<30 ";
                        else if(tree.getSelectionPath().getLastPathComponent().toString().equals("Reports "))
                            q= " and CLT_id>=30 and CLT_id<40  and PR_id="+ ID+ " ";
                        else if(tree.getSelectionPath().getLastPathComponent().toString().equals("Reports  "))
							q= " and CLT_id>=40 and CLT_id<50  and PR_id="+ ID+ " ";
						else if(tree.getSelectionPath().getLastPathComponent().toString().equals("Application generation reports"))
							q= " and CLT_id>=50 and CLT_id<60  and PR_id="+ ID+ " ";
                        rep.filter(q);
                        WindowsManager.add(rep);
                        rep.setVisible(true); 
                    }
					if (tp.getParentPath().getLastPathComponent().toString().equals("Application generation reports") || tp.getParentPath().getLastPathComponent().toString().equals("DB Schema collision reports") || tp.getParentPath().getLastPathComponent().toString().equals("DB Schema Design reports") || tp.getParentPath().getLastPathComponent().toString().equals("Repository reports") || tp.getParentPath().getLastPathComponent().toString().equals("Reports ") || tp.getParentPath().getLastPathComponent().toString().equals("Reports  "))
					  { 
					  if (tp.getParentPath().getLastPathComponent().toString().equals("Application generation reports")|| tp.getParentPath().getLastPathComponent().toString().equals("DB Schema collision reports")||  tp.getParentPath().getLastPathComponent().toString().equals("DB Schema Design reports") || tp.getParentPath().getLastPathComponent().toString().equals("Repository reports") ){
                        
                        rs=query.select("select AS_id from IISC_APP_SYSTEM where AS_name='" +tree.getSelectionPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
                        if(rs.next())
                            as=rs.getInt(1);
                        query.Close();}
                        Collision rep=new Collision(owner,"",false,con,as,this);
                        Settings.Center(rep);
                        WindowsManager.add(rep);
                        rep.cmbReportType.setSelectedItem(tp.getLastPathComponent().toString());
                        
                        String q="";
                        if (tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("DB Schema collision reports"))
                            q= " and CLT_id<10 ";
                        else if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("DB Schema Design reports"))
                            q= " and CLT_id>=10 and CLT_id<20 ";
                        else if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Repository reports"))
                            q= " and CLT_id>=20 and CLT_id<30 ";
                        else if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Reports "))
                            q= " and CLT_id>=30 and CLT_id<40  and PR_id="+ ID+ " ";
                        else if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Reports  "))
							q= " and CLT_id>=40 and CLT_id<50 and PR_id="+ ID+ " ";
					   else if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application generation reports"))
							q= " and CLT_id>=50 and CLT_id<60 and PR_id="+ ID+ " ";
                        rep.filter(q);
                        rep.setVisible(true); 
                    }
                    if (!tp.getParentPath().isDescendant(tree.getPathForRow(0)))
						if (tp.getParentPath().getParentPath().getLastPathComponent().toString().equals("Application generation reports") || tp.getParentPath().getParentPath().getLastPathComponent().toString().equals("DB Schema collision reports") || tp.getParentPath().getParentPath().getLastPathComponent().toString().equals("DB Schema Design reports") || tp.getParentPath().getParentPath().getLastPathComponent().toString().equals("Repository reports") || tp.getParentPath().getParentPath().getLastPathComponent().toString().equals("Reports ") || tp.getParentPath().getParentPath().getLastPathComponent().toString().equals("Reports  "))
						{ 
							if (tp.getParentPath().getParentPath().getLastPathComponent().toString().equals("Application generation reports")|| tp.getParentPath().getParentPath().getLastPathComponent().toString().equals("DB Schema collision reports")|| tp.getParentPath().getParentPath().getLastPathComponent().toString().equals("DB Schema Design reports") || tp.getParentPath().getParentPath().getLastPathComponent().toString().equals("Repository reports") ){
                                rs=query.select("select AS_id from IISC_APP_SYSTEM where AS_name='" +tree.getSelectionPath().getParentPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
                                if(rs.next())
                                as=rs.getInt(1);
                                query.Close();
                            }
    
                            rs=query.select("select * from IISC_COLLISION_LOG,IISC_LOG_TYPE where IISC_COLLISION_LOG.CL_type=IISC_LOG_TYPE.CLT_id and IISC_COLLISION_LOG.AS_id=" + as + " and CLT_name='" + tree.getSelectionPath().getParentPath().getLastPathComponent().toString() + "'  and CL_date='" + tree.getSelectionPath().getLastPathComponent().toString() + "' and IISC_COLLISION_LOG.PR_id="+ID);
                            if(rs.next())
                                i=rs.getInt("CL_id");
                            query.Close();
                            if (tp.getParentPath().getParentPath().getLastPathComponent().toString().equals("Application generation reports")|| tp.getParentPath().getParentPath().getLastPathComponent().toString().equals("DB Schema collision reports")|| tp.getParentPath().getParentPath().getLastPathComponent().toString().equals("DB Schema Design reports") ){
                                Report rep=new Report(owner,"",false,con,i);
                                Settings.Center(rep);
                                WindowsManager.add(rep);
                                rep.setVisible(true);
                            }
                            else{
                                RepReport rep=new RepReport(owner,"",false,con,i,this);
                                Settings.Center(rep);
                                WindowsManager.add(rep);
                                rep.setVisible(true);
                            }
                        }
					  if (tp.getParentPath().getLastPathComponent().toString().equals("Join Dependencies"))
					  { 
						  JoinDep join=new JoinDep(owner,this.getTitle(),i,false,con,this,tp.getParentPath().getParentPath().getLastPathComponent().toString());
						  Settings.Center(join);
						  WindowsManager.add(join);
						  join.setVisible(true);
					  }

                        if (tp.getLastPathComponent().toString().equals("Application Systems")){
                            AppSys app=new AppSys(owner,this.getTitle(),false,con,-1,this);
                            Settings.Center(app);
                            WindowsManager.add(app);
                            app.setVisible(true);
                        }
                        if (tp.getLastPathComponent().toString().equals("Application Types")){
                            AppType apptype=new AppType(owner,this.getTitle(),false,con,-1,this);
                            Settings.Center(apptype);
                            WindowsManager.add(apptype);
                            apptype.setVisible(true);
                        }
                        if (!tp.getParentPath().isDescendant(tree.getPathForRow(0)))
                            if( tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Application Systems")){
                                rs=query.select("select * from IISC_APP_SYSTEM where AS_name='" +tree.getSelectionPath().getParentPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
                                if(rs.next())
                                    i=rs.getInt(1);
                                query.Close();
                            }
                        if (tp.getParentPath().getLastPathComponent().toString().equals("Application Systems")){
                            AppSys app=new AppSys(owner,this.getTitle(),false,con,i,this);
                            Settings.Center(app);
                            WindowsManager.add(app);
                            app.setVisible(true);
                        }
                        if (tp.getLastPathComponent().toString().equals("Owned")){
                            Form form=new Form(owner,this.getTitle(),false,con,-1,this,tp.getParentPath().getParentPath().getLastPathComponent().toString());
                            Settings.Center(form);
                            WindowsManager.add(form);
                            form.setVisible(true);
                        }
                        if (tp.getParentPath().getLastPathComponent().toString().equals("Owned")){
                            Form form=new Form(owner,this.getTitle(),false,con,i,this,tp.getParentPath().getParentPath().getParentPath().getLastPathComponent().toString());
                            Settings.Center(form);
                            WindowsManager.add(form);
                            form.setVisible(true);
                        }
                        if (tp.getParentPath().getLastPathComponent().toString().equals("Referenced")){
                            String str="";
                            rs=query.select("select * from IISC_APP_SYSTEM,IISC_TF_APPSYS,IISC_FORM_TYPE  where IISC_APP_SYSTEM.AS_id=IISC_TF_APPSYS.AS_id and IISC_FORM_TYPE.PR_id=" + ID + " and IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and  IISC_FORM_TYPE.TF_id="+i);
                            if(rs.next())
                                str=rs.getString("AS_name");
                            query.Close();
                            Form form=new Form(owner,this.getTitle(),false,con,i,this,str);
                            Settings.Center(form);
                            form.readonly();
                            select_node(tp.getLastPathComponent().toString(),"Referenced","Form Types",tp.getParentPath().getParentPath().getParentPath().getLastPathComponent().toString() );
                            WindowsManager.add(form);
                            form.setVisible(true);
                        }
                        //stari kod funkcije
                        
                        /*if (tp.getLastPathComponent().toString().equals("Functions"))
                        {Function fun=new Function(owner,this.getTitle(),false,con,-1,this);
                        Settings.Center(fun);
                        WindowsManager.add(fun);
                        fun.setVisible(true);
                        }*/
                        if (tp.getParentPath().getLastPathComponent().toString().equals("Functions") || tp.getLastPathComponent().toString().equals("Functions")){
                            Function fun=new Function(owner,this.getTitle(),false,con,i,this);
                            Settings.Center(fun);
                            WindowsManager.add(fun);
                            fun.setVisible(true);
                        }
                        if (tp.getLastPathComponent().toString().equals("Packages") || tp.getParentPath().getParentPath().getLastPathComponent().toString().equals("Packages")){
                            Packages pack=new Packages(owner,this.getTitle(),false,con,i,this);
                            Settings.Center(pack);
                            WindowsManager.add(pack);
                            pack.setVisible(true);
                        }                        
                        if (tp.getLastPathComponent().toString().equals("DB Server") ){
                            Packages pack=new Packages(owner,this.getTitle(),false,con,i,this);
                            pack.setType(0);
                            Settings.Center(pack);
                            WindowsManager.add(pack);
                            pack.setVisible(true);
                        }                                                
                    if (tp.getLastPathComponent().toString().equals("Application Server") ){
                        Packages pack=new Packages(owner,this.getTitle(),false,con,i,this);
                        pack.setType(1);
                        Settings.Center(pack);
                        WindowsManager.add(pack);
                        pack.setVisible(true);
                    }        
                    if (tp.getLastPathComponent().toString().equals("Client") ){
                        Packages pack=new Packages(owner,this.getTitle(),false,con,i,this);
                        pack.setType(2);
                        Settings.Center(pack);
                        WindowsManager.add(pack);
                        pack.setVisible(true);
                    }                       
                        if (tp.getLastPathComponent().toString().equals("User defined domains")){
                            owner.dom=new Domain(owner,this.getTitle(),false,con,-1,this);
                            Settings.Center(owner.dom);
                            WindowsManager.add(owner.dom);
                            owner.dom.setVisible(true);
                        }
                        if (tp.getParentPath().getLastPathComponent().toString().equals("User defined domains")){
                            owner.dom=new Domain(owner,this.getTitle(),false,con,i,this);
                            Settings.Center(owner.dom);
                            WindowsManager.add(owner.dom);
                            owner.dom.setVisible(true);
                        }
                        if (tp.getLastPathComponent().toString().equals("Primitive domains")){
                            PrimitiveTypes  pt=new PrimitiveTypes(owner,this.getTitle(),-1,false,con,this);
                            Settings.Center(pt);
                            WindowsManager.add(pt);
                            pt.setVisible(true);
                        }
                        if (tp.getParentPath().getLastPathComponent().toString().equals("Primitive domains")){
                            PrimitiveTypes  pt=new PrimitiveTypes(owner,this.getTitle(),i,false,con,this);
                            Settings.Center(pt);
                            WindowsManager.add(pt);
                            pt.setVisible(true);
                        }
                        if (tp.getLastPathComponent().toString().equals("Attributes")){
                            Attribute att=new Attribute(owner,this.getTitle(),false,con,-1,this);
                            Settings.Center(att);
                            WindowsManager.add(att);
                            att.setVisible(true);
                        }
                        
                        if (tp.getParentPath().getLastPathComponent().toString().equals("Attributes")){
                            Attribute att=new Attribute(owner,this.getTitle(),false,con,i,this);
                            Settings.Center(att);
                            WindowsManager.add(att);
                            att.setVisible(true);
                        }
                        
                        if (tp.getLastPathComponent().toString().equals("Inclusion Dependencies")){
                            InclusionDep dep=new InclusionDep(owner,this.getTitle(),-1,false,con,this);
                            Settings.Center(dep);
                            WindowsManager.add(dep);
                            dep.setVisible(true);
                        }
                        
                        if (tp.getParentPath().getLastPathComponent().toString().equals("Inclusion Dependencies")){
                            InclusionDep dep=new InclusionDep(owner,this.getTitle(),i,false,con,this);
                            Settings.Center(dep);
                            WindowsManager.add(dep);
                            dep.setVisible(true);
                        }
      
    
                        if (tp.getParentPath().getLastPathComponent().toString().equals("Application Types")){
                            AppType apptype=new AppType(owner,this.getTitle(),false,con,i,this);
                            Settings.Center(apptype);
                            WindowsManager.add(apptype);
                            apptype.setVisible(true);
                        }
                        if (tp.getParentPath().getLastPathComponent().toString().equals("Relation Schemes")){ 
                            RScheme rsh=new RScheme(owner,this.getTitle(),false,con,i,this,tp.getParentPath().getParentPath().getLastPathComponent().toString());
                            Settings.Center(rsh);
                            WindowsManager.add(rsh);
                            rsh.setVisible(true);
                        }
                }
                catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }


            if((me.getModifiers() & InputEvent.BUTTON3_MASK)== InputEvent.BUTTON3_MASK){
                if(node.equals("Application generation reports") || nodepp.equals("Application generation reports") || 
					node.equals("DB Schema collision reports") || nodepp.equals("DB Schema collision reports") || 
                    nodep.equals("Application generation reports") || nodep.equals("DB Schema collision reports") || node.equals("DB Schema Design reports") || 
                    nodepp.equals("DB Schema Design reports") || nodep.equals("DB Schema Design reports") || 
                    nodep.equals("Inclusion Dependencies") || nodep.equals("Attributes") || 
                    nodep.equals("User defined domains") || nodep.equals("Primitive domains")   || 
                    nodep.equals("Functions") ||  nodep.equals("Application Types") ||  nodep.equals("Owned") ||  nodep.equals("Join Dependencies") || 
                    nodep.equals("Application Systems") || nodepp.equals("Packages") )
                    make_popup(me);
            
                if(node.equals("Inclusion Dependencies") || node.equals("Attributes") || 
                    node.equals("User defined domains")  || node.equals("Primitive domains")  || node.equals("Functions")  ||
                    node.equals("Application Types") ||  node.equals("Owned") ||  node.equals("Application Systems") || node.equals("Join Dependencies") || 
                    node.equals("Packages") || nodep.equals("Packages")) 
                    make_popup2(me);
            
                if(node.equals("Child Application Systems"))
                    make_popup1(me);
            
                if(nodep.equals("Child Application Systems"))
                    make_popup3(me);
            
                if(node.equals("Referenced"))
                    make_popup4(me);
            
                if(nodep.equals("Referenced"))
                    make_popup5(me);
            
                if(nodep.equals("Relation Schemes"))
                    make_popup7(me);
            
                if(node.equals("Relation Schemes"))
                    make_popup8(me);
            
                if(node.equals("Repository reports") || nodepp.equals("Repository reports") || 
                    nodep.equals("Repository reports") || node.equals("Reports ") || nodepp.equals("Reports ") || 
                    nodep.equals("Reports ") || node.equals("Reports  ") || nodepp.equals("Reports  ") || 
                    nodep.equals("Reports  "))
                    make_popup9(me);
            
                if(node.equals("Business Applications"))
                    make_popup10(me, nodep.toString());
            
                if(nodep.equals("Business Applications"))
                    make_popup11(me, node.toString(), nodepp.toString());
            
            }
        }
        catch (Exception eq){
            eq.printStackTrace();
        }
    }


public void make_popup10(MouseEvent me, String As_name) 
{
JMenuItem menuItem1 = new JMenuItem("New"); 
// Constructor the pop-up menu 
JPopupMenu popupMenu = new JPopupMenu();
final PTree tr= this;
final String AsNM = As_name;
menuItem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          BussinesApplication baFrm = new BussinesApplication(owner, con, "", ID, AsNM, tr);
          Settings.Center(baFrm);
          baFrm.setVisible(true);
        }
      });
      
      popupMenu.add(menuItem1);
      popupMenu.show(tree,me.getX()+10, me.getY());
}


public void make_popup11(MouseEvent me, String name, String AsName) 
{
JMenuItem menuItem1 = new JMenuItem("New"); 
JMenuItem menuItem2 = new JMenuItem("Edit"); 
JMenuItem menuItem3 = new JMenuItem("Delete"); 
JMenuItem menuItem4 = new JMenuItem("Diagram"); 
// Constructor the pop-up menu 
JPopupMenu popupMenu = new JPopupMenu();
final String nm = name;
final String asn = AsName;

final PTree tr = this;

      menuItem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
            BussinesApplication baFrm = new BussinesApplication(owner, con, "", ID,asn, tr);
            Settings.Center(baFrm);
            baFrm.setVisible(true);
        }
      });
      
      menuItem2.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
            EditBA(nm, asn);
        }
      });
      
menuItem3.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          DeleteBA(nm, asn);
        }
      });
      
      menuItem4.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          iisc.callgraph.CallingGraph cgrph = new iisc.callgraph.CallingGraph(200, 300, owner, con, ID, tr, nm);
          owner.desktop.add(cgrph, BorderLayout.CENTER);
          try 
          {
              cgrph.setSelected(true);
          } 
          catch (java.beans.PropertyVetoException ed)
          {
              //ed.printStackTrace();
          }
        }
      });

      popupMenu.add(menuItem1);
      popupMenu.add(menuItem2);
      popupMenu.add(menuItem3);
      popupMenu.addSeparator();
      popupMenu.add(menuItem4);
      popupMenu.show(tree,me.getX()+10, me.getY());
}

private void EditBA(String name, String AsName)
{
    BussinesApplication baFrm = new BussinesApplication(owner, con, name, ID, AsName, this);
    Settings.Center(baFrm);
    baFrm.setVisible(true);
    
}

private void DeleteBA(String name, String AsName)
{
    int opt;
    
    opt = JOptionPane.showConfirmDialog(this, "Confirm delete", "", JOptionPane.YES_NO_OPTION);
    
    if (opt == JOptionPane.YES_OPTION)
    {
        try
        {
            //Brisanje iz baze podataka
            int BA_id;
            
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select BA_id from IISC_BUSINESS_APPLICATION where BA_mnem ='" + name + "' and PR_id=" +  ID);
            
            if ( rs.next() )
            {
                BA_id = rs.getInt(1);
                
                BussinesApplication.Delete(BA_id, con);
                BussinesApplication.DeleteBAFromTree(name, this, AsName);
            }
            
        }
        catch(SQLException e)
        {
            System.out.println(e.toString());
        }  
    }
}

public void make_popup(MouseEvent me) 
{
  JMenuItem menuItem1 = new JMenuItem("New"); 
  JMenuItem menuItem2 = new JMenuItem("Edit"); 
  JMenuItem menuItem12 = new JMenuItem("View"); 
  JMenuItem menuItem4 = new JMenuItem("Copy"); 
  JMenuItem menuItem5 = new JMenuItem("Cut"); 
  JMenuItem menuItem6 = new JMenuItem("Paste"); 
  JMenuItem menuItem7 = new JMenuItem("DB Schema Design"); 
  JMenuItem menuItem8 = new JMenuItem("DB Schema Analysis"); 
  JMenuItem menuItem9 = new JMenuItem("Generate Application"); 
  JMenuItem menuItem3 = new JMenuItem("Delete"); 
  JMenuItem menuItem13 = new JMenuItem("Delete all");
  JMenuItem menuItem14 = new JMenuItem("Delete obsolete");
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
menuItem8.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
menuItem9.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
menuItem12.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
menuItem13.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
menuItem14.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
if(tree.getSelectionPath().getLastPathComponent().toString().equals("Application generation reports") ||tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application generation reports") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("DB Schema collision reports") || tree.getSelectionPath().getLastPathComponent().toString().equals("DB Schema collision reports") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("DB Schema Design reports") || tree.getSelectionPath().getLastPathComponent().toString().equals("DB Schema Design reports"))
{ 
popupMenu.add( menuItem12 );
popupMenu.add( menuItem13 );
popupMenu.add( menuItem14 );}
else if(tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Application generation reports") || tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("DB Schema collision reports") || tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("DB Schema Design reports"))
{ 
popupMenu.add( menuItem12 ); 
popupMenu.add( menuItem3 );}
else
{
popupMenu.add( menuItem1 );
popupMenu.add( menuItem2 ); 
popupMenu.addSeparator();
popupMenu.add( menuItem3 ); 
popupMenu.add( menuItem4 ); 
popupMenu.add( menuItem5 ); 
if (owner.clipboard.isEmpty())
menuItem6.setEnabled(false);
popupMenu.add( menuItem6 ); 
if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Owned"))
{
  popupMenu.addSeparator();
    
    JMenu fileMenu = new JMenu("Export");
   
    menuItem1 = new JMenuItem("XML Document");
    menuItem1.addActionListener(new ActionListener()
     {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      }); 
    fileMenu.add(menuItem1);
    menuItem1 = new JMenuItem("Generate Application");
    menuItem1.addActionListener(new ActionListener()
     {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      }); 
    popupMenu.add(menuItem1);
 
 popupMenu.add( fileMenu );
}
if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems"))
{
  popupMenu.addSeparator();
         try
    {int id=-1;
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(con);
    rs=query.select("select * from IISC_APP_SYSTEM where AS_name='"+ tree.getSelectionPath().getLastPathComponent().toString() +"' and PR_id="+ ID);
    if(rs.next())id=rs.getInt(1);
    query.Close();
       FunDepSet Sinth=new FunDepSet(con ,id);
    String str=new String();
    Iterator it=Sinth.getSystems(id).iterator();
     while(it.hasNext())str=str+" or  AS_id="+it.next().toString();
   boolean can1=false,can2=false;
    rs=query.select("select * from IISC_TF_APPSYS,IISC_FORM_TYPE    where IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id and IISC_FORM_TYPE.Tf_use=0 and (AS_id="+id +  str+")" );
    if(rs.next())
    can1=true;
    query.Close();
    rs=query.select("select * from IISC_APP_SYS_REFERENCE,IISC_FORM_TYPE    where IISC_APP_SYS_REFERENCE.TF_id=IISC_FORM_TYPE.TF_id and IISC_FORM_TYPE.Tf_use=0 and  IISC_APP_SYS_REFERENCE.AS_id="+id +" " );
    if(rs.next())
    can1=true;
    query.Close();
    rs=query.select("select * from IISC_APP_SYSTEM_CONTAIN where AS_id="+ id +" and   PR_id="+ ID +"");
    if(rs.next())
    can2=true;
    query.Close();
    rs=query.select("select * from IISC_APP_SYS_REFERENCE,IISC_FORM_TYPE    where IISC_APP_SYS_REFERENCE.TF_id=IISC_FORM_TYPE.TF_id and IISC_FORM_TYPE.Tf_use=0 and  IISC_APP_SYS_REFERENCE.AS_id="+id +" " );
    if(rs.next())
    can2=true;
    query.Close();
    menuItem7.setEnabled(can1);
    if(!can1)
    can2=false;
    menuItem8.setEnabled(can2);
    }
 catch(SQLException e)
    {
      e.printStackTrace();
    }
 popupMenu.add( menuItem7 );  
 popupMenu.add( menuItem8 );  
 JMenu fileMenu = new JMenu("Generate DB Schema");
   
    menuItem1 = new JMenuItem("SQL DDL");
     menuItem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      }); 
    fileMenu.add(menuItem1);
  menuItem1 = new JMenuItem("OO DDL");
     menuItem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
  menuItem1.setEnabled(false);
    fileMenu.add(menuItem1);
  menuItem1 = new JMenuItem("XML Schema");
     menuItem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      }); 
    menuItem1.setEnabled(false); 
    fileMenu.add(menuItem1);
 popupMenu.add( fileMenu );
    
    popupMenu.add( menuItem9 ); 
    fileMenu = new JMenu("Export");
   
    menuItem1 = new JMenuItem("DB Schema to XML Document");
     menuItem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      }); 
    fileMenu.add(menuItem1);
  menuItem1 = new JMenuItem("Form Types to XML Document");
     menuItem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      }); 
    fileMenu.add(menuItem1);
 popupMenu.add( fileMenu );
 
 
}
}
popupMenu.setSelected(popupMenu);
popupMenu.show(tree,me.getX()+10, me.getY());

}

public void make_popup9(MouseEvent me) 
{
JMenuItem menuItem2 = new JMenuItem("Generate Report"); 
JMenuItem menuItem12 = new JMenuItem("View"); 
JMenuItem menuItem3 = new JMenuItem("Delete"); 
JMenuItem menuItem13 = new JMenuItem("Delete all");
JMenuItem menuItem14 = new JMenuItem("Delete obsolete");
// Constructor the pop-up menu 
JPopupMenu popupMenu = new JPopupMenu();
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
menuItem12.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
menuItem13.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
menuItem14.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application generation reports") ||  tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Repository reports") || tree.getSelectionPath().getLastPathComponent().toString().equals("Repository reports")|| tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Reports ") || tree.getSelectionPath().getLastPathComponent().toString().equals("Reports ") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Reports  ") || tree.getSelectionPath().getLastPathComponent().toString().equals("Reports  "))
{ 
popupMenu.add( menuItem12 );
popupMenu.add( menuItem13 );
popupMenu.add( menuItem14 );
}
else if(tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Application generation reports") || tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Repository reports") || tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Reports ") || tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Reports  "))
{ 
popupMenu.add( menuItem12 ); 
popupMenu.add( menuItem3 );}
if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Repository reports") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Reports ") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Reports  "))
popupMenu.add( menuItem2 );
popupMenu.setSelected(popupMenu);
popupMenu.show(tree,me.getX()+10, me.getY());
}

public void make_popup1(MouseEvent me) 
{
boolean can=false;
JPopupMenu popupMenu = new JPopupMenu();
JMenuItem menuItem1 = new JMenuItem("Add Application System"); 
     try
    {int id=-1;
    ResultSet rs,rs1;
    JDBCQuery query=new JDBCQuery(con);
    JDBCQuery query1=new JDBCQuery(con);
    rs=query.select("select * from IISC_APP_SYSTEM where AS_name='"+ tree.getSelectionPath().getParentPath().getLastPathComponent().toString() +"' and PR_id="+ ID);
    if(rs.next())id=rs.getInt(1);
    query.Close();
    rs=query.select("select count(*) from IISC_APP_SYSTEM  where AS_id<>"+ id +" and   PR_id="+ ID +"");
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    String[] said=query.selectArray("select * from IISC_APP_SYSTEM where AS_id<>"+ id +" and   PR_id="+ID,j,1);
    query.Close();
    String[] sa=query.selectArray("select * from IISC_APP_SYSTEM where AS_id<>"+ id +" and   PR_id="+ID,j,3);
    query.Close();
    for (int i=1;i<sa.length;i++)
    {
    if(!isCircle(id,(new Integer(said[i])).intValue()) && !isCircle((new Integer(said[i])).intValue(), id))
    can=true;
    }
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
if(!can)menuItem1.setEnabled(false);
menuItem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup2_actionPerformed(e);
        }
      });
popupMenu.add( menuItem1);
popupMenu.show(tree,me.getX(), me.getY());
popupMenu.setVisible(true);

}

public void make_popup3(MouseEvent me) 
{JPopupMenu popupMenu = new JPopupMenu();
  JMenuItem menuItem1 = new JMenuItem("Remove Application System"); 
menuItem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup2_actionPerformed(e);
        }
      });

popupMenu.add( menuItem1 );
popupMenu.show(tree,me.getX(), me.getY());
popupMenu.setVisible(true);
}
public void make_popup5(MouseEvent me) 
{JPopupMenu popupMenu = new JPopupMenu();
  JMenuItem menuItem1 = new JMenuItem("Dereference Form Type"); 
 JMenuItem menuItem2 = new JMenuItem("View"); 
menuItem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup2_actionPerformed(e);
        }
      });
menuItem2.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
popupMenu.add( menuItem1);
popupMenu.add( menuItem2);
popupMenu.show(tree,me.getX(), me.getY());
popupMenu.setVisible(true);
}
public void make_popup7(MouseEvent me) 
{JPopupMenu popupMenu = new JPopupMenu();
 JMenuItem menuItem2 = new JMenuItem("Edit"); 
menuItem2.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup_actionPerformed(e);
        }
      });
popupMenu.add( menuItem2);
popupMenu.show(tree,me.getX(), me.getY());
popupMenu.setVisible(true);
}
public void make_popup4(MouseEvent me) 
{JPopupMenu popupMenu = new JPopupMenu();
  JMenuItem menuItem1 = new JMenuItem("Reference Form Type"); 
menuItem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup2_actionPerformed(e);
        }
      });

popupMenu.add( menuItem1 );
popupMenu.show(tree,me.getX(), me.getY());
popupMenu.setVisible(true);
}
public void make_popup6(MouseEvent me) 
{JPopupMenu popupMenu = new JPopupMenu();
  JMenuItem menuItem1 = new JMenuItem("Rename"); 
  
menuItem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup2_actionPerformed(e);
        }
      });
      
popupMenu.add( menuItem1 );
popupMenu.show(tree,me.getX(), me.getY());
tree.setSelectionInterval(0,0);
}


public void make_popup8(MouseEvent me) 
{JPopupMenu popupMenu = new JPopupMenu();
  JMenu jm = new JMenu("Generate DB Schema"); 
  JMenuItem menuItem1 = new JMenuItem("XML"); 
menuItem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup2_actionPerformed(e);
        }
      });
 jm.add( menuItem1 ) ;  
 menuItem1 = new JMenuItem("SQL DDL"); 
menuItem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup2_actionPerformed(e);
        }
      });
 jm.add( menuItem1 ) ; 
 menuItem1 = new JMenuItem("XML Schema"); 
menuItem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup2_actionPerformed(e);
        }
      });
popupMenu.add( menuItem1 ) ;  
popupMenu.add(jm );
popupMenu.show(tree,me.getX(), me.getY());
 
}
public void make_popup2(MouseEvent me) 
{
  JMenuItem menuItem1 = new JMenuItem("New"); 
    JMenuItem menuItem4 = new JMenuItem("Paste"); 
JMenuItem menuItem2 = new JMenuItem("Expand"); 
JMenuItem menuItem3 = new JMenuItem("Collapse");
// Constructor the pop-up menu 
if (owner.clipboard.isEmpty())
menuItem4.setEnabled(false);
JPopupMenu popupMenu = new JPopupMenu();
menuItem2.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup2_actionPerformed(e);
        }
      });
menuItem1.addActionListener(new ActionListener()
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
menuItem3.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          popup2_actionPerformed(e);
        }
      });
popupMenu.add( menuItem1 );
//popupMenu.add( menuItem4 ); 
 if (!tree.getSelectionPath().getLastPathComponent().toString().equals("Packages"))
   popupMenu.add( menuItem4 );
popupMenu.addSeparator();
popupMenu.add( menuItem2 ); 
popupMenu.add( menuItem3 ); 
popupMenu.validate(); 
popupMenu.show(tree,me.getX(), me.getY());
popupMenu.setVisible(true);
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

  private void btnClose_actionPerformed(ActionEvent e)
  {
  this.dispose();
  }
private void collapse(DefaultMutableTreeNode node )
{
/*
for(int i=0;i<node.getChildCount();i++)
{
  collapse((DefaultMutableTreeNode )node.getChildAt(i));
  
} */
tree.collapsePath(new TreePath(node.getPath()));
}
private void expand(DefaultMutableTreeNode node )
{
tree.expandPath(new TreePath(node.getPath()));
/*
for(int i=0;i<node.getChildCount();i++)
{
  expand((DefaultMutableTreeNode )node.getChildAt(i));
  
} */
}

  private void btnExpand_actionPerformed(ActionEvent e)
  {
  if(tree.isSelectionEmpty())
{for (int i = 0; i < tree.getRowCount(); i++) 
  tree.expandRow(i);
}
else
expand((DefaultMutableTreeNode) tree.getLastSelectedPathComponent());
  }

  private void btnCollapse_actionPerformed(ActionEvent e)
  {
 if(tree.isSelectionEmpty())
{for (int i = 0; i < tree.getRowCount(); i++) 
  tree.collapseRow(i);
}
else
collapse((DefaultMutableTreeNode) tree.getLastSelectedPathComponent());
  }

  private void btnUpp_actionPerformed(ActionEvent e)
  {
  tree.setSelectionRow(0);
  }

  private void btnUp_actionPerformed(ActionEvent e)
  {
  TreePath path=tree.getSelectionPath();
  if(path!=null)
  {
  if(tree.getRowForPath(path)>0)
  tree.setSelectionRow(tree.getRowForPath(path)-1);
  }
  else
  tree.setSelectionRow(0);
  }

  private void btnDown_actionPerformed(ActionEvent e)
  {
  TreePath path=tree.getSelectionPath();
  if(path!=null)
  {
  if(tree.getRowForPath(path)<tree.getRowCount()-1)
  tree.setSelectionRow(tree.getRowForPath(path)+1);
  }
  else
  tree.setSelectionRow(tree.getRowCount()-1);
  }

  private void btnDownn_actionPerformed(ActionEvent e)
  {
  tree.setSelectionRow(tree.getRowCount()-1);
  }

  private void popup_actionPerformed(ActionEvent e)
  {
  int i=-1;
  ResultSet rs;
  String action;
  JDBCQuery query=new JDBCQuery(con);
  JDBCQuery query4=new JDBCQuery(con);
  try { 
  if(e.getActionCommand().toString().equals(""))action=((JButton)e.getSource()).getToolTipText();
  else
  action=e.getActionCommand().toString();  
  
   if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Owned") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Referenced"))
 {rs=query.select("select * from IISC_FORM_TYPE where TF_mnem='" + tree.getSelectionPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
 if(rs.next())
  i=rs.getInt(1);
  query.Close();
  }
 if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Attributes"))
 {rs=query.select("select * from IISC_Attribute where Att_mnem='" + tree.getSelectionPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
 if(rs.next())
  i=rs.getInt(1);
  query.Close();
  }
  if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Inclusion Dependencies"))
 {rs=query.select("select * from IISC_INCLUSION_DEPENDENCY where ID_name='" + tree.getSelectionPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
 if(rs.next())
  i=rs.getInt(1);
  query.Close();
  }
   if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Relation Schemes"))
 {rs=query.select("select * from IISC_RELATION_SCHEME where RS_name='" + tree.getSelectionPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
 if(rs.next())
  i=rs.getInt(1);
  query.Close();
  }
  
  if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Join Dependencies"))
  {
        rs=query.select("select JD_id from IISC_JOIN_DEPENDENCY,IISC_APP_SYSTEM where IISC_JOIN_DEPENDENCY.AS_id=IISC_APP_SYSTEM.AS_id and JD_name='" + tree.getSelectionPath().getLastPathComponent().toString() + "'  and IISC_JOIN_DEPENDENCY.PR_id="+ID+ " and AS_name='" + tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString() + "'");
        if(rs.next())
        i=rs.getInt(1);
        query.Close();
  } 
      //jovo+
      // editovanje funk. u popup meniju na ime funkcije
      if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Functions"))
      {
          /*rs=query.select("select * from IISC_Function where Fun_name='" + tree.getSelectionPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
          
          if(rs.next())
              i=rs.getInt(1);
          
          query.Close();
          */
            ResultSet rs1;
            JDBCQuery query2=new JDBCQuery(con); 
            
            //System.out.println("select * from IISC_Function where Fun_name='" + tree.getSelectionPath().getLastPathComponent().toString().split(" ")[0] + "'  and PR_id="+ID); 
            rs=query.select("select * from IISC_Function where Fun_name='" + tree.getSelectionPath().getLastPathComponent().toString().split(" ")[0] + "'  and PR_id="+ID);
      
            i=-1;
            
            while(rs.next())
            {
                  String funnamecmb = rs.getString("Fun_name");
                  int Fun_id = rs.getInt("Fun_id");//getString("Fun_id");

                  rs1=query2.select("select F.*,D.Dom_mnem from IISC_FUN_PARAM F,IISC_DOMAIN D where F.Dom_id = D.Dom_id AND F.Fun_id = "+Fun_id+" order by F.Param_seq");
                    
                  funnamecmb = funnamecmb + " ( ";
                  int itemp = 1;
                    
                  while(rs1.next())
                  {
                      String domtemp= rs1.getString("Dom_mnem");
                      funnamecmb = funnamecmb + domtemp + " , ";  
                      itemp = 2;
                  }
                    
                  funnamecmb = funnamecmb.substring(0,funnamecmb.length()-itemp) + " )";
                  
                  if( tree.getSelectionPath().getLastPathComponent().toString().equals(funnamecmb) ) 
                  {
                      i=Fun_id;
                      break;
                  }
                  query2.Close();
            }  
            query.Close();      
      }
      
      if(tree.getSelectionPath().getParentPath().getParentPath()!=null && tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Packages")){
            
                int type = 0;
            if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("DB Server"))
                type = 1;
            else if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Server"))
                type = 2;
            else if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Client"))
                type = 3;              

          i=-1;          
          //System.out.println("select * from IISC_Function where Fun_name='" + tree.getSelectionPath().getLastPathComponent().toString().split(" ")[0] + "'  and PR_id="+ID); 
          rs=query.select("select * from IISC_Package where Pack_name='" + tree.getSelectionPath().getLastPathComponent().toString().split(" ")[0] + "'  and PR_id=" + ID + " and pack_type = " + type);
          
          while(rs.next()){
            i = rs.getInt("Pack_id");
          }
          query.Close(); 
      }
      //JOVO+ end
      
/*  
 if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Functions"))
 {rs=query.select("select * from IISC_Function where Fun_name='" + tree.getSelectionPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
 if(rs.next())
  i=rs.getInt(1);
  query.Close();
  }
  */
 if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("User defined domains"))
  {rs=query.select("select * from IISC_Domain where Dom_mnem='" + tree.getSelectionPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
  if(rs.next())
  i=rs.getInt(1);
  query.Close();
  }
  if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Primitive domains"))
  {rs=query.select("select * from IISC_PRIMITIVE_TYPE where PT_mnemonic='" + tree.getSelectionPath().getLastPathComponent().toString() + "'");
  if(rs.next())
  i=rs.getInt(1);
  query.Close();
  }
  if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Types"))
  {rs=query.select("select * from IISC_App_system_type where AS_type='" + tree.getSelectionPath().getLastPathComponent().toString().trim() + "'");
  if(rs.next())
  i=rs.getInt(1);
  query.Close();
  }
  if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems"))
  {rs=query.select("select * from IISC_App_system where AS_name='" + tree.getSelectionPath().getLastPathComponent().toString().trim() + "'  and PR_id="+ID);
  if(rs.next())
  i=rs.getInt(1);
  query.Close();
  }

  boolean can=true;
  
  if (action.equals("DB Schema Design") && tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems"))
  { DBSchemeDesign dbsys=new DBSchemeDesign((IISFrameMain)owner,this.getTitle(),false,con,this);
    Settings.Center(dbsys);
     WindowsManager.add(dbsys);
    dbsys.setVisible(true);
 }
  if (action.equals("DB Schema Analysis") && tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems"))
  { DBSchemaAnalysis dbsys=new DBSchemaAnalysis((IISFrameMain)owner,this.getTitle(),false,con,this);
    Settings.Center(dbsys);
    WindowsManager.add(dbsys);
    dbsys.setVisible(true);
 }
   if (action.equals("DB Schema to XML Document")&& ( tree.getSelectionPath().getLastPathComponent().toString().equals("Relation Schemes")|| tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems")))
  { XMLViewer xmlv=new XMLViewer((IISFrameMain)owner,"Export DB Schema to XML Document",false,con,"xml");
    Settings.Center(xmlv);
    WindowsManager.add(xmlv);
    xmlv.setVisible(true);
 }
 if (action.equals("XML Schema")&&  (tree.getSelectionPath().getLastPathComponent().toString().equals("Relation Schemes")|| tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems")))
  { XMLViewer xmlv=new XMLViewer((IISFrameMain)owner,"DB XML Schema",false,con,"xsd");
    Settings.Center(xmlv);
    WindowsManager.add(xmlv);
    xmlv.setVisible(true);
 } 
 if (action.equals("XML")&&  (tree.getSelectionPath().getLastPathComponent().toString().equals("Relation Schemes")|| tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems")))
  { XMLViewer xmlv=new XMLViewer(owner,"Export to XML",false,con,"xml");
    Settings.Center(xmlv);
    WindowsManager.add(xmlv);
    xmlv.setVisible(true);
  } 
 //Slavica
  if (action.equals("SQL DDL")&& ( tree.getSelectionPath().getLastPathComponent().toString().equals("Relation Schemes")|| tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems")))
  { GenerateSQL Gsql=new GenerateSQL((IISFrameMain)owner,"Export DB Schema to SQL DDL",false, con,this);  
    Settings.Center(Gsql);
    WindowsManager.add(Gsql);
    Gsql.setVisible(true);
 } 
 //Slavica
  if (action.equals("Generate Application") && tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems"))
  { 
      GenerateApp gapp=new GenerateApp((IISFrameMain)owner,"Generate Application",false, con,i,this);
      Settings.Center(gapp);
      WindowsManager.add(gapp);
      gapp.setVisible(true);
  } 
  if (action.equals("Generate Application")&& tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Owned"))
  { 
      GenerateApp gapp=new GenerateApp((IISFrameMain)owner,"Generate Application",false, con,-1,this,i);
      Settings.Center(gapp);
      WindowsManager.add(gapp);
      gapp.setVisible(true);
  } 
 String andstr="";
  if(action.equals("Delete") || action.equals("Cut") )
    {ResultSet rs1,rs2;
    
    if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Attributes"))
    { query=new JDBCQuery(con);
      int mnem;
      rs1=query.select("select * from IISC_Derived_Attribute where Att_id=" +  i  );
      if(rs1.next())
      can=false;
        query.Close();
      rs1=query.select("select * from IISC_Att_Tob where Att_id=" +  i );
      if(rs1.next())
      can=false;
        query.Close();
       } 
      if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("User defined domains"))
    { query=new JDBCQuery(con);
      int mnem;
      rs1=query.select("select * from IISC_Domain where Dom_parent=" +  i );
      if(rs1.next())
      can=false;
        query.Close();
      rs1=query.select("select * from IISC_Attribute where Dom_id=" +  i );
      if(rs1.next())
      can=false;
        query.Close();
       } 
      if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Primitive domains"))
    { query=new JDBCQuery(con);
      int mnem;
      rs1=query.select("select * from IISC_Domain where Dom_data_type=" +  i );
      if(rs1.next())
      can=false;
        query.Close();
       } 
       
     /*if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Functions"))
    { query=new JDBCQuery(con);
      int mnem;
      rs1=query.select("select * from IISC_DER_ATT_FUN where Fun_id=" +  i + " ");
      if(rs1.next())
      can=false;
        query.Close();
       } */
       
      //jovo+ 
      // provjera za brisanje
      if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Functions"))
      {
         query=new JDBCQuery(con);
         int mnem;
         
         rs1=query.select("select * from IISC_DER_ATT_FUN where Fun_id=" +  i + " ");
         if(rs1.next()){
           can=false;
            andstr="<br>Delete form DER_ATT_FUN first.";
         }
         
         query.Close();
       
         rs1=query.select("select * from IISC_ATTRIBUTE  where Fun_id="+ i +"");
         
         if(rs1.next()){
             can=false;
             andstr="<br>Delete form Attributes first.";
         }

         query.Close();
         
         rs1=query.select("select * from IISC_PACKAGE,IISC_PACK_FUN  where IISC_PACKAGE.PAck_id = IISC_PACK_FUN.Pack_id and IISC_PACKAGE.PR_ID = "+ID+" and  Fun_id="+ i + " ");
         if(rs1.next()){
             can=false;
             andstr="<br>Delete form Packages first.";
         }
         
         query.Close();           
      }
      //jovo+ end       
       
   if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Types"))
    { query=new JDBCQuery(con);
      int mnem;
      rs1=query.select("select * from IISC_App_System where AS_type_id="  +  i );
      if(rs1.next())
      {can=false;
     
      }
       query.Close();
       } 
    if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems"))
    { query=new JDBCQuery(con);
      int mnem;
      rs1=query.select("select * from IISC_TF_AppSys where AS_id="  +  i );
      if(rs1.next())
      {
       
       andstr="<br>Delete form types first.";}
       query.Close();
       } 
    if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Owned") && action.equals("Delete"))
    { query=new JDBCQuery(con);
      int mnem;
      rs1=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TF_id="  +  i );
      if(rs1.next())
      {
        // can=false;
      }
       query.Close();
       } 
    }
   if(!can)
   JOptionPane.showMessageDialog(null,"<html><center>"+action+ " is not allowed!"+ andstr, "IIS*Case", JOptionPane.INFORMATION_MESSAGE);
  else
  {
  if(action.equals("New"))
  {
    if (tree.getSelectionPath().getLastPathComponent().toString().equals("Owned") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Owned"))
      { String str=new String();
        if(tree.getSelectionPath().getLastPathComponent().toString().equals("Owned"))str=tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString();
        if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Owned"))str=tree.getSelectionPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString();
        Form form=new Form(owner,this.getTitle(),false,con,-1,this,str);
        Settings.Center(form);
         WindowsManager.add(form);
        form.setVisible(true);
      }
      /*
    if (tree.getSelectionPath().getLastPathComponent().toString().equals("Functions") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Functions"))
      { Function fun=new Function(owner,this.getTitle(),false,con,-1,this);
        Settings.Center(fun);
        WindowsManager.add(fun);
        fun.setVisible(true);
      }
      
      */
      
       //jovo+  
       if (tree.getSelectionPath().getLastPathComponent().toString().equals("Join Dependencies") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Join Dependencies"))
         { String str=new String();
           if(tree.getSelectionPath().getLastPathComponent().toString().equals("Join Dependencies"))str=tree.getSelectionPath().getParentPath().getLastPathComponent().toString();
           if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Join Dependencies"))str=tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString();
            JoinDep join=new JoinDep(owner,this.getTitle(),-1,false,con,this,str);
            Settings.Center(join);
            WindowsManager.add(join);
            join.setVisible(true);
         }
       if (tree.getSelectionPath().getLastPathComponent().toString().equals("Packages") || (tree.getSelectionPath().getParentPath()!=null && tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Packages") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Packages")))
       { 
           Packages pack=new Packages(owner,this.getTitle(),false,con,-1,this);       
            if( tree.getSelectionPath().getLastPathComponent().toString().equals("DB Server") || 
                tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("DB Server"))
                pack.setType(0);
            if( tree.getSelectionPath().getLastPathComponent().toString().equals("Application Server") || 
                tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Server"))
                pack.setType(1);
            if( tree.getSelectionPath().getLastPathComponent().toString().equals("Client") || 
                tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Client"))
                pack.setType(2);
                
             Settings.Center(pack);
             WindowsManager.add(pack);
             pack.setVisible(true);
       }    
       // menu popup na function 
         // akcija NEW
       if (tree.getSelectionPath().getLastPathComponent().toString().equals("Functions") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Functions"))
       { 
             Function fun=new Function(owner,this.getTitle(),true,con,-1,this);
             Settings.Center(fun);
             WindowsManager.add(fun);
             fun.setVisible(true);
       }
       //JOVO+ end      
      
   if (tree.getSelectionPath().getLastPathComponent().toString().equals("User defined domains") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("User defined domains"))
     {owner.dom=new Domain(owner,this.getTitle(),false,con,-1,this);
      Settings.Center(owner.dom);
      WindowsManager.add(owner.dom);
      owner.dom.setVisible(true);
     }
     if (tree.getSelectionPath().getLastPathComponent().toString().equals("Primitive domains") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Primitive domains"))
     {PrimitiveTypes pt=new PrimitiveTypes(owner,this.getTitle(),-1,false,con,this);
      Settings.Center(pt);
      WindowsManager.add(pt);
      pt.setVisible(true);
     }
   if (tree.getSelectionPath().getLastPathComponent().toString().equals("Attributes") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Attributes"))
     {Attribute att=new Attribute(owner,this.getTitle(),false,con,-1,this);
     Settings.Center(att);
     WindowsManager.add(att);
     att.setVisible(true);
     }
      if (tree.getSelectionPath().getLastPathComponent().toString().equals("Inclusion Dependencies") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Inclusion Dependencies"))
     {InclusionDep dep=new InclusionDep(owner,this.getTitle(),-1,false,con,this);
     Settings.Center(dep);
     WindowsManager.add(dep);
     dep.setVisible(true);
     }
    if (tree.getSelectionPath().getLastPathComponent().toString().equals("Application Types") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Types"))
      { AppType apptype=new AppType(owner,this.getTitle(),false,con,-1,this);
        Settings.Center(apptype);
        WindowsManager.add(apptype);
        apptype.setVisible(true);
      }
    if (tree.getSelectionPath().getLastPathComponent().toString().equals("Application Systems") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems"))
      { AppSys appsys=new AppSys(owner,this.getTitle(),false,con,-1,this);
        Settings.Center(appsys);
        WindowsManager.add(appsys);
        appsys.setVisible(true);
      }
  }
     if (action.equals("XML Document")&&  tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Owned"))
  {  
  int pr[]=new int[1];
  pr[0]=i;
  if(tree.getSelectionPaths().length>1)
  {
  TreePath path[]=tree.getSelectionPaths();
    pr=new int[path.length];
    for(i=0;i<path.length;i++)
    {rs=query.select("select * from IISC_FORM_TYPE where Tf_mnem='" + path[i].getLastPathComponent().toString() + "'  and PR_id="+ ID);
    if(rs.next())
      pr[i]=rs.getInt(1);
    query.Close();}
  }
   TFXMLViewer tf=new TFXMLViewer(owner,pr,con,"xml",TFXMLViewer.FORM_TYPE_SPEC);

  Settings.Center(tf);
  tf.setVisible(true);
 }
    if (action.equals("Form Types to XML Document")&&  tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems"))
  {
  int pr[]=new int[1];
  pr[0]=i;
 // TFXMLViewer tf=new TFXMLViewer(owner,pr,con,"xml",TFXMLViewer.APP_SYS_SPEC);
    SelectFormTypes tf=new SelectFormTypes(owner,i,con);
  Settings.Center(tf);
  tf.setVisible(true);
 }
  if(action.equals("View") || action.equals("Edit"))
  {
      if (tree.getSelectionPath().getLastPathComponent().toString().equals("Application generatio reports") || tree.getSelectionPath().getLastPathComponent().toString().equals("DB Schema collision reports") ||  tree.getSelectionPath().getLastPathComponent().toString().equals("DB Schema Design reports")  || tree.getSelectionPath().getLastPathComponent().toString().equals("Repository reports")  || tree.getSelectionPath().getLastPathComponent().toString().equals("Reports ") || tree.getSelectionPath().getLastPathComponent().toString().equals("Reports  "))
  {int as=-1; 
 
  if (tree.getSelectionPath().getLastPathComponent().toString().equals("Application generation reports") || tree.getSelectionPath().getLastPathComponent().toString().equals("DB Schema collision reports") ||  tree.getSelectionPath().getLastPathComponent().toString().equals("DB Schema Design reports")|| tree.getSelectionPath().getLastPathComponent().toString().equals("Repository reports")){
   rs=query.select("select AS_id from IISC_APP_SYSTEM where AS_name='" +tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
 if(rs.next())
  as=rs.getInt(1);
  query.Close();
  }
  Collision rep=new Collision(owner,"",false,con,as,this);
  Settings.Center(rep);
  String q="";
   if (tree.getSelectionPath().getLastPathComponent().toString().equals("DB Schema collision reports"))
   q= " and CLT_id<10 ";
   else if(tree.getSelectionPath().getLastPathComponent().toString().equals("DB Schema Design reports"))
   q= " and CLT_id>=10 and CLT_id<20 ";
   else if(tree.getSelectionPath().getLastPathComponent().toString().equals("Repository reports"))
   q= " and CLT_id>=20 and CLT_id<30 ";
   else if(tree.getSelectionPath().getLastPathComponent().toString().equals("Reports "))
   q= " and CLT_id>=30 and CLT_id<40 and PR_id="+ ID+ " ";
   else if(tree.getSelectionPath().getLastPathComponent().toString().equals("Reports  "))
   q= " and CLT_id>=40 and CLT_id<50 and PR_id="+ ID+ " ";

   else if(tree.getSelectionPath().getLastPathComponent().toString().equals("Reports  "))
   q= " and CLT_id>=50 and CLT_id<60 and PR_id="+ ID+ " ";
  rep.filter(q);
  rep.setVisible(true); 
  }
 
   if (tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application generation reports") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("DB Schema collision reports") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("DB Schema Design reports") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Repository reports") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Reports ") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Reports  "))
  { 
  if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Reports "))
   rs=query.select("select AS_id from IISC_APP_SYSTEM where AS_name='" +tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
  else if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Reports  " ))
   rs=query.select("select AS_id from IISC_APP_SYSTEM where AS_name='" +tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
 else
  rs=query.select("select AS_id from IISC_APP_SYSTEM where AS_name='" +tree.getSelectionPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString() + "'  and PR_id="+ID+" ");

  int as=-1;
  if(rs.next())
  as=rs.getInt(1);
  query.Close();
  Collision rep=new Collision(owner,"",false,con,as,this);
  Settings.Center(rep);
  rep.cmbReportType.setSelectedItem(tree.getSelectionPath().getLastPathComponent().toString());
  String q ="and PR_id="+ ID+ " ";
   rep.filter(q);
   rep.setVisible(true); 
  }
  
 if (tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Application generation reports") || tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("DB Schema collision reports") || tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("DB Schema Design reports")  ||  tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Repository reports"))
   { 
     rs=query.select("select * from IISC_COLLISION_LOG,IISC_LOG_TYPE,IISC_APP_SYSTEM where IISC_COLLISION_LOG.CL_type=IISC_LOG_TYPE.CLT_id and IISC_COLLISION_LOG.AS_id=IISC_APP_SYSTEM.AS_id and AS_name='" + tree.getSelectionPath().getParentPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString() + "' and CLT_name='" + tree.getSelectionPath().getParentPath().getLastPathComponent().toString() + "' and IISC_APP_SYSTEM.PR_id="+ID+" and CL_date='" + tree.getSelectionPath().getLastPathComponent().toString() + "'");

 if(rs.next())
  i=rs.getInt(1);
  if(tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Repository reports"))
  {
  RepReport rep=new RepReport(owner,"",false,con,i,this);
  Settings.Center(rep);
  WindowsManager.add(rep);
  rep.setVisible(true);
  }
  else
  {
  Report rep=new Report(owner,"",false,con,i);
  Settings.Center(rep);
  rep.setVisible(true);
  query.Close();}
  }
    if ( tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Reports ") ||  tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Reports  "))
  { 
  if(tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().trim().equals("Reports"))
      rs=query.select("select * from IISC_COLLISION_LOG,IISC_LOG_TYPE where IISC_COLLISION_LOG.CL_type=IISC_LOG_TYPE.CLT_id and CLT_name='" + tree.getSelectionPath().getParentPath().getLastPathComponent().toString() + "' and PR_id="+ID+" and CL_date='" + tree.getSelectionPath().getLastPathComponent().toString() + "'");
  else
   rs=query.select("select * from IISC_COLLISION_LOG,IISC_LOG_TYPE,IISC_APP_SYSTEM where IISC_COLLISION_LOG.CL_type=IISC_LOG_TYPE.CLT_id and IISC_COLLISION_LOG.AS_id=IISC_APP_SYSTEM.AS_id and AS_name='" + tree.getSelectionPath().getParentPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString() + "' and CLT_name='" + tree.getSelectionPath().getParentPath().getLastPathComponent().toString() + "' and IISC_APP_SYSTEM.PR_id="+ID+" and CL_date='" + tree.getSelectionPath().getLastPathComponent().toString() + "'");
 if(rs.next())
  i=rs.getInt(1);
  RepReport rep=new RepReport(owner,"",false,con,i,this);
  Settings.Center(rep);
  WindowsManager.add(rep);
  rep.setVisible(true);
  } 
  }
   if(action.equals("Edit")||action.equals("View"))
  {
  
     if (tree.getSelectionPath().getLastPathComponent().toString().equals("Owned") )
      { String str=new String();
        if(tree.getSelectionPath().getLastPathComponent().toString().equals("Owned"))str=tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString();
        if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Owned"))str=tree.getSelectionPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString();
        Form form=new Form(owner,this.getTitle(),false,con,-1,this,str);
        Settings.Center(form);
        WindowsManager.add(form);
        form.setVisible(true);
      }
      if ( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Owned") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Referenced"))
      { String str=new String();
      TreePath strpath=tree.getSelectionPath();
        if(tree.getSelectionPath().getLastPathComponent().toString().equals("Owned") || tree.getSelectionPath().getLastPathComponent().toString().equals("Referenced"))
        {str=tree.getSelectionPath().getParentPath().getLastPathComponent().toString();
        }
       
        if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Owned"))str=tree.getSelectionPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString();
        else if (tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Referenced"))
       {
           rs=query.select("select * from IISC_APP_SYSTEM,IISC_TF_APPSYS,IISC_FORM_TYPE  where IISC_APP_SYSTEM.AS_id=IISC_TF_APPSYS.AS_id and IISC_FORM_TYPE.PR_id=" + ID + " and IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and  IISC_FORM_TYPE.TF_id="+i);
          if(rs.next())
          str=rs.getString("AS_name");
          query.Close();
       }
        Form form=new Form(owner,this.getTitle(),false,con,i,this,str);
        Settings.Center(form);
        if(strpath.getParentPath().getLastPathComponent().toString().equals("Referenced"))
       { 
        form.readonly();
        select_node(strpath.getLastPathComponent().toString(),"Referenced","Form Types",strpath.getParentPath().getParentPath().getParentPath().getLastPathComponent().toString() );
        }
        else
        select_node(strpath.getLastPathComponent().toString(),"Owned","Form Types",str);
        form.setVisible(true);
        
      }/*
   if (tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Functions"))
    { Function fun=new Function(owner,this.getTitle(),false,con,i,this);
      Settings.Center(fun);
      WindowsManager.add(fun);
      fun.setVisible(true);
    }*/
    
       //jovo+
       //editovanje na ime funkcije i otvaranje frejma za editovanje funcije
       if (tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Functions"))
       { 
             Function fun=new Function(owner,this.getTitle(),true,con,i,this);
             Settings.Center(fun);
             WindowsManager.add(fun);
             fun.setVisible(true);
       }
       
      if (tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Packages"))
      { 
          Packages pack=new Packages(owner,this.getTitle(),false,con,i,this);
          Settings.Center(pack);
          WindowsManager.add(pack);
          pack.setVisible(true);//ojha
      }       
       //jovo+ end    
    
    if (tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Relation Schemes"))
    { RScheme rsh=new RScheme(owner,this.getTitle(),false,con,i,this,tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString());
      Settings.Center(rsh);
      WindowsManager.add(rsh);
      rsh.setVisible(true);
    }
  if (tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Join Dependencies"))
  { 
      JoinDep join=new JoinDep(owner,this.getTitle(),i,false,con,this,tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString());
      Settings.Center(join);
      WindowsManager.add(join);
      join.setVisible(true);
  }
    if (tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("User defined domains"))
  {owner.dom=new Domain(owner,this.getTitle(),false,con,i,this);
  Settings.Center(owner.dom);
  WindowsManager.add(owner.dom);
  owner.dom.setVisible(true);
  }
   if (tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Primitive domains"))
  {PrimitiveTypes pt=new PrimitiveTypes(owner,this.getTitle(),i,false,con,this);
  Settings.Center(pt);
  WindowsManager.add(pt);
  pt.setVisible(true);
  }
   if (tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Attributes"))
  {Attribute att=new Attribute(owner,this.getTitle(),false,con,i,this);
  Settings.Center(att);
  WindowsManager.add(att);
  att.setVisible(true);
  }
    if (tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Inclusion Dependencies"))
  {InclusionDep dep=new InclusionDep(owner,this.getTitle(),i,false,con,this);
     Settings.Center(dep);
     WindowsManager.add(dep);
     dep.setVisible(true);
  }
    if (tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Types"))
    { AppType apptype=new AppType(owner,this.getTitle(),false,con,i,this);
      Settings.Center(apptype);
      WindowsManager.add(apptype);
       apptype.setVisible(true);
    }
     if (tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems"))
    { AppSys appsys=new AppSys(owner,this.getTitle(),false,con,i,this);
      Settings.Center(appsys);
      WindowsManager.add(appsys);
       appsys.setVisible(true);
    }
    
  }
   if(action.equals("Copy") || action.equals("Cut") )
    {
    owner.clipboard=new Clipboard(ID,i, tree.getSelectionPath().getParentPath().getLastPathComponent().toString(),con,action,tree.getSelectionPath());
    }
    if(action.equals("Paste"))
    {if(ID==owner.clipboard.pid && con==owner.clipboard.con)
    {
    ResultSet rs1,rs2, rs4;
    //System.out.println("OWNER TABLE:" + owner.clipboard.elem.getParentPath().getLastPathComponent().toString());
    if(owner.clipboard.table.equals( tree.getSelectionPath().getLastPathComponent().toString()) || owner.clipboard.table.equals(tree.getSelectionPath().getParentPath().getLastPathComponent().toString())
        || owner.clipboard.elem.getParentPath().getParentPath().getLastPathComponent().toString().equals("Packages")
        || owner.clipboard.elem.getParentPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Packages"))
    {    
     if(owner.clipboard.table.equals("Owned") && owner.clipboard.pid==ID )
    { int as=-1;
      String appsys;
      if(owner.clipboard.table.equals( tree.getSelectionPath().getLastPathComponent().toString()))
      appsys=tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString();
      else
      appsys=tree.getSelectionPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString();
      query=new JDBCQuery(owner.clipboard.con);
     JDBCQuery  query2=new JDBCQuery(owner.clipboard.con);
     JDBCQuery  query1=new JDBCQuery(owner.clipboard.con);
     JDBCQuery  query3=new JDBCQuery(owner.clipboard.con);
      String mnem;
      boolean can1=false;
       rs1=query.select("select * from IISC_APP_System where AS_name='" + appsys + "'");
       if(rs1.next())as=rs1.getInt(1);
       query.Close();
      rs1=query.select("select * from IISC_FORM_TYPE where TF_id=" + owner.clipboard.id + "");
       if(rs1.next())
      { query=new JDBCQuery(con);
        mnem=rs1.getString(3);
        if(owner.clipboard.type.equals("Copy"))
        {while(true)
         {rs2=query.select("select * from  IISC_FORM_TYPE where TF_mnem='" + mnem + "' and PR_id="+ID);
         if(rs2.next())
         mnem="Copy of " + mnem;
         else
         break;}
          query.Close();
        }
        else
        {
       can1=true;
        }
       
        //query=new JDBCQuery(owner.clipboard.con);
        if(can1){ query.update("update IISC_TF_APPSYS set AS_id="+ as +" where TF_id=" + owner.clipboard.id + "");
        }
        else
        {
        query=new JDBCQuery(con);
        rs2=query.select("select max(TF_id)+1  from IISC_FORM_TYPE");
        int ida=0;
        if(rs2.next())
        ida=rs2.getInt(1);
        query.Close();
        query.update("insert into IISC_FORM_TYPE(Tf_id,PR_ID,Tf_mnem,Tf_title,Tf_crdate,Tf_freq,Tf_freq_unit,Tf_moddate,Tf_rest,Tf_rest_unit,Tf_use,Tf_comment) values(" + ida + "," + owner.clipboard.pid + ",'" + mnem +  "','" + rs1.getString(4) + "','" +ODBCList.now() + "','" + rs1.getString(6) + "','" + rs1.getString(7) + "','" + ODBCList.now() + "','" + rs1.getString(9) + "','" + rs1.getString(10) + "'," + rs1.getString(11) + ",'" + rs1.getString(12) + "')");
        query.update("insert into IISC_TF_APPSYS(Tf_id,AS_id,PR_id) values(" + ida + "," + as + "," + owner.clipboard.pid + ")");
         rs2=query2.select("select *  from IISC_COMPONENT_TYPE_OBJECT_TYPE where TF_id="+owner.clipboard.id+ " and PR_id="+owner.clipboard.pid);
       Set OB=new HashSet();
        while(rs2.next())
        { String superord=rs2.getString(6);
          if(superord==null)superord="NULL";
          int ido=rs2.getInt("Tob_id");
          
          rs=query.select("select max(TOB_id)+1  from IISC_COMPONENT_TYPE_OBJECT_TYPE ");
          int idoo=0;
          if(rs.next())
          idoo=rs.getInt(1);
          query.Close();
          int[] OB1={ido,idoo}; 
          OB.add(OB1);
          Iterator it=OB.iterator();
          while(it.hasNext())
          {
            int[] sup=(int[])it.next();
            if((""+sup[0]).equals(superord))
            {superord=""+sup[1];
            break;
            }
          }
          String strsql="insert into IISC_COMPONENT_TYPE_OBJECT_TYPE(Tob_id,PR_id,Tf_id,Tob_mnem,Tob_name,Tob_superord,Tob_mandatory,Tob_queallow,Tob_insallow,Tob_updallow,Tob_deleteallow,Tob_check) values(" + idoo + "," + ID + "," + ida + ",'" + rs2.getString(4) + "','" + rs2.getString(5) + "'," + superord + "," + rs2.getString(7) + ",'" + rs2.getString(8) + "','" + rs2.getString(9) + "','" + rs2.getString(10) + "','" + rs2.getString(11) + "','" + rs2.getString(12) + "')";
         query.update(strsql); 
         rs1=query1.select("select *  from IISC_ATT_TOB where TF_id="+owner.clipboard.id+ " and TOB_id="+ ido + " and PR_id="+owner.clipboard.pid);
        while(rs1.next())
        { String fun= rs1.getString(14);
        if(fun==null)fun="NULL";
         query.update("insert into IISC_ATT_TOB(Att_id,Tf_id,Tob_id,PR_id,W_order,W_tittle,W_mand,W_queallow,W_insallow,W_updallow,W_nullallow,W_behav,W_default,Fun_id) values(" + rs1.getString(1)  + "," + ida + "," + idoo + "," + ID + "," + rs1.getString(5) + ",'" + rs1.getString(6) + "','" + rs1.getString(7) + "','" + rs1.getString(8) + "','" + rs1.getString(9) + "','" + rs1.getString(10) + "','" + rs1.getString(11) + "'," + rs1.getString(12) + ",'" + rs1.getString(13) + "'," +fun + ")"); 
        }
        query1.Close();
        rs1=query1.select("select *  from IISC_KEY_TOB where TF_id="+owner.clipboard.id+ " and TOB_id="+ ido + " and PR_id="+owner.clipboard.pid);
        while(rs1.next())
        { 
         query.update("insert into IISC_KEY_TOB(Tf_id, Tob_id,Tob_rbrk,PR_id,Tob_local) values(" + ida + "," + idoo + "," + rs1.getString(3)  + "," + rs1.getString(4) + "," + ID + ")"); 
        }
        query1.Close();
        rs1=query1.select("select *  from IISC_UNIQUE_TOB where TF_id="+owner.clipboard.id+ " and TOB_id="+ ido + " and PR_id="+owner.clipboard.pid);
        while(rs1.next())
        { 
         query.update("insert into IISC_UNIQUE_TOB(Tf_id, Tob_id,Tob_rbrk,PR_id) values(" + ida + "," + idoo + "," + rs1.getString(3)  + "," + ID + ")"); 
        }
        query1.Close();
        rs1=query1.select("select *  from IISC_ATT_KTO where TF_id="+owner.clipboard.id+ " and TOB_id="+ ido + " and PR_id="+owner.clipboard.pid);
        while(rs1.next())
        { 
         query.update("insert into IISC_ATT_KTO(Att_id,Tf_id, Tob_id,Tob_rbrk,PR_id,Att_rbrk) values(" + rs1.getString(1) + "," + ida + "," + idoo + "," + rs1.getString(4)  + "," + ID + "," + rs1.getString(6) + ")"); 
        }
        query1.Close();
        rs1=query1.select("select *  from IISC_ATT_UTO where TF_id="+owner.clipboard.id+ " and TOB_id="+ ido + " and PR_id="+owner.clipboard.pid);
        while(rs1.next())
        { 
         query.update("insert into IISC_ATT_UTO(Att_id,Tf_id, Tob_id,Tob_rbrk,PR_id,Att_rbrk) values(" + rs1.getString(1) + "," + ida + "," + idoo + "," + rs1.getString(4)  + "," + ID + "," + rs1.getString(6) + ")"); 
        }
        query1.Close();
        }
        query2.Close();
        
       
        }
        insert(mnem,"Owned","Form Types",appsys);
        select_node(mnem,"Owned","Form Types",appsys);
      }
    }
    
    
    if(owner.clipboard.table.equals("Attributes"))
    { 
      query=new JDBCQuery(owner.clipboard.con);
      String mnem;
      boolean can1=false;
      rs1=query.select("select * from IISC_Attribute where Att_id=" + owner.clipboard.id + "");
       if(rs1.next())
      { query=new JDBCQuery(con);
        mnem=rs1.getString(3);
        if(owner.clipboard.type.equals("Copy"))
        {while(true)
         {rs2=query.select("select * from IISC_Attribute where Att_mnem='" + mnem + "' and PR_id="+ID);
         if(rs2.next())
         mnem="Copy of " + mnem;
         else
         break;}
        }
        else
        {
       can1=true;
        }
        query.Close();
        query4=new JDBCQuery(con);
        rs2=query4.select("select max(Att_id)+1  from IISC_Attribute");
        int ida=0;
        if(rs2.next())
        ida=rs2.getInt(1);
        query4.Close();
        String ff=rs1.getString(5);
        if(ff==null)ff="NULL";
        query.update("insert into IISC_Attribute(Att_id, PR_id, Att_mnem,Dom_id, Fun_id,Att_name,Att_expr,Att_sbp,Att_elem,Att_der,Att_default,Att_comment) values(" + ida + "," + owner.clipboard.pid + ",'" + mnem +  "'," + rs1.getInt(4) + "," + ff + ",'" + rs1.getString(6) + "','" + rs1.getString(7) + "','" + rs1.getString(8) + "'," + rs1.getString(9) + "," + rs1.getString(10) + ",'" + rs1.getString(11) + "','" + rs1.getString(12) + "')");
        query=new JDBCQuery(owner.clipboard.con);
        if(can1){ query.update("delete from IISC_Attribute where Att_id=" + owner.clipboard.id + "");
        removenode(mnem,"Attributes");}
        insert(mnem,"Attributes");
        select_node(mnem,"Attributes");
      }
    }
    /*if(owner.clipboard.table.equals("Functions"))
    { 
      query=new JDBCQuery(owner.clipboard.con);
      String mnem;
      boolean can1=false;
      rs1=query.select("select * from IISC_Function where Fun_id=" + owner.clipboard.id + "");
      if(rs1.next())
      { query=new JDBCQuery(con);
        mnem=rs1.getString(3);
        if(owner.clipboard.type.equals("Copy"))
        {rs2=query.select("select * from IISC_Function where Fun_name='" + mnem + "' and PR_id="+ID);
        if(rs2.next())
        mnem="Copy of " + mnem;
        query.Close();
        }
        else
        {
        can1=true;
        } 
        
        query4=new JDBCQuery(con);
        rs2=query4.select("select max(Fun_id)+1  from IISC_Function");
        int ida=0;
        if(rs2.next())
        ida=rs2.getInt(1);
        query4.Close();
        query.update("insert into IISC_Function(Fun_id, PR_id, Fun_name, Fun_desc,Fun_comment) values(" + ida + "," + ID + ",'" + mnem +  "','" + rs1.getString(4) + "','" + rs1.getString(5) + "')");
        query=new JDBCQuery(owner.clipboard.con);
        if(can1){query.update("delete from IISC_Function where Fun_id=" + owner.clipboard.id + "");
        removenode(mnem,"Functions");}
        insert(mnem,"Functions");
        select_node(mnem,"Functions");
      }
    }*/
    
     //jovo+
     // paste na copy/cut akciju
     if(owner.clipboard.elem.getParentPath().getParentPath().getLastPathComponent().toString().equals("Packages") && ( owner.clipboard.table.equals("DB Server") || owner.clipboard.table.equals("Application Server") || owner.clipboard.table.equals("Client"))){ 
     
         query=new JDBCQuery(owner.clipboard.con);
         JDBCQuery query2=new JDBCQuery(owner.clipboard.con);
         String mnem;
         String copymnem ;
         boolean can1=false;
         rs1=query.select("select * from IISC_Package where Pack_id = " + owner.clipboard.id );
         
         if(rs1.next()){ 
                
                copymnem = mnem = owner.clipboard.elem.getLastPathComponent().toString();
                
             int type = 1;
             
             //System.out.println("aaaa:"+tree.getSelectionPath().getParentPath().getLastPathComponent().toString());
             //System.out.println("bbbb:"+tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString());
             if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("DB Server") || 
                tree.getSelectionPath().getLastPathComponent().toString().equals("DB Server") )
                type = 1;
             else if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Server") || 
                tree.getSelectionPath().getLastPathComponent().toString().equals("Application Server"))
                type = 2;
             else if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Client") || 
                tree.getSelectionPath().getLastPathComponent().toString().equals("Client"))
                type = 3;                
                while(true){
                    
                    rs1=query.select("select * from IISC_Package where Pack_Name = '" + mnem +"' and Pack_type = " + type);
                    
                    if(rs1.next()){
                        mnem = "Copy_of_" + mnem;
                    }
                    else
                        break;
                }
                
             query.Close();         
         
             query4=new JDBCQuery(con);
             rs2=query4.select("select max(Pack_id)+1  from IISC_Package");
             int ida=0;
             if(rs2.next())
                 ida=rs2.getInt(1);
             
             query4.Close();
             
             rs2=query4.select("select max(PF_id)+1  from IISC_PACK_FUN");
             int idp=0;
             if(rs2.next())
                 idp=rs2.getInt(1);
             
             query4.Close();
             
                
             query.update("insert into IISC_Package(Pack_id, Pack_name, pack_type, PR_id,Pack_desc,Pack_comt) "+
                           "select "+ida+",'" + mnem +"',"+type+","+ ID +",Pack_desc,Pack_comt from IISC_PACKAGE WHERE Pack_id = "+owner.clipboard.id+"");
             
             rs2=query4.select("select "+idp+","+ida+",Fun_id FROM IISC_Pack_Fun where Pack_id = "+owner.clipboard.id+"");
             
             while(rs2.next()){
                 query.update("insert into IISC_Pack_Fun(PF_id,Pack_id,Fun_id)" +
                                " values ("+ (idp++) +","+ rs2.getString(2)+","+ rs2.getString(3) + ") ");
             }
                 

             query=new JDBCQuery(owner.clipboard.con);
             
             if(owner.clipboard.type.equals("Cut"))
             {
                   query.update("delete from IISC_Pack_Fun where Pack_id=" + owner.clipboard.id + "");
                   query.update("delete from IISC_Package where Pack_id=" + owner.clipboard.id + "");
                   //System.out.println("REMOVE NODE:" + owner.clipboard.elem.getParentPath().getLastPathComponent().toString());
                   removenode(copymnem,owner.clipboard.elem.getParentPath().getLastPathComponent().toString());
             }
             
             //System.out.println("NODE:" + tmpArgs);
             //System.out.println("MNEM:" + mnem);
             //System.out.println("ADD NODE:" + tmpArgs.replaceFirst(tmpArgs.split(" ")[0]+" ",mnem+" "));
             String ulaz = "";
              if(tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Packages"))
                ulaz = tree.getSelectionPath().getParentPath().getLastPathComponent().toString();
            else if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Packages"))
                ulaz = tree.getSelectionPath().getLastPathComponent().toString();
              //System.out.println("ULAZ:" + ulaz);
             insert(mnem,ulaz,"Packages");
         }
        
     }
     if(owner.clipboard.table.equals("Functions"))
     { 
           query=new JDBCQuery(owner.clipboard.con);
           JDBCQuery query2=new JDBCQuery(owner.clipboard.con);
           String mnem;
           String copymnem ;
           boolean can1=false;
           rs1=query.select("select * from IISC_Function where Fun_id=" + owner.clipboard.id );
           
           if(rs1.next())
           { 

               String funName = rs1.getString("Fun_name");
               mnem="Copy_of_" + funName;
               copymnem = mnem;

               String tmpArgs = "";
                 rs=query2.select("select F.*,D.Dom_mnem from IISC_FUN_PARAM F,IISC_DOMAIN D where F.Dom_id = D.Dom_id AND F.Fun_id = "+owner.clipboard.id+" order by F.Param_seq");
                 
                 tmpArgs = funName + " ( ";
               int itemp1 = 1;
                 while(rs.next())
                 {
                     String domtemp= rs.getString("Dom_mnem");
                     tmpArgs = tmpArgs + domtemp + " , ";  
                     itemp1 = 2;
                 }           

               tmpArgs = tmpArgs.substring(0,tmpArgs.length() - itemp1) + " )";
               
               //query=new JDBCQuery(con);

               query.Close();         

               int postoji = 0;
               int mnembr = 1;
               while(true)
               {
                     rs2=query.select("select * from IISC_Function where Fun_name='" + mnem + "' and PR_id="+ID);                    
                     
                     postoji = 0;
                     int Fun_id = -1;
                     while(rs2.next())
                     {
                         String funnamecmb = rs2.getString("Fun_name");
                         Fun_id = rs2.getInt("Fun_id");
       
                         rs=query2.select("select F.*,D.Dom_mnem from IISC_FUN_PARAM F,IISC_DOMAIN D where F.Dom_id = D.Dom_id AND F.Fun_id = "+Fun_id+" order by F.Param_seq");
                       
                         funnamecmb = funnamecmb + " ( ";
                         int itemp = 1;
                       
                         while(rs.next())
                         {
                             String domtemp= rs.getString("Dom_mnem");
                             funnamecmb = funnamecmb + domtemp + " , ";  
                             itemp = 2;
                         }
                       
                         funnamecmb = funnamecmb.substring(0,funnamecmb.length()-itemp) + " )";
                         query2.Close();
                         //String asdsada = ;
                         if (funnamecmb.equals(tmpArgs.replaceFirst(tmpArgs.split(" ")[0]+" ",mnem+" ")))
                         {
                             postoji = 1;
                             break;
                         }
                     }
                     if (postoji == 1)
                       mnem = copymnem + "_" + (mnembr++);
                     else
                       break;
               }
                     
               query4=new JDBCQuery(con);
               rs2=query4.select("select max(Fun_id)+1  from IISC_Function");
               int ida=0;
               if(rs2.next())
                   ida=rs2.getInt(1);
               
               query4.Close();
               
               rs2=query4.select("select max(Param_id)+1  from IISC_FUN_PARAM");
               int idp=0;
               if(rs2.next())
                   idp=rs2.getInt(1);
               
               query4.Close();
               
               query.update("insert into IISC_Function(Fun_id, PR_id, Fun_name, Fun_desc,Fun_comment,Dom_id) "+
                             "select "+ida+","+ID+",'"+mnem+"',Fun_desc,Fun_comment,Dom_id from IISC_FUNCTION WHERE Fun_id = "+owner.clipboard.id+"");
                             
               query.update("insert into IISC_Fun_Param(Param_id,PR_id,Fun_id,Param_seq,Param_name,Param_def_val,Dom_id) "+
                             "select "+idp+"+Param_seq,"+ID+","+ida+",Param_seq,Param_name,Param_def_val,Dom_id FROM IISC_fun_param where Fun_id = "+owner.clipboard.id+"");              
                             
               query=new JDBCQuery(owner.clipboard.con);
               
               if(owner.clipboard.type.equals("Cut"))
               {
                     query.update("delete from IISC_Fun_param where Fun_id=" + owner.clipboard.id + "");
                     query.update("delete from IISC_Function where Fun_id=" + owner.clipboard.id + "");
                     //System.out.println("REMOVE NODE:" + tmpArgs);
                     removenode(tmpArgs,"Functions");
               }
               //System.out.println("NODE:" + tmpArgs);
               //System.out.println("MNEM:" + mnem);
               //System.out.println("ADD NODE:" + tmpArgs.replaceFirst(tmpArgs.split(" ")[0]+" ",mnem+" "));
               insert(tmpArgs.replaceFirst(tmpArgs.split(" ")[0]+" ",mnem+" "),"Functions");
               //select_node(mnem,"Functions");
           }
     }
     //jovo+ end
     
    
        if(owner.clipboard.table.equals("User defined domains"))
    { 
      query=new JDBCQuery(owner.clipboard.con);
      String mnem;
      boolean can1=false;
      rs1=query.select("select * from IISC_Domain where Dom_id=" + owner.clipboard.id + "");
      if(rs1.next())
      {query=new JDBCQuery(con);
       mnem=rs1.getString(3);
      if(owner.clipboard.type.equals("Copy"))
       {rs2=query.select("select * from IISC_Domain where Dom_mnem='" + mnem + "' and PR_id="+ID);
       if(rs2.next())
       mnem="Copy of " + mnem;
       query.Close();
        }
        else
        {
        can1=true;
        } 
        
     //    if(owner.clipboard.type.equals("Copy"))query.Close();
         query4=new JDBCQuery(con);
       rs2=query4.select("select max(Dom_id)+1  from IISC_Domain");
        int ida=0;
        if(rs2.next())
        ida=rs2.getInt(1);
        query4.Close();
       query.update("insert into IISC_Domain(Dom_id, PR_id, Dom_mnem, Dom_name,Dom_type, Dom_data_type,Dom_length,Dom_reg_exp_str,Dom_parent,Dom_decimal,Dom_default,Dom_comment) values(" + ida+ "," + owner.clipboard.pid + ",'" + mnem +  "','" + rs1.getString(4) + "'," + rs1.getInt(5) + "," + rs1.getInt(6) + "," + rs1.getInt(7) + ",'" + rs1.getString(8) + "'," + rs1.getString(9) + "," + rs1.getInt(10) + ",'" + rs1.getString(11) + "','" + rs1.getString(12) + "')");
        rs2=query4.select("select *  from IISC_DOM_ATT where DOM_id="+owner.clipboard.id);
        while(rs2.next())
         query.update("insert into IISC_DOM_ATT(Dom_id,Att_id,PR_id,Att_rbr) values(" + ida+ ","+ rs2.getInt("ATT_id")+ "," +  owner.clipboard.pid+ ","+ rs2.getInt("ATT_rbr")+ ")");
        query4.Close();
        query=new JDBCQuery(owner.clipboard.con);
      if(can1){
      query.update("delete from IISC_DOM_ATT where Dom_id=" + owner.clipboard.id + "");
      query.update("delete from IISC_Domain where Dom_id=" + owner.clipboard.id + "");
      removenode(mnem,"User defined domains");}
       insert(mnem,"User defined domains");
       select_node(mnem,"User defined domains");
      }
    }
    
    if(owner.clipboard.table.equals("Primitive domains"))
    { 
      query=new JDBCQuery(owner.clipboard.con);
      String mnem;
      boolean can1=false;
      rs1=query.select("select * from IISC_PRIMITIVE_TYPE where PT_id=" + owner.clipboard.id + "");
      if(rs1.next())
      {query=new JDBCQuery(con);
       mnem=rs1.getString(2);
      if(owner.clipboard.type.equals("Copy"))
       {rs2=query.select("select * from IISC_PRIMITIVE_TYPE where PT_mnemonic='" + mnem + "'");
       if(rs2.next())
       mnem="Copy of " + mnem;
       query.Close();
        }
        else
        {
        can1=true;
        } 
        
     //    if(owner.clipboard.type.equals("Copy"))query.Close();
         query4=new JDBCQuery(con);
       rs2=query4.select("select max(PT_id)+1  from IISC_PRIMITIVE_TYPE");
        int ida=0;
        if(rs2.next())
        ida=rs2.getInt(1);
        query4.Close();
        String len=rs1.getString(5);
        if(len.equals("") || len==null)len="NULL";
        String dec=rs1.getString(7);
        if(dec.equals("") || dec==null)dec="NULL";
       query.update("insert into IISC_PRIMITIVE_TYPE(PT_id, PT_mnemonic,PT_name,PT_length_required,PT_length,PT_def_val,PT_decimal) values(" + ida+ ",'" + mnem +  "','" + rs1.getString(3) + "'," + rs1.getInt(4) + "," + len + ",'" + rs1.getString(6) + "'," + dec + ")");
        query=new JDBCQuery(owner.clipboard.con);
      if(can1){query.update("delete from IISC_PRIMITIVE_TYPE where PT_id=" + owner.clipboard.id + "");
      removenode(mnem,"Primitive domains");}
       insert(mnem,"Primitive domains");
       select_node(mnem,"Primitive domains");
      }
    }
            if(owner.clipboard.table.equals("Application Types"))
    { 
      query=new JDBCQuery(owner.clipboard.con);
      String mnem;
      String mnemid;
      boolean can1=false;
      rs1=query.select("select * from IISC_App_system_type where AS_type_id=" + owner.clipboard.id + "");
      if(rs1.next())
      {query=new JDBCQuery(con);
       mnem=rs1.getString(2);
       if(owner.clipboard.type.equals("Copy"))
       {rs2=query.select("select * from IISC_App_system_type where AS_type='" + mnem + "'");
        if(rs2.next())
        mnem="Copy of " + mnem;
        }
        else
        {
        can1=true;
        } 
        query.Close();
        query4=new JDBCQuery(con);
       rs2=query4.select("select max(AS_type_id)+1  from IISC_App_system_type");
       rs2.next();
       int num=rs2.getInt(1);
       query4.Close();
       query.update("insert into IISC_App_system_type(AS_type_id,AS_type,AS_type_name) values(" + num + ",'" + mnem +  "','" + rs1.getString(3) + "')");
        query=new JDBCQuery(owner.clipboard.con);
      if(can1){query.update("delete from IISC_App_system_type where AS_type_id=" + owner.clipboard.id + "");
      removenode(mnem,"Application Types");}
       insert(mnem,"Application Types");
       select_node(mnem,"Application Types");
      }
    }
    
    if(owner.clipboard.table.equals("Application Systems"))
    { 
      query=new JDBCQuery(owner.clipboard.con);
      String mnem;
      String mnemid;
      boolean can1=false, cann1=false;
      rs1=query.select("select * from IISC_APP_SYSTEM where AS_id=" + owner.clipboard.id + "");
      if(rs1.next())
      {     
       query=new JDBCQuery(con);
       mnem=rs1.getString(3);
       rs2=query.select("select * from IISC_TF_appsys where AS_id=" +  owner.clipboard.id + ""  + "  and PR_id="+owner.clipboard.pid);
       if(rs2.next())cann1=true;
       query.Close();
       if(owner.clipboard.type.equals("Copy") || (owner.clipboard.type.equals("Cut") && cann1))
       {rs2=query.select("select * from IISC_APP_SYSTEM where AS_name='" + mnem + "'");
        if(rs2.next())
        mnem="Copy of " + mnem;
         query.Close();}
        if(owner.clipboard.type.equals("Cut"))
        {
        can1=true;
        }   
        query4=new JDBCQuery(con);
       rs2=query4.select("select max(AS_id)+1  from IISC_APP_SYSTEM");
       rs2.next();
       int num=rs2.getInt(1);
       query4.Close();
       query.update("insert into IISC_APP_SYSTEM(AS_id,PR_id,AS_name,AS_desc,AS_date,AS_type_id,AS_comment,AS_collision_detection) values(" + num + "," + rs1.getInt(2) + ",'" + mnem +  "','" + rs1.getString(4) + "','" + ODBCList.now() + "'," + rs1.getString(6) + ",'" + rs1.getString(7) + "'," + rs1.getString(8) + ")");
        
        query=new JDBCQuery(owner.clipboard.con);
      if(can1){
       if(!cann1)
       {
        removeAppSystem(owner.clipboard.id, owner.clipboard.pid);
        removenode(mnem,"Application Systems");
       }
       else
        JOptionPane.showMessageDialog(null, "<html><center>The application system cannot be removed from project!", "IIS*Case", JOptionPane.INFORMATION_MESSAGE);
       }
       insert(mnem,"Application Systems");
       select_node(mnem,"Application Systems");
      }
    }
    
    if(owner.clipboard.table.equals("Inclusion Dependencies"))
    { 
      query=new JDBCQuery(owner.clipboard.con);
      String mnem;
      boolean can1=false;
      rs1=query.select("select * from IISC_INCLUSION_DEPENDENCY where ID_id=" + owner.clipboard.id + "");
       if(rs1.next())
      { 
       
        mnem=rs1.getString(3);
         query.Close();
         query=new JDBCQuery(con);
        if(owner.clipboard.type.equals("Copy"))
        {while(true)
         {rs2=query.select("select * from IISC_INCLUSION_DEPENDENCY where ID_name='" + mnem + "' and PR_id="+ID);
         if(rs2.next())
         {mnem="Copy of " + mnem;
         query.Close();}
         else
         {query.Close();
         break;
         }}
        }
        else
        {
       can1=true;
        }
       
        query=new JDBCQuery(con);
        rs2=query.select("select max(ID_id)+1  from IISC_INCLUSION_DEPENDENCY");
        int ida=0;
        if(rs2.next())
        ida=rs2.getInt(1);
        query.Close();
        query.update("insert into IISC_INCLUSION_DEPENDENCY(ID_id,PR_id,ID_name) values(" + ida + "," + owner.clipboard.pid + ",'" + mnem +  "')");
         rs1=query.select("select * from IISC_INC_DEP_LHS_RHS where ID_id=" + owner.clipboard.id + "");
       while(rs1.next())
        {
         query.update("insert into IISC_INC_DEP_LHS_RHS(ID_id,Att_id,ID_lhs_rhs,PR_id,Att_rbr) values(" + ida + "," + rs1.getInt(2) +  "," + rs1.getInt(3) +  "," + owner.clipboard.pid + "," + rs1.getInt(5)+")");
         }
      query.Close();
        query=new JDBCQuery(owner.clipboard.con);
        if(can1){
        query.update("delete from IISC_INC_DEP_LHS_RHS where ID_id=" + owner.clipboard.id + "");
        query.update("delete from IISC_INCLUSION_DEPENDENCY where ID_id=" + owner.clipboard.id + "");
        removenode(mnem,"Inclusion Dependencies");}
        insert(mnem,"Inclusion Dependencies");
        select_node(mnem,"Inclusion Dependencies");
      }
    else
    query.Close();
    }
    
    if(owner.clipboard.table.equals("Join Dependencies"))
    { 
      query4=new JDBCQuery(owner.clipboard.con);
      String mnem="";
      boolean can1=false;
      rs4=query4.select("select * from IISC_JOIN_DEPENDENCY, IISC_APP_SYSTEM where IISC_JOIN_DEPENDENCY.AS_id=IISC_APP_SYSTEM.AS_id and JD_id=" + owner.clipboard.id + "");
      while(rs4.next())
      { 
        mnem=rs4.getString(4);
        String as=rs4.getString(3);
        String asname=rs4.getString("AS_name");
        if(!tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals(asname) && !tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals(asname))
        {
            JOptionPane.showMessageDialog(null, "<html><center>Can not paste here!", "Error", JOptionPane.ERROR_MESSAGE);
            query4.Close();
            continue;
        }
        query=new JDBCQuery(con);
        if(owner.clipboard.type.equals("Copy"))
        {while(true)
        {rs2=query.select("select * from IISC_JOIN_DEPENDENCY where JD_name='" + mnem + "' and PR_id="+ID+" and AS_id="+as);
        if(rs2.next())
        {mnem="Copy of " + mnem;
        query.Close();}
        else
        {query.Close();
        break;
        }
        }
        }
        else
        {
       can1=true;
        }
        query=new JDBCQuery(con);
        JDBCQuery query1=new JDBCQuery(con);
        rs2=query.select("select max(JD_id)+1  from IISC_JOIN_DEPENDENCY");
        int ida=0;
        if(rs2.next())
        ida=rs2.getInt(1);
        query.Close();
        query.update("insert into IISC_JOIN_DEPENDENCY(JD_id,PR_id, AS_id,JD_name) values (" + ida + "," + owner.clipboard.pid + ","+as+",'" + mnem +  "')");
        rs1=query.select("select * from IISC_JOIN_DEP_RS where JD_id=" + owner.clipboard.id + "");
        while(rs1.next())
        {
            query1.update("insert into IISC_JOIN_DEP_RS(JD_id,RS_id,RS_rbr,PR_id,AS_id) values(" + ida + "," + rs1.getInt(2) +  "," + rs1.getInt(3) +  "," + owner.clipboard.pid + "," + rs1.getInt(5)+")");
        }
        query.Close();
        query=new JDBCQuery(owner.clipboard.con);
        if(can1){
        query.update("delete from IISC_JOIN_DEP_RS where JD_id=" + owner.clipboard.id + "");
        query=new JDBCQuery(owner.clipboard.con);
        query.update("delete from IISC_JOIN_DEPENDENCY where JD_id=" + owner.clipboard.id + "");
        removenode(mnem,"Join Dependencies", asname);}
        insert(mnem,"Join Dependencies", asname);
        select_node(mnem,"Join Dependencies", asname);
      }
        query4.Close();
    }
    owner.clipboard.clear();
    }
    else
    JOptionPane.showMessageDialog(null, "<html><center>Can not paste here!", "Error", JOptionPane.ERROR_MESSAGE);
    }
    else
    JOptionPane.showMessageDialog(null, "<html><center>Can not paste here!", "Error", JOptionPane.ERROR_MESSAGE);
    }
   if(action.equals("Generate Report"))
    {
    GenReport gen=new GenReport(this);
    rs=query.select("select * from IISC_LOG_TYPE where CLT_name='" +  tree.getSelectionPath().getLastPathComponent().toString() + "'");
    int t=-1;
     if(rs.next())
     t=rs.getInt("CLT_id");
    query.Close();
    int as=-1;
    if(t<30)
    {
     rs=query.select("select * from IISC_App_system where AS_name='" +  tree.getSelectionPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString() + "'"  + "  and PR_id="+ID);
     if(rs.next())
     as=rs.getInt("AS_id");
     query.Close();
    }
    gen.generate(con, ID,as, t);
    
    }
     if(action.equals("Delete all") || action.equals("Delete obsolete"))
    {
    String qs=new String();
    String tp=new String("DB Schema collision reports");
    int as=-1;
    rs=query.select("select * from IISC_App_system");
    if(!tree.getSelectionPath().getLastPathComponent().toString().equals("Reports  "))
    rs=query.select("select * from IISC_App_system where AS_name='" +  tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString() + "'"  + "  and PR_id="+ID);
 if(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application generation reports")||  tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Repository reports")|| tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("DB Schema collision reports")|| tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("DB Schema Design reports"))
		rs=query.select("select * from IISC_App_system where AS_name='" +  tree.getSelectionPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString() + "'"  + "  and PR_id="+ID);
    if(rs.next())
     as=rs.getInt("AS_id");
     if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Reports ") || tree.getSelectionPath().getLastPathComponent().toString().equals("Reports ") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Reports  ") ||  tree.getSelectionPath().getLastPathComponent().toString().equals("Reports  ") && !(tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Repository reports") || tree.getSelectionPath().getLastPathComponent().toString().equals("Repository reports") ))
    as=-1;
    
    query.Close();
    if(action.equals("Delete obsolete"))
    {
	/*
     //if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Reports ") || tree.getSelectionPath().getLastPathComponent().toString().equals("Reports ") || tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Reports  ") ||  tree.getSelectionPath().getLastPathComponent().toString().equals("Reports  "))
      rs=query.select("select * from IISC_COLLISION_LOG  where CL_obsolete=0 and  AS_id="+ as +" and  PR_id=" + ID + "   order by CL_id desc");
     //else  if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Repository reports") || tree.getSelectionPath().getLastPathComponent().toString().equals("Repository reports"))
     //rs=query.select("select * from IISC_COLLISION_LOG  where   and CL_obsolete=0 and  IISC_COLLISION_LOG.AS_id=" + as + " and  PR_id=" + ID + "   order by CL_id desc");
     //else
      //rs=query.select("select * from IISC_COLLISION_LOG,IISC_APP_SYSTEM  where (IISC_COLLISION_LOG.CL_type=-AS_collision_detection-1 or ((IISC_COLLISION_LOG.CL_type=10  or IISC_COLLISION_LOG.CL_type=11) and CL_obsolete=0)) and  IISC_COLLISION_LOG.AS_id=IISC_APP_SYSTEM.AS_id and   IISC_APP_SYSTEM.PR_id=" + ID + "  and IISC_APP_SYSTEM.AS_id="+ as + " order by CL_id desc");
    while(rs.next())
    {
    qs=qs+rs.getInt("CL_id")+",";
    } 
    String pom=" and CL_id not in ("+qs.substring(0, qs.length()-1)+")";
    qs=qs+ pom;
    query.Close();*/ 
    qs=qs+" and CL_obsolete<>0";
    }
     if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Reports ") || tree.getSelectionPath().getLastPathComponent().toString().equals("Reports "))
     {qs=qs + " and CL_type>=30  and CL_type<40 ";
     as=-1;}
     else if ( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Reports  ") ||  tree.getSelectionPath().getLastPathComponent().toString().equals("Reports  "))
     {qs=qs + " and CL_type>=40 and CL_type<50 ";
     as=-1;}
    else if ( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application generation reports") ||  tree.getSelectionPath().getLastPathComponent().toString().equals("Application generation reports"))
    {qs=qs + " and CL_type>=50 and CL_type<60 ";}
    else if (tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Repository reports") ||  tree.getSelectionPath().getLastPathComponent().toString().equals("Repository reports"))
     {qs=qs + " and CL_type>=20  and CL_type<30 ";}
     else if (tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("DB Schema collision reports") ||  tree.getSelectionPath().getLastPathComponent().toString().equals("DB Schema collision reports"))
     {qs=qs + "  and CL_type<10 ";}
     else if (tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("DB Schema Design reports") ||  tree.getSelectionPath().getLastPathComponent().toString().equals("DB Schema Design reports"))
     {qs=qs + " and CL_type>=10  and CL_type<20";}
    rs=query.select("select * from IISC_LOG_TYPE  where CLT_name='"+ tree.getSelectionPath().getLastPathComponent().toString() + "' ");
    if(rs.next())
   {
    qs=qs+" and CL_type="+rs.getInt("CLT_id");
    tp=rs.getString("CLT_name");
   } 
   query.Close(); 
 DefaultMutableTreeNode node=(DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();  
 DefaultMutableTreeNode nodep=(DefaultMutableTreeNode)tree.getSelectionPath().getParentPath().getLastPathComponent();  
  int nc=node.getChildCount();
 DefaultTreeModel model = (DefaultTreeModel)tree.getModel(); 
  tree.expandPath(tree.getSelectionPath());
  for(int j=0;j<nc;j++)
  tree.expandPath(tree.getSelectionModel().getSelectionPath().pathByAddingChild((DefaultMutableTreeNode)node.getChildAt(j)) );
 rs=query.select("select * from IISC_COLLISION_LOG,IISC_LOG_TYPE  where IISC_COLLISION_LOG.CL_type=IISC_LOG_TYPE.CLT_id and AS_id=" + as + " and PR_id="+ID+ qs);
 while(rs.next())
  { int  k=rs.getInt("CL_id");
    tp=rs.getString("CLT_name"); 
    removenode(rs.getString("CL_date"),tp);
    query4.update("delete from IISC_COLLISION_LOG where CL_id=" + k + "  and PR_id="+ID);
   }
  query.Close();
  nc=node.getChildCount(); 
 if(nc>0 && (node.getUserObject().toString().equals("DB Schema collision reports")|| node.getUserObject().toString().equals("DB Schema Design reports")))
  for(int j=nc-1;j>=0;j--)
 { if(((DefaultMutableTreeNode)node.getChildAt(j)).getChildCount()==0)
  model.removeNodeFromParent((DefaultMutableTreeNode)node.getChildAt(j));
 }
      if(node.getChildCount()==0 && !nodep.getUserObject().toString().equals("Repository reports") && !nodep.getUserObject().toString().equals("Reports ") && !nodep.getUserObject().toString().equals("Reports  "))
   { 
  model.removeNodeFromParent(node);
   }
    }
    if(action.equals("Delete"))
    {ResultSet rs1,rs2;
    if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Attributes"))
    { 
      query=new JDBCQuery(con);
      int mnem;
      rs1=query.select("select * from IISC_Attribute where Att_mnem='" +  tree.getSelectionPath().getLastPathComponent().toString() + "'"  + "  and PR_id="+ID);
      if(rs1.next())
      {removenode(rs1.getString(3),"Attributes");
       mnem=rs1.getInt(1);
       query.Close();
      query=new JDBCQuery(con);
       query.update("delete from IISC_Attribute where Att_id=" + mnem + ""  + "  and PR_id="+ID);
        } 
        else
      query.Close();
      }
    if (tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Reports ") || tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Reports  ") || tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("DB Schema collision reports") || tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("DB Schema Design reports") || tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Repository reports") || tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Application generation reports"))
   {
  int k=-1;
   rs=query.select("select * from IISC_COLLISION_LOG,IISC_LOG_TYPE where IISC_COLLISION_LOG.CL_type=IISC_LOG_TYPE.CLT_id and CLT_name='" + tree.getSelectionPath().getParentPath().getLastPathComponent().toString() + "' and PR_id="+ID);
 if(rs.next())
  k=rs.getInt("CL_id");
 query.Close();
query.update("delete from IISC_COLLISION_LOG where CL_id=" + k + "  and PR_id="+ID);

 removenode(tree.getSelectionPath().getLastPathComponent().toString(),tree.getSelectionPath().getParentPath().getLastPathComponent().toString());
   }
        if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Inclusion Dependencies"))
    { 
      query=new JDBCQuery(con);
      int mnem;
      rs1=query.select("select * from IISC_INCLUSION_DEPENDENCY where ID_name='" +  tree.getSelectionPath().getLastPathComponent().toString() + "'"  + "  and PR_id="+ID);
      if(rs1.next())
      {removenode(rs1.getString("ID_name"),"Inclusion Dependencies");
       mnem=rs1.getInt(1);
       query.Close();
        rs1=query.select(" select count(*) from IISC_INC_DEP_LHS_RHS where ID_id=" + mnem + ""  + "  and PR_id="+ID);
      if(rs1.next())
      {
       if(rs1.getInt(1)==2) 
       {
        query.Close(); 
          rs=query.select("select a.Att_id,b.Att_id, count(*) from IISC_INC_DEP_LHS_RHS as a,IISC_INC_DEP_LHS_RHS as b  where a.ID_id="+ mnem +" and a.ID_lhs_rhs=0 and b.ID_lhs_rhs=1 and a.PR_id="+  ID + " and b.ID_id="+ mnem +" and b.PR_id="+ ID + " group by a.Att_id,b.Att_id");
   if(rs.next())
   {
    int idc=rs.getInt(3);
    int at=rs.getInt(1);
    query.Close();  
    if(idc==1)
    {
    query.update("update IISC_ATTRIBUTE set  Att_elem=-1  where Att_id="+ at);   
    }
   }
    else
    query.Close();
       }
       else
       query.Close();
      }
      else
    query.Close();  
      query=new JDBCQuery(con);
       query.update("delete from IISC_INCLUSION_DEPENDENCY where ID_id=" + mnem + ""  + "  and PR_id="+ID);
       query.update("delete from  IISC_INC_DEP_LHS_RHS where ID_id=" + mnem + ""  + "  and PR_id="+ID);
       
        } 
       else
      query.Close(); 
      }
  /*  if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Functions"))
    { 
      query=new JDBCQuery(con);
      int mnem;
      rs1=query.select("select * from IISC_Function where Fun_name='" +  tree.getSelectionPath().getLastPathComponent().toString() + "'"  + "  and PR_id="+ID);
      if(rs1.next())
      {removenode(rs1.getString(3),"Functions"); 
       mnem=rs1.getInt(1);
       query.Close();
       query=new JDBCQuery(con);
        query.update("delete from IISC_Function where Fun_id=" + mnem + ""  + "  and PR_id="+ID);
       } 
        else
       query.Close();
      }*/
      
   //jovo+
   // delete funkcije iz popup menija
       if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Functions"))
       {
            TreePath a = tree.getSelectionPath();
            TreePath b = tree.getSelectionPath().getParentPath();
            String tmp = tree.getSelectionPath().getLastPathComponent().toString();
            //System.out.println(tmp);
            query=new JDBCQuery(con);
            Function fun=new Function(owner,this.getTitle(),true,con,i,this);

            tree.setSelectionPath(a);
            
             if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to delete function?", "Function", JOptionPane.YES_NO_OPTION)==0 && fun.delete()){
                 fun.dispose();
                 tree.setSelectionPath(b);
             }
       }
   
     if( tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Packages"))
     {
          TreePath a = tree.getSelectionPath();
         TreePath b = tree.getSelectionPath().getParentPath();
         String tmp = tree.getSelectionPath().getLastPathComponent().toString();
      //System.out.println(tmp);
       query=new JDBCQuery(con);
       Packages pack = new Packages(owner,this.getTitle(),true,con,i,this);
       //fun.delete();
       
       //System.out.println("asd:" + tmp);
         tree.setSelectionPath(a);
         if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to delete function?", "Function", JOptionPane.YES_NO_OPTION)==0 && pack.delete()){
           pack.dispose();
           tree.setSelectionPath(b);
           //removenode(tmp,"Functions");
       }
      /* if (JOptionPane.showConfirmDialog(null, "<html><center>Do you want to delete function?", "Function", JOptionPane.YES_NO_OPTION)==0 && i != -1)
       {
           System.out.println("delete from IISC_Function where Fun_id=" + i + "  and PR_id="+ID);
           query.update("delete from IISC_Function where Fun_id=" + i + "  and PR_id="+ID);
           System.out.println("delete from IISC_FUN_PARAM where Fun_id=" + i + "  and PR_id="+ID);
           query.update("delete from IISC_FUN_PARAM where Fun_id=" + i + "  and PR_id="+ID);
           removenode(tree.getSelectionPath().getLastPathComponent().toString(),"Functions");
           JOptionPane.showMessageDialog(null, "<html><center>Function removed!", "Functions", JOptionPane.INFORMATION_MESSAGE);
       }
       */
     } 
     
     //jovo+ end      
      
   if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("User defined domains"))
    { 
      query=new JDBCQuery(con);
      int mnem;
      rs1=query.select("select * from IISC_Domain where Dom_mnem='" +  tree.getSelectionPath().getLastPathComponent().toString() + "'"  + "  and PR_id="+ID);
      if(rs1.next())
      {removenode(rs1.getString(3),"User defined domains"); 
      mnem=rs1.getInt(1);
      query.Close();
       query=new JDBCQuery(con);
       query.update("delete from IISC_DOM_ATT where Dom_id=" + mnem + ""  + "  and PR_id="+ID);
       query.update("delete from IISC_Domain where Dom_id=" + mnem + ""  + "  and PR_id="+ID);
       } 
        else
      query.Close();
      }
      
  if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Primitive domains"))
    { 
      query=new JDBCQuery(con);
      int mnem;
      rs1=query.select("select * from IISC_PRIMITIVE_TYPE where PT_mnemonic='" +  tree.getSelectionPath().getLastPathComponent().toString() + "'"  + "");
      if(rs1.next())
      {removenode(rs1.getString(2),"Primitive domains"); 
      mnem=rs1.getInt(1);
      query.Close();
       query=new JDBCQuery(con);
       query.update("delete from IISC_PRIMITIVE_TYPE where PT_id=" + mnem + ""  + "");
       } 
        else
      query.Close();
      }
      
   if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Types"))
    { 
      query=new JDBCQuery(con);
      int mnem;
      rs1=query.select("select * from IISC_App_system_type where AS_type='" +  tree.getSelectionPath().getLastPathComponent().toString() + "'"  );
      if(rs1.next())
      {removenode(rs1.getString(2),"Application Types");
      mnem=rs1.getInt(1);
      query.Close();
      query=new JDBCQuery(con);
      query.update("delete from IISC_App_system_type where AS_type_id=" + mnem);
        } 
      else
      query.Close();
      }
         
    if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems"))
    { 
      query=new JDBCQuery(con);
      int mnem;
      rs1=query.select("select * from IISC_App_system where AS_name='" +  tree.getSelectionPath().getLastPathComponent().toString() + "'"  + "  and PR_id="+ID);
      if(rs1.next())
      {
      String j=rs1.getString(3);
      mnem=rs1.getInt(1);
      query.Close();
      query=new JDBCQuery(con);
      boolean cann=true;
      rs1=query.select("select * from IISC_TF_appsys where AS_id=" +  mnem + ""  + "  and PR_id="+ID);
      if(rs1.next())cann=false;
      query.Close();
      if(cann)
      {
      removeAppSystem(mnem, ID);
      removenode(j,"Application Systems");
      }
      else
      JOptionPane.showMessageDialog(null, "<html><center>Delete is not allowed.<br>Delete form types first!", "IIS*Case", JOptionPane.INFORMATION_MESSAGE);
      } 
       else
      query.Close(); 
      }
   if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Owned"))
    { 
      query=new JDBCQuery(con);
      int mnem;
      rs1=query.select("select * from IISC_Form_type,IISC_TF_appsys,IISC_app_system  where IISC_Form_type.TF_id=IISC_TF_appsys.TF_id and IISC_TF_appsys.AS_id=IISC_app_system.AS_id and TF_mnem='" +  tree.getSelectionPath().getLastPathComponent().toString() + "'"  + "  and IISC_TF_appsys.PR_id="+ID);
      if(rs1.next())
      {
      mnem=rs1.getInt(1);
      String mnem1=rs1.getString("TF_mnem");
      String appsys=rs1.getString("AS_name");
      query.Close();
      query=new JDBCQuery(con);
        Form form=new Form(owner,this.getTitle(),false,con,mnem,this,appsys);
        if(form.delete(mnem))
        removenode(mnem1,"Owned");
        form.dispose();
       
       //select_node("Owned", appsys);
        } 
        else
      query.Close();
      }
   
        if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Join Dependencies"))
         { 
           query=new JDBCQuery(con);
           int mnem;
           String str="select * from IISC_JOIN_DEPENDENCY,IISC_app_system  where IISC_JOIN_DEPENDENCY.AS_id=IISC_app_system.AS_id and JD_name='" +  tree.getSelectionPath().getLastPathComponent().toString() + "' and AS_name='" +  tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString() + "' and IISC_JOIN_DEPENDENCY.PR_id="+ID;
           rs1=query.select(str);
           if(rs1.next())
           {
           mnem=rs1.getInt("JD_id");
           String mnem1=rs1.getString("JD_name");
           String appsys=rs1.getString("AS_name");
           query.Close();
           query=new JDBCQuery(con);
           JoinDep join=new JoinDep(owner,this.getTitle(),mnem,false,con,this,appsys);
           if(join.delete(mnem1))
           removenode(mnem1,"Join Dependencies",appsys);
           join.dispose();
           select_node("Join Dependencies", appsys);
           } 
           else
                query.Close();
           }
   
    }
  }
  
  }
    catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
}
    catch (Exception ex) { ex.printStackTrace();}
  }
  
 private void removeAppSystem(int m, int i) {
     JDBCQuery query=new JDBCQuery(con);
     query.update("delete from IISC_RSC_ACTION where AS_id=" +  m   + " and PR_id="+i);
     query.update("delete from IISC_RSC_LHS_RHS where AS_id=" +  m   + " and PR_id="+i);
     query.update("delete from IISC_RSC_RS_SET where AS_id=" +  m   + " and PR_id="+i);
     query.update("delete from IISC_RS_CONSTRAINT where AS_id=" +  m   + " and PR_id="+i);
     query.update("delete from IISC_GRAPH_CLOSURE where AS_id=" +  m   + " and PR_id="+i);
     query.update("delete from IISC_RSK_ATT where AS_id=" +  m   + " and PR_id="+i);
     query.update("delete from IISC_RS_KEY where AS_id=" +  m   + " and PR_id="+i);
     query.update("delete from IISC_RS_UNIQUE where AS_id=" +  m   + " and PR_id="+i);
     query.update("delete from IISC_RS_ATT where AS_id=" +  m   + " and PR_id="+i);
     query.update("delete from IISC_RS_ROLE where AS_id=" +  m   + " and PR_id="+i); 
     query.update("delete from IISC_RS_SR where AS_id=" +  m   + " and PR_id="+i); 
     query.update("delete from IISC_RELATION_SCHEME where AS_id=" +  m   + " and PR_id="+i);
     query.update("delete from IISC_FNF_LHS_RHS where AS_id=" +  m   + " and PR_id="+i); 
     query.update("delete from IISC_F_NF_DEP where AS_id=" +  m   + " and PR_id="+i); 
     query.update("delete from IISC_COLLISION_LOG where AS_id=" +  m   + " and PR_id="+i);
     query.update("delete from IISC_APP_SYS_REFERENCE where AS_id=" +  m   + " and PR_id="+i);
     query.update("delete from IISC_APP_SYSTEM_CONTAIN where AS_id=" +  m   + " and PR_id="+i);
     query.update("delete from IISC_App_system where AS_id=" + m   + " and PR_id="+i);
 }
   private void popup2_actionPerformed(ActionEvent e)
  {ResultSet rs;
  JDBCQuery query=new JDBCQuery(con);
   int i=-1;
   String action=new String();
  if(e.getActionCommand().toString().equals("Expand"))
    tree.expandPath(tree.getSelectionPath());
    if(e.getActionCommand().toString().equals("Collapse"))
    tree.collapsePath(tree.getSelectionPath());
    if(e.getActionCommand().toString().equals("Rename"))
   {  
   tree.startEditingAtPath(tree.getPathForRow(0));
     }
   if(e.getActionCommand().toString().equals(""))action=((JButton)e.getSource()).getToolTipText();
  else
  action=e.getActionCommand().toString(); 
    try
    {

   if (e.getActionCommand().toString().equals("XML") && ( tree.getSelectionPath().getLastPathComponent().toString().equals("Relation Schemes")|| tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems")))
  { XMLViewer xmlv=new XMLViewer(owner,"Export to XML",false,con,"xml");
    Settings.Center(xmlv);
    WindowsManager.add(xmlv);
    xmlv.setVisible(true);
 }
   if (e.getActionCommand().toString().equals("XML Schema") && ( tree.getSelectionPath().getLastPathComponent().toString().equals("Relation Schemes")|| tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems")))
  { XMLViewer xmlv=new XMLViewer(owner,"XML Schema",false,con,"xsd");
    Settings.Center(xmlv);
    WindowsManager.add(xmlv);
    xmlv.setVisible(true);
 }
 
 //Slavica
  if (e.getActionCommand().toString().equals("SQL DDL") && ( tree.getSelectionPath().getLastPathComponent().toString().equals("Relation Schemes")|| tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems")))
  { GenerateSQL Gsql=new GenerateSQL((IISFrameMain)owner,"SQL skript",false, con,this);  
    Settings.Center(Gsql);
    WindowsManager.add(Gsql);
    Gsql.setVisible(true);
 }
 //Slavica
 
   if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Child Application Systems") )
  {rs=query.select("select * from IISC_App_system where AS_name='" + tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
  if(rs.next())
  i=rs.getInt(1);
  query.Close();
  }
   if( tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Referenced") )
  {rs=query.select("select * from IISC_App_system where AS_name='" + tree.getSelectionPath().getParentPath().getParentPath().getParentPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
  if(rs.next())
  i=rs.getInt(1);
  query.Close();
  } 
    if(e.getActionCommand().toString().equals("Add Application System") || (action.equals("Reference") && tree.getSelectionPath().getLastPathComponent().toString().equals("Child Application Systems") ))
    {
    AddAppSys app=new AddAppSys(owner,this.getTitle(),false,con,this);
  Settings.Center(app);
  WindowsManager.add(app);
  app.setVisible(true);
    }
      if(e.getActionCommand().toString().equals("Remove Application System")|| (action.equals("Dereference") && tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Child Application Systems")))
    {
    rs=query.select("select * from IISC_App_system where AS_name='" + tree.getSelectionPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
  int is=-1;
  if(rs.next())
  is=rs.getInt(1);
  query.Close();
     query.update("delete from IISC_App_system_contain where AS_id=" + i + "  and AS_id_con=" + is + "  and PR_id="+ID);
     removenode(tree.getSelectionPath().getLastPathComponent().toString(),"Child Application Systems");
    }
  
 if(e.getActionCommand().toString().equals("Reference Form Type")|| (action.equals("Reference") && tree.getSelectionPath().getLastPathComponent().toString().equals("Referenced")))
    {
    RefForm ref=new RefForm(owner,this.getTitle(),false,con,this);
  Settings.Center(ref);
  WindowsManager.add(ref);
  ref.setVisible(true);
    }
      if(e.getActionCommand().toString().equals("Dereference Form Type") || (action.equals("Dereference") && tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Referenced")))
    {
     rs=query.select("select * from IISC_FORM_TYPE where TF_mnem='" + tree.getSelectionPath().getLastPathComponent().toString() + "'  and PR_id="+ID);
  int is=-1;
  if(rs.next())
  is=rs.getInt(1);
  query.Close();
     query.update("delete from IISC_APP_SYS_REFERENCE where AS_id=" + i + "  and TF_id=" + is + "  and PR_id="+ID);
     removenode(tree.getSelectionPath().getLastPathComponent().toString(),"Referenced");
    }  
  }
catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
}
catch (Exception ex) {
 ex.printStackTrace();
}
  }  
 
  private void btnNew1_actionPerformed(ActionEvent e)
  {
  try{
  this.setVisible(false);
  pravi_drvo();
  }
  catch (Exception ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);}
  catch (Throwable ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);}
  }

  private void btnSave_actionPerformed(ActionEvent e)
  {
  SaveAs sDialog=new SaveAs(owner,name,false,con);
Settings.Center(sDialog);
  sDialog.setVisible(true);
  }

  private void btnEdit_actionPerformed(ActionEvent e)
  { popup_actionPerformed(e);
  }

  private void btnCopy_actionPerformed(ActionEvent e)
  {
  popup_actionPerformed(e);
  }

  private void btnCut_actionPerformed(ActionEvent e)
  {
  popup_actionPerformed(e);
  }

  private void btnPaste_actionPerformed(ActionEvent e)
  {
  popup_actionPerformed(e);
  }

  private void btnNew_actionPerformed(ActionEvent e)
  {  try {
  popup_actionPerformed(e);
 }
    catch (Exception ex) {} }

  private void btnNewProj_actionPerformed(ActionEvent e)
  {
  NewProject np=new NewProject(owner,owner.getTitle(),true);
  Settings.Center(np);
  np.setVisible(true);
  }

  private void this_internalFrameClosing(InternalFrameEvent e)
  {
  try {
    con.close();
    Iterator it= WindowsManager.iterator();
    while(it.hasNext())
    {
        JDialog dial = (JDialog)it.next();
        dial.dispose(); 
    }
    owner.buttonAppGenerator.setEnabled(false);
    owner.buttonDB.setEnabled(false);
    owner.buttonDBSchemaAnalysis.setEnabled(false);
    owner.buttonSQLGenerator.setEnabled(false);
}
    catch (Exception ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
  }

  private void btnErase_actionPerformed(ActionEvent e)
  {popup_actionPerformed(e);
  }

  private void btnDB_actionPerformed(ActionEvent e)
  {popup_actionPerformed(e);
  }

  private void btnXML_actionPerformed(ActionEvent e)
  {popup_actionPerformed(e);
  }

  private void this_focusGained(FocusEvent e)
  {setMenus();
  }

  private void btnRemove_actionPerformed(ActionEvent e)
  {RemoveProject rp=new RemoveProject(owner,owner.getTitle(),false,ID,con);
  Settings.Center(rp);
  rp.setVisible(true);
  }
  public void updateWindows(Object obj)
  {
    Iterator it=WindowsManager.iterator();
    while(it.hasNext())
    {
    Object object=it.next();
     
    }
  }
    private boolean isCircle(int a, int b)
    { try
      {
      ResultSet rs,rs1;
      JDBCQuery query=new JDBCQuery(con);
      int ids;
      rs=query.select("select AS_id_con from IISC_APP_SYSTEM_CONTAIN where AS_id="+ a +" and PR_id="+ ID);
      boolean is=false;
      while(rs.next())
      {
          ids=rs.getInt(1);
          if(ids==b)return true;
          else is=is || isCircle(ids, b);
      }
      query.Close();
      return is;
      }
      catch(SQLException ex)
      {
        ex.printStackTrace();
      }
      return false;
    }
}