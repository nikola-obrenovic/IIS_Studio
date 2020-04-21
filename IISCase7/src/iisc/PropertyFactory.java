package iisc;
import java.sql.*;

public class PropertyFactory 
{
    private int ddType;
    private Connection conn;
    private ResultSet rs;
    private int Dom_id;
    private ControlPanel cp;
    private boolean domExists;
    private String type;
    private int Tob_id;
    private int Tf_id;
    
    public PropertyFactory(Connection _conn, int _Dom_id, ControlPanel _cp, String _type)
    {
        ddType = 0;
        conn = _conn;
        Dom_id = _Dom_id;
        cp = _cp;
        domExists = false;
        type = _type;
        
        try
        {
            if (type.compareTo("A") == 0)
            {
                Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = statement.executeQuery("select * from IISC_ATTRIBUTE_DISPLAY where Att_id=" + Dom_id);
                if (rs.next())
                {
                    ddType = rs.getInt("AD_type");
                    domExists = true;
                }
            }
            else
            {
                Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = statement.executeQuery("select * from IISC_DOMAIN_DISPLAY where Dom_id=" + Dom_id);
                if (rs.next())
                {
                    ddType = rs.getInt("DD_type");
                    domExists = true;
                }
            }
        }
        catch (SQLException sqle)
        {
            System.out.println("SQL Exception: " + sqle); 
            //return null;
        } 
    }
    
    
    public PropertyFactory(Connection _conn, int _Dom_id, ControlPanel _cp, String _type, int _Tob_id, int _Tf_id)
    {
        ddType = 0;
        conn = _conn;
        Dom_id = _Dom_id;
        Tob_id = _Tob_id;
        Tf_id = _Tf_id;
        
        cp = _cp;
        domExists = false;
        type = _type;
        
        try
        {
            
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = statement.executeQuery("select * from IISC_COMPTYPE_ATTRIB_DISPLAY where Att_id=" + Dom_id + " and Tob_id=" + Tob_id +" and Tf_id=" + Tf_id);
            if (rs.next())
            {
                ddType = rs.getInt("CAD_type");
                domExists = true;
            }
           
        }
        catch (SQLException sqle)
        {
            System.out.println("SQL Exception: " + sqle); 
            //return null;
        } 
    }
    
    public int getDDType()
    {
        return ddType;
    }
    
    public ResultSet getResSet()
    {
        return rs;
    }
    
    public boolean getDomExists()
    {
        return domExists;
    }
    
    public Property[] GenerateFormTypeProperties(int Tf_id, Connection conn)
    {
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs;
            Property properties[] = new Property[10];
            
            
            rs = statement.executeQuery("select * from IISC_FORM_TYPE where Tf_id=" + Tf_id );
            
           // rs.first();
            
            if (rs.next())
            {
                String[] items = {"sec." , "min." , "hour" , "day" , "week" , "month" , "year"};
                String[] propmtuseitems = {"menu" , "program"};
                Integer[] useitems = new Integer[2];
                useitems[0] = new Integer(0);
                useitems[1] = new Integer(2);
                
                properties[0] = new Property("Tf_mnem", "Name", "text", rs.getString("Tf_mnem"),"IISC_FORM_TYPE",true, cp);
                properties[1] = new Property("Tf_title", "Title", "text", rs.getString("Tf_title"),"IISC_FORM_TYPE", true, cp);
                properties[2] = new Property("Tf_crdate", "Created", "text", rs.getString("Tf_crdate"),"IISC_FORM_TYPE", false, cp);
                properties[3] = new Property("Tf_moddate", "Last modified", "text", rs.getString("Tf_moddate"),"IISC_FORM_TYPE",false, cp);
                properties[4] = new Property("Tf_freq", "Frequency", "text", rs.getString("Tf_freq"),"IISC_FORM_TYPE", true, cp);
                properties[5] = new Property("Frequency unit", "Tf_freq_unit", "combo", 0, (Object[])items,  (Object[])items,"IISC_FORM_TYPE", true, cp);
                properties[6] = new Property("Tf_rest", "Response", "text", rs.getString("Tf_rest"),"IISC_FORM_TYPE", true, cp);
                properties[7] = new Property("Response unit", "Tf_rest_unit", "combo", 0, (Object[])items,  (Object[])items,"IISC_FORM_TYPE", true, cp);
                
                int use = rs.getInt("Tf_use");
                
                if (use == 2)
                {
                    properties[8] = new Property("Usage", "Tf_use", "usage", 0, (Object[])useitems,(Object[])propmtuseitems,  "IISC_FORM_TYPE",true, 9, cp);
                    String[] propmtconsitems = {"yes" , "no"};
                    Integer[] consitems = new Integer[2];
                    consitems[0] = new Integer(0);
                    consitems[1] = new Integer(1);
                    properties[9] = new Property("Considered in db design", "Tf_use", "usagechild", 0, (Object[])consitems, (Object[])propmtconsitems,  "IISC_FORM_TYPE",false, 9, cp);
                    
                }
                else
                {
                    properties[8] = new Property("Usage", "Tf_use", "usage", 1, (Object[])useitems, (Object[])propmtuseitems,  "IISC_FORM_TYPE", true, 9,  cp);
                    String[] propmtconsitems = {"yes" , "no"};
                    Integer[] consitems = new Integer[2];
                    consitems[0] = new Integer(0);
                    consitems[1] = new Integer(1);
                    properties[9] = new Property("Considered in db design", "Tf_use", "usagechild", use,  (Object[])consitems, (Object[])propmtconsitems, "IISC_FORM_TYPE",true, cp);
                  
                }
              //  properties[4] = new Property("frequency", "Tf_freq_unit", "combo", 0, (Object[])items,  (Object[])items,"IISC_FORM_TYPE",conn, Tf_id );
              
            }
            
            return properties;
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
     
     public Property[] GenerateTextBoxProperies()
     {
        try
        {
            Property properties[] = new Property[5];
            
            rs.beforeFirst();
            //rs.absolute(0);
            
            
            if (rs.next())
            {
                String[] promptScroll = {"Never", "Always", "As needed"};
                Integer[] itemScroll = new Integer[3];
                itemScroll[0] = new Integer(0);
                itemScroll[1] = new Integer(1);
                itemScroll[2] = new Integer(2);
                
                String[] propmtEditable = {"No" , "Yes"};
                Integer[] itemEditable = new Integer[2];
                itemEditable[0] = new Integer(0);
                itemEditable[1] = new Integer(1);
                
                String[] promptInAlign = {"Left", "Right", "Center"};
                Integer[] itemInAlign = new Integer[3];
                itemInAlign[0] = new Integer(0);
                itemInAlign[1] = new Integer(1);
                itemInAlign[2] = new Integer(2);
                
                properties[0] = new Property("DD_height", "height", "text", rs.getString(type + "D_height"),"IISC_DOMAIN_DISPLAY", true, cp);
                properties[1] = new Property("DD_width", "width", "text", rs.getString(type + "D_width"),"IISC_DOMAIN_DISPLAY", true, cp);
                
                properties[2] = new Property("multiline", "DD_text_multiline", "combo", rs.getInt(type + "D_text_multiline"), (Object[])itemEditable, (Object[])propmtEditable,  "IISC_DOMAIN_DISPLAY", true,  cp);
                properties[3] = new Property("scroll policy", "DD_ text_scroll", "combo", rs.getInt(type + "D_text_scroll"), (Object[])itemScroll, (Object[])promptScroll, "IISC_DOMAIN_DISPLAY", true,  cp);
                properties[4] = new Property("input alignment", "DD_input_align", "combo", rs.getInt(type + "D_input_align"), (Object[])itemInAlign, (Object[])promptInAlign, "IISC_DOMAIN_DISPLAY", true,  cp);
                
                return properties;
            }
            else
            {
                return GenerateEmptyTextBoxProperies();
            }
        }
        catch (Exception e)
        {
            return GenerateEmptyTextBoxProperies();
        } 
    }
    
    public Property[] GenerateEmptyTextBoxProperies()
    {
        try
        {
            Property properties[] = new Property[5];
               
            String[] promptScroll = {"Never", "Always", "As needed"};
            Integer[] itemScroll = new Integer[3];
            itemScroll[0] = new Integer(0);
            itemScroll[1] = new Integer(1);
            itemScroll[2] = new Integer(2);
            
            String[] propmtEditable = {"No" , "Yes"};
            Integer[] itemEditable = new Integer[2];
            itemEditable[0] = new Integer(0);
            itemEditable[1] = new Integer(1);
            
            String[] promptInAlign = {"Left", "Right", "Center"};
            Integer[] itemInAlign = new Integer[3];
            itemInAlign[0] = new Integer(0);
            itemInAlign[1] = new Integer(1);
            itemInAlign[2] = new Integer(2);
                
            properties[0] = new Property("DD_height", "height", "text", "0","IISC_DOMAIN_DISPLAY", true, cp);
            properties[1] = new Property("DD_width", "width", "text", "0","IISC_DOMAIN_DISPLAY",true, cp);
            
            properties[2] = new Property("multiline", "DD_text_multiline", "combo", 0, (Object[])itemEditable, (Object[])propmtEditable, "IISC_DOMAIN_DISPLAY", true, cp);
            properties[3] = new Property("scroll policy", "DD_text_scroll", "combo", 0, (Object[])itemScroll, (Object[])promptScroll, "IISC_DOMAIN_DISPLAY", true, cp);
            properties[4] = new Property("input alignment", "DD_input_align", "combo", 0, (Object[])itemInAlign, (Object[])promptInAlign, "IISC_DOMAIN_DISPLAY", true,  cp);
                
            return properties;
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
    public Property[] GenerateRadioProperies()
     {
        try
        {
            Property properties[] = new Property[4];
           
            rs.beforeFirst();
            
            if (rs.next())
            {
                String[] promptScroll = {"Horizontal", "Vertical"};
                Integer[] itemScroll = new Integer[2];
                itemScroll[0] = new Integer(0);
                itemScroll[1] = new Integer(1);
                                             
                String[] promptInAlign = {"Left", "Right", "Center"};
                Integer[] itemInAlign = new Integer[3];
                itemInAlign[0] = new Integer(0);
                itemInAlign[1] = new Integer(1);
                itemInAlign[2] = new Integer(2);
            
                properties[0] = new Property("DD_height", "height", "text", rs.getString(type + "D_height"),"IISC_DOMAIN_DISPLAY", true, cp);
                properties[1] = new Property("DD_width", "width", "text", rs.getString(type + "D_width"),"IISC_DOMAIN_DISPLAY", true, cp);
              
                properties[2] = new Property("orientation", "DD_radio_orientation", "combo", rs.getInt(type + "D_radio_orientation"), (Object[])itemScroll, (Object[])promptScroll,  "IISC_DOMAIN_DISPLAY", true, cp);
                properties[3] = new Property("input alignment", "DD_input_align", "combo", rs.getInt(type + "D_input_align"), (Object[])itemInAlign, (Object[])promptInAlign, "IISC_DOMAIN_DISPLAY", true,  cp);
                
                return properties;
            }
            else
            {
                return GenerateEmptyRadioProperies(); 
            }
        }
        catch(Exception e)
        {
            return GenerateEmptyRadioProperies();
        }
    }
    
    public Property[] GenerateEmptyRadioProperies()
    {
        try
        {
            Property properties[] = new Property[4];
               
            String[] promptScroll = {"Horizontal", "Vertical"};
            Integer[] itemScroll = new Integer[2];
            itemScroll[0] = new Integer(0);
            itemScroll[1] = new Integer(1);
            
            String[] promptInAlign = {"Left", "Right", "Center"};
            Integer[] itemInAlign = new Integer[3];
            itemInAlign[0] = new Integer(0);
            itemInAlign[1] = new Integer(1);
            itemInAlign[2] = new Integer(2);
                
            properties[0] = new Property("DD_height", "height", "text", "0","IISC_DOMAIN_DISPLAY", true, cp);
            properties[1] = new Property("DD_width", "width", "text", "0","IISC_DOMAIN_DISPLAY", true, cp);
            
            properties[2] = new Property("orientation", "DD_radio_orient", "combo", 0, (Object[])itemScroll, (Object[])promptScroll, "IISC_DOMAIN_DISPLAY", true, cp);
            properties[3] = new Property("input alignment", "DD_input_align", "combo", 0, (Object[])itemInAlign, (Object[])promptInAlign, "IISC_DOMAIN_DISPLAY", true,  cp);
            
            return properties;
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
    public  Property[] GenerateComboProperies()
     {
        try
        {
            Property properties[] = new Property[4];
                        
            rs.beforeFirst();
            
            if (rs.next())
            {
                String[] promptScroll = {"No", "Yes"};
                Integer[] itemScroll = new Integer[2];
                itemScroll[0] = new Integer(0);
                itemScroll[1] = new Integer(1);
                
                String[] promptInAlign = {"Left", "Right", "Center"};
                Integer[] itemInAlign = new Integer[3];
                itemInAlign[0] = new Integer(0);
                itemInAlign[1] = new Integer(1);
                itemInAlign[2] = new Integer(2);
            
                properties[0] = new Property("DD_height", "height", "text", rs.getString(type + "D_height"),"IISC_DOMAIN_DISPLAY", true, cp);
                properties[1] = new Property("DD_width", "width", "text", rs.getString(type + "D_width"),"IISC_DOMAIN_DISPLAY", true, cp);    
                properties[2] = new Property("editable", "DD_combo_editable", "combo", rs.getInt(type + "D_combo_editable"), (Object[])itemScroll, (Object[])promptScroll, "IISC_DOMAIN_DISPLAY", true, cp);     
                properties[3] = new Property("input alignment", "DD_input_align", "combo", rs.getInt(type + "D_input_align"), (Object[])itemInAlign, (Object[])promptInAlign, "IISC_DOMAIN_DISPLAY", true,  cp);
                
            }
            else
            {
                return GenerateEmptyComboProperies();
            }
            return properties;
        }
        catch(Exception e)
        {
            return GenerateEmptyComboProperies();
        }
    }
    
    public Property[] GenerateEmptyComboProperies()
    {
        try
        {
            Property properties[] = new Property[4];
               
            String[] promptScroll = {"No", "Yes"};
            Integer[] itemScroll = new Integer[2];
            itemScroll[0] = new Integer(0);
            itemScroll[1] = new Integer(1);
            
            String[] promptInAlign = {"Left", "Right", "Center"};
            Integer[] itemInAlign = new Integer[3];
            itemInAlign[0] = new Integer(0);
            itemInAlign[1] = new Integer(1);
            itemInAlign[2] = new Integer(2);
                
            properties[0] = new Property("DD_height", "height", "text", "0","IISC_DOMAIN_DISPLAY", true, cp);
            properties[1] = new Property("DD_width", "width", "text", "0","IISC_DOMAIN_DISPLAY", true, cp);
            
            properties[2] = new Property("editable", "DD_combo_editable", "combo", 0, (Object[])itemScroll, (Object[])promptScroll, "IISC_DOMAIN_DISPLAY", true, cp);
            properties[3] = new Property("input alignment", "DD_input_align", "combo", 0, (Object[])itemInAlign, (Object[])promptInAlign, "IISC_DOMAIN_DISPLAY", true,  cp);
            return properties;
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
    
    public Property[] GenerateWHProperties()
     {
        try
        {
            Property properties[] = new Property[3];
            String[] promptInAlign = {"Left", "Right", "Center"};
            Integer[] itemInAlign = new Integer[3];
            itemInAlign[0] = new Integer(0);
            itemInAlign[1] = new Integer(1);
            itemInAlign[2] = new Integer(2);
            
            rs.beforeFirst();
            
            if (rs.next())
            {         
                properties[0] = new Property("DD_height", "height", "text", rs.getString(type + "D_height"),"IISC_DOMAIN_DISPLAY", true, cp);
                properties[1] = new Property("DD_width", "width", "text", rs.getString(type + "D_width"),"IISC_DOMAIN_DISPLAY", true, cp);
                properties[2] = new Property("input alignment", "DD_input_align", "combo", rs.getInt(type + "D_input_align"), (Object[])itemInAlign, (Object[])promptInAlign, "IISC_DOMAIN_DISPLAY", true,  cp);
           }
           else
           {
              return GenerateEmptyWHProperies(Dom_id,conn);
           }
            
            return properties;
        }
        catch(Exception e)
        {
            return GenerateEmptyWHProperies(Dom_id,conn);
        }
    }
    
    public Property[] GenerateEmptyWHProperies(int Dom_id, Connection conn)
    {
        try
        {
            Property properties[] = new Property[3];
             
            String[] promptInAlign = {"Left", "Right", "Center"};
            Integer[] itemInAlign = new Integer[3];
            itemInAlign[0] = new Integer(0);
            itemInAlign[1] = new Integer(1);
            itemInAlign[2] = new Integer(2);
            
            properties[0] = new Property("DD_height", "height", "text", "0","IISC_DOMAIN_DISPLAY", true, cp);
            properties[1] = new Property("DD_width", "width", "text", "0","IISC_DOMAIN_DISPLAY", true, cp);
            properties[2] = new Property("input alignment", "DD_input_align", "combo", 0, (Object[])itemInAlign, (Object[])promptInAlign, "IISC_DOMAIN_DISPLAY", true,  cp);
            
            return properties;
        }
        catch(Exception e)
        {
            return null;
        }
    }
}