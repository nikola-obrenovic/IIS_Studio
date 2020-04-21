package iisc.callgraph;

import iisc.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.sql.*;

public class ParPanel extends JPanel
{
    private FormPanel parent;
    private CallingGraph gr;
    public boolean expanded;
    private HeadPanel ePanel;
    //private JTree cTree;
    private JList list;
    private JPanel lp;
    public CrossPanel cp;
    private JScrollPane sp;
    
    public  Vector parVec;
    private DefaultListModel listModel;
    private BorderLayout bl = new BorderLayout();
    private JPopupMenu pop;
    private JMenuItem addMi;
    private JMenuItem editMi;
    private JMenuItem deleteMi;
    private int Tf_id;
    
    public ParPanel(FormPanel _parent, int _Tf_id)
    {
        super();  
        parent = _parent;
        Tf_id = _Tf_id;
        gr = parent.parent;
        expanded = false;
        setLayout(null);
        setBackground(SystemColor.lightGray);
        
        //Inicijalizacija panela 
        ePanel = new HeadPanel();
        ePanel.setBounds(0, 0, parent.mWidth, parent.mParCHeight);
        ePanel.setBackground(SystemColor.lightGray);
        ePanel.setBorder(new LineBorder(Color.black, 1));
        ePanel.setLayout(null);
        add(ePanel);
        
        //Inicijalizacija dugmeta
        cp = new CrossPanel(this);
        ePanel.add(cp);
        add(ePanel);
        //Inicijalizija liste
        InitParList();
        //Inicijalizacija menija
        InitMenu();
        //Inicijalizija scrollPan 
        sp = new JScrollPane(list);
        lp = new JPanel();
        
        lp.setBounds(0, parent.mParCHeight - 1, parent.mWidth,parent.mParEHeight + 1);
        lp.setBorder(new LineBorder(Color.black, 1));
        lp.setBackground(Color.white);
        lp.setLayout(bl);
        lp.add(sp, bl.CENTER);
        
        add(lp, null);
        
        lp.revalidate();
        revalidate();
        
    }
    
    /**************************************************************************/
    /*****    Procedura koja vrsi reinicijalizaciju  parametara  **************/
    /**************************************************************************/
    public void ReInitParList()
    {
        lp.remove(sp);
        InitParList();
        sp = new JScrollPane(list);
        lp.add(sp, bl.CENTER);
        revalidate();
        
    }
    
    public void InitParList()
    {
        
        listModel = new DefaultListModel();
        
        //lp.add(list, BorderLayout.CENTER);  
        
        int i = 0;
        parVec = new Vector();
        try
        {
            Statement statement = gr.conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from IISC_PARAMETER,IISC_DOMAIN where IISC_PARAMETER.Dom_id=IISC_DOMAIN.Dom_id and Tf_id =" + parent.Tf_id);
            
            while(rs.next())
            {   
                parVec.add(new ParValue(rs.getInt("Par_id"), rs.getInt("Dom_id"), rs.getInt("Tf_id"), rs.getInt("Tob_id"), rs.getInt("Att_id"), rs.getString("Dom_mnem"), rs.getString("Par_mnem"), rs.getString("Par_desc"), rs.getString("Par_def_value")));
                i = i + 1;
            }
            rs.close();
            
        }
        catch(SQLException sqle)
        {
            System.out.println("Sql exception :" + sqle);
        }  
        
        for(int j = 0; j < i; j ++)
        {
            listModel.addElement(((ParValue)parVec.get(j)).Par_mnem);
        }
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new CustomCellRenderer());
        
        
        list.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() > 1)
                {
                    EditAction();
                }
                
                if((e.getModifiers() & InputEvent.BUTTON3_MASK)== InputEvent.BUTTON3_MASK)
                {
                    try 
                    {
                        pop.show(list, e.getX()+10, e.getY());
                    }
                    catch(Exception ex)
                    {
                    }
                }
            }
            
        });
        
    }
    
    private void EditAction()
    {
        int index = list.getSelectedIndex();
                
        if (index < 0)
        {
            return;
        }
        
        ParForm pf = new ParForm((ParValue)parVec.get(index),"Edit parameter",gr.parent,gr.conn,gr.PR_id, Tf_id );
        Settings.Center(pf);
        pf.setVisible(true);
        
        if (pf.action == pf.SAVE)
        {
            
            try
            {
                ParValue value = pf.getValue();
                Statement statement = gr.conn.createStatement();
                String sql = "update IISC_PARAMETER set Dom_id=" + value.Dom_id + ",Tob_id=" + value.Tob_id + ",Att_id=" + value.Att_id + ",Par_mnem='" + value.Par_mnem + "',Par_desc='" + value.Par_desc  + "',Par_def_value='" + value.Par_def_value + "' where Par_id =" + value.Par_id;
                statement.execute(sql);
                listModel.setElementAt(value.Par_mnem, index);
                parVec.setElementAt(value, index);
            }
            catch(SQLException sqle)
            {
            }
            
        }
        pf.dispose();
    }
    
    private void AddAction()
    {
        ParValue value = new ParValue(-1,-1, -1,-1, -1,"","","","");
        ParForm dialog = new ParForm(value,"Add Parameter", gr.parent, gr.conn, gr.PR_id, Tf_id);
        Settings.Center(dialog);
        dialog.setVisible(true);
        
        if (dialog.action == dialog.SAVE)
        {
            try
            {
                int max = 0;
    
                try
                {
                    Statement statement2 = gr.conn.createStatement();
                    ResultSet rs = statement2.executeQuery("select max(Par_id) from IISC_PARAMETER");
                    if (rs.next())
                    {
                        max = rs.getInt(1);
                    }
                    else
                    {
                        max = 0;
                    }
                    rs.close();
                    statement2.close();
                }
                catch(SQLException sqle)
                {
                    max = 0;
                }
                max = max + 1;
                
                value = dialog.getValue();
                Statement statement = gr.conn.createStatement();
                statement.execute("insert into IISC_PARAMETER(Par_id,Dom_id,PR_id,Tf_id, Tob_id, Att_id,Par_mnem,Par_desc,Par_def_value)  values(" + max + "," + value.Dom_id + "," + gr.PR_id + "," + parent.Tf_id +"," + value.Tob_id + "," + value.Att_id + ",'" + value.Par_mnem + "','" + value.Par_desc + "','" + value.Par_def_value + "')");
                value.Par_id = max;
                listModel.addElement(value.Par_mnem);
                parVec.add(value);
            }
            catch(SQLException sqle)
            {
            }
        }
        dialog.dispose();
        
    }
    
    private void DeleteAction()
    {
        int index = list.getSelectedIndex();
        
        if (index < 0)
        {
            return;
        }
        
        int action = JOptionPane.showConfirmDialog(this, "Confirm delete", "Parameter", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

        if (action == JOptionPane.OK_OPTION)
        {
            ParValue value = (ParValue)parVec.get(index);
            try
            {
                Statement statement = gr.conn.createStatement();
                statement.execute("delete from IISC_PARAMETER where Par_id =" + value.Par_id);                            
                parVec.get(index);
                listModel.remove(index);
                
            }
            catch(SQLException sqle)
            {
            }
        }
    }
    
    private void InitMenu()
    {
        pop = new JPopupMenu();
        addMi = new JMenuItem("  Add");
        addMi.setBackground(Color.white);
        addMi.setIcon(gr.imageNew);
        editMi = new JMenuItem("  Edit");
        editMi.setIcon(gr.imageEdit);
        editMi.setBackground(Color.white);
        deleteMi = new JMenuItem("  Delete");
        deleteMi.setIcon(gr.imageErase);
        deleteMi.setBackground(Color.white);
        editMi.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                EditAction();
            }
        });
        
        deleteMi.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                DeleteAction();
            }
        });
        
        addMi.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                AddAction();
            }
        });
        
        pop.add(addMi);
        pop.add(editMi);
        pop.add(deleteMi);
    }
    
    public void ReAdjust()
    {
        ePanel.setBounds(0, 0, parent.mWidth, parent.mParCHeight);
        lp.setBounds(0, parent.mParCHeight - 1, parent.mWidth, parent.mParEHeight + 1);
        revalidate();
    }
    
    public boolean getExpanded()
    {
        return expanded;
    }
    
    private void Expand()
    {
        expanded = true;
        parent.Readjust();   
    }
    
    private void Collapse()
    { 
        expanded = false;
        parent.Readjust();
    }
    
    private class CustomCellRenderer extends  DefaultListCellRenderer  implements ListCellRenderer<Object> 
    {
        
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) 
        {
            JLabel label =(JLabel)super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
    
            String s = value.toString();
            label.setText(s);
            label.setIcon(gr.iiscPar);
          
            if(isSelected)
            {
                label.setBackground(SystemColor.textHighlight);
                label.setForeground(Color.white);
            }
            else
            {
                label.setBackground(Color.white);
                label.setForeground(Color.black);
            }
            
            label.revalidate();
            list.revalidate();
            return this;
        }  
    }
    
    /******************************************************************/
    /********       Forma za editovanje vrijednosti iz tabele    ******/
    /******************************************************************/
    private class ParForm extends JDialog
    {
        private ParValue value;
        private Connection conn;
        private JComboBox domCombo = new JComboBox();
        private Vector domIds = new Vector();
        private JLabel domLbl = new JLabel("Domain");
        private JLabel nameLbl = new JLabel("Name");
        private JLabel descLbl = new JLabel("Description");
        private JLabel defValueLbl = new JLabel("Default value");
        private JButton searchBtn = new JButton("...");
        private JTextField nameTxt = new JTextField("");
        private JTextField descTxt = new JTextField("");
        private JTextField defValueTxt = new JTextField("");
        private JButton saveBtn = new JButton("OK");
        private JButton cancelBtn = new JButton("Cancel");
        private JComboBox attCombo = new JComboBox();
        private JLabel attLbl = new JLabel("Attribute");
        private int SAVE = 0;
        private int CANCEL = 1;
        private int action;
        private int PR_id;
        private int Tf_id;
        private IISFrameMain parent;
        private Vector attVec;
        
        public ParForm(ParValue p_Value, String title, IISFrameMain _parent, Connection _conn, int _PR_id, int _Tf_id)
        {
            super(_parent, title, true);
            parent = _parent;
            value = p_Value;
            conn = _conn;
            PR_id = _PR_id;
            Tf_id = _Tf_id;
            setSize(305, 230);
            getContentPane().setLayout(null);
            setResizable(false);
            action = CANCEL;
            
            domLbl.setBounds(10, 40, 90, 20);
            domCombo.setBounds( 100, 40, 155, 20);
            searchBtn.setBounds(260, 40,  30, 20);
            searchBtn.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                searchBtn_actionPerformed(e);
              }
            });
            
            nameLbl.setBounds(10, 10, 90, 20);
            nameTxt.setBounds(100, 10, 190, 20);
            
            descLbl.setBounds(10, 70, 90, 20);
            descTxt.setBounds(100, 70, 190, 20);
            
            defValueLbl.setBounds(10, 100, 90, 20);
            defValueTxt.setBounds(100, 100, 190, 20);
            
            attLbl.setBounds(10, 130, 90, 20);
            attCombo.setBounds(100, 130, 190, 20);
            
            nameTxt.setText(value.Par_mnem);
            descTxt.setText(value.Par_desc);
            defValueTxt.setText(value.Par_def_value);
            
            saveBtn.setBounds(53, 170,  70, 25);
            cancelBtn.setBounds(176, 170, 70, 25);
            
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
           
            getContentPane().add(domLbl);
            getContentPane().add(domCombo);
            getContentPane().add(searchBtn);
            getContentPane().add(nameLbl);
            getContentPane().add(nameTxt);
            getContentPane().add(descTxt);
            getContentPane().add(descLbl);
            getContentPane().add(defValueTxt);
            getContentPane().add(defValueLbl);
            getContentPane().add(saveBtn);
            getContentPane().add(cancelBtn);
            getContentPane().add(attLbl);
            getContentPane().add(attCombo);
            
            InitCombo();
            InitAttCombo();
        }
        
        /* Inicijalizacija kombo boksa koji sadrzi tipove formi */
        private void InitCombo()
        {
           try
            {
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("select * from IISC_DOMAIN  where PR_id="+ PR_id);
                
                //domCombo.addItem("");
                int Dom_id, i = 0;
                
                while(rs.next())
                {
                    domCombo.addItem(rs.getString("Dom_mnem"));
                    
                    Dom_id = rs.getInt("Dom_id");
                    domIds.add(new Integer(Dom_id));
                    
                    if (value.Dom_id == Dom_id)
                    {
                        domCombo.setSelectedIndex(i);
                    }
                    i = i + 1;
                }
            }
            catch(SQLException e)
            {
                System.out.println("\n" + e.toString() + "\n");
            }
            
            repaint();
        }
        
        public void InitAttCombo()
        {
            int i = 0;
            attVec = new Vector();
            int selIndex = 0;
            
            try
            {
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("select IISC_ATTRIBUTE.Att_id,IISC_ATTRIBUTE.Att_mnem,IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_id,IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_mnem  from IISC_ATT_TOB,IISC_ATTRIBUTE,IISC_COMPONENT_TYPE_OBJECT_TYPE where IISC_ATT_TOB.Tf_id=" + Tf_id + " and IISC_ATT_TOB.Att_id=IISC_ATTRIBUTE.Att_id and IISC_ATT_TOB.Tob_id=IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_id");
                
                while(rs.next())
                {   
                    Attribute a = new Attribute(rs.getInt("Att_id"), rs.getString("Att_mnem"), rs.getInt("Tob_id"), rs.getString("Tob_mnem"));
                    attVec.add(a);
                    
                    if ( a.Att_id == value.Att_id && a.Tob_id == value.Tob_id )
                    {
                        selIndex = i; 
                    }
                    
                    i = i + 1;
                }
                
                rs.close();
                
            }
            catch(SQLException sqle)
            {
                System.out.println("Sql exception :" + sqle);
            }
            
            int size = attVec.size();
            
            for(i = 0; i < size; i++)
            {
                attCombo.addItem(((Attribute)attVec.get(i)).Att_mnem + "(" + ((Attribute)attVec.get(i)).Tob_mnem + ")");
            }
            
            if (size > 0 )
            {
               attCombo.setSelectedIndex(selIndex);
            }
        }
        
        private void saveBtn_actionPerformed(ActionEvent e)
        {
            String mnem;
            mnem = nameTxt.getText();
            boolean exists = false;
            
            if ((value.Par_id == -1) || (!value.Par_mnem.endsWith(mnem)))
            {            
                try
                {
                    Statement statement = conn.createStatement();
                    ResultSet rs = statement.executeQuery("select Par_id from IISC_PARAMETER where Par_mnem='" + mnem + "' and TF_id=" + Tf_id);
                    if (rs.next())
                    {
                        exists = true;
                    }
                    rs.close();
                    statement.close();
                }
                catch(SQLException sqle)
                {
                }
                  
            }
            
            if (exists)
            {
                JOptionPane.showMessageDialog(this,"Parameter named: " + mnem + " allready exits", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            action = SAVE;
            
            value = new ParValue(value.Par_id,((Integer)domIds.get(domCombo.getSelectedIndex())).intValue(), Tf_id, ((Attribute)attVec.get(attCombo.getSelectedIndex())).Tob_id, ((Attribute)attVec.get(attCombo.getSelectedIndex())).Att_id, domCombo.getSelectedItem().toString(), nameTxt.getText(),descTxt.getText(), defValueTxt.getText()); 
            this.setVisible(false);
        }
        
        private void cancelBtn_actionPerformed(ActionEvent e)
        {
            action = CANCEL;
            this.setVisible(false);
        }
        
        private void searchBtn_actionPerformed(ActionEvent e)
        {
            SearchTable stb = new SearchTable(parent,"Select Domain", true,conn);
            stb.type = "Domain";
            stb.item = domCombo;
            Settings.Center(stb);
            stb.setVisible(true);
        }
        
        public ParValue getValue()
        {
            return value;
        }
    }

    private class ParValue
    {
        private int Par_id;
        private String Par_mnem;
        private String Par_desc;
        private String Par_def_value;
        private int Dom_id;
        private int Tf_id;
        private int Tob_id;
        private int Att_id;
        private String Dom_mnem;
        
        public ParValue(int _Par_id, int _Dom_id, int _Tf_id, int _Tob_id, int _Att_id, String _Dom_mnem, String _Par_mnem, String _Par_desc, String _Par_def_value)
        {
           Par_id = _Par_id;
           Par_mnem = _Par_mnem;
           Par_desc = _Par_desc;
           Par_def_value = _Par_def_value;
           Dom_id = _Dom_id;
           Dom_mnem = _Dom_mnem;
           Tf_id = _Tf_id;
           Tob_id = _Tob_id;
           Att_id = _Att_id;
        }
    }

    public class CrossPanel extends JLabel implements MouseListener
    {
        private int state;
        private ParPanel cp;
        private boolean mDown = false;
        private BorderLayout brLayout = new BorderLayout();
        
        private CrossPanel(ParPanel _cp)
        {
            state = -1;    
            cp = _cp;
            setBackground(SystemColor.lightGray);
            
            setBorder(BorderFactory.createEmptyBorder());
            setLayout(brLayout);
            
            setIcon(gr.imageExpand);
            
            addMouseListener(this);
            
            
            setSize(15, 15);
            setBounds(5, 2, 15, 15);
            
        }
        
        public void SetState(int newState)
        {
            state = newState;
            if (state == 1)
            {
                  setIcon(gr.imageCollapse);
            }
            else
            {
                setIcon(gr.imageExpand);
            }
            this.repaint();
              
        }
        
        public void mousePressed(MouseEvent e)
        {
            state = state * (-1);
            mDown = true;
            setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            cp.parent.dirty = true;
            cp.parent.parent.dirty = true;
              
            if (state == 1)
            {
                cp.Expand();
                setIcon(gr.imageCollapse);
            }
            else
            {
                cp.Collapse();
                setIcon(gr.imageExpand);
            }
            this.repaint();
        }
        
        public void mouseReleased(MouseEvent e)
        {
            mDown = false;
            setBorder(BorderFactory.createEmptyBorder());
        }
        
        public void mouseClicked(MouseEvent e)
        {
            
        }
        
        public void mouseEntered(MouseEvent e)
        {
            if (mDown)
            {
                return;
            }
            
            setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        }
        
        public void mouseExited(MouseEvent e)
        {
            setBorder(BorderFactory.createEmptyBorder());
        }
    }
    
    private class HeadPanel extends JPanel
    {
        public HeadPanel()
        {
            super(null);
        }
        public void paint(Graphics g)
        {
        
            Graphics2D g2 = (Graphics2D)g;
        
            super.paint(g2);
            
             // Fill with a gradient.
            GradientPaint gp = new GradientPaint(0,0,Color.white,this.getWidth(), this.getHeight(),  parent.color, false);
            g2.setPaint(gp);
            g2.fillRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);
            
            g2.setColor(Color.black);
            g2.setFont(new Font("SansSerif",0,11));
            g2.drawString("Parameters",22, 14);
            super.paintChildren(g2);
        }
    }
    
    private class Attribute
    {
        public int Att_id;
        public int Tob_id;
        public String Att_mnem;
        public String Tob_mnem;
        
        private Attribute(int _Att_id, String _Att_mnem, int _Tob_id, String _Tob_mnem)
        {
            Att_id = _Att_id;
            Tob_id = _Tob_id;
            Tob_mnem = _Tob_mnem;
            Att_mnem = _Att_mnem;
        }
    }
    
}
