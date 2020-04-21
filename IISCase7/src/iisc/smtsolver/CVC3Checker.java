package iisc.smtsolver;

import iisc.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.InputStreamReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CVC3Checker implements CheckCntInferenceChecker
{
    public CVC3Checker() {
    }

    public boolean checkInferrence(CheckConstraint from, CheckConstraint to) {
        
        try{
        
            ArrayList<LogicalTerm> fromBT = from.getAllBasicTermsCNF();
            ArrayList<LogicalTerm> toBT = to.getAllBasicTermsCNF();
            CheckConPreprocessor.preprocess(from, to, fromBT, toBT);
            
            File inFile = new File("input.cvc");
             
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
            
            for(int i=0;i<to.getCnf().getParts().size();i++){
                NormalFormPart nfp = to.getCnf().getParts().get(i);
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
                 
                 if(decl.size() == 1){
                    bw.write(name + " : " + getSMTType(decl.get(0)) + ";\n");
                 }
                 else{
                     bw.write(name + " : (");
                     for(int i=1;i<decl.size();i++){
                         if(i>1) bw.write(", ");
                         bw.write(getSMTType(decl.get(i)));
                     }
                     bw.write(") -> " + getSMTType(decl.get(0)) + ";\n");
                 }
                 
                 ///it.remove(); // avoids a ConcurrentModificationException
             }
            
            bw.write("\n");
                  
            for(int i=0;i<from.getCnf().getParts().size();i++)
            {
                String line = "";
                NormalFormPart nfp = from.getCnf().getParts().get(i);
                for(int j=0; j<nfp.getBasicTerms().size();j++){
                    if(j>0)
                        line += " OR ";
                    line += translateToCVC3(nfp.getBasicTerms().get(j));
                }
                bw.write("ASSERT (" + line + ");\n");
            }
            
            for(int i=0;i<to.getCnf().getParts().size();i++)
            {
                String line = "";
                NormalFormPart nfp = to.getCnf().getParts().get(i);
                for(int j=0; j<nfp.getBasicTerms().size();j++){
                    if(j>0)
                        line += " OR ";
                    line += translateToCVC3(nfp.getBasicTerms().get(j));
                }
                bw.write("QUERY (" + line + ");\n");
            }
            
            bw.close();
            
            boolean inference = true;
            
            try {
                
                Process p = Runtime.getRuntime().exec(".\\smtsolvers\\cvc3-2.4.1-win32-optimized\\bin\\cvc3.exe input.cvc");
                                
                String line;
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()) );
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                    if(!line.equalsIgnoreCase("Valid."))
                        inference = false;
                }
                in.close();
                
                /*BufferedReader bri = new BufferedReader(new FileReader(new File("outCVC3.txt")));
                while ((line = bri.readLine()) != null) {
                    if(line.equalsIgnoreCase("Valid."))
                        inference = true;
                }
                bri.close();*/
                
                p.waitFor();
            }
            catch (Exception err) {
                err.printStackTrace();
            }
            
            return inference;
        
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    private String translateToCVC3(LogicalTerm term) {
    
        String retVal = "";
        
        String oper = "";
        if(term.getOperator().equalsIgnoreCase("=="))
            oper = "=";
        else if(term.getOperator().equalsIgnoreCase("!="))
            oper = "/=";
        else
            oper = term.getOperator();
            
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
            
        if(leftDate != null)
            retVal += leftDate.getTime() + oper + term.getRightOperand();
        else if (rightDate != null)
            retVal += term.getLeftOperand() + oper + rightDate.getTime();
        else
            retVal += term.getLeftOperand() + oper + term.getRightOperand();
        
        if(term.isNegated())
            retVal = "NOT(" + retVal + ")";
        
        return retVal;
    
    }

    private String getSMTType(DataTypesEnum dt) {
        
        switch(dt){
            case VOID:
                return "VOID";
            case BOOLEAN:
                return "BOOLEAN";
            case INTEGER: case STRING:
                return "INT";
            default:
                return "REAL";
        }
    }
}
