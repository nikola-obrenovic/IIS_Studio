package iisc;

import java.io.IOException;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.*;

import org.xml.sax.SAXException;


public class LogFuncTreeBuilder {
    
    public LogFuncTreeBuilder() {
    }
    
    public static LogFuncTree BuildFunctionTree(String xml)
    {
        LogFuncTree tree = new LogFuncTree();
        //  create a DOM parser
        DOMParser parser = new DOMParser();
        //  parse the document
        try {
            parser.parse(new org.xml.sax.InputSource(new java.io.StringReader(xml)));
            Document document = parser.getDocument();
            Element node = (Element)document.getFirstChild();
            //skipping "Expression" node
            if(node.getAttribute("name").equals("Expression"))
            {
                for(int i=0;i<node.getChildNodes().getLength();i++)
                {
                    if(node.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE)
                    {
                        node = (Element)node.getChildNodes().item(i);
                        break;
                    }
                }
            }
            
            BuildFuncTreeLevel(node, tree);
        
        } catch (IOException e) {
            System.err.println(e);
        } catch (SAXException e) {
             System.err.println(e);
        }
        
        return tree;

    }
    
    public static void BuildFuncTreeLevel(Node node, LogFuncTree tree)
    {
        String nodeType = ((Element)node).getAttribute("name");
        
        Node leftChildElem = null;
        Node rightChildElem = null;
        for(int i=0;i<node.getChildNodes().getLength();i++)
        {
            if(node.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE)
                if(leftChildElem == null)
                    leftChildElem = node.getChildNodes().item(i);
                else
                    rightChildElem = node.getChildNodes().item(i);
        }
        
        
        if(nodeType.equals("BLOCK"))
            BuildFuncTreeLevel(leftChildElem, tree);
            
        else if (nodeType.equals("AND") 
                 || nodeType.equals("OR")
                 || nodeType.equals("XOR")
                 || nodeType.equals("IMPLICATION")
                 || nodeType.equals("EQUIVALENCE"))
        {
            tree.setOperator(nodeType);
            LogFuncTree left = new LogFuncTree();
            BuildFuncTreeLevel(leftChildElem,left);
            tree.setLeftFunc(left);
            
            LogFuncTree right = new LogFuncTree();
            BuildFuncTreeLevel(rightChildElem,right);
            tree.setRightFunc(right);
        }
        
        else if (nodeType.equals("IS EQUAL") 
                 || nodeType.equals("IS DIFFERENT")
                 || nodeType.equals("LESS THAN")
                 || nodeType.equals("LESS THAN OR EQUAL TO")
                 || nodeType.equals("GREATER THAN")
                 || nodeType.equals("GREATER THAN OR EQUAL TO"))
        {
            LogicalTerm term = new LogicalTerm();
            term.setOperator(TranslateOperator(nodeType));
            term.setLeftOperand(BuildArithFunc(leftChildElem,term));
            term.setRightOperand(BuildArithFunc(rightChildElem,term));
            
            tree.setPlainTerm(term);
        }
        else if (nodeType.equals("IN"))
        {
            LogicalTerm term = new LogicalTerm();
            term.setOperator(nodeType);
            
            Element leftOperElem = null;
            for(int i=0;i<leftChildElem.getChildNodes().getLength();i++)
            {
                if(leftChildElem.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE)
                    leftOperElem = (Element)leftChildElem.getChildNodes().item(i);
            
            }
            term.setLeftOperand(leftOperElem.getFirstChild().getNodeValue());
            term.getRefAttributes().add(term.getLeftOperand());
            
            String inSet = "";
            for(int i =0;i<rightChildElem.getChildNodes().getLength();i++){
                if(rightChildElem.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE){
                    if (!inSet.equals("")) inSet += ",";
                    inSet += ((Element)rightChildElem.getChildNodes().item(i)).getAttribute("value");
                }
            }
            inSet = "(" + inSet + ")";
            term.setRightOperand(inSet);
            tree.setPlainTerm(term);
        }
        else if (nodeType.equals("LIKE"))
        {
            LogicalTerm term = new LogicalTerm();
            term.setOperator(nodeType);
            Element leftOperElem = null;
            for(int i=0;i<leftChildElem.getChildNodes().getLength();i++)
            {
                if(leftChildElem.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE)
                    leftOperElem = (Element)leftChildElem.getChildNodes().item(i);
            
            }
            term.setLeftOperand(leftOperElem.getFirstChild().getNodeValue());
            term.getRefAttributes().add(term.getLeftOperand());
                        
            term.setRightOperand(rightChildElem.getFirstChild().getNodeValue());
            
            tree.setPlainTerm(term);
        }
        else if(nodeType.startsWith("FUNCTION"))
        {
            //TODO: check that function returns boolean
             LogicalTerm term = new LogicalTerm();
             term.setLeftOperand(BuildUserFunc(node,term));
        }
    }
    
    public static String BuildArithFunc(Node node, LogicalTerm term)
    {
        String nodeType = ((Element)node).getAttribute("name");
        
        Node leftChildElem = null;
        Node rightChildElem = null;
        for(int i=0;i<node.getChildNodes().getLength();i++)
        {
            if(node.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE)
                if(leftChildElem == null)
                    leftChildElem = node.getChildNodes().item(i);
                else
                    rightChildElem = node.getChildNodes().item(i);
        }
        
        if(((Element)node).getAttribute("name").equals("Simple Expression"))
        {
            term.getRefAttributes().add(node.getFirstChild().getNodeValue());
            return node.getFirstChild().getNodeValue();
        }
        else if(nodeType.startsWith("FUNCTION"))
        {
            return BuildUserFunc(node, term);
        }
        else if(nodeType.equals("BLOCK"))
        {
            return "(" + BuildArithFunc(leftChildElem, term) + ")";
        }
        else if (nodeType.equals("ADDITION"))
        {
            return BuildArithFunc(leftChildElem,term) + "+" + BuildArithFunc(rightChildElem,term);
        }
        else if (nodeType.equals("SUBSTRACTION"))
        {
            return BuildArithFunc(leftChildElem,term) + "-" + BuildArithFunc(rightChildElem,term);
        }
        else if (nodeType.equals("MULTIPLICATION"))
        {
            return BuildArithFunc(leftChildElem,term) + "*" + BuildArithFunc(rightChildElem,term);
        }
        else if (nodeType.equals("DIVISION"))
        {
            return BuildArithFunc(leftChildElem,term) + "/" + BuildArithFunc(rightChildElem,term);
        }
        
        return null;
    }
    
    public static String BuildUserFunc(Node node, LogicalTerm term)
    {
        String funcName = ((Element)node).getAttribute("name").split("-")[1].trim();
        
        String func = funcName + "(";
        int attrCnt = 0;
        for(int i=0;i<node.getChildNodes().getLength();i++)
        {
            if(node.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE)
            {
                Node argNode = node.getChildNodes().item(i);
                //this is to skip ARG XML node and get to real expression which is the argument
                for(int j=0;j<argNode.getChildNodes().getLength();j++)
                    if(argNode.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE)
                    {
                        argNode = argNode.getChildNodes().item(i);
                        break;
                    }
                        
                if(attrCnt != 0)   
                    func += ',';
                func += BuildArithFunc(argNode, term);
                attrCnt++;
            }
        }
        func += ')';
        
        return func;
        
        
    }

    private static String TranslateOperator(String oper) {
        if(oper.equals("IS EQUAL"))
            return "==";
        else if(oper.equals("IS DIFFERENT"))
            return "!=";
        else if(oper.equals("LESS THAN"))
            return "<";
        else if(oper.equals("LESS THAN OR EQUAL TO"))
            return "<=";
        else if(oper.equals("GREATER THAN"))
            return ">";
        else if(oper.equals("GREATER THAN OR EQUAL TO"))
            return ">=";
        else
            return "";
            
    }
}
