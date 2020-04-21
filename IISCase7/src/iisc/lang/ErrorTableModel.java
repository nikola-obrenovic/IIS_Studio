package iisc.lang;

import java.util.Vector;

import javax.swing.table.TableModel;
import javax.swing.table.AbstractTableModel;

public class ErrorTableModel extends AbstractTableModel 
{
    public Vector data = new Vector();
    
    public ErrorTableModel() 
    {
    }
    
    public int getColumnCount()
    {
        return 1;
    }
    
    public int getRowCount()
    {
        return this.data.size();
    }
    
    public String getValueAt(int i, int j)
    {
        return this.data.get(i).toString();
    }
    
    public String getColumnName(int c) 
    {
        return "Description";
    }
}
