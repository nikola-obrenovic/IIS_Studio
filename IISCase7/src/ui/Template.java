package ui;

import iisc.JDBCQuery;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class Template 
{
  int ID;
  Connection conn;
  String name;
  int group;
  FormPanel formPanel;
  TablePanel tablePanel;
  ItemPanel itemPanel;
  ButtonPanel buttonPanel;
  ItemType[] itemType=new ItemType[5];
  SFont labelFont=new SFont();
  SFont inputFont=new SFont();
  String icon;
  int full_screen;
  int resizable;
  int x;
  int y;
  int position;
  int x_position;
  int y_position;
  Color fg;
  Color bg;
  Color desktop_bg;

  public Template(Connection con, int i)
  {
    conn=con;
    JDBCQuery query=new JDBCQuery(conn);
    JDBCQuery query1=new JDBCQuery(conn);
    ResultSet rs, rs1;
    try
    {
      rs=query.select("select * from IIST_TEMPLATE where T_id="+i);
      if(rs.next())
      {
      ID=rs.getInt("T_id");
      group=rs.getInt("TG_id");
      name=rs.getString("T_name");
      full_screen=rs.getInt("T_full_screen");
      resizable=rs.getInt("T_resizable");
      x=rs.getInt("T_width");
      y=rs.getInt("T_height");
      position=rs.getInt("T_main_position");
      x_position=rs.getInt("T_x_position");
      y_position=rs.getInt("T_y_position");
      fg=new Color(rs.getInt("T_fg_rgb"));
      bg=new Color(rs.getInt("T_bg_rgb"));
      desktop_bg=new Color(rs.getInt("T_desktop_rgb"));
      icon=rs.getString("T_icon");
      rs1=query1.select("select * from IIST_FONT where F_id="+rs.getInt("T_label_font"));
      if(rs1.next())
      {
      int fontStyle;
      int bold=rs1.getInt("F_bold");
      int italic=rs1.getInt("F_italic");
      int underline=rs1.getInt("F_underline");
      if (bold==1 && italic==1)
        fontStyle = Font.BOLD | Font.ITALIC;
      else if (bold==1)
        fontStyle = Font.BOLD;
       else if (italic==1)
        fontStyle = Font.ITALIC;
      else
        fontStyle = Font.PLAIN;
      labelFont.font=new Font(rs1.getString("F_family"), fontStyle , rs1.getInt("F_size"));
      labelFont.underline=(underline==1)?true:false;
      }
      query1.Close();
      rs1=query1.select("select * from IIST_FONT where F_id="+rs.getInt("T_input_font"));
      if(rs1.next())
      {
      int fontStyle;
      int bold=rs1.getInt("F_bold");
      int italic=rs1.getInt("F_italic");
      int underline=rs1.getInt("F_underline");
      if (bold==1 && italic==1)
        fontStyle = Font.BOLD | Font.ITALIC;
      else if (bold==1)
        fontStyle = Font.BOLD;
       else if (italic==1)
        fontStyle = Font.ITALIC;
      else
        fontStyle = Font.PLAIN;
      inputFont.font=new Font(rs1.getString("F_family"), fontStyle , rs1.getInt("F_size"));
      inputFont.underline=(underline==1)?true:false;
      }
      query1.Close();  
      int fid=rs.getInt("SF_id");
      int tid=rs.getInt("TP_id");
      int iid=rs.getInt("IP_id");
      int bid=rs.getInt("BP_id");
      if(fid>=0)
        formPanel=new FormPanel(conn,fid);
      if(tid>=0)
        tablePanel=new TablePanel(conn,tid);
      if(iid>=0)
        itemPanel=new ItemPanel(conn,iid);
      if(bid>=0)
        buttonPanel=new ButtonPanel(conn,bid);
      rs=query.select("select IF_id, IT_id from IIST_ITEM_TYPE where T_id="+i);
      while(rs.next())
      {
        int ifid=rs.getInt("IF_id");
        int itid=rs.getInt("IT_id");
        itemType[ifid]=new ItemType(conn,itid);
      }
      }
      query.Close();
    }
    catch (SQLException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }  
  class FormPanel 
  { int title;
    int title_align;
    SFont titleFont=new SFont();
    int menuCall, buttonCall, keyCall;
    Color fg;
    Color bg;
    public FormPanel(Connection con, int i)
    {
    JDBCQuery query=new JDBCQuery(con);
    JDBCQuery query1=new JDBCQuery(con);
    ResultSet rs, rs1;
    try
    {
      rs=query.select("select * from IIST_SCREEN_FORM where SF_id="+i);
      if(rs.next())
      {
      title=rs.getInt("SF_title");
      title_align=rs.getInt("SF_title_align");
      fg=new Color(rs.getInt("SF_fg_rgb"));
      bg=new Color(rs.getInt("SF_bg_rgb"));
      menuCall=rs.getInt("SF_sub_form_menu_call");
      buttonCall=rs.getInt("SF_sub_form_button_call");
      keyCall=rs.getInt("SF_sub_form_key_call");
      rs1=query1.select("select * from IIST_FONT where F_id="+rs.getInt("SF_title_font"));
      if(rs1.next())
      {
      int fontStyle;
      int bold=rs1.getInt("F_bold");
      int italic=rs1.getInt("F_italic");
      int underline=rs1.getInt("F_underline");
      if (bold==1 && italic==1)
        fontStyle = Font.BOLD | Font.ITALIC;
      else if (bold==1)
        fontStyle = Font.BOLD;
       else if (italic==1)
        fontStyle = Font.ITALIC;
      else
        fontStyle = Font.PLAIN;
      titleFont.font=new Font(rs1.getString("F_family"), fontStyle , rs1.getInt("F_size"));
      titleFont.underline=(underline==1)?true:false;
      }
      query1.Close();
      }
      query.Close();
    }
    catch (SQLException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }
  }
  class TablePanel 
  { 
    int row_height;
    int column_width;
    int visible_rows;
    int overflow;
    int overflow_pos;
    int overflow_size=50;
    int border_style=2;
    int border=0;
    Color fg;
    Color bg;
    Color input_fg;
    Color input_bg;
    public TablePanel(Connection con, int i)
    {
    JDBCQuery query=new JDBCQuery(con);
    JDBCQuery query1=new JDBCQuery(con);
    ResultSet rs, rs1;
    try
    {
      int bb=0;
      rs=query.select("select * from IIST_TABLE_PANEL where TP_id="+i);
      if(rs.next())
      {
      row_height=rs.getInt("TP_row_height");
      column_width=rs.getInt("TP_column_width");
      visible_rows=rs.getInt("TP_visible_rows");
      overflow=rs.getInt("TP_overflow");
      overflow_pos=rs.getInt("TP_overflow_position");
      overflow_size=rs.getInt("TP_overflow_size");
      fg=new Color(rs.getInt("TP_fg_rgb"));
      bg=new Color(rs.getInt("TP_bg_rgb"));
      input_fg=new Color(rs.getInt("TP_input_fg_rgb"));
      input_bg=new Color(rs.getInt("TP_input_bg_rgb"));
      bb=rs.getInt("TP_border");
      }
      query.Close();
      rs=query.select("select * from IIST_BORDER where B_id="+bb);
      if(rs.next())
      {
          border=rs.getInt("B_width");
          border_style=rs.getInt("B_style");
      }
      query.Close();
    }
    catch (SQLException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }
  }
  class ItemPanel 
  {
    int type;
    int orientation;
    int spacing;
    int item_spacing;
    int contex;
    int contex_pos;
    int contex_height=-1;
    int panel_border=2;
    int nested_border=7;
    int panel_border_width=0;
    int nested_border_width=0;
    int panel_label_align=0;
    int panel_input_align=0;
    int nested_label_align=0;
    int nested_input_align=0;
    Color fg;
    Color bg;
    Color input_fg;
    Color input_bg;
    public ItemPanel(Connection con, int i)
    {
    JDBCQuery query=new JDBCQuery(con);
    JDBCQuery query1=new JDBCQuery(con);
    ResultSet rs, rs1;
    try
    {
      int bp=0;
      int bn=0;
      rs=query.select("select * from IIST_ITEM_PANEL where IP_id="+i);
      if(rs.next())
      {
      type=rs.getInt("IP_type");
      orientation=rs.getInt("IP_orientation");
      spacing=rs.getInt("IP_spacing");
      item_spacing=rs.getInt("IP_item_spacing");
      contex=rs.getInt("IP_contex");
      contex_pos=rs.getInt("IP_contex_position");
      contex_height=rs.getInt("IP_contex_height");
      fg=new Color(rs.getInt("IP_fg_rgb"));
      bg=new Color(rs.getInt("IP_bg_rgb"));
      input_fg=new Color(rs.getInt("IP_input_fg_rgb"));
      input_bg=new Color(rs.getInt("IP_input_bg_rgb"));
      bp=rs.getInt("IP_panel_border");
      bn=rs.getInt("IP_nested_border");
      panel_label_align=rs.getInt("IP_panel_label_align");
      panel_input_align=rs.getInt("IP_panel_input_align");
      nested_label_align=rs.getInt("IP_nested_label_align");
      nested_input_align=rs.getInt("IP_nested_input_align");
      }
      query.Close();
      rs=query.select("select * from IIST_BORDER where B_id="+bp);
      if(rs.next())
      {
            panel_border=rs.getInt("B_style");
            panel_border_width=rs.getInt("B_width");
      }
      query.Close();
      rs=query.select("select * from IIST_BORDER where B_id="+bn);
      if(rs.next())
      {
            nested_border=rs.getInt("B_style");
            nested_border_width=rs.getInt("B_width");
      }
      query.Close();
    }
    catch (SQLException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }
  }
  class ButtonPanel 
  { 
    Color fg;
    Color bg;
    int style;
    int border_style=5;
    int border=0;
    public ButtonPanel(Connection con, int i)
    {
    JDBCQuery query=new JDBCQuery(con);
    JDBCQuery query1=new JDBCQuery(con);
    ResultSet rs, rs1;
    try
    {
      int bb=0;
      rs=query.select("select * from IIST_BUTTON_PANEL where BP_id="+i);
      if(rs.next())
      {
          style=rs.getInt("BP_style");
          fg=new Color(rs.getInt("BP_fg_rgb"));
          bg=new Color(rs.getInt("BP_bg_rgb"));
          bb=rs.getInt("BP_border");
      }
      query.Close();
      rs=query.select("select * from IIST_BORDER where B_id="+bb);
      if(rs.next())
      {
          border=rs.getInt("B_width");
          border_style=rs.getInt("B_style");
      }
      query.Close();
    }
    catch (SQLException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }
  }
  public class ItemType 
  {
    int type;
    int title_align;
    int title_position;
    int title_offset;
    int title_bold;
    int title_italic;
    int input_border_style;
    int input_border;
    public ItemType(Connection con, int i)
    {
    JDBCQuery query=new JDBCQuery(con);
    JDBCQuery query1=new JDBCQuery(con);
    ResultSet rs, rs1;
    try
    {
      int ib=0;
      rs=query.select("select * from IIST_ITEM_TYPE where IT_id="+i);
      if(rs.next())
      {
      type=rs.getInt("IF_id");
      title_align=rs.getInt("IT_title_align");
      title_position=rs.getInt("IT_title_position");
      title_offset=rs.getInt("IT_title_offset");
      title_bold=rs.getInt("IT_title_bold");
      title_italic=rs.getInt("IT_title_italic");
      ib=rs.getInt("IT_input_border");
      }
      query.Close();
      rs=query.select("select * from IIST_BORDER where B_id="+ib);
      if(rs.next())
      {
           input_border=rs.getInt("B_width");
           input_border_style=rs.getInt("B_style");
      }
      query.Close();
    }
    catch (SQLException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  } 
  }
  class SFont
  {
    public boolean underline=false;
    public Font font=new Font("SansSerif.plain",0,11);
    public boolean isUnderline(){
        return underline;
    }
  }
}
