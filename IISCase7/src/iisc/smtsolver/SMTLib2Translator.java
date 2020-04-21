package iisc.smtsolver;

import iisc.CheckConstraint;
import iisc.DataTypesEnum;
import iisc.LogicalTerm;
import iisc.NormalFormPart;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SMTLib2Translator {
    
    public SMTLib2Translator() {
    }
    
    public static File createInputFile(CheckConstraint from, CheckConstraint to) {
        
        try{
        
            File inFile = new File("input.smt2");
             
            if (inFile.exists()) {
                inFile.delete();
            }
            inFile.createNewFile();
            
            FileWriter fw = new FileWriter(inFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            
            HashMap<String, ArrayList<DataTypesEnum>> declarations = new HashMap<String, ArrayList<DataTypesEnum>>();
            
            for(int i=0;i<from.getCnf().getParts().size();i++){
                NormalFormPart nfp = from.getCnf().getParts().get(i);
                for(int j=0; j<nfp.getBasicTerms().size();j++){
                    LogicalTerm lt = nfp.getBasicTerms().get(j);
                    Iterator it = lt.getDeclarations().entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry)it.next();
                        if(!declarations.containsKey(pair.getKey()))
                        {
                            declarations.put((String)pair.getKey(), (ArrayList<DataTypesEnum>)pair.getValue());
                        }
                        //it.remove(); // avoids a ConcurrentModificationException
                    }
                    
                }
            }
            
            for(int i=0;i<to.getDnf().getParts().size();i++){
                NormalFormPart nfp = to.getDnf().getParts().get(i);
                for(int j=0; j<nfp.getBasicTerms().size();j++){
                    LogicalTerm lt = nfp.getBasicTerms().get(j);
                    Iterator it = lt.getDeclarations().entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry)it.next();
                        if(!declarations.containsKey(pair.getKey()))
                        {
                            declarations.put((String)pair.getKey(), (ArrayList<DataTypesEnum>)pair.getValue());
                        }
                        //it.remove(); // avoids a ConcurrentModificationException
                    }
                    
                }
            }
            
             Iterator it = declarations.entrySet().iterator();
             while (it.hasNext()) {
                 Map.Entry pair = (Map.Entry)it.next();
                 
                 String name = (String)pair.getKey();
                 ArrayList<DataTypesEnum> decl= (ArrayList<DataTypesEnum>)pair.getValue();
                 
                 bw.write("(declare-fun " + name + " (");
                 for(int i=1;i<decl.size();i++){
                     if(i>1) bw.write(" ");
                     bw.write(getSMTType(decl.get(i)));
                 }
                 bw.write(") " + getSMTType(decl.get(0)) + ")\n");
                 
                 //it.remove(); // avoids a ConcurrentModificationException
             }
            
            bw.write("\n");
                  
            for(int i=0;i<from.getCnf().getParts().size();i++)
            {
                String line = "";
                NormalFormPart nfp = from.getCnf().getParts().get(i);
                for(int j=0; j<nfp.getBasicTerms().size();j++){
                    if(j>0)
                        line += " ";
                    line += translateToSMTLib2(nfp.getBasicTerms().get(j));
                }
                if(nfp.getBasicTerms().size()>1)
                    line = "( or " + line + ")";
                bw.write("(assert " + line + ")\n");
            }
            
            for(int i=0;i<to.getDnf().getParts().size();i++)
            {
                String line = "";
                NormalFormPart nfp = to.getDnf().getParts().get(i);
                for(int j=0; j<nfp.getBasicTerms().size();j++){
                    line += "(not " + translateToSMTLib2(nfp.getBasicTerms().get(j)) + ")";
                }
                
                if(nfp.getBasicTerms().size()>1)
                    line = "( or " + line + ")";
                bw.write("(assert " + line + ")\n");
            }
            
            bw.write("\n");
            bw.write("(check-sat)\n");
            
            bw.close();
            
            return inFile;
        
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private static String translateToSMTLib2(LogicalTerm term) {
    
        String retVal = "";
        
        Date leftDate = null, rightDate = null;
        try
        {
            SimpleDateFormat parser = new SimpleDateFormat("mm/dd/yyyy");
            leftDate = parser.parse(term.getLeftOperand());  
        }
        catch(ParseException ex)
        {
            leftDate = null;
        }
        
        try
        {
            SimpleDateFormat parser = new SimpleDateFormat("mm/dd/yyyy");
            rightDate = parser.parse(term.getRightOperand());  
        }
        catch(ParseException ex)
        {
            rightDate = null;
        }
            
        String leftOper = term.getLeftOperand();    
        if(leftDate != null)
            leftOper = "" + leftDate.getTime();
        
        String rightOper = term.getRightOperand();
        if (rightDate != null)
            rightOper = "" + rightDate.getTime();
        
        String oper = term.getOperator();
        if(oper.equals("=="))
            oper = "=";
        else if(oper.equals("!="))
            oper = " distinct ";
        if(!oper.equals(""))
            retVal += "(" + oper + " " + leftOper + " " + rightOper + ")";
        else
            retVal += leftOper;
        
        if(term.isNegated())
            retVal = "(not " + retVal + ")";
            
        return retVal;
    
    }

    private static String getSMTType(DataTypesEnum dt) {
        
        switch(dt){
            case VOID:
                return "Void";
            case BOOLEAN:
                return "Bool";
            case INTEGER: case STRING:
                return "Int";
            default:
                return "Real";
        }
    }
}
