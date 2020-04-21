package ui;

import iisc.JDBCQuery;

import java.sql.Connection;

import java.sql.ResultSet;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JOptionPane;

public class ComponentType {
    String name;
    int id;
    Set attributes=new HashSet();
    Set elemAttributes=new HashSet();
    Set extAttributes=new HashSet();
    Set keys=new HashSet();
    Set descedents=new HashSet();
    Set ascedents=new HashSet();
    Set[] key=new HashSet[0];
    public ComponentType(){
    }
    public ComponentType(Application.CompType cmp){
        name=cmp.name;
        id=cmp.id;
        setAtt(cmp);
        setKeys(cmp);
        setExtAtt(cmp);
        setElemAtt(cmp);
    }
    public void setAtt(Application.CompType cmp){
        for(int i=0;i<cmp.itemGroup.length;i++){
            Application.ItemGroup itemgr=cmp.itemGroup[i];
            for(int j=0;j<itemgr.items.length;j++){
            if(itemgr.items[j].elem.getClass().toString().equals("class ui.Application$AttType"))
                attributes.add(((Application.AttType)itemgr.items[j].elem).att);
            else
            {
                Application.ItemGroup ig=(Application.ItemGroup)itemgr.items[j].elem;
                for(int m=0;m<ig.items.length; m++)
                    attributes.add(((Application.AttType)ig.items[m].elem).att);
            }
        }
    }
    }
    public void setExtAtt(Application.CompType cmp){
        for(int i=0;i<cmp.itemGroup.length;i++){
            Application.ItemGroup itemgr=cmp.itemGroup[i];
            for(int j=0;j<itemgr.items.length;j++){
            if(itemgr.items[j].elem==null)
            continue;
            if( itemgr.items[j].elem.getClass().toString().equals("class ui.Application$AttType")&&((Application.AttType)itemgr.items[j].elem).att==null)
            continue;
            if(itemgr.items[j].elem.getClass().toString().equals("class ui.Application$AttType") && ((Application.AttType)itemgr.items[j].elem).att.elementary!=-1)
                extAttributes.add(""+((Application.AttType)itemgr.items[j].elem).att.id);
            else if(!itemgr.items[j].elem.getClass().toString().equals("class ui.Application$AttType"))
            {
                Application.ItemGroup ig=(Application.ItemGroup)itemgr.items[j].elem;
                for(int m=0;m<ig.items.length; m++)
                    if(((Application.AttType)ig.items[m].elem).att.elementary!=-1)
                        extAttributes.add(""+((Application.AttType)ig.items[m].elem).att.id);
            }
        }
    }
    }
    public void setElemAtt(Application.CompType cmp){
        for(int i=0;i<cmp.itemGroup.length;i++){
            Application.ItemGroup itemgr=cmp.itemGroup[i];
            for(int j=0;j<itemgr.items.length;j++){
            if(itemgr.items[j].elem.getClass().toString().equals("class ui.Application$AttType") && ((Application.AttType)itemgr.items[j].elem).att.elementary==-1)
                elemAttributes.add(""+((Application.AttType)itemgr.items[j].elem).att_id);
            else if(!itemgr.items[j].elem.getClass().toString().equals("class ui.Application$AttType"))
            {
                Application.ItemGroup ig=(Application.ItemGroup)itemgr.items[j].elem;
                for(int m=0;m<ig.items.length; m++)
                    if(((Application.AttType)ig.items[m].elem).att.elementary==-1)
                        elemAttributes.add(""+((Application.AttType)ig.items[m].elem).att_id);
            }
        }
    }
    }
    public void setKeys(Application.CompType cmp){
        keys=new HashSet();
        key=new HashSet[cmp.keys.length];
        for(int i=0;i<cmp.keys.length;i++) {
            Iterator it = cmp.keys[i].iterator();
            Set k=new HashSet();
            while(it.hasNext()){
                int att=((Application.AttType)it.next()).att_id;
                k.add(""+att);
            }
            keys.add(k);
            key[i]=k;
        }
    }

    public void setDescedent(ComponentType desc) {
        descedents.add(desc);
    }
    public void setAscedent(int desc) {
        ascedents.add(""+desc);
    }
    public void print()
    {
        print(this);
    }
    public void print(ComponentType comp)
    {
        System.out.print("("+comp.name);
        System.out.print(comp.attributes);
        System.out.print(",");
        System.out.print(comp.keys);
        System.out.println(")");
        Iterator it=comp.descedents.iterator();
        Set at=new HashSet();
        while(it.hasNext()){
            ComponentType desc=(ComponentType)it.next();
            desc.print();
        }    
    }
}
