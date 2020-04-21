package ui;

import iisc.JDBCQuery;

import iisc.RScheme;

import iisc.RelationScheme;

import iisc.Synthesys;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class Application 
{
  public ApplicationSystem application;
  public ApplicationSystem[] subApplication;
  public Connection connection;
  public int tf_entry;
  public int pr_id;
  private Connection dbConn;
  private int bid;
  
public Application(Connection con,int id, int _bid, Connection dbc)
{
    connection=con;
    dbConn=dbc;
    bid=_bid;
    application=getApplication(id);
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select PR_id from IISC_APP_SYSTEM where AS_id="+ id);
    rs.next();
    pr_id=rs.getInt(1);
    query.Close();
    rs=query.select("select count(*) from IISC_APP_SYSTEM_CONTAIN  where AS_id="+ id);
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    subApplication=new ApplicationSystem[j];
    String[] appid=query.selectArray1("select AS_id_con from IISC_APP_SYSTEM_CONTAIN  where AS_id="+ id,j,1);
    query.Close();
    for(int k=0;k<appid.length;k++)
        subApplication[k]=getApplication((new Integer(appid[k])).intValue());
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
}

public int getTFNo(FormType[] tf){
    int k=0;
    for(int i=0;i<tf.length;i++)
        if(tf[i].type!=2)k++;
    return k;
}
public SubSchemaGen[] generateSubSchemes(int _pr){
    int pom=getTFNo(application.formType);;
    for(int i=0;i<subApplication.length;i++)
        pom=pom+getTFNo(subApplication[i].formType);
    SubSchemaGen[] schemas=new SubSchemaGen[pom];
    int k=0;
    for(int i=0;i<application.formType.length;i++)
        if(application.formType[i].type!=2){
            schemas[k]= new SubSchemaGen(_pr, application.id, application.formType[i], connection);
            k++;
        }
    pom=k;
    for(int i=0;i<subApplication.length;i++)
        for(int j=0;j<subApplication[i].formType.length;j++)
        {
            if(subApplication[i].formType[j].type!=2){
                schemas[pom]= new SubSchemaGen(_pr, subApplication[i].id, subApplication[i].formType[j], connection);
                pom=pom+1;
            }
        }
    return schemas;
}
public ApplicationSystem getApplication(int id)
{
    ApplicationSystem app=new ApplicationSystem();
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select * from IISC_APP_SYSTEM, IISC_BUSINESS_APPLICATION  where IISC_BUSINESS_APPLICATION.BA_id="+bid+" and IISC_APP_SYSTEM.AS_id="+ id);
    if(rs.next()) {
        app.setApplicationSystem(id, rs.getString("AS_name"), getFormTypes(id), rs.getString("AS_dsn"), rs.getString("AS_username"), rs.getString("AS_password"), rs.getInt("Tf_entry_id"));   
    }
    setParameters(app);
    query.Close();
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return app;
}
private void setParameters(ApplicationSystem app){
    for(int i=0; i<app.formType.length; i++){
        FormType f=app.formType[i];
        Iterator its1=f.parentMenu.iterator();
        while(its1.hasNext()) {
            Application.Menu txt1=(Application.Menu)its1.next();
            Iterator its2=txt1.passedValues.iterator();
            while(its2.hasNext()) {
                Application.PassedValue txt2=(Application.PassedValue)its2.next();
                int fp= txt2.from_parameter;
                int tp= txt2.to_parameter;
                for(int j=0; j<app.formType.length; j++){
                    Iterator its3=app.formType[j].parameters.iterator();
                    while(its3.hasNext()) {
                        Application.Parameter txt3=(Application.Parameter)its3.next();
                        if(txt2.from_parameter==txt3.id)
                            txt2.f_parameter=txt3;
                        if(txt2.to_parameter==txt3.id)
                            txt2.t_parameter=txt3;
                    }
                }
            }
        }
    }
}
private FormType[] getFormTypes(int id) {
    FormType[] formTypes=new FormType[0];
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select count(*) from IISC_TF_APPSYS, IISC_FORM_TYPE  where IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id and AS_id="+ id); //and TF_use<>2
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    formTypes=new FormType[j];
    String[] appid=query.selectArray1("select IISC_FORM_TYPE.TF_id from IISC_TF_APPSYS, IISC_FORM_TYPE   where IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id and AS_id="+ id,j,1);//and TF_use<>2
    query.Close();
    for(int k=0;k<appid.length;k++)
        formTypes[k]=getFormType((new Integer(appid[k])).intValue());
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return formTypes;
}
public FormType getFormType(int id)
{
    FormType formType=new FormType();
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select * from IISC_FORM_TYPE  where TF_id="+ id);
    while(rs.next()) {
        formType.setFormType(id, rs.getString("TF_mnem"), rs.getString("TF_title"), getCompTypes(id), rs.getInt("Tf_use"), getParameters(id),getMenus(id),getParentMenus(id));   
    }
    query.Close();
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return formType;
}
public Set getParameters(int id)
{
    Set parameters=new HashSet();
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select Par_id,Par_mnem, Par_desc,Par_def_value, Att_id, Tob_id from IISC_PARAMETER  where TF_id="+ id);
    while(rs.next()) {
        Parameter pom=new Parameter();
        pom.id=rs.getInt(1);
        pom.mnem=rs.getString(2);
        pom.desc=rs.getString(3);
        pom.default_value=rs.getString(4); 
        pom.value=pom.default_value;
        pom.att=rs.getInt(5); 
        pom.tob=rs.getInt(6); 
        parameters.add(pom);
    }
    query.Close();
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return parameters;
}
public Vector getMenus(int id)
{
    Vector menu=new Vector();
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select CS_id, Called_tf_id, CMet_id, CMod_id,CS_call_UI_position from IISC_CALLING_STRUCT  where Caller_tf_id="+id+" and BA_id="+ bid);
    while(rs.next()) {
        Menu pom=new Menu();
        pom.id=rs.getInt(1);
        pom.called_tf=rs.getInt(2);
        pom.mode=rs.getInt(4);
        pom.method=rs.getInt(3);
        pom.ui_position=rs.getInt(5);
        pom.passedValues=getPassedValues(pom.id);  
        menu.add(pom);
    }
    query.Close();
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return menu;
}
public Vector getParentMenus(int id)
{
    Vector menu=new Vector();
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select CS_id, Caller_tf_id, CMet_id, CMod_id,CS_call_UI_position from IISC_CALLING_STRUCT  where Called_tf_id="+id+" and  BA_id="+ bid);
    while(rs.next()) {
        Menu pom=new Menu();
        pom.id=rs.getInt(1);
        pom.called_tf=rs.getInt(2);
        pom.mode=rs.getInt(4);
        pom.method=rs.getInt(3);
        pom.ui_position=rs.getInt(5);
        pom.passedValues=getPassedValues(pom.id);  
        menu.add(pom);
    }
    query.Close();
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return menu;
}
public Vector getPassedValues(int id)
{
    Vector values=new Vector();
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select PV_To_Par_id,PV_type,PV_value,PV_From_Par_id,Att_id,Tob_id from IISC_PASSED_VALUE  where CS_id="+id);
    while(rs.next()) {
        PassedValue pom=new PassedValue();
        pom.to_parameter=rs.getInt(1);
        pom.type=rs.getInt(2);
        pom.value=rs.getString(3);
        pom.from_parameter=rs.getInt(4);
        pom.att=rs.getInt(5); 
        pom.tob=rs.getInt(6);  
        values.add(pom);
    }
    query.Close();
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return values;
}
private CompType[] getCompTypes(int id) {
    CompType[] compTypes=new CompType[0];
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select count(*) from IISC_COMPONENT_TYPE_OBJECT_TYPE  where TF_id="+ id);
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    compTypes=new CompType[j];
    String[] appid=query.selectArray1("select TOB_id from IISC_COMPONENT_TYPE_OBJECT_TYPE  where TF_id="+ id+" order by TOB_superord",j,1);
    query.Close();
    for(int k=0;k<appid.length;k++)
        compTypes[k]=getCompType((new Integer(appid[k])).intValue());
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return compTypes;
}
private CompType getCompType(int id)
{
    CompType compType=new CompType();
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE, IISC_COMPTYPE_DISPLAY  where IISC_COMPONENT_TYPE_OBJECT_TYPE.TOB_id=IISC_COMPTYPE_DISPLAY.TOB_id and IISC_COMPTYPE_DISPLAY.TOB_id="+ id);
    if(rs.next()) {
        String supord=rs.getString("TOB_superord");
        if(supord==null )supord="-1";
        compType.setCompType(id, rs.getString("TOB_mnem"), rs.getString("TOB_name"), rs.getInt("TOB_mandatory"), (new Integer(supord)).intValue(),
        rs.getString("TOB_queallow").equals("Y")?1:0, rs.getString("TOB_insallow").equals("Y")?1:0, rs.getString("TOB_updallow").equals("Y")?1:0, rs.getString("Tob_deleteallow").equals("Y")?1:0, rs.getInt("TOB_layout"),
        rs.getInt("TOB_data_layout"), rs.getInt("TOB_order"), rs.getInt("TOB_position"), rs.getInt("TOB_position_relative"), rs.getInt("TOB_x_relative"),
        rs.getInt("TOB_y_relative"), rs.getInt("TOB_search"), rs.getInt("TOB_del_masive"), getItemGroups(id), getKeys(id), getUniques(id), rs.getInt("Tob_retain_last_ins_record"));  
    }
    else
    return null;
    query.Close();
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return compType;
}
    public RelationScheme[] getCorespRelScheme(int cid)
    {  
    try{
    boolean go=false;
    Set tob=new HashSet();
    String str=new String();
    JDBCQuery query=new JDBCQuery(connection),query1=new JDBCQuery(connection);
    ResultSet rs,rs1;
    Set s=new HashSet();
    rs1=query1.select("select * from IISC_ATT_TOB  where TOB_id="+cid );
    while(rs1.next())
    {s.add(""+rs1.getInt("Att_id"));
    }
    query1.Close();
    Synthesys syn=new Synthesys();
    rs1=query1.select("select * from IISC_RELATION_SCHEME WHERE AS_id="+application.id );
    while(rs1.next())
    {RelationScheme RS=syn.getRelationScheme(rs1.getInt("RS_id"));  
    if(!syn.Presjek(RS.atributi,s).isEmpty())
    { 
    tob.add(""+RS);
    }
    }
    query1.Close();
    RelationScheme[] rels=new RelationScheme[tob.size()];
    Iterator it=tob.iterator();
    int i=0;
    while(it.hasNext()) {
        rels[i]=(RelationScheme)it.next();
        i++;
    }
    return rels;
    }
    
    catch(Exception e)
    {
    e.printStackTrace();
    } 
    return null;
    }
private Set[] getKeys(int tid)
{
    Set[] keys=new HashSet[0];
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select count(*) from IISC_KEY_TOB where TOB_ID="+ tid );
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    String[] sa=query.selectArray1("select TOB_rbrk from IISC_KEY_TOB where TOB_ID="+ tid +" order by TOB_rbrk" ,j,1);
    keys=new HashSet[sa.length];
    for (int k=0;k<sa.length;k++){
        rs=query.select("select Att_id from IISC_ATT_KTO  where TOB_id="+ tid +" and TOB_rbrk="+ sa[k]+" order by Att_rbrk" );
        keys[k]=new HashSet();
        while(rs.next())
        {
            keys[k].add(getAttType(rs.getInt("Att_id"),tid));
        }
        query.Close();
        
    }
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return keys;
}
private Set[] getUniques(int tid)
{
    Set[] uniques=new HashSet[0];
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select count(*) from IISC_UNIQUE_TOB where TOB_ID="+ tid );
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    String[] sa=query.selectArray1("select TOB_rbrk from IISC_UNIQUE_TOB where TOB_ID="+ tid +" order by TOB_rbrk" ,j,1);
    uniques=new HashSet[sa.length];
    query.Close();
    for (int k=0;k<sa.length;k++){
        rs=query.select("select Att_id from IISC_ATT_UTO  where TOB_id="+ tid +" and TOB_rbrk="+ sa[k]+" order by Att_rbrk" );
        uniques[k]=new HashSet();
        while(rs.next())
            uniques[k].add(getAttType(rs.getInt("Att_id"),tid));
        query.Close();
    }

    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return uniques;
}
private ItemGroup[] getItemGroups(int id) {
    ItemGroup[] ItemGroups=new ItemGroup[0];
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select count(*) from IISC_ITEM_GROUP  where Tob_id="+ id);
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    ItemGroups=new ItemGroup[j];
    String[] appid=query.selectArray1("select IG_id from IISC_ITEM_GROUP  where TOB_id="+ id+" order by IG_sequence",j,1);
    query.Close();
    for(int k=0;k<appid.length;k++)
        ItemGroups[k]=getItemGroup((new Integer(appid[k])).intValue());
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return ItemGroups;
}
private ItemGroup getItemGroup(int id)
{
    ItemGroup itemGroup=new ItemGroup();
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select * from IISC_ITEM_GROUP  where IG_id="+ id+ " ORDER BY IG_sequence");
    while(rs.next()) {
        itemGroup.setItemGroup(id, rs.getString("IG_name"), rs.getString("IG_title"), rs.getInt("IG_sequence"),
        rs.getInt("IG_contex"), rs.getInt("IG_overflow"), getItems(id));  
    }
    query.Close();
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return itemGroup;
}

private Item[] getItems(int id) {
    Item[] items=new Item[0];
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select count(*) from IISC_IG_ITEM  where IG_id="+ id);
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    items=new Item[j];
    String[] appid=query.selectArray1("select IGI_id from IISC_IG_ITEM  where IG_id="+ id+" order by IGI_order",j,1);
    query.Close();
    for(int k=0;k<appid.length;k++)
        items[k]=getItem((new Integer(appid[k])).intValue());
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return items;
}
private Item getItem(int id)
{
    Item item=new Item();
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select * from IISC_IG_ITEM where IGI_id="+ id+ " ORDER BY IGI_order");
    while(rs.next()) {
        Object ob=new Object();
        String nested=rs.getString("IG_nested_group_id");
        if(nested==null)
            ob=(Object)getAttType(rs.getInt("Att_id"), rs.getInt("TOB_id"));
        else
            ob=(Object)getItemGroup((new Integer(nested)).intValue());
        item.setItem(id, ob, rs.getInt("IGI_breakline"), rs.getInt("IGI_order"));  
    }
    query.Close();
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return item;
}

private AttType getAttType(int att_id, int tob_id)
{
    AttType attType=new AttType();
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select * from IISC_ATT_TOB LEFT JOIN IISC_COMPTYPE_ATTRIB_DISPLAY ON (IISC_ATT_TOB.Att_id=IISC_COMPTYPE_ATTRIB_DISPLAY.Att_id and IISC_ATT_TOB.Tob_id=IISC_COMPTYPE_ATTRIB_DISPLAY.Tob_id) where  IISC_ATT_TOB.Att_id="+ att_id + " and IISC_ATT_TOB.Tob_id="+ tob_id+ " ORDER BY w_order");
    while(rs.next()) {
        int[] display=getDisplay(att_id,tob_id);
        attType.setAttType(tob_id, att_id, getAttribute(att_id), rs.getInt("W_order"), rs.getString("W_tittle"), rs.getString("W_mand").equals("Y")?1:0, rs.getString("W_queallow").equals("Y")?1:0, 
        rs.getString("W_insallow").equals("Y")?1:0, rs.getString("W_updallow").equals("Y")?1:0, rs.getString("W_nullallow").equals("Y")?1:0, rs.getString("W_default"), rs.getInt("W_behav"),
        display[0], display[1], display[2], display[3], display[4], 
        display[5], display[6], getFunction(rs.getInt("Fun_id")),display[7], getValues(att_id,tob_id), getListofValues(rs.getInt("LV_id")));  
        
        //Aleksandar - Begin 
        Statement stmt = connection.createStatement();
        ResultSet rs1 = stmt.executeQuery("select * from IISC_ATT_TOB_EVENT where Att_id=" + att_id + " and Tob_id="+tob_id + " and Event_level=1 and Event_type=3 and Event_id=9");
        
        if (rs1.next())
        {
            attType.generateButon = true;
        }
        //Aleksandar - End 
    }
    query.Close();
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return attType;
}

private Attribute getAttribute(int att_id)
{
    Attribute attribute=new Attribute();
    try
    {
    ResultSet rs,rs1;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select * from IISC_ATTRIBUTE where Att_id="+ att_id);
    while(rs.next()) {
        Set derAtts=new HashSet();
        rs1=query.select("select * from IISC_DERIVED_ATTRIBUTE where Att_id="+ att_id);
        while(rs1.next())
            derAtts.add(rs1.getString("Att_id_derived"));
        attribute.setAttribute(att_id, rs.getString("Att_mnem"), getDomain(rs.getInt("Dom_id")), getFunction(rs.getInt("Fun_id")), rs.getString("Att_name"), 
        rs.getString("Att_expr"), rs.getInt("Att_sbp"), rs.getInt("Att_elem"), rs.getInt("Att_der"), rs.getString("Att_default"), derAtts); 
    }
    query.Close();
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return attribute;
}
private Domain getDomain(int dom_id)
{
    Domain domain=new Domain();
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select * from IISC_DOMAIN where Dom_id="+ dom_id);
    while(rs.next()) {
        domain.setDomain(dom_id, rs.getString("Dom_mnem"), rs.getString("Dom_name"), rs.getInt("Dom_type"), getPrimitiveType(rs.getInt("Dom_data_type")),
        rs.getInt("Dom_length"), rs.getString("Dom_reg_exp_str"), getDomain(rs.getInt("Dom_parent")), rs.getInt("Dom_decimal"), rs.getString("Dom_default")); 
    }
    query.Close();
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return domain;
}
private PrimitiveType getPrimitiveType(int pt_id)
{
    PrimitiveType primitiveType=new PrimitiveType();
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select * from IISC_PRIMITIVE_TYPE where PT_id="+ pt_id);
    while(rs.next()) {
        primitiveType.setPrimitiveType(pt_id, rs.getString("PT_mnemonic"), rs.getString("PT_name"), rs.getInt("PT_length_required"), rs.getInt("PT_length"),
        rs.getString("PT_decimal"), rs.getString("PT_def_val")); 
    }
    query.Close();
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return primitiveType;
}
private Function getFunction(int id)
{
    Function function=new Function();
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select * from IISC_FUNCTION where Fun_id="+ id);
    while(rs.next()) {
        function.setFunction(id, rs.getString("Fun_name")); 
    }
    query.Close();
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return function;
}
private String[][] getValues(int att_id, int tob_id)
{
    String[][] values=new String[0][0];
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select count(*) from IISC_COMP_ATT_DISPLAY_VALUES where Att_id="+ att_id + " and Tob_id="+ tob_id);
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    if(j>0){
        values=new String[j][2];
        int i=0;
        rs=query.select("select * from IISC_COMP_ATT_DISPLAY_VALUES where Att_id="+ att_id + " and Tob_id="+ tob_id+ " order by CADV_seq");
        while(rs.next()) {
            values[i][0]=rs.getString("CADV_value"); 
            values[i][1]=rs.getString("CADV_value_display"); 
            i++;
        }
        query.Close();
    }
    else
        values=getAttributeValues(att_id);
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return values;
}
private String[][] getAttributeValues(int att_id)
{
    String[][] values=new String[0][0];
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select count(*) from IISC_ATT_DISPLAY_VALUES where Att_id="+ att_id);
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    if(j>0){
        values=new String[j][2];
        int i=0;
        rs=query.select("select * from IISC_ATT_DISPLAY_VALUES where Att_id="+ att_id + " order by ADV_seq");
        while(rs.next()) {
            values[i][0]=rs.getString("ADV_value"); 
            values[i][1]=rs.getString("ADV_value_display"); 
            i++;
        }
        query.Close();
    }
    else
    {
        rs=query.select("select * from IISC_ATTRIBUTE where Att_id="+ att_id);
        if(rs.next()) {
            values=getDomainValues(rs.getInt("Dom_id"));
        }
        query.Close();    
    }
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return values;
}
private String[][] getDomainValues(int dom_id)
{
    String[][] values=new String[0][0];
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select count(*) from IISC_DOM_DISPLAY_VALUES where Dom_id="+ dom_id);
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    values=new String[j][2];
    int i=0;
    rs=query.select("select * from IISC_DOM_DISPLAY_VALUES where Dom_id="+ dom_id + " order by DDV_seq");
    while(rs.next()) {
        values[i][0]=rs.getString("DDV_value"); 
        values[i][1]=rs.getString("DDV_value_display"); 
        i++;
    }
    query.Close();
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return values;
}
    private int[]  getDisplay(int att_id, int tob_id)
    {
        int[] values=new int[0];
        try
        {
        ResultSet rs;
        JDBCQuery query=new JDBCQuery(connection);
        rs=query.select("select count(*) from IISC_COMPTYPE_ATTRIB_DISPLAY where Att_id="+ att_id + " and Tob_id="+ tob_id);
        int j=0;
        if(rs.next())j=rs.getInt(1);
        query.Close();
        if(j>0){
            values=new int[8];
            int i=0;
            rs=query.select("select * from IISC_COMPTYPE_ATTRIB_DISPLAY where Att_id="+ att_id + " and Tob_id="+ tob_id);
            if(rs.next()) {
                values[0]=rs.getInt("CAD_type");
                values[1]=rs.getInt("CAD_height");
                values[2]=rs.getInt("CAD_width");
                values[3]=rs.getInt("CAD_text_multiline");
                values[4]=rs.getInt("CAD_text_scroll");
                values[5]=rs.getInt("CAD_radio_orientation");
                values[6]=rs.getInt("CAD_combo_editable");
                values[7]=rs.getInt("CAD_input_align"); 
            }
            query.Close();
        }
        else
            values=getAttributeDisplay(att_id);
        }
        catch(SQLException e)
        {
         e.printStackTrace();
        }
        return values;
    }
    private int[]  getAttributeDisplay(int att_id)
    {
        int[] values=new int[0];
        try
        {
        ResultSet rs;
        JDBCQuery query=new JDBCQuery(connection);
        rs=query.select("select count(*) from IISC_ATTRIBUTE_DISPLAY where Att_id="+ att_id);
        int j=0;
        if(rs.next())j=rs.getInt(1);
        query.Close();
        if(j>0){
            values=new int[8] ;
            int i=0;
            rs=query.select("select * from IISC_ATTRIBUTE_DISPLAY where Att_id="+ att_id);
            if(rs.next()) {
                values[0]=rs.getInt("AD_type");
                values[1]=rs.getInt("AD_height");
                values[2]=rs.getInt("AD_width");
                values[3]=rs.getInt("AD_text_multiline");
                values[4]=rs.getInt("AD_text_scroll");
                values[5]=rs.getInt("AD_radio_orientation");
                values[6]=rs.getInt("AD_combo_editable");
                values[7]=rs.getInt("AD_input_align"); 
            }
            query.Close();
        }
        else
        {
            rs=query.select("select * from IISC_ATTRIBUTE where Att_id="+ att_id);
            if(rs.next()) {
                values=getDomainDisplay(rs.getInt("Dom_id"));
            }
            query.Close();    
        }
        }
        catch(SQLException e)
        {
         e.printStackTrace();
        }
        return values;
    }
    private int[] getDomainDisplay(int dom_id)
    {
        int[] values=new int[0];
        try
        {
        ResultSet rs;
        JDBCQuery query=new JDBCQuery(connection);
        rs=query.select("select count(*) from IISC_DOMAIN_DISPLAY where Dom_id="+ dom_id);
        int j=0;
        if(rs.next())j=rs.getInt(1);
        query.Close();
        values=new int[8];
        int i=0;
        rs=query.select("select * from IISC_DOMAIN_DISPLAY where Dom_id="+ dom_id );
        if(rs.next()) {
            values[0]=rs.getInt("DD_type");
            values[1]=rs.getInt("DD_height");
            values[2]=rs.getInt("DD_width");
            values[3]=rs.getInt("DD_text_multiline");
            values[4]=rs.getInt("DD_text_scroll");
            values[5]=rs.getInt("DD_radio_orientation");
            values[6]=rs.getInt("DD_combo_editable");
            values[7]=rs.getInt("DD_input_align"); 
        }
        query.Close();
        }
        catch(SQLException e)
        {
         e.printStackTrace();
        }
        return values;
    }
private ListOfValues getListofValues (int id) {
    ListOfValues listOfValues=new ListOfValues();
    try
    {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select LV_type,IISC_ATT_TOB.Att_id,IISC_LIST_OF_VALUES.TF_id,LV_search,LV_regular_expression,LV_mand_validation from IISC_LIST_OF_VALUES, IISC_ATT_TOB where IISC_LIST_OF_VALUES.LV_id=IISC_ATT_TOB.LV_id and IISC_LIST_OF_VALUES.LV_id="+ id);
    if(rs.next()) {
        int at=rs.getInt(2);
        listOfValues.setListOfValues(id, rs.getInt("LV_type"), at, getFormType( rs.getInt(3)), rs.getInt("LV_search"), rs.getString("LV_regular_expression"), rs.getInt("LV_mand_validation"), getReturns(id)); 
    }
    query.Close();
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return listOfValues;
}
private int[][] getReturns(int id){
    int[][] values=new int[0][0];
    try
    {
    ResultSet rs, rs1;
    JDBCQuery query=new JDBCQuery(connection),query1=new JDBCQuery(connection);
    rs=query.select("select TOB_id from IISC_ATT_TOB where LV_id="+ id);
    rs.next();
    int c=rs.getInt(1);
    query.Close();
    rs=query.select("select count(*) from IISC_LV_RETURN where LV_id="+ id);
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    values=new int[j][4];
    int i=0;
    rs=query.select("select * from IISC_LV_RETURN,IISC_LIST_OF_VALUES where IISC_LV_RETURN.LV_id=IISC_LIST_OF_VALUES.LV_id and IISC_LV_RETURN.LV_id="+ id);
    while(rs.next()) {
        values[i][0]=rs.getInt("LV_return_att_id"); 
        values[i][1]=rs.getInt("Att_id"); 
        values[i][2]=c;
        rs1=query1.select("select TOB_id from IISC_ATT_TOB where Att_id="+ values[i][1] + " and TF_id="+rs.getInt("TF_id"));
        if(rs1.next()) {
            values[i][3]=rs1.getInt(1);
        }
        query1.Close();
        i++;
    }
    query.Close();
    }
    catch(SQLException e)
    {
     e.printStackTrace();
    }
    return values;
}
public FormType getFormT(int id) {
    for(int i=0;i< application.formType.length; i++)
        if(application.formType[i].id==id)return application.formType[i];
    FormType f=null;
    for(int i=0;i< subApplication.length; i++)
        for(int j=0;j< subApplication[i].formType.length; j++)
            if(subApplication[i].formType[j].id==id)f=subApplication[i].formType[j];
    return f;
}
class ApplicationSystem 
{
  public int id;
  public String name="";
  public FormType formType[];
  public String dsn="";
  public String username="";
  public String password="";
  public int entry;
public ApplicationSystem(){
}
public void setApplicationSystem(int _id, String _name, FormType[] _formType, String _dsn, String _username, String _password, int _entry)
{
    id=_id;
    name=_name;
    formType=_formType;
    dsn=_dsn;
    username=_username;
    password=_password;
    entry=_entry;
}
}   
public class FormType 
{
  public int id;
  public String mnem;
  public String name;
  public int type;
  public CompType[] compType;
  public Set Pu=new HashSet();
  public Set Pa=new HashSet();
  public Set parameters=new HashSet();
  public Vector menu=new Vector();
  public Vector parentMenu=new Vector();
  public FormType(){
  }
  public void setFormType(int _id, String _mnem, String _name, CompType[] _compType, int _type, Set _parameters, Vector _menu, Vector _parentMenu)
  {
    id=_id;
    mnem=_mnem;
    name=_name;
    compType=_compType;
    type=_type;
    parameters=_parameters;
    menu=_menu;
    parentMenu=_parentMenu;
  }
}
public class Parameter 
{
    public int id;
    public String mnem;
    public String desc;
    public String default_value;
    public int att;
    public int tob;
    public String value="";
    public Object attribute;
}
public class Menu 
{
    public int id;   
    public int called_tf;
    public int method;
    public int mode;
    public int ui_position;
    public Vector passedValues=new Vector();
}
public class PassedValue 
{
    public int from_parameter;
    public int to_parameter;
    public int type;
    public int att;
    public int tob;
    public String value;
    public Object attribute;
    public Parameter f_parameter;
    public Parameter t_parameter;
}
class CompType 
{
    public int id;
    public String mnem;
    public String name;
    public int mandatory;
    public int superord;
    public int que_allowed;
    public int ins_allowed;
    public int upd_allowed;
    public int del_allowed;
    public int layout;
    public int data_layout;
    public int order;
    public int position;
    public int position_relative;
    public int x_relative;
    public int y_relative;
    public int search;
    public int del_masive;
    public ItemGroup[] itemGroup;
    public RelationScheme[] rels;
    public Set[] keys;
    public Set[] uniques;
    public int retain_last_ins_record;
    public  CompType(){
    }
    public void setCompType(int _id, String _mnem, String _name, int _mandatory, int _superord, int _que_allowed, int _ins_allowed, int _upd_allowed, int _del_allowed, 
    int _layout, int _data_layout, int _order, int _position, int _position_relative, int _x_relative, int _y_relative, int _search, int _del_masive, ItemGroup[] _itemGroup, Set[] _keys, Set[] _uniques, int _retain_last_ins_record)
    {
        id=_id;
        mnem=_mnem;
        name=_name;
        mandatory=_mandatory;
        superord=_superord;
        que_allowed=_que_allowed;
        ins_allowed=_ins_allowed;
        upd_allowed=_upd_allowed;
        del_allowed=_del_allowed;
        layout=_layout;
        data_layout=_data_layout;
        order=_order;
        position=_position;
        position_relative=_position_relative;
        x_relative=_x_relative;
        y_relative=_y_relative;
        search=_search;
        del_masive=_del_masive;
        itemGroup=_itemGroup;
        keys=_keys;
        uniques=_uniques;
        retain_last_ins_record=_retain_last_ins_record;
    }
    public int getAttributesCount(){
        int k=0;
        Set nig=this.getNestedItemGroups();
        for(int i=0;i<this.itemGroup.length; i++) {
           if(nig.contains(""+this.itemGroup[i].id))continue;
           for(int j=0;j<this.itemGroup[i].items.length; j++) {
           if(this.itemGroup[i].items[j].elem.getClass().toString().equals("class ui.Application$AttType")){
               k++;
           }
           else{
               Application.ItemGroup ig=(Application.ItemGroup)this.itemGroup[i].items[j].elem;
               for(int m=0;m<ig.items.length; m++) {
                   k++;
               }
           }
           }     
        }
        return k;
    }
    public Application.AttType[] getAttributes(){
        Application.AttType[] Att=new Application.AttType[getAttributesCount()];
        int k=0;
        Set nig=this.getNestedItemGroups();
        for(int i=0;i<this.itemGroup.length; i++) {
           if(nig.contains(""+this.itemGroup[i].id))
            continue;
           for(int j=0;j<this.itemGroup[i].items.length; j++) {
           if(this.itemGroup[i].items[j].elem.getClass().toString().equals("class ui.Application$AttType")){
               Att[k]=(Application.AttType)this.itemGroup[i].items[j].elem;
               k++;
           }
           else{
               Application.ItemGroup ig=(Application.ItemGroup)this.itemGroup[i].items[j].elem;
               for(int m=0;m<ig.items.length; m++) {
                   Att[k]=(Application.AttType)ig.items[m].elem;
                   k++;
               }
           }
           }     
        }
        return Att;
    }
    
    public Set getNestedItemGroups(){
        Set nig=new HashSet();
        for(int i=0;i<this.itemGroup.length; i++) {
           for(int j=0;j<this.itemGroup[i].items.length; j++) {
               if(this.itemGroup[i].items[j].elem.getClass().toString().equals("class ui.Application$ItemGroup")){
                   Application.ItemGroup ig=(Application.ItemGroup)this.itemGroup[i].items[j].elem;
                   nig.add(""+ig.id);
               }    
            }
        }
        return nig;
    }
}
class AttType 
{
    public int tob_id;
    public int att_id;
    public Attribute att;
    public int order;
    public String title;
    public int mandatory;
    public int que_allowed;
    public int ins_allowed;
    public int upd_allowed;
    public int null_allowed;
    public int behavior;
    public String default_value="";
    public int default_index_value=-1;
    public int type;
    public int height;
    public int width;
    public int text_multiline;
    public int text_scroll;
    public int radio_orientation;
    public int combo_editable;
    public Function function;
    public int input_align;
    public String[][] values;
    public ListOfValues list_of_values;
    //Aleksandar - Begin
    public boolean generateButon = false;
    public boolean existsInDbScheme = true;
    //Aleksandar - End
        
    public AttType(){
    }
    public void setAttType(int _tob_id, int _att_id, Attribute _att, int _order, String _title, int _mandatory, int _que_allowed, int _ins_allowed, int _upd_allowed, int _null_allowed, String _default_value, int _behavior, int _type, 
    int _height, int _width, int _text_multiline, int _text_scroll, int _radio_orientation, int _combo_editable, Function _function, int _input_align, String[][] _values, ListOfValues _list_of_values)
    {
        tob_id=_tob_id;
        att_id=_att_id;
        att=_att;
        order=_order;
        title=_title; 
        mandatory=_mandatory;
        que_allowed=_que_allowed;
        ins_allowed=_ins_allowed;
        upd_allowed=_upd_allowed;
        null_allowed=_null_allowed;
        if(_default_value==null)_default_value="";
        default_value=_default_value;
        behavior=_behavior;
        type=_type;
        height=_height;
        width=_width;
        text_multiline=_text_multiline;
        text_scroll=_text_scroll;
        radio_orientation=_radio_orientation;
        combo_editable=_combo_editable;
        input_align=_input_align;
        function=_function;
        values=_values;
        for(int i=0;i<values.length;i++){
            if(values[i][0].equals(default_value))default_index_value=i;
        }
        list_of_values=_list_of_values;
        if(default_value==null || default_value.equals(""))default_value=att.default_value;
    }
    public String getValue(String[][] data){
        return "";
    }
}
class Attribute 
{
    public int id;
    public String mnem;
    public Domain domain;
    public Function function;
    public String name;
    public String regular_expression;
    public int sbp;
    public int elementary;
    public int derived_from;
    public String default_value;
    public Set derived_atts=new HashSet();
    public Attribute(){
    }
    public void setAttribute(int _id, String _mnem, Domain _domain, Function _function, String _name, String _regular_expression, int _sbp, int _elementary, int _derived_from, String _default_value, Set _derived_atts)
    {
        id=_id;
        mnem=_mnem;
        name=_name;
        domain=_domain;
        function=_function;
        regular_expression=_regular_expression;
        sbp=_sbp;
        elementary=_elementary;
        derived_from=_derived_from;
        if(_default_value==null)_default_value="";
        default_value=_default_value;
        derived_atts=_derived_atts;
        if(default_value.equals(""))default_value=domain.default_value;
    }
}
class Domain 
{
    public int id;
    public String mnem;
    public String name;
    public int type;
    public PrimitiveType primitiveType;
    public int length;
    public String regular_expression;
    public Domain parent;
    public int decimal;
    public String default_value="";
    public Domain(){
    }
    public void setDomain(int _id, String _mnem, String _name, int _type, PrimitiveType _primitiveType, int _length, String _regular_expression, Domain _parent, int _decimal, String _default_value)
    {
        id=_id;
        mnem=_mnem;
        name=_name;
        type=_type;
        primitiveType=_primitiveType;
        length=_length;
        regular_expression=_regular_expression;
        parent=_parent;
        decimal=_decimal;
        if(_default_value==null)_default_value="";
        default_value=_default_value;
        if(default_value.equals(""))default_value=parent.default_value;
        if(default_value.equals("") && primitiveType!=null)default_value=primitiveType.default_value;
    }
}
class PrimitiveType 
{
    public int id;
    public String mnem;
    public String name;
    public int type;
    public int required_length;
    public int length;
    public int decimal=0;
    public String default_value;
    public PrimitiveType(){
    }
    public void setPrimitiveType(int _id, String _mnem, String _name, int _required_length, int _length,  String _decimal, String _default_value)
    {
        id=_id;
        mnem=_mnem;
        name=_name;
        required_length=_required_length;
        length=_length;
        if(_decimal!=null && !_decimal.equals(""))
        decimal=(new Integer(_decimal)).intValue();
        if(_default_value==null)_default_value="";
        default_value=_default_value;
    }
}
class Function 
{
    public int id;
    public String name;
    public Function(){
    }
    public void setFunction(int _id, String _name)
    {
        id=_id;
        name=_name;
    }
}
class ListOfValues 
{
    public int id;
    public int att_id;
    public int type;
    public int search;
    public String regular_expression;
    public int mand_validation;
    public int[][] returns;
    public FormType tf;
    public Object[][] returnsAttribute;
    public ListOfValues(){
    }
    public void setListOfValues(int _id, int _type, int _att_id, FormType _tf, int _search, String _regular_expression, int _mand_validation, int[][] _returns)
    {
        id=_id;
        att_id= _att_id;
        type=_type;
        search=_search;
        regular_expression=_regular_expression;
        mand_validation=_mand_validation;
        returns=_returns;
        tf=_tf;
    }
}
class ItemGroup 
{
    public int id;
    public String name;
    public String title;
    public int sequence;
    public int contex;
    public int overflow;
    public Item[] items;
    public ItemGroup(){
    }
    public void setItemGroup(int _id, String _name, String _title, int _sequence, int _contex, int _overflow, Item[] _items)
    {
        id=_id;
        name=_name;
        title=_title;
        sequence=_sequence;
        contex=_contex;
        overflow=_overflow;
        items=_items;
    }
}
class Item 
{
    public int id;
    public Object elem;
    public int breakline;
    public int order;
    public int x_label;
    public int y_label;
    public int x_input;
    public int y_input;
    public int width;
    public int height;
    public int input_width;
    public int label_width;
    public Item(){
    }
    public void setItem(int _id, Object _elem, int _breakline, int _order, int _x_label, int _y_label, int _x_input, int _y_input, 
    int _width, int _height, int _input_width, int _label_width)
    {
        id=_id;
        elem=_elem;
        breakline=_breakline;
        order=_order;
        x_label=_x_label;
        y_label=_y_label;
        x_input=_x_input;
        y_input=_y_input;
        width=_width;
        height=_height;
        input_width=_input_width;
        label_width=_label_width;
    }
    public void setItem(int _id, Object _elem, int _breakline, int _order)
    {
        id=_id;
        elem=_elem;
        breakline=_breakline;
        order=_order;
    }
}
}
