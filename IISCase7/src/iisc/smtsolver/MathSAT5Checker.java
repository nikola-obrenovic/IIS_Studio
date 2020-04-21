package iisc.smtsolver;

import iisc.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MathSAT5Checker implements CheckCntInferenceChecker
{
    public MathSAT5Checker() {
    }

    public boolean checkInferrence(CheckConstraint from, CheckConstraint to) {
        
        try{
        
            ArrayList<LogicalTerm> fromBT = from.getAllBasicTermsCNF();
            ArrayList<LogicalTerm> toBT = to.getAllBasicTermsDNF();
            CheckConPreprocessor.preprocess(from, to, fromBT, toBT);
        
            File inFile = SMTLib2Translator.createInputFile(from, to);
             
            if (inFile == null) return false;
            
            boolean inference = true;
            
            try {
                File of = new File("outMathSat.txt");
                if(of.exists())
                    of.delete();
                    
                Process p = Runtime.getRuntime().exec(".\\smtsolvers\\mathsat-5.2.4-win32-msvc\\RunMathSat5.bat");
                p.waitFor();
                
                String line;
                BufferedReader bri = new BufferedReader(new FileReader(new File("outMathSat.txt")));
                while ((line = bri.readLine()) != null) {
                    if(!line.equalsIgnoreCase("unsat"))
                        inference = false;
                }
                bri.close();
                
            }
            catch (Exception err) {
                err.printStackTrace();
            }
            
            return inference;
        
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    
}
