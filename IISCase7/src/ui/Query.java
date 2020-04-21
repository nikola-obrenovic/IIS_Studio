package ui;

import iisc.JDBCQuery;
import iisc.RelationScheme;
import iisc.Synthesys;

import java.sql.Connection;

import java.sql.ResultSet;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JOptionPane;

public class Query {
    Synthesys syn=new Synthesys();
    Application.FormType tf;
    RelationScheme table;
    Set uTables;
    String uQuery;
    String aQuery;
    String keyQuery;
    Set aTables;
    Application.CompType cmp;
    Set attributes;
    Set supKey;
    Set[] a;
    Set[] u;
    Connection connection;
    Set updateAtt;
    Set invalidAtt=new HashSet();
    Set alreadyChecked=new HashSet();
    int tf_entry;
    public Query(Connection _conn, int _as, int _pr, Application.FormType _tf, int _comp, Connection _connection, int _tf_entry) {
        syn.con=_conn;
        syn.pr=_pr;
        syn.as=_as;        
        tf=_tf;
        tf_entry=_tf_entry;
        cmp=getComp(_comp);
        supKey=getSuperiorsKey(cmp.id);
        attributes=getAttributes();
        table=getTable();
        Set subTree=getSubTree(table,tf.Pa);
        aTables=subTree;
        a=getQuery(aTables,table);
        aQuery=implode(getArray(a[0])," AND ");
        uTables =getQueryTables(tf.Pu);
        u=getQuery(uTables,table);
        uQuery=implode(getArray(u[0])," AND ");
        connection=_connection;
    }
    public void printS(Set a){
        if(a==null)return;
        Iterator it=a.iterator();
        while(it.hasNext()) {
            RelationScheme r=(RelationScheme)it.next();
            r.print();
        }
        System.out.println();
    }
    public Set[] getQuery(Set rs_set, RelationScheme rs){
        Set pom[]=new HashSet[2];
        pom[0]=new HashSet();
        pom[1]=new HashSet();
        if(rs==null)
            return pom;
        pom[1].add(rs);
        Iterator it=rs_set.iterator();
        while(it.hasNext()){
            RelationScheme rel=(RelationScheme)it.next();
            if(rel.id==rs.id)continue;
            pom[0].addAll(getConnection(rs, rel));
            pom[1].add(rel);
        }
        return pom;
    }
    public String[] getSelectAtts(String[] atts){
        for(int i=0;i<atts.length; i++) {
            atts[i]=getSelectAtt(atts[i]);
        }
        return atts;
    }
    public String getSelectAtt(String att){
        Iterator it=u[1].iterator();
        int at=getAttributeId(att);
        boolean check=false;
        while(it.hasNext()){
            RelationScheme rs=(RelationScheme)it.next();
            if(rs.atributi.contains(""+at))
            {    
                check=true;
                break;
            }
        }
        if(!check)att="";
        return att;
    }
    public String[] getQueryAtts(String[] atts){
        for(int i=0;i<atts.length; i++) {
            atts[i]=getQueryAtt(atts[i]);
        }
        return atts;
    }
    public String getQueryAtt(String att){
        Iterator it=u[1].iterator();
        int at=getAttributeId(att);
        while(it.hasNext()){
            RelationScheme rs=(RelationScheme)it.next();
            if(rs.atributi.contains(""+at))
            {    
                att=rs.name+"."+att; 
                break;
            }
        }
        return att;
    }
    public String[] getArray(Set set){
        String[] s=new String[set.size()];
        Iterator it=set.iterator();
        int i=0;
        while(it.hasNext()){
            s[i]=it.next().toString();
            i++;
        }
        return s;
    }
    public Set getConnection(RelationScheme a, RelationScheme b){
        Set pom=new HashSet();
        Iterator it=b.primarni_kljuc.iterator();
        while(it.hasNext()){
            int att=(new Integer(it.next().toString())).intValue();
            if(a.atributi.contains(""+att))
            {
                String att_name=getAttributeName(""+att);
                pom.add(a.name+"."+ att_name +"="+b.name+"."+ att_name);
            }
        }
        return pom;
    }
    public String getAttributeName(String id){
        try{
         JDBCQuery query=new JDBCQuery(syn.con);
         ResultSet rs;
         String att="";
         rs=query.select("select Att_mnem from IISC_ATTRIBUTE where Att_id=" + id + " and PR_id=" + syn.pr);
         if(rs.next()){
           att=rs.getString(1);
         }
         query.Close();  
         return att;
         }
         catch(Exception e)
         {
         e.printStackTrace();
         return null;
         }
    }
    public String getAttributeTitle(String id){
        try{
         JDBCQuery query=new JDBCQuery(syn.con);
         ResultSet rs;
         String att="";
         rs=query.select("select W_tittle from IISC_ATT_TOB where Att_id=" + id + " and IISC_ATT_TOB.Tob_id="+ cmp.id +" and PR_id=" + syn.pr);
         if(rs.next()){
           att=rs.getString(1);
         }
         query.Close();  
         return att;
         }
         catch(Exception e)
         {
         e.printStackTrace();
         return null;
         }
    }
    public boolean isAttributeInteger(String id){
        try{
         JDBCQuery query=new JDBCQuery(syn.con);
         ResultSet rs;
         int type;
         rs=query.select("select PT_id from IISC_ATTRIBUTE, IISC_DOMAIN, IISC_PRIMITIVE_TYPE where IISC_ATTRIBUTE.Dom_id=IISC_DOMAIN.Dom_id and IISC_DOMAIN.dom_data_type=IISC_PRIMITIVE_TYPE.PT_id and IISC_ATTRIBUTE.Att_id=" + id + " and IISC_ATTRIBUTE.PR_id=" + syn.pr);
         if(rs.next()){
           type=rs.getInt(1);
           if(type==1 || type==2)return true;
         }
         query.Close();  
         return false;
         }
         catch(Exception e)
         {
         e.printStackTrace();
         return false;
         }
    }
    public int getAttributeId(String mnem){
        int att=-1;
        try{
         JDBCQuery query=new JDBCQuery(syn.con);
         ResultSet rs;
         rs=query.select("select Att_id from IISC_ATTRIBUTE where Att_mnem='" + mnem + "' and PR_id=" + syn.pr);
         if(rs.next()){
           att=rs.getInt(1);
         }
         query.Close();  
         return att;
         }
         catch(Exception e)
         {
         e.printStackTrace();
         return att;
         }
    }
    public RelationScheme getTable(){
        Iterator it=tf.Pu.iterator();
        while(it.hasNext()){
         RelationScheme rs=(RelationScheme)it.next();
         if(supKey.contains(rs.primarni_kljuc))
            return rs;
        }
        Set setrs=new HashSet();
        it=tf.Pa.iterator();
        while(it.hasNext()){
         RelationScheme rs=(RelationScheme)it.next();
         Iterator it1=rs.kljuc.iterator();
         boolean can=false;
         while(it1.hasNext()&!can){
            Set key=(HashSet)it1.next();
             Iterator it2=supKey.iterator();
             while(it2.hasNext()&!can){
                Set key1=(HashSet)it2.next();
                if(!syn.Presjek(key,key1).isEmpty())
                {
                    setrs.add(rs);
                    can=true;
                }
            }
         }
        }
        it=setrs.iterator();
        while(it.hasNext()){
         RelationScheme rs=(RelationScheme)it.next();
         Set sup=getSuperiors(setrs,rs);
         if(sup.isEmpty())
            return rs;
        }        
        return null;
    }
    public Set getQTables(Set rs_set, RelationScheme rs, Set pomSet){
        Set result=new HashSet();
        Set rss=getSuperiors(rs_set, rs);
        //rss.remove(rs);
        Iterator it=rss.iterator();
        while(it.hasNext()){
            RelationScheme rs1=(RelationScheme)it.next();
            boolean can=false;
            if(!syn.Presjek(rs1.primarni_kljuc,table.atributi).isEmpty())can=true;
            /*Iterator it1=supKey.iterator();
            while(it1.hasNext()){
               Set key=(HashSet)it1.next();
               if(!syn.Presjek(rs1.primarni_kljuc,key).isEmpty())can=true;
            }*/
             //if(!syn.Presjek(rs1.atributi,attributes).isEmpty()&&can){
             if(table.atributi.containsAll(rs1.primarni_kljuc) && can){
                 pomSet.addAll(rs1.atributi);
                 result.add(rs1);
             }
            //if(pomSet.containsAll(attributes))break;
            result.addAll(getQTables(rs_set,rs1, pomSet));
        }
       return result; 
    }
    public Set getSubTree(RelationScheme rs, Set P){
        Set pomSet=new HashSet();
        pomSet.add(rs);
        Set pom=getSuperiors(P, rs);
        Iterator it=pom.iterator();
        while(it.hasNext()){
         RelationScheme rs1=(RelationScheme)it.next();
         pomSet.addAll(getSubTree(rs1,P));
        }
        return pomSet;
    }
    public Set getQueryTables(Set P){
        Set pomTables=new HashSet();
        Set pomSet=new HashSet();
        Set pom=getMinNodes(P);
        Iterator it=pom.iterator();
        while(it.hasNext()){
         RelationScheme rs=(RelationScheme)it.next();
         boolean can=false;
         if(!syn.Presjek(rs.primarni_kljuc,table.atributi).isEmpty())can=true;
         /*Iterator it1=supKey.iterator();
         while(it1.hasNext()){
            Set key=(HashSet)it1.next();
            if(!syn.Presjek(rs.primarni_kljuc,key).isEmpty())can=true;
         }*/
         //if(!syn.Presjek(rs.atributi,attributes).isEmpty() && can){
          if(table.atributi.containsAll(rs.primarni_kljuc) && can){ 
             pomSet.addAll(rs.atributi);
             pomTables.add(rs);
         }
        pomTables.addAll(getQTables(P,rs, pomSet));
        if(pomSet.containsAll(attributes))break;
        }
        return pomTables;
    }
    public Set getAttributes(){
        Set pom=new HashSet();
        for(int i=0;i<cmp.itemGroup.length; i++) {
           for(int j=0;j<cmp.itemGroup[i].items.length; j++) {
           if(cmp.itemGroup[i].items[j].elem.getClass().toString().equals("class ui.Application$AttType")){
               pom.add(""+((Application.AttType)cmp.itemGroup[i].items[j].elem).att_id);
           }
           else{
               Application.ItemGroup ig=(Application.ItemGroup)cmp.itemGroup[i].items[j].elem;
               for(int m=0;m<ig.items.length; m++) {
                   pom.add(""+((Application.AttType)ig.items[m].elem).att_id);
               }
           }
           }     
        }
        return pom;
    }
    public String[] getRelationSchemeNames(Set rel){
        String[] pom=new String[rel.size()];
        Iterator it=rel.iterator();
        int i=0;
        while(it.hasNext()){
         RelationScheme rs=(RelationScheme)it.next();
         pom[i]=rs.name;
         i++;
        }
        return pom;
    }
    public String getKeyQuery(RelationScheme rs, String[][] keyvals, Set uT){
        int k=0;
        for(int i=0;i<keyvals.length;i++){
            //if(keyvals[i][1]==null)continue;
            //if(keyvals[i][1].equals(""))continue;
           // if(keyvals[i][1].toString().equals("null"))continue;
            k++;
        }
        String[] pom=new String[k];
        int j=0;
        if(k>0)
        for(int i=0;i<keyvals.length;i++){
           //if(keyvals[i][1]==null)continue;
           //if(keyvals[i][1].equals(""))continue;
           //if(keyvals[i][1].toString().equals("null"))continue;
            String att_name=getAttributeName(keyvals[i][0]);
            String rs_name=rs.name;
            if(!rs.atributi.contains(keyvals[i][0])){
                Iterator it=uT.iterator();
                while(it.hasNext()){
                    RelationScheme rss=(RelationScheme)it.next();
                    if(rss.atributi.contains(keyvals[i][0]))rs_name=rss.name;
                }
            }
            if(keyvals[i][1].toString().equals("null")|| keyvals[i][1].toString().equals("NULL")||keyvals[i][1].equals("")||keyvals[i][1]==null)
                pom[j]=rs_name+"."+att_name +" is NULL";
            else //if(isInteger(keyvals[i][1]) || isDecimal(keyvals[i][1]) || isBoolean(keyvals[i][1]))
                pom[j]=rs_name+"."+att_name +"="+ keyvals[i][1]+"";
        //    else
         //       pom[i]=rs_name+"."+att_name +"='"+ keyvals[i][1]+"'";
            j++;
        }
        return implode(pom," AND ");
    }
    public String getLKeyQuery(RelationScheme rs, String[][] keyvals, Set uT){
        int k=0;
        for(int i=0;i<keyvals.length;i++){
            //if(keyvals[i][1]==null)continue;
            //if(keyvals[i][1].equals(""))continue;
           // if(keyvals[i][1].toString().equals("null"))continue;
            k++;
        }
        String[] pom=new String[k];
        int j=0;
        if(k>0)
        for(int i=0;i<keyvals.length;i++){
           //if(keyvals[i][1]==null)continue;
           //if(keyvals[i][1].equals(""))continue;
           //if(keyvals[i][1].toString().equals("null"))continue;
            String att_name=getAttributeName(keyvals[i][0]);
            String rs_name=rs.name;
            if(!rs.atributi.contains(keyvals[i][0])){
                Iterator it=uT.iterator();
                while(it.hasNext()){
                    RelationScheme rss=(RelationScheme)it.next();
                    if(rss.atributi.contains(keyvals[i][0]))rs_name=rss.name;
                }
            }
            if(!(isInteger(keyvals[i][1]) && isDecimal(keyvals[i][1]))&&(keyvals[i][1].length()<3||keyvals[i][1].substring(0,2).equals("\"%")))
                keyvals[i][1]="'%"+keyvals[i][1]+"%'";
            if(keyvals[i][1].toString().equals("null")|| keyvals[i][1].toString().equals("NULL")||keyvals[i][1].equals("")||keyvals[i][1]==null)
                pom[j]="";
            else if(isInteger(keyvals[i][1]) || isDecimal(keyvals[i][1]))
                pom[j]=rs_name+"."+att_name +" = "+ keyvals[i][1]+"";
            else
                pom[j]=rs_name+"."+att_name +" LIKE "+ keyvals[i][1]+"";
        //    else
         //       pom[i]=rs_name+"."+att_name +"='"+ keyvals[i][1]+"'";
            j++;
        }
        return implode(pom," AND ");
    }
    public String getSearchQuery(RelationScheme rs, String[][] keyvals, Set uT){
        int k=0;
        for(int i=0;i<keyvals.length;i++){
             if(keyvals[i][1]==null)continue;
             if(keyvals[i][1].equals(""))continue;
             if(keyvals[i][1].toString().equals("null"))continue;
             if(keyvals[i][2].equals(""))continue;
            k++;
        }
        String[] pom=new String[k];
        int j=0;
        for(int i=0;i<keyvals.length;i++){
            if(keyvals[i][1]==null)continue;
            if(keyvals[i][1].equals(""))continue;
            if(keyvals[i][1].toString().equals("null"))continue;
            if(keyvals[i][2].equals(""))continue;
            String att_name=getAttributeName(keyvals[i][0]);
            String rs_name=rs.name;
            if(!rs.atributi.contains(keyvals[i][0])){
                Iterator it=uT.iterator();
                while(it.hasNext()){
                    RelationScheme rss=(RelationScheme)it.next();
                    if(rss.atributi.contains(keyvals[i][0]))rs_name=rss.name;
                }
            }
            //if(isInteger(keyvals[i][1]) || isDecimal(keyvals[i][1]) || isBoolean(keyvals[i][1]))
                //pom[j]=rs_name+"."+att_name +" "+ keyvals[i][2]+" "+ keyvals[i][1]+"";
            //else if(keyvals[i][2].equals("like"))
            //    pom[j]=rs_name+"."+att_name +" "+ keyvals[i][2]+" '%"+ keyvals[i][1]+"%'";    
           // else
                pom[j]=rs_name+"."+att_name +" "+keyvals[i][2]+" "+ keyvals[i][1]+"";
            j++;
        }
        return implode(pom," AND ");
    }
    public String getSelectQuery(String[] atts, String[][] keyVals, String[][] parameterKeyVals, int method, String[][] lovVals, int caller){
        Set pomSet=new HashSet();
        pomSet.add(table);
        pomSet.addAll(u[1]);
        keyQuery=getKeyQuery(table, keyVals, pomSet);
        String parameterKeyQuery=getKeyQuery(table, parameterKeyVals, pomSet);
        String lovQuery=getLKeyQuery(table, lovVals, pomSet);
        String[] att=getQueryAtts(getSelectAtts(atts));
        String pom="SELECT "+implode(att, ", ")+ " FROM "+implode(getRelationSchemeNames(pomSet), ", ")+ " WHERE 1=1"+addQuery(uQuery)+" "+addQuery(keyQuery)+" "+addQuery(parameterKeyQuery)+" "+addQuery(lovQuery);
        if((method==3 || method==1) && tf.id!=tf_entry)pom=pom+" AND 0=1";;
        return pom;
    } 
    public String getSelectCountQuery(String[] atts,String[][] keyVals, String[][] parameterKeyVals, int method, String[][] lovVals, int caller){
        Set pomSet=new HashSet();
        pomSet.add(table);
        pomSet.addAll(u[1]);
        keyQuery=getKeyQuery(table, keyVals,pomSet);
        String parameterKeyQuery=getKeyQuery(table, parameterKeyVals, pomSet);
        String lovQuery=getLKeyQuery(table, lovVals, pomSet);
        String[] att=getQueryAtts(atts);
        String pom="SELECT count(*) FROM "+implode(getRelationSchemeNames(pomSet), ", ")+ " WHERE 1=1"+addQuery(uQuery)+" "+addQuery(keyQuery)+" "+addQuery(parameterKeyQuery)+" "+addQuery(lovQuery);
        if((method==3 || method==1) && tf.id!=tf_entry)pom=pom+" AND 0=1";;
        return pom;
    } 
    public String getSearchSelectQuery(String[] atts, String[] ascs, String[][] searchVals, String[][] lovVals, int method, int caller){
        Set pomSet=new HashSet();
        pomSet.add(table);
        pomSet.addAll(u[1]);
        keyQuery=getSearchQuery(table, searchVals, pomSet);
        String lovQuery=getLKeyQuery(table, lovVals, pomSet);
        String[] att=getQueryAtts(getSelectAtts(atts));
        String pom="SELECT "+implode(att, ", ")+ " FROM "+implode(getRelationSchemeNames(pomSet), ", ")+ " WHERE 1=1"+addQuery(uQuery)+" "+addQuery(keyQuery)+" "+addQuery(lovQuery)+ addOrderQuery(atts, ascs);
        if((method==3 || method==1) && tf.id!=tf_entry)pom=pom+" AND 0=1";;
        return pom;
    } 
    public String addOrderQuery(String[] atts, String[] ascs){
        String[] pom=new String[atts.length];
        String pomQuery="";
        for(int i=0; i<atts.length; i++){
            if(!ascs[i].equals(""))
                pom[i]=atts[i]+" "+ascs[i]+", ";
            else
                pom[i]="";
        }
        if(!implode(pom,"").equals(""))pomQuery=" ORDER BY "+implode(pom,"").substring(0,implode(pom,"").length()-2);
        return pomQuery;
    } 
    public String getSearchSelectCountQuery(String[][] searchVals, String[][] lovVals, int method, int caller){
        Set pomSet=new HashSet();
        pomSet.add(table);
        pomSet.addAll(u[1]);
        String lovQuery=getLKeyQuery(table, lovVals, pomSet);
        keyQuery=getSearchQuery(table, searchVals,pomSet);
        String pom="SELECT count(*) FROM "+implode(getRelationSchemeNames(pomSet), ", ")+ " WHERE 1=1"+addQuery(uQuery)+" "+addQuery(keyQuery)+" "+addQuery(lovQuery);
        if((method==3 || method==1) && tf.id!=tf_entry)pom=pom+" AND 0=1";;
        return pom;
    } 
    public String addQuery(String s){
        if(s=="")
            return "";
        else
            return " AND "+s;
    }
    public String implode(String[] val, String glue){
        String pom="";
        for(int i=0;i<val.length;i++){
            if(!val[i].equals(""))
                pom=pom+val[i] +glue;
        }
        if(val.length>0)
            return pom.substring(0,pom.length()-glue.length());
        return "";
    }
    public String implode(Set val, String glue){
        String pom="";
        Iterator it=val.iterator();
        while(it.hasNext()){
            String at=it.next().toString();
            if(!at.equals(""))
                pom=pom+at +glue;
        }
        if(val.size()>0)
            return pom.substring(0,pom.length()-glue.length());
        return "";
    }
    public String getRespondingQuery(RelationScheme table, int att, String val, RelationScheme rs, String[][] keyVals){
        String que="";
        try{
         JDBCQuery query=new JDBCQuery(connection);
         ResultSet r;
         Iterator it=rs.primarni_kljuc.iterator();
         Set pom=new HashSet();
         while(it.hasNext()){
             String at=it.next().toString();
             if(rs.atributi.contains(at)){
                if(!updateAtt.contains(at))
                {
                    pom.add(at);
                    updateAtt.add(at);
                }
             }
         }
         String[] pomatt=new String[pom.size()];
         String pomquery="";
         int i=0;
         it=pom.iterator();
         while(it.hasNext()){
             String at=it.next().toString();
             pomatt[i]=at;
             i++;
             if(!pomquery.equals(""))
                pomquery=pomquery+", "+getAttributeName(at)+"";
             else
                pomquery=getAttributeName(at)+"";
         } 
         String pomKey="";
         for(int j=0;j<keyVals.length;j++){
             if(!keyVals[j][0].equals(""+att) && rs.primarni_kljuc.contains(keyVals[j][0]))
                 pomKey=pomKey+" AND "+getAttributeName(keyVals[j][0]) +"=" + keyVals[j][1] + "";
         }
         if(!pomquery.equals(""))
         {
             r=query.select("select "+ pomquery +" from "+rs.name +" where "+getAttributeName(""+att) +"=" + val + " "+pomKey);
             if(r.next()){
                for(i=0;i<pomatt.length;i++){
                    if(isAttributeInteger(pomatt[i]))
                        que=que+ getAttributeName(pomatt[i])+"="+ r.getString(i+1) +", ";
                    else
                        que=que+ getAttributeName(pomatt[i])+"='"+ r.getString(i+1) +"', ";
                }
             }
         }
         query.Close();  
         if(que.length()>2)
            return que.substring(0,que.length()-2);
         return que;
         }
         catch(Exception e)
         {
         e.printStackTrace();
         return que;
         }
    }
    public String checkQueryAtt(RelationScheme rs, String att, String val, String[][] keyVals){
        int a=getAttributeId(att);
        if(table.atributi.contains(""+ a)){
            if(!updateAtt.contains(""+a))
            {
                updateAtt.add(""+ a);
                //if(isAttributeInteger(""+a))
                    return att+"="+val+"";
                //return att+"='"+val+"'";
            }
        }
        else{
            Iterator it=uTables.iterator();
            while(it.hasNext()){
                RelationScheme rss=(RelationScheme)it.next();
                Iterator itt=rss.kljuc.iterator();
                while(itt.hasNext()){
                    Set key=(Set)itt.next();
                    if(key.contains(""+a)){
                        return getRespondingQuery(table, a, val, rss, keyVals);
                    }
                }
            }
            it=uTables.iterator();
            while(it.hasNext()){
                RelationScheme rss=(RelationScheme)it.next();
                if(rss.atributi.contains(""+a)){
                    return getRespondingQuery(table, a, val, rss, keyVals);
                }
            }
        }
        return "";
    }
    public Set checkQueryInsertAtt(RelationScheme rs, String att, String val, String[][] keyVals){
        Set set=new HashSet();
        Set pomSet=new HashSet();
        int a=getAttributeId(att);
        if(rs.atributi.contains(""+ a)){
            set.add(att);
            return set;
        }
        else{
            Iterator it=uTables.iterator();
            while(it.hasNext()){
                RelationScheme rss=syn.getRelationScheme(((RelationScheme)it.next()).id);
                Iterator itt=rss.kljuc.iterator();
                while(itt.hasNext()){
                    Set key=(Set)itt.next();
                    if(key.contains(""+a)){
                        pomSet=syn.Presjek(rss.primarni_kljuc,rs.atributi);
                    }
                }
            }
        }
        Iterator it=pomSet.iterator();
        while(it.hasNext()){
            String at=(String)it.next();
            set.add(getAttributeName(at));
        }
        return set;
    }
    public Set checkQueryInsertAttVal(RelationScheme rs, String att, String val, String[][] keyVals){
        Set set=new HashSet();
        Set pomSet=new HashSet();
        int a=getAttributeId(att);
        if(rs.atributi.contains(""+ a)){
            String[] pom=new String[2];
            pom[0]=att;
            pom[1]=val;
            invalidAtt.remove(getAttributeTitle(""+getAttributeId(att))); 
            set.add(pom);
            return set;
        }
        else{
            Iterator it=uTables.iterator();
            boolean can=false;
            while(it.hasNext()){
                RelationScheme rss=syn.getRelationScheme(((RelationScheme)it.next()).id);
                Iterator itt=rss.kljuc.iterator();
                while(itt.hasNext()){
                    Set key=(Set)itt.next();
                    if(key.contains(""+a)){
                        pomSet=syn.Presjek(rss.primarni_kljuc,rs.atributi);
                        set=getRealValue(pomSet, rss, att, val);
                        can=true;
                        if(!set.isEmpty()&&invalidAtt.contains(getAttributeTitle(""+getAttributeId(att))))invalidAtt.remove(getAttributeTitle(""+getAttributeId(att)));             
                    }
                }
            }
            if(!can){
                it=uTables.iterator();
                while(it.hasNext()){
                    RelationScheme rss=syn.getRelationScheme(((RelationScheme)it.next()).id);
                    if(rss.atributi.contains(""+a)){
                        pomSet=syn.Presjek(rss.primarni_kljuc,rs.atributi);
                        set=getRealValue(pomSet, rss, att, val);
                        if(!set.isEmpty()&&invalidAtt.contains(getAttributeTitle(""+getAttributeId(att))))invalidAtt.remove(getAttributeTitle(""+getAttributeId(att)));             
                    }
                }
            }
        }
        return set;
    }
    public Set getRealValue(Set atts, RelationScheme rs, String att, String val){
        Set que=new HashSet();
        try{
         JDBCQuery query=new JDBCQuery(connection);
         ResultSet r;
         String[] pomatt=new String[atts.size()];
         String pomquery="";
         int i=0;
         Iterator it=atts.iterator();
         while(it.hasNext()){
             String at=it.next().toString();
             pomatt[i]=at;
             i++;
             if(!pomquery.equals(""))
                pomquery=pomquery+", "+getAttributeName(at)+"";
             else
                pomquery=getAttributeName(at)+"";
         } 
         if(pomquery.length()>0){
             r=query.select("select "+ pomquery +" from "+rs.name +" where "+att +"=" + val );
             if(r.next()){
                for(i=0;i<pomatt.length;i++){
                    String[] pom=new String[2];
                    if(isAttributeInteger(pomatt[i])){
                        pom[0]=getAttributeName(pomatt[i]);
                        pom[1]=r.getString(i+1);
                    }
                    else{
                        pom[0]=getAttributeName(pomatt[i]);
                        pom[1]="'"+r.getString(i+1)+"'";
                    }
                     que.add(pom);
                }
             }
             query.Close();  
         }
         return que;
         }
         catch(Exception e)
         {
         e.printStackTrace();
         return que;
         }
    }
    public String[] getUpdateQuery(String[] atts, String[] values, String[][] keyVals){
        String[] pom=new String[aTables.size()];
        for(int k=0;k<atts.length ;k++)
            invalidAtt.add(getAttributeTitle(""+getAttributeId(atts[k])));
        RelationScheme[] pomRS=new RelationScheme[aTables.size()];
        pomRS[0]=table;
        Set aaTables=new HashSet();
        Set pomTables=new HashSet();
        aaTables.add(table);
        Set p=syn.Razlika(aTables,aaTables);
        Iterator it=p.iterator();
        for(int i=1;i<pomRS.length && it.hasNext() ;i++){
            pomRS[i]=(RelationScheme)it.next();
        }
        int j=0;
        Set pomS=new HashSet();
        for(int k=0;k<pomRS.length ;k++){
            RelationScheme t=pomRS[k];
            String[] valatt=new String[atts.length];
            updateAtt=new HashSet();
            Set updateAttPom=new HashSet();
            /*for(int i=0;i<atts.length;i++){
                 int attid=getAttributeId(atts[i]);
                 if(!t.atributi.contains(""+attid))continue;
                 String att=getQueryAtt(atts[i]);
                 valatt[i]=checkQueryAtt(t, atts[i], values[i], keyVals);
                 //if(t.atributi.contains(""+getAttributeId(atts[i])))
                 if(valatt[i].equals(""))
                 JOptionPane.showMessageDialog(null,"Invalid value for attribute "+ getAttributeTitle(""+attid) +"!",  "Error", JOptionPane.INFORMATION_MESSAGE);  
                 updateAttPom.add(valatt[i]);
            }*/
             Set attSetV=new HashSet();
            alreadyChecked=new HashSet();
             for(int i=0; i<atts.length;i++){
                 Set check=checkQueryInsertAttVal(t,atts[i], values[i], keyVals);
                 attSetV.addAll(syn.Razlika(check,attSetV));
             }
             Iterator it1=attSetV.iterator();
             while(it1.hasNext()){
                 String[] pom1=(String[])it1.next();
                 
                 if(!alreadyChecked.contains(pom1[0]))
                 {
                    for(int m=0;m<atts.length;m++)
                        if(atts[m].equals(pom1[0]))pom1[1]=values[m];
                    updateAttPom.add(pom1[0]+"="+pom1[1]);
                    alreadyChecked.add(pom1[0]);
                 }
             }
            String keyQuery="";
            for(int i=0; i<keyVals.length;i++){
                if(t.atributi.contains(keyVals[i][0])) 
                        keyQuery=keyQuery+" AND "+getAttributeName(keyVals[i][0])+"="+keyVals[i][1]+" ";
            }
            pom[j]="UPDATE "+ t.name +" SET "+implode(updateAttPom, ", ")+ " WHERE 1=1 "+keyQuery;
            pomTables.add(t.id); 
            j++;
        }    
        Set pomSet=new HashSet();
        Set rs_set=syn.getRelationSchemes();
        Iterator it1=rs_set.iterator();
        while(it1.hasNext()){
            RelationScheme t=(RelationScheme)it1.next();
            if(pomTables.contains(t.id))continue;
            for(int i=0; i<keyVals.length;i++){
                String keyQuery="";
                Set updateAttPom=new HashSet();
                if(t.atributi.contains(keyVals[i][0])){
                        keyQuery=keyQuery+" AND "+getAttributeName(keyVals[i][0])+"="+keyVals[i][1]+" ";
                        for(int l=0;l<atts.length;l++){
                             if(keyVals[i][0].equals(""+getAttributeId(atts[l])))
                             {
                                /*String valatt=checkQueryAtt(t, atts[l], values[l], keyVals);
                                if(!valatt.equals(""))
                                    updateAttPom.add(valatt);*/
                                 updateAttPom.add(atts[l]+"="+values[l]+"");   
                             }
                        }
                }
                if(updateAttPom.size()>0){
                    pomSet.add("UPDATE "+ t.name +" SET "+implode(updateAttPom, ", ")+ " WHERE 1=1 "+keyQuery);
                }
            }
        }
        if(!invalidAtt.isEmpty())
        {   
            JOptionPane.showMessageDialog(null,"Invalid value for attribute: "+ implode(invalidAtt,", ") +"!",  "Error", JOptionPane.INFORMATION_MESSAGE);  
            invalidAtt=new HashSet();
            return new String[0];
        }
        String[] arr=new String[pomSet.size()+pom.length];
        int k=0;
        for(k=0;k<pom.length ;k++)arr[k]=pom[k];
        Iterator it2=pomSet.iterator();
        for(int i=0;i<pomSet.size();i++)arr[k+i]=it2.next().toString();
        return arr;
    }
    public String[] getDeleteQuery(String[][] keyVals){
        String[] pom=new String[aTables.size()];
        RelationScheme[] pomRS=new RelationScheme[aTables.size()];
        pomRS[0]=table;
        Set aaTables=new HashSet();
        Set pomTables=new HashSet();
        aaTables.add(table);
        Set p=syn.Razlika(aTables,aaTables);
        Iterator it=p.iterator();
        for(int i=1;i<pomRS.length && it.hasNext() ;i++){
            pomRS[i]=(RelationScheme)it.next();
        }
        int j=0;
        for(int k=0;k<pomRS.length ;k++){
            RelationScheme t=pomRS[k];
            String keyQuery="";
            /*for(int i=0; i<keyVals.length;i++){
                if(t.atributi.contains(keyVals[i][0]))
                        keyQuery=keyQuery+" AND "+getAttributeName(keyVals[i][0])+"="+keyVals[i][1]+" ";
            }*/
             Set attSetV=new HashSet();
             for(int i=0; i<keyVals.length;i++){
                 Set check=checkQueryInsertAttVal(t,getAttributeName(keyVals[i][0]), keyVals[i][1], keyVals);
                 attSetV.addAll(syn.Razlika(check,attSetV));
             }
             Iterator it1=attSetV.iterator();
             while(it1.hasNext()){
                 String[] pom1=(String[])it1.next();
                 keyQuery=keyQuery+" AND "+pom1[0]+"="+pom1[1];
             }
            pom[j]="DELETE FROM "+ t.name +" WHERE 1=1 "+keyQuery;
            j++; 
            pomTables.add(t.id);
        }
        Set pomSet=new HashSet();
        Set rs_set=syn.getRelationSchemes();
        Iterator it1=rs_set.iterator();
        Set kSet=new HashSet();
        for(int i=0; i<keyVals.length;i++){
            kSet.add(keyVals[i][0]);
        }
        while(it1.hasNext()){
            RelationScheme t=(RelationScheme)it1.next();
            if(pomTables.contains(t.id))continue;
            if(!t.atributi.containsAll(kSet))continue;
            String keyQuery="";
            String updateQuery="";
            for(int i=0; i<keyVals.length;i++){
                if(t.atributi.contains(keyVals[i][0])){
                        keyQuery=keyQuery+" AND "+getAttributeName(keyVals[i][0])+"="+keyVals[i][1]+" ";
                        updateQuery=updateQuery+getAttributeName(keyVals[i][0])+"=null, ";
                }
            }
            if(updateQuery.length()>2){
                pomSet.add("UPDATE "+ t.name +" SET "+updateQuery.substring(0,updateQuery.length()-2)+" WHERE 1=1 "+ keyQuery);
            }
        }
        String[] arr=new String[pomSet.size()+pom.length];
        int k=0;
        for(k=0;k<pom.length ;k++)arr[k]=pom[k];
        Iterator it2=pomSet.iterator();
        for(int i=0;i<pomSet.size();i++)arr[k+i]=it2.next().toString();
        return arr;
    }
    public String[] getDeleteAllQuery(String[][] parentKeyVals,String[][] keyVals){
        String[] pom=new String[aTables.size()];
        RelationScheme[] pomRS=new RelationScheme[aTables.size()];
        pomRS[0]=table;
        Set aaTables=new HashSet();
        Set pomTables=new HashSet();
        aaTables.add(table);
        Set p=syn.Razlika(aTables,aaTables);
        Iterator it=p.iterator();
        for(int i=1;i<pomRS.length && it.hasNext() ;i++){
            pomRS[i]=(RelationScheme)it.next();
        }
        int j=0;
        String keyQue="";
        for(int i=0; i<parentKeyVals.length;i++){
            if(table.atributi.contains(parentKeyVals[i][0])) 
                    keyQue=keyQue+" AND "+getAttributeName(parentKeyVals[i][0])+"="+parentKeyVals[i][1]+" ";
        }
        for(int k=0;k<pomRS.length ;k++){
        
            RelationScheme t=pomRS[k];
            pom[j]="DELETE FROM "+ t.name +" WHERE 1=1"+keyQue;
            j++;
            pomTables.add(t.id);
        }
        Set pomSet=new HashSet();
        Set rs_set=syn.getRelationSchemes();
        Set kSet=new HashSet();
        for(int i=0; i<keyVals.length;i++){
            kSet.add(keyVals[i][0]);
        }
        Iterator it1=rs_set.iterator();
        while(it1.hasNext()){
            RelationScheme t=(RelationScheme)it1.next();
            if(pomTables.contains(t.id))continue;
            if(!t.atributi.containsAll(kSet))continue;
            String updateQuery="";
            for(int i=0; i<keyVals.length;i++){
                if(t.atributi.contains(keyVals[i][0])){
                        updateQuery=updateQuery+getAttributeName(keyVals[i][0])+"= null, ";
                }
            }
            if(updateQuery.length()>2){
                pomSet.add("UPDATE "+ t.name +" SET "+updateQuery.substring(0,updateQuery.length()-2)+" WHERE 1=1");
            }
        }
        String[] arr=new String[pomSet.size()+pom.length];
        int k=0;
        for(k=0;k<pom.length ;k++)arr[k]=pom[k];
        Iterator it2=pomSet.iterator();
        for(int i=0;i<pomSet.size() ;i++)arr[k+i]=it2.next().toString();
        return arr;
    }
    public String getInsertQuery(String[] atts, String[] values, String[][] keyVals){
        String keyName="";
        String keyValue="";
        for(int k=0;k<atts.length ;k++)
            invalidAtt.add(getAttributeTitle(""+getAttributeId(atts[k])));
        for(int i=0; i<keyVals.length;i++){
            if(table.atributi.contains(keyVals[i][0])) {
                keyName=keyName+","+getAttributeName(keyVals[i][0]);
                keyValue=keyValue+",'"+keyVals[i][1]+"'";
            }
        }
        Set attSet=new HashSet();
        for(int i=0; i<atts.length;i++){
            Set check=checkQueryInsertAtt(table,atts[i], values[i], keyVals);
            attSet.addAll(syn.Razlika(check,attSet));
        }
        String[] attsq=new String[attSet.size()];
        Iterator it1=attSet.iterator();
        int j=0;
        while(it1.hasNext()){
            attsq[j]=(String)it1.next();
            j++;
        }
        Set attSetV=new HashSet();
        for(int i=0; i<atts.length;i++){
            Set check=checkQueryInsertAttVal(table,atts[i], values[i], keyVals);
            attSetV.addAll(syn.Razlika(check,attSetV));
        }
        String[] attsqv=new String[attsq.length];
        it1=attSetV.iterator();
        while(it1.hasNext()){
            String[] pom=(String[])it1.next();
            for(int i=0;i<attsq.length;i++)
            {
                if(attsq[i].equals(pom[0]))
                    attsqv[i]=pom[1];
            }
        }
        if(!invalidAtt.isEmpty())
        {   
            JOptionPane.showMessageDialog(null,"Invalid value for attribute: "+ implode(invalidAtt,", ") +"!",  "Error", JOptionPane.INFORMATION_MESSAGE);  
            invalidAtt=new HashSet();
            return new String();
        }
        String pom="INSERT INTO "+table.name +" ("+implode(attsq, ", ")+ keyName+ ") VALUES ("+implode(attsqv, ", ")+""+ keyValue+")";
        //System.out.println(pom);
        return pom;
    } 
    public Set getSuperiorsKey(int cmpId)
    {
        Set keys=new HashSet();
        for(int i=0;i<tf.compType.length;i++){
           if(tf.compType[i].id==cmpId){
               Set keySet=getKeySet(tf.compType[i]);
               Set key=new HashSet();
               if(tf.compType[i].superord!=-1)
                key=getSuperiorsKey(tf.compType[i].superord);
               keys=getCombination(keySet, key);
            }
        }
        return keys;
    }
    public Set getCombination(Set a, Set b){
        if(b.isEmpty())return a;
        Set pom=new HashSet();
        Iterator it=a.iterator();
        while(it.hasNext()){
         Set aa=(HashSet)it.next();
         Iterator it1=b.iterator();
         while(it1.hasNext()){
             Set bb=(HashSet)it1.next();
             Set pom1=new HashSet();
             pom1.addAll(aa);
             pom1.addAll(bb);
             pom.add(pom1);
         }
        }
        return pom;
    }
    public Application.CompType getComp(int cmpId){
           for(int i=0;i<tf.compType.length;i++){
            if(tf.compType[i].id==cmpId)return tf.compType[i];
           }
           return null;
    }
    public Set getKeySet(Application.CompType component)
    {
        Set pom=new HashSet();
        for(int j=0;j<component.keys.length;j++){  
            Iterator it=component.keys[j].iterator();
            Set pom1=new HashSet();
            while(it.hasNext()){
             Application.AttType att=(Application.AttType)it.next();
             pom1.add(""+att.att_id);
            }
            pom.add(pom1);
        }
        return pom;
    }
    public Set getInferiors(Set rss, RelationScheme schema)
    {
       Set inferiors=syn.getInferiors(schema.id);
       Iterator it=rss.iterator();
       Set pom=new HashSet();
       while(it.hasNext()){
           RelationScheme rs=(RelationScheme)it.next();
           if(inferiors.contains(""+rs.id) && rs.id!=schema.id)
            pom.add(rs);
       }
       return pom;
    }
    public Set getSuperiors(Set rss, RelationScheme schema)
    {
       Set superiors=syn.getSuperiors(schema.id);
       Iterator it=rss.iterator();
       Set pom=new HashSet();
       while(it.hasNext()){
           RelationScheme rs=(RelationScheme)it.next();
           if(superiors.contains(""+rs.id) && rs.id!=schema.id)
            pom.add(rs);
       }
       return pom;
    }
    public Set getMinNodes(Set rss)
     {
       try{
        JDBCQuery query=new JDBCQuery(syn.con);
        ResultSet rs;
        Set pom=new HashSet();
        Set pomId=new HashSet();
        Iterator it=rss.iterator();
        while(it.hasNext()){
            RelationScheme sch=(RelationScheme)it.next(); 
            pomId.add(""+sch.id); 
        }
        it=rss.iterator();
        while(it.hasNext()){
            RelationScheme sch=(RelationScheme)it.next();
            rs=query.select("select RS_inferior from IISC_GRAPH_CLOSURE where RS_superior=" + sch.id + " and GC_edge_type=0 and  PR_id=" + syn.pr + " and  AS_id=" + syn.as);
            if(!rs.next())
            { 
                pom.add(sch); 
            }
            else {
                if(!pomId.contains(""+rs.getInt(1)))
                    pom.add(sch);
                while(rs.next()){
                    if(!pomId.contains(""+rs.getInt(1)))
                        pom.add(sch);
                }
            }
            query.Close();
        }       
        return pom;
        }
        catch(Exception e)
        {
        e.printStackTrace();
        return null;
        }
     }
    public boolean isInteger( String input ){   
        try   {      
            Integer.parseInt( input );      
            return true;   
        }   
        catch( Exception e)   
        {      return false;   
        }
    }
    public boolean isDecimal( String input ){   
        try   {      
            Double.parseDouble( input );      
            return true;   
        }   
        catch( Exception e)   
        {      return false;   
        }
    }
    public boolean isBoolean( String input ){   
        try   {      
            Boolean.parseBoolean( input );      
            return true;   
        }   
        catch( Exception e)   
        {      return false;   
        }
    }
}
