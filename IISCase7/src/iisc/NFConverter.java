package iisc;


/**
 * 
 * DNFConverter is a class capable of transforming any logical function
 * given as <code>LogicalFunction</code> object into disjunctive normal form.
 * 
 * @author      Nikola Obrenovic (nikob at sbb.rs)
 * @version     0.1
 * @since       1.0
 */

public class NFConverter {
    public NFConverter() {
    }
    
    public static NormalForm convertToDNF(LogFuncTree tree)
    {
        
        removeImpEqu(tree);
        lowerNegation(tree);
        distributionANDOverOR(tree);
        
        NormalForm dnf = new NormalForm();
        //first left and first down walk
        dnf.setOperator("OR");
        buildDNF(tree, dnf);
        
        return dnf;
    }
    
    public static NormalForm convertToCNF(LogFuncTree tree)
    {
        
        removeImpEqu(tree);
        lowerNegation(tree);
        distributionOROverAND(tree);
        
        NormalForm cnf = new NormalForm();
        //first left and first down walk
        cnf.setOperator("AND");
        buildCNF(tree, cnf);
        
        return cnf;
    }
    
    public static void removeImpEqu(LogFuncTree tree)
    {
        if(tree.getPlainTerm() != null) return;
        
        if(tree.getOperator().equals("IMPLICATION"))
        {
            tree.setOperator("OR");
            tree.getLeftFunc().setNegated(!tree.getLeftFunc().isNegated());
        }
        else if (tree.getOperator().equals("EQUIVALENCE"))
        {
            LogFuncTree left = tree.getLeftFunc();
            LogFuncTree negLeft = left.getDeepCopyOf();
            negLeft.setNegated(!negLeft.isNegated());
            
            LogFuncTree right = tree.getRightFunc();
            LogFuncTree negRight = right.getDeepCopyOf();
            negRight.setNegated(!negRight.isNegated());

            LogFuncTree newLeft = new LogFuncTree();
            newLeft.setOperator("AND");
            newLeft.setLeftFunc(left);
            newLeft.setRightFunc(right);
            
            LogFuncTree newRight = new LogFuncTree();
            newRight.setOperator("AND");
            newRight.setLeftFunc(negLeft);
            newRight.setRightFunc(negRight);
            
            tree.setOperator("OR");
            tree.setLeftFunc(newLeft);
            tree.setRightFunc(newRight);
        }
        else if (tree.getOperator().equals("XOR"))
        {
            LogFuncTree left = tree.getLeftFunc();
            LogFuncTree negLeft = left.getDeepCopyOf();
            negLeft.setNegated(!negLeft.isNegated());
            
            LogFuncTree right = tree.getRightFunc();
            LogFuncTree negRight = right.getDeepCopyOf();
            negRight.setNegated(!negRight.isNegated());

            LogFuncTree newLeft = new LogFuncTree();
            newLeft.setOperator("AND");
            newLeft.setLeftFunc(left);
            newLeft.setRightFunc(negRight);
            
            LogFuncTree newRight = new LogFuncTree();
            newRight.setOperator("AND");
            newRight.setLeftFunc(negLeft);
            newRight.setRightFunc(right);
            
            tree.setOperator("OR");
            tree.setLeftFunc(newLeft);
            tree.setRightFunc(newRight);
        }
        
        if(tree.getLeftFunc() != null)
            removeImpEqu(tree.getLeftFunc());
            
        if(tree.getRightFunc() != null)
            removeImpEqu(tree.getRightFunc());
    }
    
    
    public static void lowerNegation(LogFuncTree tree)
    {
        if(tree.isNegated())
        {
            if(tree.getPlainTerm() != null)
                tree.getPlainTerm().setNegated(!tree.getPlainTerm().isNegated());
            else
            {
                if(tree.getOperator().equals("AND"))
                    tree.setOperator("OR");
                else if(tree.getOperator().equals("OR"))
                    tree.setOperator("AND");
                
                if(tree.getLeftFunc() != null)    
                    tree.getLeftFunc().setNegated(!tree.getLeftFunc().isNegated());
                    
                if(tree.getRightFunc() != null)
                    tree.getRightFunc().setNegated(!tree.getRightFunc().isNegated());
            }
                
            tree.setNegated(false);
        }
        
        if(tree.getLeftFunc() != null)
            lowerNegation(tree.getLeftFunc());
            
        if(tree.getRightFunc() != null)
            lowerNegation(tree.getRightFunc());
    }
    
    /**
     * Distribution has to be done bottom-up
     * @param tree
     */
    public static void distributionANDOverOR(LogFuncTree tree)
    {
        if(tree.getPlainTerm() != null) return;
        
        if(tree.getLeftFunc().getPlainTerm() == null
            && (tree.getLeftFunc().getLeftFunc().getPlainTerm() == null
                || tree.getLeftFunc().getRightFunc().getPlainTerm() == null))    
          distributionANDOverOR(tree.getLeftFunc());
        
        if(tree.getRightFunc().getPlainTerm() == null
            && (tree.getRightFunc().getLeftFunc().getPlainTerm() == null
                || tree.getRightFunc().getRightFunc().getPlainTerm() == null))    
              distributionANDOverOR(tree.getRightFunc());
        
        if(tree.getLeftFunc().getPlainTerm() != null
            && tree.getRightFunc().getPlainTerm() == null
            && tree.getOperator().equals("AND") && tree.getRightFunc().getOperator().equals("OR"))
        {
            LogFuncTree p = tree.getLeftFunc();
            LogFuncTree q = tree.getRightFunc().getLeftFunc();
            LogFuncTree r = tree.getRightFunc().getRightFunc();
            
            LogFuncTree newLeft = new LogFuncTree();
            newLeft.setOperator("AND");
            newLeft.setLeftFunc(p);
            newLeft.setRightFunc(q);
            tree.setLeftFunc(newLeft);
            
            tree.getRightFunc().setLeftFunc(p.getDeepCopyOf());
            tree.getRightFunc().setOperator("AND");
            
            tree.setOperator("OR");
            
        }
        else if(tree.getRightFunc().getPlainTerm() != null
            && tree.getLeftFunc().getPlainTerm() == null
            && tree.getOperator().equals("AND") && tree.getLeftFunc().getOperator().equals("OR"))
        {
            LogFuncTree p = tree.getLeftFunc().getLeftFunc();
            LogFuncTree q = tree.getLeftFunc().getRightFunc();
            LogFuncTree r = tree.getRightFunc();
            
            LogFuncTree newRight = new LogFuncTree();
            newRight.setOperator("AND");
            newRight.setLeftFunc(q);
            newRight.setRightFunc(r);
            tree.setRightFunc(newRight);
            
            tree.getLeftFunc().setRightFunc(r.getDeepCopyOf());
            tree.getLeftFunc().setOperator("AND");
            
            tree.setOperator("OR");
            
        }
        else if(tree.getRightFunc().getPlainTerm() == null
            && tree.getLeftFunc().getPlainTerm() == null
            && tree.getOperator().equals("AND") && tree.getLeftFunc().getOperator().equals("OR") && tree.getRightFunc().getOperator().equals("OR"))
        {
            LogFuncTree p = tree.getLeftFunc().getLeftFunc();
            LogFuncTree q = tree.getLeftFunc().getRightFunc();
            LogFuncTree r = tree.getRightFunc().getLeftFunc();
            LogFuncTree s = tree.getRightFunc().getRightFunc();
            
            tree.setOperator("OR");
            
            LogFuncTree newLeftLeft = new LogFuncTree();
            newLeftLeft.setOperator("AND");
            newLeftLeft.setLeftFunc(p);
            newLeftLeft.setRightFunc(r);
            tree.getLeftFunc().setLeftFunc(newLeftLeft);
            
            LogFuncTree newLeftRight = new LogFuncTree();
            newLeftRight.setOperator("AND");
            newLeftRight.setLeftFunc(p.getDeepCopyOf());
            newLeftRight.setRightFunc(s);
            tree.getLeftFunc().setRightFunc(newLeftRight);

            LogFuncTree newRightLeft = new LogFuncTree();
            newRightLeft.setOperator("AND");
            newRightLeft.setLeftFunc(q);
            newRightLeft.setRightFunc(r.getDeepCopyOf());
            tree.getRightFunc().setLeftFunc(newRightLeft); 
            
            LogFuncTree newRightRight = new LogFuncTree();
            newRightRight.setOperator("AND");
            newRightRight.setLeftFunc(q.getDeepCopyOf());
            newRightRight.setRightFunc(s.getDeepCopyOf());
            tree.getRightFunc().setRightFunc(newRightRight);
            
            
        }
        
    }
    
    /**
     * Distribution has to be done bottom-up. This one is used for CNF.
     * @param tree
     */
    public static void distributionOROverAND(LogFuncTree tree)
    {
        if(tree.getPlainTerm() != null) return;
        
        if(tree.getLeftFunc().getPlainTerm() == null
            && (tree.getLeftFunc().getLeftFunc().getPlainTerm() == null
                || tree.getLeftFunc().getRightFunc().getPlainTerm() == null))    
          distributionOROverAND(tree.getLeftFunc());
        
        if(tree.getRightFunc().getPlainTerm() == null
            && (tree.getRightFunc().getLeftFunc().getPlainTerm() == null
                || tree.getRightFunc().getRightFunc().getPlainTerm() == null))    
              distributionOROverAND(tree.getRightFunc());
        
        if(tree.getLeftFunc().getPlainTerm() != null
            && tree.getRightFunc().getPlainTerm() == null
            && tree.getOperator().equals("OR") && tree.getRightFunc().getOperator().equals("AND"))
        {
            LogFuncTree p = tree.getLeftFunc();
            LogFuncTree q = tree.getRightFunc().getLeftFunc();
            LogFuncTree r = tree.getRightFunc().getRightFunc();
            
            LogFuncTree newLeft = new LogFuncTree();
            newLeft.setOperator("OR");
            newLeft.setLeftFunc(p);
            newLeft.setRightFunc(q);
            tree.setLeftFunc(newLeft);
            
            tree.getRightFunc().setLeftFunc(p.getDeepCopyOf());
            tree.getRightFunc().setOperator("OR");
            
            tree.setOperator("AND");
            
        }
        else if(tree.getRightFunc().getPlainTerm() != null
            && tree.getLeftFunc().getPlainTerm() == null
            && tree.getOperator().equals("OR") && tree.getLeftFunc().getOperator().equals("AND"))
        {
            LogFuncTree p = tree.getLeftFunc().getLeftFunc();
            LogFuncTree q = tree.getLeftFunc().getRightFunc();
            LogFuncTree r = tree.getRightFunc();
            
            LogFuncTree newRight = new LogFuncTree();
            newRight.setOperator("OR");
            newRight.setLeftFunc(q);
            newRight.setRightFunc(r);
            tree.setRightFunc(newRight);
            
            tree.getLeftFunc().setRightFunc(r.getDeepCopyOf());
            tree.getLeftFunc().setOperator("OR");
            
            tree.setOperator("AND");
            
        }
        else if(tree.getRightFunc().getPlainTerm() == null
            && tree.getLeftFunc().getPlainTerm() == null
            && tree.getOperator().equals("OR") && tree.getLeftFunc().getOperator().equals("AND") && tree.getRightFunc().getOperator().equals("AND"))
        {
            LogFuncTree p = tree.getLeftFunc().getLeftFunc();
            LogFuncTree q = tree.getLeftFunc().getRightFunc();
            LogFuncTree r = tree.getRightFunc().getLeftFunc();
            LogFuncTree s = tree.getRightFunc().getRightFunc();
            
            tree.setOperator("AND");
            
            LogFuncTree newLeftLeft = new LogFuncTree();
            newLeftLeft.setOperator("OR");
            newLeftLeft.setLeftFunc(p);
            newLeftLeft.setRightFunc(r);
            tree.getLeftFunc().setLeftFunc(newLeftLeft);
            
            LogFuncTree newLeftRight = new LogFuncTree();
            newLeftRight.setOperator("OR");
            newLeftRight.setLeftFunc(p.getDeepCopyOf());
            newLeftRight.setRightFunc(s);
            tree.getLeftFunc().setRightFunc(newLeftRight);

            LogFuncTree newRightLeft = new LogFuncTree();
            newRightLeft.setOperator("OR");
            newRightLeft.setLeftFunc(q);
            newRightLeft.setRightFunc(r.getDeepCopyOf());
            tree.getRightFunc().setLeftFunc(newRightLeft); 
            
            LogFuncTree newRightRight = new LogFuncTree();
            newRightRight.setOperator("OR");
            newRightRight.setLeftFunc(q.getDeepCopyOf());
            newRightRight.setRightFunc(s.getDeepCopyOf());
            tree.getRightFunc().setRightFunc(newRightRight);
            
            
        }
        
    }
    
    public static void buildDNF(LogFuncTree tree, NormalForm dnf)
    {
        if (tree.getPlainTerm() != null)
        {
            dnf.addNFPart("AND");
            dnf.addBasicTerm(tree.getPlainTerm());
        }
        else if(tree.getOperator().equals("AND"))
        {
            NormalFormPart d = new NormalFormPart("AND");
            CollectConjuncts(tree,d);
            dnf.getParts().add(d);
        }
        else
        {
            if(tree.getLeftFunc() != null)
                buildDNF(tree.getLeftFunc(), dnf);
            
            if(tree.getRightFunc() != null)
                buildDNF(tree.getRightFunc(), dnf);
        }
        
    }

    public static void buildCNF(LogFuncTree tree, NormalForm cnf)
    {
        if (tree.getPlainTerm() != null)
        {
            cnf.addNFPart("OR");
            cnf.addBasicTerm(tree.getPlainTerm());
        }
        else if(tree.getOperator().equals("OR"))
        {
            NormalFormPart d = new NormalFormPart("OR");
            CollectConjuncts(tree,d);
            cnf.getParts().add(d);
        }
        else
        {
            if(tree.getLeftFunc() != null)
                buildCNF(tree.getLeftFunc(), cnf);
            
            if(tree.getRightFunc() != null)
                buildCNF(tree.getRightFunc(), cnf);
        }
        
    }
    
    private static void CollectConjuncts(LogFuncTree tree, NormalFormPart dis)
    {
        if(tree.getLeftFunc() != null)
            CollectConjuncts(tree.getLeftFunc(), dis);
            
        if(tree.getPlainTerm() != null)
            dis.getBasicTerms().add(tree.getPlainTerm());
            
        if(tree.getRightFunc() != null)
            CollectConjuncts(tree.getRightFunc(), dis);
    }
}
