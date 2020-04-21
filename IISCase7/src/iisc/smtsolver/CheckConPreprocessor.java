package iisc.smtsolver;

import iisc.CheckConstraint;
import iisc.DataTypesEnum;
import iisc.LogicalTerm;
import iisc.NormalFormPart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CheckConPreprocessor {

    public CheckConPreprocessor() {
    }
    
    public static void preprocess(CheckConstraint from, CheckConstraint to, ArrayList<LogicalTerm> fromBT, ArrayList<LogicalTerm> toBT)
    {
        int propCnt = 1;
        String propLetter = "p";
        
        Map<String,String> map = new HashMap<String, String>();
        
        for(int i=0;i<fromBT.size();i++)
        {
            LogicalTerm termFrom = fromBT.get(i);
            if(termFrom.getOperator().equalsIgnoreCase("LIKE")
                || termFrom.getOperator().equalsIgnoreCase("IN")){
                if(map.containsKey(termFrom.toString())){
                    String newBT = map.get(termFrom.toString());
                    
                    termFrom.setLeftOperand(newBT);
                    termFrom.setRightOperand("");
                    termFrom.setOperator("");
                    termFrom.getDeclarations().clear();
                    ArrayList<DataTypesEnum> tmp =  new ArrayList<DataTypesEnum>();
                    tmp.add(DataTypesEnum.BOOLEAN);
                    termFrom.getDeclarations().put(newBT, tmp);                    
                }
                else{
                    for(int j=0;j<toBT.size();j++){
                        LogicalTerm termTo = toBT.get(j);
                        if(termTo.getOperator().equalsIgnoreCase("LIKE")
                            || termTo.getOperator().equalsIgnoreCase("IN")){
                            if(termFrom.equals(termTo)){
                                String newBT = propLetter + propCnt++;
                                map.put(termFrom.toString(), newBT);
                                
                                termFrom.setLeftOperand(newBT);
                                termFrom.setRightOperand("");
                                termFrom.setOperator("");
                                termFrom.getDeclarations().clear();
                                ArrayList<DataTypesEnum> tmp2 =  new ArrayList<DataTypesEnum>();
                                tmp2.add(DataTypesEnum.BOOLEAN);
                                termFrom.getDeclarations().put(newBT, tmp2);
                                
                                termTo.setLeftOperand(newBT);
                                termTo.setRightOperand("");
                                termTo.setOperator("");
                                termTo.getDeclarations().clear();
                                tmp2 =  new ArrayList<DataTypesEnum>();
                                tmp2.add(DataTypesEnum.BOOLEAN);
                                termTo.getDeclarations().put(newBT, tmp2);
                            }
                            else if((termFrom.getOperator().equalsIgnoreCase("LIKE") && termTo.getOperator().equalsIgnoreCase("LIKE") && testLikeInference(termFrom, termTo))
                                    || (termFrom.getOperator().equalsIgnoreCase("IN") && termTo.getOperator().equalsIgnoreCase("IN") && testInInference(termFrom, termTo)))
                            {
                                String newBTFrom = propLetter + propCnt++;
                                String newBTTo = propLetter + propCnt++;
                                map.put(termFrom.toString(), newBTFrom);
                                map.put(termTo.toString(), newBTTo);
                                
                                termFrom.setLeftOperand(newBTFrom);
                                termFrom.setRightOperand("");
                                termFrom.setOperator("");
                                termFrom.getDeclarations().clear();
                                ArrayList<DataTypesEnum> tmp2 =  new ArrayList<DataTypesEnum>();
                                tmp2.add(DataTypesEnum.BOOLEAN);
                                termFrom.getDeclarations().put(newBTFrom, tmp2);
                                
                                termTo.setLeftOperand(newBTTo);
                                termTo.setRightOperand("");
                                termTo.setOperator("");
                                termTo.getDeclarations().clear();
                                tmp2 =  new ArrayList<DataTypesEnum>();
                                tmp2.add(DataTypesEnum.BOOLEAN);
                                termTo.getDeclarations().put(newBTTo, tmp2);
                                
                                NormalFormPart nfp = new NormalFormPart();
                                nfp.setOperator("OR");
                                LogicalTerm tmp = termFrom.getDeepCopyOf();
                                tmp.setNegated(true);
                                nfp.getBasicTerms().add(tmp);
                                nfp.getBasicTerms().add(termTo.getDeepCopyOf());
                                from.getCnf().getParts().add(nfp);
                            }
                            
                        }
                    }
                }
            }
        }
            
        for(int j=0;j<fromBT.size();j++){
            LogicalTerm term = fromBT.get(j);
            
            HashMap<String, ArrayList<DataTypesEnum>> declars = term.getDeclarations();
            boolean isString = false;
            
            if(declars.containsKey(term.getLeftOperand())){
                ArrayList<DataTypesEnum> dte = declars.get(term.getLeftOperand());
                if(dte.get(0) == DataTypesEnum.STRING)
                    isString = true;
            }
            
            if(declars.containsKey(term.getRightOperand())){
                ArrayList<DataTypesEnum> dte = declars.get(term.getRightOperand());
                if(dte.get(0) == DataTypesEnum.STRING)
                    isString = true;
            }
            
            if(isString || term.getOperator().equalsIgnoreCase("LIKE")
                || term.getOperator().equalsIgnoreCase("IN")){
                
                String newBT = "";
                if(map.containsKey(term.toString())){
                    newBT = map.get(term.toString());
                }
                else{
                    newBT = propLetter + propCnt++;
                    map.put(term.toString(), newBT);
                }
                
                term.setLeftOperand(newBT);
                term.setRightOperand("");
                term.setOperator("");
                term.getDeclarations().clear();
                ArrayList<DataTypesEnum> tmp =  new ArrayList<DataTypesEnum>();
                tmp.add(DataTypesEnum.BOOLEAN);
                term.getDeclarations().put(newBT, tmp);
            }
        }
        
        for(int j=0;j<toBT.size();j++){
            LogicalTerm term = toBT.get(j);
            
            HashMap<String, ArrayList<DataTypesEnum>> declars = term.getDeclarations();
            boolean isString = false;
                            
            if(declars.containsKey(term.getLeftOperand())){
                ArrayList<DataTypesEnum> dte = declars.get(term.getLeftOperand());
                if(dte.get(0) == DataTypesEnum.STRING)
                    isString = true;
            }
            
            if(declars.containsKey(term.getRightOperand())){
                ArrayList<DataTypesEnum> dte = declars.get(term.getRightOperand());
                if(dte.get(0) == DataTypesEnum.STRING)
                    isString = true;
            }
            
            if(isString || term.getOperator().equalsIgnoreCase("LIKE")
                || term.getOperator().equalsIgnoreCase("IN")){
                
                String newBT = "";
                if(map.containsKey(term.toString())){
                    newBT = map.get(term.toString());
                }
                else{
                    newBT = propLetter + propCnt++;
                    map.put(term.toString(), newBT);
                }
                
                term.setLeftOperand(newBT);
                term.setRightOperand("");
                term.setOperator("");
                term.getDeclarations().clear();
                ArrayList<DataTypesEnum> tmp =  new ArrayList<DataTypesEnum>();
                tmp.add(DataTypesEnum.BOOLEAN);
                term.getDeclarations().put(newBT, tmp);
            }
        }
        
    }
    
    private static boolean testLikeInference(LogicalTerm termFrom, 
                                      LogicalTerm termTo) {
        
        String[] aParts = null; 
        if(termFrom.getRightOperand().startsWith("%")){
            String tmp = " " +termFrom.getRightOperand();
            aParts = tmp.split("%");
            aParts[0] = "";    
        }else if(termFrom.getRightOperand().endsWith("%")){
            String tmp = termFrom.getRightOperand() + " ";
            aParts = tmp.split("%");
            aParts[aParts.length-1] = "";    
        }else{
            aParts = termFrom.getRightOperand().split("%");
        }
        
        String[] bParts = null;
        if(termTo.getRightOperand().startsWith("%")){
            String tmp = " " +termTo.getRightOperand();
            bParts = tmp.split("%");
            bParts[0] = "";    
        }else if(termTo.getRightOperand().endsWith("%")){
            String tmp = termTo.getRightOperand() + " ";
            bParts = tmp.split("%");
            bParts[bParts.length-1] = "";    
        }else{
            bParts = termTo.getRightOperand().split("%");
        }
        
        boolean retVal = true;
        if(aParts.length == bParts.length)
        {
            for(int i=0;i<aParts.length; i++)
            {
                if(i==0){
                    if(!aParts[i].startsWith(bParts[i]))
                    {
                        retVal = false;
                        break;
                    }
                }else if (i == aParts.length-1){
                    if(!aParts[i].endsWith(bParts[i]))
                    {
                        retVal = false;
                        break;
                    }    
                }else{
                    if(aParts[i].indexOf(bParts[i]) == -1)
                    {
                        retVal = false;
                        break;
                    }
                }
            }
        }
        else
        {
            retVal = false;
        }
        return retVal;
    }
    
    private static boolean testInInference(LogicalTerm termFrom, 
                                      LogicalTerm termTo) {
        String a = termFrom.getRightOperand().substring(1, termFrom.getRightOperand().length()-1);                              
        String b = termTo.getRightOperand().substring(1, termTo.getRightOperand().length()-1);
        
        Set<String> aParts = new HashSet<String>();
        String[] aa = a.split(","); 
        for(int i=0;i<aa.length;i++)
            aParts.add(aa[i]);
        
        Set<String> bParts = new HashSet<String>();
        String[] bb = b.split(","); 
        for(int i=0;i<bb.length;i++)
            bParts.add(bb[i]);
        
        if(bParts.containsAll(aParts))
            return true;
        else
            return false;
        
    }
}
