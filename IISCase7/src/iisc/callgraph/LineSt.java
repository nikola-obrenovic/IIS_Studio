package iisc.callgraph;

import java.math.*;

public class LineSt
{
    int i;
    int j;
    int x1;
    int y1;
    int x2;
    int y2;
    int CS_id;
    int X1;
    int Y1;
    int X2;
    int Y2;
    double k;
    double n;
    double temp;
    public boolean dirty = true;
    CallingGraph gr;
    FormPanel fp;
    
    public LineSt(int _i, int _j, int _x1, int _y1, int _x2, int _y2, int _CS_id, CallingGraph _gr)
    {
        i = _i;
        j = _j;
        x1 = _x1;
        y1 = _y1;
        x2 = _x2;
        y2 = _y2;
        CS_id = _CS_id;
        gr = _gr;
    }
    
    public void UpdateLeft()
    {
        fp = (FormPanel)gr.tfVec.get(i);
        
        if (x1 == x2)
        {
            X1 = x1;
            
            if ((fp.y >= Math.min(y1, y2)) && (fp.y <= Math.max(y1, y2)))
            {
                Y1 = fp.y;
            }
            else
            {
                Y1 = fp.y + fp.getHeight();
            }
            return;
        }
        
        if (y1 == y2)
        {
            Y1 = y1;
            
            if ((fp.x >= Math.min(x1, x2)) && (fp.x <= Math.max(x1,x2)))
            {
                X1 = fp.x;
            }
            else
            {
                X1 = fp.x + fp.getWidth();
            }
            return;
        }
        
        k = ((double) y2 - y1)/((double)x2 - x1);
        n = ((double)(y1 * x2 - y2 * x1))/((double)x2 - x1);
        
        temp = k * fp.x + n;
        
        if ((temp >= Math.min(y1, y2)) && (temp <= Math.max(y1, y2)) && (temp >= fp.y) && (temp <= fp.y + fp.getHeight()))
        {
            X1 = fp.x;
            Y1 = (int)temp;
            return;
            
        }
        
        temp = k * (fp.x + fp.getWidth()) + n;
        
        if ((temp >= Math.min(y1, y2)) && (temp <= Math.max(y1, y2)) && (temp >= fp.y) && (temp <= fp.y + fp.getHeight()) && (temp >= fp.y) && (temp <= fp.y + fp.getHeight()))
        {
            X1 = fp.x + fp.getWidth();
            Y1 = (int)temp;
            return;
        }
        
        temp = (fp.y - n) / k;
        
        if ((temp >= Math.min(x1, x2)) && (temp <= Math.max(x1, x2)) && (temp >= fp.x) && (temp <= fp.x + fp.getWidth()))
        {
            X1 = (int)temp;
            Y1 = fp.y;
            return;
        }
       
        X1 = (int) ((fp.y + fp.getHeight()- n) / k);
        Y1 = fp.y + fp.getHeight();
        
        return;
        
    }
    
    public void UpdateRight()
    {
        fp = (FormPanel)gr.tfVec.get(j);
        
        if (x1 == x2)
        {
            X2 = x2;
            
            if ((fp.y >= Math.min(y1, y2)) && (fp.y <= Math.max(y1, y2)))
            {
                Y2 = fp.y;
            }
            else
            {
                Y2 = fp.y + fp.getHeight();
            }
            return;
        }
        
        if (y1 == y2)
        {
            Y2 = y2;
            
            if ((fp.x >= Math.min(x1, x2)) && (fp.x <= Math.max(x1,x2)) )
            {
                X2 = fp.x;
            }
            else
            {
                X2 = fp.x + fp.getWidth();
            }
            return;
        }
       
        temp = k * fp.x + n;
        
        if ((temp >= Math.min(y1, y2)) && (temp <= Math.max(y1, y2)) && (temp >= fp.y) && (temp <= fp.y + fp.getHeight()))
        {
            X2 = fp.x;
            Y2 = (int)temp;
            return;
        }
        
        temp = k * (fp.x + fp.getWidth()) + n;
        
        if ((temp >= Math.min(y1, y2)) && (temp <= Math.max(y1, y2)) && (temp >= fp.y) && (temp <= fp.y + fp.getHeight()))
        {
            X2 = fp.x + fp.getWidth();
            Y2 = (int)temp;
            return;
        }
        
        temp = (fp.y - n) / k;
        
        if ((temp >= Math.min(x1, x2)) && (temp <= Math.max(x1, x2)) && (temp >= fp.x) && (temp <= fp.x + fp.getWidth()))
        {
            X2 = (int)temp;
            Y2 = fp.y;
            return;
        }
       
        X2 = (int)((fp.y + fp.getHeight()- n) / k);
        Y2 = fp.y + fp.getHeight();
        
        return;
        
    }
}
    