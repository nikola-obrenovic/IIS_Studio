package iisc.lang;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class WatchTableModel extends AbstractTableModel 
{
    public ArrayList data = new ArrayList();
    
    public WatchTableModel(ArrayList watchwars) 
    {
        this.data = watchwars;
    }
    
    public WatchTableModel() 
    {
    }
    
    public int getColumnCount()
    {
        return 2;
    }
    
    public int getRowCount()
    {
        return this.data.size();
    }
    
    public String getValueAt(int i, int j)
    {
        VarRef vRef =  (VarRef)this.data.get(i);
        
        if ( j == 0 )
        {
            return vRef.fullVarName;
        }
        else
        {
            return vRef.varValue;
        }
    }
    
    public String getColumnName(int c) 
    {
        if (c==0) 
        {
            return "Variable name";
        }
        else
        {
            return "Variable value";
        }
    }
}
