package iisc;

import javax.swing.tree.DefaultMutableTreeNode;

public class DefaultMutableTreeNodeEditor extends DefaultMutableTreeNode{
        public int pozition = -1;
        public DefaultMutableTreeNodeEditor(int poz,Object obj){
            super(obj);
            pozition = poz;
        }
    }
