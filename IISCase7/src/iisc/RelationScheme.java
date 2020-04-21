package iisc;
import java.util.*;

public class RelationScheme 
{  public Set kljuc=new HashSet();
   public Set lokalni_kljuc=new HashSet();
   public Set primarni_kljuc=new HashSet();
    public Set unique=new HashSet();
     public Set role=new HashSet();
    public Set lokalni_unique=new HashSet();
   public Set kandidati=new HashSet();
   public Set atributi=new HashSet();
    public Set mod_atributi=new HashSet();
     public Set null_atributi=new HashSet();
   public String name=new String();
   public Set S=new HashSet(),S1=new HashSet();
   public int id; 
  public RelationScheme(Set r, Set k)
  {
  kljuc.addAll(k);
  atributi.addAll(r);
  }
  public RelationScheme()
  {

  }
  public void print()
  {
  System.out.print(this.name+"(");
System.out.print(this.atributi);
 // System.out.print(",");
 // System.out.print(this.kljuc);
 // System.out.print(",");
 // System.out.print(this.primarni_kljuc);
 // System.out.print(", ID:" + this.id);
 System.out.println(")");
  }
  
}