package ui;

import iisc.JDBCQuery;

import java.sql.Connection;
import java.sql.ResultSet;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FormType {
    ComponentType root=new ComponentType();
    public FormType(Application.FormType f, Connection con) {
        for(int i=0;i< f.compType.length;i++) {
            if(f.compType[i].superord==-1){
               root=getComp(f.compType[i],f);
               break;
            }      
        }
    }
    public ComponentType getComp(Application.CompType cmp, Application.FormType f){
        ComponentType comp=new ComponentType(cmp);
        for(int i=0;i< f.compType.length;i++) {
            if(f.compType[i].superord==cmp.id){
                ComponentType desc=getComp(f.compType[i],f);
                comp.setDescedent(desc);
            }
            if(f.compType[i].id==cmp.superord){
                comp.setAscedent(f.compType[i].id);
            }
        }
        return comp;
    }
    public ComponentType getComp(ComponentType comp, int id){
        if(comp.id==id)
            return comp;
        Iterator it=comp.descedents.iterator();
        while(it.hasNext()){
            ComponentType cmp=(ComponentType)it.next();
            if(cmp.id==id)
                return cmp;
            ComponentType cm=getComp(cmp, id);
            if(cm!=null)
                return cm;
        }
        return null;
    }
    public Set getTtilde(ComponentType cmp, Set supKey){
        Iterator it=cmp.descedents.iterator();
        Set gr=new HashSet();
        while(it.hasNext()){
            Iterator it1=cmp.keys.iterator();
            ComponentType desc=(ComponentType)it.next();
            while(it1.hasNext()){
                Set key1=(HashSet)it1.next();
                Set key=new HashSet();
                key.addAll(key1);
                key.addAll(supKey);
                Set groups=getTtilde(desc, key);
                Iterator it2=groups.iterator();
                gr.add(key);
                while(it2.hasNext()){
                    Set i=(HashSet)it2.next();
                    i.addAll(key);
                }
                gr.addAll(groups);
            }
        }
        if(gr.isEmpty())gr.addAll(cmp.keys);
        return gr;
    }
    public Set getNF(ComponentType cmp){
        Iterator it=cmp.descedents.iterator();
        Set gr=new HashSet();
        //Set key1=(HashSet)cmp.keys.iterator().next();
        Set key1=cmp.key[0];
        Set key=new HashSet();
        key.addAll(key1);
        while(it.hasNext()){
            ComponentType desc=(ComponentType)it.next();
            Set groups=getNF(desc);
            Iterator it1=groups.iterator();
            while(it1.hasNext()){
                Set i=(HashSet)it1.next();
                i.addAll(key);
            }
            gr.addAll(groups);
        }
        if(gr.isEmpty())gr.add(key);
        Set pom=new HashSet();
        pom.addAll(cmp.attributes);
        for(int i=0;i<cmp.key.length;i++)
            pom.removeAll(cmp.key[i]);
        if(cmp.descedents.isEmpty()&& !pom.isEmpty())return new HashSet();
        return gr;
    }
    public Set getJF(ComponentType cmp, Set supKey){
        Iterator it=cmp.descedents.iterator();
        Set gr=new HashSet();
        while(it.hasNext()){
            Iterator it1=cmp.keys.iterator();
            ComponentType desc=(ComponentType)it.next();
            while(it1.hasNext()){
                Set key1=(HashSet)it1.next();
                Set key=new HashSet();
                key.addAll(key1);
                key.addAll(supKey);
                Set groups=getTtilde(desc, key);
                Iterator it2=groups.iterator();
                gr.add(key);
                while(it2.hasNext()){
                    Set i=(HashSet)it2.next();
                    i.addAll(key);
                }
                gr.addAll(groups);
            }
        }
        it=cmp.keys.iterator();
        Set K=new HashSet();
        while(it.hasNext()){
            Set key=(HashSet)it.next();
            K.addAll(key);
        }
        if(K.containsAll(cmp.attributes) && gr.isEmpty())gr.addAll(cmp.keys);
        return gr;
    }
    public Set getT(ComponentType cmp){
        Iterator it=cmp.descedents.iterator();
        Set gr=new HashSet();
        //Set key1=(HashSet)cmp.keys.iterator().next();
        Set key1=cmp.key[0];
        Set key=new HashSet();
        key.addAll(key1);
        while(it.hasNext()){
            ComponentType desc=(ComponentType)it.next();
            Set groups=getT(desc);
            Iterator it1=groups.iterator();
            while(it1.hasNext()){
                Set i=(HashSet)it1.next();
                i.addAll(key);
            }
            gr.addAll(groups);
        }
        if(gr.isEmpty())gr.add(key);
        return gr;
    }
    public Set getAttributes(ComponentType cmp){
        Iterator it=cmp.descedents.iterator();
        Set at=new HashSet();
        while(it.hasNext()){
            ComponentType desc=(ComponentType)it.next();
            at.addAll(getAttributes(desc));
        }
        it=cmp.attributes.iterator();
        while(it.hasNext()){
            Application.Attribute att=(Application.Attribute)it.next();
            at.add(""+att.id);   
        }
        return at;
    }
    public Set getElemAttributes(ComponentType cmp){
        Iterator it=cmp.descedents.iterator();
        Set at=new HashSet();
        while(it.hasNext()){
            ComponentType desc=(ComponentType)it.next();
            at.addAll(getElemAttributes(desc));
        }
        at.addAll(cmp.elemAttributes);
        return at;
    }
    public Set getExtAttributes(ComponentType cmp){
        Iterator it=cmp.descedents.iterator();
        Set at=new HashSet();
        while(it.hasNext()){
            ComponentType desc=(ComponentType)it.next();
            at.addAll(getExtAttributes(desc));
        }
        at.addAll(cmp.extAttributes);
        return at;
    }
    public Application.Attribute getAttribute(ComponentType cmp,int att_id){
        Iterator it=cmp.attributes.iterator();
        while(it.hasNext()){
            Application.Attribute att=(Application.Attribute)it.next();
            if(att.id==att_id)
                return att;
        }
        it=cmp.descedents.iterator();
        while(it.hasNext()){
            ComponentType desc=(ComponentType)it.next();
            Application.Attribute att=getAttribute(desc, att_id);
            if(att!=null)
                return att;
        }
        return null;
    }
    public Set getUnijaPoJednogKljuca(ComponentType cmp, Set ascKey){
        Iterator it=cmp.keys.iterator();
        Set gr=new HashSet();
        while(it.hasNext()){
            Set key=(HashSet)it.next();
            Set X=new HashSet();
            X.addAll(key);
            X.addAll(ascKey);
            Iterator itd=cmp.ascedents.iterator();
            while(itd.hasNext()){
                int asc=(new Integer((String)itd.next())).intValue();
                ComponentType cm=getComp(root,asc);
                Set XSI=getUnijaPoJednogKljuca(cm, X);
                Iterator it1=XSI.iterator();
                while(it1.hasNext()){
                    Set keya=(HashSet)it1.next();
                    keya.addAll(X);
                    gr.add(keya);   
                }
            }
            if(gr.isEmpty())
                gr.add(X);
        }
        return gr;
    }
    public Set getPrimarnaObiljezja(ComponentType cmp){
        Iterator it=cmp.descedents.iterator();
        Set at=new HashSet();
        while(it.hasNext()){
            ComponentType desc=(ComponentType)it.next();
            at.addAll(getPrimarnaObiljezja(desc));
        }
        Iterator itt=cmp.keys.iterator();
        while(itt.hasNext()){
            at.addAll((Set)itt.next());
        }
        return at;
    }
    public void print() {
        System.out.println();
        System.out.println("Root");
        root.print();
    }
}
