package iisc;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;

import java.sql.*;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JList;
import java.awt.Rectangle;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JToolBar;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Packages extends JDialog 
{
  IISFrameMain parent;
  
  private int packid;
  private JComboBox cmbPackType = new JComboBox();
  private DefaultListModel modelLstFun=new DefaultListModel();
  private JList jListFun = new JList(modelLstFun);  
  private DefaultListModel modelLstFun2=new DefaultListModel();  
  private JList jListFun2 = new JList(modelLstFun2);  
  private JButton jButton1 = new JButton();
  private JButton jButton2 = new JButton();
  private Connection connection;
  private int packType;
  private PTree tree;
  private String Mnem;
  private String pr_func;
  private String pack_func;
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JScrollPane jScrollPane2 = new JScrollPane();
  private JTabbedPane jTabbedPane1 = new JTabbedPane();
  private JPanel jPanel1 = new JPanel();
  private JPanel jPanel2 = new JPanel();
  private JPanel jPanel3 = new JPanel();
  private JLabel jLabel3 = new JLabel();
  private JTextArea txtPackDesc = new JTextArea();
  private JComboBox cmbPackage = new JComboBox();
  private JLabel jLabel4 = new JLabel();
  private JLabel jLabel5 = new JLabel();
  private ImageIcon iconMoveUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
  private ImageIcon iconMoveDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));  
  private ImageIcon iconPrevv = new ImageIcon(IISFrameMain.class.getResource("icons/prevv.gif"));
  private ImageIcon iconPrev = new ImageIcon(IISFrameMain.class.getResource("icons/prev.gif"));
  private ImageIcon iconNext = new ImageIcon(IISFrameMain.class.getResource("icons/next.gif"));
  private ImageIcon iconNextt = new ImageIcon(IISFrameMain.class.getResource("icons/nextt.gif"));  
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));  
  private JTextField txtPackName = new JTextField();
  private JLabel jLabel6 = new JLabel();
  private JButton btnFirst = new JButton();
  private JButton btnPrev = new JButton();
  private JButton btnNext = new JButton();
  private JButton btnLast = new JButton();
  private JToolBar jToolBar1 = new JToolBar();
  private JButton btnApply = new JButton();
  private JButton btnNew = new JButton();
  private JButton btnErase = new JButton();
  private JButton btnSave = new JButton();
  private JButton btnClose = new JButton();
  private JButton btnName = new JButton();
  private JLabel jLabel1 = new JLabel();
  private JTextArea txtPackCom = new JTextArea();

  public Packages()
  {
    this(null, "", false);
  }
  
  public void setType(int i){
      cmbPackType.setSelectedIndex(i);
  }

  public Packages(IISFrameMain parent, String title, boolean modal,Connection con,int m , PTree tr )
  {
      super((Frame) parent, title, modal);
      try
      {   
          tree=tr;
          connection=con;
          jbInit();
          packid = m;          
          ResultSet rs;
          JDBCQuery query=new JDBCQuery(connection);
          //System.out.println("SELECT * FROM IISC_PACKAGE where Pack_id = "+packid);
          rs=query.select("SELECT * FROM IISC_PACKAGE where Pack_id = "+packid);
          
          if(rs.next())
          {
            packType = rs.getInt("Pack_type");
            cmbPackType.setSelectedIndex(packType - 1);            
            Mnem = rs.getString("Pack_name");            
            cmbPackage.setSelectedItem(Mnem);            
          }
          
          query.Close();
          
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
  }
  
  public Packages(IISFrameMain p_parent, String title, boolean modal)
  {
      super((Frame) p_parent, title, modal);
      try
      {
          parent = p_parent;
          jbInit();
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
  }
  

  /**
   * 
   * @param modal
   * @param title
   * @param parent
   */
  public Packages(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
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
    this.setSize(new Dimension(470, 436));
    this.getContentPane().setLayout(null);
    this.setTitle("Packages");
    cmbPackType.setBounds(new Rectangle(125, 5, 195, 20));
    jButton1.setBounds(new Rectangle(235, 115, 30, 25));
    jButton1.setIcon(iconMoveDown);
    jButton2.setBounds(new Rectangle(285, 115, 30, 25));
    jButton2.setIcon(iconMoveUp);
    jScrollPane1.setBounds(new Rectangle(120, 10, 320, 100));
    jScrollPane1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    jScrollPane2.setBounds(new Rectangle(120, 145, 320, 105));
    jScrollPane2.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    jTabbedPane1.setBounds(new Rectangle(5, 70, 450, 280));
    jTabbedPane1.setToolTipText("null");
    jPanel1.setLayout(null);
    jPanel2.setLayout(null);
    jPanel3.setLayout(null);
    jLabel3.setText("Description:");
    jLabel3.setBounds(new Rectangle(10, 40, 65, 20));
    txtPackDesc.setBounds(new Rectangle(80, 45, 360, 205));
    txtPackDesc.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    cmbPackage.setBounds(new Rectangle(85, 30, 285, 20));
    cmbPackage.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          cmbPackage_actionPerformed(e);
        }
      });
    jLabel4.setText("Standalone Functions:");
    jLabel4.setBounds(new Rectangle(5, 10, 120, 15));
    jLabel5.setText("Package Functions:");
    jLabel5.setBounds(new Rectangle(5, 145, 105, 15));
    txtPackName.setBounds(new Rectangle(80, 15, 360, 20));
    jLabel6.setText("Name:");
    jLabel6.setBounds(new Rectangle(10, 15, 50, 20));
    btnFirst.setBounds(new Rectangle(15, 30, 25, 20));
    btnFirst.setIcon(iconPrevv);
    btnPrev.setBounds(new Rectangle(50, 30, 20, 20));
    btnPrev.setIcon(iconPrev);
    btnNext.setBounds(new Rectangle(380, 30, 25, 20));
    btnNext.setIcon(iconNext);
    btnLast.setBounds(new Rectangle(415, 30, 25, 20));
    btnLast.setIcon(iconNextt);
    btnLast.setToolTipText("null");
    jToolBar1.setBounds(new Rectangle(5, 5, 445, 55));
    jToolBar1.setLayout(null);
    jToolBar1.setFloatable(false);
    jPanel1.add(jLabel5, null);
    jPanel1.add(jLabel4, null);
    jScrollPane1.getViewport().add(jListFun, null);
    jPanel1.add(jScrollPane1, null);
    jScrollPane2.getViewport().add(jListFun2, null);
    jPanel1.add(jScrollPane2, null);
    jPanel1.add(jButton2, null);
    jPanel1.add(jButton1, null);
    jPanel2.add(jLabel6, null);
    jPanel2.add(txtPackName, null);
    jPanel2.add(txtPackDesc, null);
    jPanel2.add(jLabel3, null);
    jTabbedPane1.addTab("Packages", jPanel2);
    jTabbedPane1.addTab("Functions", jPanel1);
    jPanel3.add(txtPackCom, null);
    jPanel3.add(jLabel1, null);
    jTabbedPane1.addTab("Notes", jPanel3);
    jToolBar1.add(btnLast, null);
    jToolBar1.add(btnNext, null);
    jToolBar1.add(btnPrev, null);
    jToolBar1.add(btnFirst, null);
    jToolBar1.add(cmbPackage, null);
    jToolBar1.add(cmbPackType, null);
    this.getContentPane().add(btnName, null);
    this.getContentPane().add(btnClose, null);
    this.getContentPane().add(btnSave, null);
    this.getContentPane().add(btnErase, null);
    this.getContentPane().add(btnNew, null);
    this.getContentPane().add(btnApply, null);
    this.getContentPane().add(jToolBar1, null);
    this.getContentPane().add(jTabbedPane1, null);
    Mnem = "";
    pr_func=",";
    packid = -1;
    pack_func=",";
    btnNext.setEnabled(true);
    btnLast.setEnabled(true);
    btnLast.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnLast_actionPerformed(e);
        }
      });
    btnNext.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnNext_actionPerformed(e);
        }
      });
    btnFirst.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnFirst_actionPerformed(e);
        }
      });
    btnPrev.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnPrev_actionPerformed(e);
        }
      });
    btnErase.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnErase_actionPerformed(e);
        }
      });
    btnClose.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnClose_actionPerformed(e);
        }
      });
    btnSave.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnSave_actionPerformed(e);
        }
      });
    btnApply.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnApply_actionPerformed(e);
        }
      });
    btnNew.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnNew_actionPerformed(e);
        }
      });
    txtPackName.addKeyListener(new KeyAdapter()
      {
        public void keyReleased(KeyEvent e)
        {
          txtPackName_keyReleased(e);
        }
      });
    txtPackCom.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtPackCom_keyPressed(e);
        }
      });
    txtPackDesc.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtPackDesc_keyPressed(e);
        }
      });
    txtPackCom.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    txtPackCom.setBounds(new Rectangle(5, 30, 435, 155));
    jLabel1.setBounds(new Rectangle(5, 5, 90, 20));
    jLabel1.setText("Comment");
    cmbPackType.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          cmbPackType_actionPerformed(e);
        }
      });
    btnName.setIcon(imageHelp);
    btnName.setBounds(new Rectangle(405, 360, 40, 30));
    btnClose.setBounds(new Rectangle(325, 360, 75, 30));
    btnClose.setText("Cancel");
    btnSave.setBounds(new Rectangle(245, 360, 75, 30));
    btnSave.setText("OK");
    btnErase.setBounds(new Rectangle(165, 360, 75, 30));
    btnErase.setText("Delete");
    btnNew.setBounds(new Rectangle(85, 360, 75, 30));
    btnNew.setText("New");
    btnApply.setBounds(new Rectangle(5, 360, 75, 30));
    btnApply.setText("Apply");
    jButton2.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton2_actionPerformed(e);
        }
      });
    jButton1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton1_actionPerformed(e);
        }
      });
      
    cmbPackType.addItem("DB Server");
    cmbPackType.addItem("Application Server");
    cmbPackType.addItem("Client");
    
    //cmbPackageLoad();
    btnApply.setEnabled(false);
    btnSave.setEnabled(false);
    btnErase.setEnabled(false);
  }

  private void cmbPackageLoad()
  {
    try
    {
            ResultSet rs2,rs1;
            JDBCQuery query=new JDBCQuery(connection);
            JDBCQuery query2=new JDBCQuery(connection);
            //System.out.println("tree.ID"+tree.ID);
            query=new JDBCQuery(connection); 
            rs2=query.select("select * from IISC_PACKAGE where PR_id="+ tree.ID +" and Pack_type = "+packType+" order by pack_name");
            //System.out.println("select * from IISC_PACKAGE where PR_id="+ tree.ID +" and Pack_type = "+packType);
            cmbPackage.removeAllItems();
            Mnem = "";
            cmbPackage.addItem(Mnem);
            while(rs2.next())
            {
                cmbPackage.addItem(rs2.getString("Pack_name"));
            }
            query.Close(); 
            
            if (cmbPackage.getItemCount()>1)
            {
              btnNext.setEnabled(true);
              btnLast.setEnabled(true);
            }
            //cmbPackage.setSelectedItem(Mnem);
            
    }
    catch (Exception ex)
    {
      System.out.println("Greska class Packages:"+ex);
    }    
  }

  private void setPackage(String mnem)
  {
      try
      {   
          ResultSet rs2,rs1;
          JDBCQuery query=new JDBCQuery(connection);
          JDBCQuery query2=new JDBCQuery(connection);  
                    
          if (mnem == "")
          {
              //cmbPackage.setSelectedIndex(0);        
              txtPackDesc.setText("");
              txtPackName.setText("");
              txtPackCom.setText("");
              packid = -1;
          }
          else
          {
                //System.out.println("tree.ID"+tree.ID);
                rs1=query.select("select * from IISC_Package where Pack_name='"+ mnem +"' and pack_type = "+packType+" order by pack_name");
                if(rs1.next())
                {
                  txtPackName.setText(rs1.getString("Pack_name"));  
                  txtPackDesc.setText(rs1.getString("Pack_desc"));
                  txtPackCom.setText(rs1.getString("Pack_comt"));
                  packid = rs1.getInt("Pack_id");
                }
                query.Close();

          }
          //System.out.println("Mnem u setpackage:"+mnem);          
          pr_func = ",";
          pack_func = ",";
      

          rs1=query.select("select * from IISC_Function where PR_id="+ tree.ID +" and Fun_id not in ( select Fun_id from IISC_PACK_FUN where Pack_id = "+packid+" ) order by Fun_name" );
          modelLstFun.removeAllElements();  
          while(rs1.next())
          {
              String funnamecmb = rs1.getString("Fun_name");
              int id = rs1.getInt("Fun_id");
              rs2=query2.select("select F.*,D.Dom_mnem from IISC_FUN_PARAM F,IISC_DOMAIN D where F.Dom_id = D.Dom_id AND F.PR_id= " + tree.ID + " AND F.Fun_id = "+id+" order by F.Param_seq");    
          
              funnamecmb = funnamecmb + " ( ";
              int itemp = 1;
            
              while(rs2.next())
              {
                  String domtemp= rs2.getString("Dom_mnem");
                  funnamecmb = funnamecmb + domtemp + " , ";  
                  itemp = 2;
              }
              pr_func += id+",";              
            
              funnamecmb = funnamecmb.substring(0,funnamecmb.length()-itemp) + " )";  
              modelLstFun.addElement(funnamecmb);

              query2.Close();
          }
          query.Close();    
          
          
          rs1=query.select("select * from IISC_Function where Fun_id in ( select Fun_id from IISC_PACK_FUN where Pack_id = "+packid+" ) order by fun_name" );
          modelLstFun2.removeAllElements();  
          while(rs1.next())
          {
              String funnamecmb = rs1.getString("Fun_name");
              int id = rs1.getInt("Fun_id");
              rs2=query2.select("select F.*,D.Dom_mnem from IISC_FUN_PARAM F,IISC_DOMAIN D where F.Dom_id = D.Dom_id AND F.PR_id= " + tree.ID + " AND F.Fun_id = "+id+" order by F.Param_seq");    
          
              funnamecmb = funnamecmb + " ( ";
              int itemp = 1;
            
              while(rs2.next())
              {
                  String domtemp= rs2.getString("Dom_mnem");
                  funnamecmb = funnamecmb + domtemp + " , ";  
                  itemp = 2;
              }
              pack_func += id+",";
            
              funnamecmb = funnamecmb.substring(0,funnamecmb.length()-itemp) + " )";  
              modelLstFun2.addElement(funnamecmb);

              query2.Close();
          }
          query.Close(); 
          //System.out.println("Id-evi funkcija projekta"+pr_func);
          //System.out.println("Id-evi funkcija Packeta"+pack_func);
      }
      catch (Exception ex)
      {
          System.out.println(""+ex.toString());
      }
  }

  private void cmbPackage_actionPerformed(ActionEvent e)
  {
            if (cmbPackage.getItemCount() > 0)
              Mnem=cmbPackage.getSelectedItem().toString();
            else
              Mnem = "";
              
            //System.out.println("index cmbFun:"+cmbPackage.getSelectedIndex());
            //System.out.println("MNEM:"+Mnem);
            //System.out.println("count cmbFun:"+cmbPackage.getItemCount());
            if (cmbPackage.getSelectedIndex()==0)
            {
                btnPrev.setEnabled(false);
                btnFirst.setEnabled(false);
            }
            else
            {
                btnPrev.setEnabled(true);
                btnFirst.setEnabled(true);              
            }
            
            if(cmbPackage.getSelectedIndex()== cmbPackage.getItemCount()-1)
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
                
            btnApply.setEnabled(false);
            btnSave.setEnabled(false);
           
            //System.out.println("index jovo:"+cmbFun.getSelectedIndex());
            //System.out.println(""+Mnem);
            setPackage(Mnem);
            //tree.select_node(Mnem,"Functions");    
  }

  private void jButton1_actionPerformed(ActionEvent e)
  {
    if(jListFun.getSelectedIndices().length > 0)
    {
      modelLstFun2.addElement(modelLstFun.getElementAt(jListFun.getSelectedIndex()));
      String alo[] = pr_func.split(",");
      int i=0;
      pr_func="";
      while(i<alo.length )
      {
        if(i != jListFun.getSelectedIndex() + 1 )
          pr_func += alo[i]+",";
        else
          pack_func += alo[i]+",";
        i++;
      }      
      modelLstFun.removeElementAt(jListFun.getSelectedIndex());  
      btnApply.setEnabled(true);
      btnSave.setEnabled(true);   
    }
          //System.out.println("Id-evi funkcija projekta"+pr_func);
          //System.out.println("Id-evi funkcija Packeta"+pack_func);    
  }

  private void jButton2_actionPerformed(ActionEvent e)
  {
    if(jListFun2.getSelectedIndices().length > 0)
    {
      modelLstFun.addElement(modelLstFun2.getElementAt(jListFun2.getSelectedIndex()));
      String alo[] = pack_func.split(",");
      int i=0;
      pack_func="";
      while(i<alo.length )
      {
        if(i != jListFun2.getSelectedIndex() + 1 )
          pack_func += alo[i]+",";
        else
          pr_func += alo[i]+",";
        i++;
      }        
      modelLstFun2.removeElementAt(jListFun2.getSelectedIndex());     
      btnApply.setEnabled(true);
      btnSave.setEnabled(true);         
    }
          //System.out.println("Id-evi funkcija projekta"+pr_func);
          //System.out.println("Id-evi funkcija Packeta"+pack_func);    
  }

  private void cmbPackType_actionPerformed(ActionEvent e)
  {
    packType = cmbPackType.getSelectedIndex() + 1;
    cmbPackage.removeAllItems();
    cmbPackageLoad();
  }

  private void txtPackDesc_keyPressed(KeyEvent e)
  {
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);     
  }

  private void txtPackCom_keyPressed(KeyEvent e)
  {
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);     
  }

  private void txtPackName_keyReleased(KeyEvent e)
  {
          //System.out.println("dugme:"+ e.getKeyChar());
          //System.out.println("dugme2:"+ e.getKeyCode());
              int i=0;
              btnApply.setEnabled(true);
              btnSave.setEnabled(true);
              while(i<txtPackName.getText().length())
              {
                  if (txtPackName.getText().charAt(txtPackName.getText().length()-1)=='_')
                  {
                      txtPackName.setText(txtPackName.getText().substring(0,txtPackName.getText().length()-1));
                      i--;
                  }
                  else if ( txtPackName.getText().length() > 0 && ( txtPackName.getText().charAt(0) >= '0' && txtPackName.getText().charAt(0) <= '9') || txtPackName.getText().charAt(0)==' ' || txtPackName.getText().charAt(0)=='_' )
                  {
                      txtPackName.setText(txtPackName.getText().substring(1));    
                      i=0;
                  }
/*                  else if (txtName.getText().charAt(txtName.getText().length()-1)== '-' || txtName.getText().charAt(txtName.getText().length()-1)== '_')
                  {
                      txtName.setText(txtName.getText().substring(0,txtName.getText().length()-1));
                  }
                  */
                  else if  ((txtPackName.getText().charAt(i) < 'a' || txtPackName.getText().charAt(i) > 'z') && (txtPackName.getText().charAt(i) < '0' || txtPackName.getText().charAt(i) > '9' )&& (txtPackName.getText().charAt(i) < 'A' || txtPackName.getText().charAt(i) > 'Z' ) && txtPackName.getText().charAt(i)!='_')
                  {
                      txtPackName.setText(txtPackName.getText().substring(0,i)+txtPackName.getText().substring(i+1));   
                      i--;
                  }
                  i++;
              }        
              //else txtNameBool = txtName.getText().length();  
  }
  
  public void update ( int r )
  {
      
      try
      {
          JDBCQuery query = new JDBCQuery(connection);
          JDBCQuery query2 = new JDBCQuery(connection);
          ResultSet rs1,rs2;  
          boolean can=true;
          //int Pack_id=0;
          
          rs1=query.select("Select * from IISC_Package where PR_id = "+tree.ID+" and Pack_type = "+packType+" and Pack_name = '"+txtPackName.getText()+"'");
          
          while(rs1.next())
          {
              packid = rs1.getInt("Pack_id");
              if (Mnem == "" || txtPackName.getText() != Mnem )
                can = false;
          }
          query.Close();
          
          if(txtPackName.getText().trim().equals("")) //da li je zadato ime funkcije
            JOptionPane.showMessageDialog(null, "<html><center>Name required!", "Package", JOptionPane.INFORMATION_MESSAGE);
          else if(txtPackDesc.getText().trim().equals(""))//da li postoji description
            JOptionPane.showMessageDialog(null, "<html><center>Description required!", "Package", JOptionPane.INFORMATION_MESSAGE);
          else if(can)
          {
                int selekcija = 0;
                
                if(Mnem.trim().equals(""))// ako je moguce odraditi insert novog paketa
                {
                      selekcija = 1;
                      rs1=query.select("select max(Pack_id)+1  from IISC_Package");
                  
                      if(rs1.next())
                          packid=rs1.getInt(1);
                      else
                          packid=0;
      
                      query.Close();                    
                      
                      int i=query.update("insert into IISC_Package(Pack_id,Pack_name,Pack_type,PR_id,Pack_desc,Pack_comt) values ("+ packid +",'"+txtPackName.getText()+"',"+packType+","+tree.ID+",'"+txtPackDesc.getText()+"','"+txtPackCom.getText()+"')");
                }
                else
                {
                      selekcija = 2;
                      //update postojece funkcije
                      //Mnem=txtName.getText().trim();
                      int i=query.update("update IISC_Package set Pack_name = '"+txtPackName.getText()+"', Pack_desc = '"+txtPackDesc.getText()+"', Pack_comt = '"+txtPackCom.getText()+"' where Pack_id = "+packid+""); 
                      int j=query.update("delete from IISC_PACK_FUN WHERE  Pack_id = "+packid+"");                   
                }
                
                String tmpCol[] = pack_func.split(",");

                  int idd;
                  rs1=query.select("select max(PF_id)+1  from IISC_PACK_FUN ");// where Fun_id = "+id+" AND PR_id="+tree.ID+"");                  
                  //System.out.println("select max(Param_id)+1  from IISC_FUN_PARAM where Fun_id = "+id+" AND PR_id="+tree.ID+"");
                  if( rs1.next() )
                    idd=rs1.getInt(1);
                  else
                    idd=0;   
                
                int tmpi=1;
                while (tmpi < tmpCol.length)
                {
                    int i = query.update("insert into IISC_PACK_FUN(PF_id,Pack_id,Fun_id) values ("+idd+","+packid+","+tmpCol[tmpi]+")");
                    idd++;
                    tmpi++;
                }
                
              if (selekcija == 1)
              {
                  String tmptxt = txtPackName.getText();              
                  cmbPackage.addItem(tmptxt);
                  cmbPackage.setSelectedItem(tmptxt);
                  tree.insert(tmptxt,cmbPackType.getSelectedItem().toString(),"Packages");
              }
              else if (selekcija == 2)
              {
                  String tmpmnem = Mnem;
                  String tmptxt = txtPackName.getText();
                  cmbPackage.removeItem(tmpmnem);     
                  cmbPackage.addItem(tmptxt);
                  cmbPackage.setSelectedItem(tmptxt);
                  tree.removenode(tmpmnem,cmbPackType.getSelectedItem().toString());
                  tree.insert(tmptxt,cmbPackType.getSelectedItem().toString(),"Packages");
              }
               
              JOptionPane.showMessageDialog(null, "<html><center>Package saved!", "Packages", JOptionPane.INFORMATION_MESSAGE);
          }
          else
          {
            JOptionPane.showMessageDialog(null, "<html><center>Change Name of package!", "Packages", JOptionPane.INFORMATION_MESSAGE);
          }
          
      }
      catch (Exception ex)
      {
        System.out.println("Greska :" + ex.toString());
      }
      
  }

  private void btnNew_actionPerformed(ActionEvent e)
  {
    Mnem = "";
    cmbPackage.setSelectedItem(Mnem);
  }

  private void btnApply_actionPerformed(ActionEvent e)
  {
      update(1);
  }

  private void btnSave_actionPerformed(ActionEvent e)
  {
      update(1);  
      this.dispose();
  }

  private void btnClose_actionPerformed(ActionEvent e)
  {
        if(btnSave.isEnabled()) //ako je save digne enabled pita se da li hoce da snimi promjene
          if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Packages", JOptionPane.YES_NO_OPTION)==0)
            update(1);
            
        this.dispose();   
  }

  private void btnErase_actionPerformed(ActionEvent e)
  {
      if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to delete function?", "Function", JOptionPane.YES_NO_OPTION)==0 && delete())
      {

      }    
  }
  
  public boolean delete ()
  {
      JDBCQuery query=new JDBCQuery(connection);
      ResultSet rs1;  
      boolean can=true;
      
      if(Mnem.equals("")) //ne moze se izbrisati ako funkcija nije odabrana
        can=false;

      try
      {
          if(can)
          {
              int i=query.update("delete from IISC_PACK_FUN where Pack_id=" + packid + " "); 
              i=query.update("delete from IISC_Package where Pack_id=" + packid + " "); 
              
              cmbPackage.removeItem(Mnem);    
              tree.removenode(Mnem,cmbPackType.getSelectedItem().toString());
              JOptionPane.showMessageDialog(null, "<html><center>Package removed!", "Packages", JOptionPane.INFORMATION_MESSAGE);
          }

      }
      catch(Exception e)
      {
          JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    
      return can;    
  }

  private void btnPrev_actionPerformed(ActionEvent e)
  {
        if(btnSave.isEnabled()) //ako je save digne enabled pita se da li hoce da snimi promjene
            if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Function", JOptionPane.YES_NO_OPTION)==0)
              update(1);        
              
        if (cmbPackage.getSelectedIndex()!=0)
        {                  
            cmbPackage.setSelectedIndex(cmbPackage.getSelectedIndex()-1);
        }  
  }

  private void btnFirst_actionPerformed(ActionEvent e)
  {
        if(btnSave.isEnabled()) //ako je save digne enabled pita se da li hoce da snimi promjene
            if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Function", JOptionPane.YES_NO_OPTION)==0)
                update(1);        
                
        if (cmbPackage.getSelectedIndex()!=0)
        {                    
            cmbPackage.setSelectedIndex(0);
        }  
  }

  private void btnNext_actionPerformed(ActionEvent e)
  {
      if(btnSave.isEnabled()) //ako je save digne enabled pita se da li hoce da snimi promjene
        if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Function", JOptionPane.YES_NO_OPTION)==0)
              update(1);
 
      if (cmbPackage.getSelectedIndex()!=cmbPackage.getItemCount()-1)
      {            
          cmbPackage.setSelectedIndex(cmbPackage.getSelectedIndex()+1);
      }  
  }

  private void btnLast_actionPerformed(ActionEvent e)
  {
      if(btnSave.isEnabled()) //ako je save digne enabled pita se da li hoce da snimi promjene
          if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Function", JOptionPane.YES_NO_OPTION)==0)
              update(1);      
              
      if (cmbPackage.getSelectedIndex()!=cmbPackage.getItemCount()-1)
      {
          cmbPackage.setSelectedIndex(cmbPackage.getItemCount()-1);  
      }  
  }
  
}