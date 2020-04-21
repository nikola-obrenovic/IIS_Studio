package iisc.callgraph;

import iisc.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.border.LineBorder;


public class FormPanel extends JPanel
{
  public int Tf_id;
  public String Tf_mnem;
  public int AS_id;
  public String As_mnem;
  public int type;
  
  public Connection con;
  public int x;
  public int y;
  public int index;
  
  public FrmMainPanel mPanel;
  public CallingGraph parent;
  public CompPanel compPanel;
  public ParPanel parPanel;
  public  int mHeight = 40;
  
  public  int mCTCHeight = 20;
  public  int mCTEHeight = 80;
  
  public  int mParCHeight = 20;
  public  int mParEHeight = 80;
  
  public  int mWidth = 200;
  public  int smallWidth = 140;
  public  int smallHeight = 40;
  public boolean expanded;
  public int width;
  public int height;
  public boolean dirty = false;
  public Color color = CallingGraph.unmarkColor;
  public int aLayout = 0;
  
  public FormPanel(int _x, int _y, CallingGraph _parent, int _Tf_id, int _index)
  {
      super();  
      x = _x;
      y = _y;
      Tf_id = _Tf_id;
      
      expanded = false;
      
      index = _index;
      parent = _parent;
      InitApplicationSystem();
      setLayout(null);
      setBorder(new LineBorder(Color.black, 1));
      setBackground(SystemColor.lightGray);
      mPanel = new FrmMainPanel(this);
      add(mPanel);
      compPanel = new CompPanel(this);
      add(compPanel);
      parPanel = new ParPanel(this, Tf_id);
      add(parPanel);
      mPanel.setBounds(0, 0, smallWidth, smallHeight);
      setBounds(x, y, smallWidth, smallHeight);
      width = smallWidth;
      height = smallHeight;
     
  }
  
  private void InitApplicationSystem()
  {
      try
      {
          Statement statement = parent.conn.createStatement();
          ResultSet rs = statement.executeQuery("select * from IISC_TF_APPSYS,IISC_FORM_TYPE,IISC_APP_SYSTEM where IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_TF_APPSYS.PR_id="+ parent.PR_id + " and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id and IISC_FORM_TYPE.Tf_id=" + Tf_id);
          
          if(rs.next())
          {
              AS_id = rs.getInt("AS_id");
              As_mnem = rs.getString("AS_name");
              Tf_mnem = rs.getString("Tf_mnem");
              
              if (rs.getInt("Tf_use") == 2)
              {
                  type = 1;
              }
              else
              {
                  type = 0;
              }
          }
      }
      catch(SQLException e)
      {
          
      }
  }
  
  public void ReInit()
  {
      try
      {
          Statement statement = parent.conn.createStatement();
          ResultSet rs = statement.executeQuery("select * from IISC_FORM_TYPE where PR_id="+ parent.PR_id + " and Tf_id=" + Tf_id);
          
          if(rs.next())
          {
              Tf_mnem = rs.getString("Tf_mnem");
          }
      }
      catch(SQLException e)
      {
          
      }
  }
  public CallingGraph getCallingGraph()
  {
      return parent;
  }
  
  public void Readjust()
  {
      height = mHeight;
      
      if (expanded)
      {
          width = mWidth;
          
          if (compPanel.getExpanded())
          {
              
              compPanel.setBounds(0, height - 1, mWidth, mCTEHeight + mCTCHeight + 1);
              compPanel.ReAdjust();
              
              height = height + mCTEHeight + mCTCHeight;
              
              if (parPanel.getExpanded())
              {
                  parPanel.setBounds(0, height - 1, mWidth, mParEHeight + mParCHeight + 1);
                  height = height + mParEHeight + mParCHeight;    
              }
              else
              {
                  parPanel.setBounds(0, height - 1, mWidth, mParCHeight + 1);
                  height = height + mParCHeight;   
              }
              parPanel.ReAdjust();
          }
          else
          {
              
              compPanel.setBounds(0, height - 1, mWidth, mCTCHeight + 1);
              compPanel.ReAdjust();
              height = height + mCTCHeight;
              
              if (parPanel.getExpanded())
              {
                  parPanel.setBounds(0, height - 1, mWidth, mParEHeight + mParCHeight + 1);
                  height = height + mParEHeight + mParCHeight;    
              }
              else
              {
                  parPanel.setBounds(0, height - 1, mWidth, mParCHeight + 1);
                  height = height + mParCHeight;   
              }
              parPanel.ReAdjust();
          }
          mPanel.setBounds(0, 0, mWidth, mHeight);
          setBounds(x, y, mWidth, height);
          //parent.AdjustLine(index);
          parent.CheckLines(index);
          parent.setSelected(-1);
          parent.setSelected(index);
          //revalidate();
          //drawingPanel.repaint();
      }
      else
      {
          width = smallWidth;
          height = smallHeight;
          
          mPanel.setBounds(0, 0, smallWidth, smallHeight);
          setBounds(x, y, smallWidth, smallHeight);
          parent.CheckLines(index);
          //parent.AdjustLine(index);
          parent.setSelected(-1);
          parent.setSelected(index);   
          parent.repaint();
      }
  }
  
  private void CollapseAll()
  {
      expanded = false;
      
  }
  
  
    
      
    
}