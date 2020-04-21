package iisc;

import java.util.ArrayList;
import java.util.HashMap;

public class Table 
{
  private String ImeTabele;
  private int BrAtributa;
  String[] ImenaAtributa;
  String[] TipPodataka;
  String[] Izrazi;
  String[] Uniqueovi;
  String[] Defaultovi;
  int[] Nulls;
  private String PKljuc="";
  String[] Kljuc;
  
  /*nobrenovic: start*/
  ArrayList<CheckConstraint> checkCons = new ArrayList<CheckConstraint>();
  HashMap<String, ArrayList<CheckConstraint>> attrChkCons = new HashMap<String, ArrayList<CheckConstraint>>();
  int tableId;
   /*nobrenovic: stop*/
  
  
  public Table()
  {
      
  }
  
  String[] KreirajNizAtr(int k)
  {
   String[] ImenaAtributa = new String[k];
   for (int i=0;i<ImenaAtributa.length;i++)
   {
     ImenaAtributa[i]="";
   }
   return ImenaAtributa;
  }
  String[] KreirajNizUniq(int k)
  {
   String[] Uniqueovi = new String[k];
   for (int i=0;i<Uniqueovi.length;i++)
   {
     Uniqueovi[i]="";
   }
   return Uniqueovi;
  }
  String[] KreirajNizDefault(int k)
  {
   String[] Defaultovi = new String[k];
   for (int i=0;i<Defaultovi.length;i++)
   {
     Defaultovi[i]="";
   }
   return Defaultovi;
  }
  String[] KreirajNizTip(int k)
  {
   String[] TipPodataka = new String[k];
   for (int i=0;i<TipPodataka.length;i++)
   {
     TipPodataka[i]="";
   }
   return TipPodataka;
  }
  int[] KreirajNizNull(int k)
  {
   int[] Nulls = new int[k];
   for (int i=0;i<Nulls.length;i++)
   {
     Nulls[i]=0;
   }
   return Nulls;
  }
  String[] KreirajNizIzrazi(int k)
  {
   String[] Izrazi = new String[k];
   for (int i=0;i<Izrazi.length;i++)
   {
     Izrazi[i]="";
   }
   return Izrazi;
  }
  String[] KreirajNizKljuc(int k)
  {
   String[] Kljuc = new String[k];
   for (int i=0;i<Kljuc.length;i++)
   {
     Kljuc[i]="";
   }
   return Kljuc;
  }
  void setImeTabele(String ime)
  {
    ImeTabele = ime;
  }
  String getImeTabele()
  {
    return ImeTabele;
  }
  void setBrAtributa(int broj)
  {
    BrAtributa = broj;
  }
  int getBrAtributa()
  {
    return BrAtributa;
  }
  void setPKljuc(String kljuc)
  {
    PKljuc = kljuc;
  }

  String getPKljuc()
  {
    return PKljuc;
  }
  void setImenaAtributa(String[] Atributi)
  {
    for (int i=0;i<BrAtributa;i++)
    ImenaAtributa[i]=Atributi[i];
  }
  String[] getImenaAtributa()
  {    
    return ImenaAtributa;
  }
  String getImeAtributa(int m)
  {    
    return ImenaAtributa[m];
  }
  void setImeAtributa(int m,String vrednost)
  {    
    ImenaAtributa[m]=vrednost;
  }
  void setTipPodataka(String[] Tipovi)
  {
    for (int i=0;i<BrAtributa;i++)
    ImenaAtributa[i]=Tipovi[i];
  }
  String[] getTipPodataka()
  {    
    return TipPodataka;
  }
  String getTip(int m)
  {    
    return TipPodataka[m];
  }
  void setTip(int m,String vrednost)
  {    
    TipPodataka[m]=vrednost;
  }
  void setIzraziNiz(String[] Atributi)
  {
    for (int i=0;i<BrAtributa;i++)
    Izrazi[i]=Atributi[i];
  }
  String[] getIzraziNiz()
  {    
    return Izrazi;
  }
  String getIzrazi(int m)
  {    
    return Izrazi[m];
  }
  void setIzrazi(int m,String vrednost)
  {    
    Izrazi[m]=vrednost;
  }
  String getUnique(int m)
  {    
    return Uniqueovi[m];
  }
  void setUnique(int m,String vrednost)
  {    
    Uniqueovi[m]=vrednost;
  }
  
  int[] getNulls()
  {    
    return Nulls;
  }
  int getNullNotNull(int m)
  {    
    return Nulls[m];
  }
  void setNullNotNull(int m,int vrednost)
  {    
    Nulls[m]=vrednost;
  }
  String getDefault(int m)
  {    
    return Defaultovi[m];
  }
  void setDefault(int m,String vrednost)
  {    
    Defaultovi[m]=vrednost;
  }
  String getAtributKljuca(int m)
  {    
    return Kljuc[m];
  }
  void setAtributKljuca(int m,String vrednost)
  {    
    Kljuc[m] = vrednost;
  }

    /*nobrenovic: start*/
    public void setCheckCons(ArrayList<CheckConstraint> checkCons) {
        this.checkCons = checkCons;
    }

    public ArrayList<CheckConstraint> getCheckCons() {
        return checkCons;
    }
    
    public void setAttrChkCons(HashMap<String, ArrayList<CheckConstraint>> attrChkCons) {
        this.attrChkCons = attrChkCons;
    }

    public HashMap<String, ArrayList<CheckConstraint>> getAttrChkCons() {
        return attrChkCons;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getTableId() {
        return tableId;
    }
    /*nobrenovic: stop*/
}
