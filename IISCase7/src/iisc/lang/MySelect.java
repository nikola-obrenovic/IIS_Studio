package iisc.lang;

import org.gjt.sp.jedit.buffer.JEditBuffer;
import org.gjt.sp.jedit.textarea.Selection;

public class MySelect 
{
    public MySelect() {
    }

    public int getStart(JEditBuffer jEditBuffer, int i) {
        return 0;
    }

    public int getEnd(JEditBuffer jEditBuffer, int i) {
        return 0;
    }

    void getText(JEditBuffer jEditBuffer, StringBuilder stringBuilder) 
    {
        int t ;
    }

    int setText(JEditBuffer jEditBuffer, String string) {
        return 0;
    }

    boolean contentInserted(JEditBuffer jEditBuffer, int i, int i1, int i2, 
                            int i3) {
        return false;
    }

    boolean contentRemoved(JEditBuffer jEditBuffer, int i, int i1, int i2, 
                           int i3) {
        return false;
    }
}
