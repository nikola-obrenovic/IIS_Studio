package iisc;


import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * This class is the object representation of the form type
 * used in the tranformation of check constraints.
 * 
 * @author      Nikola Obrenovic (nikob at sbb.rs)
 * @version     0.1 20/05/10
 */
public class CheckConFormType {
    
    protected int projID;
    protected int formID;
    Connection conn;
    public CheckConComponentType root;
    protected ArrayList<CheckConComponentType> compTypes = new ArrayList<CheckConComponentType>();
    
    public CheckConFormType(int projID, int formID, Connection con) {
        this.projID = projID;
        this.formID = formID;
        this.conn = con;
        loadFormType(projID,formID,con);
    }
    
    public void loadFormType(int projID, int formID, Connection con){
        
        try
        {
            ResultSet rs;
            JDBCQuery query=new JDBCQuery(con);
            rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TF_id=" + formID + " AND PR_id=" + projID);
            while(rs.next()) {
                int compID = rs.getInt("TOB_id");
                CheckConComponentType comp = new CheckConComponentType(projID,formID,compID,con);
                for(int i=0;i<compTypes.size();i++)
                {
                    if(compTypes.get(i).getSuperord().equals(((Integer)comp.getCompID()).toString()))
                    {
                        comp.setDescedent(compTypes.get(i));
                        compTypes.get(i).setAscedent(comp);
                    }
                    else if(((Integer)compTypes.get(i).getCompID()).toString().equals(comp.getSuperord()))
                    {
                        comp.setAscedent(compTypes.get(i));
                        compTypes.get(i).setDescedent(comp);
                    }
                }
                if(comp.getSuperord().equals("-1"))
                    root = comp;
                    
                compTypes.add(comp);
            }
            query.Close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public CheckConComponentType getComp(CheckConComponentType comp, int id){
        if(comp.getCompID()==id)
            return comp;
        Iterator it=comp.descedents.iterator();
        while(it.hasNext()){
            CheckConComponentType cmp=(CheckConComponentType)it.next();
            if(cmp.getCompID()==id)
                return cmp;
            CheckConComponentType cm=getComp(cmp, id);
            if(cm!=null)
                return cm;
        }
        return null;
    }
    public Set getTtilde(CheckConComponentType cmp, Set supKey){
        Iterator it=cmp.descedents.iterator();
        Set gr=new HashSet();
        while(it.hasNext()){
            Iterator it1=cmp.keys.iterator();
            CheckConComponentType desc=(CheckConComponentType)it.next();
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
    public Set getNF(CheckConComponentType cmp){
        Iterator it=cmp.descedents.iterator();
        Set gr=new HashSet();
        //Set key1=(HashSet)cmp.keys.iterator().next();
        Set key1=cmp.key[0];
        Set key=new HashSet();
        key.addAll(key1);
        while(it.hasNext()){
            CheckConComponentType desc=(CheckConComponentType)it.next();
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
    public Set getJF(CheckConComponentType cmp, Set supKey){
        Iterator it=cmp.descedents.iterator();
        Set gr=new HashSet();
        while(it.hasNext()){
            Iterator it1=cmp.keys.iterator();
            CheckConComponentType desc=(CheckConComponentType)it.next();
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
    public Set getT(CheckConComponentType cmp){
        Iterator it=cmp.descedents.iterator();
        Set gr=new HashSet();
        //Set key1=(HashSet)cmp.keys.iterator().next();
        Set key1=cmp.key[0];
        Set key=new HashSet();
        key.addAll(key1);
        while(it.hasNext()){
            CheckConComponentType desc=(CheckConComponentType)it.next();
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
    public Set getAttributes(CheckConComponentType cmp){
        Iterator it=cmp.descedents.iterator();
        Set at=new HashSet();
        while(it.hasNext()){
            CheckConComponentType desc=(CheckConComponentType)it.next();
            at.addAll(getAttributes(desc));
        }
        at.addAll(cmp.attributes);   
        
        return at;
    }
    public Set getUnijaPoJednogKljuca(CheckConComponentType cmp, Set ascKey){
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
                CheckConComponentType cm=getComp(root,asc);
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
    public Set getPrimarnaObiljezja(CheckConComponentType cmp){
        Iterator it=cmp.descedents.iterator();
        Set at=new HashSet();
        while(it.hasNext()){
            CheckConComponentType desc=(CheckConComponentType)it.next();
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
