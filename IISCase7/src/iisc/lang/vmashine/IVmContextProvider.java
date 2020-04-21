package iisc.lang.vmashine;

import java.util.ArrayList;

public interface IVmContextProvider 
{
    public String GetEnvVar(String varName);
    public void ExecuteFunction(String funcName, ArrayList params);
    public void DispatchSignal(ArrayList params);
}
