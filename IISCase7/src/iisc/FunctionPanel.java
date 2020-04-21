package iisc;

import iisc.CheckConstraintExpressionEditor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

import java.awt.event.MouseMotionAdapter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;


public class FunctionPanel extends JPanel {

    public int i = -1;
    public JButton jButton1 = new JButton();
    public JComboBox jComboBox1 = new JComboBox();
    public JLabel jLabel1 = new JLabel();
    public JTextArea jTextArea1 = new JTextArea();
    public FunctionEditor my;
    public boolean jcombot = false;
    private boolean enabledPopup1 = true;
    
    private ComboBoxRenderer render = new ComboBoxRenderer();


    private ImageIcon blockIcon = new ImageIcon(IISFrameMain.class.getResource("icons/block2.gif"));
    private ImageIcon forIcon = new ImageIcon(IISFrameMain.class.getResource("icons/for.gif"));
    private ImageIcon whileIcon = new ImageIcon(IISFrameMain.class.getResource("icons/while.gif"));
    private ImageIcon doWhileIcon = new ImageIcon(IISFrameMain.class.getResource("icons/dowhile.gif"));    
    private ImageIcon ifIcon = new ImageIcon(IISFrameMain.class.getResource("icons/if.gif"));
    private ImageIcon tryIcon = new ImageIcon(IISFrameMain.class.getResource("icons/try.gif"));
    private ImageIcon catchIcon = new ImageIcon(IISFrameMain.class.getResource("icons/catch.gif"));
    private ImageIcon finallyIcon = new ImageIcon(IISFrameMain.class.getResource("icons/finally.gif"));
    private ImageIcon inBlockIcon = new ImageIcon(expPanel.class.getResource("icons/inblock.gif"));
    private ImageIcon thenIcon = new ImageIcon(expPanel.class.getResource("icons/then.gif"));
    private ImageIcon elseIcon = new ImageIcon(expPanel.class.getResource("icons/else.gif"));    
    private ImageIcon elseifIcon = new ImageIcon(expPanel.class.getResource("icons/elseif.gif"));  
    private ImageIcon funIcon = new ImageIcon(expPanel.class.getResource("icons/fung.gif"));      
    private ImageIcon argsBlock = new ImageIcon(expPanel.class.getResource("icons/argsBlock.gif"));   

    private ImageIcon down = new ImageIcon(expPanel.class.getResource("icons/down2.gif"));
    private ImageIcon up = new ImageIcon(expPanel.class.getResource("icons/up2.gif"));
    private ImageIcon erase = new ImageIcon(expPanel.class.getResource("icons/erase.gif"));  
    private ImageIcon cut = new ImageIcon(expPanel.class.getResource("icons/cut.gif"));  
    private ImageIcon copy = new ImageIcon(expPanel.class.getResource("icons/copy.gif"));  
    private ImageIcon paste = new ImageIcon(expPanel.class.getResource("icons/paste.gif"));
    private ImageIcon param = new ImageIcon(expPanel.class.getResource("icons/param.gif"));
    private ImageIcon functions2 = new ImageIcon(expPanel.class.getResource("icons/functions2.gif"));
    private ImageIcon declaration = new ImageIcon(expPanel.class.getResource("icons/declaration.gif"));
    

    private JPopupMenu jPopupMenu1 = new JPopupMenu();
    private JMenuItem copyMI = new JMenuItem();
    private JMenuItem cutMI = new JMenuItem();
    private JMenuItem deleteMI = new JMenuItem();
    private JMenuItem pasteMI = new JMenuItem();
    private JPopupMenu jPopupMenu2 = new JPopupMenu();
    //private JMenuItem jMenuItem10 = new JMenuItem();
    public JLabel lblPosition = new JLabel();
    private DropDownList ddList = null;

    public int getI(){
        return i;
    }
    
    public void setI(int a){
        i = a;
    }

    public FunctionPanel(int a,FunctionEditor b) {
        try {
            i = a;
            my = b;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout( null );
        this.setSize(new Dimension(312, 49));
        this.setBackground(new Color(247, 247, 247));
        this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        this.setToolTipText("null");
        this.addComponentListener(new ComponentAdapter() {
                    public void componentResized(ComponentEvent e) {
                        this_componentResized(e);
                    }
                });
        this.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        this_mouseClicked(e);
                    }

                    public void mouseEntered(MouseEvent e) {
                        this_mouseEntered(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        this_mouseExited(e);
                    }

                    public void mouseReleased(MouseEvent e) {
                        this_mouseReleased(e);
                    }

                    public void mousePressed(MouseEvent e) {
                        this_mousePressed(e);
                    }
                });
        this.addMouseMotionListener(new MouseMotionAdapter() {
                    public void mouseDragged(MouseEvent e) {
                        this_mouseDragged(e);
                    }
                });
        jButton1.setText("-");
        jButton1.setBounds(new Rectangle(5, 5, 15, 40));
        jButton1.setMargin(new Insets(1, 1, 0, 0));
        jButton1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButton1_actionPerformed(e);
                    }
                });
        jComboBox1.setBounds(new Rectangle(0, 0, 150, 16));
        jLabel1.setBounds(new Rectangle(25, 3, 160, 15));
        jLabel1.setFont(new Font("Tahoma", 1, 11));
        jLabel1.setBackground(new Color(255, 33, 33));
        jLabel1.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        jLabel1_mouseEntered(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        jLabel1_mouseExited(e);
                    }

                    public void mouseClicked(MouseEvent e) {
                        jLabel1_mouseClicked(e);
                    }

                    public void mousePressed(MouseEvent e) {
                        jLabel1_mousePressed(e);
                    }

                    public void mouseReleased(MouseEvent e) {
                        jLabel1_mouseReleased(e);
                    }
                });
        jLabel1.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        jLabel1_propertyChange(e);
                    }
                });
        jLabel1.addMouseMotionListener(new MouseMotionAdapter() {
                    public void mouseDragged(MouseEvent e) {
                        jLabel1_mouseDragged(e);
                    }
                });
        jTextArea1.setBounds(new Rectangle(150, 0, 145, 15));
        jTextArea1.setOpaque(false);
        jTextArea1.setBorder(BorderFactory.createLineBorder(new Color(150,150,150), 1));
        this.setBorder(BorderFactory.createLineBorder(new Color(150,150,150), 1));
        jTextArea1.setFont(new Font("SansSerif", 0, 11));
        jTextArea1.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        jTextArea1_mouseClicked(e);
                    }

                    public void mouseEntered(MouseEvent e) {
                        jTextArea1_mouseEntered(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        jTextArea1_mouseExited(e);
                    }
                });
        jTextArea1.addKeyListener(new KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        jTextArea1_keyReleased(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        jTextArea1_keyPressed(e);
                    }
                });
        jTextArea1.addMouseMotionListener(new MouseMotionAdapter() {
                    public void mouseDragged(MouseEvent e) {
                        jTextArea1_mouseDragged(e);
                    }
                });
        jPopupMenu1.setLabel("jPopupMenu1");
        copyMI.setText("Copy");
        copyMI.setIcon(copy);
        copyMI.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem1_actionPerformed(e);
                    }
                });
        cutMI.setText("Cut");
        cutMI.setIcon(cut);
        cutMI.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem2_actionPerformed(e);
                    }
                });
        deleteMI.setText("Delete");
        deleteMI.setIcon(erase);
        deleteMI.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem3_actionPerformed(e);
                    }
                });
        pasteMI.setText("Paste");
        pasteMI.setIcon(paste);
        pasteMI.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem4_actionPerformed(e);
                    }
                });
        jPopupMenu2.setLabel("jPopupMenu2");
        //jMenuItem10.setText("Convert");
        /*jMenuItem10.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem10_actionPerformed(e);
                    }
                });
        */
        lblPosition.setBounds(new Rectangle(295, 0, 15, 15));
        lblPosition.setBackground(new Color(231, 231, 231));
        lblPosition.setToolTipText("Position");
        this.add(lblPosition, null);
        this.add(jTextArea1, null);
        this.add(jLabel1, null);
        this.add(jComboBox1, null);
        this.add(jButton1, null);
        jPopupMenu1.add(cutMI);
        jPopupMenu1.add(copyMI);
        jPopupMenu1.add(pasteMI);
        jPopupMenu1.addSeparator();
        jPopupMenu1.add(deleteMI);
        jPopupMenu1.addSeparator();
        //jPopupMenu1.add(jMenuItem10);
        jComboBox1.setRenderer(render);
        jComboBox1.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        jComboBox1_mouseEntered(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        jComboBox1_mouseExited(e);
                    }

                    public void mousePressed(MouseEvent e) {
                        jComboBox1_mousePressed(e);
                    }

                    public void mouseReleased(MouseEvent e) {
                        jComboBox1_mouseReleased(e);
                    }

                    public void mouseClicked(MouseEvent e) {
                        jComboBox1_mouseClicked(e);
                    }
                });
        jComboBox1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jComboBox1_actionPerformed(e);
                    }
                });
        jComboBox1.addMouseMotionListener(new MouseMotionAdapter() {
                    public void mouseDragged(MouseEvent e) {
                        jComboBox1_mouseDragged(e);
                    }
                });
                
    }

    private void this_componentResized(ComponentEvent e) {
        jButton1.setBounds(5,3,15, this.getHeight() - 6);
        jComboBox1.setBounds(0,0,150,16);
        jTextArea1.setBounds(150,0,this.getWidth() - 166,16);
        jLabel1.setBounds(25,3,this.getWidth() - 46 , 15);        
        lblPosition.setBounds(jTextArea1.getX() + jTextArea1.getWidth(),jTextArea1.getY(),16,16);
        for(int j=0;j<this.getComponentCount();j++)
            if(this.getComponent(j).getClass().toString().compareTo("class iisc.FunctionPanel") == 0)
                ((FunctionPanel)this.getComponent(j)).setBounds(((FunctionPanel)this.getComponent(j)).getX(),((FunctionPanel)this.getComponent(j)).getY()
                ,this.getWidth() - my.rightHeight,
                ((FunctionPanel)this.getComponent(j)).getHeight() );
    }

    private void this_mouseClicked(MouseEvent e) {
        mClicked(e);
    }
    
    
    public void mClicked(MouseEvent e){
    

    
        my.ddListEditor.setVisible(false);    
        my.dragY = my.dragH = my.dragYe = -1;    
        //System.out.println("Clicked: " + i);
        my.pressed = my.released = -1;

        if(my.tmp1[i] == null)
            return;
            
        //my.mouseD = true;
        //Font a1 = new Font(this.jLabel1.getFont().getName(),Font.ITALIC,this.jLabel1.getFont().getSize());
        //Font a2 = new Font(this.jLabel1.getFont().getName(),Font.BOLD,this.jLabel1.getFont().getSize());
        

        //System.out.println("DUZINA:::" + my.tmp1.length);
        for(int ii = 0; ii < my.tmp1.length; ii++)
            if(my.tmp1[ii] != null){
                my.tmp1[ii].setBackground(new Color(247,247,247));
                my.tmp1[ii].jLabel1.setForeground(Color.BLACK);
                //my.tmp1[my.clicked].jLabel1.setFont(a2);
                my.tmp1[ii].jTextArea1.setBackground(Color.WHITE);
                my.tmp1[ii].jTextArea1.setForeground(Color.BLACK); 
                my.tmp1[ii].jTextArea1.setCaretColor(Color.BLACK);
                //if( my.tmp1[ii].getParent() != null)
                //    System.out.println(my.tmp1[ii].getParent().getClass());
            }

        my.clicked = i;
        
        //System.out.println("Width:" + this.getWidth() + " Heigth:" + this.getHeight());
        
        my.status.setForeground(Color.BLUE);
        my.status.setText("Clicked:" + my.aaa[i].name + ":" + my.aaa[i].value);
        if(e != null)
            my.status2.setText("");

        this.setBackground(new Color(0,49,152));
        this.jLabel1.setForeground(Color.WHITE);  
        this.jTextArea1.setForeground(Color.WHITE);
        this.jTextArea1.setCaretColor(Color.WHITE);
        //this.jLabel1.setFont(a1);
        enabledPopup1 = true;
        if( my.aaa[i].group == 77 || my.aaa[i].group == 99 || my.aaa[i].group == 88 || 
            my.aaa[i].group == 999 || my.aaa[i].group == 888 ){
            enabledPopup1 = false;
        }        
        if(e != null){
            //System.out.println("CLICKED: x:" + e.getX() + "  Y:" + e.getY() );
            if(e.getButton() == MouseEvent.BUTTON3 ){
                setMenu();
                jPopupMenu1.setSelected(jPopupMenu1);            
                jPopupMenu1.show(this,e.getX(),e.getY()); 
                jPopupMenu1.setVisible(true);
                
            } 
        }
        my.disableBlockButtons();
        //jovovo
        /*
        int ll = -1;
        for(int l=0; l <= my.clicked; l++){
            if( my.aaa[l].name.compareTo("*END*") != 0 )
                ll++;
            if(my.aaa[l].type.compareTo("B") == 0 && my.aaa[l].expand.compareTo("+") == 0){    
                int br = 1;
                for(l = l + 1 ; l < my.aaa.length ;l++){
                    if(my.aaa[l].type.compareTo("B") == 0 && my.aaa[l].name.compareTo("*END*") != 0)
                        br++;
                    else if(my.aaa[l].type.compareTo("B") == 0 && my.aaa[l].name.compareTo("*END*") == 0)
                        br--;           
                    if(br == 0)
                        break;
                }                         
            }                
        }
        my.treeView.setSelectionInterval(ll,ll);
        */
        int gg = my.treeView.getRowCount();
        int dg = 0;
        int tmpsel = -1;
        while(true){
            int sel = dg + ( gg - dg ) / 2;
            if(tmpsel == sel){
                my.treeView.setSelectionInterval(-1,-1);    
                break;
            }
            tmpsel = sel;
            my.treeView.setSelectionInterval(sel,sel);
            DefaultMutableTreeNodeEditor selNode = (DefaultMutableTreeNodeEditor)my.treeView.getLastSelectedPathComponent();
            if (selNode == null || selNode.pozition == this.i || dg >= gg)
                break;
            else if( selNode.pozition < this.i )
                dg = dg + (gg - dg) / 2;
            else if( selNode.pozition > this.i )
                gg = gg - (gg - dg ) / 2;
        }
        
        Object a = this;
        int x = 0;
        int y = 0;
        while(a!= null && a.getClass().toString().compareTo("class iisc.FunctionPanel") == 0){
            x += ((FunctionPanel)a).getX();
            y += ((FunctionPanel)a).getY();
            a = ((FunctionPanel)a).getParent();
        }         
        
        y-= 50;
        if(y < 0) 
            y=0;
            
        //System.out.println("scroll x:" + my.jPanel1.getVisibleRect().getX());
        //System.out.println("scroll y:" + my.jPanel1.getVisibleRect().getY());
        //System.out.println("scroll w:" + my.jPanel1.getVisibleRect().getWidth());
        //System.out.println("scroll h:" + my.jPanel1.getVisibleRect().getHeight());
        //System.out.println("X:       " + x);
        //System.out.println("Y:       " + y);
        /*
        if( my.jPanel1.getVisibleRect().getX() > x || my.jPanel1.getVisibleRect().getWidth() + my.jPanel1.getVisibleRect().getX() < x  )
            my.jScrollPane1.getViewport().setViewPosition(new java.awt.Point(x + 20, (int)my.jPanel1.getVisibleRect().getY()));
         if( my.jPanel1.getVisibleRect().getY() + 50 > y || my.jPanel1.getVisibleRect().getHeight() + my.jPanel1.getVisibleRect().getY() - 100 < y )
            my.jScrollPane1.getViewport().setViewPosition(new java.awt.Point((int)my.jPanel1.getVisibleRect().getX(),y));
            */
        //my.jScrollPane1.getViewport().setViewPosition(new java.awt.Point(x, y));
        
    }
    
    private void setMenu(){
    
        //brisi poslije
        boolean cut = false;
        boolean copy = false;
        boolean paste = false;
        boolean delete = false;
        
        
        if( my.aaa[i].group == -4 || my.aaa[i].group == -5 || my.aaa[i].group == -6 )
                cut = copy = delete = true;
        else if( 
                    my.aaa[i].group == 0 || 
                    my.aaa[i].group == 1 || 
                    my.aaa[i].group == 2 || 
                    my.aaa[i].group == 3 || 
                    my.aaa[i].group == 5 || 
                    my.aaa[i].group == 6 || 
                    my.aaa[i].group == 8
                ){
            cut = copy = delete = true;
        }
        else if( 
                    my.aaa[i].group == 11 || 
                    my.aaa[i].group == 22 || 
                    my.aaa[i].group == 33 ||  
                    my.aaa[i].group == 88 ||   
                    my.aaa[i].group == 888 || 
                    my.aaa[i].group == 4 || 
                    my.aaa[i].group == 4444 ||
                    my.aaa[i].group == 0 ||  
                    my.aaa[i].group == 5
                ){
            if(my.copyPoz != my.cutPoz)
                paste = true;
        }
        else if( my.aaa[i].group == 333 || my.aaa[i].group == 444 || my.aaa[i].group == 3333 ){
            delete = true;
            if(my.copyPoz != my.cutPoz)
                paste = true;
        }
        else if( my.aaa[i].group == 44 )
            delete = true;
        
        
        
        if(i > 0 && my.aaa[i - 1].type.compareTo("B") == 0 && my.aaa[i - 1].group == -1){
            //System.out.println("*** Prije mene je block:" + my.tmp1[i - 1].jTextArea1.getText()); 
        }
        

        copyMI.setEnabled(copy);
        cutMI.setEnabled(cut);
        pasteMI.setEnabled(paste);
        deleteMI.setEnabled(delete);
        //jMenuItem10.setEnabled(enabledPopup1);        
    }


    private void jTextArea1_mouseClicked(MouseEvent e) {
        mClicked(e);
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        if(my.aaa[i].expand.compareTo("-") == 0){
            my.aaa[i].expand = "+";
            this.jButton1.setText("+");
        }
        else{
            my.aaa[i].expand = "-";
            this.jButton1.setText("-");            
        }
        my.design(true);
        my.tmp1[i].mClicked(null);
    }

    private void this_mouseEntered(MouseEvent e) {
        //my.movePosition = 0;

        /*if(my.pressed != -1)
            System.out.println("USAO   i:" + this.i + "  x:" + e.getX() + "  y:" + e.getY());
            */
        mEntered(e);
    }
    
    private void mEntered(MouseEvent e){
        //if(!my.mouseD)
        //    return;
         if ( my.ddListEditor.isVisible())
            return;
        //System.out.println("ENTERED i: " + this.i );
        
//        my.ddListEditor.setVisible(false);    
        my.dragYe = e.getY();
        my.dragH = this.getHeight();        
        
        my.entered = this.i; 
        
        if(my.pressed != -1)
            my.status.setText("Pressed item:" + my.aaa[my.pressed].name + ":" + my.aaa[my.pressed].value + " and Entered in:" + my.aaa[my.entered].name + ":" + my.aaa[my.entered].value );        
        else
            my.status.setText("Entered in: " + my.aaa[my.entered].name + ":" + my.aaa[my.entered].value + " no." + my.entered );        
        
        if(this.i != my.clicked){
            this.setBackground(new Color(165,212,248));
            jLabel1.setForeground(new Color(247,247,247));
            jTextArea1.setForeground(Color.BLACK);
        }
    }  
         
    private void mExited(){

        if ( my.ddListEditor.isVisible())
           return;

         my.dragY = my.dragH = my.dragYe = -1;
         my.tmp1[this.i].lblPosition.setIcon(null);
         
        if(my.clicked != this.i){
            this.setBackground(new Color(247,247,247));
            jLabel1.setForeground(Color.BLACK);
            jTextArea1.setForeground(Color.BLACK);
        }
    }
    
    public void mReleased(MouseEvent e){

        my.dragY = my.dragH = my.dragYe = -1;
        my.tmp1[this.i].lblPosition.setIcon(null);        
        
        my.releasedTmp = my.released = my.entered;
        my.status.setText("PRESED: " + my.pressed + "    RELEASED: " + my.released );
        //if(my.pressed )
        //System.out.println(" i: " + i );
        //System.out.println("PRESSED : " + my.pressed + "RELEASED: " + my.released );
        
        if(my.pressed == my.released || my.pressed == -1 || my.released == -1){
            my.released = my.pressed = -1;
            return;            
        }

        /*int count = 1;
        
        if(my.aaa[my.clicked].type.compareTo("B")==0){
            int br = 1;
            count = 0;
            for(int u=my.clicked + 1; br != 0; u++){
            
                if(br == 1 && ((my.aaa[u].type.compareTo("B")==0 && my.aaa[u].name.compareTo("*END*") != 0)|| my.aaa[u].type.compareTo("S")==0 ))
                    count++;

                if( my.aaa[u].type.compareTo("B")==0 && my.aaa[u].name.compareTo("*END*") == 0 )
                    br--;
                else if( my.aaa[u].type.compareTo("B")==0 && my.aaa[u].name.compareTo("*END*") != 0 )
                    br++;
            }
        }
        */
                
        my.move(my.movePosition,true,my.pressed,my.released);
        my.pressed = my.released = -1;
        my.movePosition = 0;
        if(my.clicked != -1)
            my.tmp1[my.clicked].mClicked(null);
    }    

    private void this_mouseExited(MouseEvent e) {
        mExited();
    }

    private void jTextArea1_mouseEntered(MouseEvent e) {
        mEntered(e);
    }

    private void jTextArea1_mouseExited(MouseEvent e) {
        mExited();
    }

    private void jComboBox1_mouseEntered(MouseEvent e) {
        mEntered(e);
    }

    private void jComboBox1_mouseExited(MouseEvent e) {
        mExited();
    }

    private void this_mouseReleased(MouseEvent e) {
        mReleased(e);
    }
    
    public void mPressed(MouseEvent e){
        //System.out.println("PRESSED: i: " + i );
        my.dragY = e.getY();        
        my.pressedTmp = my.pressed = this.i;
        my.status.setText("PRESSED: " + my.pressed);        
    }    

    private void this_mousePressed(MouseEvent e) {
        mPressed(e);
    }

    private void jComboBox1_actionPerformed(ActionEvent e) {
        if(this.jcombot)
            my.aaa[this.i].name = ((JLabel)this.jComboBox1.getSelectedItem()).getText();
        //for(int y=0;y<my.aaa.length;y++)
            //System.out.println("ja: " + y + "   ex: " + my.aaa[y].name);
    }


    private void jLabel1_mouseEntered(MouseEvent e) {
        //System.out.println("usao u labelu2:" + my.pressed);
        if(my.pressed != -1){
            //System.out.println("DOLJE "+ this.i);
            //my.movePosition = -1;
        }
        mEntered(e);      
    }

    private void jLabel1_mouseExited(MouseEvent e) {
        if(my.pressed != -1){
            //my.movePosition = 0;
        }    
        mExited();   
    }





    private void jComboBox1_mousePressed(MouseEvent e) {
        mPressed(e);
    }

    private void jComboBox1_mouseReleased(MouseEvent e) {
        mReleased(e);
    }

    private void jMenuItem3_actionPerformed(ActionEvent e) {
        my.copyPoz = my.cutPoz = 0;
        my.deleteNode(true);
    }

    private void jLabel1_mouseClicked(MouseEvent e) {
        mClicked(e);
        
        if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1){
            if(my.aaa[my.clicked].name.length() > 7 && my.aaa[my.clicked].name.substring(0,8).compareTo("FUNCTION") == 0){
                //System.out.println("LAOAOOA");
                //FuncSelection tmp = new FuncSelection(my,"Select Function",true,my,false);
                //tmp.setVisible(true);                
            }
            else if(my.aaa[my.clicked].group == 1){
                //jPopupMenu2.removeAll();
                if(e.getX() < 20 && e.getY() < 20){
                    jPopupMenu2.setSelected(jPopupMenu2);
                    jPopupMenu2.show(this,e.getX(),e.getY()); 
                    jPopupMenu2.setVisible(true);
                }
            }
        }
    }

    private void jComboBox1_mouseClicked(MouseEvent e) {
        mClicked(e);
    }

    private void jMenuItem2_actionPerformed(ActionEvent e) {
        my.cutPoz = my.clicked;
        my.copyPoz = 0;
    }

    private void jMenuItem1_actionPerformed(ActionEvent e) {
        my.copyPoz = my.clicked;
        my.cutPoz = 0;
    }

    private void jMenuItem4_actionPerformed(ActionEvent e) {
        if(my.copyPoz == my.cutPoz)
            return;
            
        my.released = my.clicked;        
            
        if(my.copyPoz != 0){
            my.pressed = my.copyPoz;
            my.copy(0,true,my.pressed,my.released);
        }
        else{
            my.pressed = my.cutPoz;
            my.move(0,true,my.pressed,my.released);
        }
        my.copyPoz = my.cutPoz = 0;
        my.pressed = my.released = -1;
    }

    private void jLabel1_propertyChange(PropertyChangeEvent e) {
        if(e.getPropertyName().compareTo("text") == 0){
            //System.out.println("STARA VALUE: " + e.getOldValue().toString());
            //System.out.println("NOVA VALUE: " + e.getNewValue().toString());
            if(e.getNewValue().toString().compareTo("CATCH_BLOCK") == 0 || 
                e.getNewValue().toString().compareTo("EXECUTION_BLOCK") == 0 || 
                e.getNewValue().toString().compareTo("WHILE_BODY") == 0 || 
                e.getNewValue().toString().compareTo("FOR_BODY") == 0 || 
                e.getNewValue().toString().compareTo("LOCAL_FUNCTION_BODY") == 0 || 
                e.getNewValue().toString().compareTo("DO_WHILE_BODY") == 0)
                jLabel1.setIcon(blockIcon);
            else if(e.getNewValue().toString().compareTo("IF") == 0)
                jLabel1.setIcon(ifIcon);
            else if(e.getNewValue().toString().compareTo("THEN") == 0)
                jLabel1.setIcon(thenIcon);
            else if(e.getNewValue().toString().compareTo("ELSE") == 0)
                jLabel1.setIcon(elseIcon);                
            else if(e.getNewValue().toString().compareTo("ELSEIF") == 0)
                jLabel1.setIcon(elseifIcon);
            else if(e.getNewValue().toString().compareTo("FOR") == 0)
                jLabel1.setIcon(forIcon);
            else if(e.getNewValue().toString().compareTo("WHILE") == 0)
                jLabel1.setIcon(whileIcon);
            else if(e.getNewValue().toString().compareTo("DO_WHILE") == 0)
                jLabel1.setIcon(doWhileIcon);                
            else if(e.getNewValue().toString().compareTo("TRY") == 0)
                jLabel1.setIcon(tryIcon);                
            else if(e.getNewValue().toString().compareTo("CATCH") == 0)
                jLabel1.setIcon(catchIcon);                
            else if(e.getNewValue().toString().compareTo("FINNALY") == 0)
                jLabel1.setIcon(finallyIcon);
            else if(e.getNewValue().toString().compareTo("LOCAL_FUNCTION") == 0)
                jLabel1.setIcon(funIcon);
            else if(e.getNewValue().toString().compareTo("ARGUMENTS") == 0)
                jLabel1.setIcon(argsBlock);
            else if(e.getNewValue().toString().compareTo("DECLARATION") == 0)
                jLabel1.setIcon(declaration);
            else if(e.getNewValue().toString().length()>7 && e.getNewValue().toString().substring(0,8).compareTo("FUNCTION") == 0)
                jLabel1.setIcon(functions2);                        
            else
                jLabel1.setIcon(param);             
        }
    }

    private void jMenuItem5_actionPerformed(ActionEvent e) {
        my.changeBlock("AND",this.i,true);
    }
    
    private void jMenuItem6_actionPerformed(ActionEvent e) {
        my.changeBlock("OR",this.i,true);
    }

    private void jMenuItem7_actionPerformed(ActionEvent e) {
        my.changeBlock("XOR",this.i,true);
    }

    private void jMenuItem8_actionPerformed(ActionEvent e) {
        my.changeBlock("IMPLICATION",this.i,true);
    }

    private void jMenuItem9_actionPerformed(ActionEvent e) {
        my.changeBlock("EQUIVALENCE",this.i,true);
    }


    private void jTextArea1_keyReleased(KeyEvent e) {
        my.aaa[this.i].value = this.jTextArea1.getText();
        //my.txtExpression.setText(my.print(1).trim());
        this.jTextArea1.setToolTipText(this.jTextArea1.getText());
        my.saveOpt = false;
        //Attribute.checkSyntax2(my.txtExpression,false,my.con,my.typeOfEditor,String.valueOf(my.treeID),String.valueOf(my.ID2),String.valueOf(my.id_comp),my.nodeNameSyntax,my.model2)     ;
        //my.checkSyntax(false);
    }

    /*private void jMenuItem10_actionPerformed(ActionEvent e) {
        if(my.aaa[my.clicked].simpleExpr)
            my.aaa[my.clicked].simpleExpr = false;
        else
            my.aaa[my.clicked].simpleExpr = true;
        my.design(true);
    }
    */

    private void jLabel1_mousePressed(MouseEvent e) {
        mPressed(e);
    }

    private void jLabel1_mouseReleased(MouseEvent e) {
        mReleased(e);
    }

    private void this_mouseDragged(MouseEvent e) {
         mDragged(e);
    }
    
    private void mDragged(MouseEvent e){
        //System.out.println("DRAG: x:" + e.getX() + "  Y:" + e.getY());    
        if(my.dragH!= -1 &&  my.dragY == -1 ){
            my.dragY = e.getY();
        }
        
        if(my.dragY != -1 && my.dragH != -1 && my.dragYe != -1){
           
            /*
            System.out.println("\n\n\nDRAG: x:" + e.getX() + "  Y:" + e.getY());  
            System.out.println("Spoljna Y:" + my.dragY);
            System.out.println("Ulkaz Y:" + my.dragYe);
            System.out.println("DRAGH:" + my.dragH);
            System.out.println("1trecina:" + (my.dragH / 3));
            System.out.println("2trecine:"+ (2 * my.dragH / 3));
            System.out.println("velicina :" + (e.getY() - ( my.dragY - my.dragYe )));
            */
            
            if((e.getY() - ( my.dragY - my.dragYe )) < my.dragH / 3 && (e.getY() - ( my.dragY - my.dragYe )) >= 0){
                //System.out.println("IDE U PLANINE");
                my.tmp1[my.entered].lblPosition.setIcon(up);
                my.movePosition = -1;
            }
            else if((e.getY() - ( my.dragY - my.dragYe )) < 2 * my.dragH / 3 && (e.getY() - ( my.dragY - my.dragYe )) >= my.dragH / 3){
                //System.out.println("IDE U GRAD");
                my.tmp1[my.entered].lblPosition.setIcon(inBlockIcon);
                my.movePosition = 0;
            }
            else if((e.getY() - ( my.dragY - my.dragYe )) <= my.dragH && (e.getY() - ( my.dragY - my.dragYe )) >= 2 * my.dragH / 3){
                //System.out.println("IDE NA MORE");
                my.tmp1[my.entered].lblPosition.setIcon(down);
                my.movePosition = 1;
            }
            
        }        
    }

    private void jComboBox1_mouseDragged(MouseEvent e) {
        mDragged(e);    
    }

    private void jTextArea1_mouseDragged(MouseEvent e) {
        mDragged(e);    
    }

    private void jLabel1_mouseDragged(MouseEvent e) {
        mDragged(e);    
    }

    private void jTextArea1_keyPressed(KeyEvent e) {
        if( e.getKeyCode() == 113){
        
            if(!my.aaa[my.clicked].type.equals("S"))
                return;
        
            my.ddListEditor.setTxtFun(this);
            /*System.out.println(my.tmp1[my.clicked].jTextArea1.getText().trim() + ":");
            System.out.println(my.aaa[my.clicked].group + ":");
            System.out.println(my.aaa[my.clicked].name + ":");*/
            String domain = ""; 
            String var = "";
            String exp = "";
            
            int poz = my.tmp1[my.clicked].jTextArea1.getCaretPosition();
            int pozjednako = my.tmp1[my.clicked].jTextArea1.getText().indexOf("=");
            int pozdom = my.tmp1[my.clicked].jTextArea1.getText().split("=")[0].indexOf(" ");
            
            my.tmp1[my.clicked].jTextArea1.setText( 
                                    my.tmp1[my.clicked].jTextArea1.getText()
                                    .replaceAll("\n"," ").replaceAll("\r"," ")
                            );
                            
            my.tmp1[my.clicked].jTextArea1.setCaretPosition(poz);
                            
            //System.out.println(my.tmp1[my.clicked].jTextArea1.getText() + ":");

            
            System.out.println(poz);
            System.out.println(pozjednako);
            System.out.println(pozdom);          
            
            if (   
                        (   my.aaa[my.clicked].group == -5 &&
                            (   my.aaa[my.clicked].name.trim().equals("Variable") || 
                                my.aaa[my.clicked].name.trim().equals("Constant")
                            ) 
                        ) || 
                        ( my.aaa[my.clicked].group == -8 || my.aaa[my.clicked].group == -88 )
                  ){
                    System.out.println(my.tmp1[my.clicked].jTextArea1.getText().substring(0,poz).trim().contains(" "));
                      System.out.println(my.tmp1[my.clicked].jTextArea1.getText().substring(0,poz).trim().contains("="));
                      if(poz >=  my.tmp1[my.clicked].jTextArea1.getText().length())
                        poz = my.tmp1[my.clicked].jTextArea1.getText().length() ;
                    if( pozdom >= poz || pozdom == -1  ){
                        my.ddListEditor.setDataDomain(my.cmbExp);      
                    }
                    else if(poz > pozdom && (poz < pozjednako || pozjednako == -1))
                        return;
                    else if (!my.tmp1[my.clicked].jTextArea1.getText().substring(0,poz).trim().contains(" ") && 
                            !my.tmp1[my.clicked].jTextArea1.getText().substring(0,poz).trim().contains("=")) {
                        my.ddListEditor.setDataDomain(my.cmbExp);
                    }
                    else{
                        my.ddListEditor.setDataFunEditor(my.aaa,my.clicked,my.cmbExp);
                        my.ddListEditor.setTxtFun(my.tmp1[my.clicked]);
                    }                        
                  }
            else{
                my.ddListEditor.setDataFunEditor(my.aaa,my.clicked,my.cmbExp);
                my.ddListEditor.setTxtFun(my.tmp1[my.clicked]);
            }
            Object a = jTextArea1.getParent();
            int x = jTextArea1.getX();
            int y = jTextArea1.getY() + jTextArea1.getHeight() ;
            while(a.getClass().toString().compareTo("class iisc.FunctionPanel") == 0){
                x += ((FunctionPanel)a).getX();
                y += ((FunctionPanel)a).getY();
                a = ((FunctionPanel)a).getParent();
            }            
            my.ddListEditor.setBounds( x, y, 300, 100 );
            my.ddListEditor.setVisible(true);
            my.ddListEditor.requestFocus();
        }      
    }

    private class ComboBoxRenderer extends JLabel implements ListCellRenderer{

        private ImageIcon exp = new ImageIcon(IISFrameMain.class.getResource("icons/exp.gif"));
        private ImageIcon param = new ImageIcon(IISFrameMain.class.getResource("icons/param.gif"));
        private ImageIcon question2 = new ImageIcon(IISFrameMain.class.getResource("icons/question2.gif"));
        private ImageIcon begin = new ImageIcon(IISFrameMain.class.getResource("icons/begin.gif"));
        private ImageIcon step = new ImageIcon(IISFrameMain.class.getResource("icons/step.gif"));
        private ImageIcon constant = new ImageIcon(IISFrameMain.class.getResource("icons/constant.gif"));
        private ImageIcon comments = new ImageIcon(IISFrameMain.class.getResource("icons/comments.gif"));
        private ImageIcon arg = new ImageIcon(IISFrameMain.class.getResource("icons/arg.gif"));
        private ImageIcon funcName = new ImageIcon(IISFrameMain.class.getResource("icons/funname.gif"));
        private ImageIcon variable = new ImageIcon(IISFrameMain.class.getResource("icons/variable.gif"));
        private ImageIcon type = new ImageIcon(IISFrameMain.class.getResource("icons/type.gif"));
        private ImageIcon cons = new ImageIcon(IISFrameMain.class.getResource("icons/constant.gif"));
        private ImageIcon incfun = new ImageIcon(IISFrameMain.class.getResource("icons/incfun.gif"));
        private ImageIcon excep = new ImageIcon(IISFrameMain.class.getResource("icons/exception2.gif"));
        private ImageIcon cur = new ImageIcon(IISFrameMain.class.getResource("icons/cursor2.gif"));
        private ImageIcon assignment = new ImageIcon(IISFrameMain.class.getResource("icons/assignment.gif"));

        public ComboBoxRenderer() {
            setOpaque(true);
            setHorizontalAlignment(LEFT);
            setVerticalAlignment(CENTER);
        }
        
        public Component getListCellRendererComponent(
                                           JList list,
                                           Object value,
                                           int index,
                                           boolean isSelected,
                                           boolean cellHasFocus) {
            //Get the selected index. (The index param isn't
            //always valid, so just use the value.)
            //int selectedIndex = ((Integer)value).intValue();
            if(value != null)
            { 
                JLabel alo = (JLabel)value;

                setText(alo.getText());
                //System.out.println(" name: " + alo.getText());
                if(alo.getText().equals("Simple Expression") || alo.getText().equals("Expression"))
                  setIcon(exp);
                else if(alo.getText().equals("Assignment"))
                    setIcon(assignment);
                //else if(alo.getText().equals("SET Element"))
                  //setIcon(ine);
                else if(alo.getText().equals("Declaration"))
                    setIcon(constant);
                else if( alo.getText().equals("Begin") )
                    setIcon(begin);                  
                else if( alo.getText().equals("Condition") )
                    setIcon(question2);
                else if( alo.getText().equals("Arg") )
                    setIcon(arg);                    
                else if( alo.getText().equals("Step") )
                    setIcon(step);                    
                else if( alo.getText().equals("Comments") )
                    setIcon(comments);                      
                else if( alo.getText().equals("Function Name") )
                    setIcon(funcName);                      
                else if( alo.getText().equals("Variable") )
                    setIcon(variable);
                else if( alo.getText().equals("Type") )
                    setIcon(type);
                else if( alo.getText().equals("Constant") )
                    setIcon(cons);
                else if( alo.getText().equals("Include Function") )
                    setIcon(incfun);
                else if( alo.getText().equals("Exception") )
                    setIcon(excep);
                else if( alo.getText().equals("Cursor") )
                    setIcon(cur);                    
                else
                    setIcon(param);
            }

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            return this;
        }    
    }        
}
