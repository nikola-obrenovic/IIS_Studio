package ui;

import iisc.FunDep;
import iisc.JDBCQuery;
import iisc.Synthesys;
import iisc.RelationScheme;
import iisc.RelationScheme;

import java.sql.Connection;

import java.sql.ResultSet;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SubSchemaGen {
    Set SubSchema=new HashSet(); 
    Synthesys syn;
    Connection conn;
    public FormType form;
    public Application.FormType tf;
    Object[][] Omega;
    Object[][] Teta;
    int[] ZAST;
    Set rs_set;
    Object[] funDep;
    Set T;
    Set T_tilde;
    Set W;
    Set Wext;
    Set U;
    Set KpF;
    Set Sj=new HashSet();
    Set JF=new HashSet();;
    Set Tr;
    Set Tr_tilde;
    public int apply_id;
    public Set Pa;
    public Set Pu;
    
public SubSchemaGen(int pr, int as, Application.FormType _tf, Connection _conn) {
    syn=new Synthesys();
    syn.pr=pr;
    syn.as=as;
    syn.con=_conn;
    conn=_conn;
    tf=_tf;
    form=LoadForm(tf);
    T=form.getT(form.root);
    System.out.println(tf.name);
    System.out.println("T="+T.toString());
    JF.addAll(T);
    Sj=getSJ();
    T_tilde=form.getTtilde(form.root, new HashSet());
    rs_set=syn.getRelationSchemes();
    funDep=syn.getFunDepScheme(rs_set);
    W=form.getAttributes(form.root);
    Wext=form.getExtAttributes(form.root);
    U=getU();
    KpF=form.getPrimarnaObiljezja(form.root);
    apply_id=analiza_primjenljivosti();
    System.out.println("Z0"+ apply_id);
    Pu=new HashSet();
    Pa=new HashSet();
    if(apply_id==1)
        Pu=generisanje_P427(T,T_tilde);
    else if(apply_id==2)
        Pu=generisanje_P425(T,T_tilde);  
    else if(apply_id==5)
        Pu=generisanje_P421(T,T_tilde);
    else if(apply_id!=0) {
        Set[] ret=generisanje_P_a();
        Pu=ret[0];
        Pa=ret[1];
    }
    if(apply_id!=3)Pa=new HashSet();
    System.out.println("Skup Pu ");
    printS(Pu);
    System.out.println("Skup Pa ");
    printS(Pa);
    tf.Pa.addAll(Pa);
    tf.Pu.addAll(Pu);
}
public Set[] generisanje_P_a()
{
    Set[] ret=new Set[2];
    Set P_s_min=minimalni_cvorovi_podseme(T);
    System.out.println("Minimalni skup");
    //printS(P_s_min);
    Set Rc=relevantni_cvorovi(P_s_min, form, T_tilde);        
    System.out.println("Relevantni cvorovi");
    printS(Rc);
    Set P_s_pod=generisanje_P_s_pod(Rc, P_s_min);
    System.out.println("Podredjeni cvorovi");
    //printS(P_s_pod);
    Set Psu=syn.Unija(P_s_min,P_s_pod);
    Set[] setsForUpdate=skupovi_za_azuriranje(Psu);
    Set Psref=formiranje_P_s_ref(setsForUpdate[0],setsForUpdate[1],setsForUpdate[2]);
    Set Psa=syn.Unija(Psref,Psu);
    Iterator it=Psa.iterator();
    Set Kp=new HashSet();
    while(it.hasNext()){
        RelationScheme rs=(RelationScheme)it.next();
        Kp.addAll(rs.primarni_kljuc);
    }
    Set Pu=new HashSet();
    it=Psu.iterator();
    while(it.hasNext()){
        RelationScheme Ri=(RelationScheme)it.next();
        Set[] rel=new Set[2];
        rel[0]=syn.Presjek(Ri.atributi,syn.Unija(Kp,W)); 
        rel[1]=new HashSet();
        Iterator itt=Ri.kljuc.iterator();
        while(itt.hasNext()){
            Set K=(Set)itt.next();
            if(W.containsAll(K))
                rel[1].add(K);
        }
        rel[1].add(Ri.primarni_kljuc);
        RelationScheme rs=new RelationScheme();
        rs.id=Ri.id;
        rs.atributi=rel[0];
        rs.kljuc.addAll(rel[1]);
        rs.primarni_kljuc.addAll(Ri.primarni_kljuc);
        rs.name=Ri.name;
        Pu.add(rs);
    }
    Set Pa=new HashSet();
    Pa.addAll(Pu);
    it=(syn.Razlika(Psa,Psu)).iterator();
    while(it.hasNext()){
        RelationScheme Ri=(RelationScheme)it.next();
        Set[] rel=new HashSet[2];
        rel[1].addAll(Ri.primarni_kljuc);
        rel[0]=syn.Presjek(Ri.atributi,syn.Unija(W,Ri.primarni_kljuc));
        RelationScheme rs=new RelationScheme();
        rs.atributi=rel[0];
        rs.kljuc.add(rel[1]);
        rs.primarni_kljuc.addAll(rel[1]);
        rs.name=Ri.name;
        rs.id=Ri.id;
        Pa.add(rs);
    }
    ret[0]=Pu;
    ret[1]=Pa; 
    return ret;
}
public FormType LoadForm(Application.FormType f){
        FormType tf=new FormType(f,conn);
        return tf;
}
public Set getMinNodes(Set rss)
 {
   try{
    JDBCQuery query=new JDBCQuery(conn);
    JDBCQuery query1=new JDBCQuery(conn);
    ResultSet rs;
    Set pom=new HashSet();
    Iterator it=rss.iterator();
    while(it.hasNext()){
        RelationScheme sch=(RelationScheme)it.next();
        rs=query.select("select *  from IISC_GRAPH_CLOSURE where RS_inferior=" + sch.id + " and GC_edge_type=0 and  PR_id=" + syn.pr + " and  AS_id=" + syn.as);
        if(!rs.next())
        { 
            pom.add(sch); 
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
public Set getSJ()
 {
   try{
    JDBCQuery query=new JDBCQuery(conn),query1=new JDBCQuery(conn),query2=new JDBCQuery(conn);
    ResultSet rs, rs1, rs2 ;
    Set pom= new HashSet();
    rs=query.select("select JD_id  from IISC_JOIN_DEPENDENCY where PR_id=" + syn.pr + " and  AS_id=" + syn.as);
    while(rs.next())
    {
        Set pom1= new HashSet();
        rs1=query1.select("select RS_id  from IISC_JOIN_DEP_RS where PR_id=" + syn.pr + " and  AS_id=" + syn.as+ " and JD_id="+rs.getInt(1));
        while(rs1.next())
        { 
            Set pom2= new HashSet();
            rs2=query2.select("select IISC_ATTRIBUTE.Att_id from IISC_RS_KEY,IISC_RSK_ATT,IISC_ATTRIBUTE where IISC_RS_KEY.RS_primary_key=1 and IISC_RS_KEY.RS_rbrk=IISC_RSK_ATT.RS_rbrk and IISC_RS_KEY.RS_id=IISC_RSK_ATT.RS_id and IISC_RSK_ATT.Att_id=IISC_ATTRIBUTE.Att_id and IISC_RS_KEY.RS_id="+rs1.getInt(1)+"  and IISC_RS_KEY.PR_id="+ syn.pr+" and IISC_RS_KEY.AS_id="+syn.as );
            while(rs2.next()){
               pom2.add(rs2.getString(1));
            }
            query2.Close();
            pom1.add(pom2); 
        }
        query1.Close();
        pom.add(pom1);
    }       
    return pom;
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
     return new HashSet();
 }
public Set minimalni_cvorovi_podseme(Set T_set) {
        int k=0;
        Set min_set=new HashSet();
        Set min_graph=new HashSet();
        min_graph=getMinNodes(rs_set); 
        Iterator itn=T_set.iterator(); 
        while(itn.hasNext()) {
            Set X=(HashSet)itn.next();
            Iterator it=min_graph.iterator();
            while(it.hasNext()) {
                boolean m_ind=false;
                RelationScheme schema=(RelationScheme)it.next();
                Iterator its=schema.kljuc.iterator();
                while(its.hasNext() && m_ind==false) {
                    Set kljuc=(HashSet)its.next();
                    if(syn.equalSets(kljuc,X)) {
                        m_ind=true;
                        min_set.add(schema);
                    }
                }
                boolean g_ind=false;
                if(!m_ind && syn.zatvaranje(schema.atributi,(Set)funDep[0]).containsAll(X)){
                    m_ind=true;
                    g_ind=true;
                    RelationScheme max=schema;
                    while(g_ind) {
                        boolean p_ind=false;
                        Set descedents=syn.graf_nivo(0,max.id);
                        Iterator itd=descedents.iterator();
                        while(itd.hasNext()) {
                            int rsd=(new Integer(itd.next().toString())).intValue();
                            RelationScheme schemad=syn.getRelationScheme(rsd);
                            if(syn.zatvaranje(schemad.atributi,(Set)funDep[0]).containsAll(X)){
                                p_ind=true;
                                max=schemad;
                            }
                        }
                        if(!p_ind || descedents.isEmpty()){
                            g_ind=false;   
                        }
                    }
                    min_set.add(max);
                }
            }
        }     
        return getDistinct(min_set);
    }
    
    public Set getDistinct(Set a){
        Iterator it=a.iterator();
        Set b=new HashSet();
        while(it.hasNext()) {
            RelationScheme r=(RelationScheme)it.next();
            Iterator itb=b.iterator();
            int ind=0; 
            while(itb.hasNext()) {
                if(r.id==((RelationScheme)itb.next()).id)
                    ind=1;
            }
            if(ind==0)b.add(r);
        }
        return b;
    }
    
    public void printS(Set a){
        Iterator it=a.iterator();
        while(it.hasNext()) {
            RelationScheme r=(RelationScheme)it.next();
            r.print();
        }
        System.out.println();
    }
    public Set relevantni_cvorovi(Set minNodes, FormType form,Set T) {
    Omega=new Object[rs_set.size()][2];
    Set Rc =new HashSet();
    Iterator itO=rs_set.iterator();
    int ind=0;
    while(itO.hasNext()) {
        RelationScheme Rj=(RelationScheme)itO.next();
        Omega[ind][0]=""+Rj.id;
        Omega[ind][1]=new HashSet();
        ind++;
    }
    Set OO=razlikaRS(rs_set, minNodes);
    itO=OO.iterator();
    while(itO.hasNext()) {
        RelationScheme Rj=(RelationScheme)itO.next();
        if(!(syn.Razlika(syn.Presjek(Rj.atributi,form.getAttributes(form.root)),Rj.primarni_kljuc).isEmpty()) || !syn.Presjek(T,Rj.kljuc).isEmpty()){
            Iterator itm=minNodes.iterator();
            while(itm.hasNext()) {
                RelationScheme Ri=(RelationScheme)itm.next();
                Set pTild=getPtild(Ri);
                ind=getOccarencyIndex(Ri);
                if(pTild.contains(Rj))
                    ((Set)Omega[ind][1]).add(Rj);
            }
        }
    }
    Rc.addAll(minNodes);
    for (int i=0;i<rs_set.size();i++) {
        Set pom1=new HashSet();
        pom1=syn.Razlika((Set)Omega[i][1], Rc);
        Iterator it=((Set)Omega[i][1]).iterator();
        while(it.hasNext()){
            RelationScheme Ri=(RelationScheme)it.next();
            if(Rc.contains(Ri))continue;
            Rc.add(Ri);
        }
    }
    return getDistinct(Rc);
    }
    public Set razlikaRS(Set a, Set b){
        Set pom=new HashSet();
        pom.addAll(a);
        Iterator it=pom.iterator();
        while(it.hasNext()){
            RelationScheme R=(RelationScheme)it.next();
            Iterator it1=b.iterator();
            while(it1.hasNext()){
                RelationScheme R1=(RelationScheme)it1.next();
                if(R.id==R1.id)it.remove();
            }
        }
        return pom;
    }
    public Set generisanje_P_s_pod(Set rel_set, Set min_set) {
        Set PSMIN=new HashSet();
        PSMIN.addAll(min_set);
        ZAST=new int[Omega.length]; 
        Set TNVt=new HashSet();
        Set Omegat=new HashSet();
        Teta=new Set[Omega.length][Omega.length];
        Set[] SIG=new HashSet[Omega.length];
        for (int i=0;i<Omega.length;i++) {
            ZAST[i]=((Set)Omega[i][1]).size();
            for (int j=0;j<Omega.length;j++) 
                Teta[i][j]=new HashSet();
            SIG[i]=new HashSet();
        }
        Set RC=new HashSet();
        RC=syn.Razlika(rel_set,min_set);
        Iterator it=PSMIN.iterator();
        while(it.hasNext()){
            RelationScheme Rs=(RelationScheme)it.next();
            RelationScheme Rt=izbor_max_zast_cvora(PSMIN, false, new HashSet());
            PSMIN.remove(Rt);
            it=PSMIN.iterator();
            int t=getOccarencyIndex(Rt);
            TNVt=new HashSet();
            TNVt.add(Rt);
            SIG[t]=new HashSet();
            Omegat=syn.Presjek((Set)Omega[t][1],RC);
            RC=syn.Razlika(RC,(Set)Omega[t][1]);
            while(!Omegat.isEmpty()){
                Set RLN=new HashSet();
                RLN.addAll(Omegat);
                int NTNt=TNVt.size();
                Set[] SLN=new HashSet[NTNt+1];
                int i=NTNt;
                while(i!=0){
                    RelationScheme RI=izbor_max_zast_cvora(TNVt, false, new HashSet());
                    TNVt.remove(RI);
                    Set p=getSchemes(syn.getInferiors(RI.id));
                    Set pTild=getPtild(RI);
                    Set[] ret=relevantni_i_vezni_cvorovi(RI,p, pTild,RLN,SIG[t],Omegat);
                    Set RSL=ret[0];
                    Set RLD=ret[1];
                    SLN[i]=propagacija_puta(RI,p, RLD, SIG[t]);
                    SIG[t]=prebacivanje_kandidata(RI, RSL, SLN[i], min_set, SIG[t]);
                    i=i-1;
                }
                Set pom;
                pom = new HashSet();
                for(int k=1;k<=NTNt;k++)
                    pom.addAll(SLN[k]);
                TNVt=pom;
            }
        }
        Set POD=new HashSet();
        for(int i=0;i<SIG.length;i++)
            POD.addAll(SIG[i]);
        return POD;
    }
    public Set getPtild(RelationScheme RS){
        Set ret=new HashSet();
        ret.addAll(getSchemes(syn.getSubGraph(RS.id)));
        return ret;
    }
    public Set prebacivanje_kandidata(RelationScheme RI, Set RSL, Set SLN1, Set P_s_min, Set SIGt){
        Set retSIG=new HashSet();
        retSIG.addAll(SIGt);
        if(!retSIG.contains(RI) && !P_s_min.contains(RI)){
            boolean ind=false;
            Set pom=syn.Unija(SLN1,RSL);
            Iterator it=pom.iterator();
            while (it.hasNext() && !ind){
                RelationScheme Rm=(RelationScheme)it.next();
                if(RI.primarni_kljuc.containsAll(Rm.primarni_kljuc))
                    ind=true;
            }
            if(ind)
                retSIG.add(RI);
        }
        return retSIG;
    }
    public Set[] relevantni_i_vezni_cvorovi(RelationScheme RI, Set P, Set Ptild, Set RLN, Set SIGt, Set Omegat){
        Set[] ret=new HashSet[2];
        Set RSL=new HashSet();
        Set RLD=new HashSet();
        Iterator it=P.iterator();
        int RIIndex=getOccarencyIndex(RI);
        while(it.hasNext()){
            RelationScheme rs=(RelationScheme)it.next();
            int i=getOccarencyIndex(rs);
            ZAST[i]=0;
        }
        Iterator itr=RLN.iterator();
        while(itr.hasNext()){
            RelationScheme Rj=(RelationScheme)itr.next();
            int rsjIndex=getOccarencyIndex(Rj);
            if(P.contains(Rj)){
                SIGt.add(Rj);
                Omegat.remove(Rj);
                RLN.remove(Rj);
                itr=RLN.iterator();
                RSL.add(Rj);
            }
            else{
                Iterator itp=P.iterator();
                while(itp.hasNext()){
                    RelationScheme Ri=(RelationScheme)itp.next();
                    Set pi=getSchemes(syn.getInferiors(Ri.id));
                    Set pTildi=new HashSet();
                    Iterator it1=pi.iterator();
                    while(it1.hasNext()){
                        RelationScheme Rs=(RelationScheme)it1.next();
                        pTildi.addAll(getSchemes(syn.getSubGraph(Rs.id)));
                    }
                    if(getRSAttributes(pTildi).containsAll(Rj.atributi))
                        ((Set)Teta[RIIndex][rsjIndex]).add(Ri);   
                }
                if(!((Set)Teta[RIIndex][rsjIndex]).isEmpty()){
                    RLD.add(Rj);
                    RLN.remove(Rj);
                    itr=RLN.iterator();
                    Iterator itk;
                    itk = ((Set)Teta[RIIndex][rsjIndex]).iterator();
                    while(itk.hasNext()){
                        RelationScheme Ri=(RelationScheme)itk.next();
                        int indk=getOccarencyIndex(Ri);
                        ZAST[indk]=ZAST[indk]+1;
                    }
                }
            }
                
        }
        ret[0]=RSL;
        ret[1]=RLD;
        return ret;
    }
    public Set getRSAttributes(Set P) {
        Set ret=new HashSet();
        Iterator itr=P.iterator();
        while(itr.hasNext()){
            RelationScheme Ri=(RelationScheme)itr.next();
            ret.addAll(Ri.atributi);
        }   
        return ret;
    }
    public Set propagacija_puta(RelationScheme RI, Set P, Set RLD, Set SIG){
        Set SLN1=new HashSet();
        Set SC=new HashSet();
        SC.addAll(P);
        int indRI=getOccarencyIndex(RI);
        while(!RLD.isEmpty()){
            RelationScheme Ri=izbor_max_zast_cvora(SC,true,SIG);
            SC.remove(Ri);
            Set pom=new HashSet();
            Iterator it=RLD.iterator();
            while(it.hasNext()){
                RelationScheme Rj=(RelationScheme)it.next();
                int indj=getOccarencyIndex(Rj);
                if(((Set)Teta[indRI][indj]).contains(Ri))
                    pom.add(Rj);
            }
            if(!pom.isEmpty()){
                RLD.removeAll(pom);
                SLN1.add(Ri);
            }
        }
        return SLN1;
    }
    public RelationScheme izbor_max_zast_cvora(Set SC, boolean ind, Set  SIG){
        int maxOccurency=0;
        RelationScheme Rmax=null;
        Iterator it=SC.iterator();
        while(it.hasNext()){
            RelationScheme Rk=(RelationScheme)it.next();
            int occur=getOccarencyIndex(Rk);
            if(Rmax==null){
                Rmax=Rk;
                maxOccurency=ZAST[occur];
            }
            if(ZAST[occur]>maxOccurency){
                Rmax=Rk;
                maxOccurency=occur;
            }
            else{
                if(ind && ZAST[occur]==maxOccurency && SIG.contains(Rk))
                    Rmax=Rk;
            }
        }
        return Rmax;
    }
    public int getOccarency(RelationScheme rs){
        for (int i=0;i<Omega.length;i++) {
            if(Omega[i][0]!=null)
                if((new Integer((String)Omega[i][0])).intValue()==rs.id)
                    return ((Set)Omega[i][1]).size();
        }  
        return 0;
    }
    public int getOccarencyIndex(RelationScheme rs){
        for (int i=0;i<Omega.length;i++) {
            if(Omega[i][0]!=null)
                if((new Integer((String)Omega[i][0])).intValue()==rs.id)
                    return i;
        }  
        return -1;
    }
    public Set getSchemes(Set rs){
        Set ret=new HashSet();
        Iterator it=rs_set.iterator();
        while(it.hasNext()){
            RelationScheme rsm=(RelationScheme)it.next();
            Iterator its=rs.iterator();
            while(its.hasNext()){
                int m=(new Integer(its.next().toString())).intValue();
                if(rsm.id==m)
                    ret.add(rsm);
            }
        }
        return ret;
    }
    public Set[] skupovi_za_azuriranje(Set P){
        Set ins=new HashSet(), upd=new HashSet(), del=new HashSet();
        boolean ind=false;
        for(int i=0;i<tf.compType.length;i++)
        {
            ind=false;
            ComponentType cmp=form.getComp(tf.compType[i], tf);
            Iterator it=P.iterator();
            Set X=form.getUnijaPoJednogKljuca(cmp,(new HashSet()));
            while(it.hasNext()&!ind) {
                RelationScheme Rj=(RelationScheme)it.next();
                if(Rj.kljuc.contains(X)) {
                    Object[] rel=new Object[2];
                    rel[0]=cmp;
                    rel[1]=Rj;
                    del.add(rel);
                    it.remove();
                    ind=true;
                    Set pom=syn.Razlika(Rj.atributi,form.getAttributes(form.root));
                    Iterator it1=pom.iterator();
                    boolean can=true;
                    while(it.hasNext()){
                        Application.AttType att=(Application.AttType)it.next();
                        if(att.null_allowed==0)
                            can=false;
                    }
                    if(X.containsAll(Rj.kljuc) && can){
                        ins.add(rel);
                    }
                }
            }
        }
        Set pom=syn.Razlika(form.getAttributes(form.root), KpF);
        Iterator it=pom.iterator();
        ind=false;
        while(it.hasNext()) {
            int att=(new Integer(it.next().toString())).intValue();
            if(!form.getAttribute(form.root,att).derived_atts.isEmpty())continue;
            Iterator itt=del.iterator();
            while(itt.hasNext() && !ind){
                Object[] rel=(Object[])itt.next();
                ComponentType comp=(ComponentType)rel[0];
                RelationScheme rs=(RelationScheme)rel[1];
                Set pom1=syn.Presjek(comp.attributes,rs.atributi);
                if(pom1.contains(""+att)) {
                    ind=true;
                    upd.add(""+att);
                }
            }
        }
        Set[] ret=new HashSet[3];
        ret[0]=del;
        ret[1]=ins;
        ret[2]=upd;
        return ret;
    }
    public Set formiranje_P_s_ref(Set del, Set ins, Set upd){
        Set P=new HashSet();
        Iterator it=del.iterator();
        while(it.hasNext()) {
            Object[] rel=(Object[])it.next();
            ComponentType comp=(ComponentType)rel[0];
            RelationScheme R1=(RelationScheme)rel[1];
            P.addAll(getSchemes(syn.getSuperiors(R1.id)));
            Set pod=getSchemes(syn.getInferiors(R1.id));
            Iterator itt=pod.iterator();
            while(itt.hasNext()) {
                RelationScheme Rk= (RelationScheme)itt.next();
                if(!(syn.Presjek(Rk.primarni_kljuc,upd)).isEmpty() || (ins.contains(rel) && R1.atributi.containsAll(Rk.primarni_kljuc))) {
                    P.add(Rk);  
                }
            }
        }
        return P;
    }
    public Set redukcija_P_s_min(Set P_s_min) {
        Set PsminR=new HashSet();
        PsminR.addAll(P_s_min);
        Iterator it=PsminR.iterator();
        while(it.hasNext()){
            RelationScheme Ri= (RelationScheme)it.next();   
            Set pomm=new HashSet();
            pomm.add(Ri);
            Set pom=syn.Razlika(PsminR,pomm);
            Iterator itt=pom.iterator();
            while(itt.hasNext()){
                RelationScheme Rj= (RelationScheme)itt.next(); 
                if(syn.zatvaranje(Ri.atributi,(Set)funDep[0]).containsAll(Rj.atributi))
                    PsminR.remove(Rj);
            }
        }
        return PsminR;
    }
    public Set getU(){
        Set U=new HashSet();
        Iterator it=rs_set.iterator();
        while(it.hasNext()){
            RelationScheme rs= (RelationScheme)it.next();   
            U.addAll(rs.atributi);
        }
        return U;
    }
    public Set prosirenje_skupa_obelezja(){
        Set Wex=new HashSet();
        Wex.addAll(Wext);
        Iterator it=Wext.iterator();
        while(it.hasNext()){
            int a =(new Integer((String)it.next())).intValue();
            Application.Attribute A= form.getAttribute(form.root,a);  
            if(!A.derived_atts.isEmpty()){
                Wex.addAll(syn.Presjek(U,A.derived_atts));
                Set Wpom=getF(A);
                Iterator it1=Wpom.iterator();
                while(it1.hasNext()){
                    Application.Attribute A1= (Application.Attribute)it1.next(); 
                    Set Wpom1=getF(A1);
                    it1.remove();
                    Wpom.addAll(Wpom1);
                    it1=Wpom.iterator();
                    Wex.addAll(syn.Presjek(U,A1.derived_atts));
                }
            }
        }
        return Wex;
    }
    public Set getF(Application.Attribute A){
        Set pom=new HashSet();
        Set Wpom=new HashSet();
        pom.addAll(A.derived_atts);
        Iterator it1=pom.iterator();
        while(it1.hasNext()){
            Application.Attribute A1= (Application.Attribute)it1.next(); 
            if(!A1.derived_atts.isEmpty())
                Wpom.add(A1);
        }
        return Wpom;
    }
    public Set generisanje_P421(Set Ty, Set Ttilde){
       Set Psmin=minimalni_cvorovi_podseme(Ty);
       Set PsminR=redukcija_P_s_min(Psmin);
       Set Rc=relevantni_cvorovi(PsminR, form, Ttilde);
       Set Pspod=generisanje_P_s_pod(PsminR, Rc);
       Set Psu=syn.Unija(Psmin,Pspod);
       Set Kp=new HashSet();
       Iterator it=Psu.iterator();
       while(it.hasNext()){
            RelationScheme Ri=(RelationScheme)it.next();
            Kp.addAll(Ri.primarni_kljuc);
       }
       Set Wex=prosirenje_skupa_obelezja();
       Set Pu=new HashSet();
       it=Psu.iterator();
       while(it.hasNext()){
           RelationScheme Ri=(RelationScheme)it.next();
           Set[] rel=new Set[2];
           rel[0]=syn.Presjek(Ri.atributi,syn.Unija(Kp,Wex)); 
           rel[1]=syn.Unija(Ri.primarni_kljuc,(new HashSet()));
           Pu.add(rel);
       }
       return Pu;
    }
    public Set generisanje_P_s_rek(){
        Set PsRek=new HashSet();
        Set Wpom=new HashSet();
        Set pom=syn.Razlika(KpF,U);
        Iterator it=pom.iterator();
        while(it.hasNext()){
            Application.Attribute att= (Application.Attribute)it.next(); 
            Wpom=getF(att);
            Iterator it1=Wpom.iterator();
            while(it1.hasNext()){
                Application.Attribute att1= form.getAttribute(form.root,((Integer)it1.next()).intValue()); 
                it1.remove();
                Wpom.addAll(getF(att1));
                Iterator it2=rs_set.iterator();
                while(it2.hasNext()){
                    RelationScheme Ri= (RelationScheme)it2.next();    
                    Iterator it3=Ri.kljuc.iterator();
                    while(it3.hasNext()){
                        Set kljuc=(Set)it.next();
                        if(syn.equalSets(syn.Presjek(att1.derived_atts,U),kljuc))
                            PsRek.add(Ri); 
                    }
                }
            }
        }
        return PsRek;
    }
    public Set generisanje_P425(Set Tr, Set Trtild){
        Set Psrmin=minimalni_cvorovi_podseme(Tr);
        Set PsminR=redukcija_P_s_min(Psrmin);
        Set Rc=relevantni_cvorovi(PsminR, form, Trtild);
        Set Psrpod=generisanje_P_s_pod(PsminR, Rc);
        Set Psrek=generisanje_P_s_rek();
        Set Psu=syn.Unija(PsminR,Psrpod);
        Psu.addAll(Psrek);
        Set Kp=new HashSet();
        Iterator it=Psu.iterator();
        while(it.hasNext()){
            RelationScheme Ri=(RelationScheme)it.next();
            Kp.addAll(Ri.primarni_kljuc);
        }
       Set Wex=prosirenje_skupa_obelezja();
       Set Pu=new HashSet();
       it=Psu.iterator();
       while(it.hasNext()){
           RelationScheme Ri=(RelationScheme)it.next();
           Set[] rel=new Set[2];
           rel[0]=syn.Presjek(Ri.atributi,syn.Unija(Kp,Wex)); 
           rel[1]=syn.Unija(Ri.primarni_kljuc,(new HashSet()));
           Pu.add(rel);
       }
       return Pu;
    }
    public Set generisanje_P427(Set Ty, Set Ttilde){
        Set Psrmin=minimalni_cvorovi_podseme(Ty);
        Set PsminR=redukcija_P_s_min(Psrmin);
        Set Rc=relevantni_cvorovi(PsminR, form, Ttilde);
        Set Psrpod=generisanje_P_s_pod(PsminR, Rc);
        Set Psrek=generisanje_P_s_rek();
        Set Psu=syn.Unija(PsminR,Psrpod);
        Psu.addAll(Psrek);
        Set Pu=new HashSet();
        Set Kp=new HashSet();
        Iterator it=Psu.iterator();
        while(it.hasNext()){
            RelationScheme Ri=(RelationScheme)it.next();
            Kp.addAll(Ri.primarni_kljuc);
        }
        Set Wex=prosirenje_skupa_obelezja();
        it=Psu.iterator();
        while(it.hasNext()){
           RelationScheme Ri=(RelationScheme)it.next();
           Set[] rel=new Set[2];
           rel[0]=syn.Presjek(Ri.atributi,syn.Unija(Kp,Wex)); 
           rel[1]=syn.Unija(Ri.primarni_kljuc,(new HashSet()));
           Pu.add(rel);
        }
        return Pu;
    }
    public boolean test_zatvaranja_nos_grupa(Set T){
        boolean ind=true;
        Iterator it=T.iterator();
        while(it.hasNext() && ind){
            boolean ind1=false;
            Set Xi=(HashSet)it.next();
            Iterator it1=rs_set.iterator();
            while(it1.hasNext() && !ind1){
                RelationScheme Rj=(RelationScheme)it1.next();
                Set p=getSchemes(syn.getInferiors(Rj.id));
                Set pTild=getPtild(Rj);
                Iterator it2=pTild.iterator();
                Set pom =new HashSet();
                while(it2.hasNext()){
                    RelationScheme Ri=(RelationScheme)it2.next();
                    pom.addAll(Ri.atributi);
                }
                if(pom.containsAll(Xi))
                    ind1=true;
            }
            if(!ind1)
                ind=false;
        }
        return ind;
    }
    public Object[] test_spojivosti(Set Sj, Set T){
        Object[] ret=new Object[2];
        boolean ind=false;
        int b=Sj.size();
        Set[][] TY=new HashSet[b][2];
        Set Ty=new HashSet();
        Set pomT=new HashSet();
        Iterator it=T.iterator();
        while(it.hasNext()){
            Set X=(HashSet)it.next();
            pomT.addAll(X);
        }
        it=Sj.iterator();
        int k=0;
        while(it.hasNext()&&!ind){
            Set Jy=(HashSet)it.next();
            TY[k][0]=Jy;
            TY[k][1]=new HashSet();
            Set pomJy=new HashSet();
            Iterator it1=Jy.iterator();
            while(it1.hasNext()){
                Set Y=(HashSet)it1.next();
                pomJy.addAll(Y);
            }
            if(syn.equalSets(pomJy,pomT)){
                boolean ind1=true;
                it1=Jy.iterator();
                while(it1.hasNext()&&ind1){
                    Set Y=(HashSet)it1.next();
                    Iterator it2=T.iterator();
                    while(it2.hasNext()){
                        Set X=(HashSet)it2.next();
                        if(X.containsAll(Y))
                            continue;
                        else
                            ind1=false;
                    }
                }
                if(ind1){
                    ind=true;
                    Ty=Jy;
                    TY[k][1]=Jy;
                }
            }
            k=k+1;
        }
        ret[0]=new Boolean(ind);
        ret[1]=TY;
        return ret;
    }
    public Object[] transformacija_T(Set T, Set T_tilde) {
       boolean ind=true;
       Set pom=syn.Razlika(KpF,U);
       Iterator it=pom.iterator();
        while(it.hasNext()&& ind){
            int A= (new Integer(it.next().toString())).intValue();
            Application.Attribute a=form.getAttribute(form.root,A);
            boolean w_ind=false;
            Set WPOM=new HashSet();
            WPOM.add(""+A);
            Iterator it1=WPOM.iterator();
            while(it1.hasNext()&& !w_ind){
                int Ar= (new Integer(it1.next().toString())).intValue();
                Application.Attribute ar=form.getAttribute(form.root,Ar);
                Set pom1=new HashSet();
                Set pomSet1=syn.Presjek(ar.derived_atts,U);
                Iterator it2=pomSet1.iterator();
                while(it2.hasNext()){
                    int Ab= (new Integer(it2.next().toString())).intValue();
                    Application.Attribute ab=form.getAttribute(form.root,Ab);
                    if(a.domain.id==ab.domain.id) {
                        w_ind=true;
                    }
                    else{
                        WPOM.remove(""+Ar);
                        Iterator it3=pom1.iterator();
                        while(it3.hasNext()){
                            int Ap= ((Integer)it3.next()).intValue();
                            Application.Attribute ap=form.getAttribute(form.root,Ap);
                            if(!ap.derived_atts.isEmpty())
                                WPOM.add(""+Ap);        
                        }
                    }
                }
                if(!w_ind)ind=false;
            }
        }
        if(ind){
            Tr=new HashSet();
            Tr_tilde=new HashSet();
            it=T_tilde.iterator();
            while(it.hasNext()){
                Set Xi= (Set)it.next();
                Set Xri=syn.Presjek(Xi,U);
                Set pomSet=syn.Razlika(Xi,U);
                Iterator it1=pomSet.iterator();
                while(it1.hasNext()){
                    int A= ((Integer)it1.next()).intValue();
                    Application.Attribute a=form.getAttribute(form.root,A);
                    Xi.addAll(syn.Presjek(a.derived_atts,U));
                }
                Tr_tilde.addAll(Xri);
                if(T.contains(Xi))Tr.addAll(Xri);
            }
        }
        Object[] ret=new Object[3];
        ret[0]=(new Boolean(ind));
        ret[1]=Tr;
        ret[2]=Tr_tilde;
        return ret;
    }
    public boolean test_uslova_430(){
        boolean ind=true;
        Set[] ret=new HashSet[2];
        Set IZV=new HashSet();
        Set NF=new HashSet();
        NF=form.getNF(form.root);
        Iterator it=NF.iterator();
        while(it.hasNext()){
            Set X=(Set)it.next();
            Set LSX=new HashSet();
            Set X1=new HashSet();
            X1.addAll(X);
            Iterator it1=X.iterator();
            while(it1.hasNext()){
                int A= (new Integer((String)it1.next())).intValue(); 
                Set aSet=new HashSet();
                aSet.add(""+A);
                Set pomSet=syn.zatvaranje(syn.Razlika(X1,aSet),(Set)funDep[0]);
                if(pomSet.contains(""+A)){
                    LSX.add(""+A);
                    X1.remove(""+A);
                }
            }
            if(!LSX.isEmpty()){
                ind=false;
                ret[0]=X;
                ret[1]=X1;
                IZV.add(ret);
            }
        }
        return ind;
    }
    public boolean test_uslova_419(Set Sj, Set T){
        boolean ind=false;
        if(T.size()!=1){
            Iterator it=Sj.iterator();
            while(it.hasNext()&&!ind){
                Set Ji= (Set)it.next();
                boolean ind1=true;
                Iterator it1=T.iterator();
                while(it1.hasNext()&&ind1){
                    Set Xj= (Set)it1.next();
                    Iterator it2=Ji.iterator();
                    boolean can=false;
                    while(it2.hasNext()&&ind1){
                        Set Yi=(Set)it2.next();
                        if(syn.equalSets(Yi,Xj))
                            can=true;
                    }
                    if(!can)
                        ind1=false;
                }
                if(ind1)ind=true;
            }
        }
        else ind=true;
        return ind;
    }
    public int analiza_primjenljivosti(){
        int ind=0;
        boolean ind1,can;
        Set pom=syn.Razlika(Wext,U);
        can=true;
        Iterator it=pom.iterator();
        while(it.hasNext()){
            int A =(new Integer((String)it.next())).intValue();
            Application.Attribute a= form.getAttribute(form.root,A);
            if(a.derived_atts.isEmpty())
                can=false;
        }
        if(can){
            if(syn.Razlika(KpF,U).isEmpty()){
                ind1=test_zatvaranja_nos_grupa(T);
                if(ind1){
                    ind1=test_uslova_419(Sj,T);
                    if(!ind1 && T.size()!=1)
                        Sj.add(JF);
                    ind1=test_uslova_430();
                    System.out.println(""+ind1);
                    if(ind1){
                        Iterator it1=W.iterator();
                        ind=3;
                        while(it1.hasNext()){
                            int A =(new Integer((String)it1.next())).intValue();
                            Application.Attribute a1= form.getAttribute(form.root,A);  
                            if(!W.containsAll(a1.derived_atts))ind=4;
                        }
                    }
                    else ind=4;
                }
                else{
                    Object[] ret=test_spojivosti(Sj,T);
                    ind1=((Boolean)ret[0]).booleanValue();
                    if(ind1) ind=5;
                    else ind=0;
                }
            }
            else{
                Object[] ret=transformacija_T(T, T_tilde);
                ind1=((Boolean)ret[0]).booleanValue();
                Tr=(Set)ret[1];
                Tr_tilde=(Set)ret[2];
                if(ind1){
                    ind1=test_zatvaranja_nos_grupa(Tr);
                    if(ind1)ind=2;
                    else{
                        ret=test_spojivosti(Sj,Tr);
                        ind1=((Boolean)ret[0]).booleanValue();
                        if(ind1)ind=1;
                        else ind=0;
                    }
                }
                else ind=0;
            }
        }
        else ind=0;
        return ind;
    }
}
