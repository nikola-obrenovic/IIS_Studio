package iisc;

/**
 * LogFuncTree ...
 * 
 * @author      Nikola Obrenovic (nikob at sbb.rs)
 * @version     0.1 05/18/2010
 * @since       1.0
 */
public class LogFuncTree {
    
    protected LogFuncTree leftFunc = null;
    protected LogFuncTree rightFunc = null;
    protected String operator = null;
    protected LogicalTerm plainTerm = null;
    protected boolean negated = false;
    
    public LogFuncTree() {
    }


    public void setLeftFunc(LogFuncTree leftFunc) {
        this.leftFunc = leftFunc;
    }

    public LogFuncTree getLeftFunc() {
        return leftFunc;
    }

    public void setRightFunc(LogFuncTree rightFunc) {
        this.rightFunc = rightFunc;
    }

    public LogFuncTree getRightFunc() {
        return rightFunc;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public void setPlainTerm(LogicalTerm plainTerm) {
        this.plainTerm = plainTerm;
    }

    public LogicalTerm getPlainTerm() {
        return plainTerm;
    }


    public void setNegated(boolean negated) {
        this.negated = negated;
    }

    public boolean isNegated() {
        return negated;
    }

    public LogFuncTree getDeepCopyOf() {
        LogFuncTree newTree = new LogFuncTree();
        newTree.operator = operator;
        newTree.negated = negated;
        
        if(leftFunc != null)
            newTree.leftFunc = leftFunc.getDeepCopyOf();
        else
            newTree.leftFunc = null;
            
        if(rightFunc != null)
            newTree.rightFunc = rightFunc.getDeepCopyOf();
        else
            newTree.rightFunc = null;
            
        if(plainTerm != null)
            newTree.plainTerm = plainTerm.getDeepCopyOf();
        else
            newTree.plainTerm = null;
            
        return newTree;
    }
}
