package iisc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

public class ItemGroup extends JPanel 
{
    private ItemTableModel datm = new ItemTableModel();
    public JTable itemTable = new JTable(datm);
    private JButton addBtn = new JButton();
    private JButton removeBtn = new JButton();
    private JButton editBtn = new JButton();
    private JScrollPane tableSp = new JScrollPane();
    private JButton btnUp = new JButton();
    private ImageIcon iconUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
    private ImageIcon iconDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
    private JButton btnDown = new JButton();
    private Vector attPanels;
    private DefaultListModel listModel = new DefaultListModel();
    private DefaultListModel nestedModel = new DefaultListModel();
    private int selectedIndex = 0;
    
    int Tob_id, Tf_id, PR_id;
    ObjectType ob;
    Connection conn;
    private JScrollPane listSp = new JScrollPane();
    private JButton moveBtn = new JButton();
    private JList attList = new JList();
    private Vector attVec;
    private JList nestedList = new JList();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JButton backBtn = new JButton();
    private JButton moveNesBtn = new JButton();
    private JButton backNesBtn = new JButton();
    private Vector removedIG = new Vector();
    private Vector removeNested = new Vector();
    
    public ItemGroup(int _Tob_id, int _Tf_id, int _PR_id, ObjectType _ob, Connection _conn) 
    {
        super(null);
        
        Tob_id = _Tob_id;
        Tf_id = _Tf_id;
        PR_id = _PR_id;
        ob = _ob;
        conn = _conn;
        
        try 
        {
            jbInit();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        attPanels = new Vector();
        InitTable();
        
        if (datm.data.size() == 0)
        {
            InitEmptyTable(); 
            ItemGroupItems ig = new ItemGroupItems(-1, Tob_id, Tf_id, PR_id, ob, conn);
            //ig.SetData(attVec);
            attPanels.add(ig);
            ig.setBounds(178, 105, 190,195);
            add(ig);
            InitNewList();
            revalidate();
            repaint();
        }
        else
        {
            InitAttPanels();
            InitList();
            itemTable.getSelectionModel().setSelectionInterval(0, 0);
            add((ItemGroupItems)attPanels.get(0));
            revalidate();
            repaint();
        }
        
        
        itemTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent se)
            {
                ChangeSelected();
            }
        });
        
        itemTable.getSelectionModel().setSelectionInterval(0, 0);
        
        InitNestedList();
        RemoveCurrentItemGroupFromNested();
        repaint();
    }

    private void ChangeSelected()
    {
        if (itemTable.getSelectedRow() == -1)
        {
            return;
        }
        if (selectedIndex != itemTable.getSelectedRow())
        {
            remove((ItemGroupItems)attPanels.get(selectedIndex));
            selectedIndex = itemTable.getSelectedRow();
            add((ItemGroupItems)attPanels.get(selectedIndex));
            
            InitNestedList();
            RemoveCurrentItemGroupFromNested();
            
            revalidate();
            repaint();
        }
    }
    
    private void RemoveCurrentItemGroupFromNested()
    {
        
        int selected = itemTable.getSelectedRow();
        
        if (selected < 0)
        {
            return;
        
        }
        
        ItemGroupItems igi;
        int size = datm.data.size();
        ItemGrop ig;
        AttValue attV;
        int i = 0;
        boolean can = false;
        
        ig = (ItemGrop)datm.data.get(selected);
        nestedModel.removeElement(ig.IG_name);
               
    }
    
    public ItemGroup() 
    {
        try 
        {
            jbInit();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception 
    {
        setLayout(null);
        this.setSize(new Dimension(380, 314));
        setSize(380, 310);
        addBtn.setText("Add");
        addBtn.setBounds(new Rectangle(295, 5, 75, 25));
        addBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        addBtn_actionPerformed(e);
                    }
                });
        removeBtn.setText("Remove");
        removeBtn.setBounds(new Rectangle(295, 35, 75, 25));
        editBtn.setText("Edit");
        editBtn.setBounds(new Rectangle(295, 65, 75, 25));
        editBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        editBtn_actionPerformed(e);
                    }
                });
        tableSp.setBounds(new Rectangle(35, 5, 255, 85));
        btnUp.setMaximumSize(new Dimension(60, 60));
        btnUp.setPreferredSize(new Dimension(25, 20));
        btnUp.setBounds(new Rectangle(5, 5, 25, 20));
        btnUp.setIcon(iconUp);
        btnUp.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnUp_actionPerformed(e);
                    }
                });
        btnDown.setBounds(new Rectangle(5, 70, 25, 20));
        btnDown.setIcon(iconDown);

       // listSp.setSize(new Dimension(20, 10));

        btnDown.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnDown_actionPerformed(e);
                    }
                });
        listSp.setBounds(new Rectangle(5, 105, 120, 90));
        jScrollPane1.setBounds(new Rectangle(5, 205, 120, 95));
        
        moveBtn.setText(">");
        moveBtn.setSize(new Dimension(20, 20));
        moveBtn.setBounds(new Rectangle(130, 120, 42, 25));
        moveBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        moveBtn_actionPerformed(e);
                    }
                });
        
        attList.setSize(new Dimension(20, 20));
        nestedList.setSize(new Dimension(20, 20));
        
        backBtn.setText("<");
        backBtn.setBounds(new Rectangle(130, 155, 42, 25));


        backBtn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                backBtn_actionPerformed(e);
            }
        });
        
        removeBtn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                remove_actionPerformed(e);
            }
        });
        
        moveNesBtn.setText(">");
        moveNesBtn.setBounds(new Rectangle(130, 220, 42, 25));
        moveNesBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        moveNesBtn_actionPerformed(e);
                    }
                });
        backNesBtn.setText("<");
        backNesBtn.setBounds(new Rectangle(130, 260, 42, 25));
        backNesBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        backNesBtn_actionPerformed(e);
                    }
                });
        tableSp.getViewport().add(itemTable, null);

        jScrollPane1.getViewport().add(nestedList, null);
        add(moveNesBtn, null);
        add(backNesBtn, null);
        add(backBtn, null);
        add(jScrollPane1, null);
        add(moveBtn, null);
        listSp.getViewport().add(attList, null);
        this.add(listSp, null);
        add(btnDown, null);
        add(btnUp, null);
        add(tableSp, null);
        add(addBtn, null);
        add(removeBtn, null);
        add(editBtn, null);

    }
    
    public void InitTable()
    {
        Vector data = new Vector();
        
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from IISC_ITEM_GROUP where Tob_id=" + Tob_id + " order by IG_sequence");
            
            while(rs.next())
            {   
                data.add(new ItemGrop(rs.getInt("IG_id"), rs.getString("IG_name"), rs.getString("IG_title"), rs.getInt("IG_contex"), rs.getInt("IG_overflow")));
            }
            rs.close();
            
        }
        catch(SQLException sqle)
        {
            System.out.println("Sql exception :" + sqle);
        }  
        datm.populateData(data);
             
        itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemTable.setRowSelectionAllowed(true);
        itemTable.setGridColor(new Color(0,0,0));
        itemTable.setBackground(Color.white);
        itemTable.getTableHeader().setReorderingAllowed(false);
        itemTable.setAutoscrolls(true);
        itemTable.getTableHeader().setResizingAllowed(true);
        itemTable.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        itemTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        itemTable.repaint();
    
    }
    
    
    //Ako tek treba da se definise item podgrupa
    public void InitNewList()
    {
        attVec = new Vector();
        attList.setModel(listModel);
        AttValue att;
        
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select IISC_ATTRIBUTE.Att_id, IISC_ATTRIBUTE.Att_mnem from IISC_ATTRIBUTE, IISC_ATT_TOB where IISC_ATTRIBUTE.Att_id=IISC_ATT_TOB.Att_id and Tob_id=" + Tob_id);
            
            while(rs.next())
            {   
                att = new AttValue(-1,rs.getInt(1),rs.getString(2),-1,"", false);
               
                listModel.addElement(att.Att_mnem);
            }
            rs.close();
            
        }
        catch(SQLException sqle)
        {
            System.out.println("Sql exception :" + sqle);
        }  
        
    }
    
    public void InitList()
    {
        attVec = new Vector();
        attList.setModel(listModel);
        AttValue att;
        
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select IISC_ATTRIBUTE.Att_id, IISC_ATTRIBUTE.Att_mnem from IISC_ATTRIBUTE, IISC_ATT_TOB where IISC_ATTRIBUTE.Att_id=IISC_ATT_TOB.Att_id and Tob_id=" + Tob_id);
            
            while(rs.next())
            {   
                att = new AttValue(-1,rs.getInt(1),rs.getString(2),-1,"", false);
               
                //Ako nije rasporedjen dodaj ga u novu item grupu
                if (!CheckAssigned(att.Att_id))
                {
                    listModel.addElement(att.Att_mnem);
                }
            }
            rs.close();
            
        }
        catch(SQLException sqle)
        {
            System.out.println("Sql exception :" + sqle);
        }  
        
    }
    
    //Provjerava da li attribut vec rasporedjen 
    //Za slucaj da je korisnik dodao novi attribut nekom tipu komponente
    public boolean CheckAssigned(int Att_id)
    {
        int size = datm.data.size();
        ItemGroupItems igi;
        ItemGrop ig;
        AttValue attV;
        
        boolean exists = false;
        
        int i, j;
        
        for(i = 0; i < size && !exists; i++)
        {
           ig = (ItemGrop)datm.data.get(i);
           
           //Prvo provjera da li ima item podgrupu
           igi = (ItemGroupItems)attPanels.get(i);
           
           for(j = 0; j < igi.datm.data.size() && !exists; j++)
           {
               attV = (AttValue) igi.datm.data.get(j);
               
               if (attV.Att_id == Att_id)
               {
                   exists = true;
                   break;
               }
           }
        }
    
        return exists;
    }
    
    public void InitNestedList()
    {
         nestedModel.clear();
         attVec = new Vector();
         attList.setModel(listModel);
         nestedList.setModel(nestedModel);
         
         int size = datm.data.size();
         ItemGroupItems igi;
         ItemGrop ig;
         AttValue attV;
         
         boolean can = true;
         
         int i, j;
         
         for(i = 0; i < size; i++)
         {
            can = true;
            ig = (ItemGrop)datm.data.get(i);
            
            //Prvo provjera da li ima item podgrupu
            igi = (ItemGroupItems)attPanels.get(i);
            
            for(j = 0; j < igi.datm.data.size() && can; j++)
            {
                attV = (AttValue) igi.datm.data.get(j);
                
                if (attV.Att_id == 0)
                {
                    can = false;
                    break;
                }
            }
            
            //Ako nema podgrupu provjeriti da se ne javlja vec kao podgrupa
            if (can)
            {
                for(j = 0; j < datm.data.size() && can; j++)
                {
                    if( i == j )
                    {
                        continue;
                    }
                    igi = (ItemGroupItems)attPanels.get(j);
                    
                    for(int k = 0; k < igi.datm.data.size(); k++)
                    {
                        attV = (AttValue) igi.datm.data.get(k);
                        
                        if (attV.IGI_nes_id == ig.IG_id)
                        {
                            can = false;
                            break;
                        }
                    }
                }
            }
            
            if (can)
            {
                nestedModel.addElement(ig.IG_name);
            }
            
         }
     }
    
    public void InitAttPanels()
    {
        int i;
        attPanels = new Vector();
        ItemGroupItems ig;
        
        for(i = 0; i < datm.data.size(); i++)
        {
            ig = new ItemGroupItems(((ItemGrop)datm.data.get(i)).IG_id, Tob_id, Tf_id, PR_id, ob, conn);
            ig.setBounds(180, 105, 380,195);
            attPanels.add(ig);
        }
    }
    
    public void InitEmptyTable()
    {
        Vector data = new Vector();
        /*int max = 0;
        
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select max(IG_id) from IISC_ITEM_GROUP");
            
            if ( rs.next() )
            {
                max = rs.getInt(1);
            }
            rs.close();
            
        }
        catch(SQLException sqle)
        {
            System.out.println("Sql exception :" + sqle);
        }  
        
        max = max + 1;
        */
        datm.populateData(data);
        data.add(new ItemGrop(-1, "ItemGroup1", "ItemGroup1", 0, 0));
        //data.add(new ItemGrop(-1, "ItemGroup" + max, "ItemGroup" + max, 0, 0));
    }

    private void moveBtn_actionPerformed(ActionEvent e) 
    {
        int selectedAtt = attList.getSelectedIndex();
        
        if ( selectedAtt < 0 )
        {
            return;
        }
        
        int selected = itemTable.getSelectedRow();
        
        if (selected < 0)
        {
            return;
        }
        
        ItemGroupItems igi = (ItemGroupItems)attPanels.get(selected);
        
        String Att_mnem = attList.getSelectedValue().toString();
        int Att_id;
        int IG_id = igi.IG_id;
        
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select Att_id from IISC_ATTRIBUTE where Att_mnem='" + Att_mnem + "'");
            
            if(rs.next())
            {   
                Att_id = rs.getInt(1);
            }
            else
            {
                rs.close();
                return;
            }
            rs.close();
            
            rs = statement.executeQuery("select IGI_id from IISC_IG_ITEM where Att_id=" + Att_id + " and Tob_id=" + Tob_id + " and Tf_id=" + Tf_id);
            
            if(rs.next())
            {   
                igi.datm.data.add(new AttValue(rs.getInt(1), Att_id, Att_mnem, 0, "", false));
                listModel.remove(selectedAtt);
                igi.datm.fireTableDataChanged();
            }
            else
            {
                igi.datm.data.add(new AttValue(-1, Att_id, Att_mnem, 0, "", false));
                listModel.remove(selectedAtt);
                igi.datm.fireTableDataChanged();
                EnableButtons();
            }
            rs.close();
            
            
            
        }
        catch(SQLException sqle)
        {
            System.out.println("Sql exception :" + sqle);
            return;
        }  
        
        if (listModel.size() == 0)
        {
            return;
        }
        
        if (selectedAtt == listModel.size())
        {
            selectedAtt = selectedAtt - 1;
        }
        
        attList.getSelectionModel().setSelectionInterval(selectedAtt, selectedAtt);
        
        /*ItemGroupItems ig = (ItemGroupItems)attPanels.get(0);
        Vector temp = ig.GetData();
        AttValue att;
        
        for(int i = 0; i < temp.size(); i++)
        {
            att = (AttValue) temp.get(i);
            System.out.print("\n" + att.breakLine);
        }*/
    }

    private void addBtn_actionPerformed(ActionEvent e) 
    {
        AddNewItemGroup();
    }

    private void AddNewItemGroup()
    {
        ItemGrop newIg = new ItemGrop(-1, "", "", 0, 0);
        ItemGrpFrm igfrm = new ItemGrpFrm(newIg, ob);
        Settings.Center(igfrm);
        
        igfrm.setVisible(true);
        
        if ( igfrm.option == 1)
        {
            ItemGroupItems igitems = new ItemGroupItems(-1, Tob_id, Tf_id, Tf_id, ob, conn);
            igitems.setBounds(178, 105, 190,195);
            nestedModel.addElement(igfrm.ig.IG_name);
            attPanels.add(igitems);
            datm.data.add(igfrm.ig);
            datm.fireTableDataChanged();
            itemTable.getSelectionModel().setSelectionInterval(selectedIndex,selectedIndex);
            EnableButtons();
        }
        
        igfrm.dispose();
    }

    private void editBtn_actionPerformed(ActionEvent e)
    {
        int selected = itemTable.getSelectedRow();
        
        if ( selected < 0)
        {
            return;
        }
        
        ItemGrop ig = (ItemGrop)datm.data.get(selected);
        ItemGrpFrm igfrm = new ItemGrpFrm(ig, ob);
        Settings.Center(igfrm);
        
        igfrm.setVisible(true);
        
        if ( igfrm.option == 1)
        {
            //tu treba kod koji ce da promijeni mnemonik kod svih item podgupa ma gdje da se nalaze
            //Novo ime za item grupu
            ItemGrop temp = igfrm.ig;
            ChangeNameOfNestedItemGroup(temp.IG_name, ig.IG_name);
            
            datm.data.setElementAt(igfrm.ig, selected);
            datm.fireTableDataChanged();
            itemTable.getSelectionModel().setSelectionInterval(selectedIndex ,selectedIndex);
            EnableButtons();
        }
        
        igfrm.dispose();
        
        
    }

    //Vrati neki atribut tako da bude nerasporedjen
    private void backBtn_actionPerformed(ActionEvent e) 
    {
        int selectedInd = itemTable.getSelectedRow();
        
        if ( selectedInd < 0 )
        {
            return;
        }
        
        ItemGroupItems ig = (ItemGroupItems)attPanels.get(selectedInd);
        
        selectedInd = ig.table.getSelectedRow();
        
        if ( selectedInd < 0 )
        {
            return;
        }
        
        AttValue at = (AttValue)ig.datm.data.get(selectedInd);
        
        
        if (at.Att_id == 0)
        {
            return;
        }
        
        ig.datm.data.remove(selectedInd);
        
        if (selectedInd == ig.datm.data.size())
        {
            selectedInd = selectedInd - 1;
        }
        
        listModel.addElement(at.Att_mnem);
        
        
        //Ako je ima nesto u tabeli
        if (ig.datm.data.size() > 0)
        {
            ig.table.getSelectionModel().setSelectionInterval(selectedInd, selectedInd+1);
        }
        
        ig.datm.fireTableDataChanged();
        ig.table.repaint();
        EnableButtons();
        attList.repaint();
    }
    
    
    //Vrati neki atribut tako da bude nerasporedjen
    private void remove_actionPerformed(ActionEvent e) 
    {
        int selectedInd = itemTable.getSelectedRow();
        
        if ( selectedInd < 0 )
        {
            return;
        }
        
        int size = datm.data.size();
        
        if (size == 1)
        {
            JOptionPane.showMessageDialog(this, "Item group can not be removed","Error",JOptionPane.ERROR_MESSAGE );
            return;
        }
        
        AttValue attV;
        
        //Ako je item grupa uklonjena vrati sve attribute u nerasporedjene 
        ItemGroupItems ig = (ItemGroupItems)attPanels.get(selectedInd);
        String igName = ((ItemGrop)datm.data.get(selectedInd)).IG_name;
        
        if (((ItemGrop)datm.data.get(selectedInd)).IG_id > -1 )
        {
            removedIG.addElement(new Integer(((ItemGrop)datm.data.get(selectedInd)).IG_id));
        }
        
        int i;
        
        //Prodji kroz sve element
        size = ig.datm.data.size();
        
        for(i = 0; i < size; i++)         
        {
            attV = (AttValue) ig.datm.data.get(i);
            
            //Provjeri da li se radi o attributu
            if (attV.Att_id != 0)
            {
                listModel.addElement(attV.Att_mnem);
            }
            else
            {
                nestedModel.addElement(attV.igName);
            }   
        }
        
        //Probaj da vidis da ona sama nije item podgrupa
        //provjeri da nije jedini potomak svog roditelja
        datm.data.remove(selectedInd);
        nestedModel.removeElement(igName);
        size = datm.data.size();
        int k;
        ItemGroupItems igi;
        boolean removed = false;//Indikator da li je ta iteg grupa bila podgrupa neke druge
        int count = 0;
        int index = 0;
        
        AttValue att;
        
        for(i = 0; i < size & !removed; i++)
        {
            igi = (ItemGroupItems)attPanels.get(i);
            count = 0;
            
            for(k = 0; k < igi.datm.data.size(); k++)
            {
                att = (AttValue)igi.datm.data.get(k);
                
                if (att.Att_id == 0)
                {
                    count = count + 1;
                    
                    if (att.igName == igName)
                    {
                        removed = true;    
                        index = k;
                    }
                }  
            }
            
            //Ako je nadjen onda treba da se ukloni iz tog panela
            if (removed)   
            {
                igi.datm.data.remove(index);
                
                //Ako je jedina item podgrupa
                if (count == 1)
                {
                    //Nema vise item podgrupa
                    nestedModel.addElement(((ItemGrop)datm.data.get(i)).IG_name);
                }
            }
            
        }
        
        remove((ItemGroupItems)attPanels.get(selectedInd));
        attPanels.removeElementAt(selectedInd);
        
        if (selectedInd == datm.data.size())
        {
            selectedInd = selectedInd - 1;
        }
        
        add((ItemGroupItems)attPanels.get(selectedInd));
        selectedIndex = selectedInd;
        
        datm.fireTableDataChanged();
        itemTable.getSelectionModel().setSelectionInterval(selectedIndex,selectedIndex);
        EnableButtons();
        revalidate();
        repaint();
    }
    
    private void ChangeNameOfNestedItemGroup(String newName, String oldName)
    {
        ItemGroupItems igi;
        
        int size = nestedModel.size();
        int i;
        
        for(i = 0; i < size; i++)
        {
            if (nestedModel.getElementAt(i).toString().equals(oldName))
            {
                nestedModel.setElementAt(newName,i);
                break;
            }
        }
        
        //ItemGrop ig;
        AttValue attV;
        int k;
        
        size = datm.data.size();
        
        //Prolazak kroz sve panele da se odredi da li je 
        //ta item grupa podgrupa neke druge
        for(i = 0; i < size; i++)
        {
           igi = (ItemGroupItems)attPanels.get(i);
           
           for(k = 0; k < igi.datm.data.size(); k++)
           {
               attV = (AttValue) igi.datm.data.get(k);
               
               if (attV.igName == oldName)
               {
                   attV.igName = newName;
                   //igi.datm.data.setElementAt(attV,k);
                   break;
               }
           }
        }
    }
    
    private void moveNesBtn_actionPerformed(ActionEvent e) 
    {
        //provjera da li se radi o item podgrupi
        //Ako je to slucaj onda ona ne moze da ima svoje poditeme
         int selectedAtt = nestedList.getSelectedIndex();
         
         if ( selectedAtt < 0 )
         {
             return;
         }
         
         int selected = itemTable.getSelectedRow();
         
         if (selected < 0)
         {
             return;
         
         }
         
         ItemGroupItems igi;
         int size = datm.data.size();
         ItemGrop ig;
         AttValue attV;
         
         String nesName = nestedList.getSelectedValue().toString();
         int nesId = -1;
         
         boolean can = true;
         
         int i, k;
         
         ig = (ItemGrop)datm.data.get(selected);
        
         //Prvo provjera da li je selectovana item grupa ujedno i podgrupa
         for(i = 0; i < size && can; i++)
         {
            igi = (ItemGroupItems)attPanels.get(i);
            
            for(k = 0; k < igi.datm.data.size() && can; k++)
            {
                attV = (AttValue) igi.datm.data.get(k);
                
                if (attV.igName.equals(nesName))
                {
                    can = false;
                    break;
                }
            }
             
        }        
        
        //Ako vec nije item podgrupa
        if (can)
        {
            //Nadji ID odabranog
            for(i = 0; i < size; i++)
            {
               if (((ItemGrop)datm.data.get(i)).IG_name.equals(nesName))
               {
                    nesId = ((ItemGrop)datm.data.get(i)).IG_id;
                    break;
               }
            }
            
            //Da ne moze biti item podgrupa same sebe
            if (ig.IG_name.equals(nesName))
            {
                return;
            }
            
            EnableButtons();
            removeNested.remove(Integer.toString(nesId));
                       
            try
            {
                Statement statement = conn.createStatement();
                ResultSet rs;
                
                rs = statement.executeQuery("select IGI_id from IISC_IG_ITEM where IG_nested_group_id=" + nesId);
                igi = (ItemGroupItems)attPanels.get(selected);
                
                if(rs.next())
                {   
                    igi.datm.data.add(new AttValue(rs.getInt(1), 0, "", nesId, nesName, false));
                    igi.datm.fireTableDataChanged();
                }
                else
                {
                    igi.datm.data.add(new AttValue(-1,0, "", nesId, nesName, false));
                    igi.datm.fireTableDataChanged();
                }
                rs.close();
                System.out.print(ig.IG_name);
                nestedModel.removeElement(nesName);
                nestedModel.removeElement(ig.IG_name);
                
            }
            catch(SQLException sqle)
            {
                System.out.println("Sql exception :" + sqle);
                return;
            }  
            
            if (selectedAtt >= nestedModel.size())
            {
                selectedAtt = nestedModel.size() - 1;
            }
            
            nestedList.getSelectionModel().setSelectionInterval(selectedAtt, selectedAtt);   
            
        }
    }

    private void backNesBtn_actionPerformed(ActionEvent e)
    {   
        int selected = itemTable.getSelectedRow();
        
        if (selected < 0)
        {
            return;
        }
        
        ItemGroupItems igi;
        ItemGrop ig;
        AttValue attV;
        
        boolean can = true;
        
        int i, k;
        
        ig = (ItemGrop)datm.data.get(selected);
        
        igi = (ItemGroupItems)attPanels.get(selected);
        
        int selNesItem = igi.table.getSelectedRow();
        
        if (selNesItem < 0)
        {
            return;
        }
        
        attV = (AttValue) igi.datm.data.get(selNesItem);
        
        if (attV.IGI_nes_id == 0)
        {
            return;
        }
        
        //Ako je to item grupa koja postoji u bazi podataka onda je dodaj u spisak za brisanje
        if (attV.IGI_id != -1)
        {
            removeNested.add(Integer.toString(attV.IGI_id));
        }
        
        //removeNested.add(attV.IGI_id);
        
        nestedModel.addElement(attV.igName);
        EnableButtons();
        igi.datm.data.remove(selNesItem);
        igi.datm.fireTableDataChanged();
        
        for(k = 0; k < igi.datm.data.size() && can; k++)
        {
            attV = (AttValue) igi.datm.data.get(k);
             
            if (attV.IGI_nes_id != 0)
            {
                 can = false;
                 break;
            }
        }
         
        //Ako vec nije item podgrupa
        if (can)
        {
            nestedModel.addElement(ig.IG_name);      
        }
        RemoveCurrentItemGroupFromNested();
    }
    
    private void EnableButtons()
    {
        ob.btnApply.setEnabled(true);
        ob.btnSave.setEnabled(true);
    }
    //Provjera da li su korisnicke specifikacije validne
    //Ako jesu vraca prazan string a ako ne vraca poruku o greski
    public String CheckValid()
    {   
    
        //Provjera da li je korisnik uopste definisao neku item grupu
        if (datm.data.size() == 0)
        {
            return "Define at least one item group";
        }
        
        //Ako je neki od atributa ostao neraspordjen onda prijaviri gresku
        if (listModel.getSize() > 0)
        {
           return "Attribute " + listModel.getElementAt(0) + " must asigned to some item group";     
        }
        
        if (Tob_id > -1)
        {
            for(int i = 0; i < datm.data.size(); i++)
            {
                //Ako je neka item grupa prazna regulisati 
                if (((ItemGroupItems)attPanels.get(i)).datm.data.size() == 0)
                {
                    return "Item gruoup " + ((ItemGrop)datm.data.get(i)).IG_name + " can not be empty";
                }
            }
        }
        return "";
    }
    
    //Realizuje operaciju azuriranja item grupa u repozitorijumu
    public void Update()
    {
        try
        {
            Statement statement = conn.createStatement();
            //ResultSet rs = statement.executeQuery("select Att_id from IISC_ATTRIBUTE where Att_mnem='" + Att_mnem + "'");
            int i = 0, j, k;
            
            //Obrisi one item grupe koje su removed
            for(i  = 0; i < removedIG.size(); i++)
            {
                //System.out.println("delete from IISC_ITEM_GROUP where IG_id=" + removedIG.get(i).toString());
                statement.execute("delete from IISC_ITEM_GROUP where IG_id=" + removedIG.get(i).toString());
                statement.execute("delete from IISC_IG_ITEM where IG_nested_group_id=" + removedIG.get(i).toString());
                statement.execute("delete from IISC_IG_ITEM where IG_nested_group_id=" + removedIG.get(i).toString());
                
            }
            
            for(i  = 0; i < removeNested.size(); i++)
            {
                statement.execute("delete from IISC_IG_ITEM where IGI_id=" + removeNested.get(i).toString());
            }
            
            ResultSet rs;
            int maxIg = 0;
            
            try
            {
                rs = statement.executeQuery("select max(IG_id) from IISC_ITEM_GROUP");
                
                if (rs.next())
                {
                    maxIg = rs.getInt(1);
                }
                else
                {
                    maxIg  = 0;
                }
            }
            catch(Exception e)
            {
                maxIg = 0;
            }
            
            maxIg = maxIg + 1;
            
            ItemGrop ig;
            
            //One koje su nove treba dodati u bazi
            //Mislim da je bag u parajzu
            String sql = "";
            boolean removed; 
            
            for(i = 0; i < datm.data.size(); i ++ )
            {
                ig = (ItemGrop)datm.data.get(i);
                
                if (ig.IG_id == -1)//Potrebno je uraditi insert 
                {
                    sql = "insert into IISC_ITEM_GROUP(IG_id,PR_id,TF_id,TOB_id,IG_name,IG_title,IG_sequence,IG_contex,IG_overflow) values(";
                    sql = sql + maxIg + "," + PR_id + "," + Tf_id + "," + Tob_id + ",'" + ig.IG_name + "','" + ig.IG_title + "'," + i + "," + ig.IG_context + "," + ig.IG_overflow + ")";
                    maxIg = maxIg + 1;
                    
                    //System.out.println("Upit je " + sql);
                    statement.execute(sql);
                    
                    //Poslije inserta treba update uraditi od nestedid
                    ig.IG_id = maxIg - 1;
                    
                    removed = false;
                    ItemGroupItems igi;
                    AttValue att;
                    
                    //Petlja koja prolazi provjerava da li je nova item grupa ujedno 
                    //i podgrupa i ako jeste onda se dodjeljuje njen novo-generisani ID
                    for(j = 0; !removed && j < datm.data.size(); j++)
                    {
                        igi = (ItemGroupItems)attPanels.get(j);
                        
                        for(k = 0; !removed && k < igi.datm.data.size(); k++)
                        {
                            att = (AttValue)igi.datm.data.get(k);
                            
                            if(att.igName == ig.IG_name)
                            {
                                att.IGI_nes_id = ig.IG_id;
                                removed = true;
                            }
                        }
                    }
                }
                else //Radi se update 
                {
                    sql = "update IISC_ITEM_GROUP set IG_name='" + ig.IG_name + "',IG_title='" + ig.IG_title  + "',IG_sequence=" + i + ",IG_contex=" + ig.IG_context + ",IG_overflow=" + ig.IG_overflow;
                    sql += " where IG_id=" + ig.IG_id;
                    //System.out.println("Upit je " + sql);
                    statement.execute(sql);
                    //prodji kroz sve podgrupe i promijeni ID
                }
                
               
                
            }
            //Upadate detalja
             
            try  //Generisi sekvencu 
            {
                 rs = statement.executeQuery("select max(IGI_id) from IISC_IG_ITEM");
                 
                 if (rs.next())
                 {
                     maxIg = rs.getInt(1);
                 }
                 else
                 {
                     maxIg  = 0;
                 }
             }
             catch(Exception e)
             {
                 maxIg = 0;
             }
             
             maxIg = maxIg + 1;
             
             ItemGroupItems igi;
             
             AttValue att;
             
             for(j = 0; j < datm.data.size(); j++)
             {
                 ig = (ItemGrop)datm.data.get(j);
                 igi = (ItemGroupItems)attPanels.get(j);
                 
                 for(k = 0; k < igi.datm.data.size(); k++)
                 {
                     att = (AttValue)igi.datm.data.get(k);
                     
                     if(att.Att_id > 0)//Radi se samo o atributu
                     {
                         //Ako je nova item podgrupa mora da se radi insert   
                         if (att.IGI_id == -1)
                         {
                             sql = "insert into IISC_IG_ITEM(IGI_id,IG_id,PR_id,TF_id,TOB_id,Att_id,IG_nested_group_id,IGI_order,IGI_breakline) values(";
                             if (att.breakLine)
                             {
                                sql = sql + maxIg + "," + ig.IG_id + "," + PR_id + "," + Tf_id + "," + Tob_id + "," + att.Att_id + "," + att.IGI_nes_id + "," + k + "," + 1 + ")";
                             }
                             else
                             {
                                 sql = sql + maxIg + "," + ig.IG_id + "," + PR_id + "," + Tf_id + "," + Tob_id + "," + att.Att_id + "," + att.IGI_nes_id + "," + k + "," + 0 + ")";
                             }
                             att.IGI_id = maxIg;
                             
                             maxIg = maxIg + 1;
                             
                             statement.execute(sql);
                         }
                         else //Radi se update item grupe
                         {
                             sql = "update IISC_IG_ITEM set IGI_order = " + k + ",IG_id=" + ig.IG_id + ",";
                             
                             if (att.breakLine)
                             {
                                sql = sql + "IGI_breakline=" + 1;
                             }
                             else
                             {
                                 sql = sql + "IGI_breakline=" + 0;
                             }
                             
                             sql = sql + " where IGI_id=" + att.IGI_id;
                             
                             //System.out.println(sql);
                             
                             statement.execute(sql);
                         }
                     }
                     else//Radi se o item podgrupi
                     {
                         //Ako je nova item podgrupa mora da se radi insert   
                         if (att.IGI_id == -1)
                         {
                             sql = "insert into IISC_IG_ITEM(IGI_id,IG_id,PR_id,TF_id,TOB_id,Att_id,IG_nested_group_id,IGI_order,IGI_breakline) values(";
                             if (att.breakLine)
                             {
                                sql = sql + maxIg + "," + ig.IG_id + "," + PR_id + "," + Tf_id + "," + Tob_id + "," + att.Att_id + "," + att.IGI_nes_id + "," + k + "," + 1 + ")";
                             }
                             else
                             {
                                 sql = sql + maxIg + "," + ig.IG_id + "," + PR_id + "," + Tf_id + "," + Tob_id + "," + att.Att_id + "," + att.IGI_nes_id + "," + k + "," + 0 + ")";
                             }
                             att.IGI_id = maxIg;
                             
                             maxIg = maxIg + 1;
                             
                             //System.out.println(sql);
                             statement.execute(sql);
                         }
                         else //Radi se update item grupe
                         {
                             sql = "update IISC_IG_ITEM set IGI_order = " + k + ",IG_id=" + ig.IG_id + ",";
                             
                             if (att.breakLine)
                             {
                                sql = sql + "IGI_breakline=" + 1;
                             }
                             else
                             {
                                 sql = sql + "IGI_breakline=" + 0;
                             }
                             
                             sql = sql + " where IGI_id=" + att.IGI_id;
                             statement.execute(sql);
                         }
                     }
                 }
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }

    //Promjena ordera
    private void btnUp_actionPerformed(ActionEvent e) 
    {
        selectedIndex = itemTable.getSelectedRow();
        datm.IncrementRow(selectedIndex);
        
        if (selectedIndex > 0)
        {
            EnableButtons();
                        
            ItemGroupItems temp1 = (ItemGroupItems)attPanels.get(selectedIndex);
            selectedIndex = selectedIndex - 1;
            ItemGroupItems temp2 = (ItemGroupItems)attPanels.get(selectedIndex);
            attPanels.setElementAt(temp1, selectedIndex);
            attPanels.setElementAt(temp2, selectedIndex + 1);
            
            itemTable.getSelectionModel().setSelectionInterval(selectedIndex ,selectedIndex );        
        }
        itemTable.repaint();
    }

    private void btnDown_actionPerformed(ActionEvent e) 
    {
        selectedIndex = itemTable.getSelectedRow();
        datm.DecrementRow(selectedIndex);
        
        //Ako je poslednji 
        if (selectedIndex < datm.data.size() - 1)
        {
            EnableButtons();
                        
            ItemGroupItems temp1 = (ItemGroupItems)attPanels.get(selectedIndex);
            selectedIndex = selectedIndex + 1;
            ItemGroupItems temp2 = (ItemGroupItems)attPanels.get(selectedIndex);
            attPanels.setElementAt(temp1, selectedIndex);
            attPanels.setElementAt(temp2, selectedIndex - 1);
            
            itemTable.getSelectionModel().setSelectionInterval(selectedIndex ,selectedIndex );        
        }
        itemTable.repaint();
    }


    public class ItemGrop 
    {
        public int IG_id;
        public String IG_name;
        public String IG_title;
        public int IG_context;
        public int IG_overflow;
        
        public ItemGrop(int _IG_id, String _IG_name, String _IG_title, int _IG_context, int _IG_overflow) 
        {   
            IG_id = _IG_id;
            IG_name = _IG_name;
            IG_title = _IG_title;
            IG_context = _IG_context;
            IG_overflow = _IG_overflow;
        }
    }
    
    public class ItemTableModel extends AbstractTableModel 
    {
        private String[] headers;
        public Vector data;
        
        public ItemTableModel()
        {
            headers = new String[4];
            data = new Vector();
            headers[0] = "Name";
            headers[1] = "Title";
            headers[2] = "Context";
            headers[3] = "Overflow";
        }
        
        public int getColumnCount()
        {
            return 4;
        }
        
        public String getColumnName(int index)
        {
            return headers[index];
        }
        
        public int getRowCount()
        {
            return data.size();
        }
        
        public Object getValueAt(int x, int y)
        {
            ItemGrop value = (ItemGrop)data.get(x);
        
            if (y == 0)
            { 
                return value.IG_name;
            } 
            else
            {
                if (y == 1)
                {
                    return value.IG_title;
                }
                else
                {
                    if (y == 2)
                    {
                        if ( value.IG_context == 0 )
                        {
                            return "No";
                        }
                        else
                        {
                            return "Yes";
                        }
                    }
                    else
                    {
                        if ( value.IG_overflow == 0 )
                        {
                            return "No";
                        }
                        else
                        {
                            return "Yes";
                        }
                    }
                }
            }
        }
        
        public void setValueAt(Object value, int row, int col) 
        {
        
            if (col == 0)
            { 
                ((ItemGrop)data.elementAt(row)).IG_name = ((String)value);
                fireTableCellUpdated(row, col);
            } 
            else
            {
                if (col == 1)
                {
                    ((ItemGrop)data.elementAt(row)).IG_title = ((String)value);
                    fireTableCellUpdated(row, col);
                }
                else
                {
                    if (col == 2)
                    {
                        ((ItemGrop)data.elementAt(row)).IG_context = ((Integer)value).intValue();
                        fireTableCellUpdated(row, col);
                    }
                    else
                    {
                        ((ItemGrop)data.elementAt(row)).IG_overflow = ((Integer)value).intValue();
                        fireTableCellUpdated(row, col);
                    }
                }
            }
        }
        
        public void populateData(Vector value)
        {
            data = value;
            fireTableDataChanged();
        }
        
        public boolean isCellEditable(int row, int col) 
        {
            return false;
        }    
        
        public void IncrementRow(int rowIndex)
        {
            if (rowIndex == 0) 
            {
                return;
            }
            
            ItemGrop temp1 = (ItemGrop)data.get(rowIndex);
            ItemGrop temp2 = (ItemGrop)data.get(rowIndex - 1);
            
            data.set(rowIndex, temp2);
            data.set(rowIndex - 1, temp1);
            fireTableDataChanged();
        }
        
        public void DecrementRow(int rowIndex)
        {
            if (rowIndex + 1 == data.size()) 
            {
                return;
            }
            
            ItemGrop temp1 = (ItemGrop)data.get(rowIndex);
            ItemGrop temp2 = (ItemGrop)data.get(rowIndex + 1);
            
            data.set(rowIndex, temp2);
            data.set(rowIndex + 1, temp1);
            fireTableDataChanged();
        }
    }
    
    public class ItemGroupItems extends JPanel 
    {
        private ItemTableModel datm = new ItemTableModel();
        private JTable table = new JTable(datm);
        private JScrollPane tableSp = new JScrollPane(table);
        int Tob_id, Tf_id, PR_id, IG_id;
        ObjectType ob;
        Connection conn;
        private JButton btnUp = new JButton();
        private ImageIcon iconUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
        private ImageIcon iconDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
        private JButton btnDown = new JButton();
        public CheckBoxRenderer cr = new CheckBoxRenderer();
        
        public ItemGroupItems(int _IG_id, int _Tob_id, int _Tf_id, int _PR_id, ObjectType _ob, Connection _conn) 
        {
            Tob_id = _Tob_id;
            Tf_id = _Tf_id;
            PR_id = _PR_id;
            ob = _ob;
            conn = _conn;
            IG_id = _IG_id;
            
            try 
            {
                jbInit();
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
            
            InitTable();
        }
        
        public ItemGroupItems() 
        {
            try 
            {
                jbInit();
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }

        public Vector GetData()
        {
            return datm.data;
        }
        
        public void SetData(Vector vec)
        {
            datm.data = vec;
            table.repaint();
        }
        
        private void jbInit() throws Exception 
        {
            setLayout(null);

            this.setSize(192, 195);
            //tableSp.setSize(new Dimension(20, 45));

            tableSp.setBounds(0, 0, 162, 195);
            btnUp.setMaximumSize(new Dimension(60, 60));
            btnUp.setPreferredSize(new Dimension(25, 20));
            btnUp.setIcon(iconUp);
            btnUp.setSize(new Dimension(20, 25));

            btnUp.setBounds(166, 0, 25, 20);
            btnUp.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {
                    btnUp_actionPerformed(e);
                }
            });
            
            btnDown.setIcon(iconDown);
            
                    
            btnDown.setSize(new Dimension(25, 20));
            btnDown.setBounds(166, 175, 25, 20);
            btnDown.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {
                    btnDown_actionPerformed(e);
                }
            });
            
            this.add(btnUp, null);
            this.add(btnDown, null);
            
            tableSp.getViewport().add(table);
            add(tableSp, null);
            
        }
        
        private void btnDown_actionPerformed(ActionEvent e)
        {
            int rowIndex = table.getSelectedRow();
            datm.DecrementRow(rowIndex);
            
            if (rowIndex + 1 < datm.data.size())
            {
                EnableButtons();
                table.getSelectionModel().setSelectionInterval(rowIndex + 1,rowIndex + 1);
            }
        }
        
        private void btnUp_actionPerformed(ActionEvent e)
        {
            int rowIndex = table.getSelectedRow();
            datm.IncrementRow(rowIndex);
            
            if (rowIndex > 0)
            {
                EnableButtons();
                table.getSelectionModel().setSelectionInterval(rowIndex - 1,rowIndex - 1);
            }
            table.repaint();
        }
        
        public void InitTable()
        {
            Vector data = new Vector();
            int breakLine;
            
            try
            {
                Statement statement = conn.createStatement();
                Statement statement2 = conn.createStatement();
               
                
                ResultSet rs = statement.executeQuery("select * from IISC_IG_ITEM where  IG_id=" + IG_id + " order by IGI_order");
                
                int Att_id;
                int neIg_id;
                String Att_mnem = "";
                String igName = "";
                
                while(rs.next())
                {   
                    Att_id = rs.getInt("Att_id");
                    neIg_id = rs.getInt("IG_nested_group_id");
                   
                    
                    if ( Att_id == 0 )
                    {
                        Att_mnem = "";
                        ResultSet rs2 = statement2.executeQuery("select IG_name from IISC_ITEM_GROUP where IG_id=" + neIg_id);
                        if (rs2.next())
                        {
                            igName = rs2.getString(1);
                        }
                    }
                    else
                    {
                        igName = "";
                        ResultSet rs2 = statement2.executeQuery("select Att_mnem from IISC_ATTRIBUTE where Att_id=" + Att_id);
                        
                        if (rs2.next())
                        {
                            Att_mnem = rs2.getString(1);
                        }
                    }
                    breakLine = rs.getInt("IGI_breakline");
                    
                    if ( breakLine  == 0 )
                    {
                        data.add(new AttValue(rs.getInt("IGI_id"), Att_id, Att_mnem, neIg_id, igName, false));
                    }
                    else
                    {
                        data.add(new AttValue(rs.getInt("IGI_id"), Att_id, Att_mnem, neIg_id, igName, true));
                    }
                }
                rs.close();
                
            }
            catch(SQLException sqle)
            {
                System.out.println("Sql exception :" + sqle);
            }  
            datm.populateData(data);
            
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setRowSelectionAllowed(true);
            table.setGridColor(new Color(0,0,0));
            table.setBackground(Color.white);
            table.getTableHeader().setReorderingAllowed(false);
            table.setAutoscrolls(true);
            table.getTableHeader().setResizingAllowed(true);
            table.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            table.setPreferredScrollableViewportSize(new Dimension(500, 70));
            table.getColumn("Breakline").setCellRenderer(cr);
            JCheckBox checkBox = new JCheckBox();
            checkBox.addItemListener(new ItemListener()
            {

                public void itemStateChanged(ItemEvent e) 
                {
                    EnableButtons();
                }
            });
            checkBox.setHorizontalAlignment(JLabel.CENTER);
            DefaultCellEditor checkBoxEditor = new DefaultCellEditor(checkBox);
            table.getColumn("Breakline").setCellEditor(checkBoxEditor);
            table.getColumn("Breakline").setMaxWidth(70);
            table.repaint();
        }
        
        public class ItemTableModel extends AbstractTableModel 
        {
            private String[] headers;
            public Vector data;
            
            public ItemTableModel()
            {
                headers = new String[2];
                data = new Vector();
                headers[0] = "Attribute";
                headers[1] = "Breakline";
            }
            
            public int getColumnCount()
            {
                return 2;
            }
            
            public String getColumnName(int index)
            {
                return headers[index];
            }
            
            public int getRowCount()
            {
                return data.size();
            }
            
            public Object getValueAt(int x, int y)
            {
                AttValue value = (AttValue)data.get(x);
            
                if (y == 0)
                { 
                    if (value.Att_id == 0)
                    {
                        return value.igName;
                    }
                    else
                    {
                        return value.Att_mnem;
                    }
                } 
                else
                {
                    
                     return Boolean.valueOf(value.breakLine);
                }
            }
            
            public void setValueAt(Object value, int row, int col) 
            {
            
                if (col == 0)
                { 
                    ((AttValue)data.elementAt(row)).Att_mnem = ((String)value);
                    fireTableCellUpdated(row, col);
                } 
                else
                {
                    ((AttValue)data.elementAt(row)).breakLine = ((Boolean)value).booleanValue();
                    fireTableCellUpdated(row, col);
                }
            }
            
            public void populateData(Vector value)
            {
                data = value;
                fireTableDataChanged();
            }
            
            public boolean isCellEditable(int row, int col) 
            {
                
                if ( col == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }    
            
            public void IncrementRow(int rowIndex)
            {
                if (rowIndex == 0) 
                {
                    return;
                }
                
                AttValue temp1 = (AttValue)data.get(rowIndex);
                AttValue temp2 = (AttValue)data.get(rowIndex - 1);
                
                data.set(rowIndex, temp2);
                data.set(rowIndex - 1, temp1);
                fireTableDataChanged();
            }
            
            public void DecrementRow(int rowIndex)
            {
                if (rowIndex + 1 == data.size()) 
                {
                    return;
                }
                
                AttValue temp1 = (AttValue)data.get(rowIndex);
                AttValue temp2 = (AttValue)data.get(rowIndex + 1);
                
                data.set(rowIndex, temp2);
                data.set(rowIndex + 1, temp1);
                fireTableDataChanged();
            }
        }
        
        
        private class CheckBoxRenderer extends JCheckBox implements TableCellRenderer 
        {

          CheckBoxRenderer() 
          {
            setHorizontalAlignment(JLabel.CENTER);
          }

          public Component getTableCellRendererComponent(JTable table, Object value,
              boolean isSelected, boolean hasFocus, int row, int column) 
         {
            if (isSelected) 
            {
              setForeground(table.getSelectionForeground());
              //super.setBackground(table.getSelectionBackground());
              setBackground(table.getSelectionBackground());
            } 
            else 
            {
              setForeground(table.getForeground());
              setBackground(table.getBackground());
            }
            setSelected((value != null && ((Boolean) value).booleanValue()));
            return this;
          }
        }
    }
    
    public class AttValue
    {
        public int Att_id;
        public String Att_mnem;
        public boolean breakLine = false;
        public int IGI_id = -1;
        public int IGI_nes_id = -1;
        public String igName;
        
        public AttValue(int _Att_id, String _Att_mnem)
        {
            Att_id = _Att_id;
            Att_mnem = _Att_mnem;
        }
        
        public AttValue(int _IGI_id, int _Att_id, String _Att_mnem, int _IGI_nes_id, String _igName, boolean _breakLine)
        {
            Att_id = _Att_id;
            Att_mnem = _Att_mnem;
            IGI_id = _IGI_id;
            IGI_nes_id = _IGI_nes_id;
            breakLine = _breakLine;
            igName = _igName;
        }
    }
    
    public class ItemGrpFrm extends JDialog
    {
         private JLabel jLabel1 = new JLabel();
         private JLabel jLabel2 = new JLabel();
         private JLabel jLabel3 = new JLabel();
         private JLabel jLabel4 = new JLabel();
         private JLabel jLabel5 = new JLabel();
         
         private JTextField nameTxt = new JTextField();
         private JTextField titleTxt = new JTextField();
         private JCheckBox contexChb = new JCheckBox();
         private JCheckBox overflowChb = new JCheckBox();
         
         private JButton okBtn = new JButton();
         private JButton cancelBtn = new JButton();
         private int option = 0;
         private ItemGrop ig;
         
         public ItemGrpFrm(ItemGrop _ig, ObjectType ob) 
         {
             super(ob, "Item Group", true);
             try 
             {
                 jbInit();
             } 
             catch (Exception e) 
             {
                 e.printStackTrace();
             }
             ig = _ig;
             
             nameTxt.setText(ig.IG_name);
             titleTxt.setText(ig.IG_title);
             
             if (ig.IG_context == 1)
             {
                contexChb.setSelected(true);
             }
             
             if (ig.IG_overflow == 1)
             {
                overflowChb.setEnabled(true);
             }
         }

         private void jbInit() throws Exception 
         {
             setLayout(null);
             this.setSize(new Dimension(281, 265));
             jLabel1.setText("Item Group");
             jLabel1.setBounds(new Rectangle(10, 10, 85, 15));
             jLabel1.setFont(new Font("SansSerif", 1, 12));
             jLabel2.setText("Name");
             jLabel2.setBounds(new Rectangle(10, 40, 80, 20));
             nameTxt.setBounds(new Rectangle(75, 40, 185, 20));
             jLabel3.setText("Title");
             jLabel3.setBounds(new Rectangle(10, 80, 65, 15));
             titleTxt.setBounds(new Rectangle(75, 75, 185, 20));
             jLabel4.setText("Contex");
             jLabel4.setBounds(new Rectangle(75, 125, 80, 15));
             contexChb.setText("");
             contexChb.setBounds(new Rectangle(165, 120, 20, 20));
             overflowChb.setText("");
             overflowChb.setBounds(new Rectangle(165, 155, 25, 15));
             jLabel5.setText("Overflow");
             jLabel5.setBounds(new Rectangle(75, 155, 45, 15));
             okBtn.setText("Ok");
             okBtn.setBounds(new Rectangle(40, 195, 75, 25));
             okBtn.addActionListener(new ActionListener() 
             {
                   public void actionPerformed(ActionEvent e) 
                   {
                         okBtn_actionPerformed(e);
                   }
            });
             cancelBtn.setText("Cancel");
             cancelBtn.setBounds(new Rectangle(145, 195, 75, 25));
             cancelBtn.addActionListener(new ActionListener()
             {
                public void actionPerformed(ActionEvent e) 
                {
                    cancelBtn_actionPerformed(e);
                }
             });
             
             this.getContentPane().add(cancelBtn, null);
             this.getContentPane().add(okBtn, null);
             this.getContentPane().add(jLabel5, null);
             this.getContentPane().add(overflowChb, null);
             this.getContentPane().add(contexChb, null);
             this.getContentPane().add(jLabel4, null);
             this.getContentPane().add(titleTxt, null);
             this.getContentPane().add(jLabel3, null);
             this.getContentPane().add(nameTxt, null);
             this.getContentPane().add(jLabel2, null);
             this.getContentPane().add(jLabel1, null);
         }

         private void okBtn_actionPerformed(ActionEvent e) 
         {
             option = 1;
             
             int size = datm.data.size();
             
             for(int i = 0; i < size; i++)
             {
                if (((ItemGrop)datm.data.get(i)).IG_name == nameTxt.getText() && (((ItemGrop)datm.data.get(i)).IG_name != ig.IG_name))       
                {
                    JOptionPane.showMessageDialog(this,"Item group already exists", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
             }
             
             int temp1 = 0, temp2 = 0;
             
             if (contexChb.isSelected())
             {
                temp1 = 1;
             }
             
             if (overflowChb.isSelected())
             {
                temp2 = 1;
             }
             
             ig = new ItemGrop(ig.IG_id,nameTxt.getText(),titleTxt.getText(),temp1,temp2);
             
             this.setVisible(false);
         }

         private void cancelBtn_actionPerformed(ActionEvent e) 
         {
             option = 0;
             this.setVisible(false);
         }
     }
}
