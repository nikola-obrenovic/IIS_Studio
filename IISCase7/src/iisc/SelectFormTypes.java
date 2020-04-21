package iisc;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JCheckBox;
import java.awt.Rectangle;
import javax.swing.SwingConstants;
import java.sql.*;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.SystemColor;

public class SelectFormTypes extends JDialog 
{
    private Connection conn;
    private int AS_id;
    private int[] ownedFormTypesId;
    private int[] referencedFormTypesId;
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    
    private JCheckBox[] ownedCheckBox;
    private JCheckBox selectAllOwned = new JCheckBox();
    private JCheckBox selectAllReferenced = new JCheckBox();
    private JCheckBox[] referencedCheckBox;
    private JLabel[] ownedLabel;
    private JLabel[] referencedLabel;
    private JButton okButton = new JButton();
    private JCheckBox selectAllOwne = new JCheckBox();
    private JScrollPane spMain = new JScrollPane();
    private JPanel jpMain = new JPanel();
    private JButton btnClose = new JButton();
    private JButton btnHelp = new JButton();
    private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
    
    public SelectFormTypes(IISFrameMain parent, int p_AS_id, Connection con)
    {
        super(parent, "Form types to XML Document", true);
        AS_id = p_AS_id;
        conn = con;
        
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
        setSize(new Dimension(300, 281));
        getContentPane().setLayout(null);
       setResizable(false);
        jLabel2.setText("Owned form types");
        jLabel2.setBounds(new Rectangle(10, 10, 229, 15));
        jLabel2.setBackground(Color.white);
        jLabel2.setFont(new Font("SansSerif", 1, 11));
        okButton.setText("Generate");
        okButton.setBounds(new Rectangle(80, 210, 80, 30));
        okButton.setFont(new Font("SansSerif", 0, 11));
        okButton.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              okButton_actionPerformed(e);
            }
          });
        selectAllOwne.setText("");
        selectAllOwne.setBounds(new Rectangle(240, 8, 40, 20));
        selectAllOwne.setBackground(Color.white);
        selectAllOwne.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              selectAllRef_actionPerformed(e);
            }
        });
        spMain.setBounds(new Rectangle(0, 0, 290, 200));
        spMain.getViewport().setLayout(null);
        spMain.setBackground(Color.white);
        spMain.setForeground(Color.white);
    spMain.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        
        //jpMain.setBounds(new Rectangle(0, 0, 285, 1000));
        jpMain.setLayout(null);
        jpMain.setBackground(Color.white);
        btnClose.setMaximumSize(new Dimension(50, 30));
        btnClose.setPreferredSize(new Dimension(50, 30));
        btnClose.setText("Close");
        btnClose.setBounds(new Rectangle(165, 210, 80, 30));
        btnClose.setMinimumSize(new Dimension(50, 30));
        btnClose.setFont(new Font("SansSerif", 0, 11));
        btnClose.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent ae)
          {
            close_ActionPerformed(ae);
          }
        });
        btnHelp.setBounds(new Rectangle(250, 210, 35, 30));
        //btnHelp.setSelectedIcon(imageHelp);
        btnHelp.setIcon(imageHelp);
        btnHelp.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnHelp_actionPerformed(e);
          }
        });
        //jpMain.setPreferredSize(new Dimension(285, 1000));
        jpMain.add(selectAllOwne);
        jpMain.add(jLabel2);
        spMain.getViewport().add(jpMain, null);
        this.getContentPane().add(spMain, null);
        this.getContentPane().add(okButton, null);
        this.getContentPane().add(btnClose, null);
        this.getContentPane().add(btnHelp, null);
        AddCheckBoxes();
        selectAllReferenced.setSelected(true);
        selectAllOwne.setSelected(true);
    }
    
    private void AddCheckBoxes()
    {
        
        try
        {
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("select Tf_id from IISC_TF_APPSYS where AS_id=" + AS_id);
            resultSet.last();
            
            int ownedCount = resultSet.getRow();
            int i = 0;
            ownedFormTypesId = new int[ownedCount];
            ownedCheckBox = new JCheckBox[ownedCount];
            ownedLabel = new JLabel[ownedCount];
            int begin = 25;
            int step = 18;
            int Tf_id;
            Statement statement2;
            ResultSet resultSet2;
            resultSet.beforeFirst();
            
            while (resultSet.next())
            {
                ownedLabel[i] = new JLabel();
                ownedCheckBox[i] = new JCheckBox();
                
                Tf_id = resultSet.getInt("Tf_id");
                ownedFormTypesId[i] = Tf_id;
                
                statement2 = conn.createStatement();
                resultSet2 = statement2.executeQuery("select Tf_mnem from IISC_FORM_TYPE where Tf_id=" + Tf_id);
                if (resultSet2.next())
                {
                    ownedLabel[i].setText(resultSet2.getString("Tf_mnem"));
                }  
                else
                {
                    ownedLabel[i].setText("");
                }
                ownedLabel[i].setBounds(new Rectangle(10, begin + i * step, 229, 20));
                ownedLabel[i].setFont(new Font("SansSerif", 0, 11));   
                ownedLabel[i].setBackground(Color.white);
                jpMain.add(ownedLabel[i]);
                
                ownedCheckBox[i].setText("");
                ownedCheckBox[i].setBounds(new Rectangle(240, begin + i * step, 40, 20)); 
                ownedCheckBox[i].setSelected(true);
                ownedCheckBox[i].setBackground(Color.white);
                jpMain.add(ownedCheckBox[i]);
                
                i = i + 1;
                resultSet2.close();
                statement2.close();
            }
            resultSet.close();
            statement.close();
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery("select Tf_id from IISC_APP_SYS_REFERENCE where AS_id=" + AS_id);
            resultSet.last();
            
            int referencedCount = resultSet.getRow();
            
            begin = begin + ownedCount * step + 10;
            
            if (referencedCount > 0)
            {
                jLabel4.setText("Referenced form types");
                jLabel4.setBounds(new Rectangle(10, begin, 229, 20));
                jLabel4.setBackground(Color.white);
                jLabel4.setFont(new Font("SansSerif", 1, 11));
                jpMain.add(jLabel4, null);
                selectAllReferenced.setBounds(new Rectangle(240, begin, 40, 20));
                selectAllReferenced.setBackground(Color.white);
                selectAllReferenced.addActionListener(new ActionListener()
                {
                  public void actionPerformed(ActionEvent e)
                  {
                    selectAllReferenced_actionPerformed(e);
                  }
                });
                jpMain.add(selectAllReferenced);
                begin = begin + step;
            }                
            
            referencedFormTypesId = new int[referencedCount];
            referencedCheckBox = new JCheckBox[referencedCount];
            referencedLabel = new JLabel[referencedCount];

            i = 0;
            resultSet.beforeFirst();
            
            while (resultSet.next())
            {
                referencedLabel[i] = new JLabel();
                referencedCheckBox[i] = new JCheckBox();
                
                Tf_id = resultSet.getInt("Tf_id");
                referencedFormTypesId[i] = Tf_id;
                
                statement2 = conn.createStatement();
                resultSet2 = statement2.executeQuery("select Tf_mnem from IISC_FORM_TYPE where Tf_id=" + Tf_id);
                if (resultSet2.next())
                {
                    referencedLabel[i].setText(resultSet2.getString("Tf_mnem"));
                }  
                else
                {
                    referencedLabel[i].setText("");
                }
                referencedLabel[i].setBounds(new Rectangle(10, begin + i * step, 229, 20));
                referencedLabel[i].setFont(new Font("SansSerif", 0, 11)); 
                jpMain.add(referencedLabel[i]);
                referencedLabel[i].setBackground(Color.white);
                referencedCheckBox[i].setText("");
                referencedCheckBox[i].setBounds(new Rectangle(240, begin + i * step, 20, 20)); 
                referencedCheckBox[i].setSelected(true);
                referencedCheckBox[i].setBackground(Color.white);
                jpMain.add(referencedCheckBox[i]);
                
                i = i + 1;
                resultSet2.close();
                statement2.close();
            }
            resultSet.close();
            statement.close();
            
            int end = begin + referencedCount*step + 20;
            
            if (end < spMain.getHeight())
                end = spMain.getHeight();
            jpMain.setBounds(new Rectangle(0, 0, 290, end));
            jpMain.setLayout(null);
            jpMain.setPreferredSize(new Dimension(290, end));
           
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }

  private void selectAllReferenced_actionPerformed(ActionEvent e)
  {
      //System.out.println(selectAllReferenced.isSelected());
      int len = referencedCheckBox.length;
      
      if (selectAllReferenced.isSelected())
      {
          for (int i = 0; i < len; i++)
          {
                referencedCheckBox[i].setSelected(true);
          }
      }
      else
      {
          for (int i = 0; i < len; i++)
          {
                referencedCheckBox[i].setSelected(false);
          }
      }
  }

  private void okButton_actionPerformed(ActionEvent e)
  {
      int[] tempArr;
      int selectCount = 0, len,i;
      
      len = ownedCheckBox.length;
      
      for(i = 0; i < len; i++)
      {
          if (ownedCheckBox[i].isSelected())
          {
              selectCount = selectCount + 1;
          }
      }
      
      tempArr = new int[selectCount];
      selectCount = 0;
      
      for(i = 0; i < len; i++)
      {
          if (ownedCheckBox[i].isSelected())
          {
              tempArr[selectCount] = ownedFormTypesId[i];
              selectCount = selectCount + 1;              
          }
      }
      
      ownedFormTypesId = tempArr;
      
      selectCount = 0;
      len = referencedCheckBox.length;
      
      for(i = 0; i < len; i++)
      {
          if (referencedCheckBox[i].isSelected())
          {
              selectCount = selectCount + 1;
          }
      }
      
      tempArr = new int[selectCount];
      selectCount = 0;
      
      for(i = 0; i < len; i++)
      {
          if (referencedCheckBox[i].isSelected())
          {
              tempArr[selectCount] = referencedFormTypesId[i];
              selectCount = selectCount + 1;              
          }
      }
      
      referencedFormTypesId = tempArr;
      
      
      TFXMLViewer tf = new TFXMLViewer((IISFrameMain)this.getOwner(), AS_id, conn, "xml", TFXMLViewer.APP_SYS_SPEC, ownedFormTypesId, referencedFormTypesId);
      Settings.Center(tf);
      tf.setVisible(true);
      this.dispose();
  }

  private void cancelButton_actionPerformed(ActionEvent e)
  {
      this.dispose();
  }

  private void selectAllRef_actionPerformed(ActionEvent e)
  {
      int len = ownedCheckBox.length;
      
      if (selectAllOwne.isSelected())
      {
          for (int i = 0; i < len; i++)
          {
                ownedCheckBox[i].setSelected(true);
          }
      }
      else
      {
          for (int i = 0; i < len; i++)
          {
                ownedCheckBox[i].setSelected(false);
          }
      }
  }

  private void close_ActionPerformed(ActionEvent e)
  {
      this.dispose();
  }

  private void btnHelp_actionPerformed(ActionEvent e)
  {
   Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, conn );
 Settings.Center(hlp);
 hlp.setVisible(true);
  }
   
}
