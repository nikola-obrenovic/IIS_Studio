package iisc.lang.vmashine;

public class FuncSpec 
{
    public int varCount;
    public Instruction[] dataIns;
    public int dataInsCont;
    public Instruction[] codeIns;
    public int codeInsCont;
    public String name;
    Variable[] vars;
    
    public FuncSpec() 
    {
    }
}
