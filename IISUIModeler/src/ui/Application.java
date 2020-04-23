package ui;

public class Application 
{
  public String name="";
  public FormType formType[];
  public String menu="";
  public String about="";
  public String dsn="";
  
public Application()
{
}
public void applicationExample()
{
    name="Application System";
    menu="Application";
    Attribute[] att=new Attribute[20];
    AttType[] attType=new AttType[20];
    formType=new FormType[6];
    Domain[] domain=new Domain[3];
    domain[0]=new Domain(1,"String",250,-1);
    domain[1]=new Domain(1,"Number",250,-1);
    domain[2]=new Domain(1,"Boolean",250,-1);
    att[0]=new Attribute(1,"attribute1",domain[0],-1);
    att[1]=new Attribute(2,"attribute2",domain[0],-1);
    att[2]=new Attribute(3,"attribute3",domain[1],-1);
    att[3]=new Attribute(4,"attribute4",domain[0],-1);
    att[4]=new Attribute(5,"attribute5",domain[0],-1);
    att[5]=new Attribute(6,"attribute6",domain[2],-1);
    att[6]=new Attribute(7,"attribute7",domain[0],-1);
    att[7]=new Attribute(8,"attribute8",domain[0],-1);
    att[8]=new Attribute(9,"attribute9",domain[1],-1);
    att[9]=new Attribute(10,"attribute10",domain[0],-1);
    att[10]=new Attribute(11,"attribute11",domain[0],-1);
    att[11]=new Attribute(12,"attribute12",domain[0],-1);
    att[12]=new Attribute(13,"attribute13",domain[0],-1);
    att[13]=new Attribute(14,"attribute14",domain[0],-1);
    att[14]=new Attribute(15,"attribute15",domain[0],-1);
    att[15]=new Attribute(16,"attribute16",domain[0],-1);
    att[16]=new Attribute(17,"attribute17",domain[0],-1);
    att[17]=new Attribute(18,"attribute18",domain[0],-1);
    att[18]=new Attribute(19,"attribute19",domain[0],-1);
    att[19]=new Attribute(20,"attribute20",domain[0],-1);
    String[] list={};
    attType[0]=new AttType(1,1,att[0],"Item 1",1,1,1,1,1,"",0,20,250,0,0,-1,-1,list);
    attType[1]=new AttType(2,2,att[1],"Item 2",0,1,1,1,1,"default value",0,20,250,0,0,-1,-1,list);
    attType[2]=new AttType(3,3,att[2],"Item 3",0,1,1,1,1,"",0,60,450,1,0,-1,-1,list);
    attType[3]=new AttType(4,4,att[3],"Item 4",0,1,1,1,1,"Item 4 Value",0,60,150,1,1,-1,-1,list);
    attType[4]=new AttType(5,5,att[4],"Item 5 long",0,1,1,1,1,"true",2,20,20,0,0,-1,-1,list);
    attType[5]=new AttType(6,6,att[5],"Item 6",0,1,1,1,1,"2",3,20,150,0,0,-1,0,list);
    attType[6]=new AttType(7,7,att[6],"Item 7",0,1,1,1,1,"4",4,40,100,0,0,-1,-1,list);
    attType[7]=new AttType(8,8,att[7],"Item 8",0,1,1,1,1,"",0,20,250,0,0,-1,-1,list);
    String[] list1={"yes", "no"};
    attType[8]=new AttType(9,9,att[8],"Item 9 long",0,1,1,1,1,"0",1,20,40,0,0,1,-1,list1);
    attType[9]=new AttType(10,10,att[9],"Item 10",0,1,1,1,1,"",3,20,100,0,0,-1,1,list);
    attType[10]=new AttType(1,1,att[10],"Item 11",1,1,1,1,1,"",0,20,250,0,0,-1,-1,list);
    attType[11]=new AttType(1,1,att[11],"Item 12",1,1,1,1,1,"",0,20,250,0,0,-1,-1,list);
    attType[12]=new AttType(1,1,att[12],"Item 13",1,1,1,1,1,"",0,20,250,0,0,-1,-1,list);
    attType[13]=new AttType(1,1,att[13],"Item 14",1,1,1,1,1,"",0,20,250,0,0,-1,-1,list);
    attType[14]=new AttType(1,1,att[14],"Item 15",1,1,1,1,1,"",0,20,250,0,0,-1,-1,list);
    attType[15]=new AttType(1,1,att[15],"Item 16",1,1,1,1,1,"",0,20,250,0,0,-1,-1,list);
    attType[16]=new AttType(1,1,att[16],"Item 17",1,1,1,1,1,"",0,20,250,0,0,-1,-1,list);
    attType[17]=new AttType(1,1,att[17],"Item 18",1,1,1,1,1,"",0,20,250,0,0,-1,-1,list);
    attType[18]=new AttType(1,1,att[18],"Item 19",1,1,1,1,1,"",0,20,250,0,0,-1,-1,list);
    attType[19]=new AttType(1,1,att[19],"Item 20",1,1,1,1,1,"",0,20,250,0,0,-1,-1,list);

   // public AttType(int id, int i, String tit, int mand,  int query, int ins, int upd, int nul, String default_val, int t, int h, int w, int multiline, int scroll, int orientation, int editable)
   
    Item[] items=new Item[2];
    items[0]=new Item(0,attType[0],1);
    items[1]=new Item(1,attType[1],1);
    ItemGroup[] itemGr=new ItemGroup[2];
    itemGr[0]=new ItemGroup(1,"itemGroup1","Item Group 1",0,0,0,items);
    items=new Item[1];
    items[0]=new Item(2,attType[3],1);
    itemGr[1]=new ItemGroup(2,"itemGroup2","Item Group 2",0,0,1,items);
    CompType[] comp=new CompType[1];
    comp[0]=new CompType(1,"compType1","Component Type",0,-1,1,1,1,1,1,1,0,-1,0,-1,-1,0,1,itemGr);
    formType[0]=new FormType(1,"formType1","Form Type Table",comp,0x46);
    
    items=new Item[2];
    items[0]=new Item(0,attType[3],1);
    items[1]=new Item(1,attType[4],1);
    itemGr=new ItemGroup[2];
    itemGr[0]=new ItemGroup(1,"nestedItemGroup1","Nested Item Group 1",0,0,0,items);
    items=new Item[1];
    items[0]=new Item(2,attType[5],1);
    itemGr[1]=new ItemGroup(2,"nestedItemGroup2","Nested Item Group 2",0,0,0,items);
    items=new Item[2];
    items[0]=new Item(0,itemGr[0],0);
    items[1]=new Item(1,itemGr[1],0);
    itemGr=new ItemGroup[2];
    itemGr[0]=new ItemGroup(1,"itemGroup1","Item Group 1",0,0,1,items);
    items=new Item[2];
    items[0]=new Item(0,attType[7],1);
    items[1]=new Item(1,attType[8],1);
    itemGr[1]=new ItemGroup(2,"itemGroup2","Item Group 2",0,0,0,items);
    comp=new CompType[1];
    comp[0]=new CompType(2,"compType2","Component Type",0,-1,1,1,1,1,0,0,0,-1,0,-1,-1,0,1,itemGr);
    formType[1]=new FormType(2,"formType2","Form Type Nested Item Groups", comp, 0x4E);
    
    items=new Item[4];
    items[0]=new Item(0,attType[6],1);
    items[1]=new Item(1,attType[7],1);
    items[2]=new Item(0,attType[8],1);
    items[3]=new Item(1,attType[9],1);
    itemGr=new ItemGroup[3];
    itemGr[0]=new ItemGroup(1,"itemGroup1","Item Group 1",0,0,0,items);
    items=new Item[1];
    items[0]=new Item(1,attType[1],0);
    itemGr[1]=new ItemGroup(2,"itemGroup2","Item Group 2",0,0,0,items);
    items=new Item[1];
    items[0]=new Item(1,attType[10],0);
    itemGr[2]=new ItemGroup(3,"itemGroup3","Item Group 3",0,1,0,items);
    comp=new CompType[1];
    comp[0]=new CompType(3,"compType3","Component Type",0,-1,1,1,1,1,0,0,0,-1,0,-1,-1,0,1,itemGr);
    formType[2]=new FormType(3,"formType3","Form Type 3 Item Groups", comp,0x33);
    
    items=new Item[10];
    items[0]=new Item(0,attType[10],0);
    items[1]=new Item(1,attType[11],1);
    items[2]=new Item(0,attType[12],0);
    items[3]=new Item(1,attType[13],1);
    items[4]=new Item(0,attType[14],0);
    items[5]=new Item(1,attType[15],1);
    items[6]=new Item(0,attType[16],0);
    items[7]=new Item(1,attType[17],1);
    items[8]=new Item(0,attType[18],0);
    items[9]=new Item(1,attType[19],1);
    itemGr=new ItemGroup[1];
    itemGr[0]=new ItemGroup(1,"itemGroup1","Item Group",0,0,0,items);
    comp=new CompType[1];
    comp[0]=new CompType(4,"compType4","Component Type",0,-1,1,1,1,1,0,0,0,-1,0,-1,-1,1,1,itemGr);
    formType[3]=new FormType(4,"formType4","Form Type Input Fields with Search", comp,0x49);
    
    
    items=new Item[2];
    items[0]=new Item(0,attType[0],1);
    items[1]=new Item(1,attType[1],1);
    itemGr=new ItemGroup[2];
    itemGr[0]=new ItemGroup(1,"itemGroup1","Item Group 1",0,0,0,items);
    items=new Item[1];
    items[0]=new Item(2,attType[2],1);
    itemGr[1]=new ItemGroup(2,"itemGroup2","Item Group 2",0,0,1,items);
    comp=new CompType[2];
    comp[0]=new CompType(5,"compType5","Component Type 1",0,-1,1,1,1,1,0,0,0,-1,0,-1,-1,0,0,itemGr);
    items=new Item[2];
    items[0]=new Item(0,attType[7],1);
    items[1]=new Item(1,attType[8],1);    
    itemGr=new ItemGroup[1];
    itemGr[0]=new ItemGroup(1,"itemGroup1","Item Group",0,0,0,items);
    comp[1]=new CompType(51,"compType51","Component Type 2",0,5,1,1,1,1,0,1,0,-1,0,-1,-1,0,0,itemGr);
    formType[4]=new FormType(5,"formType5","Form Type New Window Subcomponents",comp,0x54);
    
    items=new Item[1];
    items[0]=new Item(0,attType[1],1);
    
    itemGr=new ItemGroup[1];
    itemGr[0]=new ItemGroup(0,"itemGroup1","Item Group",0,0,0,items);
    comp=new CompType[2];
    comp[0]=new CompType(6,"compType6","Component Type 1",0,-1,1,1,1,1,1,0,0,-1,0,-1,-1,0,0,itemGr);
    
    items=new Item[4];
    items[0]=new Item(0,attType[10],0);
    items[1]=new Item(1,attType[11],1);
    items[2]=new Item(0,attType[12],0);
    items[3]=new Item(1,attType[13],1);
    itemGr=new ItemGroup[1];
    itemGr[0]=new ItemGroup(0,"itemGroup1","Item Group",0,0,0,items);
    
    comp[1]=new CompType(7,"compType7","Child Component Type 2",0,6,1,1,1,1,1,1,0,-1,0,-1,-1,0,0,itemGr);
    formType[5]=new FormType(6,"formType6","Form Type Master-Detail", comp,0x44);
}    
          
public class FormType 
{
  public int id;
  public String mnem;
  public int mnemonic;
  public String name="";
  public CompType[] compType;
  public FormType(int i, String mn, String nam, CompType[] comps, int mnemon)
  {
    id=i;
    mnem=mn;
    name=nam;
    compType=comps;
    mnemonic=mnemon;
  }
}
public class CompType 
{
    public int id;
    public String mnem;
    public String name;
    public int mandatory;
    public int superord;
    public int query_allowed;
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
    public CompType(int i, String mn, String nam, int mand, int supero, int query, int ins, int upd, int del, int lay, int data_lay, int ord, int pos, int position_rel, int x_rel, int y_rel, int s, int del_m, ItemGroup[] itemG)
    {
        id=i;
        mnem=mn;
        name=nam;
        mandatory=mand;
        superord=supero;
        query_allowed=query;
        ins_allowed=ins;
        upd_allowed=upd;
        del_allowed=del;
        layout=lay;
        data_layout=data_lay;
        order=ord;
        position=pos;
        position_relative=position_rel;
        x_relative=x_rel;
        y_relative=y_rel;
        search=s;
        del_masive=del_m;
        itemGroup=itemG;
    }
    public Application.AttType[] getAttributes(){
        Application.AttType[] Att=new Application.AttType[getAttributesCount()];
        int k=0;
        for(int i=0;i<this.itemGroup.length; i++) {
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
    public int getAttributesCount(){
        int k=0;
        for(int i=0;i<this.itemGroup.length; i++) {
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
}
public class AttType 
{
    public int att_type_id;
    public int att_id;
    public Attribute att;
    public String title;
    public int mandatory;
    public int query_allowed;
    public int ins_allowed;
    public int upd_allowed;
    public int null_allowed;
    public String default_value;
    public int type;
    public int height;
    public int width;
    public int text_multiline;
    public int text_scroll;
    public int radio_orientation;
    public int combo_editable;
    public String[] list_of_values;
    public AttType(int id, int i, Attribute _att,String tit, int mand,  int query, int ins, int upd, int nul, String default_val, int t, int h, int w, int multiline, int scroll, int orientation, int editable, String[] list)
    {
        att_type_id=id;
        att_id=i;
        att=_att;
        title=tit; 
        mandatory=mand;
        query_allowed=query;
        ins_allowed=ins;
        upd_allowed=upd;
        null_allowed=nul;
        default_value=default_val;
        type=t;
        height=h;
        width=w;
        text_multiline=multiline;
        text_scroll=scroll;
        radio_orientation=orientation;
        combo_editable=editable;
        list_of_values=list;
    }
}
public class Attribute 
{
    public int id;
    public String mnem;
    public Domain domain;
    public int renamed_from=-1;
    public Attribute(int i, String mn, Domain dom, int renamed)
    {
        id=i;
        mnem=mn;
        domain=dom;
        renamed_from=renamed;
    }
}
public class Domain 
{
    public int id;
    public String mnem;
    public int length;
    public int decimal;
    public Domain(int i, String mn, int l, int dec)
    {
        id=i;
        mnem=mn;
        length=l;
        decimal=dec;
    }
}
public class ItemGroup 
{
    public int id;
    public String name;
    public String title;
    public int sequence;
    public int contex;
    public int overflow;
    public Item[] items;
    public ItemGroup(int i, String nam, String tit, int seq, int cont, int over, Item[] it)
    {
        id=i;
        name=nam;
        title=tit;
        sequence=seq;
        contex=cont;
        overflow=over;
        items=it;
    }
}
public class Item 
{
    public int breakline;
    public int order;
    public Object elem;
    public int x_label;
    public int y_label;
    public int x_input;
    public int y_input;
    public int width;
    public int height;
    public int input_width;
    public int label_width;
    public Item(int ord, Object e, int breakl)
    {
        breakline=breakl;
        order=ord;
        elem=e;
    }
}
}