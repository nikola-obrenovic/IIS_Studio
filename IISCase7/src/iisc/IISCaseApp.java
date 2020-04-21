package iisc;

public class IISCaseApp {
public static void main (String[] args) {
   
  Settings.lookFeel();
  IISFrameMain IISFrm=new IISFrameMain();
  Settings.Center(IISFrm);
  IISFrm.setVisible(true);
  JDBCDialog Dialog =new JDBCDialog(IISFrm,"IIS*CASE Repository",true);

  } 
} 

