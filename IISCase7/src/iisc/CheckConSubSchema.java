package iisc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * CheckConSubScheme class contains methods for generation of the DB subscheme 
 * that corresponds to the check constraint. Algorithms implemented here are 
 * taken over from Prof. Lukovic's Master thesis.
 * 
 * @author      Nikola Obrenovic (nikob at sbb.rs)
 * @version     0.1 20/05/10
 */
public class CheckConSubSchema{
    Set SubSchema=new HashSet(); 
    Synthesys syn;
    Connection conn;
    public CheckConFormType form;
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
    Set JF=new HashSet();;
    Set Tr;
    Set Tr_tilde;
    public int apply_id;
    public Set Pa;
    public Set Pu;
    NormalForm checkCon;
    
    /**
     * Contructor.
     * 
     * @param _conn JDBC connection
     * @param pr Project ID.
     * @param as Application System ID
     * @param formTypeID Form type ID.
     * @param checkCon Check constraint.
     */
    public CheckConSubSchema(Connection _conn, int pr, int as, int formTypeID, NormalForm checkCon) {
        this.checkCon = checkCon;
        syn=new Synthesys();
        syn.pr=pr;
        syn.as=as;
        syn.con=_conn;
        conn=_conn;
        
        form= new CheckConFormType(pr, formTypeID, conn);
        T=form.getT(form.root);
        System.out.println("T="+T.toString());
        JF.addAll(T);
        //Sj=getSJ();
        T_tilde=form.getTtilde(form.root, new HashSet());
        rs_set=syn.getRelationSchemes();
        funDep=syn.getFunDepScheme(rs_set);
    }
    
    /**
     * Finds the relation schemes that match the check constraint given in the 
     * constructor.
     * 
     * @return Set of RelationScheme objects.
     */
    public Set findRelSchemes()
    {
        Set P_s_min = minimalni_cvorovi_podseme(T);
        System.out.println("Minimalni skup");
        
        Set Reduced_P_s_min = new HashSet();
        Set cand = new HashSet();
        Set tmp = new HashSet();
        tmp.addAll(P_s_min);
        do
        {
            cand = new HashSet();
            Iterator iter = tmp.iterator();
            while(iter.hasNext())
            {
                RelationScheme minRS = (RelationScheme)iter.next();
                if(getRSAttributes(getPtild(minRS)).containsAll(checkCon.getAllAttrIDs()))
                    cand.add(minRS);
            }
            if(cand.size() >= 1)
            {
                Reduced_P_s_min =  new HashSet();
                iter = cand.iterator();
                RelationScheme rsch = (RelationScheme)iter.next(); 
                Reduced_P_s_min.add(rsch);
                tmp = new HashSet();
                tmp.addAll(getSchemes(syn.getInferiors(rsch.id)));
            }
        }while(cand.size() >= 1);
        //prinS(P_s_min);
        
        if(Reduced_P_s_min.size() == 1)
            P_s_min = Reduced_P_s_min;
            
        Set Rc = relevantni_cvorovi(P_s_min, form, T_tilde,checkCon);
        System.out.println("Relevantni cvorovi");
        printS(Rc);
        Set P_s_pod=generisanje_P_s_pod(Rc, P_s_min);
        System.out.println("Podredjeni cvorovi");
        //printS(P_s_pod);
        Set Psu=syn.Unija(P_s_min,P_s_pod);
        
        return Psu;
    }
     
    /**
     * Finds minimal nodes corresponding to the given set of nodes.
     * 
     * @param rss Set of RelationScheme objects.
     * @return Set of RelationScheme objects.
     */
    public Set getMinNodes(Set rss)
     {
       try{
        JDBCQuery query=new JDBCQuery(conn);
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
    
    /**
     * Finds minimal nodes of the subscheme.
     * 
     * @param T_set Set of RelationScheme objects.
     * @return Set of RelationScheme objects.
     */
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
    
    /**
     * Throws out duplicates from the given set. Schemes are compared by their 
     * ID. 
     * 
     * @param a Set of RelationScheme objects with duplicate schemes.
     * @return Set of dinstinct RelationScheme objects.
     */
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
    
    /**
     * Prints out relation schemes for testing purposes.
     * 
     * @param a Set of RelationScheme objects.
     */
    public void printS(Set a){
        Iterator it=a.iterator();
        while(it.hasNext()) {
            RelationScheme r=(RelationScheme)it.next();
            r.print();
        }
        System.out.println();
    }
    
    /**
     * Finds relation schemes relevant to the check constraint.
     * 
     * @param minNodes Subscheme minimal nodes. 
     * @param form Form type.
     * @param T ...
     * @param checkCon Check constraint.
     * @return
     */
    public Set relevantni_cvorovi(Set minNodes, CheckConFormType form,Set T, NormalForm checkCon) {
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
        if(!(syn.Presjek(Rj.atributi,checkCon.getAllAttrIDs()).isEmpty()) && 
            (!(syn.Razlika(syn.Presjek(Rj.atributi,form.getAttributes(form.root)),Rj.primarni_kljuc).isEmpty()) || !syn.Presjek(T,Rj.kljuc).isEmpty())){
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
    
        if(getRSAttributes(Rc).containsAll(checkCon.getAllAttrIDs()))
            break;
            
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
    
    /**
     * Finds difference between two sets of RelationScheme objects.
     * 
     * @param a Set of RelationScheme objects.
     * @param b Set of RelationScheme objects.
     * @return Set of RelationScheme objects.
     */
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
    
    /**
     * Finds relation schemes needed to link minimal schemes with the relevant 
     * schemes.
     * 
     * @param rel_set Relevant schemes. Set of RelationScheme objects.
     * @param min_set Minimal schemes. Set of RelationScheme objects.
     * @return Set of RelationScheme objects.
     */
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
    
    /**
     * Finds inferior schemes to the given relation scheme, according to the 
     * closure graph.
     * 
     * @param rs
     * @return Set of RelationScheme objects.
     */
    public Set getPtild(RelationScheme rs){
        Set ret=new HashSet();
        ret.addAll(getSchemes(syn.getSubGraph(rs.id)));
        return ret;
    }
    
    /**
     * Function that selects the relation schemes that belong to the next level 
     * path from the minimal to the relavant schemes.
     * 
     * @param RI
     * @param RSL
     * @param SLN1
     * @param P_s_min
     * @param SIGt
     * @return
     */
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
    
    /**
     * Finds relation schemes that link minimal and relevant schemes.
     * 
     * @param RI
     * @param P
     * @param Ptild
     * @param RLN
     * @param SIGt
     * @param Omegat
     * @return
     */
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
    
    /**
     * Finds all attributes contained in the given relation schemes.
     * 
     * @param P Set of RelationScheme objects.
     * @return Set of atrribute IDs as Strings.
     */
    public Set getRSAttributes(Set P) {
        Set ret=new HashSet();
        Iterator itr=P.iterator();
        while(itr.hasNext()){
            RelationScheme Ri=(RelationScheme)itr.next();
            ret.addAll(Ri.atributi);
        }   
        return ret;
    }
    
    /**
     * Determines the next level of path (relation schemes) from minimal to the
     * relevant schemes.
     * 
     * @param RI
     * @param P
     * @param RLD
     * @param SIG
     * @return
     */
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
    
    /**
     * Selects a relation schemes that leads to the most relevant relation 
     * schemes from all possible candidates for the next level of the path.
     * 
     * @param SC
     * @param ind
     * @param SIG
     * @return
     */
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
    
    /**
     * 
     * @param rs
     * @return
     */
    public int getOccarencyIndex(RelationScheme rs){
        for (int i=0;i<Omega.length;i++) {
            if(Omega[i][0]!=null)
                if((new Integer((String)Omega[i][0])).intValue()==rs.id)
                    return i;
        }  
        return -1;
    }
    
    /**
     * Returns the set of RelationScheme objects that correspond to the given 
     * IDs of the relation schemes..
     * 
     * @param rs Set of IDs (as Strings).
     * @return Set of RelationScheme objects.
     */
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
    
}
