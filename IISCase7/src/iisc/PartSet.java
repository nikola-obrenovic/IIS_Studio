package iisc;
import java.util.*;
public class PartSet
{
public Set FunDeps;
public  Set X;
  public PartSet()
  {
  FunDeps=new HashSet();
  X=new HashSet();
  }
   public PartSet(Set a,Set x )
  {
  FunDeps=new HashSet(a);
  X=new HashSet(x);
  }
}