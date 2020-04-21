package iisc;

import iisc.lang.JSourceCodeEditor;

import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.sql.*;

import java.util.*;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

import javax.swing.SwingConstants;
import java.awt.event.ItemListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.KeyListener;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Function extends JDialog// implements ActionListener
{
  IISFrameMain parent;
  private ImageIcon iconPrevv = new ImageIcon(IISFrameMain.class.getResource("icons/prevv.gif"));
  private ImageIcon iconPrev = new ImageIcon(IISFrameMain.class.getResource("icons/prev.gif"));
  private ImageIcon iconNext = new ImageIcon(IISFrameMain.class.getResource("icons/next.gif"));
  private ImageIcon iconNextt = new ImageIcon(IISFrameMain.class.getResource("icons/nextt.gif"));
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
  private ImageIcon iconMoveUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
  private ImageIcon iconMoveDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
  private JButton btnNew = new JButton();
  private JButton btnClose = new JButton();
  private PTree tree;
  private JLabel jLabel3 = new JLabel();
  private JTextField txtName = new JTextField();
  private JLabel jLabel10 = new JLabel();
  private Connection connection;
  private String Mnem;
  private int id;
  private String dom_fun_id;
  private JButton btnSave = new JButton();
  private JTextArea txtDescription = new JTextArea();
  private JButton btnErase = new JButton();
  private JButton btnHelp = new JButton();
  private JToolBar jToolBar1 = new JToolBar();
  private JButton btnFirst = new JButton();
  private JButton btnPrev = new JButton();
  private JComboBox cmbFun = new JComboBox();
  private JButton btnNext = new JButton();
  private JButton btnLast = new JButton();
  private JButton btnApply = new JButton();
  private JTabbedPane jTabbedPane1 = new JTabbedPane();
  private JPanel funcTabPanel = new JPanel();
  private JPanel noteTabParam = new JPanel();
  private JTextArea txtComment = new JTextArea();
  private JLabel jLabel4 = new JLabel();
  private boolean dispose=false;
  private JPanel paramTabPanel = new JPanel();
  public JComboBox returnCombo = new JComboBox();
  //private XYLayout xYLayout1 = new XYLayout();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JScrollPane scrollPanelTabele = new JScrollPane();
  private JTable jTable1 = new JTable(){
      public boolean isCellEditable(int rowIndex, int colIndex) {
        return false;   //Disallow the editing of any cell
      }
    };
  private DefaultTableModel t=new DefaultTableModel(); 
  private JButton addParam = new JButton();
  private JButton editParam = new JButton();
  private JButton delParam = new JButton();
  private JButton moveUpParam = new JButton();
  private JButton moveDownParam = new JButton();
  //private boolean cmbenable;
  private JButton jBtnAlg = new JButton();
  private String tmpFunName = "";
  
  
//nove jovo+
  private boolean funcEditable = true;
  private JButton jBtnAlg2 = new JButton();
  private JButton jBtnAlg3 = new JButton();
    private JButton funcDomainSelect = new JButton();
    private boolean can=true;
    //  private XYLayout xYLayout1 = new XYLayout();

  public Function()
  {
    this(null, "", false);
  }

  public Function(IISFrameMain p_parent, String title, boolean modal)
  {
      super((Frame) p_parent, title, modal);
      try
      {
          id = -1;
          parent = p_parent;
          jbInit();
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
  }
  
  
  
  public Function(IISFrameMain parent, String title, boolean modal,Connection con,int m , PTree tr )
  {
      super((Frame) parent, title, modal);
      try
      {   
          id = -1;
          tree=tr;
          connection=con;
          this.parent = parent;
          if(m>=0)
          {
              id=m;
              JDBCQuery query=new JDBCQuery(connection);
              JDBCQuery query2=new JDBCQuery(connection);
              ResultSet rs1,rs2;
              rs1=query.select("select * from IISC_Function where PR_id="+ tree.ID +" and Fun_id="+id);
              while(rs1.next())
              {
                  String funnamecmb = rs1.getString("Fun_name");
                  rs2=query2.select("select F.*,D.Dom_mnem from IISC_FUN_PARAM F,IISC_DOMAIN D where F.Dom_id = D.Dom_id AND F.PR_id = D.PR_id AND F.PR_id= " + tree.ID + " AND F.Fun_id = "+id+" order by F.Param_seq");    
              
                  funnamecmb = funnamecmb + " ( ";
                  int itemp = 1;
                
                  while(rs2.next())
                  {
                      String domtemp= rs2.getString("Dom_mnem");
                      funnamecmb = funnamecmb + domtemp + " , ";  
                      itemp = 2;
                  }
                
                  funnamecmb = funnamecmb.substring(0,funnamecmb.length()-itemp) + " )";   
                  Mnem=funnamecmb;
                  query2.Close();
              }
              query.Close();
          }
          else
              Mnem="";
          
          Iterator it=tree.WindowsManager.iterator();
      
          while(it.hasNext())
          {
              Object obj=(Object)it.next();
              Class cls=obj.getClass();
              if(cls==this.getClass())
              {  
                  ((Function)obj).dispose();
              }
          }
      
          jbInit();
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
  }
  
  
  public String getTmpName(){
      return tmpFunName;
  }
  
  private void jbInit() throws Exception
  {
  


ComboBoxRenderer rendererr = new ComboBoxRenderer();
//renderer.setPreferredSize(new Dimension(200, 130))
//jComboBox1.setRenderer(renderer);


      jTable1.setGridColor(Color.black);
      jTable1.setCellSelectionEnabled(true);
      jTable1.setColumnSelectionAllowed(false);
    jTable1.setFocusable(false);
      addParam.setText("Add Parameter");
    addParam.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          addParam_actionPerformed(e);
        }
      });

      editParam.setText("Edit Parameter");
    editParam.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          editParam_actionPerformed(e);
        }
      });

      delParam.setText("Delete Parameter");
    delParam.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          delParam_actionPerformed(e);
        }
      });

      moveUpParam.setIcon(iconMoveUp);
      moveUpParam.setHorizontalTextPosition(SwingConstants.LEFT);
    moveUpParam.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          moveUpParam_actionPerformed(e);
        }
      });
  
      moveDownParam.setIcon(iconMoveDown);
    moveDownParam.setBounds(new Rectangle(10, 70, 25, 20));
    moveDownParam.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          moveDownParam_actionPerformed(e);
        }
      });

      jBtnAlg.setText("Specification");
      jBtnAlg.setBounds(new Rectangle(315, 160, 120, 25));
    jBtnAlg.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jBtnAlg_actionPerformed(e);
        }
      });
    jBtnAlg2.setText("Specification");
    jBtnAlg2.setBounds(new Rectangle(315, 160, 120, 25));
    jBtnAlg2.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton1_actionPerformed(e);
        }
      });
    jBtnAlg3.setText("Specification");
    jBtnAlg3.setBounds(new Rectangle(315, 160, 120, 25));
    jBtnAlg3.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton2_actionPerformed(e);
        }
      });

        funcDomainSelect.setText("...");
        funcDomainSelect.setBounds(new Rectangle(240, 165, 30, 20));
        funcDomainSelect.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        funcDomainSelect_actionPerformed(e);
                    }
                });
        this.setSize(new Dimension(468, 339));
      this.getContentPane().setLayout(null);

      this.setTitle("Functions");
      this.setResizable(false);  
  
      btnNew.setMaximumSize(new Dimension(50, 30));
      btnNew.setPreferredSize(new Dimension(50, 30));
      btnNew.setText("New");
      btnNew.setBounds(new Rectangle(85, 265, 80, 30));
      btnNew.setMinimumSize(new Dimension(50, 30));
      btnNew.setFont(new Font("SansSerif", 0, 11));
    btnNew.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnNew_actionPerformed(e);
        }
      });
  
      btnClose.setMaximumSize(new Dimension(50, 30));
      btnClose.setPreferredSize(new Dimension(50, 30));
      btnClose.setText("Cancel");
      btnClose.setBounds(new Rectangle(330, 265, 80, 30));
      btnClose.setMinimumSize(new Dimension(50, 30));
      btnClose.setFont(new Font("SansSerif", 0, 11));
    btnClose.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnClose_actionPerformed(e);
        }
      });
  
      jLabel3.setText("Description:");
      jLabel3.setBounds(new Rectangle(10, 55, 85, 20));
      jLabel3.setFont(new Font("SansSerif", 0, 11));
      txtName.setBounds(new Rectangle(80, 20, 355, 20));
      txtName.setFont(new Font("Verdana", 0, 11));

    txtName.addKeyListener(new KeyAdapter()
      {
        public void keyReleased(KeyEvent e)
        {
          txtName_keyReleased(e);
        }
      });

      jLabel10.setText("Name:");
      jLabel10.setBounds(new Rectangle(10, 20, 65, 20));
      jLabel10.setFont(new Font("SansSerif", 0, 11));
      btnSave.setMaximumSize(new Dimension(50, 30));
      btnSave.setPreferredSize(new Dimension(50, 30));
      btnSave.setText("OK");
      btnSave.setBounds(new Rectangle(250, 265, 75, 30));
      btnSave.setMinimumSize(new Dimension(50, 30));
      btnSave.setFont(new Font("SansSerif", 0, 11));
    btnSave.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnSave_actionPerformed(e);
        }
      });
  
      txtDescription.setBounds(new Rectangle(80, 55, 355, 100));
      txtDescription.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
      txtDescription.setFont(new Font("SansSerif", 0, 11));
    txtDescription.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtDescription_keyPressed(e);
        }
      });
  
      btnErase.setMaximumSize(new Dimension(50, 30));
      btnErase.setPreferredSize(new Dimension(50, 30));
      btnErase.setText("Delete");
      btnErase.setBounds(new Rectangle(170, 265, 75, 30));
      btnErase.setMinimumSize(new Dimension(50, 30));
      btnErase.setFont(new Font("SansSerif", 0, 11));
    btnErase.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnErase_actionPerformed(e);
        }
      });
  
      btnHelp.setBounds(new Rectangle(415, 265, 35, 30));
      btnHelp.setIcon(imageHelp);

      jToolBar1.setFont(new Font("Verdana", 0, 11));
      jToolBar1.setLayout(null);
      jToolBar1.setPreferredSize(new Dimension(249, 60));
      jToolBar1.setFloatable(false);
      jToolBar1.setBounds(new Rectangle(5, 5, 445, 30));
      btnFirst.setMaximumSize(new Dimension(60, 60));
      btnFirst.setPreferredSize(new Dimension(25, 20));
      btnFirst.setIcon(iconPrevv);
      btnFirst.setBounds(new Rectangle(5, 5, 25, 20));
    btnFirst.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnFirst_actionPerformed(e);
        }
      });

      btnPrev.setMaximumSize(new Dimension(60, 60));
      btnPrev.setIcon(iconPrev);
      btnPrev.setPreferredSize(new Dimension(25, 20));
      btnPrev.setBounds(new Rectangle(35, 5, 25, 20));
    btnPrev.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnPrev_actionPerformed(e);
        }
      });

      cmbFun.setFont(new Font("SansSerif", 0, 11));
      cmbFun.setBounds(new Rectangle(65, 5, 300, 20));
    cmbFun.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        cmbFun_actionPerformed(e);
                    }
      });
      btnNext.setMaximumSize(new Dimension(60, 60));
      btnNext.setIcon(iconNext);
      btnNext.setPreferredSize(new Dimension(25, 20));
      btnNext.setBounds(new Rectangle(370, 5, 25, 20));
    btnNext.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnNext_actionPerformed(e);
        }
      });

      btnLast.setMaximumSize(new Dimension(60, 60));
      btnLast.setIcon(iconNextt);
      btnLast.setPreferredSize(new Dimension(25, 20));
      btnLast.setBounds(new Rectangle(405, 5, 25, 20));
    btnLast.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnLast_actionPerformed(e);
        }
      });

      btnApply.setMaximumSize(new Dimension(50, 30));
      btnApply.setPreferredSize(new Dimension(50, 30));
      btnApply.setText("Apply");
      btnApply.setMinimumSize(new Dimension(50, 30));
      btnApply.setFont(new Font("SansSerif", 0, 11));
      btnApply.setBounds(new Rectangle(5, 265, 75, 30));
    btnApply.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnApply_actionPerformed(e);
        }
      });

      jTabbedPane1.setBounds(new Rectangle(5, 35, 445, 215));
      funcTabPanel.setLayout(null);
      funcTabPanel.setFont(new Font("SansSerif", 0, 11));
      noteTabParam.setLayout(null);
      noteTabParam.setFont(new Font("SansSerif", 0, 11));
      txtComment.setBounds(new Rectangle(5, 30, 430, 125));
      txtComment.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
      txtComment.setFont(new Font("SansSerif", 0, 11));
    txtComment.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtComment_keyPressed(e);
        }
      });
      jLabel4.setText("Comment:");
      jLabel4.setBounds(new Rectangle(5, 5, 85, 20));
      jLabel4.setFont(new Font("SansSerif", 0, 11));
      paramTabPanel.setLayout(null);
    returnCombo.setBounds(new Rectangle(115, 165, 125, 20));
    returnCombo.addFocusListener(new FocusAdapter()
      {
        public void focusGained(FocusEvent e)
        {
          returnCombo_focusGained(e);
        }
      });

    returnCombo.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        returnCombo_actionPerformed(e);
                    }
      });

      jLabel1.setText("Return Value Type");
    jLabel1.setBounds(new Rectangle(10, 175, 80, 20));
      jLabel2.setText("Parameters");
        jToolBar1.add(btnFirst, null);
    jToolBar1.add(btnPrev, null);
      jToolBar1.add(cmbFun, null);
    jToolBar1.add(btnNext, null);
    jToolBar1.add(btnLast, null);
        funcTabPanel.add(jBtnAlg, null);
        funcTabPanel.add(txtDescription, null);
      funcTabPanel.add(jLabel3, null);
      funcTabPanel.add(txtName, null);
      funcTabPanel.add(jLabel10, null);
        noteTabParam.add(jBtnAlg3, null);
        noteTabParam.add(jLabel4, null);
      noteTabParam.add(txtComment, null);
      jTabbedPane1.addTab("Function", funcTabPanel);
      scrollPanelTabele.getViewport().add(jTable1, null);
      moveDownParam.setBounds(10, 105, 25, 20);
     moveUpParam.setBounds(10, 20, 25, 20);
        paramTabPanel.add(funcDomainSelect, null);
        paramTabPanel.add(jBtnAlg2, null);
        paramTabPanel.add(moveDownParam, null);
    paramTabPanel.add(moveUpParam, null);
    delParam.setBounds(315, 130, 120, 25);
    paramTabPanel.add(delParam, null);
     
     editParam.setBounds(175, 130, 110, 25);
    paramTabPanel.add(editParam, null);
      addParam.setBounds(40, 130, 105, 25);
    paramTabPanel.add(addParam, null);
      
      scrollPanelTabele.setBounds(40, 20, 395, 105);
      paramTabPanel.add(scrollPanelTabele, null);
      jLabel2.setBounds(10, 0, 75, 20);
      jLabel1.setBounds(10, 165, 100, 20);      
    paramTabPanel.add(jLabel2, null);
    paramTabPanel.add(jLabel1, null);
    paramTabPanel.add(returnCombo, null);
returnCombo.setBounds(105, 165, 125, 20);
      
      jTabbedPane1.addTab("Parameters / Return Value", paramTabPanel);    
      jTabbedPane1.addTab("Notes", noteTabParam);
      this.getContentPane().add(jTabbedPane1, null);
      this.getContentPane().add(btnApply, null);
      this.getContentPane().add(jToolBar1, null);
      this.getContentPane().add(btnHelp, null);
      this.getContentPane().add(btnNew, null);
      this.getContentPane().add(btnClose, null);
      this.getContentPane().add(btnErase, null);
      this.getContentPane().add(btnSave, null);  
      this.addWindowListener(new WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }
      });  
            
      try
      {
            //cmbenable = false;
            ResultSet rs,rs1;
            JDBCQuery query=new JDBCQuery(connection);
            JDBCQuery query2=new JDBCQuery(connection);
    
            rs=query.select("select count(*) from IISC_DOMAIN where PR_id="+tree.ID);
    
            int count = 0;
            if (rs.next())
            {
                count = rs.getInt(1);
            }
    
            if (count == 0)
            {
                JOptionPane.showMessageDialog(null, "<html><center>You do not have Domains!", "Domain Dependencies", JOptionPane.OK_OPTION);      
                //if (JOptionPane.showConfirmDialog(null, "<html><center>You do not have Domains!", "Domain Dependencies", JOptionPane.OK_OPTION)==0)
                //{
                funcEditable = false;
                query.Close();
                return;
                //}
            }

            t=new DefaultTableModel();
            jTable1.setModel(t);
      
            t.addColumn("Seq"); 
            t.addColumn("Name"); 
            t.addColumn("Domain"); 
            t.addColumn("Default Value"); 
            t.addColumn("In/Out");
            //t.addColumn("dom_id"); 
            
            jTable1.setColumnSelectionAllowed(false);

            rs1=query.select("select Dom_mnem,Dom_id from IISC_DOMAIN where PR_id="+tree.ID + " order by Dom_mnem");
            boolean setselect = true;
            String dom_mnem="";
            while (rs1.next())
            {
                  returnCombo.addItem(rs1.getString("Dom_mnem"));
                  if (setselect)
                  {
                      dom_fun_id = rs1.getString("Dom_id");
                      setselect = false;
                  }
        
                  //jComboBox1.addItem(new Object[] { rs1.getString("Dom_mnem") , rs1.getString("Dom_id") } );
                  //System.out.println("Ime:"+jComboBox1.getSelectedObjects()[0]+"vrijednost:"+jComboBox1.getSelectedObjects()[1]);
            }
              
            query.Close();   
      
          rs=query.select("select * from IISC_FUNCTION  where PR_id=" + tree.ID + " order by Fun_name");
          String mnemtmp = Mnem;
          can=false;
          cmbFun.addItem("");
          while(rs.next())
          {
              String funnamecmb = rs.getString("Fun_name");
              String Fun_id = rs.getString("Fun_id");

              rs1=query2.select("select F.*,D.Dom_mnem from IISC_FUN_PARAM F,IISC_DOMAIN D where F.Dom_id = D.Dom_id AND F.PR_id = D.PR_id AND F.PR_id= " + tree.ID + " AND F.Fun_id = "+Fun_id+" order by F.Param_seq");
                
              funnamecmb = funnamecmb + " ( ";
              int itemp = 1;
                
              while(rs1.next())
              {
                  String domtemp= rs1.getString("Dom_mnem");
                  funnamecmb = funnamecmb + domtemp + " , ";  
                  itemp = 2;
              }
                
              funnamecmb = funnamecmb.substring(0,funnamecmb.length()-itemp) + " )";
              cmbFun.addItem(funnamecmb);
              query2.Close();
          }
          can=true;
            query.Close();
            Mnem = mnemtmp ;
            cmbFun.setSelectedItem(Mnem);
    
            btnNew.setEnabled(true);
            btnApply.setEnabled(false);
            btnErase.setEnabled(false);
            btnSave.setEnabled(false);            
      }
      catch(Exception ex)
      {
            ex.printStackTrace();
      }
      //jComboBox1.setSelectedIndex(0);
      //System.out.println("dom_id:"+dom_id+"dom:"+jComboBox1.getSelectedItem()+"");
    setFunction(Mnem);
    
  }

  private void this_windowOpened(WindowEvent e)
  {
      if (!funcEditable)
          this.dispose();
  }

  private void addParam_actionPerformed(ActionEvent e)
  {
      AddDisplayValue dialog = new AddDisplayValue("","","Add Parameter",parent,"","In",connection,tree);
      Settings.Center(dialog);
      dialog.setVisible(true); 
      
      
      if (dialog.action == dialog.SAVE)
      {
          btnApply.setEnabled(true);
          btnSave.setEnabled(true);
          String seq = "" + (t.getRowCount()+1);
          t.addRow(new Object[] {seq,dialog.name,dialog.domain,dialog.dafaultValue,dialog.iodefault});
      }
      dialog.dispose();    
  }

  private void editParam_actionPerformed(ActionEvent e)
  {
      if(jTable1.getSelectedRowCount() == 0)
          return;
      
      AddDisplayValue dialog = new AddDisplayValue(t.getValueAt(jTable1.getSelectedRow(),1).toString(),t.getValueAt(jTable1.getSelectedRow(),2).toString(),"Edit Parameter",parent,t.getValueAt(jTable1.getSelectedRow(),3).toString(),t.getValueAt(jTable1.getSelectedRow(),4).toString(),connection,tree);
      Settings.Center(dialog);
      dialog.setVisible(true); 
      
     
      if (dialog.action == dialog.SAVE)
      {
          //parent.btnApply.setEnabled(true);
          //parent.btnSave.setEnabled(true);
          t.setValueAt(dialog.name,jTable1.getSelectedRow(),1);
          t.setValueAt(dialog.domain,jTable1.getSelectedRow(),2);
          t.setValueAt(dialog.dafaultValue,jTable1.getSelectedRow(),3);
          t.setValueAt(dialog.iodefault,jTable1.getSelectedRow(),4);
          btnApply.setEnabled(true);
          btnSave.setEnabled(true);
//          t.addRow(new Object[] {,dialog.name,dialog.domain,dialog.dafaultValue});
      }
      dialog.dispose();  
  }

  private void delParam_actionPerformed(ActionEvent e)
  {
      int action = JOptionPane.showConfirmDialog(this, "Confirm delete", "Function Parameter", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

      int selectedRows[] = jTable1.getSelectedRows() ;      
      if (selectedRows.length != 0 )
      {
          if (action == JOptionPane.OK_OPTION)
          {
          
              int i = selectedRows.length - 1;
              while( i >= 0 )
              {
                  //System.out.println("params:"+selectedRows[i]+"");
                  t.removeRow(selectedRows[i--]);
              }
              
              i=0;
              while ( i < t.getRowCount() )
              {
                t.setValueAt((i+1)+"",i,0);
                i++;
              }
             
              btnApply.setEnabled(true);
              btnSave.setEnabled(true);  
          }
      }  
  }

  private void moveUpParam_actionPerformed(ActionEvent e)
  {
      btnApply.setEnabled(true);
      btnSave.setEnabled(true);
      
      int selectedRows[] = jTable1.getSelectedRows() ;

      if (selectedRows.length > 0 && selectedRows[0] != 0 )
      {
          int j=0;
          while ( j < selectedRows.length )
          {
            t.moveRow(selectedRows[j],selectedRows[j],selectedRows[j]-1);
            j++;
          }
          
          j=0;
          while ( j < t.getRowCount() )
          {
            t.setValueAt((j+1)+"",j,0);
            j++;
          }
          jTable1.setRowSelectionInterval(selectedRows[0]-1,selectedRows[0]-1);
          btnApply.setEnabled(true);
          btnSave.setEnabled(true);  
      }  
  }

  private void moveDownParam_actionPerformed(ActionEvent e)
  {
      btnApply.setEnabled(true);
      btnSave.setEnabled(true);
      
      int selectedRows[] = jTable1.getSelectedRows() ;

      if (selectedRows.length > 0 && selectedRows[selectedRows.length - 1] != jTable1.getRowCount() - 1 )
      {
          int j = selectedRows.length - 1;
          while ( j >= 0 )
          {
            t.moveRow(selectedRows[j],selectedRows[j],selectedRows[j]+1);
            j--;
          }
          
          j=0;
          while ( j < t.getRowCount() )
          {
            t.setValueAt((j+1)+"",j,0);
            j++;
          }
          jTable1.setRowSelectionInterval(selectedRows[selectedRows.length - 1] + 1,selectedRows[selectedRows.length - 1] + 1);
          btnApply.setEnabled(true);
          btnSave.setEnabled(true);  
      }  
  }



  private void returnCombo_actionPerformed(ActionEvent e)
  {
      try
      {
            //btnApply.setEnabled(true);
            //btnSave.setEnabled(true);
      
            JDBCQuery query=new JDBCQuery(connection);
            ResultSet rs; 
      
            //System.out.println("select Dom_id from IISC_DOMAIN  where PR_id="+ tree.ID +" and Dom_mnem='"+ jComboBox1.getSelectedItem() +"'");
            rs=query.select("select Dom_id from IISC_DOMAIN  where PR_id="+ tree.ID +" and Dom_mnem='"+ returnCombo.getSelectedItem() +"'");
              
            if ( rs.next() )
            {
                dom_fun_id = rs.getString("Dom_id");
                /*if (cmbenable)
                {
                    btnApply.setEnabled(true);
                    btnSave.setEnabled(true);  
                }
                */
            }
            //System.out.println("dom_id:"+dom_id+"dom:"+jComboBox1.getSelectedItem()+"");
            
            query.Close();
      }
      catch( Exception ex)
      {
          System.out.println("ex:"+ex);
      }  
  }

  private void txtComment_keyPressed(KeyEvent e)
  {
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);  
  }


  private void txtDescription_keyPressed(KeyEvent e)
  {
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);    
  }

  private void btnClose_actionPerformed(ActionEvent e)
  {
  
        if(btnSave.isEnabled()) //ako je save digne enabled pita se da li hoce da snimi promjene
          if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Function", JOptionPane.YES_NO_OPTION)==0)
            update(1);
            
        this.dispose();   
  }

  private void btnApply_actionPerformed(ActionEvent e)
  {
      dispose=false;
      update(1);
      //cmbFun.cmb
      //cmbFun.setSelectedItem(Mnem);
      //cmbFun_actionPerformed(e);
      tree.select_node(Mnem,"Functions");  
  }

  private void btnSave_actionPerformed(ActionEvent e)
  {
      dispose=true;
      update(1);
      tree.select_node(Mnem,"Functions");   
  }

  private void btnNew_actionPerformed(ActionEvent e)
  {
      Mnem="";
      id=-1;
      cmbFun.setSelectedItem(Mnem);
      setFunction(Mnem);
  }

  private void btnErase_actionPerformed(ActionEvent e)
  {
      if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to delete function?", "Function", JOptionPane.YES_NO_OPTION)==0 && delete())
      {
          //tree.removenode(Mnem,"Functions");
          Mnem="";
          cmbFun.setSelectedItem(Mnem);
          //setFunction(Mnem);
          //tree.pravi_drvo();
          //tree.select_node(Mnem,"Functions");
      }  
  }


/*
 *          SETOVANJE FUNKCIJA tj
 *          popunjavanje odredjenjih Parametera - neophodnih 
 */
  public void setFunction (String m)
  {
      try
      {
          //zamrzavanje dogmjeta apply i save
          btnApply.setEnabled(false);
          btnSave.setEnabled(false);
          btnErase.setEnabled(false);
          
          ResultSet rs,rs1;
          JDBCQuery query=new JDBCQuery(connection);
          JDBCQuery query2=new JDBCQuery(connection);
            
         
            
          int br_row=jTable1.getRowCount();
          //System.out.println("broj redova:"+jTable1.getRowCount());
          for(int i=0;i<br_row;i++)
            t.removeRow(0);  
            
        /*JComboBox comboBox = new JComboBox();
        comboBox.addItem("Snowboarding");
        comboBox.addItem("Rowing");
        comboBox.addItem("Knitting");
        comboBox.addItem("Speed reading");
        comboBox.addItem("Pool");
        comboBox.addItem("None of the above");*/
        //JCheckBox a = new JCheckBox();
        //a.setSelected(true);
        //JLabel aa = new JLabel();
        //aa.setText("ss");
        
/*class JComponentCellRenderer extends JCheckBox implements TableCellRenderer
{
    public Component getTableCellRendererComponent(JTable table, Object value,
		boolean isSelected, boolean hasFocus, int row, int column) {
    
      return this;
    }
}
*/
        
        
        //jTable1.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboBox));
        //jTable1.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(a));
        
        //jTable1.getColumnModel().getColumn(1).setCellRenderer(new JComponentCellRenderer());
        //jTable1.getColumnModel().getColumn(1).setCellRenderer(new JComponentCellRenderer());
        

          if(Mnem.equals(""))
          {
            txtName.setText("");
            txtDescription.setText("");
            txtComment.setText("");
            tree.select_node(Mnem,"Functions");
            //tree.pravi_drvo();
          }
          else
          {
            String jov = Mnem.split(" ")[0];
            //System.out.println("select Fun_name,Fun_id from IISC_Function where PR_id="+ tree.ID +"  and Fun_name='"+ Mnem.split(" ")[0] +"' ");
            rs=query.select("select Fun_name,Fun_id from IISC_Function where PR_id="+ tree.ID +"  and Fun_name='"+ Mnem.split(" ")[0] +"' ");
            
            String Fun_id ="";
            
            while(rs.next())
            {
                String funnamecmb = rs.getString("Fun_name");
                Fun_id = rs.getString("Fun_id");

                rs1=query2.select("select F.*,D.Dom_mnem from IISC_FUN_PARAM F,IISC_DOMAIN D where F.Dom_id = D.Dom_id AND F.PR_id = D.PR_id AND F.Fun_id = "+Fun_id+" order by F.Param_seq");
                
                funnamecmb = funnamecmb + " ( ";
                int itemp = 1;
                  
                while(rs1.next())
                {
                    String domtemp= rs1.getString("Dom_mnem");
                    funnamecmb = funnamecmb + domtemp + " , ";  
                    itemp = 2;
                }
                
                funnamecmb = funnamecmb.substring(0,funnamecmb.length()-itemp) + " )";
              
                if (Mnem.equals(funnamecmb))
                {
                    query2.Close();
                    break;
                }
              
                Fun_id ="";
                query2.Close();
            }
            query.Close();

              //ucitavamo parametre za trazenu funkciju
              //System.out.println("select * from IISC_Function,IISC_Domain where IISC_FUNCTION.PR_id="+ tree.ID +"  and Fun_id="+ Fun_id +" and IISC_Function.Dom_id = IISC_Domain.Dom_id and IISC_Function.PR_id = IISC_Domain.PR_id ");
              rs1=query.select("select * from IISC_Function,IISC_Domain where Fun_id="+ Fun_id +" and IISC_Function.Dom_id = IISC_Domain.Dom_id ");
            
              if(rs1.next())
              {
                  txtName.setText(rs1.getString("Fun_name"));
                  txtDescription.setText(rs1.getString("Fun_desc"));
                  txtComment.setText(rs1.getString("Fun_comment"));
                  returnCombo.setSelectedItem(rs1.getString("Dom_mnem"));
                  dom_fun_id = rs1.getString("Dom_id");
                  id=rs1.getInt("Fun_id");
                  
                  query.Close();
                  
                  rs=query.select("select * from IISC_FUN_PARAM,IISC_DOMAIN where Fun_id = "+ id +" and IISC_FUN_PARAM.Dom_id = IISC_DOMAIN.Dom_id order by Param_seq ");
                  //System.out.println("select * from IISC_FUN_PARAM,IISC_DOMAIN where IISC_FUN_PARAM.PR_id="+ tree.ID +"  and Fun_id = "+ id +" and IISC_FUN_PARAM.Dom_id = IISC_DOMAIN.Dom_id and IISC_FUN_PARAM.PR_id = IISC_DOMAIN.PR_id order by Param_seq ");

                  String inoutarray[] = new String[3];
                  inoutarray[0] = "In";
                  inoutarray[1] = "Out";
                  inoutarray[2] = "In/Out";
                  while(rs.next())
                  {
                      t.addRow(new Object[] {rs.getString("Param_seq"), rs.getString("Param_name"),rs.getString("Dom_mnem"),rs.getString("Param_def_val"),inoutarray[rs.getInt("inout")]});
                  }
                  query.Close();
              }
              btnErase.setEnabled(true);
          }
     
      }
      catch(SQLException e)
      {
          JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
  }






  private void txtName_keyReleased(KeyEvent e)
  {
          //System.out.println("dugme:"+ e.getKeyChar());
          //System.out.println("dugme2:"+ e.getKeyCode());
              int i=0;
              btnApply.setEnabled(true);
              btnSave.setEnabled(true);
              while(i<txtName.getText().length())
              {
                  if (txtName.getText().charAt(txtName.getText().length()-1)=='_')
                  {
                      txtName.setText(txtName.getText().substring(0,txtName.getText().length()-1));
                      i--;
                  }
                  else if ( txtName.getText().length() > 0 && ( txtName.getText().charAt(0) >= '0' && txtName.getText().charAt(0) <= '9') || txtName.getText().charAt(0)==' ' || txtName.getText().charAt(0)=='_' )
                  {
                      txtName.setText(txtName.getText().substring(1));    
                      i=0;
                  }
/*                  else if (txtName.getText().charAt(txtName.getText().length()-1)== '-' || txtName.getText().charAt(txtName.getText().length()-1)== '_')
                  {
                      txtName.setText(txtName.getText().substring(0,txtName.getText().length()-1));
                  }
                  */
                  else if  ((txtName.getText().charAt(i) < 'a' || txtName.getText().charAt(i) > 'z') && (txtName.getText().charAt(i) < '0' || txtName.getText().charAt(i) > '9' )&& (txtName.getText().charAt(i) < 'A' || txtName.getText().charAt(i) > 'Z' ) && txtName.getText().charAt(i)!='_')
                  {
                      txtName.setText(txtName.getText().substring(0,i)+txtName.getText().substring(i+1));   
                      i--;
                  }
                  i++;
              }        
              //else txtNameBool = txtName.getText().length();

  }

  private void cmbFun_actionPerformed(ActionEvent e)
  {
            if(can)Mnem=cmbFun.getSelectedItem().toString();
            //System.out.println("index cmbFun:"+cmbFun.getSelectedIndex());
            //System.out.println("MNEM:"+Mnem);
            tmpFunName = Mnem;
            //System.out.println("count cmbFun:"+cmbFun.getItemCount());
            if (cmbFun.getSelectedIndex()==0)
            {
                btnPrev.setEnabled(false);
                btnFirst.setEnabled(false);
            }
            else
            {
                btnPrev.setEnabled(true);
                btnFirst.setEnabled(true);              
            }
            
            if(cmbFun.getSelectedIndex()== cmbFun.getItemCount()-1)
            {
                btnNext.setEnabled(false);
                btnLast.setEnabled(false);
            }
            else
            {
                btnNext.setEnabled(true);
                btnLast.setEnabled(true);              
            }
            
            if(Mnem.length() > 0)
                btnErase.setEnabled(true);
            else
                btnErase.setEnabled(false);            
            //System.out.println("index jovo:"+cmbFun.getSelectedIndex());
            //System.out.println(""+Mnem);
            setFunction(Mnem);
            if(Mnem.trim().equals("")){
                jBtnAlg.setEnabled(false);
                jBtnAlg2.setEnabled(false);
                jBtnAlg3.setEnabled(false);
            }
            else{
                jBtnAlg.setEnabled(true);
                jBtnAlg2.setEnabled(true);
                jBtnAlg3.setEnabled(true);
            }
            //tree.select_node(Mnem,"Functions");  
  }

  private void btnPrev_actionPerformed(ActionEvent e)
  {
        if(btnSave.isEnabled()) //ako je save digne enabled pita se da li hoce da snimi promjene
            if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Function", JOptionPane.YES_NO_OPTION)==0)
              update(1);        
              
        if (cmbFun.getSelectedIndex()!=0)
        {                  
            cmbFun.setSelectedIndex(cmbFun.getSelectedIndex()-1);
        }
  }

  private void btnFirst_actionPerformed(ActionEvent e)
  {
        if(btnSave.isEnabled()) //ako je save digne enabled pita se da li hoce da snimi promjene
            if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Function", JOptionPane.YES_NO_OPTION)==0)
                update(1);        
                
        if (cmbFun.getSelectedIndex()!=0)
        {                    
            cmbFun.setSelectedIndex(0);
        }
  }

  private void btnNext_actionPerformed(ActionEvent e)
  {
      if(btnSave.isEnabled()) //ako je save digne enabled pita se da li hoce da snimi promjene
        if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Function", JOptionPane.YES_NO_OPTION)==0)
              update(1);
 
      if (cmbFun.getSelectedIndex()!=cmbFun.getItemCount()-1)
      {            
          cmbFun.setSelectedIndex(cmbFun.getSelectedIndex()+1);
      }
  }

  private void btnLast_actionPerformed(ActionEvent e)
  {
      if(btnSave.isEnabled()) //ako je save digne enabled pita se da li hoce da snimi promjene
          if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Function", JOptionPane.YES_NO_OPTION)==0)
              update(1);      
              
      if (cmbFun.getSelectedIndex()!=cmbFun.getItemCount()-1)
      {
          cmbFun.setSelectedIndex(cmbFun.getItemCount()-1);  
      }
  }


  public void update (int k)
  {
      JDBCQuery query = new JDBCQuery(connection);
      JDBCQuery query2 = new JDBCQuery(connection);
      ResultSet rs1,rs2;  
      boolean can=true; //promjenjiva kojom utvrdjujemo da li je moguce uraditi update funkcije
      
      //System.out.println(txtName.getText().trim());
      //System.out.println("jovo mnem func"+Mnem);

      try
      {
          String funnamecmb2 ="";
          /*if( Mnem.trim().equals("") || ( Mnem.length() > 0 && !Mnem.split(" ")[0].trim().equals(txtName.getText().trim())))// mnem je prazan ako je nova funkcija 
          {
          */
              
              funnamecmb2 = txtName.getText().trim();
              funnamecmb2 = funnamecmb2 + " ( ";              
              
              int brteb = 0; 
              int itemp2 = 1;
              while(brteb < jTable1.getRowCount())
              {
                     funnamecmb2 = funnamecmb2 + jTable1.getValueAt(brteb,2) + " , "; 
                     itemp2 = 2;
                     brteb++;
              }  
              funnamecmb2 = funnamecmb2.substring(0,funnamecmb2.length()-itemp2) + " )";
              
                //trazi se da li postoji funcija sa tim imenom u projektu
                rs1=query.select("select Fun_id,Fun_name from IISC_Function  where PR_id="+ tree.ID +" and Fun_name='"+ txtName.getText().trim() +"'");              
              while(rs1.next())
              {
                    String Fun_id ="";
                    String funnamecmb = rs1.getString("Fun_name").trim();
                    
                    Fun_id = rs1.getString("Fun_id");                    

                    rs2=query2.select("select F.*,D.Dom_mnem from IISC_FUN_PARAM F,IISC_DOMAIN D where F.Dom_id = D.Dom_id AND F.Fun_id = "+Fun_id+" order by F.Param_seq");
                  
                    funnamecmb = funnamecmb + " ( ";
                    int itemp = 1;
                   
                    while(rs2.next())
                    {
                          funnamecmb = funnamecmb + rs2.getString("Dom_mnem") + " , ";  
                          itemp = 2;
                    }
                
                    funnamecmb = funnamecmb.substring(0,funnamecmb.length()-itemp) + " )";
                    
                    //System.out.println("combo: " + cmbFun.getSelectedItem())              ;
                    //System.out.println("funna: " + funnamecmb);
                    //System.out.println("finn2: " + funnamecmb2);
                    if (funnamecmb2.equals(funnamecmb))
                    {
                        if (Mnem == "" || !cmbFun.getSelectedItem().equals(funnamecmb2))//txtName.getText() != Mnem )
                        {
                            can=false;
                            JOptionPane.showMessageDialog(null, "<html><center>Function with that name and parameters exists!\nTry diferent name or diferent order of parameters!", "Functions", JOptionPane.INFORMATION_MESSAGE);                          
                            query2.Close();
                        }
                        break;
                    }
              
                    Fun_id ="";
                    query2.Close();
  
                  //JOptionPane.showMessageDialog(null, "<html><center>Name exists!", "Error", JOptionPane.ERROR_MESSAGE);
                  //can=false;
              }
              
                   /*rs2=query2.select("select count(*) from IISC_PACK_FUN where Pack_id = " + tree.ID + " and fun_id = " + id);
                   String err = "";
                   if(rs2.next())
                   {
                        can = false;
                        err = "<html><center>Function is included in packages !";
                   }*/

              
              query.Close();
         // }

          if(txtName.getText().trim().equals("")) //da li je zadato ime funkcije
            JOptionPane.showMessageDialog(null, "<html><center>Name required!", "Functions", JOptionPane.INFORMATION_MESSAGE);
          else if(txtDescription.getText().trim().equals(""))//da li postoji description
            JOptionPane.showMessageDialog(null, "<html><center>Description required!", "Functions", JOptionPane.INFORMATION_MESSAGE);
          /*else if(!err.equals(""))
            JOptionPane.showMessageDialog(null, err, "Functions", JOptionPane.INFORMATION_MESSAGE);*/
          else if(can)
          {
              int selekcija = 0;
              
              if(Mnem.trim().equals(""))// ako je moguce odraditi insert nove funkcije
              {
                  selekcija = 1;
                  rs1=query.select("select max(Fun_id)+1  from IISC_Function");
                  
                  if(rs1.next())
                    id=rs1.getInt(1);
                  else
                    id=0;
      
                  query.Close();
                  
                  //insert nove funkcije
                  int i=query.update("insert into IISC_Function(Fun_id,PR_id,Fun_name,Fun_desc,Fun_comment,Dom_id) values ("+ id +","+ tree.ID +", '" + txtName.getText().trim() + "','" + txtDescription.getText().trim() + "','" + txtComment.getText().trim() + "',"+dom_fun_id+")");

                  //Mnem=txtName.getText().trim();                  
                  //tree.insert(Mnem,"Functions");
                  //tree.insert("ddd",txtName.getText().trim());

              }
              else
              {
                 try 
                 {
                    Statement st = connection.createStatement();
                    ResultSet chekcrs = st.executeQuery("Select * from IISC_ATT_TOB WHERE Fun_id=" + id);
                    
                    if (chekcrs.next())
                    {
                        JOptionPane.showMessageDialog(null, "<html><center>Function can not be altered. It is used for attribute calculation!", "Functions", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                     chekcrs = st.executeQuery("Select * from IISC_ATT_TOB_EVENT WHERE Fun_id=" + id);
                                         
                     if (chekcrs.next())
                     {
                         JOptionPane.showMessageDialog(null, "<html><center>Function can not be altered. It is used for event specification!", "Functions", JOptionPane.ERROR_MESSAGE);
                         return;
                     }
                 }
                 catch(Exception ex)
                 {
                 }
                  selekcija = 2;
                  //update postojece funkcije
                  //Mnem=txtName.getText().trim();
                  int i=query.update("update IISC_Function set Fun_name='" + txtName.getText().trim() + "',Fun_desc='" + txtDescription.getText().trim() + "',Fun_comment='" + txtComment.getText().trim() + "', Dom_id="+dom_fun_id+" where Fun_id=" + id + " "); 
                  int j=query.update("delete from IISC_FUN_PARAM WHERE  Fun_id = "+id+" and PR_ID="+tree.ID); 

                  //tree.change(Mnem ,"Functions",txtName.getText().trim());
              }
              
              //System.out.println(""+jTable1.getRowCount());
              if (jTable1.getRowCount() != 0)
              {
                  int idp;
                  rs1=query.select("select max(Param_id)+1  from IISC_FUN_PARAM ");// where Fun_id = "+id+" AND PR_id="+tree.ID+"");                  
                  //System.out.println("select max(Param_id)+1  from IISC_FUN_PARAM where Fun_id = "+id+" AND PR_id="+tree.ID+"");
                  if( rs1.next() )
                    idp=rs1.getInt(1);
                  else
                    idp=0;                  
                  
                  query.Close();
                  
                  int  iinsert=0;

                  while (iinsert < jTable1.getRowCount())
                  {
                      rs1=query.select("select Dom_id FROM IISC_DOMAIN where PR_id="+tree.ID+" AND Dom_mnem = '"+jTable1.getValueAt(iinsert,2)+"'");                                        
                      //System.out.println("select Dom_id FROM IISC_DOMAIN where PR_id="+tree.ID+" AND Dom_mnem = '"+jTable1.getValueAt(iinsert,2)+"'");
                      String dom_id_param="";
                          
                      if( rs1.next() )
                        dom_id_param = rs1.getString("Dom_id");                            
                      else
                        break;
                        
                      query.Close();
                        
                      int inoutparam = 0;
                  
                      if (jTable1.getValueAt(iinsert,4) == "In/Out")
                        inoutparam = 2;
                      else if (jTable1.getValueAt(iinsert,4) == "Out")
                        inoutparam = 1;                        
                        
                      //System.out.println("insert into IISC_FUN_PARAM(Param_id,PR_id,Fun_id,Param_seq,Param_name,Param_def_val,Dom_id,inout) values ("+idp+","+tree.ID+","+id+","+jTable1.getValueAt(iinsert,0)+",'"+jTable1.getValueAt(iinsert,1)+"','"+jTable1.getValueAt(iinsert,3)+"',"+dom_id_param+","+inoutparam+")");                            
                      int j=query.update("insert into IISC_FUN_PARAM(Param_id,PR_id,Fun_id,Param_seq,Param_name,Param_def_val,Dom_id,inout) values ("+(idp++)+","+tree.ID+","+id+","+jTable1.getValueAt(iinsert,0)+",'"+jTable1.getValueAt(iinsert,1)+"','"+jTable1.getValueAt(iinsert,3)+"',"+dom_id_param+","+inoutparam+")");
                      iinsert++;
                  }     
              } 
              
              if (selekcija == 1)
              {
                  tree.insert(funnamecmb2,"Functions");
                  cmbFun.addItem(funnamecmb2);
                  cmbFun.setSelectedItem(funnamecmb2);
              }
              else if (selekcija == 2)
              {
                  tree.change(Mnem ,"Functions",funnamecmb2);
                  cmbFun.removeItemAt(cmbFun.getSelectedIndex());
                  //cmbFun.cha
                  cmbFun.addItem(funnamecmb2);         
                  cmbFun.setSelectedItem(funnamecmb2);
              }
            
              if(k==1)
                  JOptionPane.showMessageDialog(null, "<html><center>Function saved!", "Functions", JOptionPane.INFORMATION_MESSAGE);
  
              //setFunction(Mnem);
              

              if(dispose)
                  dispose();
          }
      }   
      catch(SQLException e)
      {
          JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }

  } 

  private void returnCombo_focusGained(FocusEvent e)
  {
      btnApply.setEnabled(true);
      btnSave.setEnabled(true);   
  }
/*
 * funkcija za brisanje funk
 */
  public boolean delete()
  {
      JDBCQuery query=new JDBCQuery(connection);
      ResultSet rs1;  
      boolean can=true;
      
      if(Mnem.equals("")) //ne moze se izbrisati ako funkcija nije odabrana
        can=false;

      try
      {
          rs1=query.select("select * from IISC_DER_ATT_FUN  where Fun_id="+ id + " ");
          if(rs1.next())
          {
              JOptionPane.showMessageDialog(null, "<html><center>Function can not be removed!<br>Delete from DER_ATT_FUN first.", "Error", JOptionPane.ERROR_MESSAGE);
              can=false;
          } 
          
          query.Close();
          rs1=query.select("select * from IISC_ATTRIBUTE  where Fun_id="+ id + " ");
          
          if(rs1.next())
          { 
                JOptionPane.showMessageDialog(null, "<html><center>Function can not be removed!<br>Delete from Attributes first.", "Error", JOptionPane.ERROR_MESSAGE);
              can=false;
          } 
 
          query.Close();
          
          rs1=query.select("select * from IISC_PACKAGE,IISC_PACK_FUN  where IISC_PACKAGE.PAck_id = IISC_PACK_FUN.Pack_id and IISC_PACKAGE.PR_ID = "+tree.ID+" and  Fun_id="+ id + " ");
          if(rs1.next())
          {
              JOptionPane.showMessageDialog(null, "<html><center>Function can not be removed!<br>Delete from Packages first.", "Error", JOptionPane.ERROR_MESSAGE);
              can=false;
          } 
          
          query.Close();          
          
          if(can)
          {
              int i=query.update("delete from IISC_FUN_PARAM where Fun_id=" + id + " and PR_ID = " + tree.ID); 
              int jj=query.update("delete from IISC_FUN_BODY WHERE Fun_id = "+id+" and PR_ID = " + tree.ID); 
              query.update("delete from IISC_FUN_COMPILED_CODE where Fun_id=" + id); 
              query.update("delete from IISC_FUN_TEXT where Fun_id=" + id); 
              i=query.update("delete from IISC_Function where Fun_id=" + id + " and PR_ID = " + tree.ID); 
              
              tree.removenode(Mnem,"Functions");
              cmbFun.removeItem(Mnem);
              //cmbFun.setSelectedIndex(0);              
              JOptionPane.showMessageDialog(null, "<html><center>Function removed!", "Functions", JOptionPane.INFORMATION_MESSAGE);
          }
      }
      catch(SQLException e)
      {
          JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    
      return can;
  }

  private void jBtnAlg_actionPerformed(ActionEvent e){
      /*funAlg a = new funAlg(this, "Function", true,connection,id);
      Settings.Center(a);
      //WindowsManager.add(a);
      a.setVisible(true);    
      */
       /*FunctionEditor a = new FunctionEditor(parent,"",true,connection,id,1,null,"","1",String.valueOf(tree.ID));
       a.setVisible(true);*/
       try 
       {
            JSourceCodeEditor editor = new JSourceCodeEditor(parent, Integer.toString(id), connection, tree.ID);
            Settings.Center(editor);
            editor.setVisible(true);
            editor = null;
            System.gc(); 
            System.runFinalization();
       }
       catch(Exception ex)
       {}
  } 

  private void jButton2_actionPerformed(ActionEvent e)
  {
      JSourceCodeEditor editor = new JSourceCodeEditor(parent, Integer.toString(id), connection, tree.ID);
      Settings.Center(editor);
      editor.setVisible(true);
      editor = null;
      System.gc(); 
      System.runFinalization();
  }

 

    private void jButton1_actionPerformed(ActionEvent e){
         /*FunctionEditor a = new FunctionEditor(parent,"",true,connection,id,1,null,"","1",String.valueOf(tree.ID));
         a.setVisible(true);      */
          JSourceCodeEditor editor = new JSourceCodeEditor(parent, Integer.toString(id), connection, tree.ID);
          Settings.Center(editor);
          editor.setVisible(true);
          editor = null;
          System.gc(); 
          System.runFinalization();
    }


    private void funcDomainSelect_actionPerformed(ActionEvent e) {
        SearchTable ptype=new SearchTable((Frame)getParent(),"Select User Define Domain",true,connection,String.valueOf(tree.ID),"","",0);
        Settings.Center(ptype);
        ptype.type="Domain3";
        ptype.owner=this;
        ptype.setVisible(true);    
    }

}


