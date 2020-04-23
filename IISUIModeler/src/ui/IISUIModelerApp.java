package ui;

public class IISUIModelerApp {
public static void main (String[] args) {
   
  Settings.lookFeel();
  IISFrameMain IISFrm=new IISFrameMain();
  Settings.Center(IISFrm);
  IISFrm.setVisible(true);
  JDBCDialog Dialog =new JDBCDialog(IISFrm,"IIS*UIModeler Repository",true);

  } 
} 