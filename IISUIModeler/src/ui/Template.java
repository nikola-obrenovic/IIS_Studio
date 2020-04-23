package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
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

  public Template(UIWizard ui, int i, int grp)
  {
    ID=i;
    group=grp;
    name=ui.txtName.getText();
    full_screen=ui.rdFullScreen.isSelected()?1:0;
    resizable=ui.rdResizable.isSelected()?1:0;
    icon=ui.lbIcon.getToolTipText();
    labelFont.font=ui.lbFontGlobal.getFont();
    labelFont.underline=ui.lbFontGlobal.getText().contains("<u>");
    inputFont.font=ui.lbInputFontGlobal.getFont();
    inputFont.underline=ui.lbInputFontGlobal.getText().contains("<u>");
    if(full_screen==0)
    {
      x=(new Integer(ui.txtX.getText())).intValue();
      y=(new Integer(ui.txtY.getText())).intValue();
    }
    position= ui.rdCenter.isSelected()?0:(ui.rdLeftonTop.isSelected()?1:2);
    if (position == 2)
    {
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      if(ui.lbX.getText().length()>3){
          x_position=ui.xPos;
          y_position=ui.yPos; 
      }
    }
    fg=ui.lbForeground.getBackground();
    bg=ui.lbBackground.getBackground();
    desktop_bg=ui.lbDesktop.getBackground();
 
    formPanel=new FormPanel(ui);
    tablePanel=new TablePanel(ui);
    itemPanel=new ItemPanel(ui);
    buttonPanel=new ButtonPanel(ui);
    for(i=0; i<5; i++)
      itemType[i]=new ItemType(ui, i);
  } 
  
  public void LoadTemplate(UIWizard ui)
  {
    ui.txtName.setText(name);
    if(full_screen==1)
    {
      ui.rdFullScreen.setSelected(true);
      ui.rdFixedSize.setSelected(false);
      ui.txtX.setText(""+600);
      ui.txtY.setText(""+300);
    }
    else
    {
      ui.rdFullScreen.setSelected(false);
      ui.rdFixedSize.setSelected(true);
      ui.txtX.setText(""+x);
      ui.txtY.setText(""+y);
    }
    if(resizable==1)
      ui.rdResizable.setSelected(true);
    else
      ui.rdResizable.setSelected(false);
    if(icon!=null){
    ImageIcon icn = new ImageIcon(icon);
    ui.lbIcon.setIcon(icn);
    ui.lbIcon.setToolTipText(icon);  }  
    ui.lbFontGlobal.setFont(labelFont.font);
    ui.lbFontGlobal.setText("<html>"+(labelFont.isUnderline()?"<u>":"") +(labelFont.font.isBold()?"<b>":"")+(labelFont.font.isItalic()?"<i>":"")+ "<font style=\"font-family:"+labelFont.font.getFontName() + ";font-size:"+labelFont.font.getSize()+"pt;\">"+ labelFont.font.getName() + ", " + labelFont.font.getSize()+"</font>"+(labelFont.font.isItalic()?"</i>":"")+(labelFont.font.isBold()?"</b>":"")+(labelFont.isUnderline()?"</u>":"")+"</html>");
    ui.lbInputFontGlobal.setFont(inputFont.font);
    ui.lbInputFontGlobal.setText("<html>"+(inputFont.isUnderline()?"<u>":"") +(inputFont.font.isBold()?"<b>":"")+(inputFont.font.isItalic()?"<i>":"")+ "<font style=\"font-family:"+inputFont.font.getFontName() + ";font-size:"+inputFont.font.getSize()+"pt;\">"+ inputFont.font.getName() + ", " + inputFont.font.getSize()+"</font>"+(labelFont.font.isItalic()?"</i>":"")+(inputFont.font.isBold()?"</b>":"")+(inputFont.isUnderline()?"</u>":"")+"</html>");
    if(position==0)
    {
      ui.rdCenter.setSelected(true);
      ui.rdLeftonTop.setSelected(false);
      ui.rdCustom.setSelected(false);
    }
    else if(position==1)
    {
      ui.rdCenter.setSelected(false);
      ui.rdLeftonTop.setSelected(true);
      ui.rdCustom.setSelected(false);
    }
    else if(position==2)
    {
      ui.rdCenter.setSelected(false);
      ui.rdLeftonTop.setSelected(false);
      ui.rdCustom.setSelected(true);
      ui.lbScreen.repaint();
    }
    ui.lbBackground.setBackground(bg);
    ui.lbForeground.setBackground(fg);
    ui.lbDesktop.setBackground(desktop_bg);
 
    formPanel.LoadFormPanel(ui);
    tablePanel.LoadTablePanel(ui);
    itemPanel.LoadItemPanel(ui);
    buttonPanel.LoadButtonPanel(ui);
    for (int g=0;g<5 ;g++)
    {
        itemType[g]=new ItemType(ui,g);
        itemType[g].LoadInitialItemType(ui);
    }
    itemType[ui.itemID].LoadItemType(ui);
  } 
  public void LoadItemType(UIWizard ui) {
      for (int g=0;g<5 ;g++)
      {
          itemType[g].LoadUIItemType(ui, g);
      }
  }
  public void Save(Connection conn)
  {
    JDBCQuery query=new JDBCQuery(conn);
    JDBCQuery query1=new JDBCQuery(conn);
    ResultSet rs, rs1;
    String sql="";
    if (ID>=0)
    { 
      String lb_font="null";
      String in_font="null";
      try
      {
      rs=query.select("select *  from IIST_TEMPLATE where T_id="+ID);
      if(rs.next())
      {
        lb_font=rs.getString("T_label_font");
        in_font=rs.getString("T_input_font");
        formPanel.Save(conn,rs.getInt("SF_id"));
        tablePanel.Save(conn,rs.getInt("TP_id"));
        itemPanel.Save(conn,rs.getInt("IP_id"));
        buttonPanel.Save(conn,rs.getInt("BP_id"));
        rs1=query1.select("select *  from IIST_ITEM_TYPE where T_id="+ID);
        while(rs1.next())
        {
        itemType[rs1.getInt("IF_id")].Save(conn,rs1.getInt("IT_id"),ID);
        }
        query1.Close();
      }
      query.Close();
      }
      catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
      sql="update IIST_TEMPLATE set" +
      " T_name='" + name + "'," +
      " T_icon='" + icon + "'," +
      " T_full_screen=" + full_screen + "," +
      " T_width=" + x + "," +
      " T_height=" + y + "," +
      " T_resizable=" + resizable + "," +
      " T_main_position=" + position + "," +
      " T_x_position=" + x_position + "," +
      " T_y_position=" + y_position + "," +
      " T_fg_rgb=" + fg.getRGB() + "," +
      " T_bg_rgb=" + bg.getRGB() + "," +
      " T_desktop_rgb=" + desktop_bg.getRGB() +
      " where T_id="+ID;
      query.update(sql);
      
      sql="update IIST_FONT set" +
      " F_family='" + labelFont.font.getName() + "'," +
      " F_size=" + labelFont.font.getSize() + "," +
      " F_bold=" + (labelFont.font.isBold()?1:0) + "," +
      " F_italic=" + (labelFont.font.isItalic()?1:0) + "," +
      " F_underline=" + (labelFont.isUnderline()?1:0) +
      " where F_id="+lb_font;
      query.update(sql);
      sql="update IIST_FONT set" +
      " F_family='" + inputFont.font.getName() + "'," +
      " F_size=" + inputFont.font.getSize() + "," +
      " F_bold=" + (inputFont.font.isBold()?1:0) + "," +
      " F_italic=" + (inputFont.font.isItalic()?1:0) + "," +
      " F_underline=" + (inputFont.isUnderline()?1:0) +
      " where F_id="+in_font;
      query.update(sql);
    }
    else
    {
      try {
      rs=query.select("select max(T_id)  from IIST_TEMPLATE");
      if(rs.next())
      {
        ID=rs.getInt(1)+1;
      }
      query.Close();
      int f=-1;
      rs=query.select("select max(F_id)  from IIST_FONT");
      if(rs.next())
      {
        f=rs.getInt(1)+1;
      }   
      query.Close();
      query.update("insert into IIST_FONT (F_id, F_family, F_size, F_bold, F_italic, F_underline) values (" + f + ",'" + labelFont.font.getName() + "'," + labelFont.font.getSize() + "," + (labelFont.font.isBold()?1:0) + "," + (labelFont.font.isItalic()?1:0) + ","+ (labelFont.isUnderline()?1:0) +")");
      int fi=f+1; 
      query.update("insert into IIST_FONT (F_id, F_family, F_size, F_bold, F_italic, F_underline) values (" + fi + ",'" + inputFont.font.getName() + "'," + inputFont.font.getSize() + "," + (inputFont.font.isBold()?1:0) + "," + (inputFont.font.isItalic()?1:0) + ","+ (inputFont.isUnderline()?1:0) +")");
      int fpid=formPanel.Save(conn,-1);
      int tpid=tablePanel.Save(conn,-1);
      int ipid=itemPanel.Save(conn,-1);
      int bpid=buttonPanel.Save(conn,-1);
      rs=query.select("select * from IIST_INPUT_FIELD");
      while(rs.next())
      {
      itemType[rs.getInt("IF_id")].Save(conn,-1, ID);
      }
      query.Close();
      query.update("insert into IIST_TEMPLATE (T_id, TG_id, SF_id, TP_id, IP_id, BP_id, T_name,T_label_font, T_input_font, T_icon, T_full_screen, T_width, T_height, T_resizable, T_main_position, T_x_position, T_y_position, T_fg_rgb, T_bg_rgb, T_desktop_rgb) values " +
      "(" + ID + "," + group + "," + fpid + "," + tpid + "," + ipid + "," + bpid + ",'" + name + "'," + f + "," + fi + ",'" + icon + "'," + full_screen + "," + x + "," + y + "," + resizable + "," + position + "," + x_position + "," + y_position + "," + fg.getRGB() + "," + bg.getRGB() + "," + desktop_bg.getRGB() + ")");
      }
      catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
      }
    }

  public void Delete(Connection conn)
  {
    JDBCQuery query=new JDBCQuery(conn);
    JDBCQuery query1=new JDBCQuery(conn);
    ResultSet rs, rs1;
    String sql="";
    if (ID>=0)
    { 
      String lb_font="null";
      String in_font="null";
      try
      {
      rs=query.select("select T_label_font, T_input_font, SF_id, TP_id, IP_id, BP_id  from IIST_TEMPLATE where T_id="+ID);
      if(rs.next())
      {
        int f[]= new int[3];
        int b[]= new int[9];
        f[0]=rs.getInt(1);
        f[1]=rs.getInt(2);
        int sf=rs.getInt(3);
        int tp=rs.getInt(4);
        int ip=rs.getInt(5);
        int bp=rs.getInt(6);
        rs1=query1.select("select SF_title_font  from IIST_SCREEN_FORM where SF_id="+sf);
        if(rs1.next())
        {
            f[2]=rs1.getInt(1);
        }
        rs1.close();
        rs1=query1.select("select TP_border  from IIST_TABLE_PANEL where TP_id="+tp);
        if(rs1.next())
        {
            b[0]=rs1.getInt(1);
        }
        rs1.close();        
        rs1=query1.select("select IP_panel_border, IP_nested_border  from IIST_ITEM_PANEL where IP_id="+ip);
        if(rs1.next())
        {
            b[1]=rs1.getInt(1);
            b[2]=rs1.getInt(2);
        }
        rs1.close();        
        rs1=query1.select("select BP_border  from IIST_BUTTON_PANEL where BP_id="+bp);
        if(rs1.next())
        {
            b[3]=rs1.getInt(1);
        }
        rs1.close();        
        rs1=query1.select("select IT_input_border  from IIST_ITEM_TYPE where T_id="+ID);
        int k=4;
        while(rs1.next())
        {
            b[k]=rs1.getInt(1);
            k++;
        }
        rs1.close();
        query1.update("delete from IIST_SCREEN_FORM where SF_id="+sf);
        query1.update("delete from IIST_TABLE_PANEL where TP_id="+tp);
        query1.update("delete from IIST_ITEM_PANEL where IP_id="+ip);
        query1.update("delete from IIST_BUTTON_PANEL where BP_id="+bp);
        query1.update("delete from IIST_ITEM_TYPE where T_id="+ID);
        for(int i=0;i<f.length; i++)
            query1.update("delete from IIST_FONT where F_id="+i);
        for(int i=0;i<b.length; i++)
            query1.update("delete from IIST_BORDER where B_id="+i);
      }
      query.Close();
      }
      catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
      sql="delete from IIST_TEMPLATE  where T_id="+ID;
      query.update(sql); 
    }
    }    
    public int getBorderStyle(String s, int def){
        int k=def;
        if(s.startsWith("Empty"))k=0;
        else if(s.startsWith("Line"))k=1;
        else if(s.startsWith("Etched"))k=2;
        else if(s.startsWith("Etched Raised"))k=3;
        else if(s.startsWith("Etched Lowered"))k=4;
        else if(s.startsWith("Bevel Raised"))k=5;
        else if(s.startsWith("Bevel Lowered"))k=6;
        else if(s.startsWith("Titled"))k=7;
        return k;
    }
    public String getBorderName(int s){
        String k="";
        if(s==0)k="Empty";
        else if(s==1)k="Line";
        else if(s==2)k="Etched";
        else if(s==3)k="Etched Raised";
        else if(s==4)k="Etched Lowered";
        else if(s==5)k="Bevel Raised";
        else if(s==6)k="Bevel Lowered";
        else if(s==7)k="Titled";
        return k;
    }
    public int getBorderWidth(String s, int def){
       int k=1;
       if(getBorderStyle(s,def)<=1)
       {
        String[] m=s.split(",");
        if(m.length>1)
        k=(new Integer(m[1].trim())).intValue();
       }
       return k;
    }
    public Border getBorders(int border_style, int border) {
        Border b=BorderFactory.createBevelBorder(border);
          if(border_style==0)
              b=BorderFactory.createEmptyBorder(border,border,border,border);
          else if(border_style==1)
              b=BorderFactory.createLineBorder(Color.black, border);
          else if(border_style==2)
              b=BorderFactory.createEtchedBorder();
          else if(border_style==3)
              b=BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
          else if(border_style==4)
              b=BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
          else if(border_style==5)
              b=BorderFactory.createBevelBorder(BevelBorder.RAISED);
          else if(border_style==6)
              b=BorderFactory.createBevelBorder(BevelBorder.LOWERED);
          else if(border_style==7)
              b=BorderFactory.createTitledBorder("Title");
          return b;
    }
  class FormPanel 
  { int title;
    int title_align;
    SFont titleFont=new SFont();
    int menuCall, buttonCall, keyCall;
    Color fg;
    Color bg;
    public FormPanel(UIWizard ui)
    {
      title=ui.chTitle.isSelected()?1:0;
      title_align=ui.cmbFormTitle.getSelectedIndex();
      titleFont.font=ui.lbFontTitle.getFont();
      titleFont.underline=ui.lbFontTitle.getText().contains("<u>");
      bg=ui.lbBackgroundForm.getBackground();
      fg=ui.lbForegroundForm.getBackground();
      menuCall=ui.rdMenuCall.isSelected()?1:0;
      buttonCall=ui.rdButtonCall.isSelected()?1:0;
      keyCall=ui.rdKeyCall.isSelected()?1:0;
    }
    public void LoadFormPanel(UIWizard ui)
    {
      if(title==1)
        ui.chTitle.setSelected(true);
      else
        ui.chTitle.setSelected(false);
      ui.cmbFormTitle.setSelectedIndex(title_align);
      ui.lbFontTitle.setFont(titleFont.font);
      ui.lbFontTitle.setText("<html>"+(titleFont.isUnderline()?"<u>":"") +(titleFont.font.isBold()?"<b>":"")+(titleFont.font.isItalic()?"<i>":"")+ "<font style=\"font-family:"+titleFont.font.getFontName() + ";font-size:"+titleFont.font.getSize()+"pt;\">"+ titleFont.font.getName() + ", " + titleFont.font.getSize()+"</font>"+(titleFont.font.isItalic()?"</i>":"")+(titleFont.font.isBold()?"</b>":"")+(titleFont.isUnderline()?"</u>":"")+"</html>");
      ui.lbBackgroundForm.setBackground(bg);
      ui.lbForegroundForm.setBackground(fg);
      ui.rdMenuCall.setSelected(menuCall==1);
      ui.rdButtonCall.setSelected(buttonCall==1);
      ui.rdKeyCall.setSelected(keyCall==1);
    }
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
  public int Save(Connection conn, int ID)
  {
    JDBCQuery query=new JDBCQuery(conn);
    ResultSet rs;
    String sql="";
    if (ID>=0)
    { 
      String font="";
      try
      {
      rs=query.select("select SF_title_font  from IIST_SCREEN_FORM where SF_id="+ID);
      if(rs.next())
      {
        font=rs.getString("SF_title_font");
      }
      query.Close();
      }
      catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
      sql="update IIST_SCREEN_FORM set" +
      " SF_title=" + title + "," + 
      " SF_title_align=" + title_align + "," + 
      " SF_fg_rgb=" + fg.getRGB() + "," +
      " SF_bg_rgb=" + bg.getRGB() + "," +
      " SF_sub_form_menu_call=" + menuCall + ","+
      " SF_sub_form_button_call=" + buttonCall + ","+
      " SF_sub_form_key_call=" + keyCall +
      " where SF_id="+ID;
      query.update(sql);
      sql="update IIST_FONT set" +
      " F_family='" + titleFont.font.getName() + "'," +
      " F_size=" + titleFont.font.getSize() + "," +
      " F_bold=" + (titleFont.font.isBold()?1:0)  + "," +
      " F_italic=" + (titleFont.font.isItalic()?1:0) + "," +
      " F_underline=" + (titleFont.isUnderline()?1:0) +
      " where F_id="+font;
      query.update(sql);
    }
    else
    {
      try {
      rs=query.select("select max(SF_id)  from IIST_SCREEN_FORM");
      if(rs.next())
      {
        ID=rs.getInt(1)+1;
      }
      query.Close();
      int f=-1;
      rs=query.select("select max(F_id)  from IIST_FONT");
      if(rs.next())
      {
        f=rs.getInt(1)+1;
      }   
      query.Close();
      query.update("insert into IIST_FONT (F_id, F_family, F_size, F_bold, F_italic, F_underline) values (" + f + ",'" + titleFont.font.getName() + "'," + titleFont.font.getSize() + "," + (titleFont.font.isBold()?1:0) + "," + (titleFont.font.isItalic()?1:0) + "," + (titleFont.isUnderline()?1:0) + ")");
      query.update("insert into IIST_SCREEN_FORM (SF_id, SF_title, SF_title_align, SF_title_font, SF_fg_rgb, SF_bg_rgb, SF_sub_form_menu_call, SF_sub_form_button_call, SF_sub_form_key_call) values " +
      "(" + ID + "," + title + "," + title_align + "," + f + "," + fg.getRGB() + "," + bg.getRGB() + "," + menuCall + "," + buttonCall + "," + keyCall + ")");
      }
      catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
      
      }
      return ID;
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
    public TablePanel(UIWizard ui)
    {
      row_height=(new Integer(ui.cmbRowHeight.getSelectedItem().toString())).intValue();
      column_width=(new Integer(ui.cmbColumnWidth.getSelectedItem().toString())).intValue();
      visible_rows=(new Integer(ui.cmbVisibleRows.getSelectedItem().toString())).intValue();
      overflow=ui.chOverflow.isSelected()?1:0;
      if (overflow == 1)
      {
        overflow_pos=ui.cmbOverflow.getSelectedIndex();
        overflow_size=(new Integer(ui.cmbOverflowSize.getSelectedItem().toString())).intValue();
      }
      border_style=getBorderStyle(ui.lbTableBorder.getText(),border_style);
      border=getBorderWidth(ui.lbTableBorder.getText(),border_style);
      bg=ui.lbBackgroundTablePanel.getBackground();
      fg=ui.lbForegroundTablePanel.getBackground();
      input_bg=ui.lbBackgroundCell.getBackground();
      input_fg=ui.lbForegroundCell.getBackground();  
    }
    public void LoadTablePanel(UIWizard ui)
    {
      ui.cmbRowHeight.setSelectedItem(""+row_height);
      ui.cmbColumnWidth.setSelectedItem(""+column_width);
      ui.cmbVisibleRows.setSelectedItem(""+visible_rows);
      if (overflow == 1)
      {
        ui.chOverflow.setSelected(true);
        ui.cmbOverflow.setSelectedIndex(overflow_pos);
        ui.cmbOverflowSize.setSelectedItem(""+overflow_size);
      }
      else
      {
        ui.chOverflow.setSelected(false);
      }
      ui.lbTableBorder.setText(getBorderName(border_style) +(border_style<=1?(", "+border):"").toString());
      ui.lbTableBorder.setBorder(getBorders(border_style,border));
      ui.lbBackgroundTablePanel.setBackground(bg);
      ui.lbForegroundTablePanel.setBackground(fg);
      ui.lbBackgroundCell.setBackground(input_bg);
      ui.lbForegroundCell.setBackground(input_fg);  
    }
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
  public int Save(Connection conn, int ID)
  {
    JDBCQuery query=new JDBCQuery(conn);
    ResultSet rs;
    String sql="";
    try {
    if (ID>=0)
    { 
      sql="update IIST_TABLE_PANEL set" +
      " TP_row_height=" + row_height + "," + 
      " TP_column_width=" + column_width + "," + 
      " TP_visible_rows=" + visible_rows + "," + 
      " TP_overflow_position=" + overflow_pos + "," + 
      " TP_overflow=" + overflow + "," + 
      " TP_overflow_size=" + overflow_size + "," +
      " TP_input_fg_rgb=" + input_fg.getRGB() + "," +
      " TP_input_bg_rgb=" + input_bg.getRGB() + "," +
      " TP_fg_rgb=" + fg.getRGB() + "," +
      " TP_bg_rgb=" + bg.getRGB() +
      " where TP_id="+ID;
      query.update(sql);
        int b=0;
        rs=query.select("select TP_border  from IIST_TABLE_PANEL where TP_id="+ID);
        if(rs.next())
        {
            b=rs.getInt(1);
        }
        query.Close(); 
        sql="update IIST_BORDER set" +
        " B_style=" + border_style + "," + 
        " B_width=" + border  +
        " where B_id="+b; 
        query.update(sql);
    }
    else
    {
      try {
      int b=0;
      rs=query.select("select max(B_id)  from IIST_BORDER");
      if(rs.next())
      {
             b=rs.getInt(1)+1;
      }
      query.Close();
      query.update("insert into IIST_BORDER (B_id, B_style, B_width) values " +
      "(" + b + "," + border_style + "," + border + ")");

      rs=query.select("select max(TP_id)  from IIST_TABLE_PANEL");
      if(rs.next())
      {
        ID=rs.getInt(1)+1;
      }
      query.Close(); 
      query.update("insert into IIST_TABLE_PANEL (TP_id, TP_row_height, TP_column_width, TP_visible_rows, TP_overflow, TP_overflow_position,  TP_overflow_size, TP_border, TP_input_fg_rgb, TP_input_bg_rgb, TP_fg_rgb, TP_bg_rgb) values " +
      "(" + ID + "," + row_height + "," + column_width + "," + visible_rows + "," + overflow + "," + overflow_pos + "," + overflow_size + "," + b + "," + input_fg.getRGB() + "," + input_bg.getRGB() + "," + fg.getRGB() + "," + bg.getRGB() + ")");
      }
      catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
      
      }
     }
     catch (SQLException e) {
          JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
     }
      return ID;
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
    public ItemPanel(UIWizard ui)
    {
      type=ui.rdTabbed.isSelected()?1:0;
      orientation=ui.cmbOrientation.getSelectedIndex();
      spacing=(new Integer(ui.cmbItemPanelSpacing.getSelectedItem().toString())).intValue();
      item_spacing=ui.cmbItemSpacing.getSelectedIndex()+1;
      contex=ui.chContex.isSelected()?1:0;
      if (contex == 1)
      {   
        contex_pos=ui.cmbContex.getSelectedIndex();
        contex_height=ui.rdContexDefault.isSelected()?-1:ui.cmbContexHeight.getSelectedIndex();
      }
      bg=ui.lbBackgroundItemPanel.getBackground();
      fg=ui.lbForegroundItemPanel.getBackground();
      input_bg=ui.lbBackgroundInput.getBackground();
      input_fg=ui.lbForegroundInput.getBackground();
      panel_label_align=ui.cmbPLAlignment.getSelectedIndex();
      panel_input_align=ui.cmbPIAlignment.getSelectedIndex();
      nested_label_align=ui.cmbNPLAlignment.getSelectedIndex();
      nested_input_align=ui.cmbNPIAlignment.getSelectedIndex();
      nested_border=getBorderStyle(ui.lbNestedPanelBorder.getText(),nested_border);
      nested_border_width=getBorderWidth(ui.lbNestedPanelBorder.getText(),nested_border);
      panel_border=getBorderStyle(ui.lbItemPanelBorder.getText(),panel_border);
      panel_border_width=getBorderWidth(ui.lbItemPanelBorder.getText(),panel_border);
    }
    public void LoadItemPanel(UIWizard ui)
    {
      if(type==1)
        ui.rdTabbed.setSelected(true);
      else
      {
        ui.rdNormal.setSelected(true);
        ui.cmbOrientation.setSelectedIndex(orientation);
      }
      ui.cmbItemPanelSpacing.setSelectedItem(""+spacing);
      ui.cmbItemSpacing.setSelectedIndex(item_spacing-1);
      if(contex==1)
      {
          ui.chContex.setSelected(true);
          ui.cmbContex.setSelectedIndex(contex_pos);
          if(contex_height==-1)
            ui.rdContexDefault.setSelected(true);
          else
          {
            ui.cmbContexHeight.setSelectedIndex(contex_height);
            ui.rdContexHeight.setSelected(true);
          }
      }
      else
        ui.chContex.setSelected(false);
      ui.lbBackgroundItemPanel.setBackground(bg);
      ui.lbForegroundItemPanel.setBackground(fg);
      ui.lbBackgroundInput.setBackground(input_bg);
      ui.lbForegroundInput.setBackground(input_fg);
      ui.lbItemPanelBorder.setBorder(getBorders(panel_border,panel_border_width)); 
      ui.lbItemPanelBorder.setText(getBorderName(panel_border) +(panel_border<=1?(", "+panel_border_width):"").toString());
      ui.lbNestedPanelBorder.setBorder(getBorders(nested_border,nested_border_width)); 
      ui.lbNestedPanelBorder.setText(getBorderName(nested_border) +(nested_border<=1?(", "+nested_border_width):"").toString());
      ui.cmbPLAlignment.setSelectedIndex(panel_label_align);
      ui.cmbPIAlignment.setSelectedIndex(panel_input_align);
      ui.cmbNPLAlignment.setSelectedIndex(nested_label_align);
      ui.cmbNPIAlignment.setSelectedIndex(nested_input_align);
    }
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
  public int Save(Connection conn, int ID)
  {
    JDBCQuery query=new JDBCQuery(conn);
    ResultSet rs;
    String sql="";
    try {
    if (ID>=0)
    { 
      sql="update IIST_ITEM_PANEL set" +
      " IP_type=" + type + "," +
      " IP_spacing=" + spacing + "," + 
      " IP_item_spacing=" + item_spacing + "," + 
      " IP_orientation=" + orientation + "," +
      " IP_contex=" + contex + "," + 
      " IP_contex_position=" + contex_pos + "," + 
      " IP_contex_height=" + contex_height + "," +
      " IP_panel_label_align=" + panel_label_align + "," +
      " IP_panel_input_align=" + panel_input_align + "," +
      " IP_nested_label_align=" + nested_label_align + "," +
      " IP_nested_input_align=" + nested_input_align + "," +
      " IP_fg_rgb=" + fg.getRGB() + "," +
      " IP_bg_rgb=" + bg.getRGB() + "," +
      " IP_input_fg_rgb=" + input_fg.getRGB() + "," +
      " IP_input_bg_rgb=" + input_bg.getRGB() +
      " where IP_id="+ID;
      query.update(sql);
      int b=0;
      rs=query.select("select IP_panel_border  from IIST_ITEM_PANEL where IP_id="+ID);
      if(rs.next())
      {
            b=rs.getInt(1);
      }
      query.Close();
      sql="update IIST_BORDER set" +
      " B_style=" + panel_border + "," +
      " B_width=" + panel_border_width +
      " where B_id="+b; 
      query.update(sql);
      b=0;
      rs=query.select("select IP_nested_border from IIST_ITEM_PANEL where IP_id="+ID);
      if(rs.next())
      {
          b=rs.getInt(1);
      }
      query.Close(); 
      sql="update IIST_BORDER set" +
      " B_style=" + nested_border + "," + 
      " B_width=" + nested_border_width +
      " where B_id="+b; 
      query.update(sql);
    }
    else
    {
          
      int bp=0;
      rs=query.select("select max(B_id)  from IIST_BORDER");
      if(rs.next())
      {
         bp=rs.getInt(1)+1;
      }
      query.Close();
      query.update("insert into IIST_BORDER (B_id, B_style, B_width) values " +
      "(" + bp + "," + panel_border + "," + panel_border_width + ")");   
      
      int bn=0;
      rs=query.select("select max(B_id)  from IIST_BORDER");
      if(rs.next())
      {
         bn=rs.getInt(1)+1;
      }
      query.Close();
      query.update("insert into IIST_BORDER (B_id, B_style, B_width) values " +
      "(" + bn + "," + nested_border + "," + nested_border_width + ")"); 
          
      rs=query.select("select max(IP_id)  from IIST_ITEM_PANEL");
      if(rs.next())
      {
        ID=rs.getInt(1)+1;
      }
      query.Close(); 
      query.update("insert into IIST_ITEM_PANEL (IP_id, IP_type, IP_spacing, IP_item_spacing, IP_orientation, IP_contex, IP_contex_position,  IP_contex_height, IP_panel_border, IP_nested_border, IP_panel_label_align, IP_panel_input_align, IP_nested_label_align, IP_nested_input_align, IP_fg_rgb, IP_bg_rgb, IP_input_fg_rgb, IP_input_bg_rgb) values " +
      "(" + ID + "," + type + "," + spacing + "," + item_spacing + "," + orientation + "," + contex + "," + contex_pos + "," + contex_height + "," + bp + "," + bn + "," + panel_label_align + "," + panel_input_align + "," + nested_label_align + "," + nested_input_align + "," + fg.getRGB() + "," + bg.getRGB() + "," + input_fg.getRGB() + "," + input_bg.getRGB() + ")");
      }
      }
      catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    return ID;
  }
  }
  class ButtonPanel 
  { 
    Color fg;
    Color bg;
    int style;
    int border_style=5;
    int border=0;
    public ButtonPanel(UIWizard ui)
    {
      bg=ui.lbBackgroundButton.getBackground();
      fg=ui.lbForegroundButton.getBackground();
      style=ui.rdGraphical.isSelected()?1:0;
      border_style=getBorderStyle(ui.lbButtonBorder.getText(),border_style);
      border=getBorderWidth(ui.lbButtonBorder.getText(),border_style);
    }
    public void LoadButtonPanel(UIWizard ui)
    {
      ui.lbBackgroundButton.setBackground(bg);
      ui.lbForegroundButton.setBackground(fg);
      if(style==1)
        ui.rdGraphical.setSelected(true);
      else
        ui.rdGraphical.setSelected(false);
      ui.lbButtonBorder.setText(getBorderName(border_style) +(border_style<=1?(", "+border):"").toString());
      ui.lbButtonBorder.setBorder(getBorders(border_style,border));
    }

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
  public int Save(Connection conn, int ID) throws SQLException {
    JDBCQuery query=new JDBCQuery(conn);
    ResultSet rs;
    String sql="";
    try {
    if (ID>=0)
    { 
      sql="update IIST_BUTTON_PANEL set" +
      " BP_style=" + style + "," + 
      " BP_fg_rgb=" + fg.getRGB() + "," +
      " BP_bg_rgb=" + bg.getRGB() +
      " where BP_id="+ID; 
      query.update(sql);
      int b=0;
      rs=query.select("select BP_border  from IIST_BUTTON_PANEL where BP_id="+ID);
      if(rs.next())
      {
          b=rs.getInt(1);
      }
      query.Close(); 
      sql="update IIST_BORDER set" +
      " B_style=" + border_style + "," + 
      " B_width=" + border  +
      " where B_id="+b; 
      query.update(sql);
    }
    else
    {
      int b=0;
      rs=query.select("select max(B_id)  from IIST_BORDER");
      if(rs.next())
      {
         b=rs.getInt(1)+1;
      }
      query.Close();
      query.update("insert into IIST_BORDER (B_id, B_style, B_width) values " +
      "(" + b + "," + border_style + "," + border + ")");
      
      rs=query.select("select max(BP_id)  from IIST_BUTTON_PANEL");
      if(rs.next())
      {
        ID=rs.getInt(1)+1;
      }
      query.Close(); 
      query.update("insert into IIST_BUTTON_PANEL (BP_id, BP_style, BP_border, BP_fg_rgb, BP_bg_rgb) values " +
      "(" + ID + "," + style + "," + b + "," + fg.getRGB() + "," + bg.getRGB() + ")");
    
      }
      }
      catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
      
    
    return ID;
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
    public ItemType(UIWizard ui, int i)
    {
      type=i;
      title_align=ui.item[i][0];
      title_position=ui.item[i][1];
      title_offset=ui.item[i][2];
      title_bold=ui.item[i][3];
      title_italic=ui.item[i][4];
      input_border_style=ui.item[i][5];
      input_border=ui.item[i][6];
    }
    public void LoadItemType(UIWizard ui)
    {
      ui.itemID=type;
      ui.setBorder();
      ui.cmbTitleAlign.setSelectedIndex(title_align);
      ui.cmbTitlePosition.setSelectedIndex(title_position);
      ui.cmbTitleOffset.setSelectedIndex(title_offset);
      if (title_bold==1)
        ui.chBold.setSelected(true);
      else
        ui.chBold.setSelected(false);
      if (title_italic==1)
        ui.chItalic.setSelected(true);
      else
        ui.chItalic.setSelected(false);
      ui.lbItemTypeBorder.setText(getBorderName(input_border_style) +(input_border_style<=1?(", "+input_border):"").toString());
      ui.lbItemTypeBorder.setBorder(getBorders(input_border_style,input_border)); 
    }
    public void LoadInitialItemType(UIWizard ui)
      {
        ui.item[type][0]=title_align;
        ui.item[type][1]=title_position;
        ui.item[type][2]=title_offset;
        ui.item[type][3]=title_bold;
        ui.item[type][4]=title_italic;
        ui.item[type][5]=input_border_style;
        ui.item[type][6]=input_border;
      }
    public void LoadUIItemType(UIWizard ui, int type)
    {
        title_align=ui.item[type][0];
        title_position=ui.item[type][1];
        title_offset=ui.item[type][2];
        title_bold=ui.item[type][3];
        title_italic=ui.item[type][4];
        input_border_style=ui.item[type][5];
        input_border=ui.item[type][6];
    }
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
  public int Save(Connection conn, int ID, int T_id)
  {
    JDBCQuery query=new JDBCQuery(conn);
    ResultSet rs;
    String sql="";
    try {
    if (ID>=0)
    { 
      sql="update IIST_ITEM_TYPE set" +
      " IT_title_align=" + title_align + "," + 
      " IT_title_position=" + title_position + "," + 
      " IT_title_offset=" + title_offset + "," + 
      " IT_title_bold=" + title_bold + "," +
      " IT_title_italic=" + title_italic +
      " where IT_id="+ID;  
      query.update(sql);
        
        int b=0;
        rs=query.select("select IT_input_border  from IIST_ITEM_TYPE where IT_id="+ID);
        if(rs.next())
        {
          b=rs.getInt(1);
        }
        query.Close();
        sql="update IIST_BORDER set" +
        " B_style=" + input_border_style + "," +
        " B_width=" + input_border +
        " where B_id="+b; 
        query.update(sql);
    }
    else
    {
     
     int bp=0;
     rs=query.select("select max(B_id)  from IIST_BORDER");
     if(rs.next())
     {
        bp=rs.getInt(1)+1;
     }
     query.Close();
     query.update("insert into IIST_BORDER (B_id, B_style, B_width) values " +
     "(" + bp + "," + input_border_style + "," + input_border + ")");   
          
      rs=query.select("select max(IT_id)  from IIST_ITEM_TYPE");
      if(rs.next())
      {
        ID=rs.getInt(1)+1;
      }
      query.Close(); 
      query.update("insert into IIST_ITEM_TYPE (IT_id, IF_id, T_id, IT_title_align, IT_title_position, IT_title_offset, IT_title_bold, IT_title_italic, IT_input_border) values " +
      "(" + ID + ", " + type + ", " + T_id + "," + title_align + "," + title_position + "," + title_offset + "," + title_bold + "," + title_italic + "," + bp + ")");
      }
      }
      catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      } 
    return ID;
  }
  } 
  public class SFont
  {
    public boolean underline=false;
    public Font font=new Font("SansSerif.plain",0,11);
    public boolean isUnderline(){
        return underline;
    }
  
  }
}
