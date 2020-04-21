package ui;

import iisc.JDBCQuery;

import iisc.Synthesys;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.Serializable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import ui.events.DetailDataChanged;
import ui.events.IDdActionListener;
import ui.events.ILoadData;
import ui.events.INextRecord;
import ui.events.IPreviousRecord;

public class DBPanel extends JPanel
{
    String[] headerData=new String[0];
    String[] headerContexData=new String[0];
    String[] searchHeaderData=new String[0];
    String[] parameterHeaderData=new String[0];
    String[][] dbData=new String[0][0];
    String[][] dbContexData=new String[0][0];
    String[][] dbSearchData=new String[0][0];
    String[][] dbParameterData=new String[0][0];
    String[][] keyData=new String[0][0];
    String[][] parentKeyData=new String[0][0];
    String[][] searchKeyData=new String[0][0];
    Vector LoV=new Vector();
    Vector LoVF=new Vector();
    int lov=-1;
    Vector fieldAttributes=new Vector();
    Vector masterAttributes=new Vector();
    Vector searchFieldAttributes=new Vector();
    JTable contexTable;
    JTable searchTable;
    JTable masiveDeleteTable;
    TableModel contexModel;
    TableModel searchModel;
    TableModel masiveDeleteModel;
    JTable parameterTable;
    TableModel parameterModel;
    Template template;
    Connection connection;
    public Connection conn;
    int current=0;
    int comp;
    int as;
    int pr;
    String url="";
    String username="";
    String password="";
    boolean newRecord=true;
    JLabel record; 
    Application.FormType tf;
    Synthesys  syn=new Synthesys();
    Query queryGenerate;
    Set keys= new HashSet();
    Object parent;
    Object[] child= new Object[0];
    Set switchOn=new HashSet();
    Set switchOff=new HashSet();
    int caller_tf=-1;
    boolean restrictedSelect=false;
    int currentParameter=-1;
    JButton selectButton;
    int method;
    int originMethod=-1;
    String[][] lastSavedData;
    String[][] duplicatedData;
    int masive_delete=0;
    Application.ItemGroup contex;
    Set itemGroupPanels=new HashSet();
    JTabbedPane pane;
    boolean setLov=false;
    int retA=-1;
    //Aleksandar-begin 
    ArrayList dataChangeListeners = new ArrayList();
    ArrayList nextRecordListeners = new ArrayList();
    ArrayList prevRecordListeners = new ArrayList();
    ArrayList loadDataListeners = new ArrayList();
    ArrayList beforeInsertListeners = new ArrayList();
    ArrayList afterInsertListeners = new ArrayList();
    ArrayList beforeUpdateListeners = new ArrayList();
    ArrayList afterUpdateListeners = new ArrayList();
    ArrayList beforeDeleteListeners = new ArrayList();
    ArrayList afterDeleteListeners = new ArrayList();
    String tob_id = "";
    //Aleksandar-end
    
public DBPanel(){   
    addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            setButtons();
                        }
                    });
}

//Aleksandar-begin 
public void AddDataChangeListener(DetailDataChanged listener)
{
    this.dataChangeListeners.add(listener);
}
private void RaiseDataChangeListeners()
{
    for(int i = 0; i < this.dataChangeListeners.size(); i++)
    {
        DetailDataChanged listener = (DetailDataChanged)this.dataChangeListeners.get(i);
        listener.DataChanged(this.tob_id);
    }
}

public void AddNextRecordListener(INextRecord listener)
{
    this.nextRecordListeners.add(listener);
}
private void RaiseNextRecordListeners(int dir)
{
    for(int i = 0; i < this.nextRecordListeners.size(); i++)
    {
        INextRecord listener = (INextRecord)this.nextRecordListeners.get(i);
        
        if (dir == 0)
        {
            listener.BeforeNextRecord();
        }
        else
        {
            listener.AfterNextRecord();
        }
    }
}

public void AddPrevRecordListener(IPreviousRecord listener)
{
    this.prevRecordListeners.add(listener);
}
private void RaisePrevRecordListeners(int dir)
{
    for(int i = 0; i < this.prevRecordListeners.size(); i++)
    {
        IPreviousRecord listener = (IPreviousRecord )this.prevRecordListeners.get(i);
        
        if (dir == 0)
        {
            listener.BeforePreviousRecord();
        }
        else        
        {
            listener.AfterPreviousRecord();
        }
    }
}

public void AddLoadDataListener(ILoadData listener)
{
    this.loadDataListeners.add(listener);
}
public void RaiseLoadDataListeners(int dir)
{
    for(int i = 0; i < this.loadDataListeners.size(); i++)
    {
        ILoadData listener = (ILoadData)this.loadDataListeners.get(i);
        
        if (dir == 0)
        {
            listener.BeforeLoadData();
        }
        else        
        {
            listener.AfterLoadData();
        }
    }
}

public void AddBeforeInsertListener(IDdActionListener listener)
{
    this.beforeInsertListeners.add(listener);
}

public void AddAfterInsertListener(IDdActionListener listener)
{
    this.afterInsertListeners.add(listener);
}
    
public void AddBeforeUpdateListener(IDdActionListener listener)
{
    this.beforeUpdateListeners.add(listener);
}

public void AddAfterUpdateListener(IDdActionListener listener)
{
    this.afterUpdateListeners.add(listener);
}

public void AddBeforeDeleteListener(IDdActionListener listener)
{
    this.beforeDeleteListeners.add(listener);
}

public void AddAfterDeleteListener(IDdActionListener listener)
{
    this.afterDeleteListeners.add(listener);
}    

public boolean RaiseDbActionListeners(int dir, boolean status)
{
    boolean aborted = false;
    
    IDdActionListener listener = null;    
        
    if (dir == 0)
    {
        for(int i = 0; i < this.beforeInsertListeners.size(); i++)
        {
            listener = (IDdActionListener)this.beforeInsertListeners.get(i);
            
            if (!listener.BeforeInsert())
            {
                aborted = true;
            }
        }
    }
    
    if(dir == 1)
    {
        for(int i = 0; i < this.afterInsertListeners.size(); i++)
        {
            listener = (IDdActionListener)this.beforeInsertListeners.get(i);
            listener.AfterInsert(status, this.tob_id);
        }
    }
    
    if(dir == 2)
    {
        for(int i = 0; i < this.beforeUpdateListeners.size(); i++)
        {
            listener = (IDdActionListener)this.beforeUpdateListeners.get(i);
            
            if (!listener.BeforeUpdate())
            {
                aborted = true;
            }
        }
    }
    
    if(dir == 3)
    {
        for(int i = 0; i < this.afterUpdateListeners.size(); i++)
        {
            listener = (IDdActionListener)this.afterUpdateListeners.get(i);
            listener.AfterUpdate(status, this.tob_id);
        }
    }
    
    if(dir == 4)
    {
        for(int i = 0; i < this.beforeDeleteListeners.size(); i++)
        {
            listener = (IDdActionListener)this.beforeDeleteListeners.get(i);
            
            if (!listener.BeforeDelete())
            {
                aborted = true;
            }
        }
    }
    
    if(dir == 5)
    {
        for(int i = 0; i < this.afterDeleteListeners.size(); i++)
        {
            listener = (IDdActionListener)this.afterDeleteListeners.get(i);
            listener.AfterDelete(status, this.tob_id);
        }
    }
    
    return !aborted;
}

public Query getQueryGenerate()
{
    return this.queryGenerate;
}

public Connection getConn()
{
    return this.connection;
}

public void setTobId(String value)
{
    this.tob_id = value;
}

public String[][] getKeyData()
{
    return this.keyData;
}
//Aleksandar-end

public void setUrl(String _url) {
 url=_url;
}
public void setUsername(String _username) {
 username=_username;
}
public void setPassword(String _password) {
  password=_password;
}
public void setTemplate(Template _template) {
    template=_template;
} 
public void setPR(int _pr) {
    pr=_pr;
}
public void setAS(int _as) {
    as=_as;
}
public void setComp(int _comp) {
comp=_comp;
}
public void setForm(Application.FormType _tf, Connection _con, int _tf_entry) {
    tf=_tf;
    syn.pr=pr;
    syn.as=as;
    syn.con=_con;
    conn=_con;
    keys=getKeys();
    queryGenerate=new Query(conn,as,pr,tf,comp, connection, _tf_entry);
    queryGenerate.printS(queryGenerate.uTables);
}
public void setItemGroupPanels(){
    Iterator it=itemGroupPanels.iterator();
    while(it.hasNext()){
        JPanel itg=(JPanel)it.next();
        itg.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            setButtons();
                        }
                    });
    }
    if(pane!=null)pane.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            setButtons();
                        }
                    });
    if(contexTable!=null){
        contexTable.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            setButtons();
                        }
                    });
        contexTable.getTableHeader().addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            setButtons();
                        }
                    });
    }
}
public void setL0Vs(int i){
    lov=i;
}
public void setCaller(int i){ 
    setLov=false;
    if(i!=-1)caller_tf=i;
//    if(i!=-1){
        method=getMethod();
        if(originMethod==-1)originMethod=method;
        if(method==3 || method==1)selectButton.setVisible(true);
        else selectButton.setVisible(false);
        if(parameterTable!=null)setParameterModel();
        if(method==2 || method==1)restrictedSelect=true;
        setData();
//    }
//    else
//        setData();
}
public int getMethod(){
    Iterator its1=tf.parentMenu.iterator();
    while(its1.hasNext()) {
        Application.Menu txt1=(Application.Menu)its1.next();
        if(txt1.called_tf==caller_tf){
            if(txt1.method==1)return 3;
            if(txt1.method==2)return 0;
            return txt1.method;
        }
    }
    return 0;
}
 public void setData()
 {
    try
      {
      contex=getContex();
      newRecord=true;
      if(getCompType(comp).ins_allowed==0)newRecord=false;
      String[][] data=readData();
      current=0;
      headerData=new String[data.length];
      for(int i=0; i<data.length; i++)
          headerData[i]=data[i][0];
      for(int i=0; i<data.length; i++)
      {  
          Iterator its=fieldAttributes.iterator();
          while(its.hasNext()) {
              Object[] txt=(Object[])its.next();
              if(((Application.AttType)txt[0]).att.mnem.equals(data[i][0]))
                headerData[i]=((Application.AttType)txt[0]).att.name;
          }
      }
      ResultSet rs;
      JDBCQuery query=new JDBCQuery(connection);
      keyData=new String[0][0];
      setKeyData();
      String[][] parameterKeyData=readParameterKeyData();
      String upit=queryGenerate.getSelectCountQuery(readColumn(data,0),parentKeyData, parameterKeyData, method, readLOVData(), caller_tf);
      rs=query.select(upit);
      System.out.println(upit);
      int i=0;
      if(rs.next())
      i=rs.getInt(1);
      query.Close();
      dbData=new String[i][data.length];
      upit=queryGenerate.getSelectQuery(readColumn(data,0),parentKeyData, parameterKeyData, method, readLOVData(), caller_tf);
      rs=query.select(upit);
      i=0;
      currentParameter=-1;
      boolean can=true;
      parameterKeyData=readParKeyData();
      System.out.println(upit);
      while(rs.next())
      {
        for(int j=0;j<data.length;j++){
            dbData[i][j]=rs.getString(j+1);
            if(can)
                for(int k=0; k<parameterKeyData.length; k++){
                    if(parameterKeyData[k][0].equals(data[j][2]) && dbData[i][j].equals(parameterKeyData[k][1])){
                        currentParameter=i;
                        //current=i;
                        can=false;
                    }
                }
        }
        i++;
        newRecord=false;
      }
      query.Close();
      setData(current);
      if(parameterTable!=null)
        setParameterData(0);
      setChildData();
      setMasterData();
      if(masive_delete==1)
        setMasiveDeleteModel();
      if(contex!=null&& template.itemPanel.type==1){
        setContexModel();
      }
      setLOVvalidate();
      }
      catch(SQLException ex)
      {
      System.out.println("Exception: " + ex);
      ex.printStackTrace ();
      JOptionPane.showMessageDialog(null, "Error!", "DB Error", JOptionPane.ERROR_MESSAGE);
      }
      
     //Aleksandar begin
     this.RaiseDataChangeListeners();
     //Aleksandr end
 }
public void resData(int i)
{
    setLov=true;
    if(tf!=null)setData();
}
public void resetData()
{
   try
     {
     newRecord=true;
     if(getCompType(comp).ins_allowed==0)newRecord=false;
     String[][] data=readData();
     ResultSet rs;
     JDBCQuery query=new JDBCQuery(connection);
     keyData=new String[0][0];
     setKeyData();
     String upit=queryGenerate.getSelectCountQuery(readColumn(data,0),parentKeyData, readParameterKeyData(), method, readLOVData(), caller_tf);
     rs=query.select(upit);
     int i=0;
     if(rs.next())
     i=rs.getInt(1);
     query.Close();
     dbData=new String[i][data.length];
     upit=queryGenerate.getSelectQuery(readColumn(data,0),parentKeyData, readParameterKeyData(), method, readLOVData(), caller_tf);
     rs=query.select(upit);
     i=0;
     while(rs.next())
     {
       for(int j=0;j<data.length;j++){
           dbData[i][j]=rs.getString(j+1);
       }
       i++;
       newRecord=false;
     }
     query.Close();
     }
     catch(SQLException ex)
     {
     System.out.println("Exception: " + ex);
     ex.printStackTrace ();
     JOptionPane.showMessageDialog(null, "Error!", "DB Error", JOptionPane.ERROR_MESSAGE);
     }
    catch(Exception ex)
    {
    System.out.println("Exception: " + ex);
    ex.printStackTrace ();
    }
}
public void resetData(int i){
    Iterator its=searchFieldAttributes.iterator();
    while(its.hasNext()) {
        Object[] txt=(Object[])its.next();
        ((JTextField)txt[1]).setText("");
        ((JComboBox)txt[2]).setSelectedIndex(0);
        ((JComboBox)txt[3]).setSelectedIndex(0);
    }
    setSearchData(i);
}
public void setParameterData(int l)
{
   try
   {
     if(parameterTable==null)return;
     parameterHeaderData=new String[]{"Parameter","Value","Attribute"};
     parameterTable.setVisible(false);
     dbParameterData=readParameterData();
     parameterTable.setVisible(true);
    }
    catch(Exception ex)
    {
     System.out.println("Exception: " + ex);
     ex.printStackTrace ();
    }
}
public void setSearchData(int l)
{
   try
   {
     String[][] data=readSearchData();
     searchHeaderData=new String[data.length];
     for(int i=0; i<data.length; i++)
     {  
         Iterator its=searchFieldAttributes.iterator();
         while(its.hasNext()) {
             Object[] txt=(Object[])its.next();
             if(((Application.AttType)txt[0]).att.mnem.equals(data[i][0]))
               searchHeaderData[i]=((Application.AttType)txt[0]).att.name;
         }
     }
     searchTable.setVisible(false);
     ResultSet rs;
     JDBCQuery query=new JDBCQuery(connection);
     setSearchKeyData();
     String upit=queryGenerate.getSearchSelectCountQuery(searchKeyData, readLOVData(), method, caller_tf);
     rs=query.select(upit);
     int i=0;
     if(rs.next())
     i=rs.getInt(1);
     query.Close();
     dbSearchData=new String[i][fieldAttributes.size()];
     upit=queryGenerate.getSearchSelectQuery(readColumn(data,0),readColumn(data,3),searchKeyData, readLOVData(), method, caller_tf);
     rs=query.select(upit);
     System.out.println(upit);
     i=0;
     while(rs.next())
     {
       for(int j=0;j<data.length;j++){
           dbSearchData[i][j]="";
           //dbSearchData[i][j]=rs.getString(j+1);
           Iterator its=searchFieldAttributes.iterator();
           l=0;
           while(its.hasNext()) {
               Object[] txt=(Object[])its.next();
               if(((Application.AttType)txt[0]).att.sbp==0)
                   dbSearchData[i][l]=((Application.AttType)txt[0]).getValue(data);
               else if(((Application.AttType)txt[0]).att.mnem.equals(data[j][0]))
               {
                   dbSearchData[i][l]=rs.getString(j+1);
                   String[][] values=((Application.AttType)txt[0]).values;
                   for(int k=0;k<values.length;k++){
                       if(values[k][0].equals(dbSearchData[i][l]))dbSearchData[i][l]=values[k][1];
                   }
               }
               l++;
           }
       }
       i++;
     }
     query.Close();
     searchTable.setVisible(true);
     searchTable.getSelectionModel().setSelectionInterval(0,0);
     }
     catch(SQLException ex)
     {
     System.out.println("Exception: " + ex);
     ex.printStackTrace ();
     JOptionPane.showMessageDialog(null, "Error!", "DB Error", JOptionPane.ERROR_MESSAGE);
     }
}
public void setSearchKeyData()
{
    String[][] pom=new String[0][0];
    int k=0;
    searchKeyData=new String[searchFieldAttributes.size()][4];
    Iterator its=searchFieldAttributes.iterator();
    int i=0;
    while(its.hasNext()) {
        Object[] txt=(Object[])its.next();
        if(((Application.AttType)txt[0]).att.sbp==0)continue;
        searchKeyData[i][0]=""+((Application.AttType)txt[0]).att.id;
        searchKeyData[i][1]=readValue(txt[1]);
        searchKeyData[i][2]=readValue(txt[2]);
        searchKeyData[i][3]=readValue(txt[3]);
        if(((Application.AttType)txt[0]).att.domain.primitiveType.id!=1 && ((Application.AttType)txt[0]).att.domain.primitiveType.id!=2 && ((Application.AttType)txt[0]).att.domain.primitiveType.id!=4)
            if(searchKeyData[i][2].equals("like"))
                searchKeyData[i][1]="'%"+searchKeyData[i][1]+"%'";
            else
                searchKeyData[i][1]="'"+searchKeyData[i][1]+"'";
        i++;
    }
}
public void setChildData(){
    if(child!=null)
        for(int i=0;i<child.length;i++){
            if(child[i].getClass().toString().equals("class ui.DBTable")){
                ((DBTable)child[i]).caller_tf=caller_tf;
                ((DBTable)child[i]).restrictedSelect=restrictedSelect;
                ((DBTable)child[i]).parent=this;
                //Aleksandar - begin
                ((DBTable)child[i]).RaiseLoadDataListeners(0);
                ((DBTable)child[i]).setData();
                ((DBTable)child[i]).RaiseLoadDataListeners(1);
                //Aleksandar - end
                }
                else{
                ((DBPanel)child[i]).caller_tf=caller_tf;
                ((DBPanel)child[i]).restrictedSelect=restrictedSelect;
                ((DBPanel)child[i]).parent=this;
                //Aleksandar - begin
                ((DBPanel)child[i]).RaiseLoadDataListeners(0);
                ((DBPanel)child[i]).setData();
                ((DBPanel)child[i]).RaiseLoadDataListeners(1);
                //Aleksandar - end
            }         
        }
}
public void setSearchChoiceData(int c)
{
    for(int i=0;i<dbData.length;i++){
        boolean can=true;
        for(int j=0;j<dbData[i].length;j++){
            if(!dbData[i][j].equals(searchTable.getValueAt(searchTable.getSelectedRow(),j)))can=false;
        }
        if(can){
            setData(i);
            current=i;
            return;
        }
    }
}
public void setMasiveDelete(int c)
{
    if(masiveDeleteTable.getSelectedRowCount()>0){
        boolean can=true;
        JDBCQuery query=new JDBCQuery(connection);
        int[] rows=masiveDeleteTable.getSelectedRows();
        for(int k=0;k<rows.length;k++){
            Iterator its=fieldAttributes.iterator();
            int m=0;int n=0;
            String[][] masiveDeleteKeyData=new String[keyData.length][2];
            while(its.hasNext() && fieldAttributes.size()==masiveDeleteTable.getColumnCount()) {
                Object[] txt=(Object[])its.next();
                int id=((Application.AttType)txt[0]).att.id;
                if(keys.contains(""+id)){
                    masiveDeleteKeyData[n][0]=""+id;
                    String value=masiveDeleteTable.getValueAt(rows[k],m).toString();
                    masiveDeleteKeyData[n][1]=value;
                    if(((Application.AttType)txt[0]).att.domain.primitiveType.id!=1 && ((Application.AttType)txt[0]).att.domain.primitiveType.id!=2 && ((Application.AttType)txt[0]).att.domain.primitiveType.id!=4)
                        masiveDeleteKeyData[n][1]="'"+masiveDeleteKeyData[n][1]+"'";
                    n++;
                }
                m++;
            }
            String[] upit=queryGenerate.getDeleteQuery(masiveDeleteKeyData);
            int[] ret=new int[upit.length];
            for(int i=0; i<upit.length&& can;i++){
               ret[i]=query.update(upit[i]);
               if(ret[i]<0)can=false;
            }
        }
        if(can)
           JOptionPane.showMessageDialog(null,"Data deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
        setData();
    }
}
public void setData(int c)
{
    try{
    resetData();
    String[][] data=readData();
    Iterator its=fieldAttributes.iterator();
    int i=0;
    if(dbData.length>0)
        while(its.hasNext()) {
            Object[] txt=(Object[])its.next();
            if(((Application.AttType)txt[0]).att.sbp==1){
                setValue(txt[1],dbData[c][i]);
                String[][] values=((Application.AttType)txt[0]).values;
                for(int j=0;j<values.length;j++){
                    if(values[j][0].equals(dbData[c][i]))setValue(txt[1],values[j][1]);
                }
                i++;
            }
            else
                    setValue(txt[1],((Application.AttType)txt[0]).getValue(data));
        }
    else {
        while(its.hasNext()) {
            Object[] txt=(Object[])its.next();
            setValue(txt[1],"");
            i++;
        }
    }
    setKeyData();
    setChildData();
    if(contex!=null && template.itemPanel.type==1)contexTable.getSelectionModel().setSelectionInterval(current,current);
    if(dbData.length>0)
    {
      if(record!=null)record.setText("Record:"+(c+1)+"/"+dbData.length);
    }
    else
        if(record!=null)record.setText("Records: "+dbData.length);
    }
    catch(Exception ex)
    {
    System.out.println("Exception: " + ex);
    ex.printStackTrace ();
    }
    
    //Aleksandar begin
    this.RaiseDataChangeListeners();
    //Aleksandr end
}
 public void Connect()
  {
       try
      {
       connection = (Connection)DriverManager.getConnection(url, username, password);
       queryGenerate.connection=connection;
      }
      catch(SQLException ex)
      {
      System.out.println("Exception: " + ex);
      ex.printStackTrace ();
      } 
  }
    public void setModel() {
    Connect();
    setData();
}
public void getNext() 
{
    this.RaiseNextRecordListeners(0);
    
    if(newRecord)
        getFirst();
    else if(current<dbData.length-1)
    {
        current ++;
        setData(current);
    }
    //Aleksandar - begin
    this.RaiseNextRecordListeners(1);
}
public void getPrevios() {
    
    this.RaisePrevRecordListeners(0);
    
    if(newRecord)
        getFirst();
    else if(current>0)
    {
        current --;
        setData(current);
    }
    //Aleksandar - begin
    this.RaisePrevRecordListeners(1);
}
public void getFirst() {
        current=0;
        setData(current);
}
public void getLast() {
        current=dbData.length -1;
        setData(current);
}
public void setAction(String action) {
    try{
        if(action.equals("select") && !(method==1 || method==3))return;
        else if(action.equals("last"))getLast();
        else if(action.equals("next"))getNext();
        else if(action.equals("first"))getFirst();
        else if(action.equals("previos"))getPrevios();
        else if(action.equals("delete"))
        {
            clearData();
            delete();
        }
        else if(action.equals("insert")&&getCompType(comp).ins_allowed==1)
        {
            clearData();
            newRecord=true;
        }
        else if(action.equals("duplicate")&&getCompType(comp).ins_allowed==1)
        {
            duplicatedData=readData();
            clearData();
            duplicatedData=null;
            newRecord=true;
        }
        else if(action.equals("deleteAll"))
            deleteAll();
        else if(action.equals("lov")){
            if(lov!=-1)setLOVsearch();
        }
        else if(action.equals("select")){
            if(method==0)restrictedSelect=false;
            if(method==1)method=2;
            if(method==3)method=0;
            setData();
        }
        else if(action.equals("save")){
            if(newRecord)insert();
            else update();
        }
        else if(action.equals("new"))create();
    }
    catch(Exception ex)
          {
          System.out.println("Exception: " + ex);
            ex.printStackTrace ();
    }
}
public void setLOVvalidate(){
    try{
        Iterator it=LoVF.iterator();
        while(it.hasNext()){
            Application.ListOfValues l=(Application.ListOfValues)it.next();
            if(l.mand_validation==1)
                for(int i=0;i<l.returnsAttribute.length;i++){
                    retA=l.returns[i][1];
                    ActionListener ac[]= ((JTextField)l.returnsAttribute[i][0]).getActionListeners();
                    for(int k=0;k<ac.length;k++)((JTextField)l.returnsAttribute[i][0]).removeActionListener(ac[k]);
                    ((JTextField)l.returnsAttribute[i][0]).setToolTipText(""+retA);
                    ((JTextField)l.returnsAttribute[i][0]).addKeyListener(new KeyAdapter()
                      {
                        public void keyReleased(KeyEvent e)
                        {
                            if(!readValue(e.getComponent()).equals("") && !validate(((JTextField)e.getComponent()).getToolTipText(), readValue(e.getComponent()))){
                                JOptionPane.showMessageDialog(null,"Invalid value!",  "Error", JOptionPane.INFORMATION_MESSAGE);
                                setValue(e.getComponent(),"");
                            }
                        }
                      }); 
                }
        }
    }
    catch(Exception ex)
          {
          System.out.println("Exception: " + ex);
    }
}
public boolean  validate(String att, String value){
    Iterator it=fieldAttributes.iterator();
    int i=0;
    boolean cn=false;
    while(it.hasNext()&&!cn) {
        Object[] txt=(Object[])it.next();
        int id=((Application.AttType)txt[0]).att_id;
        if(att.equals(""+id)){
            for(int k=0;k<dbData.length;k++)
            {
                if(value.length()>dbData[k][i].length())continue;
                System.out.println("*"+value+"*");
                System.out.println("*"+dbData[k][i].substring(0,value.length())+"*");
                String val=dbData[k][i].substring(0,value.length()).toString();
                if(value.equals(val))
                    cn=true;
            }
        }
        i++;
    }
    return cn;
}
public void setLOVsearch(){
    try{
        Iterator it=LoV.iterator();
        Application.ListOfValues lv=null;
        while(it.hasNext()){
            Application.ListOfValues l=(Application.ListOfValues)it.next();
            if(l.id==lov) 
                lv=l;
        }
        if(lv!=null){
            for(int i=0;i<lv.returnsAttribute.length;i++){
                setValue(lv.returnsAttribute[i][0], readValue(lv.returnsAttribute[i][1])); 
            }
        }
    }
    catch(Exception ex)
          {
          System.out.println("Exception: " + ex);
    }
}
public void delete()
{
     if(dbData.length>0){
         //Aleksandar - begin 
         if (!this.RaiseDbActionListeners(4, true))
         {
            this.RaiseDbActionListeners(5, false);
            return;
         }
         //Aleksandar - end
         
         JDBCQuery query=new JDBCQuery(connection);
         String[] upit=queryGenerate.getDeleteQuery(keyData);
         int[] ret=new int[upit.length];
         boolean can=true;
         for(int i=0; i<upit.length&&can;i++){
            ret[i]=query.update(upit[i]);
            if(ret[i]<0)can=false;
         }
         if(can)
            JOptionPane.showMessageDialog(null,"Data deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
         setData();
         setData(0);
         
         //Aleksandar - begin 
         this.RaiseDbActionListeners(5, can);
         //Aleksandar - end
     }
}
public void deleteAll()
{
     if(dbData.length>0){
         if(child!=null){
             for(int i=0;i<child.length;i++){
                 if(child[i].getClass().toString().equals("class ui.DBTable"))
                     ((DBTable)child[i]).deleteAll(1);
                 else
                     ((DBPanel)child[i]).deleteAll(1);
             }
          }
         JDBCQuery query=new JDBCQuery(connection);
         String[] upit=queryGenerate.getDeleteAllQuery(parentKeyData, keyData);
         int[] ret=new int[upit.length];
         boolean can=true;
         for(int i=0; i<upit.length&&can;i++){
            ret[i]=query.update(upit[i]);
            if(ret[i]<0)can=false;
         }
         setData();
         if(can)
            JOptionPane.showMessageDialog(null,"All Data deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
     }
}
public void deleteAll(int k)
{
    try {
        if(child!=null){
            for(int i=0;i<child.length;i++){
                if(child[i].getClass().toString().equals("class ui.DBTable"))
                    ((DBTable)child[i]).deleteAll(1);
                else
                    ((DBPanel)child[i]).deleteAll(1);
            }
         }
        JDBCQuery query=new JDBCQuery(connection);
        String[] upit=queryGenerate.getDeleteAllQuery(parentKeyData, keyData);
        int[] ret=new int[upit.length];
        boolean can=true;
        for(int i=0; i<upit.length;i++){
           ret[i]=query.update(upit[i]);
        }
        setData();
    }
    catch(Exception ex)
    {
    System.out.println("Exception: " + ex);
    ex.printStackTrace ();
    }
}
public void update()
{
    //Aleksandar - begin 
    if (!this.RaiseDbActionListeners(2, true))
    {
       this.RaiseDbActionListeners(3, false);
       return;
    }
    boolean updated = false;
    //Aleksandar - end
    
    String[][] data=readData();
    if(dbData.length>0){         
         JDBCQuery query=new JDBCQuery(connection);
         if(checkData(1))
         {
             String[] upit=queryGenerate.getUpdateQuery(readColumn(data,0),readColumn(data,1),keyData);
             int[] ret=new int[upit.length];
             boolean can=true;
             for(int i=0; i<upit.length && can;i++){
                ret[i]=query.update(upit[i]);
                if(ret[i]<0)can=false;
             }
             updated = can&&upit.length>0;
             if(can&&upit.length>0){
                JOptionPane.showMessageDialog(null,"Data updated!",  "Success", JOptionPane.INFORMATION_MESSAGE);
                setData();
             }
         }        
     }
    //Aleksandar - begin 
    this.RaiseDbActionListeners(3, updated);
    //Aleksandar - end
}
public void insert()
{
         //Aleksandar - begin 
         if (!this.RaiseDbActionListeners(0, false))
         {
            this.RaiseDbActionListeners(1, false);
            return;
         }
         boolean inserted =false;
         //Aleksandar - end 
         
         String[][] data=readData();
         JDBCQuery query=new JDBCQuery(connection);
         String upit=queryGenerate.getInsertQuery(readColumn(data,0),readColumn(data,1), parentKeyData);
         if(!upit.equals("")&& checkData(0))
         {
            int i=query.update(upit);
             if(i>0)
             {
                lastSavedData=readData();
                JOptionPane.showMessageDialog(null,"Data inserted!",  "Success", JOptionPane.INFORMATION_MESSAGE);
                setData();
                inserted = true;
             }
             
         }
         
        //Aleksandar - begin 
        this.RaiseDbActionListeners(1, inserted);
        //Aleksandar - end  
        
}
public boolean checkData(int i) {
    Iterator its=fieldAttributes.iterator();
    int k=0;
    boolean cant=false;
    while(its.hasNext()) {
        Object[] txt=(Object[])its.next();
        Application.AttType at=((Application.AttType)txt[0]);
        if(((Application.AttType)txt[0]).att.sbp==1){
            String[][] values=((Application.AttType)txt[0]).values;
            String str=readValue(txt[1]);
            boolean can=false;
            for(int j=0;j<values.length;j++){
                if(values[j][1].equals(str)){
                    can=true;
                    str=values[j][0];
                }
            }
            if(dbData.length>0 && !str.equals(dbData[current][k]))cant=true;
            if((at.att.domain.primitiveType.id==1 || at.att.domain.primitiveType.id==2 || at.att.domain.primitiveType.id==4) && str.equals(""))
            {    
                setValue(txt[1],"NULL");
                str=readValue(txt[1]);
            }  
            if((at.att.domain.primitiveType.id==1 || at.att.domain.primitiveType.id==2 || at.att.domain.primitiveType.id==4) && !str.equals("NULL"))
            {    
                if((at.att.domain.primitiveType.id==1 && !queryGenerate.isInteger(str)) ||(at.att.domain.primitiveType.id==2 && !queryGenerate.isDecimal(str)) || at.att.domain.primitiveType.id==4 && !queryGenerate.isBoolean(str)) {
                    JOptionPane.showMessageDialog(null,"Invalid value for attribute "+ at.title +"!",  "Error", JOptionPane.INFORMATION_MESSAGE);
                    return false;   
                }
            } 
            if(at.null_allowed==0 && (str.equals("NULL") || str.equals("null") || str==null)){
                JOptionPane.showMessageDialog(null,"NULL value is not allowed for attribute "+ at.title +"!",  "Error", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            if(!can && values.length>0)
            {
                JOptionPane.showMessageDialog(null,"Value is not in list of values for attribute "+ at.title +"!",  "Error", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            if(i==0 && at.ins_allowed==0)
            {
                JOptionPane.showMessageDialog(null,"It is not allowed to insert value for attribute "+ at.title +"!",  "Error", JOptionPane.INFORMATION_MESSAGE);
                return false;
            } 
            if(i==1 && at.upd_allowed==0 && !str.equals(dbData[current][k]))
            {
                JOptionPane.showMessageDialog(null,"It is not allowed to update value for attribute "+ at.title +"!",  "Error", JOptionPane.INFORMATION_MESSAGE);
                return false;
            } 
            if(at.mandatory==1 && (str.equals("")||str.equals("NULL")))
            {
                JOptionPane.showMessageDialog(null,"The attribute"+ at.title +" is mandatory!",  "Error", JOptionPane.INFORMATION_MESSAGE);
                return false;
            } 
            if(at.att.domain.length>0 && str.length()>at.att.domain.length)
            {
                JOptionPane.showMessageDialog(null,"The value for attribute "+ at.title +" is too long!",  "Error", JOptionPane.INFORMATION_MESSAGE);
                return false;
            } 
            k++;
        }
    }
    if(dbData.length>0 &&!cant) {
        JOptionPane.showMessageDialog(null,"There is no data changes!",  "Error", JOptionPane.INFORMATION_MESSAGE);
        return false;   
    }
    return true;
}
public String[] readColumn(String[][] data, int id){
    String[] column=new String[data.length];
    for(int i=0;i<data.length;i++){
        column[i]=data[i][id];
    }
    return column;
}
public String[][] readData()
{
    Iterator its=fieldAttributes.iterator();
    int k=0;
    while(its.hasNext()) {
        Object[] txt=(Object[])its.next();
        if(((Application.AttType)txt[0]).att.sbp==0)k++;
    }
    String[][] str=new String[fieldAttributes.size()-k][3];
    its=fieldAttributes.iterator();
    int i=0;
    while(its.hasNext()) {
        Object[] txt=(Object[])its.next();
        if(((Application.AttType)txt[0]).att.sbp==1){
            str[i][0]=((Application.AttType)txt[0]).att.mnem;
            int id=((Application.AttType)txt[0]).att.id;
            String[][] values=((Application.AttType)txt[0]).values;
            str[i][1]=readValue(txt[1]);
            str[i][2]=""+id;
            for(int j=0;j<values.length;j++){
                if(values[j][1].equals(str[i][1]))str[i][1]=values[j][0];
            }
            if(((Application.AttType)txt[0]).att.domain.primitiveType.id!=1 && ((Application.AttType)txt[0]).att.domain.primitiveType.id!=2 && ((Application.AttType)txt[0]).att.domain.primitiveType.id!=4)
                str[i][1]="'"+str[i][1]+"'";
            i++;
        }
    }
    return str;
}
public String[][] readSearchData()
{
    String[][] str=new String[searchFieldAttributes.size()][4];
    Iterator its=searchFieldAttributes.iterator();
    int i=0;
    while(its.hasNext()) {
        Object[] txt=(Object[])its.next();
        str[i][0]=((Application.AttType)txt[0]).att.mnem;
        str[i][1]=readValue(txt[1]);
        str[i][2]=readValue(txt[2]);
        str[i][3]=readValue(txt[3]);
        if(((Application.AttType)txt[0]).att.domain.primitiveType.id!=1 && ((Application.AttType)txt[0]).att.domain.primitiveType.id!=2 && ((Application.AttType)txt[0]).att.domain.primitiveType.id!=4)
        {
            if(str[i][2].equals("like"))
                str[i][1]="'%"+str[i][1]+"%'";
            else 
                str[i][1]="'"+str[i][1]+"'";
        }
        else if(str[i][2].equals("like"))
            str[i][2]="=";
        i++;
    }
    return str;
}
public String[][] readParameterData()
{
    String[][] str=new String[tf.parameters.size()][3];
    Iterator its=tf.parameters.iterator();
    int i=0;
    while(its.hasNext()) {
        Application.Parameter txt=(Application.Parameter)its.next();
        str[i][0]=txt.desc;
        Iterator its1=tf.parentMenu.iterator();
        while(its1.hasNext()) {
            Application.Menu txt1=(Application.Menu)its1.next();
            if(txt1.called_tf==caller_tf){
                Iterator its2=txt1.passedValues.iterator();
                while(its2.hasNext()) {
                    Application.PassedValue txt2=(Application.PassedValue)its2.next();
                    if(txt2.to_parameter==txt.id)
                        setParameterValue(txt,txt2);
                }
            }
        }
        str[i][1]=txt.value;
        str[i][2]=getAtt(txt.att).att.name;
        i++;
    }
    return str;
}
public String[][] readParameterKeyData()
{
    String[][] pom=new String[0][0];
    if(restrictedSelect)pom=readParKeyData();
    return pom;
}
public String[][] readLOVData()
{
    if(!setLov)return new String[0][2];
    Vector parameterKey=new Vector();
    Iterator its=LoV.iterator();
    while(its.hasNext()) {
        Application.ListOfValues lv=(Application.ListOfValues)its.next();
        if(lv.search==1){
            for(int k=0;k<lv.returns.length;k++){
                Application.AttType at=getAtt(lv.returns[k][0]);
                String[] str=new String[2];
                str[0]=""+at.att.id;
                str[1]=readValue(lv.returnsAttribute[k][0]);
                if((at.att.domain.primitiveType.id!=1 && at.att.domain.primitiveType.id!=2 && at.att.domain.primitiveType.id!=4))
                    str[1]="'%"+str[1]+"%'";
                parameterKey.add(str);
            }
        }
    }
    String[][] str=new String[parameterKey.size()][2];
    Object[] pom=parameterKey.toArray();
    for(int i=0;i<pom.length;i++){
        str[i]=(String[])pom[i];
    }
    return str;
}
public String[][] readParKeyData()
{
    Vector parameterKey=new Vector();
    Iterator its=tf.parameters.iterator();
    while(its.hasNext()) {
        Application.Parameter txt=(Application.Parameter)its.next();
        Application.AttType at=getAtt(txt.att);
        String[] str=new String[2];
        str[0]=""+at.att.id;
        str[1]=txt.value;
        if(at.att.domain.primitiveType.id!=1 && at.att.domain.primitiveType.id!=2 && at.att.domain.primitiveType.id!=4)
            str[1]="'"+str[1]+"'";
        parameterKey.add(str);
    }
    String[][] str=new String[parameterKey.size()][2];
    Object[] pom=parameterKey.toArray();
    for(int i=0;i<pom.length;i++){
        str[i]=(String[])pom[i];
    }
    return str;
}
public void setParameterValue(Application.Parameter param, Application.PassedValue pass){
    if(pass.type==0)
        param.value=pass.value;
    else if(pass.type==1)
        param.value=readValue(pass.attribute);
    else if(pass.type==2)
        param.value=pass.f_parameter.value;
}
public Application.AttType getAtt(int id){
    for(int k=0;k<tf.compType.length;k++){
        Application.CompType cmp=tf.compType[k];
        for(int i=0;i<cmp.itemGroup.length;i++){
            Application.ItemGroup itemgr=cmp.itemGroup[i];
            for(int j=0;j<itemgr.items.length;j++){
                if(itemgr.items[j].elem.getClass().toString().equals("class ui.Application$AttType")){
                    if(((Application.AttType)itemgr.items[j].elem).att_id==id)
                        return ((Application.AttType)itemgr.items[j].elem);
                }
                else
                {
                    Application.ItemGroup ig=(Application.ItemGroup)itemgr.items[j].elem;
                    for(int m=0;m<ig.items.length; m++)
                        if(((Application.AttType)ig.items[m].elem).att_id==id)
                            return ((Application.AttType)ig.items[m].elem);
                }
            }
        }
    }
    return null;
}
public Application.ItemGroup getContex(){
    for(int k=0;k<tf.compType.length;k++){
        Application.CompType cmp=tf.compType[k];
        if(cmp.id==comp)
        for(int i=0;i<cmp.itemGroup.length;i++){
            Application.ItemGroup itemgr=cmp.itemGroup[i];
            if(itemgr.contex==1)return itemgr;
        }
    }
    return null;
}  
public Set getContexAtt(){
    Set contexAtt=new HashSet();
    if(contex==null)return contexAtt;
    for(int j=0;j<contex.items.length;j++){
        if(contex.items[j].elem.getClass().toString().equals("class ui.Application$AttType")){
                contexAtt.add((Application.AttType)contex.items[j].elem);
        }
        else
        {
            Application.ItemGroup ig=(Application.ItemGroup)contex.items[j].elem;
            for(int m=0;m<ig.items.length; m++)
                    contexAtt.add((Application.AttType)ig.items[m].elem);
        }
    }
    return contexAtt;
} 
public void clearData()
{
    Iterator its=fieldAttributes.iterator();
    int i=0;
    while(its.hasNext()) {
        Object[] txt=(Object[])its.next();
        setValue(txt[1],"");
        setValue(txt[1],((Application.AttType)txt[0]).default_value);
        String[][] values=((Application.AttType)txt[0]).values;
        for(int j=0;j<values.length;j++){
            if(values[j][0].equals(((Application.AttType)txt[0]).default_value))setValue(txt[1],values[j][1]);
        }
        if(duplicatedData!=null)
        {
            if(((Application.AttType)txt[0]).att.domain.primitiveType.id!=1 && ((Application.AttType)txt[0]).att.domain.primitiveType.id!=2 && ((Application.AttType)txt[0]).att.domain.primitiveType.id!=4)
                setValue(txt[1],duplicatedData[i][1].substring(1,duplicatedData[i][1].length()-1));
            else
                setValue(txt[1],duplicatedData[i][1]);
        }
        if(getCompType(comp).retain_last_ins_record==1)
        {
            if(lastSavedData!=null)
            {
                if(((Application.AttType)txt[0]).att.domain.primitiveType.id!=1 && ((Application.AttType)txt[0]).att.domain.primitiveType.id!=2 && ((Application.AttType)txt[0]).att.domain.primitiveType.id!=4)
                    setValue(txt[1],lastSavedData[i][1].substring(1,lastSavedData[i][1].length()-1));
                else
                    setValue(txt[1],lastSavedData[i][1]);
            }
        }
        i++;
    }
    if(record!=null)record.setText("Records: "+dbData.length);
}
public void create()
{
    Iterator its=fieldAttributes.iterator();
    int i=0;
    while(its.hasNext()) {
        Object[] txt=(Object[])its.next();
            setValue(txt[1],"");
        i++;
    }
}
public void setMasterData()
{
    String[][] pom=new String[0][0];
    Iterator its=masterAttributes.iterator();
    int i=0;
    while(its.hasNext()) {
        Object[] txt=(Object[])its.next();
        Application.AttType at=(Application.AttType)txt[0];
        setValue(txt[1],"");
    }
    its=masterAttributes.iterator();
    if(parentKeyData.length>0)
        while(its.hasNext()) {
            Object[] txt=(Object[])its.next();
            Application.AttType at=(Application.AttType)txt[0];
            if((at.att.domain.primitiveType.id!=1 && at.att.domain.primitiveType.id!=2 && at.att.domain.primitiveType.id!=4)&&parentKeyData[i][1].length()>2)
                setValue(txt[1],parentKeyData[i][1].substring(1,parentKeyData[i][1].length()-1));
            else 
                setValue(txt[1],parentKeyData[i][1]);
            i++;
        }
}
public void setContexData()
{
    Set contexAtt=getContexAtt();
    headerContexData=new String[contexAtt.size()];
    dbContexData=new String[dbData.length][contexAtt.size()];
    Iterator it=fieldAttributes.iterator();
    int i=0,j=0;
    while(it.hasNext()) {
        Object[] txt=(Object[])it.next();
        int id=((Application.AttType)txt[0]).att_id;
        Iterator its=contexAtt.iterator();
        while(its.hasNext()) {
            Application.AttType att=((Application.AttType)its.next());
            if(id==att.att_id){
                headerContexData[i]=att.att.name;
                for(int k=0;k<dbData.length;k++)
                dbContexData[k][i]=dbData[k][j];
                i++; 
            }
        } 
        j++;
    }
}
public void setKeyData()
{
    String[][] pom=new String[0][0];
    int k=0;
    if(parent!=null)
    {
        if(parent.getClass().toString().equals("class ui.DBTable"))
            pom=((DBTable)parent).keyData;
        else
            pom=((DBPanel)parent).keyData;
        if(pom!=null)
            for(int j=0;j<pom.length;j++)
                k++;
    }
    parentKeyData=new String[k][2];
    keyData=new String[keys.size()+k][2];
    Iterator its=fieldAttributes.iterator();
    int i=0;
    while(its.hasNext()) {
        Object[] txt=(Object[])its.next();
        int id=((Application.AttType)txt[0]).att.id;
        if(keys.contains(""+id)){
            keyData[i][0]=""+((Application.AttType)txt[0]).att.id;
            String[][] values=((Application.AttType)txt[0]).values;
            keyData[i][1]=readValue(txt[1]);
            for(int j=0;j<values.length;j++){
                if(values[j][1].equals(keyData[i][1]))keyData[i][1]=values[j][0];
            }
            if(((Application.AttType)txt[0]).att.domain.primitiveType.id!=1 && ((Application.AttType)txt[0]).att.domain.primitiveType.id!=2 && ((Application.AttType)txt[0]).att.domain.primitiveType.id!=4)
                keyData[i][1]="'"+keyData[i][1]+"'";
            i++;
        }
    }
    if(parent!=null)
    {
        int m=0;
        for(int j=0;j<pom.length;j++)
        {
           // if(pom[j][1]!=null)
            //    if(!pom[j][1].equals("")){
                    keyData[i+j][0]=pom[j][0];
                    keyData[i+j][1]=pom[j][1]; 
                    parentKeyData[m][0]=pom[j][0];
                    parentKeyData[m][1]=pom[j][1];
                    m++;
           //     }
        }
    }
}

public Application.CompType getCompType(int cmp){
    for(int i=0; i< tf.compType.length; i++){
        if(tf.compType[i].id==cmp)
            return tf.compType[i];
    }
    return null;
}

public Set getKeys(){
    Set ret=new HashSet();
    Application.CompType cmp=getCompType(comp);
    for(int j=0; j< cmp.keys.length; j++){
        Iterator it=cmp.keys[j].iterator();
        while(it.hasNext()) {
            Application.AttType att=(Application.AttType)it.next();
            ret.add(""+att.att_id);
        }
    }
    return ret;
}
public void setButtons(){
    Iterator it=switchOn.iterator();
    while(it.hasNext()){
        JPanel panel=(JPanel)it.next();
        System.out.println(panel.getName());
        panel.setVisible(true);
    }
    it=switchOff.iterator();
    while(it.hasNext()){
        JPanel panel=(JPanel)it.next();
        System.out.println(panel.getName());
        panel.setVisible(false);
    }
}
public String readValue(Object o){
    try {
        if(o.getClass().toString().equals("class javax.swing.JTextField"))
            return ((JTextField)o).getText();
        if(o.getClass().toString().equals("class javax.swing.JTextArea"))
            return ((JTextArea)o).getText();
        else if(o.getClass().toString().equals("class javax.swing.JComboBox"))
        {
            JComboBox combo=new JComboBox();
            combo=(JComboBox)o;
            if(combo.getSelectedItem()!=null)
            return (combo.getSelectedItem().toString());
        }
        else if(o.getClass().toString().equals("class javax.swing.JList"))
        {
            JList list=new JList();
            list=(JList)o;
            if(list.getSelectedValue()!=null)
            return (list.getSelectedValue().toString());
        }
        else if(o.getClass().toString().equals("class javax.swing.JCheckBox")){
            JCheckBox check=(JCheckBox)o;
            if(check.isSelected())return check.getText();
        }
        else if(o.getClass().toString().equals("class javax.swing.ButtonGroup"))
        {       
            ButtonGroup radio=new ButtonGroup();
            radio=(ButtonGroup)o;
            Enumeration en=radio.getElements();
            String value="";
            while(en.hasMoreElements()) {
                JRadioButton btn=(JRadioButton)en.nextElement();
                if(btn.isSelected())value=btn.getText();
            }
            return value;
        }
    }
    catch(Exception ex)
          {
          System.out.println("Exception: " + ex);
    }
    return "";
}   
public void setValue(Object o, String s){
    try{
        if(o.getClass().toString().equals("class javax.swing.JTextField"))
            ((JTextField)o).setText(s);
        else if(o.getClass().toString().equals("class javax.swing.JTextArea"))
            ((JTextArea)o).setText(s);
        else if(o.getClass().toString().equals("class javax.swing.JComboBox"))
        {   
            ((JComboBox)o).setSelectedIndex(0);
            ((JComboBox)o).setSelectedItem(s);
        }
        else if(o.getClass().toString().equals("class javax.swing.JList"))
        {
            ((JList)o).setSelectedIndex(0);
            ((JList)o).setSelectedValue(s, true);
        }
        else if(o.getClass().toString().equals("class javax.swing.JCheckBox")){
            JCheckBox check=(JCheckBox)o;
            check.setSelected(false);
            if(check.getText().equals(s))check.setSelected(true);
        }
        else if(o.getClass().toString().equals("class javax.swing.ButtonGroup")){
            ButtonGroup radio=new ButtonGroup();
            radio=(ButtonGroup)o;
            Enumeration en=radio.getElements();
            ((JRadioButton)en.nextElement()).setSelected(true);
            en=radio.getElements();
            while(en.hasMoreElements()) {
                JRadioButton btn=(JRadioButton)en.nextElement();
                if(btn.getText().equals(s))btn.setSelected(true);
                else btn.setSelected(false);
            }
        }
    }
    catch(Exception ex)
          {
          System.out.println("Exception: " + ex);
    }        
}
public void setVisible(Object o, boolean s){
    try{
        if(o.getClass().toString().equals("class javax.swing.JTextField"))
            ((JTextField)o).setVisible(s);
        else if(o.getClass().toString().equals("class javax.swing.JComboBox"))
            ((JComboBox)o).setVisible(s);
        else if(o.getClass().toString().equals("class javax.swing.JList"))
            ((JList)o).setVisible(s);
        else if(o.getClass().toString().equals("class javax.swing.JCheckBox"))
            ((JCheckBox)o).setVisible(s);
        else if(o.getClass().toString().equals("class javax.swing.ButtonGroup")){
            ButtonGroup radio=new ButtonGroup();
            radio=(ButtonGroup)o;
            Enumeration en=radio.getElements();
            while(en.hasMoreElements()) {
                JRadioButton btn=(JRadioButton)en.nextElement();
                btn.setVisible(s);
            }
        }
    }
    catch(Exception ex)
          {
          System.out.println("Exception: " + ex);
    }        
}
public void setParameterModel() {
    setParameterData(0);
    parameterModel = new AbstractTableModel() {

           public int getColumnCount() {
                   return parameterHeaderData.length;
           }

           public int getRowCount() {
                   return dbParameterData.length;
           }
           public Object getValueAt(int row, int col) {
                   if (row >= getRowCount() || col >= getColumnCount())
                           return null;
                   return dbParameterData[row][col];
           }

    public String getColumnName(int i)
    {
    return parameterHeaderData[i].toString();
    }
    };
    parameterTable.setModel(parameterModel);
    parameterTable.setSelectionMode(parameterTable.getSelectionModel().SINGLE_INTERVAL_SELECTION);
    for (int i = 0; i < parameterTable.getColumnCount(); i++) {
        parameterTable.getColumnModel().getColumn(i).setHeaderRenderer(new MyHeaderRenderer()); 
        parameterTable.getColumnModel().getColumn(i).setCellRenderer(new MyCellRenderer()); 
        parameterTable.getColumnModel().getColumn(i).setCellEditor(new MyTableCellEditor());
        parameterTable.getColumnModel().getColumn(i).setPreferredWidth(template.tablePanel.column_width);
        parameterTable.getColumnModel().getColumn(i).setMaxWidth(template.tablePanel.column_width);
        parameterTable.getColumnModel().getColumn(i).setMinWidth(template.tablePanel.column_width);
        parameterTable.getColumnModel().getColumn(i).setWidth(template.tablePanel.column_width);
    }
}
    public void setMasiveDeleteModel() {
    masiveDeleteModel = new AbstractTableModel() {
           public int getColumnCount() {
                   return headerData.length;
           }

           public int getRowCount() {
                   return dbData.length;
           }
           public Object getValueAt(int row, int col) {
                   if (row >= getRowCount() || col >= getColumnCount())
                           return null;
                   return dbData[row][col];
           }
    
    public String getColumnName(int i)
    {
    return headerData[i].toString();
    }
    };
    masiveDeleteTable.setModel(masiveDeleteModel);
    masiveDeleteTable.setSelectionMode(masiveDeleteTable.getSelectionModel().MULTIPLE_INTERVAL_SELECTION);
    for (int i = 0; i < masiveDeleteTable.getColumnCount(); i++) {
        masiveDeleteTable.getColumnModel().getColumn(i).setHeaderRenderer(new MyHeaderRenderer()); 
        masiveDeleteTable.getColumnModel().getColumn(i).setCellRenderer(new MyCellRenderer()); 
        masiveDeleteTable.getColumnModel().getColumn(i).setCellEditor(new MyTableCellEditor());
        masiveDeleteTable.getColumnModel().getColumn(i).setPreferredWidth(template.tablePanel.column_width);
        masiveDeleteTable.getColumnModel().getColumn(i).setMaxWidth(template.tablePanel.column_width);
        masiveDeleteTable.getColumnModel().getColumn(i).setMinWidth(template.tablePanel.column_width);
        masiveDeleteTable.getColumnModel().getColumn(i).setWidth(template.tablePanel.column_width);
    }
    }
public void setContexModel() {
    setContexData();
    contexModel = new AbstractTableModel() {
           public int getColumnCount() {
                   return headerContexData.length;
           }

           public int getRowCount() {
                   return dbContexData.length;
           }
           public Object getValueAt(int row, int col) {
                   if (row >= getRowCount() || col >= getColumnCount())
                           return null;
                   return dbContexData[row][col];
           }
    
    public String getColumnName(int i)
    {
    return headerContexData[i].toString();
    }
    };
    contexTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
    public void valueChanged(ListSelectionEvent e) {
            if(dbData!=null){
                current=contexTable.getSelectedRow();
                if(current>=0)
                setData(current);
            }
        }
    });
    contexTable.setModel(contexModel);
    contexTable.setSelectionMode(contexTable.getSelectionModel().SINGLE_SELECTION);
    for (int i = 0; i < contexTable.getColumnCount(); i++) {
        contexTable.getColumnModel().getColumn(i).setHeaderRenderer(new MyHeaderRenderer()); 
        contexTable.getColumnModel().getColumn(i).setCellRenderer(new MyCellRenderer()); 
        contexTable.getColumnModel().getColumn(i).setCellEditor(new MyTableCellEditor());
        contexTable.getColumnModel().getColumn(i).setPreferredWidth(template.tablePanel.column_width);
        contexTable.getColumnModel().getColumn(i).setMaxWidth(template.tablePanel.column_width);
        contexTable.getColumnModel().getColumn(i).setMinWidth(template.tablePanel.column_width);
        contexTable.getColumnModel().getColumn(i).setWidth(template.tablePanel.column_width);
    }
    if(contexTable.getModel().getRowCount()>0)contexTable.getSelectionModel().setSelectionInterval(0,0);
}
public void setSearchModel() {
setSearchData(0);
searchModel = new AbstractTableModel() {

       public int getColumnCount() {
               return searchHeaderData.length;
       }

       public int getRowCount() {
               return dbSearchData.length;
       }
       public Object getValueAt(int row, int col) {
               if (row >= getRowCount() || col >= getColumnCount())
                       return null;
               return dbSearchData[row][col];
       }

public String getColumnName(int i)
{
return searchHeaderData[i].toString();
}
};
searchTable.setModel(searchModel);
searchTable.setSelectionMode(searchTable.getSelectionModel().SINGLE_INTERVAL_SELECTION);
if(searchTable.getRowCount()>0) {
searchTable.getSelectionModel().setSelectionInterval(current,current);
}
for (int i = 0; i < searchTable.getColumnCount(); i++) {
    searchTable.getColumnModel().getColumn(i).setHeaderRenderer(new MyHeaderRenderer()); 
    searchTable.getColumnModel().getColumn(i).setCellRenderer(new MyCellRenderer()); 
    searchTable.getColumnModel().getColumn(i).setCellEditor(new MyTableCellEditor());
    searchTable.getColumnModel().getColumn(i).setPreferredWidth(template.tablePanel.column_width);
    searchTable.getColumnModel().getColumn(i).setMaxWidth(template.tablePanel.column_width);
    searchTable.getColumnModel().getColumn(i).setMinWidth(template.tablePanel.column_width);
    searchTable.getColumnModel().getColumn(i).setWidth(template.tablePanel.column_width);
}
}
    public Border getBorders(int border_style, int border, Color color) {
        Border b=BorderFactory.createBevelBorder(border);
          if(border_style==0)
              b=BorderFactory.createEmptyBorder(border,border,border,border);
          else if(border_style==1)
              b=BorderFactory.createLineBorder(color, border);
          else if(border_style==2)
              b=BorderFactory.createEtchedBorder();
          else if(border_style==3)
              b=BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
          else if(border_style==4)
              b=BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
          else if(border_style==5)
              b=BorderFactory.createBevelBorder(BevelBorder.RAISED);
          else if(border_style==6)
              b=BorderFactory.createBevelBorder(BevelBorder.LOWERED);
          else if(border_style==7)
              b=BorderFactory.createTitledBorder("Title");
          return b;
    }
    class MyHeaderRenderer extends JLabel implements 
                TableCellRenderer, Serializable 
    {
        public  MyHeaderRenderer() 
        { 
            setOpaque(true);
            setBorder(getBorders(template.tablePanel.border_style,template.tablePanel.border, template.tablePanel.fg)); 
            setForeground(template.tablePanel.fg); 
            setBackground(template.tablePanel.bg);
            setFont(template.labelFont.font);
               
        } 
        public Component getTableCellRendererComponent(JTable table, 
                Object value, boolean isSelected, boolean hasFocus, int row, 
                int column) 
        { 
                this.setText(value.toString()); 
                return this; 
        } 

    }
class MyCellRenderer extends JLabel implements 
            TableCellRenderer, Serializable 
{
    public  MyCellRenderer() 
    { 
        setOpaque(true);
        setFont(template.inputFont.font);
           
    } 
    public Component getTableCellRendererComponent(JTable table, 
            Object value, boolean isSelected, boolean hasFocus, int row, 
            int column) 
    { 
    
            this.setText(value.toString()); 
            this.setBorder(getBorders(template.tablePanel.border_style,template.tablePanel.border, template.tablePanel.fg)); 
            if(isSelected)
            {
                this.setBackground(table.getSelectionBackground());
                this.setForeground(table.getSelectionForeground());
            }
            else {
                this.setForeground(template.tablePanel.input_fg); 
                this.setBackground(template.tablePanel.input_bg);
            }
            return this; 
    } 
    public boolean isEditable() {
        return false;
    }
}
}