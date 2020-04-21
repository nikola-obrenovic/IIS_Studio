package iisc;

import java.util.ArrayList;

public class LogTermFunction extends LogTermOperand {

    private LogTermOperand retVal;
    private ArrayList<LogTermOperand> params;
    
    public LogTermFunction(LogTermOperand ret, ArrayList<LogTermOperand> pars) {
        retVal = ret;
        params = pars;
    }


    public void setRetVal(LogTermOperand retVal) {
        this.retVal = retVal;
    }

    public LogTermOperand getRetVal() {
        return retVal;
    }

    public void setParams(ArrayList<LogTermOperand> params) {
        this.params = params;
    }

    public ArrayList<LogTermOperand> getParams() {
        return params;
    }
}
