package iisc.lang;

import iisc.IISFrameMain;

import iisc.JDBCQuery;
import iisc.SearchTable;
import iisc.Settings;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.SystemColor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.Connection;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTree;

import javax.swing.event.TreeModelEvent;
import javax.swing.tree.DefaultMutableTreeNode;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;

import javax.swing.tree.TreePath;

import org.gjt.sp.jedit.textarea.Selection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CommandTree extends JTree
{
    CommTreeNode root = new CommTreeNode();
    CommTreeNode argsNode =  new CommTreeNode();
    CommTreeNode varsNode =  new CommTreeNode();
    CommTreeNode bodyNode =  new CommTreeNode();
    MyTextArea area = null;
    ArrayList undoStack = new ArrayList();
    ArrayList undoStackText = new ArrayList();
    ArrayList undoStackCarrPos = new ArrayList();
    private int undoPos = -1;
    private long lastActionTime = 0;
    TreeModelEvent lastEvent;
    private static final int undoCount = 10;
    CommTreeNode cutcopyNode = null;
    String cutcopyText = "";
    public boolean editable = false;
    int PR_id;
    private ImageIcon undoIcon = new ImageIcon(IISFrameMain.class.getResource("icons/arrow_undo.png"));
    private ImageIcon redoIcon = new ImageIcon(IISFrameMain.class.getResource("icons/arrow_redo.png"));
    private ImageIcon cutIcon = new ImageIcon(IISFrameMain.class.getResource("icons/cut2.png"));
    private ImageIcon copyIcon = new ImageIcon(IISFrameMain.class.getResource("icons/page_copy.png"));
    private ImageIcon pasteIcon = new ImageIcon(IISFrameMain.class.getResource("icons/page_paste.png"));
    private ImageIcon deleteIcon = new ImageIcon(IISFrameMain.class.getResource("icons/bullet_delete.png"));
    private ImageIcon upIcon = new ImageIcon(IISFrameMain.class.getResource("icons/arrow_down.png"));
    private ImageIcon downIcon = new ImageIcon(IISFrameMain.class.getResource("icons/arrow_up.png"));
    
    public CommandTree(String funcName, MyTextArea area, int PR_id) 
    {
        this.area = area;
        this.InitRootNodes(funcName);    
        this.setCellRenderer(new CommTreeCellRenderer());
        this.editable = false;
        
        this.addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent me) 
            {
                doMouseClicked(me);
            }
        });
    }
    
    private String ParseBooleanExpression(String exp)
    {
        String result = "";
        int errCount = this.area.editor.parser.getErrors().size();
        Element node = (Element)this.area.editor.parser.ParseExp(exp);
        int new_errCount = this.area.editor.parser.getErrors().size();
        
        if (new_errCount > errCount)
        {
            for(int i = errCount; i < new_errCount; i++)
            {
                ErrorDescription errDesc = (ErrorDescription)this.area.editor.parser.getErrors().get(i);
                result += errDesc.message + "\n";
            }
            return result;
        }
        else
        {
            if (!this.area.editor.parser.CheckCond(node))
            {
                return "Boolean expression expected";
            }
            return "";
        }
    }
    
    private String ParseAssignment(String var, String exp)
    {
        String result = "";
        int errCount = this.area.editor.parser.getErrors().size();
        Element varNode = (Element)this.area.editor.parser.ParseVariable(var);
        int new_errCount = this.area.editor.parser.getErrors().size();
        
        if (new_errCount > errCount)
        {
            for(int i = errCount; i < new_errCount; i++)
            {
                ErrorDescription errDesc = (ErrorDescription)this.area.editor.parser.getErrors().get(i);
                result += errDesc.message + "\n";
            }
            return result;
        }
        else
        {
            Element expNode = (Element)this.area.editor.parser.ParseExp(exp);
            new_errCount = this.area.editor.parser.getErrors().size();
            
            if (new_errCount > errCount)
            {
                for(int i = errCount; i < new_errCount; i++)
                {
                    ErrorDescription errDesc = (ErrorDescription)this.area.editor.parser.getErrors().get(i);
                    result += errDesc.message + "\n";
                }
                return result;
            }
            else
            {
                if (!this.area.editor.parser.CheckVarAssigment(varNode, expNode))
                {
                    return "Data type mismatch.";
                }
            }
            
            return "";
        }
    }
    
    private String ParseReturnValue(String exp)
    {
        String result = "";
        int errCount = this.area.editor.parser.getErrors().size();
        Element expNode = this.area.editor.parser.ParseExp(exp);
        int new_errCount = this.area.editor.parser.getErrors().size();
        
        if (new_errCount > errCount)
        {
            for(int i = errCount; i < new_errCount; i++)
            {
                ErrorDescription errDesc = (ErrorDescription)this.area.editor.parser.getErrors().get(i);
                result += errDesc.message + "\n";
            }
            return result;
        }
        else
        {
            if (!this.area.editor.parser.CheckReturnCommand(expNode))
            {
                return "Expression data type does not match return type of the function.";
            }
            return "";
        }
    }
    
    private String ParseSwitchValue(String exp)
    {
        String result = "";
        int errCount = this.area.editor.parser.getErrors().size();
        Element expNode = this.area.editor.parser.ParseExp(exp);
        int new_errCount = this.area.editor.parser.getErrors().size();
        
        if (new_errCount > errCount)
        {
            for(int i = errCount; i < new_errCount; i++)
            {
                ErrorDescription errDesc = (ErrorDescription)this.area.editor.parser.getErrors().get(i);
                result += errDesc.message + "\n";
            }
            return result;
        }
        else
        {   
            return "";
        }
    }
    
    private String ParseCaseValue(String exp)
    {
        String result = "";
        int errCount = this.area.editor.parser.getErrors().size();
        Element expNode = this.area.editor.parser.ParseExp(exp);
        int new_errCount = this.area.editor.parser.getErrors().size();
        
        if (new_errCount > errCount)
        {
            for(int i = errCount; i < new_errCount; i++)
            {
                ErrorDescription errDesc = (ErrorDescription)this.area.editor.parser.getErrors().get(i);
                result += errDesc.message + "\n";
            }
            return result;
        }
        else
        {
            CommTreeNode node = (CommTreeNode)this.getSelectionPath().getLastPathComponent();
            
            if (node.type != 10)
            {
                node = (CommTreeNode)node.getParent();
            }
            
            if (node.type == 10)
            {
                SwitchTreeNode switchNode = (SwitchTreeNode)node;
                
                Element expNode1 = this.area.editor.parser.ParseExp(switchNode.value);
                
                if (expNode1!= null)
                {
                   return this.area.editor.parser.CheckCase(expNode1, expNode);
                }
                
            }
                        
            return "";
        }
    }
    
    private String ParsePrintValue(String exp)
    {
        String result = "";
        int errCount = this.area.editor.parser.getErrors().size();
        Element expNode = this.area.editor.parser.ParseExp(exp);
        int new_errCount = this.area.editor.parser.getErrors().size();
        
        if (new_errCount > errCount)
        {
            for(int i = errCount; i < new_errCount; i++)
            {
                ErrorDescription errDesc = (ErrorDescription)this.area.editor.parser.getErrors().get(i);
                result += errDesc.message + "\n";
            }
            return result;
        }
        else
        {
            return "";
        }
    }
    
    private String ParseForComm(String var, String from, String to)
    {
        String result = this.area.editor.parser.CheckForVar(var);
        if (result != null && !result.equals(""))
        {
            return result;
        }
        
        int errCount = this.area.editor.parser.getErrors().size();
        Element expNode = this.area.editor.parser.ParseExp(from);
        int new_errCount = this.area.editor.parser.getErrors().size();
        
        if (new_errCount > errCount)
        {
            for(int i = errCount; i < new_errCount; i++)
            {
                ErrorDescription errDesc = (ErrorDescription)this.area.editor.parser.getErrors().get(i);
                result += errDesc.message + "\n";
            }
            return result;
        }
        
        result = this.area.editor.parser.CheckForBoundry(expNode);
        if (result != null && !result.equals(""))
        {
            return result;
        }
        
        expNode = this.area.editor.parser.ParseExp(to);
        new_errCount = this.area.editor.parser.getErrors().size();
        
        if (new_errCount > errCount)
        {
            for(int i = errCount; i < new_errCount; i++)
            {
                ErrorDescription errDesc = (ErrorDescription)this.area.editor.parser.getErrors().get(i);
                result += errDesc.message + "\n";
            }
            return result;
        }
        
        result = this.area.editor.parser.CheckForBoundry(expNode);
        if (result != null && !result.equals(""))
        {
            return result;
        }
        
        return "";
    }
    
    private String ParseSelect(String var, String func, String exp)
    {
        String result = "";
        int errCount = this.area.editor.parser.getErrors().size();
        Element varNode = (Element)this.area.editor.parser.ParseVariable(var);
        int new_errCount = this.area.editor.parser.getErrors().size();
        
        if (new_errCount > errCount)
        {
            for(int i = errCount; i < new_errCount; i++)
            {
                ErrorDescription errDesc = (ErrorDescription)this.area.editor.parser.getErrors().get(i);
                result += errDesc.message + "\n";
            }
            return result;
        }
        else
        {
            Element expNode = (Element)this.area.editor.parser.ParseExp(exp);
            new_errCount = this.area.editor.parser.getErrors().size();
            
            if (new_errCount > errCount)
            {
                for(int i = errCount; i < new_errCount; i++)
                {
                    ErrorDescription errDesc = (ErrorDescription)this.area.editor.parser.getErrors().get(i);
                    result += errDesc.message + "\n";
                }
                return result;
            }
            else
            {
                return this.area.editor.parser.CheckSelectStatement(func, varNode, expNode);
            }
        }
    }
    
    private String ParseFetch(String var, String var_list)
    {
        String result = this.area.editor.parser.CheckIsIteratorName(var);
        if (result != null && !result.equals(""))
        {
            return result;
        }
        int errCount = this.area.editor.parser.getErrors().size();
        this.area.editor.parser.ParseVariableList(var_list);
        int new_errCount = this.area.editor.parser.getErrors().size();
        
        if (new_errCount > errCount)
        {
            for(int i = errCount; i < new_errCount; i++)
            {
                ErrorDescription errDesc = (ErrorDescription)this.area.editor.parser.getErrors().get(i);
                result += errDesc.message + "\n";
            }
            return result;
        }
        return "";
    }
    
    private String ParseSignal(String list)
    {
        int errCount = this.area.editor.parser.getErrors().size();
        this.area.editor.parser.ParseSignalList(list);
        int new_errCount = this.area.editor.parser.getErrors().size();
        String result = "";
        
        if (new_errCount > errCount)
        {
            for(int i = errCount; i < new_errCount; i++)
            {
                ErrorDescription errDesc = (ErrorDescription)this.area.editor.parser.getErrors().get(i);
                result += errDesc.message + "\n";
            }
            return result;
        }
        return "";
    }
    
    public void RemoveFromList(ArrayList list, int pos)
    {
        while(pos + 1 < list.size())
        {
            list.remove(pos + 1);
        }
    }
    
    public void TreeChanged(TreeModelEvent e)
    {
        this.cutcopyNode = null;
        
        this.RemoveFromList(this.undoStack, this.undoPos);
        this.RemoveFromList(this.undoStackText, this.undoPos);
        this.RemoveFromList(this.undoStackCarrPos, this.undoPos);
        
        this.undoPos = this.undoPos + 1;
        
        if (this.undoStack.size() == undoCount)
        {
            this.undoStack.remove(0);
        }
        DefaultTreeModel model = (DefaultTreeModel)this.getModel();
        
        try 
        {
            CommTreeNode root = ((CommTreeNode)model.getRoot()).clone();
            DefaultTreeModel oldmodel = new DefaultTreeModel(root);
            
            this.undoStack.add(oldmodel);
            this.undoStackText.add(this.area.getText());
            this.undoStackCarrPos.add(new Integer(this.area.getCaretPosition()));                
        }
        catch(Exception ex)
        {}
    }

    public void Undo()
    {
        this.undoPos = this.undoPos - 1;
        DefaultTreeModel oldModel = (DefaultTreeModel)this.undoStack.get(this.undoPos);
        CommTreeNode root = ((CommTreeNode)oldModel.getRoot()).clone();
        oldModel = new DefaultTreeModel(root);
        this.setModel(oldModel);
        this.area.setText((String)this.undoStackText.get(this.undoPos));
        this.area.setCaretPosition(((Integer)this.undoStackCarrPos.get(this.undoPos)).intValue());        
        
        CommTreeNode rootNode = (CommTreeNode)oldModel.getRoot();

        this.argsNode = (CommTreeNode)rootNode.getChildAt(0);
        this.varsNode = (CommTreeNode)rootNode.getChildAt(1);
        this.bodyNode = (CommTreeNode)rootNode.getChildAt(2);
    }
    
    public void Redo()
    {
        this.undoPos = this.undoPos + 1;
        DefaultTreeModel oldModel = (DefaultTreeModel)this.undoStack.get(this.undoPos);
        CommTreeNode root = ((CommTreeNode)oldModel.getRoot()).clone();
        oldModel = new DefaultTreeModel(root);
        this.setModel(oldModel);
        this.area.setText((String)this.undoStackText.get(this.undoPos));
        this.area.setCaretPosition(((Integer)this.undoStackCarrPos.get(this.undoPos)).intValue());        
        
        CommTreeNode rootNode = (CommTreeNode)oldModel.getRoot();

        this.argsNode = (CommTreeNode)rootNode.getChildAt(0);
        this.varsNode = (CommTreeNode)rootNode.getChildAt(1);
        this.bodyNode = (CommTreeNode)rootNode.getChildAt(2);
    }
    
    public void Cut()
    {
        CommTreeNode node = (CommTreeNode)this.getSelectionPath().getLastPathComponent();
        ((DefaultTreeModel)this.getModel()).removeNodeFromParent(node);
        this.cutcopyText = this.area.getText().substring(node.begin, node.end+1);
        this.area.getBuffer().remove(node.begin, node.end - node.begin +1);
        this.UpdateStatPosition(this.bodyNode, node.begin, -(node.end - node.begin + 1));
        TreeChanged(null);
        this.cutcopyNode = node.clone();
        this.UpdatePosition(this.cutcopyNode, this.cutcopyNode.begin, (-1)*this.cutcopyNode.begin);
    }
    
    public void Copy()
    {
        CommTreeNode node = (CommTreeNode)this.getSelectionPath().getLastPathComponent();
        this.cutcopyNode = node.clone();
        this.cutcopyText = this.area.getText().substring(node.begin, node.end+1);
        this.UpdatePosition(this.cutcopyNode, this.cutcopyNode.begin, (-1)*this.cutcopyNode.begin);
    }
    
    public void Paste()
    {
        CommTreeNode node = (CommTreeNode)this.getSelectionPath().getLastPathComponent();        
        String nodeName = node.getUserObject().toString();
        CommTreeNode newNode = this.cutcopyNode.clone();
        String newText = this.cutcopyText;
        
        if (nodeName.equals("Body") || nodeName.equals("Default") || nodeName.equals("Else"))
        {
            int pos = this.FindInsertPos(node);
            String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), ((CommTreeNode)node.getParent()).begin-1) + "\t";
            
            this.area.getBuffer().insert(pos, blankStr + newText);
            int offset = (blankStr + newText).length();            
            this.UpdateStatPosition(this.bodyNode, pos + 1, offset);
            
            this.UpdatePosition(newNode, 0, pos + blankStr.length());
            
            ((DefaultTreeModel)this.getModel()).insertNodeInto(newNode, node, 0);
        }
        else
        {
            int pos = node.begin;
            String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), pos-1);
            
            this.area.getBuffer().insert(pos, newText + blankStr);
            int offset = (blankStr + newText).length();
            
            this.UpdateStatPosition(this.bodyNode, pos, offset);
            this.UpdatePosition(newNode, 0, pos);
            
            int index  = node.getParent().getIndex(node);
            ((DefaultTreeModel)this.getModel()).insertNodeInto(newNode, (CommTreeNode)node.getParent(), index);
        }
        
        CommTreeNode tempCutCopyNode = this.cutcopyNode;
    
        this.TreeChanged(null);
        this.cutcopyNode = tempCutCopyNode;
    }
    
    public void InitRootNodes(String funcName)
    {
        root = new CommTreeNode(funcName);
        argsNode = new CommTreeNode("Arguments");
        varsNode = new CommTreeNode("Variables");
        bodyNode = new CommTreeNode("Body");
        bodyNode.type = -2;
        root.add(argsNode);
        root.add(varsNode);
        root.add(bodyNode);
        DefaultTreeModel model = new DefaultTreeModel(root);
        this.setModel(model);
    }
    
    public void InitTree(Document doc)
    {
        try 
        {
            Element rootXml = doc.getDocumentElement();
            Element funcNode = (Element)rootXml.getFirstChild();
            
            if (funcNode != null)
            {
                this.InitRootNodes(funcNode.getNodeName());
                
                this.LoadArgs(funcNode);
                this.LoadVars(funcNode);
                
                NodeList list = funcNode.getElementsByTagName("BODY");
                
                if(list.getLength() > 0)
                {
                    Element bodyNode = (Element)list.item(0);
                    
                    if (bodyNode.getFirstChild() != null)
                    {
                        this.LoadStatements(this.bodyNode, (Element)bodyNode.getFirstChild());
                    }
                }
            }
            this.undoPos = -1;
            this.undoStack.clear();
            this.undoStackText.clear();
            this.undoStackCarrPos.clear();
            TreeChanged(null);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    public void doMouseClicked(MouseEvent me) 
    {
        try 
        {
            if (!this.editable)
            {
                return;
            }
            /*if (this.getPathForLocation(me.getX(), me.getY()) == null)
            {
                return;
            }*/
            if((me.getModifiers() & InputEvent.BUTTON3_MASK)== InputEvent.BUTTON3_MASK)
            {
                if (this.getPathForLocation(me.getX(), me.getY()) != null)
                {
                    this.setSelectionPath(this.getPathForLocation(me.getX(), me.getY()));
                }
                
                if (this.getSelectionPath() != null && this.getSelectionPath().getParentPath() != null)
                {
                    String nodePStr = this.getSelectionPath().getParentPath().getLastPathComponent().toString();
                    String nodeStr = this.getSelectionPath().getLastPathComponent().toString();
                    CommTreeNode node = (CommTreeNode)this.getSelectionPath().getLastPathComponent();
                    
                    JPopupMenu popupMenu = new JPopupMenu();
                    
                    if (nodePStr.equals("Arguments") || nodePStr.equals("Variables"))
                    {
                        this.ShowArgVarMenu(node, me);
                        return;
                    }
                    
                    //if (nodePStr.equals("Body") || nodePStr.equals("Else") || nodePStr.equals("Default"))
                    {
                        if (node.type == 2)
                        {
                            ShowAssignMenu(node, me);
                            return;
                        }
                        
                        if (node.type == 3)
                        {
                            ShowIfMenu(node, me);
                            return;
                        }
                        
                        if (node.type == 4)
                        {
                            ShowWhileMenu(node, me);
                            return;
                        }
                        
                        if (node.type == 5)
                        {
                            ShowRepeatMenu(node, me);
                            return;
                        }
                        
                        if (node.type == 6)
                        {
                            ShowForMenu(node, me);
                            return;
                        }
                        
                        if (node.type == 7)
                        {
                            ShowBreakMenu(node, me);
                            return;
                        }
                        
                        if (node.type == 8)
                        {
                            ShowReturnMenu(node, me);
                            return;
                        }
                        
                        if (node.type == 9)
                        {
                            ShowSelectMenu(node, me);
                            return;
                        }
                        
                        if (node.type == 10)
                        {
                            ShowSwitchMenu(node, me);
                            return;
                        }
                        
                        if (node.type == 11)
                        {
                            ShowCaseMenu(node, me);
                            return;
                        }
                        
                        if (node.type == 12)
                        {
                            ShowPrintMenu(node, me);
                            return;
                        }
                        
                        if (node.type == 13)
                        {
                            ShowFetchMenu(node, me);
                            return;
                        }
                        
                        if (node.type == 14)
                        {
                            ShowSignalMenu(node, me);
                            return;
                        }
                        //return;
                    }
                    
                    if (nodeStr.equals("Arguments") || nodeStr.equals("Variables"))
                    {
                        this.CreateUndoRedoMenu(popupMenu);
                        
                        JMenuItem newItem = new JMenuItem("Add");
                        newItem.addActionListener(new ActionListener()
                        {
                            public void actionPerformed(ActionEvent e) 
                            {
                                insertActionPerformed(e, true);
                            }
                        });
                        
                        popupMenu.add(newItem);
                        popupMenu.show(this, me.getX()+10, me.getY());
                        return;
                    }
                    
                    if (nodeStr.equals("Else") || nodeStr.equals("Default"))
                    {
                        this.CreateCutCopyPasteMenu(popupMenu);
                        this.CreateUndoRedoMenu(popupMenu);
                        
                        JMenu addCommandItem = new JMenu("Add command");
                        this.CreateAddCommnadMenu(addCommandItem, true, false);
                        
                        popupMenu.add(addCommandItem);
                        popupMenu.addSeparator();
                        
                        JMenuItem deleteItem = new JMenuItem("Delete");
                        
                        deleteItem.addActionListener(new ActionListener()
                        {

                            public void actionPerformed(ActionEvent e) 
                            {
                                deleteActionPerformed(e);
                            }
                        });
                        deleteItem.setIcon(deleteIcon);
                        popupMenu.add(deleteItem);
                        popupMenu.addSeparator();
                        JMenuItem selectItem = new JMenuItem("Select In Editor");
                        selectItem.addActionListener(new ActionListener()
                        {

                            public void actionPerformed(ActionEvent e) 
                            {
                                selectActionPerformed(e);
                            }
                        });
                        
                        popupMenu.add(selectItem);
                        popupMenu.show(this, me.getX()+10, me.getY());
                        return;
                    }
                    
                    if (nodeStr.equals("Body"))
                    {
                        this.CreateCutCopyPasteMenu(popupMenu);
                        
                        JMenu addCommandItem = new JMenu("Add command");
                        this.CreateUndoRedoMenu(popupMenu);
                        this.CreateAddCommnadMenu(addCommandItem, true, false);
                        popupMenu.add(addCommandItem);
                        
                        popupMenu.show(this, me.getX()+10, me.getY());
                        return;
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void ShowSwitchMenu(CommTreeNode node, MouseEvent me)
    {
        JPopupMenu popupMenu = new JPopupMenu();
        
        this.CreateCutCopyPasteMenu(popupMenu);
        this.CreateUndoRedoMenu(popupMenu);
        this.ShowBeforeAfterInsert(node, popupMenu);
        
        JMenuItem moveUpItem = new JMenuItem("Move Up");
        moveUpItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveUpActionPerformed(e);
            }
        });
        
        if (node.getPreviousSibling() == null)
        {
            moveUpItem.setEnabled(false);
        }
        popupMenu.add(moveUpItem);
        
        JMenuItem moveDownItem = new JMenuItem("Move Down");
        moveDownItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveDownActionPerformed(e);
            }
        });
        
        if (node.getNextSibling() == null)
        {
            moveDownItem.setEnabled(false);
        }
        moveDownItem.setIcon(upIcon);
        moveUpItem.setIcon(downIcon);
        popupMenu.add(moveDownItem);        
        popupMenu.addSeparator();
        
        JMenuItem addCaseItem = new JMenuItem("Add Case");
        addCaseItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                addCaseActionPerformed(e);
            }
        });
        popupMenu.add(addCaseItem);
        popupMenu.addSeparator();
        
        JMenuItem editItem = new JMenuItem("Edit");
        editItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                editActionPerformed(e);
            }
        });
        popupMenu.add(editItem);
        
        JMenuItem addElseItem = new JMenuItem("Add default");
        addElseItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                addDefaultActionPerformed(e);
            }
        });
        
        SwitchTreeNode switchNode = (SwitchTreeNode)node;
        
        if (switchNode.start_default != -1)
        {
            addElseItem.setEnabled(false);
        }
        popupMenu.add(addElseItem);
        JMenuItem deleteItem = new JMenuItem("Delete");
        
        deleteItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                deleteActionPerformed(e);
            }
        });
        
        deleteItem.setIcon(deleteIcon);
        popupMenu.add(deleteItem);
        popupMenu.addSeparator();
        JMenuItem selectItem = new JMenuItem("Select In Editor");
        selectItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                selectActionPerformed(e);
            }
        });
        
        popupMenu.add(selectItem);
        popupMenu.show(this, me.getX()+10, me.getY());
    }
    
    public void ShowCaseMenu(CommTreeNode node, MouseEvent me)
    {
        JPopupMenu popupMenu = new JPopupMenu();
        
        JMenuItem moveUpItem = new JMenuItem("Move Up");
        moveUpItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveUpActionPerformed(e);
            }
        });
        
        if (node.getPreviousSibling() == null)
        {
            moveUpItem.setEnabled(false);
        }
        popupMenu.add(moveUpItem);
        
        JMenuItem moveDownItem = new JMenuItem("Move Down");
        moveDownItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveDownActionPerformed(e);
            }
        });
        
        if (node.getNextSibling() == null)
        {
            moveDownItem.setEnabled(false);
        }
        popupMenu.add(moveDownItem);        
        moveDownItem.setIcon(upIcon);
        moveUpItem.setIcon(downIcon);
        popupMenu.addSeparator();
        
        JMenuItem editItem = new JMenuItem("Edit");
        editItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                editActionPerformed(e);
            }
        });
        popupMenu.add(editItem);
        
        JMenuItem deleteItem = new JMenuItem("Delete");
        
        deleteItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                deleteActionPerformed(e);
            }
        });
        
        deleteItem.setIcon(deleteIcon);
        popupMenu.add(deleteItem);
        popupMenu.addSeparator();
        JMenuItem selectItem = new JMenuItem("Select In Editor");
        selectItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                selectActionPerformed(e);
            }
        });
        
        popupMenu.add(selectItem);
        popupMenu.show(this, me.getX()+10, me.getY());
    }
    
    public void ShowIfMenu(CommTreeNode node, MouseEvent me)
    {
        JPopupMenu popupMenu = new JPopupMenu();
        
        this.CreateCutCopyPasteMenu(popupMenu);
        this.CreateUndoRedoMenu(popupMenu);
        this.ShowBeforeAfterInsert(node, popupMenu);
        
        JMenuItem moveUpItem = new JMenuItem("Move Up");
        moveUpItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveUpActionPerformed(e);
            }
        });
        
        if (node.getPreviousSibling() == null)
        {
            moveUpItem.setEnabled(false);
        }
        popupMenu.add(moveUpItem);
        
        JMenuItem moveDownItem = new JMenuItem("Move Down");
        moveDownItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveDownActionPerformed(e);
            }
        });
        
        if (node.getNextSibling() == null)
        {
            moveDownItem.setEnabled(false);
        }
        popupMenu.add(moveDownItem);        
        moveDownItem.setIcon(upIcon);
        moveUpItem.setIcon(downIcon);
        popupMenu.addSeparator();
        
        JMenuItem editItem = new JMenuItem("Edit condition");
        editItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                editActionPerformed(e);
            }
        });
        popupMenu.add(editItem);
        
        JMenuItem addElseItem = new JMenuItem("Add else");
        addElseItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                addElseActionPerformed(e);
            }
        });
        
        IfTreeNode ifNode = (IfTreeNode)node;
        
        if (ifNode.start_else != -1)
        {
            addElseItem.setEnabled(false);
        }
        popupMenu.add(addElseItem);
        JMenuItem deleteItem = new JMenuItem("Delete");
        
        deleteItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                deleteActionPerformed(e);
            }
        });
        
        deleteItem.setIcon(deleteIcon);
        popupMenu.add(deleteItem);
        popupMenu.addSeparator();
        JMenuItem selectItem = new JMenuItem("Select In Editor");
        selectItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                selectActionPerformed(e);
            }
        });
        
        popupMenu.add(selectItem);
        popupMenu.show(this, me.getX()+10, me.getY());
    }
    
    public void ShowWhileMenu(CommTreeNode node, MouseEvent me)
    {
        JPopupMenu popupMenu = new JPopupMenu();
        
        this.CreateCutCopyPasteMenu(popupMenu);
        this.CreateUndoRedoMenu(popupMenu);
        this.ShowBeforeAfterInsert(node, popupMenu);
        
        JMenuItem moveUpItem = new JMenuItem("Move Up");
        moveUpItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveUpActionPerformed(e);
            }
        });
        
        if (node.getPreviousSibling() == null)
        {
            moveUpItem.setEnabled(false);
        }
        popupMenu.add(moveUpItem);
        
        JMenuItem moveDownItem = new JMenuItem("Move Down");
        moveDownItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveDownActionPerformed(e);
            }
        });
        
        if (node.getNextSibling() == null)
        {
            moveDownItem.setEnabled(false);
        }
        popupMenu.add(moveDownItem);        
        moveDownItem.setIcon(upIcon);
        moveUpItem.setIcon(downIcon);
        popupMenu.addSeparator();
        
        JMenuItem editItem = new JMenuItem("Edit condition");
        editItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                editActionPerformed(e);
            }
        });
        popupMenu.add(editItem);
        
        JMenuItem deleteItem = new JMenuItem("Delete");
        
        deleteItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                deleteActionPerformed(e);
            }
        });
        
        deleteItem.setIcon(deleteIcon);
        popupMenu.add(deleteItem);
        popupMenu.addSeparator();
        JMenuItem selectItem = new JMenuItem("Select In Editor");
        selectItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                selectActionPerformed(e);
            }
        });
        
        popupMenu.add(selectItem);
        popupMenu.show(this, me.getX()+10, me.getY());
    }
    
    public void ShowBreakMenu(CommTreeNode node, MouseEvent me)
    {
        JPopupMenu popupMenu = new JPopupMenu();
        
        this.CreateCutCopyPasteMenu(popupMenu);
        this.CreateUndoRedoMenu(popupMenu);
        this.ShowBeforeAfterInsert(node, popupMenu);
        
        JMenuItem moveUpItem = new JMenuItem("Move Up");
        moveUpItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveUpActionPerformed(e);
            }
        });
        
        if (node.getPreviousSibling() == null)
        {
            moveUpItem.setEnabled(false);
        }
        popupMenu.add(moveUpItem);
        
        JMenuItem moveDownItem = new JMenuItem("Move Down");
        moveDownItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveDownActionPerformed(e);
            }
        });
        
        if (node.getNextSibling() == null)
        {
            moveDownItem.setEnabled(false);
        }
        popupMenu.add(moveDownItem);        
        moveDownItem.setIcon(upIcon);
        moveUpItem.setIcon(downIcon);
        popupMenu.addSeparator();
        
        JMenuItem deleteItem = new JMenuItem("Delete");
        
        deleteItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                deleteActionPerformed(e);
            }
        });
        
        deleteItem.setIcon(deleteIcon);
        popupMenu.add(deleteItem);
        popupMenu.addSeparator();
        JMenuItem selectItem = new JMenuItem("Select In Editor");
        selectItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                selectActionPerformed(e);
            }
        });
        
        popupMenu.add(selectItem);
        popupMenu.show(this, me.getX()+10, me.getY());
    }
    
    public void ShowRepeatMenu(CommTreeNode node, MouseEvent me)
    {
        JPopupMenu popupMenu = new JPopupMenu();
        
        this.CreateCutCopyPasteMenu(popupMenu);
        this.CreateUndoRedoMenu(popupMenu);
        this.ShowBeforeAfterInsert(node, popupMenu);
        
        JMenuItem moveUpItem = new JMenuItem("Move Up");
        moveUpItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveUpActionPerformed(e);
            }
        });
        
        if (node.getPreviousSibling() == null)
        {
            moveUpItem.setEnabled(false);
        }
        popupMenu.add(moveUpItem);
        
        JMenuItem moveDownItem = new JMenuItem("Move Down");
        moveDownItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveDownActionPerformed(e);
            }
        });
        
        if (node.getNextSibling() == null)
        {
            moveDownItem.setEnabled(false);
        }
        popupMenu.add(moveDownItem);        
        moveDownItem.setIcon(upIcon);
        moveUpItem.setIcon(downIcon);
        popupMenu.addSeparator();
        
        JMenuItem editItem = new JMenuItem("Edit condition");
        editItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                editActionPerformed(e);
            }
        });
        popupMenu.add(editItem);
        
        JMenuItem deleteItem = new JMenuItem("Delete");
        
        deleteItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                deleteActionPerformed(e);
            }
        });
        
        deleteItem.setIcon(deleteIcon);
        
        popupMenu.add(deleteItem);
        popupMenu.addSeparator();
        JMenuItem selectItem = new JMenuItem("Select In Editor");
        selectItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                selectActionPerformed(e);
            }
        });
        
        popupMenu.add(selectItem);
        popupMenu.show(this, me.getX()+10, me.getY());
    }
    
    public void ShowReturnMenu(CommTreeNode node, MouseEvent me)
    {
        JPopupMenu popupMenu = new JPopupMenu();
        
        this.CreateCutCopyPasteMenu(popupMenu);
        this.CreateUndoRedoMenu(popupMenu);
        this.ShowBeforeAfterInsert(node, popupMenu);
        
        JMenuItem moveUpItem = new JMenuItem("Move Up");
        moveUpItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveUpActionPerformed(e);
            }
        });
        
        if (node.getPreviousSibling() == null)
        {
            moveUpItem.setEnabled(false);
        }
        popupMenu.add(moveUpItem);
        
        JMenuItem moveDownItem = new JMenuItem("Move Down");
        moveDownItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveDownActionPerformed(e);
            }
        });
        
        if (node.getNextSibling() == null)
        {
            moveDownItem.setEnabled(false);
        }
        popupMenu.add(moveDownItem);        
        moveDownItem.setIcon(upIcon);
        moveUpItem.setIcon(downIcon);
        popupMenu.addSeparator();
        
        JMenuItem editItem = new JMenuItem("Edit");
        editItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                editActionPerformed(e);
            }
        });
        popupMenu.add(editItem);
        
        JMenuItem deleteItem = new JMenuItem("Delete");
        
        deleteItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                deleteActionPerformed(e);
            }
        });
        
        deleteItem.setIcon(deleteIcon);
        popupMenu.add(deleteItem);
        popupMenu.addSeparator();
        JMenuItem selectItem = new JMenuItem("Select In Editor");
        selectItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                selectActionPerformed(e);
            }
        });
        
        popupMenu.add(selectItem);
        popupMenu.show(this, me.getX()+10, me.getY());
    }
    
    public void ShowForMenu(CommTreeNode node, MouseEvent me)
    {
        JPopupMenu popupMenu = new JPopupMenu();
        
        this.CreateCutCopyPasteMenu(popupMenu);
        this.CreateUndoRedoMenu(popupMenu);
        this.ShowBeforeAfterInsert(node, popupMenu);
        
        JMenuItem moveUpItem = new JMenuItem("Move Up");
        moveUpItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveUpActionPerformed(e);
            }
        });
        
        if (node.getPreviousSibling() == null)
        {
            moveUpItem.setEnabled(false);
        }
        popupMenu.add(moveUpItem);
        
        JMenuItem moveDownItem = new JMenuItem("Move Down");
        moveDownItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveDownActionPerformed(e);
            }
        });
        
        if (node.getNextSibling() == null)
        {
            moveDownItem.setEnabled(false);
        }
        popupMenu.add(moveDownItem);        
        moveDownItem.setIcon(upIcon);
        moveUpItem.setIcon(downIcon);
        popupMenu.addSeparator();
        
        JMenuItem editItem = new JMenuItem("Edit");
        editItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                editActionPerformed(e);
            }
        });
        popupMenu.add(editItem);
        
        JMenuItem deleteItem = new JMenuItem("Delete");
        
        deleteItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                deleteActionPerformed(e);
            }
        });
        
        deleteItem.setIcon(deleteIcon);
        popupMenu.add(deleteItem);
        popupMenu.addSeparator();
        JMenuItem selectItem = new JMenuItem("Select In Editor");
        selectItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                selectActionPerformed(e);
            }
        });
        
        popupMenu.add(selectItem);
        popupMenu.show(this, me.getX()+10, me.getY());
    }
    
    public void ShowBeforeAfterInsert(CommTreeNode node, JPopupMenu popupMenu)
    {
        JMenu insertBeforeItem = new JMenu("Insert Before");
        popupMenu.add(insertBeforeItem);
        
        JMenu insertAfterItem = new JMenu("Insert After");  
        popupMenu.add(insertAfterItem);        
        popupMenu.addSeparator();
        
        CreateAddCommnadMenu(insertBeforeItem, false, true);
        CreateAddCommnadMenu(insertAfterItem, false, false);
    }
    
    public void ShowAssignMenu(CommTreeNode node, MouseEvent me)
    {
        JPopupMenu popupMenu = new JPopupMenu();        
        
        this.CreateCutCopyPasteMenu(popupMenu);
        this.CreateUndoRedoMenu(popupMenu);
        this.ShowBeforeAfterInsert(node, popupMenu);
        
        JMenuItem moveUpItem = new JMenuItem("Move Up");
        moveUpItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveUpActionPerformed(e);
            }
        });
        
        if (node.getPreviousSibling() == null)
        {
            moveUpItem.setEnabled(false);
        }
        popupMenu.add(moveUpItem);
        
        JMenuItem moveDownItem = new JMenuItem("Move Down");
        moveDownItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveDownActionPerformed(e);
            }
        });
        
        if (node.getNextSibling() == null)
        {
            moveDownItem.setEnabled(false);
        }
        popupMenu.add(moveDownItem);        
        moveDownItem.setIcon(upIcon);
        moveUpItem.setIcon(downIcon);
        popupMenu.addSeparator();
        
        JMenuItem editItem = new JMenuItem("Edit");
        editItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                editActionPerformed(e);
            }
        });
        
        popupMenu.add(editItem);
        JMenuItem deleteItem = new JMenuItem("Delete");
        
        deleteItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                deleteActionPerformed(e);
            }
        });
        
        deleteItem.setIcon(deleteIcon);
        popupMenu.add(deleteItem);
        popupMenu.addSeparator();
        JMenuItem selectItem = new JMenuItem("Select In Editor");
        selectItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                selectActionPerformed(e);
            }
        });
        
        popupMenu.add(selectItem);
        popupMenu.show(this, me.getX()+10, me.getY());
    }
    
    public void ShowSelectMenu(CommTreeNode node, MouseEvent me)
    {
        JPopupMenu popupMenu = new JPopupMenu();       
        
        this.CreateCutCopyPasteMenu(popupMenu);
        this.CreateUndoRedoMenu(popupMenu);
        this.ShowBeforeAfterInsert(node, popupMenu);
        
        JMenuItem moveUpItem = new JMenuItem("Move Up");
        moveUpItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveUpActionPerformed(e);
            }
        });
        
        if (node.getPreviousSibling() == null)
        {
            moveUpItem.setEnabled(false);
        }
        popupMenu.add(moveUpItem);
        
        JMenuItem moveDownItem = new JMenuItem("Move Down");
        moveDownItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveDownActionPerformed(e);
            }
        });
        
        if (node.getNextSibling() == null)
        {
            moveDownItem.setEnabled(false);
        }
        popupMenu.add(moveDownItem);        
        moveDownItem.setIcon(upIcon);
        moveUpItem.setIcon(downIcon);
        popupMenu.addSeparator();
        
        JMenuItem editItem = new JMenuItem("Edit");
        editItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                editActionPerformed(e);
            }
        });
        popupMenu.add(editItem);
        
        JMenuItem deleteItem = new JMenuItem("Delete");
        
        deleteItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                deleteActionPerformed(e);
            }
        });
        
        deleteItem.setIcon(deleteIcon);
        popupMenu.add(deleteItem);
        popupMenu.addSeparator();
        JMenuItem selectItem = new JMenuItem("Select In Editor");
        selectItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                selectActionPerformed(e);
            }
        });
        
        popupMenu.add(selectItem);
        popupMenu.show(this, me.getX()+10, me.getY());
    }
    
    public void ShowFetchMenu(CommTreeNode node, MouseEvent me)
    {
        JPopupMenu popupMenu = new JPopupMenu();       
        
        this.CreateCutCopyPasteMenu(popupMenu);
        this.CreateUndoRedoMenu(popupMenu);
        this.ShowBeforeAfterInsert(node, popupMenu);
        
        JMenuItem moveUpItem = new JMenuItem("Move Up");
        moveUpItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveUpActionPerformed(e);
            }
        });
        
        if (node.getPreviousSibling() == null)
        {
            moveUpItem.setEnabled(false);
        }
        popupMenu.add(moveUpItem);
        
        JMenuItem moveDownItem = new JMenuItem("Move Down");
        moveDownItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveDownActionPerformed(e);
            }
        });
        
        if (node.getNextSibling() == null)
        {
            moveDownItem.setEnabled(false);
        }
        popupMenu.add(moveDownItem);        
        moveDownItem.setIcon(upIcon);
        moveUpItem.setIcon(downIcon);
        popupMenu.addSeparator();
        
        JMenuItem editItem = new JMenuItem("Edit");
        editItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                editActionPerformed(e);
            }
        });
        popupMenu.add(editItem);
        
        JMenuItem deleteItem = new JMenuItem("Delete");
        
        deleteItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                deleteActionPerformed(e);
            }
        });
        
        deleteItem.setIcon(deleteIcon);
        popupMenu.add(deleteItem);
        popupMenu.addSeparator();
        JMenuItem selectItem = new JMenuItem("Select In Editor");
        selectItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                selectActionPerformed(e);
            }
        });
        
        popupMenu.add(selectItem);
        popupMenu.show(this, me.getX()+10, me.getY());
    }
    
    public void ShowSignalMenu(CommTreeNode node, MouseEvent me)
    {
        JPopupMenu popupMenu = new JPopupMenu();       
        
        this.CreateCutCopyPasteMenu(popupMenu);
        this.CreateUndoRedoMenu(popupMenu);
        this.ShowBeforeAfterInsert(node, popupMenu);
        
        JMenuItem moveUpItem = new JMenuItem("Move Up");
        moveUpItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveUpActionPerformed(e);
            }
        });
        
        if (node.getPreviousSibling() == null)
        {
            moveUpItem.setEnabled(false);
        }
        popupMenu.add(moveUpItem);
        
        JMenuItem moveDownItem = new JMenuItem("Move Down");
        moveDownItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveDownActionPerformed(e);
            }
        });
        
        if (node.getNextSibling() == null)
        {
            moveDownItem.setEnabled(false);
        }
        popupMenu.add(moveDownItem);        
        moveDownItem.setIcon(upIcon);
        moveUpItem.setIcon(downIcon);
        popupMenu.addSeparator();
        
        JMenuItem editItem = new JMenuItem("Edit");
        editItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                editActionPerformed(e);
            }
        });
        popupMenu.add(editItem);
        
        JMenuItem deleteItem = new JMenuItem("Delete");
        
        deleteItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                deleteActionPerformed(e);
            }
        });
        
        deleteItem.setIcon(deleteIcon);
        popupMenu.add(deleteItem);
        popupMenu.addSeparator();
        JMenuItem selectItem = new JMenuItem("Select In Editor");
        selectItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                selectActionPerformed(e);
            }
        });
        
        popupMenu.add(selectItem);
        popupMenu.show(this, me.getX()+10, me.getY());
    }
    
    private void ShowArgVarMenu(CommTreeNode node, MouseEvent me)
    {
        JPopupMenu popupMenu = new JPopupMenu();
        this.CreateUndoRedoMenu(popupMenu);
        JMenuItem insertBeforeItem = new JMenuItem("Insert Before");
        insertBeforeItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                insertActionPerformed(e, true);
            }
        });
        
        
        popupMenu.add(insertBeforeItem);
        
        JMenuItem insertAfterItem = new JMenuItem("Insert After");
        insertAfterItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                insertActionPerformed(e, false);
            }
        });
        
        popupMenu.add(insertAfterItem);
        
        popupMenu.addSeparator();
        
        JMenuItem moveUpItem = new JMenuItem("Move Up");
        moveUpItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveUpActionPerformed(e);
            }
        });
        
        if (node.getPreviousSibling() == null)
        {
            moveUpItem.setEnabled(false);
        }
        popupMenu.add(moveUpItem);
        
        JMenuItem moveDownItem = new JMenuItem("Move Down");
        moveDownItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveDownActionPerformed(e);
            }
        });
        
        if (node.getNextSibling() == null)
        {
            moveDownItem.setEnabled(false);
        }
        popupMenu.add(moveDownItem);
        moveDownItem.setIcon(upIcon);
        moveUpItem.setIcon(downIcon);
        popupMenu.addSeparator();
        
        JMenuItem editItem = new JMenuItem("Edit");
        
        editItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                editActionPerformed(e);
            }
        });
        
        popupMenu.add(editItem);
        JMenuItem deleteItem = new JMenuItem("Delete");
        
        deleteItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                deleteActionPerformed(e);
            }
        });
        
        deleteItem.setIcon(deleteIcon);
        popupMenu.add(deleteItem);
        popupMenu.addSeparator();
        JMenuItem selectItem = new JMenuItem("Select In Editor");
        selectItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                selectActionPerformed(e);
            }
        });
        
        popupMenu.add(selectItem);
        popupMenu.show(this, me.getX()+10, me.getY());
    }
    
    public void ShowPrintMenu(CommTreeNode node, MouseEvent me)
    {
        JPopupMenu popupMenu = new JPopupMenu();
        
        this.CreateCutCopyPasteMenu(popupMenu);
        this.CreateUndoRedoMenu(popupMenu);
        this.ShowBeforeAfterInsert(node, popupMenu);
        
        JMenuItem moveUpItem = new JMenuItem("Move Up");
        moveUpItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveUpActionPerformed(e);
            }
        });
        
        if (node.getPreviousSibling() == null)
        {
            moveUpItem.setEnabled(false);
        }
        popupMenu.add(moveUpItem);
        
        JMenuItem moveDownItem = new JMenuItem("Move Down");
        moveDownItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                moveDownActionPerformed(e);
            }
        });
        
        if (node.getNextSibling() == null)
        {
            moveDownItem.setEnabled(false);
        }
        popupMenu.add(moveDownItem);        
        moveDownItem.setIcon(upIcon);
        moveUpItem.setIcon(downIcon);
        popupMenu.addSeparator();
        
        JMenuItem editItem = new JMenuItem("Edit");
        editItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                editActionPerformed(e);
            }
        });
        popupMenu.add(editItem);
        
        JMenuItem deleteItem = new JMenuItem("Delete");
        
        deleteItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                deleteActionPerformed(e);
            }
        });
        
        deleteItem.setIcon(deleteIcon);
        popupMenu.add(deleteItem);
        popupMenu.addSeparator();
        JMenuItem selectItem = new JMenuItem("Select In Editor");
        selectItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                selectActionPerformed(e);
            }
        });
        
        popupMenu.add(selectItem);
        popupMenu.show(this, me.getX()+10, me.getY());
    }
    
    public String translateArgType(String type, int dir)
    {
        int len = type.length();
        if (dir == 0)
        {
            if (len == 2)
            {
                return "In";
            }
            
            if (len == 3)
            {
                return "Out";
            }
            
            return "In/Out";
        }
        else
        {
            if (len == 2)
            {
                return "IN";
            }
            
            if (len == 3)
            {
                return "OUT";
            }
            
            return "INOUT";
        }
    }
    
    public void insertActionPerformed(ActionEvent e, boolean before) 
    {
        CommTreeNode node = (CommTreeNode)this.getSelectionPath().getLastPathComponent();
        
        String nodePStr = this.getSelectionPath().getParentPath().getLastPathComponent().toString();
        String nodeStr = this.getSelectionPath().getLastPathComponent().toString();
        
        if (nodePStr.equals("Arguments"))
        {
            this.InsertArgument(node, before, false);
            return;
        }
        
        if (nodeStr.equals("Arguments"))
        {
            this.InsertArgument(node, before, true);
            return;
        }

        if (nodePStr.equals("Variables"))
        {
            this.InsertVariable(node, before, false);
            return;
        }
        
        if (nodeStr.equals("Variables"))
        {
            this.InsertVariable(node, before, true);
            return;
        }
    }
    
    private void InsertArgument(CommTreeNode node,  boolean before, boolean isNew)
    {
        VarFunParamFrm frm = new VarFunParamFrm("", "", "Add parameter", this.area.frame, "", "In", this.area.conn, this.PR_id);
        TreePath path = this.getSelectionPath();
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           int oldLen = 0;//argNode.end - argNode.begin + 1;
           ArgDeclTreeNode argNode = new ArgDeclTreeNode();
           argNode.argName = frm.name;
           argNode.argDataType = frm.domain;
           argNode.defaultValue = frm.dafaultValue;
           argNode.argType = this.translateArgType(frm.iodefault, 1);
           argNode.setUserObject(argNode.argName + " " + argNode.argType + " " + argNode.argDataType);
           
           String newText = argNode.argName;
           
           if (!argNode.argType.equals("IN"))
           {
                newText += " " + argNode.argType;
           }
           newText += " " + argNode.argDataType;
           
           if (!argNode.defaultValue.equals(""))
           {
                newText += " := " + argNode.defaultValue;
           }
           
           //((DefaultTreeModel)this.getModel()).reload(argNode);
           if (isNew) 
           {
               int index  = 0;
               
               ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, this.argsNode, index);
               this.setSelectionPath(path);
               
               int prenodetemp = this.area.GetOpenBraPos() + 1;
               argNode.begin = -1;
               argNode.end = 0;
               
               int offset = newText.length()  + 2;
               
               if (this.argsNode.getChildCount() == 1)
               {
                  offset = offset - 2;
               }
               
               this.UpdateArgVarPosition(prenodetemp - 1, offset);
               this.UpdateStatPosition(this.bodyNode, prenodetemp - 1, offset);
               
               argNode.begin = prenodetemp;
               argNode.end = argNode.begin + newText.length() - 1;
               
               if (this.argsNode.getChildCount() == 1)
               {
                    this.area.getBuffer().insert(prenodetemp, newText );
               }
               else
               {
                    this.area.getBuffer().insert(prenodetemp, newText +  ", ");
               }
           }
           else
           {
                ArgDeclTreeNode prevArgNode = (ArgDeclTreeNode)node;
               
                if (before)
                {
                    int index  = this.argsNode.getIndex(prevArgNode);
                    
                    ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, this.argsNode, index);
                    this.setSelectionPath(path);
                    
                    int prenodetemp = prevArgNode.begin;
                    argNode.begin = -1;
                    argNode.end = 0;
                    
                    int offset = newText.length()  + 2;
                    this.UpdateArgVarPosition(prevArgNode.begin-1, offset);
                    this.UpdateStatPosition(this.bodyNode, prevArgNode.begin, offset);
                    
                    argNode.begin = prenodetemp;
                    argNode.end = argNode.begin + newText.length() - 1;
                    this.area.getBuffer().insert(argNode.begin, newText + ", ");
                }
                else
                {
                    int index  = this.argsNode.getIndex(prevArgNode);
                    
                    ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, this.argsNode, index+1);
                    this.setSelectionPath(path);
                    
                    int prenodetemp = prevArgNode.begin;
                    argNode.begin = -1;
                    argNode.end = 0;
                    
                    int offset = newText.length()  + 2;
                    this.UpdateArgVarPosition(prevArgNode.end+1, offset);
                    this.UpdateStatPosition(this.bodyNode, prevArgNode.end, offset);
                    
                    argNode.begin = prevArgNode.end+3;
                    argNode.end = argNode.begin + newText.length() - 1;
                    this.area.getBuffer().insert(prevArgNode.end+1, ", " + newText );
                }
           }
           
            TreeChanged(null);
            area.editor.BuildWithoutBuilidgTree();
        }
    }
    
    private void InsertVariable(CommTreeNode node,  boolean before, boolean isNew)
    {
        VarFunParamFrm frm = new VarFunParamFrm("", "", "Add variable", this.area.frame, "", "In", this.area.conn, this.PR_id);
        TreePath path = this.getSelectionPath();
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           int oldLen = 0;//argNode.end - argNode.begin + 1;
           VarDeclTreeNode argNode = new VarDeclTreeNode();
           argNode.varNameList = frm.name;
           argNode.varType = frm.domain;
           argNode.defaultValue = frm.dafaultValue;
           
            String varNamesListShort = argNode.varNameList;
            
            if (varNamesListShort.length() > 10)
            {
                varNamesListShort = varNamesListShort.substring(0,9) + "..";
            }
            
           argNode.setUserObject(varNamesListShort +  " " + argNode.varType);
            
           String newText = argNode.varNameList;
           
           newText += " " + argNode.varType;
           
           if (!argNode.defaultValue.equals(""))
           {
                newText += " := " + argNode.defaultValue;
           }
           
            newText += ";";
            
           //((DefaultTreeModel)this.getModel()).reload(argNode);
           if (isNew) 
           {
               int index  = 0;
               
               ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, this.varsNode, index);
               this.setSelectionPath(path);
               
               int prenodetemp = this.area.GetVarPos();
               argNode.begin = -1;
               argNode.end = 0;
               
               int offset = newText.length()  + 2;
               
               this.UpdateArgVarPosition(prenodetemp - 1, offset);
               this.UpdateStatPosition(this.bodyNode, prenodetemp - 1, offset);
               
               argNode.begin = prenodetemp+2;
               argNode.end = argNode.begin + newText.length() - 1;
               
               this.area.getBuffer().insert(prenodetemp,"\n\t" + newText);
           }
           else
           {
                VarDeclTreeNode prevArgNode = (VarDeclTreeNode)node;
               
                if (before)
                {
                    int index  = this.varsNode.getIndex(prevArgNode);
                    
                    ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, this.varsNode, index);
                    this.setSelectionPath(path);
                    
                    int prenodetemp = prevArgNode.begin;
                    argNode.begin = -1;
                    argNode.end = 0;
                    
                    int offset = newText.length()  + 2;
                    this.UpdateArgVarPosition(prevArgNode.begin-1, offset);
                    this.UpdateStatPosition(this.bodyNode, prevArgNode.begin, offset);
                    
                    argNode.begin = prenodetemp;
                    argNode.end = argNode.begin + newText.length() - 1;
                    this.area.getBuffer().insert(argNode.begin,  newText +"\n\t");
                }
                else
                {
                    int index  = this.varsNode.getIndex(prevArgNode);
                    
                    ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, this.varsNode, index+1);
                    this.setSelectionPath(path);
                    
                    int prenodetemp = prevArgNode.begin;
                    argNode.begin = -1;
                    argNode.end = 0;
                    
                    int offset = newText.length()  + 2;
                    this.UpdateArgVarPosition(prevArgNode.end+1, offset);
                    this.UpdateStatPosition(this.bodyNode, prevArgNode.end, offset);
                    
                    argNode.begin = prevArgNode.end+3;
                    argNode.end = argNode.begin + newText.length() - 1;
                    this.area.getBuffer().insert(prevArgNode.end+1, "\n\t" + newText );
                }
           }
           TreeChanged(null);
           area.editor.BuildWithoutBuilidgTree();
        }
    }
    
    public void addElseActionPerformed(ActionEvent e)
    {
        CommTreeNode node = (CommTreeNode)this.getSelectionPath().getLastPathComponent();
        
        IfTreeNode ifNode = (IfTreeNode)node;
        
        int pos = ifNode.start_end_if - 1;
        
        String blankoStr = this.GetBlankoStr(this.area.getText(), pos);
        String newString = "else" + "\n" + blankoStr;
        this.area.getBuffer().insert(pos+1, newString);
        int offset  = newString.length();
        
        this.UpdateStatPosition(this.bodyNode, ifNode.start_end_if, offset);
        ifNode.start_else = pos+1;
        CommTreeNode elseNode = new CommTreeNode("Else");
        ((DefaultTreeModel)this.getModel()).insertNodeInto(elseNode, ifNode, 1);
        
        TreeChanged(null);
    }
    
    public void addDefaultActionPerformed(ActionEvent e)
    {
        CommTreeNode node = (CommTreeNode)this.getSelectionPath().getLastPathComponent();
        
        SwitchTreeNode switchNode = (SwitchTreeNode)node;
        
        int pos = switchNode.start_end_switch - 1;
        
        String blankoStr = this.GetBlankoStr(this.area.getText(), pos);
        String newString = "default" + "\n" + blankoStr;
        this.area.getBuffer().insert(pos+1, newString);
        int offset  = newString.length();
        
        this.UpdateStatPosition(this.bodyNode, switchNode.start_end_switch, offset);
        switchNode.start_default = pos+1;
        CommTreeNode elseNode = new CommTreeNode("Default");
        int index = switchNode.getChildCount();
        ((DefaultTreeModel)this.getModel()).insertNodeInto(elseNode, switchNode, index);
        
        TreeChanged(null);
    }
    
    public void addCaseActionPerformed(ActionEvent e)
    {
        CommTreeNode node = (CommTreeNode)this.getSelectionPath().getLastPathComponent();
        SwitchTreeNode switchNode = (SwitchTreeNode)node;
        
        CommValueFrm frm = new CommValueFrm("", "", "Add case", this.area.frame, "", "", this.area.conn,this.PR_id, 2);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
            CaseTreeNode argNode = new CaseTreeNode();
            CommTreeNode bNode = new CommTreeNode("Body");
            argNode.add(bNode);
            argNode.value = frm.dafaultValue;
           
            String nname = "case " + argNode.value + "";            
            argNode.setUserObject(nname);
            String blankoStr = this.GetBlankoStr(this.area.getText(), switchNode.begin - 1);
            
            String newText = "\n" + blankoStr + "case " + argNode.value + ":\n" + blankoStr + "end_case";
            
            int pos = switchNode.end_cond + 1;
            
            ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, switchNode, 0);
           
            this.area.getBuffer().insert(pos, newText);
            int offset = newText.length();
            
            this.UpdateStatPosition(this.bodyNode, pos, offset);
            
            argNode.begin = pos + ("\n" + blankoStr).length();
            argNode.end = pos + newText.length() - 1;
            argNode.start_col = pos + ("\n" + blankoStr + "case " + argNode.value).length();
            
            TreeChanged(null);
        }
    }
    
    public void editActionPerformed(ActionEvent e) 
    {
        CommTreeNode node = (CommTreeNode)this.getSelectionPath().getLastPathComponent();
        
        String nodePStr = this.getSelectionPath().getParentPath().getLastPathComponent().toString();
        String nodeStr = this.getSelectionPath().getLastPathComponent().toString();
        
        if (nodePStr.equals("Arguments"))
        {
            this.EditArgument(node);
            return;
        }
        
        if (nodePStr.equals("Variables"))
        {
            this.EditVariable(node);
            return;
        }
        
        if (node.type == 2) //assign
        {
            EditAssign(node);
            return;
        }
        
        if (node.type == 3) //if
        {
            EditIf(node);
            return;
        }
        
        if (node.type == 4) //if
        {
            EditWhile(node);
            return;
        }
        
        if (node.type == 5) //repeat
        {
            EditRepeat(node);
            return;
        }
        
        if (node.type == 6) //for
        {
            EditFor(node);
            return;
        }
        
        if (node.type == 7) //break
        {
            return;
        }
        
        if (node.type == 8) //return
        {
            EditReturn(node);
            return;
        }
        
        if (node.type == 9) //select
        {
            EditSelect(node);
            return;
        }
        
        if (node.type == 10) //select
        {
            EditSwitch(node);
            return;
        }
        
        if (node.type == 11) //select
        {
            EditCase(node);
            return;
        }
        
        if (node.type == 12) //print        
        {
            EditPrint(node);
            return;
        }
        
        if (node.type == 13) //fetch      
        {
            EditFetch(node);
            return;
        }

        if (node.type == 14) //Signal        
        {
            EditSignal(node);
            return;
        }
    }
    
    private void EditIf( CommTreeNode node)
    {
        IfTreeNode argNode = (IfTreeNode)node;
        
        ConditionFrm frm = new ConditionFrm("", "", "Edit command", this.area.frame, argNode.cond, "", this.area.conn ,this.PR_id);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           int oldLen = argNode.start_then - argNode.begin;
           argNode.cond = frm.dafaultValue;
           
            String nname = "if ";
            
            if(argNode.cond != null)
            {
                if(argNode.cond.length() > 10)
                {
                    nname += argNode.cond.substring(0, 7) + "...";
                }
                else
                {
                    nname += argNode.cond;
                }
            }
            
            argNode.setUserObject(nname);
           
            String newText = "if " + argNode.cond + " ";
           
           ((DefaultTreeModel)this.getModel()).reload(argNode);
           
           this.area.getBuffer().remove(argNode.begin, argNode.start_then - argNode.begin);                
           this.area.getBuffer().insert(argNode.begin, newText);
           int offset = newText.length() - oldLen;
           //argNode.end = argNode.begin + newText.length() - 1;
           this.UpdateStatPosition(this.bodyNode, argNode.begin + 1, offset);
           
           TreeChanged(null);
        }
    }
    
    private void EditSwitch( CommTreeNode node)
    {
        SwitchTreeNode argNode = (SwitchTreeNode)node;
        
        CommValueFrm frm = new CommValueFrm("", "", "Edit command", this.area.frame, argNode.value, "", this.area.conn ,this.PR_id, 1);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           int oldLen = argNode.end_cond - argNode.begin + 1;
           argNode.value = frm.dafaultValue;
           
           String nname = "switch " + argNode.value + "";
            
           argNode.setUserObject(nname);
           
           String newText = "switch " + argNode.value + "";
           
           ((DefaultTreeModel)this.getModel()).reload(argNode);
           
           this.area.getBuffer().remove(argNode.begin, oldLen);                
           this.area.getBuffer().insert(argNode.begin, newText);
           int offset = newText.length() - oldLen;
           //argNode.end = argNode.begin + newText.length() - 1;
           this.UpdateStatPosition(this.bodyNode, argNode.begin + 1, offset);
           
           TreeChanged(null);
        }
    }
    
    private void EditCase(CommTreeNode node)
    {
        CaseTreeNode argNode = (CaseTreeNode)node;
        
        CommValueFrm frm = new CommValueFrm("", "", "Edit command", this.area.frame, argNode.value, "", this.area.conn ,this.PR_id, 2);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           int oldLen = argNode.start_col - argNode.begin - 5;
           argNode.value = frm.dafaultValue;
           
            String nname = "case " + argNode.value + "";
            
            argNode.setUserObject(nname);
           
            String newText = argNode.value + "";
           
           ((DefaultTreeModel)this.getModel()).reload(argNode);
           
           this.area.getBuffer().remove(argNode.begin+5, oldLen);                
           this.area.getBuffer().insert(argNode.begin+5, newText);
           int offset = newText.length() - oldLen;
           //argNode.end = argNode.begin + newText.length() - 1;
           this.UpdateStatPosition(this.bodyNode, argNode.begin + 1, offset);
           
            TreeChanged(null);
        }
    }
    
    private void EditWhile(CommTreeNode node)
    {
        WhileTreeNode argNode = (WhileTreeNode)node;
        
        ConditionFrm frm = new ConditionFrm("", "", "Edit command", this.area.frame, argNode.cond, "", this.area.conn ,this.PR_id);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           int oldLen = argNode.start_begin - argNode.begin;
           argNode.cond = frm.dafaultValue;
           
            String nname = "while ";
            
            if(argNode.cond != null)
            {
                if(argNode.cond.length() > 10)
                {
                    nname += argNode.cond.substring(0, 7) + "...";
                }
                else
                {
                    nname += argNode.cond;
                }
            }
            
            argNode.setUserObject(nname);
           
            String newText = "while " + argNode.cond + " ";
           
           ((DefaultTreeModel)this.getModel()).reload(argNode);
           
           this.area.getBuffer().remove(argNode.begin, argNode.start_begin - argNode.begin);                
           this.area.getBuffer().insert(argNode.begin, newText);
           int offset = newText.length() - oldLen;
           //argNode.end = argNode.begin + newText.length() - 1;
           this.UpdateStatPosition(this.bodyNode, argNode.begin + 1, offset);
           
            TreeChanged(null);
        }
    }
    
    private void EditRepeat(CommTreeNode node)
    {
        RepeatTreeNode argNode = (RepeatTreeNode)node;
        
        ConditionFrm frm = new ConditionFrm("", "", "Edit command", this.area.frame, argNode.cond, "", this.area.conn ,this.PR_id);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           int oldLen = argNode.start_endrepeat - argNode.start_until - 6;
           argNode.cond = frm.dafaultValue;
           
           String nname = "repeat until ";
            
            if(argNode.cond != null)
            {
                if(argNode.cond.length() > 10)
                {
                    nname += argNode.cond.substring(0, 7) + "...";
                }
                else
                {
                    nname += argNode.cond;
                }
            }
            
            argNode.setUserObject(nname);
           
            String newText = "" + argNode.cond + " ";
           
           ((DefaultTreeModel)this.getModel()).reload(argNode);
           
           this.area.getBuffer().remove(argNode.start_until+6, argNode.start_endrepeat - argNode.start_until - 6);
           this.area.getBuffer().insert(argNode.start_until+6, newText);
           int offset = newText.length() - oldLen;
           
           this.UpdateStatPosition(this.bodyNode, argNode.start_until+1, offset);
        }
        
        TreeChanged(null);
    }
    
    private void EditReturn(CommTreeNode node)
    {
        ReturnTreeNode argNode = (ReturnTreeNode)node;
        
        CommValueFrm frm = new CommValueFrm("", "", "Edit command", this.area.frame, argNode.value, "", this.area.conn ,this.PR_id,0);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           int oldLen = argNode.end - argNode.begin - 7;
           argNode.value = frm.dafaultValue;
           
           String nname = "return ";
            
           if (argNode.value != null) 
           {
                if (argNode.value.length() > 10)
                {
                    nname += argNode.value.substring(0,7) + "...";
                }
                else
                {
                    nname += argNode.value;
                }
            }
            node.setUserObject(nname); 
            
            argNode.setUserObject(nname);
           
            String newText = "" + argNode.value + "";
           
           ((DefaultTreeModel)this.getModel()).reload(argNode);
           
           this.area.getBuffer().remove(argNode.begin+7, argNode.end - argNode.begin - 7);
           this.area.getBuffer().insert(argNode.begin+7, newText);
           int offset = newText.length() - oldLen;
           
           this.UpdateStatPosition(this.bodyNode, argNode.begin+1, offset);
           
           TreeChanged(null);
        }
    }
    
    private void EditPrint(CommTreeNode node)
    {
        PrintTreeNode argNode = (PrintTreeNode)node;
        
        CommValueFrm frm = new CommValueFrm("", "", "Edit command", this.area.frame, argNode.value, "", this.area.conn ,this.PR_id,3);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           int oldLen = argNode.end - argNode.begin;
           argNode.value = frm.dafaultValue;
           
           String nname = "print ";
            
           if (argNode.value != null) 
           {
                if (argNode.value.length() > 10)
                {
                    nname += argNode.value.substring(0,7) + "...";
                }
                else
                {
                    nname += argNode.value;
                }
            }
            node.setUserObject(nname); 
            
            argNode.setUserObject(nname);
           
            String newText = "print(" + argNode.value + ")";
           
           ((DefaultTreeModel)this.getModel()).reload(argNode);
           
           this.area.getBuffer().remove(argNode.begin, argNode.end - argNode.begin);
           this.area.getBuffer().insert(argNode.begin, newText);
           int offset = newText.length() - oldLen;
           
           this.UpdateStatPosition(this.bodyNode, argNode.begin+1, offset);
           
           TreeChanged(null);
        }
    }
    
    private void EditFor(CommTreeNode node)
    {
        ForTreeNode argNode = (ForTreeNode)node;
        
        ForEditFrm frm = new ForEditFrm(argNode.varname, "", "Edit command", this.area.frame, argNode.from, argNode.to, this.area.conn ,this.PR_id);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           int oldLen = argNode.start_begin - argNode.begin - 4;
           argNode.varname = frm.name;
           argNode.from = frm.fromValue;
           argNode.to = frm.toValue;
            
            String nname = "for ";
            
            if(argNode.varname != null)
            {
                nname += argNode.varname + " := ";
                
                if(argNode.from != null)
                {
                    nname += argNode.from;    
                }
            }
            
            nname += " to ...";
            argNode.setUserObject(nname);
           
            String newText = "" + argNode.varname + " := " + argNode.from + " to " + argNode.to + " ";
           
           ((DefaultTreeModel)this.getModel()).reload(argNode);
           
           this.area.getBuffer().remove(argNode.begin+4, argNode.start_begin - argNode.begin - 4);
           this.area.getBuffer().insert(argNode.begin+4, newText);
           int offset = newText.length() - oldLen;
           
           this.UpdateStatPosition(this.bodyNode, argNode.begin+1, offset);
           
            TreeChanged(null);
        }
    }
    
    private void EditSelect(CommTreeNode node)
    {
        SelectTreeNode argNode = (SelectTreeNode)node;
        
        SelectEditFrm frm = new SelectEditFrm(argNode.varName, "", "Edit command", this.area.frame, argNode.func, argNode.from, this.area.conn, this.PR_id);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
            int oldLen = argNode.end - argNode.begin - 7;
            argNode.varName = frm.name;
            argNode.func = frm.func;
            argNode.from = frm.fromValue;
            
            String nname = "select " + argNode.func + " into ..";
            node.setUserObject(nname);
             
             argNode.setUserObject(nname);
            
             String newText = "" + argNode.func + " into " + argNode.varName + " from " + argNode.from;
            
            ((DefaultTreeModel)this.getModel()).reload(argNode);
            
            this.area.getBuffer().remove(argNode.begin+7, oldLen);
            this.area.getBuffer().insert(argNode.begin+7, newText);
            int offset = newText.length() - oldLen;
            
            this.UpdateStatPosition(this.bodyNode, argNode.begin+1, offset);
            
            TreeChanged(null);
        }
    }
    
    private void EditFetch(CommTreeNode node)
    {
        FetchTreeNode argNode = (FetchTreeNode)node;
        
        FetchEditFrm frm = new FetchEditFrm(argNode.iter, "", "Edit command", this.area.frame, "", argNode.varlist, this.area.conn, this.PR_id);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
            int oldLen = argNode.end - argNode.begin;
            argNode.iter = frm.name;
            argNode.varlist = frm.fromValue;
            
            String nname = "fetch ";
            
            if (argNode.iter != null) 
            {
                if (argNode.iter.length() > 10)
                {
                    nname += argNode.iter.substring(0,7) + "...";
                }
                else
                {
                    nname += argNode.iter;
                }
            }            
            node.setUserObject(nname);
            argNode.setUserObject(nname);
             
            String newText = "fetch " + argNode.iter + " into " + argNode.varlist;
            
            ((DefaultTreeModel)this.getModel()).reload(argNode);
            
            this.area.getBuffer().remove(argNode.begin, argNode.end - argNode.begin);
            this.area.getBuffer().insert(argNode.begin, newText);
            int offset = newText.length() - oldLen;
            
            this.UpdateStatPosition(this.bodyNode, argNode.begin+1, offset);
            
            TreeChanged(null);
        }
    }
    
    private void EditSignal(CommTreeNode node)
    {
        SignalTreeNode argNode = (SignalTreeNode)node;
        
        SignalEditFrm frm = new SignalEditFrm(argNode.paramlist, "Edit command", this.area.frame, this.area.conn, this.PR_id);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
            int oldLen = argNode.end - argNode.begin;
            argNode.paramlist = frm.name;
            
            String nname = "signal(";
            
            if (argNode.paramlist != null) 
            {
                if (argNode.paramlist.length() > 10)
                {
                    nname += argNode.paramlist.substring(0,7) + "...)";
                }
                else
                {
                    nname += argNode.paramlist + ")";
                }
            }
            node.setUserObject(nname);
            argNode.setUserObject(nname);
             
            String newText = "signal(" + argNode.paramlist + ")";
            
            ((DefaultTreeModel)this.getModel()).reload(argNode);
            
            this.area.getBuffer().remove(argNode.begin, argNode.end - argNode.begin);
            this.area.getBuffer().insert(argNode.begin, newText);
            int offset = newText.length() - oldLen;
            
            this.UpdateStatPosition(this.bodyNode, argNode.begin+1, offset);
            
            TreeChanged(null);
        }
    }
    
    private void EditAssign( CommTreeNode node)
    {
        AssignTreeNode argNode = (AssignTreeNode)node;
        
        AssignFrm frm = new AssignFrm(argNode.varName, "", "Edit command", this.area.frame, argNode.value, "", this.area.conn, this.PR_id);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           int oldLen = argNode.end - argNode.begin + 1;
           argNode.varName = frm.name;
           argNode.value = frm.dafaultValue;
           
            String nname = argNode.varName + " := ";
            
            if(argNode.value != null)
            {
                if(argNode.value.length() > 10)
                {
                    nname += argNode.value.substring(0, 7) + "...";
                }
                else
                {
                    nname += argNode.value;
                }
            }
            
            argNode.setUserObject(nname);
           
           String newText = argNode.varName + " := " + argNode.value + ";";
           
           ((DefaultTreeModel)this.getModel()).reload(argNode);
           
           this.area.getBuffer().remove(argNode.begin, argNode.end - argNode.begin + 1);                
           this.area.getBuffer().insert(argNode.begin, newText);
           int offset = newText.length() - oldLen;
           argNode.end = argNode.begin + newText.length() - 1;
           this.UpdateStatPosition(this.bodyNode, argNode.end + 1, offset);
           
           TreeChanged(null);
        }
    }
    
    private void AddAssign(CommTreeNode node, boolean add, boolean before)
    {
        AssignFrm frm = new AssignFrm("", "", "Add command", this.area.frame, "", "", this.area.conn, this.PR_id);
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
            AssignTreeNode argNode = new AssignTreeNode("");
            int oldLen = 0;
            argNode.varName = frm.name;
            argNode.value = frm.dafaultValue;
           
            String nname = argNode.varName + " := ";
            
            if(argNode.value != null)
            {
                if(argNode.value.length() > 10)
                {
                    nname += argNode.value.substring(0, 7) + "...";
                }
                else
                {
                    nname += argNode.value;
                }
            }
            
            argNode.setUserObject(nname);
            
            if (add)
            {
                int pos = this.FindInsertPos(node);
                String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), ((CommTreeNode)node.getParent()).begin-1) + "\t";
                
                String newText = argNode.varName + " := " + argNode.value + ";";
                
                this.area.getBuffer().insert(pos, blankStr + newText);
                int offset = (blankStr + newText).length();
                
                this.UpdateStatPosition(this.bodyNode, pos + 1, offset);
                argNode.begin = pos + blankStr.length();
                argNode.end = argNode.begin + newText.length() - 1;
                ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, node, 0);
            }
            else
            {
                if (before)
                {
                    int pos = node.begin;
                    String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), pos-1);
                    
                    String newText = argNode.varName + " := " + argNode.value + ";";
                    
                    this.area.getBuffer().insert(pos, newText + blankStr);
                    int offset = (blankStr + newText).length();
                    
                    this.UpdateStatPosition(this.bodyNode, pos, offset);
                    argNode.begin = pos;
                    argNode.end = argNode.begin + newText.length() - 1;
                    
                    int index  = node.getParent().getIndex(node);
                    ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index);
                }
                else
                {
                    int pos = node.end+1;
                    String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), node.begin-1);
                    
                    String newText = argNode.varName + " := " + argNode.value + ";";
                    
                    this.area.getBuffer().insert(pos, blankStr + newText);
                    int offset = (blankStr + newText).length();
                    
                    this.UpdateStatPosition(this.bodyNode, pos, offset);
                    argNode.begin = pos + blankStr.length();
                    argNode.end = argNode.begin + newText.length() - 1;
                    
                    int index  = node.getParent().getIndex(node);
                    ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index+1);
                }
            }
            
            TreeChanged(null);
        }
    }
    
    private void AddIf(CommTreeNode node, boolean add, boolean before)
    {
        ConditionFrm frm = new ConditionFrm("", "", "Add command", this.area.frame, "", "", this.area.conn ,this.PR_id);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           IfTreeNode argNode = new IfTreeNode("");
            
           int oldLen = argNode.start_then - argNode.begin;
           argNode.cond = frm.dafaultValue;
           
           String nname = "if ";
            
           if(argNode.cond != null)
           {
                if(argNode.cond.length() > 10)
                {
                    nname += argNode.cond.substring(0, 7) + "...";
                }
                else
                {
                    nname += argNode.cond;
                }
           }
            
           argNode.setUserObject(nname);
           
           if (add)
           {
               int pos = this.FindInsertPos(node);
               String blankStr = "";
               
               if (node.getParent() == null || node.getParent().getParent() == null)
               {
                   blankStr = "\n\t";
               }
               else
               {
                    blankStr = "\n" + this.GetBlankoStr(this.area.getText(), ((CommTreeNode)node.getParent()).begin-1) + "\t";
               }
               String newText = "if " + argNode.cond + " " + "then" + blankStr+"end_if;";
               
               this.area.getBuffer().insert(pos, blankStr + newText);
               int offset = (blankStr + newText).length();
               
               this.UpdateStatPosition(this.bodyNode, pos + 1, offset);
               
               argNode.begin = pos + blankStr.length();
               argNode.start_else = -1;
               argNode.start_then = argNode.begin + ("if " + argNode.cond + " ").length();
               argNode.start_end_if = argNode.begin + ("if " + argNode.cond + " " + "then" + blankStr).length();
               argNode.end = argNode.begin + newText.length() - 1;
               
               CommTreeNode bodyNode = new CommTreeNode("Body");
               argNode.add(bodyNode);
               ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, node, 0);
           }
           else
           {
               if(before)
               {
                   int pos = node.begin;
                   String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), pos-1);
                   
                   String newText = "if " + argNode.cond + " " + "then" + blankStr+"end_if;";
                   
                   this.area.getBuffer().insert(pos, newText + blankStr);
                   int offset = (blankStr + newText).length();
                   
                   this.UpdateStatPosition(this.bodyNode, pos, offset);
                   
                   argNode.begin = pos;
                   argNode.start_else = -1;
                   argNode.start_then = argNode.begin + ("if " + argNode.cond + " ").length();
                   argNode.start_end_if = argNode.begin + ("if " + argNode.cond + " " + "then" + blankStr).length();
                   argNode.end = argNode.begin + newText.length() - 1;
                   
                   CommTreeNode bodyNode = new CommTreeNode("Body");
                   argNode.add(bodyNode);
                   
                   int index  = node.getParent().getIndex(node);
                   ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index);
               }
               else
               {
                   int pos = node.end+1;
                   String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), node.begin-1);
                   
                   String newText = "if " + argNode.cond + " " + "then" + blankStr+"end_if;";
                   
                   this.area.getBuffer().insert(pos, blankStr + newText);
                   int offset = (blankStr + newText).length();
                   
                   this.UpdateStatPosition(this.bodyNode, pos, offset);
                   
                   argNode.begin = pos + blankStr.length();;
                   argNode.start_else = -1;
                   argNode.start_then = argNode.begin + ("if " + argNode.cond + " ").length();
                   argNode.start_end_if = argNode.begin + ("if " + argNode.cond + " " + "then" + blankStr).length();
                   argNode.end = argNode.begin + newText.length() - 1;
                   
                   CommTreeNode bodyNode = new CommTreeNode("Body");
                   argNode.add(bodyNode);
                   
                   int index  = node.getParent().getIndex(node);
                   ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index+1); 
               }
           }
           
           TreeChanged(null);
        }
    }
    
    private void AddWhile(CommTreeNode node, boolean add, boolean before)
    {
        ConditionFrm frm = new ConditionFrm("", "", "Add command", this.area.frame, "", "", this.area.conn ,this.PR_id);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           WhileTreeNode argNode = new WhileTreeNode("");
            
           argNode.cond = frm.dafaultValue;
           
           String nname = "while ";
            
           if(argNode.cond != null)
           {
                if(argNode.cond.length() > 10)
                {
                    nname += argNode.cond.substring(0, 7) + "...";
                }
                else
                {
                    nname += argNode.cond;
                }
           }
            
           argNode.setUserObject(nname);
           
           if (add)
           {
               int pos = this.FindInsertPos(node);
               String blankStr = "";
               
               if (node.getParent() == null || node.getParent().getParent() == null)
               {
                   blankStr = "\n\t";
               }
               else
               {
                    blankStr = "\n" + this.GetBlankoStr(this.area.getText(), ((CommTreeNode)node.getParent()).begin-1) + "\t";
               }
               
               String newText = "while " + argNode.cond + " " + "begin" + blankStr + "end_while;";
               
               this.area.getBuffer().insert(pos, blankStr + newText);
               int offset = (blankStr + newText).length();
               
               this.UpdateStatPosition(this.bodyNode, pos + 1, offset);
               
               argNode.begin = pos + blankStr.length();
               argNode.start_begin = argNode.begin + ("while " + argNode.cond + " ").length();
               argNode.end = argNode.begin + newText.length() - 1;
               
               CommTreeNode bodyNode = new CommTreeNode("Body");
               argNode.add(bodyNode);
               ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, node, 0);
           }
           else
           {
               if(before)
               {
                   int pos = node.begin;
                   String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), pos-1);
                   String newText = "while " + argNode.cond + " " + "begin" + blankStr + "end_while;";
                   
                   this.area.getBuffer().insert(pos, newText + blankStr);
                   int offset = (blankStr + newText).length();
                   
                   this.UpdateStatPosition(this.bodyNode, pos, offset);
                   
                   argNode.begin = pos;
                   argNode.start_begin = argNode.begin + ("while " + argNode.cond + " ").length();
                   argNode.end = argNode.begin + newText.length() - 1;
                   
                   CommTreeNode bodyNode = new CommTreeNode("Body");
                   argNode.add(bodyNode);
                   
                   int index  = node.getParent().getIndex(node);
                   ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index);
               }
               else
               {
                   int pos = node.end+1;
                   String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), node.begin-1);
                   String newText = "while " + argNode.cond + " " + "begin" + blankStr + "end_while;";
                   
                   this.area.getBuffer().insert(pos, blankStr + newText);
                   int offset = (blankStr + newText).length();
                   
                   this.UpdateStatPosition(this.bodyNode, pos, offset);
                   
                   argNode.begin = pos + blankStr.length();;
                   argNode.start_begin = argNode.begin + ("while " + argNode.cond + " ").length();
                   argNode.end = argNode.begin + newText.length() - 1;
                   
                   CommTreeNode bodyNode = new CommTreeNode("Body");
                   argNode.add(bodyNode);
                   
                   int index  = node.getParent().getIndex(node);
                   ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index+1); 
               }
           }
           
           TreeChanged(null);
        }
    }
    
    private void AddRepeat(CommTreeNode node, boolean add, boolean before)
    {
        ConditionFrm frm = new ConditionFrm("", "", "Add command", this.area.frame, "", "", this.area.conn ,this.PR_id);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           RepeatTreeNode argNode = new RepeatTreeNode("");
            
           argNode.cond = frm.dafaultValue;
           
            String nname = "repeat until ";
             
            if(argNode.cond != null)
            {
                 if(argNode.cond.length() > 10)
                 {
                     nname += argNode.cond.substring(0, 7) + "...";
                 }
                 else
                 {
                     nname += argNode.cond;
                 }
            }
             
            argNode.setUserObject(nname);
           
            if (add)
            {
               int pos = this.FindInsertPos(node);
               String blankStr = "";
               
               if (node.getParent() == null || node.getParent().getParent() == null)
               {
                   blankStr = "\n\t";
               }
               else
               {
                    blankStr = "\n" + this.GetBlankoStr(this.area.getText(), ((CommTreeNode)node.getParent()).begin-1) + "\t";
               }
               
               String newText = "repeat" + blankStr + "until " +  argNode.cond + " " + "end_repeat;";
               
               this.area.getBuffer().insert(pos, blankStr + newText);
               int offset = (blankStr + newText).length();
               
               this.UpdateStatPosition(this.bodyNode, pos + 1, offset);
               
               argNode.begin = pos + blankStr.length();
               argNode.start_until = argNode.begin + ("repeat" + blankStr).length();
               argNode.start_endrepeat = argNode.begin + ("repeat" + blankStr + "until " +  argNode.cond + " ").length();
               argNode.end = argNode.begin + newText.length() - 1;
               
               CommTreeNode bodyNode = new CommTreeNode("Body");
               argNode.add(bodyNode);
               ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, node, 0);
           }
           else
           {
               if(before)
               {
                   int pos = node.begin;
                   String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), pos-1);
                   String newText = "repeat" + blankStr + "until " +  argNode.cond + " " + "end_while;";
                   
                   this.area.getBuffer().insert(pos, newText + blankStr);
                   int offset = (blankStr + newText).length();
                   
                   this.UpdateStatPosition(this.bodyNode, pos, offset);
                   
                   argNode.begin = pos;
                   argNode.start_until = argNode.begin + ("repeat" + blankStr).length();
                   argNode.start_endrepeat = argNode.begin + ("repeat" + blankStr + "until " +  argNode.cond + " ").length();
                   argNode.end = argNode.begin + newText.length() - 1;
                   
                   CommTreeNode bodyNode = new CommTreeNode("Body");
                   argNode.add(bodyNode);
                   
                   int index  = node.getParent().getIndex(node);
                   ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index);
               }
               else
               {
                   int pos = node.end+1;
                   String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), node.begin-1);
                   String newText = "repeat" + blankStr + "until " +  argNode.cond + " " + "end_while;";
                   
                   this.area.getBuffer().insert(pos, blankStr + newText);
                   int offset = (blankStr + newText).length();
                   
                   this.UpdateStatPosition(this.bodyNode, pos, offset);
                   
                   argNode.begin = pos + blankStr.length();;
                   argNode.start_until = argNode.begin + ("repeat" + blankStr).length();
                   argNode.start_endrepeat = argNode.begin + ("repeat" + blankStr + "until " +  argNode.cond + " ").length();
                   argNode.end = argNode.begin + newText.length() - 1;
                   
                   CommTreeNode bodyNode = new CommTreeNode("Body");
                   argNode.add(bodyNode);
                   
                   int index  = node.getParent().getIndex(node);
                   ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index+1); 
               }
           }
           
            TreeChanged(null);
        }
    }
    
    private void AddForCommand(CommTreeNode node, boolean add, boolean before)
    { 
        ForEditFrm frm = new ForEditFrm("", "", "Edit command", this.area.frame, "", "", this.area.conn ,this.PR_id);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           ForTreeNode argNode = new ForTreeNode("");
            
           argNode.varname = frm.name;
           argNode.from = frm.fromValue;
           argNode.to = frm.toValue;
            
            String nname = "for ";
            
            if(argNode.varname != null)
            {
                nname += argNode.varname + " := ";
                
                if(argNode.from != null)
                {
                    nname += argNode.from;    
                }
            }
            
            nname += " to ...";
            argNode.setUserObject(nname);
           
            if (add)
            {
               int pos = this.FindInsertPos(node);
               String blankStr = "";
               
               if (node.getParent() == null || node.getParent().getParent() == null)
               {
                   blankStr = "\n\t";
               }
               else
               {
                    blankStr = "\n" + this.GetBlankoStr(this.area.getText(), ((CommTreeNode)node.getParent()).begin-1) + "\t";
               }
               
               String newText = "for " + argNode.varname + " := " + argNode.from + " to " + argNode.to + " " + "begin"+ blankStr + "end_for;";
               
               this.area.getBuffer().insert(pos, blankStr + newText);
               int offset = (blankStr + newText).length();
               
               this.UpdateStatPosition(this.bodyNode, pos + 1, offset);
               
               argNode.begin = pos + blankStr.length();
               argNode.start_begin = argNode.begin + ("for " + argNode.varname + " := " + argNode.from + " to " + argNode.to + " ").length();
               argNode.end = argNode.begin + newText.length() - 1;
               
               CommTreeNode bodyNode = new CommTreeNode("Body");
               argNode.add(bodyNode);
               ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, node, 0);
           }
           else
           {
               if(before)
               {
                   int pos = node.begin;
                   String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), pos-1);
                   String newText = "for " + argNode.varname + " := " + argNode.from + " to " + argNode.to + " " + "begin"+ blankStr + "end_for;";
                   
                   this.area.getBuffer().insert(pos, newText + blankStr);
                   int offset = (blankStr + newText).length();
                   
                   this.UpdateStatPosition(this.bodyNode, pos, offset);
                   
                   argNode.begin = pos;
                   argNode.start_begin = argNode.begin + ("for " + argNode.varname + " := " + argNode.from + " to " + argNode.to + " ").length();
                   argNode.end = argNode.begin + newText.length() - 1;
                   
                   CommTreeNode bodyNode = new CommTreeNode("Body");
                   argNode.add(bodyNode);
                   
                   int index  = node.getParent().getIndex(node);
                   ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index);
               }
               else
               {
                   int pos = node.end+1;
                   String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), node.begin-1);
                   String newText = "for " + argNode.varname + " := " + argNode.from + " to " + argNode.to + " " + "begin"+ blankStr + "end_for;";
                   
                   this.area.getBuffer().insert(pos, blankStr + newText);
                   int offset = (blankStr + newText).length();
                   
                   this.UpdateStatPosition(this.bodyNode, pos, offset);
                   
                   argNode.begin = pos + blankStr.length();;
                   argNode.start_begin = argNode.begin + ("for " + argNode.varname + " := " + argNode.from + " to " + argNode.to + " ").length();
                   argNode.end = argNode.begin + newText.length() - 1;
                   
                   CommTreeNode bodyNode = new CommTreeNode("Body");
                   argNode.add(bodyNode);
                   
                   int index  = node.getParent().getIndex(node);
                   ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index+1); 
               }
           }
           
            TreeChanged(null);
        }
    }
    
    private void AddBreakCommand(CommTreeNode node, boolean add, boolean before)
    { 
        BreakTreeNode argNode = new BreakTreeNode("");
            
        String nname = "break";
        argNode.setUserObject(nname);
       
        if (add)
        {
           int pos = this.FindInsertPos(node);
           String blankStr = "";
           
           if (node.getParent() == null || node.getParent().getParent() == null)
           {
               blankStr = "\n\t";
           }
           else
           {
                blankStr = "\n" + this.GetBlankoStr(this.area.getText(), ((CommTreeNode)node.getParent()).begin-1) + "\t";
           }
           
           String newText = "break;";
           
           this.area.getBuffer().insert(pos, blankStr + newText);
           int offset = (blankStr + newText).length();
           
           this.UpdateStatPosition(this.bodyNode, pos + 1, offset);
           
           argNode.begin = pos + blankStr.length();
           argNode.end = argNode.begin + newText.length() - 1;
           
           ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, node, 0);
       }
       else
       {
           if(before)
           {
               int pos = node.begin;
               String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), pos-1);
               String newText = "break;";
               
               this.area.getBuffer().insert(pos, newText + blankStr);
               int offset = (blankStr + newText).length();
               
               this.UpdateStatPosition(this.bodyNode, pos, offset);
               
               argNode.begin = pos;
               argNode.end = argNode.begin + newText.length() - 1;
               
               int index  = node.getParent().getIndex(node);
               ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index);
           }
           else
           {
               int pos = node.end+1;
               String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), node.begin-1);
               String newText = "break;";
               
               this.area.getBuffer().insert(pos, blankStr + newText);
               int offset = (blankStr + newText).length();
               
               this.UpdateStatPosition(this.bodyNode, pos, offset);
               
               argNode.begin = pos + blankStr.length();;
               argNode.end = argNode.begin + newText.length() - 1;
               
               int index  = node.getParent().getIndex(node);
               ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index+1); 
           }
       }
       
       TreeChanged(null);
    }
    
    private void AddReturn(CommTreeNode node, boolean add, boolean before)
    {
        CommValueFrm frm = new CommValueFrm("", "", "Add command", this.area.frame, "", "", this.area.conn ,this.PR_id, 0);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           ReturnTreeNode argNode = new ReturnTreeNode("");
            
           argNode.value = frm.dafaultValue;
           
            String nname = "return ";
            
            if (argNode.value != null) 
            {
                if (argNode.value.length() > 10)
                {
                    nname += argNode.value.substring(0,7) + "...";
                }
                else
                {
                    nname += argNode.value;
                }
            }
            
           argNode.setUserObject(nname);
           
           if (add)
           {
               int pos = this.FindInsertPos(node);
               String blankStr = "";
               
               if (node.getParent() == null || node.getParent().getParent() == null)
               {
                   blankStr = "\n\t";
               }
               else
               {
                    blankStr = "\n" + this.GetBlankoStr(this.area.getText(), ((CommTreeNode)node.getParent()).begin-1) + "\t";
               }
               
               String newText = "return " + argNode.value + ";";
               
               this.area.getBuffer().insert(pos, blankStr + newText);
               int offset = (blankStr + newText).length();
               
               this.UpdateStatPosition(this.bodyNode, pos + 1, offset);
               
               argNode.begin = pos + blankStr.length();
               argNode.end = argNode.begin + newText.length() - 1;
               
               ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, node, 0);
           }
           else
           {
               if(before)
               {
                   int pos = node.begin;
                   String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), pos-1);
                   String newText = "return " + argNode.value + ";";
                   
                   this.area.getBuffer().insert(pos, newText + blankStr);
                   int offset = (blankStr + newText).length();
                   
                   this.UpdateStatPosition(this.bodyNode, pos, offset);
                   
                   argNode.begin = pos;
                   argNode.end = argNode.begin + newText.length() - 1;
                   
                   int index  = node.getParent().getIndex(node);
                   ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index);
               }
               else
               {
                   int pos = node.end+1;
                   String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), node.begin-1);
                   String newText = "return " + argNode.value + ";";
                   
                   this.area.getBuffer().insert(pos, blankStr + newText);
                   int offset = (blankStr + newText).length();
                   
                   this.UpdateStatPosition(this.bodyNode, pos, offset);
                   
                   argNode.begin = pos + blankStr.length();
                   argNode.end = argNode.begin + newText.length() - 1;
                   
                   int index  = node.getParent().getIndex(node);
                   ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index+1); 
               }
           }
           
           TreeChanged(null);
        }
    }
    
    private void AddSelect(CommTreeNode node, boolean add, boolean before)
    {
        SelectEditFrm frm = new SelectEditFrm("", "", "Add command", this.area.frame, "", "", this.area.conn ,this.PR_id);
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
            SelectTreeNode argNode = new SelectTreeNode("");
            
            argNode.varName = frm.name;
            argNode.func = frm.func;
            argNode.from = frm.fromValue;
           
            String nname = "select " + argNode.func + " into ..";
            argNode.setUserObject(nname);
           
            if (add)
            {
               int pos = this.FindInsertPos(node);
               String blankStr = "";
               
               if (node.getParent() == null || node.getParent().getParent() == null)
               {
                   blankStr = "\n\t";
               }
               else
               {
                    blankStr = "\n" + this.GetBlankoStr(this.area.getText(), ((CommTreeNode)node.getParent()).begin-1) + "\t";
               }
               String newText = "select " + argNode.func + " into " + argNode.varName + " from " + argNode.from + ";";
               
               this.area.getBuffer().insert(pos, blankStr + newText);
               int offset = (blankStr + newText).length();
               
               this.UpdateStatPosition(this.bodyNode, pos + 1, offset);
               
               argNode.begin = pos + blankStr.length();
               argNode.end = argNode.begin + newText.length() - 1;
               
               ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, node, 0);
           }
           else
           {
               if(before)
               {
                   int pos = node.begin;
                   String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), pos-1);
                   
                   String newText = "select " + argNode.func + " into " + argNode.varName + " from " + argNode.from + ";";
                   
                   this.area.getBuffer().insert(pos, newText + blankStr);
                   int offset = (blankStr + newText).length();
                   
                   this.UpdateStatPosition(this.bodyNode, pos, offset);
                   
                   argNode.begin = pos;
                   argNode.end = argNode.begin + newText.length() - 1;
                   
                   int index  = node.getParent().getIndex(node);
                   ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index);
               }
               else
               {
                   int pos = node.end+1;
                   String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), node.begin-1);
                   
                   String newText = "select " + argNode.func + " into " + argNode.varName + " from " + argNode.from + ";";
                   
                   this.area.getBuffer().insert(pos, blankStr + newText);
                   int offset = (blankStr + newText).length();
                   
                   this.UpdateStatPosition(this.bodyNode, pos, offset);
                   
                   argNode.begin = pos + blankStr.length();;
                   argNode.end = argNode.begin + newText.length() - 1;
                   
                   int index  = node.getParent().getIndex(node);
                   ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index+1); 
               }
           }
           TreeChanged(null);
        }
    }
    
    private void AddSwitch(CommTreeNode node, boolean add, boolean before)
    {
        CommValueFrm frm = new CommValueFrm("", "", "Add command", this.area.frame, "", "", this.area.conn ,this.PR_id, 1);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           SwitchTreeNode argNode = new SwitchTreeNode("");
            
           argNode.value = frm.dafaultValue;
           
           String nname = "switch " + argNode.value + "";            
           argNode.setUserObject(nname);
           
           if (add)
           {
               int pos = this.FindInsertPos(node);
               String blankStr = "";
               
               if (node.getParent() == null || node.getParent().getParent() == null)
               {
                   blankStr = "\n\t";
               }
               else
               {
                    blankStr = "\n" + this.GetBlankoStr(this.area.getText(), ((CommTreeNode)node.getParent()).begin-1) + "\t";
               }
               String newText = "switch " + argNode.value + blankStr + "end_switch;";
               
               this.area.getBuffer().insert(pos, blankStr + newText);
               int offset = (blankStr + newText).length();
               
               this.UpdateStatPosition(this.bodyNode, pos + 1, offset);
               
               argNode.begin = pos + blankStr.length();
               argNode.start_default = -1;
               argNode.start_end_switch = argNode.begin + ("switch " + argNode.value + blankStr).length();
               argNode.end_cond = argNode.begin + ("switch " + argNode.value).length() - 1;
               argNode.end = argNode.begin + newText.length() - 1;
               
               ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, node, 0);
           }
           else
           {
               if(before)
               {
                   int pos = node.begin;
                   String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), pos-1);
                   
                   String newText = "switch " + argNode.value + blankStr + "end_switch;";
                   
                   this.area.getBuffer().insert(pos, newText + blankStr);
                   int offset = (blankStr + newText).length();
                   
                   this.UpdateStatPosition(this.bodyNode, pos, offset);
                   
                   argNode.begin = pos;
                   argNode.start_default = -1;
                   argNode.start_end_switch = argNode.begin + ("switch " + argNode.value + blankStr).length();
                   argNode.end_cond = argNode.begin + ("switch " + argNode.value).length() - 1;
                   argNode.end = argNode.begin + newText.length() - 1;
                   
                   int index  = node.getParent().getIndex(node);
                   ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index);
               }
               else
               {
                   int pos = node.end+1;
                   String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), node.begin-1);
                   
                   String newText = "switch " + argNode.value + blankStr + "end_switch;";
                   
                   this.area.getBuffer().insert(pos, blankStr + newText);
                   int offset = (blankStr + newText).length();
                   
                   this.UpdateStatPosition(this.bodyNode, pos, offset);
                   
                   argNode.begin = pos + blankStr.length();
                   argNode.start_default = -1;
                   argNode.start_end_switch = argNode.begin + ("switch " + argNode.value + blankStr).length();
                   argNode.end_cond = argNode.begin + ("switch " + argNode.value).length() - 1;
                   argNode.end = argNode.begin + newText.length() - 1;
                   
                   int index  = node.getParent().getIndex(node);
                   ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index+1); 
               }
           }
           TreeChanged(null);
        }
    }
    
    private void AddPrint(CommTreeNode node, boolean add, boolean before)
    {
        CommValueFrm frm = new CommValueFrm("", "", "Add command", this.area.frame, "", "", this.area.conn,this.PR_id, 3);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           PrintTreeNode argNode = new PrintTreeNode("");
            
           argNode.value = frm.dafaultValue;
           
            String nname = "print ";
            
            if (argNode.value != null) 
            {
                if (argNode.value.length() > 10)
                {
                    nname += argNode.value.substring(0,7) + "...";
                }
                else
                {
                    nname += argNode.value;
                }
            }
            
           argNode.setUserObject(nname);
           
           if (add)
           {
               int pos = this.FindInsertPos(node);
               String blankStr = "";
               
               if (node.getParent() == null || node.getParent().getParent() == null)
               {
                   blankStr = "\n\t";
               }
               else
               {
                    blankStr = "\n" + this.GetBlankoStr(this.area.getText(), ((CommTreeNode)node.getParent()).begin-1) + "\t";
               }
               
               String newText = "print(" + argNode.value + ");";
               
               this.area.getBuffer().insert(pos, blankStr + newText);
               int offset = (blankStr + newText).length();
               
               this.UpdateStatPosition(this.bodyNode, pos + 1, offset);
               
               argNode.begin = pos + blankStr.length();
               argNode.end = argNode.begin + newText.length() - 1;
               
               ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, node, 0);
           }
           else
           {
               if(before)
               {
                   int pos = node.begin;
                   String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), pos-1);
                   String newText = "print(" + argNode.value + ");";
                   
                   this.area.getBuffer().insert(pos, newText + blankStr);
                   int offset = (blankStr + newText).length();
                   
                   this.UpdateStatPosition(this.bodyNode, pos, offset);
                   
                   argNode.begin = pos;
                   argNode.end = argNode.begin + newText.length() - 1;
                   
                   int index  = node.getParent().getIndex(node);
                   ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index);
               }
               else
               {
                   int pos = node.end+1;
                   String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), node.begin-1);
                   String newText = "print(" + argNode.value + ");";
                   
                   this.area.getBuffer().insert(pos, blankStr + newText);
                   int offset = (blankStr + newText).length();
                   
                   this.UpdateStatPosition(this.bodyNode, pos, offset);
                   
                   argNode.begin = pos + blankStr.length();
                   argNode.end = argNode.begin + newText.length() - 1;
                   
                   int index  = node.getParent().getIndex(node);
                   ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index+1); 
               }
           }
           
           TreeChanged(null);
        }
    }
    
    private void AddFetch(CommTreeNode node, boolean add, boolean before)
    {
        FetchEditFrm frm = new FetchEditFrm("", "", "Add command", this.area.frame, "", "", this.area.conn,this.PR_id);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           FetchTreeNode argNode = new FetchTreeNode("");
            
           argNode.iter = frm.name;
           argNode.varlist = frm.fromValue;
            
           String nname = "fetch ";
            
           if (argNode.iter != null) 
           {
                if (argNode.iter.length() > 10)
                {
                    nname += argNode.iter.substring(0,7) + "...";
                }
                else
                {
                    nname += argNode.iter;
                }
           }
           
           argNode.setUserObject(nname);
           
           if (add)
           {
               int pos = this.FindInsertPos(node);
               String blankStr = "";
               
               if (node.getParent() == null || node.getParent().getParent() == null)
               {
                   blankStr = "\n\t";
               }
               else
               {
                    blankStr = "\n" + this.GetBlankoStr(this.area.getText(), ((CommTreeNode)node.getParent()).begin-1) + "\t";
               }
               
               String newText = "fetch " + argNode.iter + " into " + argNode.varlist + ";";
               
               this.area.getBuffer().insert(pos, blankStr + newText);
               int offset = (blankStr + newText).length();
               
               this.UpdateStatPosition(this.bodyNode, pos + 1, offset);
               
               argNode.begin = pos + blankStr.length();
               argNode.end = argNode.begin + newText.length() - 1;
               
               ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, node, 0);
           }
           else
           {
               if(before)
               {
                   int pos = node.begin;
                   String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), pos-1);
                   String newText = "fetch " + argNode.iter + " into " + argNode.varlist + ";";
                   
                   this.area.getBuffer().insert(pos, newText + blankStr);
                   int offset = (blankStr + newText).length();
                   
                   this.UpdateStatPosition(this.bodyNode, pos, offset);
                   
                   argNode.begin = pos;
                   argNode.end = argNode.begin + newText.length() - 1;
                   
                   int index  = node.getParent().getIndex(node);
                   ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index);
               }
               else
               {
                   int pos = node.end+1;
                   String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), node.begin-1);
                   String newText = "fetch " + argNode.iter + " into " + argNode.varlist + ";";
                   
                   this.area.getBuffer().insert(pos, blankStr + newText);
                   int offset = (blankStr + newText).length();
                   
                   this.UpdateStatPosition(this.bodyNode, pos, offset);
                   
                   argNode.begin = pos + blankStr.length();
                   argNode.end = argNode.begin + newText.length() - 1;
                   
                   int index  = node.getParent().getIndex(node);
                   ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index+1); 
               }
           }
           
           TreeChanged(null);
        }
    }
    
    private void AddSignal(CommTreeNode node, boolean add, boolean before)
    {
        SignalEditFrm frm = new SignalEditFrm("", "Add command", this.area.frame, this.area.conn,this.PR_id);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           SignalTreeNode argNode = new SignalTreeNode("");
            
           argNode.paramlist = frm.name;
            
           String nname = "signal(";
            
           if (argNode.paramlist != null) 
           {
                if (argNode.paramlist.length() > 10)
                {
                    nname += argNode.paramlist.substring(0,7) + "...)";
                }
                else
                {
                    nname += argNode.paramlist + ")";
                }
           }
           argNode.setUserObject(nname);
             
           if (add)
           {
               int pos = this.FindInsertPos(node);
               String blankStr = "";
               
               if (node.getParent() == null || node.getParent().getParent() == null)
               {
                   blankStr = "\n\t";
               }
               else
               {
                    blankStr = "\n" + this.GetBlankoStr(this.area.getText(), ((CommTreeNode)node.getParent()).begin-1) + "\t";
               }
               
               String newText = "signal(" + argNode.paramlist + ");";
               
               this.area.getBuffer().insert(pos, blankStr + newText);
               int offset = (blankStr + newText).length();
               
               this.UpdateStatPosition(this.bodyNode, pos + 1, offset);
               
               argNode.begin = pos + blankStr.length();
               argNode.end = argNode.begin + newText.length() - 1;
               
               ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, node, 0);
           }
           else
           {
               if(before)
               {
                   int pos = node.begin;
                   String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), pos-1);
                   String newText = "signal(" + argNode.paramlist + ");";
                   
                   this.area.getBuffer().insert(pos, newText + blankStr);
                   int offset = (blankStr + newText).length();
                   
                   this.UpdateStatPosition(this.bodyNode, pos, offset);
                   
                   argNode.begin = pos;
                   argNode.end = argNode.begin + newText.length() - 1;
                   
                   int index  = node.getParent().getIndex(node);
                   ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index);
               }
               else
               {
                   int pos = node.end+1;
                   String blankStr = "\n" + this.GetBlankoStr(this.area.getText(), node.begin-1);
                   String newText = "signal(" + argNode.paramlist + ");";
                   
                   this.area.getBuffer().insert(pos, blankStr + newText);
                   int offset = (blankStr + newText).length();
                   
                   this.UpdateStatPosition(this.bodyNode, pos, offset);
                   
                   argNode.begin = pos + blankStr.length();
                   argNode.end = argNode.begin + newText.length() - 1;
                   
                   int index  = node.getParent().getIndex(node);
                   ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)node.getParent(), index+1); 
               }
           }
           
           TreeChanged(null);
        }
    }
    
    private void EditArgument( CommTreeNode node)
    {
        ArgDeclTreeNode argNode = (ArgDeclTreeNode)node;
        
        VarFunParamFrm frm = new VarFunParamFrm(argNode.argName, argNode.argDataType, "Edit parameter", this.area.frame, argNode.defaultValue, translateArgType(argNode.argType,0), this.area.conn, this.PR_id);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           int oldLen = argNode.end - argNode.begin + 1;
           argNode.argName = frm.name;
           argNode.argDataType = frm.domain;
           argNode.defaultValue = frm.dafaultValue;
           argNode.argType = this.translateArgType(frm.iodefault, 1);
           argNode.setUserObject(argNode.argName + " " + argNode.argType + " " + argNode.argDataType);
           
           String newText = argNode.argName;
           
           if (!argNode.argType.equals("IN"))
           {
                newText += " " + argNode.argType;
           }
           newText += " " + argNode.argDataType;
           
           if (!argNode.defaultValue.equals(""))
           {
                newText += " := " + argNode.defaultValue;
           }
           
           ((DefaultTreeModel)this.getModel()).reload(argNode);
           
           this.area.getBuffer().remove(argNode.begin, argNode.end - argNode.begin + 1);                
           this.area.getBuffer().insert(argNode.begin, newText);
           int offset = newText.length() - oldLen;
           argNode.end = argNode.begin + newText.length() - 1;
           this.UpdateArgVarPosition(argNode.begin, offset);
           this.UpdateStatPosition(this.bodyNode, argNode.begin, offset);
           
           TreeChanged(null);
           area.editor.BuildWithoutBuilidgTree();
        }
    }
    
    private void EditVariable(CommTreeNode node)
    {
        VarDeclTreeNode argNode = (VarDeclTreeNode)node;
        
        VarFunParamFrm frm = new VarFunParamFrm(argNode.varNameList, argNode.varType, "Edit variable", this.area.frame, argNode.defaultValue, "IN", this.area.conn, this.PR_id);
        
        frm.setVisible(true);
        
        if (frm.action == frm.SAVE)
        {
           int oldLen = argNode.end - argNode.begin + 1;
           argNode.varNameList = frm.name;
           argNode.varType = frm.domain;
           argNode.defaultValue = frm.dafaultValue;
           
            String varNamesListShort = argNode.varNameList;
            
            if (varNamesListShort.length() > 10)
            {
                varNamesListShort = varNamesListShort.substring(0,9) + "..";
            }
            
           argNode.setUserObject(varNamesListShort +  " " + argNode.varType);
           
           String newText = argNode.varNameList;
           
           newText += " " + argNode.varType;
           
           if (!argNode.defaultValue.equals(""))
           {
                newText += " := " + argNode.defaultValue;
           }
           
            newText += ";";
           ((DefaultTreeModel)this.getModel()).reload(argNode);
           
           this.area.getBuffer().remove(argNode.begin, argNode.end - argNode.begin + 1);                
           this.area.getBuffer().insert(argNode.begin, newText);
           int offset = newText.length() - oldLen;
           argNode.end = argNode.begin + newText.length() - 1;
           this.UpdateArgVarPosition(argNode.end+1, offset);
           this.UpdateStatPosition(this.bodyNode, argNode.begin, offset);
           
            TreeChanged(null);
            area.editor.BuildWithoutBuilidgTree();
        }
    }
    
    public void deleteActionPerformed(ActionEvent e) 
    {
        CommTreeNode node = (CommTreeNode)this.getSelectionPath().getLastPathComponent();
        String nodePStr = this.getSelectionPath().getParentPath().getLastPathComponent().toString();
        String nodeStr = this.getSelectionPath().getLastPathComponent().toString();
        
        if (node != null)
        {
            if(nodePStr != null && nodePStr.equalsIgnoreCase("Arguments"))
            {
                DeleteArgument(node);
                return;
            }
            
            if(nodePStr != null && nodePStr.equalsIgnoreCase("Variables"))
            {
                DeleteVariable(node);
                return;
            }
            
            if (node.toString().equals("Else"))
            {
                if (node.getParent() == null)
                {
                    return;
                }
                IfTreeNode ifNode = (IfTreeNode)node.getParent();
                this.area.getBuffer().remove(ifNode.start_else, ifNode.start_end_if - ifNode.start_else);                
                this.UpdateStatPosition(this.bodyNode, ifNode.start_else, -(ifNode.start_end_if - ifNode.start_else));                
                ((DefaultTreeModel)this.getModel()).removeNodeFromParent(node);
                ifNode.start_else = -1;
                TreeChanged(null);
            }
            else
            {
                if (node.toString().equals("Default"))
                {
                    if (node.getParent() == null)
                    {
                        return;
                    }
                    SwitchTreeNode switchNode = (SwitchTreeNode)node.getParent();
                    this.area.getBuffer().remove(switchNode.start_default, switchNode.start_end_switch - switchNode.start_default);                
                    this.UpdateStatPosition(this.bodyNode, switchNode.start_default, -(switchNode.start_end_switch - switchNode.start_default));                
                    ((DefaultTreeModel)this.getModel()).removeNodeFromParent(node);
                    switchNode.start_default = -1;
                    TreeChanged(null);
                }
                else
                {
                    this.area.getBuffer().remove(node.begin, node.end - node.begin +1);
                    ((DefaultTreeModel)this.getModel()).removeNodeFromParent(node);
                    this.UpdateStatPosition(this.bodyNode, node.begin, -(node.end - node.begin + 1));
                    TreeChanged(null);
                }
            }
            
            this.repaint();
        }
    }
    
    private void DeleteArgument( CommTreeNode node)
    {
        ArgDeclTreeNode argNode = (ArgDeclTreeNode)node;
        
        int commaPos = this.area.GetNextComma(argNode.begin, argNode.end);
        
        if (commaPos < 0)
        {
            argNode.begin = (-1)*commaPos;
        }
        else
        {
            if (commaPos > 0)
            {
                argNode.end = commaPos;
            }
        }
        
        this.area.getBuffer().remove(argNode.begin, argNode.end - argNode.begin + 1); 
        
        int offset = -(argNode.end - argNode.begin + 1);
        this.UpdateArgVarPosition(argNode.begin, offset);
        this.UpdateStatPosition(this.bodyNode, argNode.begin, offset);
        ((DefaultTreeModel)this.getModel()).removeNodeFromParent(node);
        TreeChanged(null);
        area.editor.BuildWithoutBuilidgTree();
    }
    
    private void DeleteVariable( CommTreeNode node)
    {
        VarDeclTreeNode argNode = (VarDeclTreeNode)node;
        
        this.area.getBuffer().remove(argNode.begin, argNode.end - argNode.begin + 1); 
        
        int offset = -(argNode.end - argNode.begin + 1);
        this.UpdateArgVarPosition(argNode.begin, offset);
        this.UpdateStatPosition(this.bodyNode, argNode.begin, offset);
        ((DefaultTreeModel)this.getModel()).removeNodeFromParent(node);
        TreeChanged(null);
        area.editor.BuildWithoutBuilidgTree();
    }
    
    public void selectActionPerformed(ActionEvent e) 
    {
        CommTreeNode node = (CommTreeNode)this.getSelectionPath().getLastPathComponent();
        
        if (node != null)
        {
            if (node.toString().equals("Else"))
            {
                if (node.getParent() == null)
                {
                    return;
                }
                IfTreeNode ifNode = (IfTreeNode)node.getParent();
                
                if (ifNode.start_else != -1)
                {
                    Selection.Range rang = new Selection.Range(ifNode.start_else, ifNode.start_end_if);
                    this.area.setCaretPosition(ifNode.start_else);
                    this.area.setSelection(rang);
                }
            }
            else
            {
                if (node.toString().equals("Default"))
                {
                    if (node.getParent() == null)
                    {
                        return;
                    }
                    SwitchTreeNode switchNode = (SwitchTreeNode)node.getParent();
                    
                    if (switchNode.start_default != -1)
                    {
                        Selection.Range rang = new Selection.Range(switchNode.start_default, switchNode.start_end_switch);
                        this.area.setCaretPosition(switchNode.start_default);
                        this.area.setSelection(rang);
                    }
                }
                else
                {
                    //System.out.println(this.area.getText().substring(node.begin, node.end + 1));
                    Selection.Range rang = new Selection.Range(node.begin, node.end+1);                    
                    this.area.setCaretPosition(node.begin);
                    this.area.setSelection(rang);
                }
            }
        }
    }
    
    public void moveUpActionPerformed(ActionEvent e) 
    {
        CommTreeNode node = (CommTreeNode)this.getSelectionPath().getLastPathComponent();
        
        String nodePStr = this.getSelectionPath().getParentPath().getLastPathComponent().toString();
        String nodeStr = this.getSelectionPath().getLastPathComponent().toString();
        
        if (nodePStr.equals("Arguments") || nodePStr.equals("Variables"))
        {
            this.MoveUpArgument(node);
            return;
        }
        else
        {
            this.MoveUpCommand(node);
            return;    
        }
    }
    
    public void moveDownActionPerformed(ActionEvent e) 
    {
        CommTreeNode node = (CommTreeNode)this.getSelectionPath().getLastPathComponent();
        
        String nodePStr = this.getSelectionPath().getParentPath().getLastPathComponent().toString();
        String nodeStr = this.getSelectionPath().getLastPathComponent().toString();
        
        if (nodePStr.equals("Arguments") || nodePStr.equals("Variables"))
        {
            this.MoveDownArgument(node);
        }
        else
        {
            this.MoveDownCommand(node);
        }
    }
    
    private void MoveUpCommand(CommTreeNode node)
    {
        if (node.getPreviousSibling() == null)
        {
            return;
        }
        
        TreePath path = this.getSelectionPath();
        
        CommTreeNode argNode = (CommTreeNode)node;
        CommTreeNode prevargNode = (CommTreeNode)node.getPreviousSibling();
        
        String argStr =  this.area.getText(argNode.begin, argNode.end - argNode.begin + 1);
        String prevArgStr =  this.area.getText(prevargNode.begin, prevargNode.end - prevargNode.begin +1);
        
        int totalLen = argNode.end - prevargNode.begin + 1;
        int ofs = argNode.begin - prevargNode.begin;
        
        this.area.getBuffer().remove(argNode.begin, argNode.end - argNode.begin + 1); 
        this.area.getBuffer().remove(prevargNode.begin, prevargNode.end - prevargNode.begin + 1); 
        this.area.getBuffer().insert(prevargNode.begin, argStr);
        
        int offset = argStr.length() - prevArgStr.length();
        int remain = totalLen - (argStr.length() + prevArgStr.length());
        int begTemp = argNode.begin;
        
        argNode.begin = prevargNode.begin;
        
        this.area.getBuffer().insert(begTemp + offset, prevArgStr);
        
        ((DefaultTreeModel)this.getModel()).removeNodeFromParent(node);  
        int index  = ((CommTreeNode)prevargNode.getParent()).getIndex(prevargNode);
        
        ((DefaultTreeModel)this.getModel()).insertNodeInto(node, (CommTreeNode)prevargNode.getParent(), index);
        this.setSelectionPath(path);
        
        this.UpdatePosition(argNode, argNode.begin + 1, -ofs);
        this.UpdatePosition(prevargNode, prevargNode.begin, argStr.length() + remain);
        
        TreeChanged(null);
    }
    
    private void CreateCutCopyPasteMenu(JPopupMenu popup)
    {
        CommTreeNode node = (CommTreeNode)this.getSelectionPath().getLastPathComponent();
        
        JMenuItem cutItem = new JMenuItem("Cut");
        
        cutItem.addActionListener(new ActionListener()
        {    
            public void actionPerformed(ActionEvent e) 
            {
                Cut();
            }
        });
        cutItem.setIcon(cutIcon);
        
        JMenuItem copyItem = new JMenuItem("Copy");
        
        copyItem.addActionListener(new ActionListener()
        {    
            public void actionPerformed(ActionEvent e) 
            {
                Copy();
            }
        });
        copyItem.setIcon(copyIcon);
        
        JMenuItem pasteItem = new JMenuItem("Paste");
        
        pasteItem.addActionListener(new ActionListener()
        {    
            public void actionPerformed(ActionEvent e) 
            {
                Paste();
            }
        });
        pasteItem.setIcon(pasteIcon);
        
        if (this.cutcopyNode == null)
        {   
            pasteItem.setEnabled(false);
        }
        
        String nodeStr = node.getUserObject().toString();
        
        if (nodeStr.equals("Body") || nodeStr.equals("Default") || nodeStr.equals("Else"))
        {
            popup.add(pasteItem);
        }
        else
        {
            popup.add(cutItem);
            popup.add(copyItem);
            popup.add(pasteItem);
        }
        
        popup.addSeparator();
    }
    
    private void CreateUndoRedoMenu(JPopupMenu popup)
    {
        JMenuItem undoItem = new JMenuItem("Undo");
        
        undoItem.addActionListener(new ActionListener()
        {    
            public void actionPerformed(ActionEvent e) 
            {
                Undo();
            }
        });
        undoItem.setIcon(undoIcon);
        
        if (this.undoPos < 1 )
        {
            undoItem.setEnabled(false);
        }
        popup.add(undoItem);
        
        JMenuItem redoItem = new JMenuItem("Redo");
        
        redoItem.addActionListener(new ActionListener()
        {    
            public void actionPerformed(ActionEvent e) 
            {
                Redo();
            }
        });
        redoItem.setIcon(redoIcon);
        
        if (this.undoPos == this.undoStack.size() - 1)
        {
            redoItem.setEnabled(false);
        }
        popup.add(redoItem);
        
        popup.addSeparator();
    }
    
    private void CreateAddCommnadMenu(JMenu addCommItem, boolean add, boolean before)
    {
        JMenuItem addAssItem = new JMenuItem("Assignment");
    
        if (add)
        {
            addAssItem.addActionListener(new ActionListener()
            {
    
                public void actionPerformed(ActionEvent e) 
                {
                    AddCommand(e, 2, true, false);
                }
            });
        }
        else
        {
            if (before)
            {
                addAssItem.addActionListener(new ActionListener()
                {
            
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 2, false, true);
                    }
                });
            }        
            else
            {
                addAssItem.addActionListener(new ActionListener()
                {
                
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 2, false, false);
                    }
                });
            }
        }
        addCommItem.add(addAssItem);
        
        JMenuItem ifItem = new JMenuItem("If");
        if (add)
        {
            ifItem.addActionListener(new ActionListener()
            {
        
                public void actionPerformed(ActionEvent e) 
                {
                    AddCommand(e, 3, true, false);
                }
            });
        }
        else
        {
            if (before)
            {
                ifItem.addActionListener(new ActionListener()
                {
            
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 3, false, true);
                    }
                });
            }        
            else
            {
                ifItem.addActionListener(new ActionListener()
                {
                
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 3, false, false);
                    }
                });
            }
        }
        
        addCommItem.add(ifItem);
        
        JMenuItem whileItem = new JMenuItem("While");
        if (add)
        {
            whileItem.addActionListener(new ActionListener()
            {
        
                public void actionPerformed(ActionEvent e) 
                {
                    AddCommand(e, 4, true, false);
                }
            });
        }
        else
        {
            if (before)
            {
                whileItem.addActionListener(new ActionListener()
                {
            
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 4, false, true);
                    }
                });
            }        
            else
            {
                whileItem.addActionListener(new ActionListener()
                {
                
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 4, false, false);
                    }
                });
            }
        }
        
        addCommItem.add(whileItem);
        
        JMenuItem repeatItem = new JMenuItem("Repeat");
        if (add)
        {
            repeatItem.addActionListener(new ActionListener()
            {
        
                public void actionPerformed(ActionEvent e) 
                {
                    AddCommand(e, 5, true, false);
                }
            });
        }
        else
        {
            if (before)
            {
                repeatItem.addActionListener(new ActionListener()
                {
            
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 5, false, true);
                    }
                });
            }        
            else
            {
                repeatItem.addActionListener(new ActionListener()
                {
                
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 5, false, false);
                    }
                });
            }
        }
        
        addCommItem.add(repeatItem);
        
        JMenuItem forItem = new JMenuItem("For");
        if (add)
        {
            forItem.addActionListener(new ActionListener()
            {
        
                public void actionPerformed(ActionEvent e) 
                {
                    AddCommand(e, 6, true, false);
                }
            });
        }
        else
        {
            if (before)
            {
                forItem.addActionListener(new ActionListener()
                {
            
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 6, false, true);
                    }
                });
            }        
            else
            {
                forItem.addActionListener(new ActionListener()
                {
                
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 6, false, false);
                    }
                });
            }
        }
        
        JMenuItem breakItem = new JMenuItem("Break");
        if (add)
        {
            breakItem.addActionListener(new ActionListener()
            {
        
                public void actionPerformed(ActionEvent e) 
                {
                    AddCommand(e, 7, true, false);
                }
            });
        }
        else
        {
            if (before)
            {
                breakItem.addActionListener(new ActionListener()
                {
            
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 7, false, true);
                    }
                });
            }        
            else
            {
                breakItem.addActionListener(new ActionListener()
                {
                
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 7, false, false);
                    }
                });
            }
        }
        
        addCommItem.add(breakItem);
        
        JMenuItem returnItem = new JMenuItem("Return");
        if (add)
        {
            returnItem.addActionListener(new ActionListener()
            {
        
                public void actionPerformed(ActionEvent e) 
                {
                    AddCommand(e, 8, true, false);
                }
            });
        }
        else
        {
            if (before)
            {
                returnItem.addActionListener(new ActionListener()
                {
            
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 8, false, true);
                    }
                });
            }        
            else
            {
                returnItem.addActionListener(new ActionListener()
                {
                
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 8, false, false);
                    }
                });
            }
        }
        
        addCommItem.add(returnItem);
        
        JMenuItem selectItem = new JMenuItem("Select");
        
        if (add)
        {
            selectItem.addActionListener(new ActionListener()
            {
        
                public void actionPerformed(ActionEvent e) 
                {
                    AddCommand(e, 9, true, false);
                }
            });
        }
        else
        {
            if (before)
            {
                selectItem.addActionListener(new ActionListener()
                {
            
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 9, false, true);
                    }
                });
            }        
            else
            {
                selectItem.addActionListener(new ActionListener()
                {
                
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 9, false, false);
                    }
                });
            }
        }
        
        addCommItem.add(selectItem);
        
        JMenuItem switchItem = new JMenuItem("Switch");
        
        if (add)
        {
            switchItem.addActionListener(new ActionListener()
            {
        
                public void actionPerformed(ActionEvent e) 
                {
                    AddCommand(e, 10, true, false);
                }
            });
        }
        else
        {
            if (before)
            {
                switchItem.addActionListener(new ActionListener()
                {
            
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 10, false, true);
                    }
                });
            }        
            else
            {
                switchItem.addActionListener(new ActionListener()
                {
                
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 10, false, false);
                    }
                });
            }
        }
        
        addCommItem.add(switchItem);
        
        JMenuItem printItem = new JMenuItem("Print");
        
        if (add)
        {
            printItem.addActionListener(new ActionListener()
            {
        
                public void actionPerformed(ActionEvent e) 
                {
                    AddCommand(e, 12, true, false);
                }
            });
        }
        else
        {
            if (before)
            {
                printItem.addActionListener(new ActionListener()
                {
            
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 12, false, true);
                    }
                });
            }        
            else
            {
                printItem.addActionListener(new ActionListener()
                {
                
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 12, false, false);
                    }
                });
            }
        }
        
        addCommItem.add(printItem);
        
        JMenuItem fetchItem = new JMenuItem("Fetch");
        
        if (add)
        {
            fetchItem.addActionListener(new ActionListener()
            {
        
                public void actionPerformed(ActionEvent e) 
                {
                    AddCommand(e, 13, true, false);
                }
            });
        }
        else
        {
            if (before)
            {
                fetchItem.addActionListener(new ActionListener()
                {
            
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 13, false, true);
                    }
                });
            }        
            else
            {
                fetchItem.addActionListener(new ActionListener()
                {
                
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 13, false, false);
                    }
                });
            }
        }
        
        addCommItem.add(fetchItem);
        
        JMenuItem signalItem = new JMenuItem("Signal");
        
        if (add)
        {
            signalItem.addActionListener(new ActionListener()
            {
        
                public void actionPerformed(ActionEvent e) 
                {
                    AddCommand(e, 14, true, false);
                }
            });
        }
        else
        {
            if (before)
            {
                signalItem.addActionListener(new ActionListener()
                {
            
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 14, false, true);
                    }
                });
            }        
            else
            {
                signalItem.addActionListener(new ActionListener()
                {
                
                    public void actionPerformed(ActionEvent e) 
                    {
                        AddCommand(e, 14, false, false);
                    }
                });
            }
        }
        
        addCommItem.add(signalItem);
    }
    
    private int FindInsertPos(CommTreeNode n)
    {
        if (n.getParent() == null || n.getParent().getParent() == null)
        {
            return this.area.GetProgBeginPos();
        }
        
        if (n.getUserObject().toString().equals("Else"))
        {
            IfTreeNode nNode = (IfTreeNode)n.getParent();
            return nNode.start_else + 4;
        }
        
        if (n.getUserObject().toString().equals("Default"))
        {
            SwitchTreeNode nNode = (SwitchTreeNode)n.getParent();
            return nNode.start_default + 7;
        }
        
        CommTreeNode node = (CommTreeNode)n.getParent();
        
        if (node.type == 3)
        {
            //ShowIfMenu(node, me);
            IfTreeNode nNode = (IfTreeNode)node;
            return nNode.start_then + 4;
        }
        
        if (node.type == 4)
        {
            //ShowWhileMenu(node, me);
            WhileTreeNode nNode = (WhileTreeNode)node;
            return nNode.start_begin + 5;
        }
        
        if (node.type == 5)
        {
            RepeatTreeNode nNode = (RepeatTreeNode)node;
            return nNode.begin + 6;
        }
        
        if (node.type == 6)
        {
            ForTreeNode nNode = (ForTreeNode)node;
            return nNode.start_begin + 5;
        }
        
        if (node.type == 11)
        {
             CaseTreeNode nNode = (CaseTreeNode)node;
             return nNode.start_col + 1;
        }
        
        return 0;
    }
    
    private void AddCommand(ActionEvent e, int commandCode, boolean add, boolean before)
    {
        CommTreeNode node = (CommTreeNode)this.getSelectionPath().getLastPathComponent();
        
        if (commandCode == 2)//assignment
        {
            this.AddAssign(node, add, before);
        }
        
        if (commandCode == 3)
        {
            this.AddIf(node, add, before);
        }
        
        if (commandCode == 4)
        {
            this.AddWhile(node, add, before);
        }
        
        if (commandCode == 5)
        {
            this.AddRepeat(node, add, before);
        }
        
        if (commandCode == 6)
        {
            this.AddForCommand(node, add, before);
        }
        
        if (commandCode == 7)
        {
            this.AddBreakCommand(node, add, before);
        }
        
        if (commandCode == 8)
        {
            this.AddReturn(node, add, before);
        }
        
        if (commandCode == 9)
        {
            this.AddSelect(node, add, before);
        }
        
        if (commandCode == 10)
        {
            this.AddSwitch(node, add, before);
        }
        
        if (commandCode == 12)
        {
            this.AddPrint(node, add, before);
        }
        
        if (commandCode == 13)
        {
            this.AddFetch(node, add, before);
        }
        
        if (commandCode == 14)
        {
            this.AddSignal(node, add, before);
        }
    }

    private void MoveDownCommand(CommTreeNode node)
    {
        if (node.getNextSibling() == null)
        {
            return;
        }
        
        TreePath path = this.getSelectionPath();
        
        CommTreeNode argNode = (CommTreeNode)node;
        CommTreeNode prevargNode = (CommTreeNode)node.getNextSibling();
        
        String argStr =  this.area.getText(argNode.begin, argNode.end - argNode.begin + 1);
        String prevArgStr =  this.area.getText(prevargNode.begin, prevargNode.end - prevargNode.begin +1);
        
        int totalLen = prevargNode.end - argNode.begin + 1;
        int ofs = argNode.begin - prevargNode.begin;
        
        this.area.getBuffer().remove(prevargNode.begin, prevargNode.end - prevargNode.begin + 1); 
        this.area.getBuffer().remove(argNode.begin, argNode.end - argNode.begin + 1); 
        
        this.area.getBuffer().insert(argNode.begin, prevArgStr);
        
        int remain = totalLen - (argStr.length() + prevArgStr.length());
        
        prevargNode.begin = argNode.begin;
        
        this.area.getBuffer().insert(prevargNode.begin + prevArgStr.length() + remain, argStr);
        
        ((DefaultTreeModel)this.getModel()).removeNodeFromParent(node);  
        int index  = ((CommTreeNode)prevargNode.getParent()).getIndex(prevargNode);
        
        ((DefaultTreeModel)this.getModel()).insertNodeInto(node, (CommTreeNode)prevargNode.getParent(), index+1);
        this.setSelectionPath(path);
        
        this.UpdatePosition(prevargNode, prevargNode.begin + 1, ofs);
        this.UpdatePosition(argNode, argNode.begin, prevArgStr.length() + remain);
        
        TreeChanged(null);
    }
    
    private void MoveUpArgument(CommTreeNode node)
    {
        if (node.getPreviousSibling() == null)
        {
            return;
        }
        
        TreePath path = this.getSelectionPath();
        
        CommTreeNode argNode = (CommTreeNode)node;
        CommTreeNode prevargNode = (CommTreeNode)node.getPreviousSibling();
        
        String argStr =  this.area.getText(argNode.begin, argNode.end - argNode.begin + 1);
        String prevArgStr =  this.area.getText(prevargNode.begin, prevargNode.end - prevargNode.begin +1);
        
        this.area.getBuffer().remove(argNode.begin, argNode.end - argNode.begin + 1); 
        this.area.getBuffer().remove(prevargNode.begin, prevargNode.end - prevargNode.begin + 1); 
        this.area.getBuffer().insert(prevargNode.begin, argStr);
        
        int offset = argStr.length() - prevArgStr.length();
        int begTemp = argNode.begin;
        
        argNode.begin = prevargNode.begin;
        argNode.end = argNode.begin + argStr.length() - 1;
        
        prevargNode.begin = begTemp + offset;
        prevargNode.end = prevargNode.begin + prevArgStr.length() - 1;    
        
        this.area.getBuffer().insert(prevargNode.begin, prevArgStr);
        
        ((DefaultTreeModel)this.getModel()).removeNodeFromParent(node);  
        int index  = ((CommTreeNode)prevargNode.getParent()).getIndex(prevargNode);
        
        ((DefaultTreeModel)this.getModel()).insertNodeInto(node, (CommTreeNode)prevargNode.getParent(), index);
        this.setSelectionPath(path);
        
        TreeChanged(null);
    }
    
    private void MoveDownArgument(CommTreeNode node)
    {
        if (node.getNextSibling() == null)
        {
            return;
        }
        
        TreePath path = this.getSelectionPath();
        
        CommTreeNode prevargNode = (CommTreeNode)node;
        CommTreeNode argNode = (CommTreeNode)node.getNextSibling();
        
        String argStr =  this.area.getText(argNode.begin, argNode.end - argNode.begin + 1);
        String prevArgStr =  this.area.getText(prevargNode.begin, prevargNode.end - prevargNode.begin +1);
        
        this.area.getBuffer().remove(argNode.begin, argNode.end - argNode.begin + 1); 
        this.area.getBuffer().remove(prevargNode.begin, prevargNode.end - prevargNode.begin + 1); 
        this.area.getBuffer().insert(prevargNode.begin, argStr);
        
        int offset = argStr.length() - prevArgStr.length();
        int begTemp = argNode.begin;
        
        argNode.begin = prevargNode.begin;
        argNode.end = argNode.begin + argStr.length() - 1;
        
        prevargNode.begin = begTemp + offset;
        prevargNode.end = prevargNode.begin + prevArgStr.length() - 1;    
        
        this.area.getBuffer().insert(prevargNode.begin, prevArgStr);
        
        ((DefaultTreeModel)this.getModel()).removeNodeFromParent(argNode);  
        int index  = ((CommTreeNode)prevargNode.getParent()).getIndex(prevargNode);
        System.out.println("index " + index);
        ((DefaultTreeModel)this.getModel()).insertNodeInto(argNode, (CommTreeNode)prevargNode.getParent(), index);
        this.setSelectionPath(path);
        
        TreeChanged(null);
    }
    
    private void LoadBorders(CommTreeNode node, Element xmlnode)
    {
        Point p = this.ParsePoint(xmlnode.getAttribute("Borders"));
        
        node.begin = p.x;
        node.end = p.y;
    }
    
    private void UpdateStatPosition(CommTreeNode bodyNode, int begin, int offset)
    {
        for(int i = 0; i < bodyNode.getChildCount(); i++)
        {
            this.UpdatePosition((CommTreeNode)bodyNode.getChildAt(i), begin, offset);
        }
    }
    
    private void UpdateArgVarPosition(int begin, int offset)
    {
        for(int i = 0; i < this.argsNode.getChildCount(); i++)
        {   
            CommTreeNode commNode = (CommTreeNode)this.argsNode.getChildAt(i);
            
            if(commNode.end < begin || commNode.begin == begin)
            {
                continue;
            }
            
            commNode.end += offset;
            
            if(commNode.begin >= begin)
            {
                commNode.begin += offset;
            }
        }
        
        for(int i = 0; i < this.varsNode.getChildCount(); i++)
        {   
            CommTreeNode commNode = (CommTreeNode)this.varsNode.getChildAt(i);
            
            if(commNode.end < begin)
            {
                continue;
            }
            
            commNode.end += offset;
            
            if(commNode.begin >= begin)
            {
                commNode.begin += offset;
            }
        }
    }
    
    private void UpdatePosition(CommTreeNode commNode, int begin, int offset)
    {
        if(commNode.end < begin)
        {
            return;
        }
        commNode.end += offset;
        
        if(commNode.begin >= begin)
        {
            commNode.begin += offset;
        }
        
        if (commNode.type == 3) //if
        {   
            if(((IfTreeNode)commNode).start_then >= begin)
            {
                ((IfTreeNode)commNode).start_then += offset;
            }
            
            if( (((IfTreeNode)commNode).start_else != -1) && ((IfTreeNode)commNode).start_else >= begin)
            {
                ((IfTreeNode)commNode).start_else += offset;
            }
            
            if(((IfTreeNode)commNode).start_end_if >= begin)
            {
                ((IfTreeNode)commNode).start_end_if += offset;
            }
            
            if (commNode.getChildCount() > 0)
            {
                CommTreeNode thenNode = (CommTreeNode)commNode.getChildAt(0);
                this.UpdateStatPosition(thenNode, begin, offset);
            }
            
            if (commNode.getChildCount() > 1)
            {
                CommTreeNode elseNode = (CommTreeNode)commNode.getChildAt(1);
                this.UpdateStatPosition(elseNode, begin, offset);
            }
        }
        
        if (commNode.type == 4) //while
        {   
            if(((WhileTreeNode)commNode).start_begin >= begin)
            {
                ((WhileTreeNode)commNode).start_begin += offset;
            }
            
            if (commNode.getChildCount() > 0)
            {
                CommTreeNode bNode = (CommTreeNode)commNode.getChildAt(0);
                this.UpdateStatPosition(bNode, begin, offset);
            }
        }
        
        if (commNode.type == 5) //repeat
        {   
            if(((RepeatTreeNode)commNode).start_until >= begin)
            {
                ((RepeatTreeNode)commNode).start_until += offset;
            }
            
            if(((RepeatTreeNode)commNode).start_endrepeat >= begin)
            {
                ((RepeatTreeNode)commNode).start_endrepeat += offset;
            }
            
            if (commNode.getChildCount() > 0)
            {
                CommTreeNode thenNode = (CommTreeNode)commNode.getChildAt(0);
                this.UpdateStatPosition(thenNode, begin, offset);
            }
        }
        
        if (commNode.type == 6) //if
        {   
            if(((ForTreeNode)commNode).start_begin >= begin)
            {
                ((ForTreeNode)commNode).start_begin += offset;
            }
            
            if (commNode.getChildCount() > 0)
            {
                CommTreeNode thenNode = (CommTreeNode)commNode.getChildAt(0);
                this.UpdateStatPosition(thenNode, begin, offset);
            }
        }
        
        if (commNode.type == 10) //switch
        {   
            SwitchTreeNode switchNode = (SwitchTreeNode)commNode;
            
            if((((SwitchTreeNode)commNode).start_default != -1) && (((SwitchTreeNode)commNode).start_default >= begin))
            {
                ((SwitchTreeNode)commNode).start_default += offset;
            }
            
            if(((SwitchTreeNode)commNode).start_end_switch >= begin)
            {
                ((SwitchTreeNode)commNode).start_end_switch += offset;
            }
            
            if(((SwitchTreeNode)commNode).end_cond >= begin)
            {
                ((SwitchTreeNode)commNode).end_cond += offset;
            }
            
            for(int i = 0; i < switchNode.getChildCount(); i++)
            {
                CommTreeNode childNode = (CommTreeNode)commNode.getChildAt(i);
                
                if (childNode.type == 11) //case
                {
                    UpdatePosition(childNode, begin, offset);
                }
                else
                {
                    //default
                    this.UpdateStatPosition(childNode, begin, offset);
                }
            }
        }
        
        if (commNode.type == 11) //switch
        {   
            CaseTreeNode caseNode = (CaseTreeNode)commNode;
            
            if(caseNode.start_col >= begin)
            {
                caseNode.start_col += offset;
            }
            
            if (caseNode.getChildCount() > 0)
            {
                CommTreeNode thenNode = (CommTreeNode)caseNode.getChildAt(0);
                this.UpdateStatPosition(thenNode, begin, offset);
            }
        }
    }
    
    private String GetBlankoStr(String input, int pos)
    {
        int i = pos; 
        String result = "";
        
        while(i >= 0)
        {
            if (input.charAt(i) == ' ')
            {
                result = result + " ";
                i = i - 1;
            }
            else
            {
                if (input.charAt(i) == '\t')
                {
                    result = result + "\t";
                    i = i - 1;
                }
                else
                {
                    break;
                }
            }
        }
        
        if (i == pos)
        {
            return " ";
        }
        return result;
    }
    
    private void LoadStatements(CommTreeNode statNode, Element statementNode)
    {
        try 
        {
            int len = statementNode.getChildNodes().getLength();
            
            for(int i = 0; i < len; i++)
            {
                Element sNode = (Element)statementNode.getChildNodes().item(i);
                
                if (sNode != null)
                {
                    String name = sNode.getNodeName();
                    
                    if (name.equals("assign"))
                    {
                        AssignTreeNode assTreeNode = new AssignTreeNode("Ass");
                        
                        if (sNode.getAttribute("VarName") != null)
                        {
                            assTreeNode.varName = sNode.getAttribute("VarName");
                        }
                        
                        if (sNode.getAttribute("VarValue") != null)
                        {
                            assTreeNode.value = sNode.getAttribute("VarValue");
                        }
                        
                        String nname = assTreeNode.varName + " := ";
                        
                        if (assTreeNode.value != null)
                        {
                            if(assTreeNode.value.length() > 10)
                            {
                                nname += assTreeNode.value.substring(0, 7) + "...";
                            }
                            else
                            {
                                nname += assTreeNode.value;
                            }
                        }
                        
                        assTreeNode.setUserObject(nname);
                        
                        this.LoadBorders(assTreeNode, sNode);
                        statNode.add(assTreeNode);
                        continue;
                    }
                    
                    if (name.equals("if"))
                    {
                        IfTreeNode ifTreeNode = new IfTreeNode("If");
                        this.LoadBorders(ifTreeNode, sNode);
                        statNode.add(ifTreeNode);
                        
                        if (sNode.getAttribute("Cond") != null)
                        {
                            ifTreeNode.cond = sNode.getAttribute("Cond");
                        }
                        
                        if (sNode.getAttribute("ThenStart") != null)
                        {
                            try
                            {
                                ifTreeNode.start_then = Integer.parseInt(sNode.getAttribute("ThenStart"));
                            }
                            catch(Exception ex)
                            {}
                        }
                        
                        if (sNode.getAttribute("EndIfStart") != null)
                        {
                            try
                            {
                                ifTreeNode.start_end_if = Integer.parseInt(sNode.getAttribute("EndIfStart"));
                            }
                            catch(Exception ex)
                            {}
                        }
                        
                        if (sNode.getAttribute("ElseStart") != null)
                        {
                            try
                            {
                                ifTreeNode.start_else = Integer.parseInt(sNode.getAttribute("ElseStart"));
                            }
                            catch(Exception ex)
                            {}
                        }
                        
                        String nname = "if ";
                        
                        if(ifTreeNode.cond != null)
                        {
                            if(ifTreeNode.cond.length() > 10)
                            {
                                nname += ifTreeNode.cond.substring(0, 7) + "...";
                            }
                            else
                            {
                                nname += ifTreeNode.cond;
                            }
                        }
                        
                        ifTreeNode.setUserObject(nname);
                        
                        NodeList list = sNode.getElementsByTagName("statements");
                        if (list.getLength() > 0)
                        {
                            Element ifStat = (Element)list.item(0);
                            CommTreeNode ifBodyNode = new CommTreeNode("Body");
                            ifTreeNode.add(ifBodyNode);
                            this.LoadStatements(ifBodyNode, ifStat);
                        }
                        
                        list = sNode.getElementsByTagName("else");
                        if (list.getLength() > 0)
                        {
                            Element elseStat = (Element)list.item(0);
                            CommTreeNode elseNode = new CommTreeNode("Else");
                            ifTreeNode.add(elseNode);
                            
                            if (elseStat.getFirstChild()!=null)
                            {
                                this.LoadStatements(elseNode, (Element)elseStat.getFirstChild());
                            }
                        }
                        continue;
                    }
                    
                    if (name.equals("while"))
                    {
                        WhileTreeNode whileTreeNode = new WhileTreeNode("While");
                        this.LoadBorders(whileTreeNode, sNode);
                        statNode.add(whileTreeNode);
                        
                        if (sNode.getAttribute("Cond") != null)
                        {
                            whileTreeNode.cond = sNode.getAttribute("Cond");
                        }
                        
                        if (sNode.getAttribute("BeginStart") != null)
                        {
                            try
                            {
                                whileTreeNode.start_begin = Integer.parseInt(sNode.getAttribute("BeginStart"));
                            }
                            catch(Exception ex)
                            {}
                        }
                        
                        String nname = "while ";
                        
                        if(whileTreeNode.cond != null)
                        {
                            if(whileTreeNode.cond.length() > 10)
                            {
                                nname += whileTreeNode.cond.substring(0, 7) + "...";
                            }
                            else
                            {
                                nname += whileTreeNode.cond;
                            }
                        }
                        
                        whileTreeNode.setUserObject(nname);
                        
                        NodeList list = sNode.getElementsByTagName("statements");
                        if (list.getLength() > 0)
                        {
                            Element ifStat = (Element)list.item(0);
                            CommTreeNode ifBodyNode = new CommTreeNode("Body");
                            whileTreeNode.add(ifBodyNode);
                            this.LoadStatements(ifBodyNode, ifStat);
                        }
                        continue;
                    }
                    
                    if (name.equals("repeat"))
                    {
                        RepeatTreeNode repeatTreeNode = new RepeatTreeNode("If");
                        this.LoadBorders(repeatTreeNode, sNode);
                        statNode.add(repeatTreeNode);
                        
                        if (sNode.getAttribute("Cond") != null)
                        {
                            repeatTreeNode.cond = sNode.getAttribute("Cond");
                        }
                        
                        if (sNode.getAttribute("UntilStart") != null)
                        {
                            try
                            {
                                repeatTreeNode.start_until = Integer.parseInt(sNode.getAttribute("UntilStart"));
                            }
                            catch(Exception ex)
                            {}
                        }
                        
                        if (sNode.getAttribute("EndRepeatStart") != null)
                        {
                            try
                            {
                                repeatTreeNode.start_endrepeat = Integer.parseInt(sNode.getAttribute("EndRepeatStart"));
                            }
                            catch(Exception ex)
                            {}
                        }
                        
                        String nname = "repeat until ";
                        
                        if(repeatTreeNode.cond != null)
                        {
                            if(repeatTreeNode.cond.length() > 10)
                            {
                                nname += repeatTreeNode.cond.substring(0, 7) + "...";
                            }
                            else
                            {
                                nname += repeatTreeNode.cond;
                            }
                        }
                        
                        repeatTreeNode.setUserObject(nname);
                        
                        NodeList list = sNode.getElementsByTagName("statements");
                        if (list.getLength() > 0)
                        {
                            Element ifStat = (Element)list.item(0);
                            CommTreeNode ifBodyNode = new CommTreeNode("Body");
                            repeatTreeNode.add(ifBodyNode);
                            this.LoadStatements(ifBodyNode, ifStat);
                        }
                        continue;
                    }
                    
                    if (name.equals("for"))
                    {
                        ForTreeNode forTreeNode = new ForTreeNode("for");
                        this.LoadBorders(forTreeNode, sNode);
                        statNode.add(forTreeNode);
                        
                        if (sNode.getAttribute("From") != null)
                        {
                            forTreeNode.from = sNode.getAttribute("From");
                        }
                        
                        if (sNode.getAttribute("To") != null)
                        {
                            forTreeNode.to = sNode.getAttribute("To");
                        }
                        
                        if (sNode.getAttribute("VarName") != null)
                        {
                            forTreeNode.varname = sNode.getAttribute("VarName");
                        }
                        
                        if (sNode.getAttribute("BeginStart") != null)
                        {
                            try
                            {
                                forTreeNode.start_begin = Integer.parseInt(sNode.getAttribute("BeginStart"));
                            }
                            catch(Exception ex)
                            {}
                        }
                        
                        String nname = "for ";
                        
                        if(forTreeNode.varname != null)
                        {
                            nname += forTreeNode.varname + " := ";
                            
                            if(forTreeNode.from != null)
                            {
                                nname += forTreeNode.from;    
                            }
                        }
                        
                        nname += " to ...";
                        forTreeNode.setUserObject(nname);
                        
                        NodeList list = sNode.getElementsByTagName("statements");
                        if (list.getLength() > 0)
                        {
                            Element ifStat = (Element)list.item(0);
                            CommTreeNode ifBodyNode = new CommTreeNode("Body");
                            forTreeNode.add(ifBodyNode);
                            this.LoadStatements(ifBodyNode, ifStat);
                        }
                        continue;
                    }
                    
                    if (name.equals("break"))
                    {
                        BreakTreeNode node = new BreakTreeNode("break");
                        this.LoadBorders(node, sNode);
                        statNode.add(node);
                        continue;
                    }
                    
                    if (name.equals("return"))
                    {
                        ReturnTreeNode node = new ReturnTreeNode("return");
                        this.LoadBorders(node, sNode);
                        statNode.add(node);
                        
                        if (sNode.getAttribute("Value") != null)
                        {
                            node.value = sNode.getAttribute("Value");
                        }                     
                        
                        String nname = "return ";
                        
                        if (node.value != null) 
                        {
                            if (node.value.length() > 10)
                            {
                                nname += node.value.substring(0,7) + "...";
                            }
                            else
                            {
                                nname += node.value;
                            }
                        }
                        node.setUserObject(nname);                        
                        continue;
                    }
                    
                    if (name.equals("select"))
                    {
                        SelectTreeNode node = new SelectTreeNode("select");
                        this.LoadBorders(node, sNode);
                        statNode.add(node);
                        
                        if (sNode.getAttribute("VarName") != null)
                        {
                            node.varName = sNode.getAttribute("VarName");
                        }                     
                        
                        if (sNode.getAttribute("FuncName") != null)
                        {
                            node.func = sNode.getAttribute("FuncName");
                        }
                        
                        if (sNode.getAttribute("From") != null)
                        {
                            node.from = sNode.getAttribute("From");
                        }
                        
                        String nname = "select " + node.func + " into ..";
                        node.setUserObject(nname);                        
                        continue;
                    }
                    
                    if (name.equals("switch"))
                    {
                        SwitchTreeNode node = new SwitchTreeNode("switch");
                        this.LoadBorders(node, sNode);
                        statNode.add(node);
                        
                        if (sNode.getAttribute("Cond") != null)
                        {
                            node.value = sNode.getAttribute("Cond");
                        }
                        
                        if (sNode.getAttribute("EndSwitchStart") != null)
                        {
                            try
                            {
                                node.start_end_switch = Integer.parseInt(sNode.getAttribute("EndSwitchStart"));
                            }
                            catch(Exception ex)
                            {}
                        }
                        
                        if (sNode.getAttribute("EndCond") != null)
                        {
                            try
                            {
                                node.end_cond = Integer.parseInt(sNode.getAttribute("EndCond"));
                            }
                            catch(Exception ex)
                            {}
                        }
                        
                        if (sNode.getAttribute("DefStart") != null)
                        {
                            try
                            {
                                node.start_default = Integer.parseInt(sNode.getAttribute("DefStart"));
                            }
                            catch(Exception ex)
                            {}
                        }
                        
                        String nname = "switch " + node.value + "";
                        node.setUserObject(nname);  
                        
                        NodeList list = sNode.getElementsByTagName("case");
                                                
                        for(int j =0; j < list.getLength(); j++)
                        {
                            Element caseStat = (Element)list.item(j);
                            CaseTreeNode caseNode = new CaseTreeNode("case");
                            this.LoadBorders(caseNode, caseStat);
                            node.add(caseNode);
                            
                            if (caseStat.getAttribute("Cond") != null)
                            {
                                caseNode.value = caseStat.getAttribute("Cond");
                            }
                            
                            if (caseStat.getAttribute("ColStart") != null)
                            {
                                try
                                {
                                    caseNode.start_col = Integer.parseInt(caseStat.getAttribute("ColStart"));
                                }
                                catch(Exception ex)
                                {}
                            }
                            
                            nname = "case " + caseNode.value + "";
                            caseNode.setUserObject(nname);  
                            
                            if (caseStat.getFirstChild()!=null)
                            {
                                CommTreeNode bNode = new CommTreeNode("Body");
                                caseNode.add(bNode);
                                
                                NodeList tempList = caseStat.getElementsByTagName("statements");
                                
                                if (tempList.getLength() > 0)
                                {
                                    this.LoadStatements(bNode, (Element)tempList.item(0));
                                }
                            }
                        }
                        
                        list = sNode.getElementsByTagName("default");
                                                
                        if (list.getLength() > 0)
                        {
                            Element defStat = (Element)list.item(0);
                            CommTreeNode defNode = new CommTreeNode("Default");
                            node.add(defNode);
                            
                            if (defStat.getFirstChild()!=null)
                            {
                                this.LoadStatements(defNode, (Element)defStat.getFirstChild());
                            }
                        }                    
                        continue;
                    }
                    
                    if (name.equals("debug"))
                    {
                        PrintTreeNode node = new PrintTreeNode("print");
                        this.LoadBorders(node, sNode);
                        statNode.add(node);
                        
                        if (sNode.getAttribute("Value") != null)
                        {
                            node.value = sNode.getAttribute("Value");
                        }                     
                        
                        String nname = "print ";
                        
                        if (node.value != null) 
                        {
                            if (node.value.length() > 10)
                            {
                                nname += node.value.substring(0,7) + "...";
                            }
                            else
                            {
                                nname += node.value;
                            }
                        }
                        node.setUserObject(nname);                        
                        continue;
                    }
                    
                    if (name.equals("fetch"))
                    {
                        FetchTreeNode node = new FetchTreeNode("fetch");
                        this.LoadBorders(node, sNode);
                        statNode.add(node);
                        
                        if (sNode.getAttribute("IterName") != null)
                        {
                            node.iter = sNode.getAttribute("IterName");
                        }                     
                        
                        if (sNode.getAttribute("VarList") != null)
                        {
                            node.varlist = sNode.getAttribute("VarList");
                        }   
                        String nname = "fetch ";
                        
                        if (node.iter != null) 
                        {
                            if (node.iter.length() > 10)
                            {
                                nname += node.iter.substring(0,7) + "...";
                            }
                            else
                            {
                                nname += node.iter;
                            }
                        }
                        node.setUserObject(nname);                        
                        continue;
                    }
                    
                    if (name.equals("signal"))
                    {
                        SignalTreeNode node = new SignalTreeNode("signal");
                        this.LoadBorders(node, sNode);
                        statNode.add(node);  
                        
                        if (sNode.getAttribute("Value") != null)
                        {
                            node.paramlist = sNode.getAttribute("Value");
                        }   
                        String nname = "signal(";
                        
                        if (node.paramlist != null) 
                        {
                            if (node.paramlist.length() > 10)
                            {
                                nname += node.paramlist.substring(0,7) + "...)";
                            }
                            else
                            {
                                nname += node.paramlist + ")";
                            }
                        }
                        node.setUserObject(nname);                        
                        continue;
                    }
                }
                
            }
        }
        catch(Exception e)
        {
        }
    }
    
    private void LoadArgs(Element funcNode)
    {
        try 
        {
            NodeList list = funcNode.getElementsByTagName("ARGS");
            
            if (list.getLength() > 0)
            {
                Element argsXmlNode = (Element)list.item(0);
                
                for(int i = 0; i < argsXmlNode.getChildNodes().getLength(); i++)
                {
                    Element argNode = (Element)argsXmlNode.getChildNodes().item(i);
                    
                    if (argNode.getChildNodes().getLength() > 1)
                    {
                        Element nameNode = (Element)argNode.getChildNodes().item(0);
                        Element typeNode = (Element)argNode.getChildNodes().item(1);
                        String type = "IN";
                        
                        if (argNode.getAttribute("Type") != null && !argNode.getAttribute("Type").equals(""))
                        {
                            type = argNode.getAttribute("Type");
                        }
                        
                        if (nameNode.getFirstChild() != null && typeNode.getFirstChild() != null)
                        {
                            String text = nameNode.getFirstChild().getNodeName() + " " + type + " " + typeNode.getFirstChild().getNodeName();
                            
                            ArgDeclTreeNode argTreeNode = new ArgDeclTreeNode(text);
                            argTreeNode.argName = nameNode.getFirstChild().getNodeName();
                            argTreeNode.argType = type;
                            argTreeNode.argDataType = typeNode.getFirstChild().getNodeName();
                            argTreeNode.defaultValue = "";
                            
                            if (argNode.getAttribute("DefVal") != null)
                            {
                                argTreeNode.defaultValue = argNode.getAttribute("DefVal");
                            }
                            
                            Point p = this.ParsePoint(argNode.getAttribute("Borders"));
                            
                            argTreeNode.begin = p.x;
                            argTreeNode.end = p.y;
                            
                            this.argsNode.add(argTreeNode);
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void LoadVars(Element funcNode)
    {
        try 
        {
            NodeList list = funcNode.getElementsByTagName("VARS");
            
            if (list.getLength() > 0)
            {
                Element argsXmlNode = (Element)list.item(0);
                
                for(int i = 0; i < argsXmlNode.getChildNodes().getLength(); i++)
                {
                    Element argNode = (Element)argsXmlNode.getChildNodes().item(i);
                    
                    if (argNode.getChildNodes().getLength() > 1)
                    {
                        Element nameNode = (Element)argNode.getChildNodes().item(0);
                        Element typeNode = (Element)argNode.getChildNodes().item(1);
                        
                        String varNamesList = "";
                        
                        if (nameNode != null)
                        {
                            for(int j = 0; j < nameNode.getChildNodes().getLength(); j++)
                            {
                                Element nNode = (Element)nameNode.getChildNodes().item(j);
                                
                                if(varNamesList.equals(""))
                                {
                                    varNamesList = nNode.getNodeName();
                                }
                                else
                                {
                                    varNamesList += "," + nNode.getNodeName();
                                }
                            }
                        }

                        String varNamesListShort = varNamesList;
                        
                        if (varNamesListShort.length() > 10)
                        {
                            varNamesListShort = varNamesListShort.substring(0,9) + "..";
                        }
                        
                        if (nameNode.getFirstChild() != null && typeNode.getFirstChild() != null)
                        {
                            String text = varNamesListShort  + " " + typeNode.getFirstChild().getNodeName();
                            
                            VarDeclTreeNode varTreeNode = new VarDeclTreeNode(text);
                            varTreeNode.varNameList = varNamesList;
                            varTreeNode.varType = typeNode.getFirstChild().getNodeName();
                            varTreeNode.defaultValue = "";
                            
                            if (argNode.getAttribute("DefVal") != null)
                            {
                                varTreeNode.defaultValue = argNode.getAttribute("DefVal");
                            }
                            
                            Point p = this.ParsePoint(argNode.getAttribute("Borders"));
                            
                            varTreeNode.begin = p.x;
                            varTreeNode.end = p.y;
                            
                            this.varsNode.add(varTreeNode);
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private Point ParsePoint(String value)
    {
        Point p = new Point(0,0);
        
        if (value == null || value.equals(""))
        {
            return p;    
        }
        String points[] = value.split(",");
        
        if (points.length != 2)
        {
            return p;
        }
        
        try         
        {
            p.x = Integer.parseInt(points[0]);
            p.y = Integer.parseInt(points[1]);
        }
        catch(Exception e)
        {
        }
        
        return p;
    }
    
    private class CommTreeNode extends DefaultMutableTreeNode
    {
        public int begin = 0;
        public int end = 0;
        public int type = -1;//0 - argdecl, 1- vardec, 2-ass, 3-if
        
        public CommTreeNode()
        {
            super();
        }
        
        public CommTreeNode(String value)
        {
            super(value);
        }
        
        public boolean isRootBody()
        {
            return this.type == -2;
        }
        
        public CommTreeNode clone()
        {
            CommTreeNode newNode = new CommTreeNode();
            newNode.setUserObject(this.getUserObject());
            newNode.type = this.type;
            newNode.begin = this.begin;
            newNode.end = this.end;
            
            for(int i = 0; i < this.getChildCount(); i++)
            {
                CommTreeNode childnode = (CommTreeNode)this.getChildAt(i);
                newNode.add((CommTreeNode)(childnode.clone()));
            }
            return newNode;
        }
    }
    
    private class  ArgDeclTreeNode extends CommTreeNode
    {
        public String argName = "";
        public String argType = "";
        public String argDataType = "";
        public String defaultValue = "";
        
        public ArgDeclTreeNode()
        {
            super();
            this.type = 0;
        }
        
        public ArgDeclTreeNode(String value)
        {
            super(value);
            this.type = 0;
        }
        
         public CommTreeNode clone()
         {
             ArgDeclTreeNode newNode = new ArgDeclTreeNode();
             newNode.setUserObject(this.getUserObject());
             newNode.type = this.type;
             newNode.begin = this.begin;
             newNode.end = this.end;
             newNode.argName = this.argName;
             newNode.argDataType = this.argDataType;
             newNode.argType = this.argDataType;
             newNode.defaultValue = this.defaultValue;
             
             for(int i = 0; i < this.getChildCount(); i++)
             {
                 CommTreeNode childnode = (CommTreeNode)this.getChildAt(i);
                 newNode.add((CommTreeNode)(childnode.clone()));
             }
             return newNode;
         }
    }
    
    private class  VarDeclTreeNode extends CommTreeNode
    {
        public String varNameList = "";
        public String varType = "";
        public String defaultValue = "";
        
        public VarDeclTreeNode()
        {
            super();
            this.type = 1;
        }
        
        public VarDeclTreeNode(String value)
        {
            super(value);
            this.type = 1;
        }
        
        public CommTreeNode clone()
        {
            VarDeclTreeNode newNode = new VarDeclTreeNode();
            newNode.setUserObject(this.getUserObject());
            newNode.type = this.type;
            newNode.begin = this.begin;
            newNode.end = this.end;
            newNode.varNameList = this.varNameList;
            newNode.varType = this.varType;
            newNode.defaultValue = this.defaultValue;
            
            for(int i = 0; i < this.getChildCount(); i++)
            {
                CommTreeNode childnode = (CommTreeNode)this.getChildAt(i);
                newNode.add((CommTreeNode)(childnode.clone()));
            }
            return newNode;
        }
    }
    
    private class AssignTreeNode extends CommTreeNode
    {
        public String varName = "";
        public String value = "";
        
        public AssignTreeNode()
        {
            super();
            this.type = 2;
        }
        
        public AssignTreeNode(String value)
        {
            super(value);
            this.type = 2;
        }
        
        public CommTreeNode clone()
        {
            AssignTreeNode newNode = new AssignTreeNode();
            newNode.setUserObject(this.getUserObject());
            newNode.type = this.type;
            newNode.begin = this.begin;
            newNode.end = this.end;
            newNode.varName = this.varName;
            newNode.value = this.value;
            
            for(int i = 0; i < this.getChildCount(); i++)
            {
                CommTreeNode childnode = (CommTreeNode)this.getChildAt(i);
                newNode.add((CommTreeNode)(childnode.clone()));
            }
            return newNode;
        }
    }
    
    private class IfTreeNode extends CommTreeNode
    {
        public String cond = "";
        public int start_then;
        public int start_else = -1;
        public int start_end_if;
        
        public IfTreeNode()
        {
            super();
            this.type = 3;
        }
        
        public IfTreeNode(String value)
        {
            super(value);
            this.type = 3;
        }
        
        public CommTreeNode clone()
        {
            IfTreeNode newNode = new IfTreeNode();
            newNode.setUserObject(this.getUserObject());
            newNode.type = this.type;
            newNode.begin = this.begin;
            newNode.end = this.end;
            newNode.cond = this.cond;
            newNode.start_then = this.start_then;
            newNode.start_else = this.start_else;
            newNode.start_end_if = this.start_end_if;
            
            for(int i = 0; i < this.getChildCount(); i++)
            {
                CommTreeNode childnode = (CommTreeNode)this.getChildAt(i);
                newNode.add((CommTreeNode)(childnode.clone()));
            }
            return newNode;
        }
    }
    
    private class WhileTreeNode extends CommTreeNode
    {
        public String cond = "";
        public int start_begin;
        
        public WhileTreeNode()
        {
            super();
            this.type = 4;
        }
        
        public WhileTreeNode(String value)
        {
            super(value);
            this.type = 4;
        }
        
        public CommTreeNode clone()
        {
            WhileTreeNode newNode = new WhileTreeNode();
            newNode.setUserObject(this.getUserObject());
            newNode.type = this.type;
            newNode.begin = this.begin;
            newNode.end = this.end;
            newNode.cond = this.cond;
            newNode.start_begin = this.start_begin;
            
            for(int i = 0; i < this.getChildCount(); i++)
            {
                CommTreeNode childnode = (CommTreeNode)this.getChildAt(i);
                newNode.add((CommTreeNode)(childnode.clone()));
            }
            return newNode;
        }
    }
    
    private class RepeatTreeNode extends CommTreeNode
    {
        public String cond = "";
        public int start_until;
        public int start_endrepeat;
        
        public RepeatTreeNode()
        {
            super();
            this.type = 5;
        }
        
        public RepeatTreeNode(String value)
        {
            super(value);
            this.type = 5;
        }
        
        public CommTreeNode clone()
        {
            RepeatTreeNode newNode = new RepeatTreeNode();
            newNode.setUserObject(this.getUserObject());
            newNode.type = this.type;
            newNode.begin = this.begin;
            newNode.end = this.end;
            newNode.cond = this.cond;
            newNode.start_until = this.start_until;
            newNode.start_endrepeat = this.start_endrepeat;
            
            for(int i = 0; i < this.getChildCount(); i++)
            {
                CommTreeNode childnode = (CommTreeNode)this.getChildAt(i);
                newNode.add((CommTreeNode)(childnode.clone()));
            }
            return newNode;
        }
    }
    
    private class ForTreeNode extends CommTreeNode
    {
        public String varname = "";
        public String from = "";
        public String to = "";
        public int start_begin;
        
        public ForTreeNode()
        {
            super();
            this.type = 6;
        }
        
        public ForTreeNode(String value)
        {
            super(value);
            this.type = 6;
        }
        
        public CommTreeNode clone()
        {
            ForTreeNode newNode = new ForTreeNode();
            newNode.setUserObject(this.getUserObject());
            newNode.type = this.type;
            newNode.begin = this.begin;
            newNode.end = this.end;
            newNode.varname = this.varname;
            newNode.from = this.from;
            newNode.to = this.to;
            newNode.start_begin = this.start_begin;
            
            for(int i = 0; i < this.getChildCount(); i++)
            {
                CommTreeNode childnode = (CommTreeNode)this.getChildAt(i);
                newNode.add((CommTreeNode)(childnode.clone()));
            }
            return newNode;
        }
    }
    
    private class BreakTreeNode extends CommTreeNode
    {
        public BreakTreeNode()
        {
            super();
            this.type = 7;
        }
        
        public BreakTreeNode(String value)
        {
            super(value);
            this.type = 7;
        }
        
        public CommTreeNode clone()
        {
            BreakTreeNode newNode = new BreakTreeNode();
            newNode.setUserObject(this.getUserObject());
            newNode.type = this.type;
            newNode.begin = this.begin;
            newNode.end = this.end;
            
            for(int i = 0; i < this.getChildCount(); i++)
            {
                CommTreeNode childnode = (CommTreeNode)this.getChildAt(i);
                newNode.add((CommTreeNode)(childnode.clone()));
            }
            return newNode;
        }
    }
    
    private class ReturnTreeNode extends CommTreeNode
    {
        String value;
        public ReturnTreeNode()
        {
            super();
            this.type = 8;
        }
        
        public ReturnTreeNode(String value)
        {
            super(value);
            this.type = 8;
        }
        
        public CommTreeNode clone()
        {
            ReturnTreeNode newNode = new ReturnTreeNode();
            newNode.setUserObject(this.getUserObject());
            newNode.type = this.type;
            newNode.begin = this.begin;
            newNode.end = this.end;
            newNode.value = this.value;
            
            for(int i = 0; i < this.getChildCount(); i++)
            {
                CommTreeNode childnode = (CommTreeNode)this.getChildAt(i);
                newNode.add((CommTreeNode)(childnode.clone()));
            }
            return newNode;
        }
    }
    
    private class SelectTreeNode extends CommTreeNode
    {
        String varName;
        String func;
        String from;
        
        public SelectTreeNode()
        {
            super();
            this.type = 9;
        }
        
        public SelectTreeNode(String value)
        {
            super(value);
            this.type = 9;
        }
        
        public CommTreeNode clone()
        {
            SelectTreeNode newNode = new SelectTreeNode();
            newNode.setUserObject(this.getUserObject());
            newNode.type = this.type;
            newNode.begin = this.begin;
            newNode.end = this.end;
            newNode.varName = this.varName;
            newNode.func = this.func;
            newNode.from = this.from;
            
            for(int i = 0; i < this.getChildCount(); i++)
            {
                CommTreeNode childnode = (CommTreeNode)this.getChildAt(i);
                newNode.add((CommTreeNode)(childnode.clone()));
            }
            return newNode;
        }
    }
    
    private class SwitchTreeNode extends CommTreeNode
    {
        String value;
        int start_default;
        int start_end_switch;
        int end_cond;
        
        public SwitchTreeNode()
        {
            super();
            this.type = 10;
            this.start_default = -1;
        }
        
        public SwitchTreeNode(String value)
        {
            super(value);
            this.type = 10;
            this.start_default = -1;
        }
        
        public CommTreeNode clone()
        {
            SwitchTreeNode newNode = new SwitchTreeNode();
            newNode.setUserObject(this.getUserObject());
            newNode.type = this.type;
            newNode.begin = this.begin;
            newNode.end = this.end;
            newNode.value = this.value;
            newNode.start_default = this.start_default;
            newNode.start_end_switch = this.start_end_switch;
            newNode.end_cond = this.end_cond;
            
            for(int i = 0; i < this.getChildCount(); i++)
            {
                CommTreeNode childnode = (CommTreeNode)this.getChildAt(i);
                newNode.add((CommTreeNode)(childnode.clone()));
            }
            return newNode;
        }
    }
    
    private class CaseTreeNode extends CommTreeNode
    {
        String value;
        int start_col;
        
        public CaseTreeNode()
        {
            super();
            this.type = 11;
        }
        
        public CaseTreeNode(String value)
        {
            super(value);
            this.type = 11;
        }
        
        public CommTreeNode clone()
        {
            CaseTreeNode newNode = new CaseTreeNode();
            newNode.setUserObject(this.getUserObject());
            newNode.type = this.type;
            newNode.begin = this.begin;
            newNode.end = this.end;
            newNode.value = this.value;
            newNode.start_col = this.start_col;
            
            for(int i = 0; i < this.getChildCount(); i++)
            {
                CommTreeNode childnode = (CommTreeNode)this.getChildAt(i);
                newNode.add((CommTreeNode)(childnode.clone()));
            }
            return newNode;
        }
    }
    
    private class PrintTreeNode extends CommTreeNode
    {
        String value;
        int start_col;
        
        public PrintTreeNode()
        {
            super();
            this.type = 12;
        }
        
        public PrintTreeNode(String value)
        {
            super(value);
            this.type = 12;
        }
        
        public CommTreeNode clone()
        {
            PrintTreeNode newNode = new PrintTreeNode();
            newNode.setUserObject(this.getUserObject());
            newNode.type = this.type;
            newNode.begin = this.begin;
            newNode.end = this.end;
            newNode.value = this.value;
            newNode.start_col = this.start_col;
            return newNode;
        }
    }
    
    private class FetchTreeNode extends CommTreeNode
    {
        String iter;
        String varlist;
        int start_col;
        
        public FetchTreeNode()
        {
            super();
            this.type = 13;
        }
        
        public FetchTreeNode(String value)
        {
            super(value);
            this.type = 13;
        }
        
        public CommTreeNode clone()
        {
            FetchTreeNode newNode = new FetchTreeNode();
            newNode.setUserObject(this.getUserObject());
            newNode.type = this.type;
            newNode.begin = this.begin;
            newNode.end = this.end;
            newNode.iter = this.iter;
            newNode.varlist = this.varlist;
            newNode.start_col = this.start_col;
            return newNode;
        }
    }
    
    private class SignalTreeNode extends CommTreeNode
    {
        String paramlist;
        int start_col;
        
        public SignalTreeNode()
        {
            super();
            this.type = 14;
        }
        
        public SignalTreeNode(String value)
        {
            super(value);
            this.type = 14;
        }
        
        public CommTreeNode clone()
        {
            SignalTreeNode newNode = new SignalTreeNode();
            newNode.setUserObject(this.getUserObject());
            newNode.type = this.type;
            newNode.begin = this.begin;
            newNode.end = this.end;
            newNode.paramlist = this.paramlist;
            newNode.start_col = this.start_col;
            return newNode;
        }
    }
    
    private class CommTreeCellRenderer extends  JLabel implements  TreeCellRenderer
    {
         private ImageIcon[]     iiscImage;
         private boolean selected;
         
         public CommTreeCellRenderer()
         {
             // Ucitaj ikonice za drvo
             iiscImage= new ImageIcon[6];
             iiscImage[0] = new ImageIcon(IISFrameMain.class.getResource("icons/folder.gif"));
             iiscImage[1] = new ImageIcon(IISFrameMain.class.getResource("icons/function.gif"));
             iiscImage[2] = new ImageIcon(IISFrameMain.class.getResource("icons/param.gif"));
             iiscImage[3] = new ImageIcon(IISFrameMain.class.getResource("icons/lai.gif"));
         }
     
         public Component getTreeCellRendererComponent( JTree tree,
                                               Object value, boolean bSelected, boolean bExpanded,
                                               boolean bLeaf, int iRow, boolean bHasFocus )
         {
             // Find out which node we are rendering and get its text
             DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
             Object[]    Path =  node.getUserObjectPath();
             String      labelText = (String)node.getUserObject();
              
             setText( labelText );
             setFont(new Font("SansSerif",0,11));
             ///setForeground(Color.black);
             selected = bSelected;
             
             if( Path == null || Path.length == 1 || Path.length == 0)
             {
                 setIcon( iiscImage[1] );
                 return this;
             }
             
             if( Path[Path.length - 1] == null )
             {
                 setIcon( iiscImage[3] );
                 return this;
             }
             
             if(Path[Path.length - 1].toString() == "Arguments" || Path[Path.length -1].toString()=="Variables" || Path[Path.length -1].toString()=="Body" || Path[Path.length -1].toString()=="Else" || Path[Path.length -1].toString()=="Default")
             {
                 setIcon( iiscImage[0] );
                 return this;
             }
             
             if(Path[Path.length - 2].toString() == "Arguments")
             {
                 setIcon( iiscImage[2] );
                 return this;
             }
             
             if(Path[Path.length - 2].toString() == "Variables")
             {
                 setIcon( iiscImage[2] );
                 return this;
             }
             
             if(Path[Path.length - 2].toString() == "Body" || Path[Path.length - 2].toString() == "Else" || Path[Path.length - 2].toString() == "Default")
             {
                 setIcon( iiscImage[3] );
                 return this;
             }
             
             setIcon( iiscImage[3] );
             return this;
         }
         
         public void paint( Graphics g )
         {
             Color               bColor;
           // Set the correct background color
            if (selected)
            {
               bColor = SystemColor.textHighlight;
               setForeground(Color.white);
            }
            else
            {
               bColor = Color.white;
               setForeground(Color.black);
            }
            g.setColor(bColor);
            g.fillRect( 0, 0, getWidth() - 1, getHeight() - 1 );
       
            super.paint( g );
         }     
     }
     
    public class AssignFrm extends JDialog
    {
        public String name;
        public String domain;
        public String dafaultValue;
        public String iodefault;
        public JLabel nameLbl = new JLabel("Name");
        public JLabel defaultValueLbl = new JLabel("Value");
        public JTextField nameTxt = new JTextField("");
        public JTextField defValTxt = new JTextField("");
        public JButton saveBtn = new JButton("OK");
        public JButton cancelBtn = new JButton("Cancel");
        public int SAVE = 0;
        public int CANCEL = 1;
        public int action;
        public int id = -1;
        public Connection connection = null;
        public Frame parentS = null;
        
        public AssignFrm(String p_name, String p_domain, String title, JFrame parent,String p_defVal,String io_val,Connection con, int PR_id)
        {
            super(parent, title, true);
            this.id = PR_id;
            parentS = parent;
            connection = con;
            name = p_name;
            domain = p_domain;
            dafaultValue = p_defVal;
            iodefault = io_val;
            
            setSize(285, 125);
            getContentPane().setLayout(null);
            setResizable(false);
            action = CANCEL;
            
            nameLbl.setBounds(10, 10, 80, 20);
            nameTxt.setBounds(90, 10, 180, 20);
            nameTxt.setText(name);

            defaultValueLbl.setBounds(10, 35, 80, 20);
            defValTxt.setBounds(90, 35, 180, 20);
            defValTxt.setText(dafaultValue);
            
            saveBtn.setBounds(55, 65, 70, 25);
            cancelBtn.setBounds(160, 65, 70, 25);
            saveBtn.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                saveBtn_actionPerformed(e);
              }
            });
            
            cancelBtn.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                cancelBtn_actionPerformed(e);
              }
            });  
           
            getContentPane().add(nameLbl);
            getContentPane().add(defaultValueLbl);
            getContentPane().add(nameTxt);
            getContentPane().add(defValTxt);
            getContentPane().add(saveBtn);
            getContentPane().add(cancelBtn);
                      
            Settings.Center(this);
        }
        
        private void saveBtn_actionPerformed(ActionEvent e)
        {
            action = SAVE;
            name = nameTxt.getText();
            dafaultValue = defValTxt.getText();
            
            String result = ParseAssignment(name, dafaultValue);
            
            if (!result.equals(""))
            {
                JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.OK_OPTION);
                return;
            }
            
            this.setVisible(false);
        }
        
        private void cancelBtn_actionPerformed(ActionEvent e)
        {
            action = CANCEL;
            setVisible(false);
        }
    }
    
    public class ConditionFrm extends JDialog
    {
        public String name;
        public String domain;
        public String dafaultValue;
        public String iodefault;
        public JLabel defaultValueLbl = new JLabel("Condition");
        public JTextField defValTxt = new JTextField("");
        public JButton saveBtn = new JButton("OK");
        public JButton cancelBtn = new JButton("Cancel");
        public int SAVE = 0;
        public int CANCEL = 1;
        public int action;
        public int id = -1;
        public Connection connection = null;
        public Frame parentS = null;
        
        public ConditionFrm(String p_name, String p_domain, String title, JFrame parent,String p_defVal,String io_val,Connection con, int PR_id)
        {
            super(parent, title, true);
            this.id = PR_id;
            parentS = parent;
            connection = con;
            name = p_name;
            domain = p_domain;
            dafaultValue = p_defVal;
            iodefault = io_val;
            
            setSize(285, 105);
            getContentPane().setLayout(null);
            setResizable(false);
            action = CANCEL;
            
            defaultValueLbl.setBounds(10, 10, 80, 20);
            defValTxt.setBounds(90, 10, 180, 20);
            defValTxt.setText(dafaultValue);
            
            saveBtn.setBounds(55, 45, 70, 25);
            cancelBtn.setBounds(160, 45, 70, 25);
            saveBtn.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                saveBtn_actionPerformed(e);
              }
            });
            
            cancelBtn.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                cancelBtn_actionPerformed(e);
              }
            });  
           
            getContentPane().add(defaultValueLbl);
            getContentPane().add(defValTxt);
            getContentPane().add(saveBtn);
            getContentPane().add(cancelBtn);
                      
            Settings.Center(this);
        }
        
        private void saveBtn_actionPerformed(ActionEvent e)
        {
            action = SAVE;
            dafaultValue = defValTxt.getText();
            
            String result = ParseBooleanExpression(dafaultValue);
            
            if (!result.equals(""))
            {
                JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.OK_OPTION);
                return;
            }
            
            this.setVisible(false);
        }
        
        private void cancelBtn_actionPerformed(ActionEvent e)
        {
            action = CANCEL;
            setVisible(false);
        }
    }

    public class CommValueFrm extends JDialog
    {
        public String name;
        public String domain;
        public String dafaultValue;
        public String iodefault;
        public JLabel defaultValueLbl = new JLabel("Value");
        public JTextField defValTxt = new JTextField("");
        public JButton saveBtn = new JButton("OK");
        public JButton cancelBtn = new JButton("Cancel");
        public int SAVE = 0;
        public int CANCEL = 1;
        public int action;
        public int id = -1;
        public Connection connection = null;
        public Frame parentS = null;
        private int option = 0;
        
        public CommValueFrm(String p_name, String p_domain, String title, JFrame parent,String p_defVal,String io_val,Connection con, int PR_id, int option)
        {
            super(parent, title, true);
            this.id = PR_id;
            parentS = parent;
            connection = con;
            name = p_name;
            domain = p_domain;
            dafaultValue = p_defVal;
            iodefault = io_val;
            this.option = option;
            
            setSize(285, 105);
            getContentPane().setLayout(null);
            setResizable(false);
            action = CANCEL;
            
            defaultValueLbl.setBounds(10, 10, 80, 20);
            defValTxt.setBounds(90, 10, 180, 20);
            defValTxt.setText(dafaultValue);
            
            saveBtn.setBounds(55, 45, 70, 25);
            cancelBtn.setBounds(160, 45, 70, 25);
            saveBtn.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                saveBtn_actionPerformed(e);
              }
            });
            
            cancelBtn.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                cancelBtn_actionPerformed(e);
              }
            });  
           
            getContentPane().add(defaultValueLbl);
            getContentPane().add(defValTxt);
            getContentPane().add(saveBtn);
            getContentPane().add(cancelBtn);
                      
            Settings.Center(this);
        }
        
        private void saveBtn_actionPerformed(ActionEvent e)
        {
            action = SAVE;
            dafaultValue = defValTxt.getText();
            
            if (option == 0)
            {
                String result = ParseReturnValue(dafaultValue);
                
                if (!result.equals(""))
                {
                    JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.OK_OPTION);
                    return;
                }
            }
            else
            {
                if (option == 1)
                {
                    String result = ParseSwitchValue(dafaultValue);
                    
                    if (!result.equals(""))
                    {
                        JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.OK_OPTION);
                        return;
                    }
                }
                else
                {
                    if (option == 2)
                    {
                        String result = ParseCaseValue(dafaultValue);
                        
                        if (!result.equals(""))
                        {
                            JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.OK_OPTION);
                            return;
                        }
                    }
                    else
                    {
                        if (option == 3)
                        {
                            String result = ParsePrintValue(dafaultValue);
                            
                            if (!result.equals(""))
                            {
                                JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.OK_OPTION);
                                return;
                            }
                        }
                    }
                }
            }
            
            this.setVisible(false);
        }
        
        private void cancelBtn_actionPerformed(ActionEvent e)
        {
            action = CANCEL;
            setVisible(false);
        }
    }
    
    public class ForEditFrm extends JDialog
    {
        public String name;
        public String fromValue;
        public String toValue;
        public JLabel nameLbl = new JLabel("Var Name");
        public JLabel fromValueLbl = new JLabel("From");
        public JLabel toValueLbl = new JLabel("To");
        public JTextField nameTxt = new JTextField("");
        public JTextField fromTxt = new JTextField("");
        public JTextField toTxt = new JTextField("");
        public JButton saveBtn = new JButton("OK");
        public JButton cancelBtn = new JButton("Cancel");
        public int SAVE = 0;
        public int CANCEL = 1;
        public int action;
        public int id = -1;
        public Connection connection = null;
        public Frame parentS = null;
        private boolean isVar = false;
        
        public ForEditFrm(String p_name, String p_domain, String title, JFrame parent,String p_fromVal,String to_val,Connection con, int PR_id)
        {
            super(parent, title, true);
            
            if (title.endsWith("variable"))
            {
                  this.isVar = true;
            }
            this.id = PR_id;
            parentS = parent;
            connection = con;
            name = p_name;
            fromValue = p_fromVal;
            toValue = to_val;
            
            setSize(285, 180);
            getContentPane().setLayout(null);
            setResizable(false);
            action = CANCEL;
            
            nameLbl.setBounds(10, 10, 80, 20);
            nameTxt.setBounds(90, 10, 180, 20);
            nameTxt.setText(name);
                  

            fromValueLbl.setBounds(10, 40, 80, 20);
            fromTxt.setBounds(90, 40, 180, 20);
            fromTxt.setText(fromValue);
            
            this.toValueLbl.setBounds(10, 70, 80, 20);
            toTxt.setBounds(90, 70, 180, 20);
            toTxt.setText(to_val);
            
            saveBtn.setBounds(55, 115, 70, 25);
            cancelBtn.setBounds(160, 115, 70, 25);
            saveBtn.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                saveBtn_actionPerformed(e);
              }
            });
            
            cancelBtn.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                cancelBtn_actionPerformed(e);
              }
            });   
           
            getContentPane().add(nameLbl);
            getContentPane().add(fromValueLbl);
            getContentPane().add(toValueLbl);
            getContentPane().add(nameTxt);
            getContentPane().add(fromTxt);
            getContentPane().add(toTxt);
            getContentPane().add(saveBtn);
            getContentPane().add(cancelBtn);
            
            Settings.Center(this);
        }
        
        
        private void saveBtn_actionPerformed(ActionEvent e)
        {
            action = SAVE;
            name = nameTxt.getText();
            fromValue = fromTxt.getText();
            toValue = toTxt.getText();
            
            String result = ParseForComm(name, fromValue, toValue);
            
            if (!result.equals(""))
            {
                JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.OK_OPTION);
                return;
            }
            
            this.setVisible(false);
        }
        
        private void cancelBtn_actionPerformed(ActionEvent e)
        {
            action = CANCEL;
            setVisible(false);
        }
    }
    
    public class SelectEditFrm extends JDialog
    {
        public String name;
        public String func;
        public String fromValue;
        public JLabel nameLbl = new JLabel("Var Name");
        public JLabel funNameLbl = new JLabel("Function");
        public JLabel fromValueLbl = new JLabel("From");
        public JTextField nameTxt = new JTextField("");
        public JComboBox funcCombo = new JComboBox();
        public JTextField toTxt = new JTextField("");
        public JButton saveBtn = new JButton("OK");
        public JButton cancelBtn = new JButton("Cancel");
        public int SAVE = 0;
        public int CANCEL = 1;
        public int action;
        public int id = -1;
        public Connection connection = null;
        public Frame parentS = null;
        private boolean isVar = false;
        
        public SelectEditFrm(String p_name, String p_domain, String title, JFrame parent,String p_funcname,String p_fromval,Connection con, int PR_id)
        {
            super(parent, title, true);
            
            if (title.endsWith("variable"))
            {
                  this.isVar = true;
            }
            this.id = PR_id;
            parentS = parent;
            connection = con;
            name = p_name;
            func = p_funcname;
            fromValue = p_fromval;
            
            setSize(285, 180);
            getContentPane().setLayout(null);
            setResizable(false);
            action = CANCEL;
            
            nameLbl.setBounds(10, 10, 80, 20);
            nameTxt.setBounds(90, 10, 180, 20);
            nameTxt.setText(name);
            
            funNameLbl.setBounds(10, 40, 80, 20);
            funcCombo.setBounds(90, 40, 180, 20);
            funcCombo.addItem("COUNT");
            funcCombo.addItem("DISTINCT_COUNT");
            funcCombo.addItem("SUM");
            funcCombo.addItem("AVG");
            funcCombo.addItem("MIN");
            funcCombo.addItem("MAX");
            funcCombo.setSelectedItem(func.toUpperCase(Locale.US));
            this.fromValueLbl.setBounds(10, 70, 80, 20);
            toTxt.setBounds(90, 70, 180, 20);
            toTxt.setText(p_fromval);
            
            saveBtn.setBounds(55, 115, 70, 25);
            cancelBtn.setBounds(160, 115, 70, 25);
            saveBtn.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                saveBtn_actionPerformed(e);
              }
            });
            
            cancelBtn.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                cancelBtn_actionPerformed(e);
              }
            });   
           
            getContentPane().add(nameLbl);
            getContentPane().add(funNameLbl);
            getContentPane().add(fromValueLbl);
            getContentPane().add(nameTxt);
            getContentPane().add(funcCombo);
            getContentPane().add(toTxt);
            getContentPane().add(saveBtn);
            getContentPane().add(cancelBtn);
            
            Settings.Center(this);
        }
        
        
        private void saveBtn_actionPerformed(ActionEvent e)
        {
            action = SAVE;
            name = nameTxt.getText();
            func = funcCombo.getSelectedItem().toString();
            fromValue = toTxt.getText();
            
            String result = ParseSelect(name, func, fromValue);
            
            if (!result.equals(""))
            {
                JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.OK_OPTION);
                return;
            }
            
            this.setVisible(false);
        }
        
        private void cancelBtn_actionPerformed(ActionEvent e)
        {
            action = CANCEL;
            setVisible(false);
        }
    }

    public class FetchEditFrm extends JDialog
    {
        public String name;
        public String func;
        public String fromValue;
        public JLabel nameLbl = new JLabel("Iterator");
        public JLabel fromValueLbl = new JLabel("Into");
        public JTextField nameTxt = new JTextField("");
        public JTextField toTxt = new JTextField("");
        public JButton saveBtn = new JButton("OK");
        public JButton cancelBtn = new JButton("Cancel");
        public int SAVE = 0;
        public int CANCEL = 1;
        public int action;
        public int id = -1;
        public Connection connection = null;
        public Frame parentS = null;
        private boolean isVar = false;
        
        public FetchEditFrm(String p_name, String p_domain, String title, JFrame parent,String p_funcname,String p_fromval,Connection con, int PR_id)
        {
            super(parent, title, true);
            
            if (title.endsWith("variable"))
            {
                  this.isVar = true;
            }
            this.id = PR_id;
            parentS = parent;
            connection = con;
            name = p_name;
            func = p_funcname;
            fromValue = p_fromval;
            
            setSize(285, 150);
            getContentPane().setLayout(null);
            setResizable(false);
            action = CANCEL;
            
            nameLbl.setBounds(10, 10, 80, 20);
            nameTxt.setBounds(90, 10, 180, 20);
            nameTxt.setText(name);
            
            this.fromValueLbl.setBounds(10, 40, 80, 20);
            toTxt.setBounds(90, 40, 180, 20);
            toTxt.setText(p_fromval);
            
            saveBtn.setBounds(55, 85, 70, 25);
            cancelBtn.setBounds(160, 85, 70, 25);
            saveBtn.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                saveBtn_actionPerformed(e);
              }
            });
            
            cancelBtn.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                cancelBtn_actionPerformed(e);
              }
            });   
           
            getContentPane().add(nameLbl);
            getContentPane().add(fromValueLbl);
            getContentPane().add(nameTxt);
            getContentPane().add(toTxt);
            getContentPane().add(saveBtn);
            getContentPane().add(cancelBtn);
            
            Settings.Center(this);
        }
        
        
        private void saveBtn_actionPerformed(ActionEvent e)
        {
            action = SAVE;
            name = nameTxt.getText();
            fromValue = toTxt.getText();
            
            String result = ParseFetch(name, fromValue);
            
            if (!result.equals(""))
            {
                JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.OK_OPTION);
                return;
            }
            
            this.setVisible(false);
        }
        
        private void cancelBtn_actionPerformed(ActionEvent e)
        {
            action = CANCEL;
            setVisible(false);
        }
    }
    
    public class SignalEditFrm extends JDialog
    {
        public String name;
        public String func;
        public String fromValue;
        public JLabel nameLbl = new JLabel("List");
        public JLabel fromValueLbl = new JLabel("Into");
        public JTextField nameTxt = new JTextField("");
        public JTextField toTxt = new JTextField("");
        public JButton saveBtn = new JButton("OK");
        public JButton cancelBtn = new JButton("Cancel");
        public int SAVE = 0;
        public int CANCEL = 1;
        public int action;
        public int id = -1;
        public Connection connection = null;
        public Frame parentS = null;
        private boolean isVar = false;
        
        public SignalEditFrm(String p_name, String title, JFrame parent,Connection con, int PR_id)
        {
            super(parent, title, true);
            
            this.id = PR_id;
            parentS = parent;
            connection = con;
            name = p_name;
            
            setSize(285, 120);
            getContentPane().setLayout(null);
            setResizable(false);
            action = CANCEL;
            
            nameLbl.setBounds(10, 10, 80, 20);
            nameTxt.setBounds(90, 10, 180, 20);
            nameTxt.setText(name);
            
            saveBtn.setBounds(55, 55, 70, 25);
            cancelBtn.setBounds(160, 55, 70, 25);
            saveBtn.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                saveBtn_actionPerformed(e);
              }
            });
            
            cancelBtn.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                cancelBtn_actionPerformed(e);
              }
            });   
           
            getContentPane().add(nameLbl);
            getContentPane().add(fromValueLbl);
            getContentPane().add(nameTxt);
            getContentPane().add(toTxt);
            getContentPane().add(saveBtn);
            getContentPane().add(cancelBtn);
            
            Settings.Center(this);
        }
        
        
        private void saveBtn_actionPerformed(ActionEvent e)
        {
            action = SAVE;
            name = nameTxt.getText();
            fromValue = toTxt.getText();
            
            String result = ParseSignal(name);
            
            if (!result.equals(""))
            {
                JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.OK_OPTION);
                return;
            }
            
            this.setVisible(false);
        }
        
        private void cancelBtn_actionPerformed(ActionEvent e)
        {
            action = CANCEL;
            setVisible(false);
        }
    }
    
    public class VarFunParamFrm extends JDialog
    {
        public String name;
        public String domain;
        public String dafaultValue;
        public String iodefault;
        public JLabel nameLbl = new JLabel("Name");
        public JLabel domainLbl = new JLabel("Domain");
        public JLabel defaultValueLbl = new JLabel("Default Value");
        public JLabel inoutLbl = new JLabel("In/Out Type");
        public JTextField nameTxt = new JTextField("");
        public JTextField defValTxt = new JTextField("");
        public JComboBox cmbiout = new JComboBox();
        public JButton saveBtn = new JButton("OK");
        public JButton cancelBtn = new JButton("Cancel");
        public JButton domainSelect = new JButton("...");
        public JComboBox domainCombo = new JComboBox();
        public int SAVE = 0;
        public int CANCEL = 1;
        public int action;
        public int id = -1;
        public Connection connection = null;
        public Frame parentS = null;
        private boolean isVar = false;
        
        public VarFunParamFrm(String p_name, String p_domain, String title, JFrame parent,String p_defVal,String io_val,Connection con, int PR_id)
        {
            super(parent, title, true);
            
            if (title.endsWith("variable"))
            {
                  this.isVar = true;
            }
            this.id = PR_id;
            parentS = parent;
            connection = con;
            name = p_name;
            domain = p_domain;
            dafaultValue = p_defVal;
            iodefault = io_val;
            
            setSize(285, 210);
            getContentPane().setLayout(null);
            setResizable(false);
            action = CANCEL;
            
            nameLbl.setBounds(10, 10, 80, 20);
            nameTxt.setBounds(90, 10, 180, 20);
            nameTxt.setText(name);
            
            domainLbl.setBounds(10, 35, 80, 20);
            domainCombo.setBounds(90,35,150,20);
            domainSelect.setBounds(245,35,25,20);
            
            try
            {
                ResultSet rs;
                JDBCQuery query=new JDBCQuery(connection);
                //System.out.println("select Dom_mnem from IISC_DOMAIN where PR_id="+tree.ID + " order by dom_mnem");
                rs=query.select("select Dom_mnem from IISC_DOMAIN where PR_id="+ this.id + " order by dom_mnem");
                  
                while (rs.next())
                {
                    domainCombo.addItem(rs.getString("Dom_mnem"));
                }
                
                if (this.isVar)
                {
                    rs=query.select("select PT_mnemonic from IISC_PRIMITIVE_TYPE order by PT_mnemonic");
                      
                    while (rs.next())
                    {
                        domainCombo.addItem(rs.getString("PT_mnemonic"));
                    }
                    
                    domainCombo.addItem("ITERATOR");
                }
                
                domainCombo.setSelectedItem(domain);
                
                query.Close();          
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
      

            defaultValueLbl.setBounds(10, 60, 80, 20);
            defValTxt.setBounds(90, 60, 180, 20);
            defValTxt.setText(dafaultValue);
            
            inoutLbl.setBounds(10,85,80,20);
            cmbiout.setBounds(90,85,180,20);
            cmbiout.addItem("In");
            cmbiout.addItem("Out");
            cmbiout.addItem("In/Out");
            cmbiout.setSelectedItem(io_val);
            
            saveBtn.setBounds(55, 115, 70, 25);
            cancelBtn.setBounds(160, 115, 70, 25);
            saveBtn.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                saveBtn_actionPerformed(e);
              }
            });
            
            cancelBtn.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                cancelBtn_actionPerformed(e);
              }
            });
           
            domainSelect.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                domainSelect_actionPerformed(e);
              }
            });         
           
            getContentPane().add(nameLbl);
            getContentPane().add(domainLbl);
            getContentPane().add(defaultValueLbl);
            getContentPane().add(nameTxt);
            getContentPane().add(defValTxt);
            getContentPane().add(domainCombo);
            getContentPane().add(saveBtn);
            getContentPane().add(cancelBtn);
            getContentPane().add(cmbiout);
            getContentPane().add(inoutLbl);
            getContentPane().add(domainSelect);
            
            if (this.isVar)
            {
                getContentPane().remove(cmbiout);
                saveBtn.setBounds(55, 95, 70, 25);
                cancelBtn.setBounds(160, 95, 70, 25);
                setSize(285, 160);
                getContentPane().remove(inoutLbl);
            }
            
            Settings.Center(this);
        }
        private void domainSelect_actionPerformed(ActionEvent e)
        {
            SearchTable ptype=new SearchTable((Frame)getParent(),"Select User Define Domain",true,connection,String.valueOf(id),"","",0);
            ptype.item = this.domainCombo;
            Settings.Center(ptype);
            if (this.isVar)
            {
                ptype.type="Domain4";
            }
            else
            {
              ptype.type="Domain4";
            }
            ptype.owner=this;
            ptype.setVisible(true);          
        }
        
        private void saveBtn_actionPerformed(ActionEvent e)
        {
            action = SAVE;
            name = nameTxt.getText();
            domain = domainCombo.getSelectedItem().toString();
            dafaultValue = defValTxt.getText();
            iodefault = cmbiout.getSelectedItem().toString();
            this.setVisible(false);
        }
        
        private void cancelBtn_actionPerformed(ActionEvent e)
        {
            action = CANCEL;
            setVisible(false);
        }
    }
}


