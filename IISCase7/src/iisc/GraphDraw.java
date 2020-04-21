package iisc;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.sql.*; 
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
    public class GraphDraw extends JDialog  implements ActionListener {
      private Connection con;
      public int app;
      IISFrameMain owner;
      Set GF=new HashSet();
      Set GFE=new HashSet();
       Set GFEID=new HashSet();
     public String appsys;
     public MyComponent my;
  private JScrollPane jScrollPane1 ;
  private JPanel jPanel1 = new JPanel();
  private JButton btnClose = new JButton();
  private JButton btnHelp = new JButton();
   private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
      GraphDraw(IISFrameMain parent, String title,  Connection conn, int ap ) {
      super((Frame) parent, title, false);
            con=conn;
            app=ap;
            appsys=title;
            
            owner=parent;
            Iterator it=((PTree)((IISFrameMain)owner).desktop.getSelectedFrame()).WindowsManager.iterator();
      while(it.hasNext())
      {
        Object obj=(Object)it.next();
        Class cls=obj.getClass();
        if(cls==this.getClass())
        {if(((GraphDraw)obj).app==app)
        { ((GraphDraw)obj).dispose();
        }
        }
      }
           this.setTitle("Closure Graph");
            
             
          try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
        }
     public GraphDraw()
  {
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }
    public GraphNode getNode(int id)
    {
      Iterator it=GF.iterator();
      while(it.hasNext())
      { GraphNode g=(GraphNode)it.next();
        if(g.id==id) return g;
      }
      return null;
    }
    public GraphNode getNode(int x1,int y1)
    {
      Iterator it=GF.iterator();
      while(it.hasNext())
      { GraphNode g=(GraphNode)it.next();
        if(g.x<=x1 && g.y<=y1 && x1<=(g.x+130) && y1 <=(g.y+50)  ) return g;
      }
      return null;
    }
        class MyComponent extends JPanel {
        int init=0;
            // This method is called whenever the contents needs to be painted
       MyComponent()
        { 
    
      initMycomponent();
            
     }
        public void initMycomponent()  
        {
      Synthesys syn=new Synthesys();
      syn.as=app;
      syn.con=con;
      syn.pr=((PTree)((IISFrameMain)owner).desktop.getSelectedFrame()).ID;
      int k=0;
       while(!syn.graf_nivo(k,-1).isEmpty())
       k=k+1;
       Object[] S=new Object[k];
       int max=0;
       Set pom= syn.getRelationSchemesID();
      for(int i=0;i<k;i++)
      { S[i]=syn.graf_nivo(i,-1);
       pom.removeAll((Set)S[i]);
        if(((Set)S[i]).size()>max)max=((Set)S[i]).size();
      }
      ((Set)S[0]).addAll(pom);
         if(((Set)S[0]).size()>max)max=((Set)S[0]).size();
      
      
                // Retrieve the graphics context; this object is used to paint shapes
                
    
                // Draw an oval that fills the window
                int x = 0;
                int y = 0;
                int k2=2*k;
                Object[] S1=new Object[k2]; 
                int l=0;
                try{
                JDBCQuery query=new JDBCQuery(con);
                ResultSet rs;
              
                for(int i=0;i<k;i++)
                 {//S[i]=syn.graf_nivo(i,-1);
                 Iterator it=((Set)S[i]).iterator();
                 int j=0;
                 Set poms= new HashSet();
                 while(it.hasNext())
                 {String g=it.next().toString();
                  rs=query.select("select *  from IISC_GRAPH_CLOSURE where GC_edge_type=0 and RS_inferior="+ g +" and RS_superior<>"+ g +" and PR_id=" + ((PTree)(owner.desktop.getSelectedFrame())).ID + " and  AS_id=" + app);
                  while(rs.next())
                  {
                  String sup=rs.getString("RS_superior");
                  if(((Set)S[i]).contains(sup))
                  {
                  poms.add(sup);
                  }
                  }
                  query.Close();
                 }
                 S1[l]=new HashSet();
                 ((Set)S1[l]).addAll(syn.Razlika((Set)S[i],poms));
                 l++;
                 if(!poms.isEmpty())
                 {S1[l]=new HashSet();
                  ((Set)S1[l]).addAll(poms);
                  l++; 
                 }
                 }
                 }
                  catch(Exception e)
                  {
                 e.printStackTrace();
                  }
                  S=S1;
                  int od=100;
                  int max1=0;
                for(int i=0;i<l;i++)
                 { 
                 Iterator it=((Set)S[i]).iterator();
                 int j=0;
                 int size=((Set)S[i]).size();
                 while(it.hasNext())
                 { x = ((max-size)/2 +j)*145+20 +od;
                   y = i*120 +20;
                   Integer si=new Integer(it.next().toString());
                   int s=si.intValue();
                   j++;
                   GF.add(new GraphNode(x,y,s));
                   if(x>max1)max1=x;
                 }
                 if(od==100)od=0;
                 else
                 od=100;
                 }
        int height =y+150+30;
       int width = max1 + 30+150;
       this.setBounds(0,0,width,height);
        this.setPreferredSize(new Dimension(width,height));
    
                 
     try{
     JDBCQuery query=new JDBCQuery(con);
     ResultSet rs;
     rs=query.select("select *  from IISC_GRAPH_CLOSURE where PR_id=" + ((PTree)(owner.desktop.getSelectedFrame())).ID + " and  AS_id=" + app);
     while(rs.next())
     {
       int id1,id2,id;
       id=rs.getInt("GC_id");
       id1=rs.getInt("RS_Superior");
       id2=rs.getInt("RS_Inferior");
       GraphNode g1,g2;
       g1=getNode(id1);
       g2=getNode(id2);
       int tp=rs.getInt("GC_edge_type");
       Color color=new Color(0,0,0);
       int typ=0;
       if(tp==2)typ=1;
       if(tp==1)
       {color=new Color(255,0,0);
       GraphEdge gd=new GraphEdge(g1.getTopX(),g1.getTopY(),g2.getBottomX(),g2.getBottomY(),id1,id2,color,id);
       gd.tp=typ;
       GFE.add(gd);
       }
       else if(tp==2)
       {color=new Color(0,0,255);
        GraphEdge gd=new GraphEdge(g1.getBottomX(),g1.getBottomY(),g2.getTopX(),g2.getTopY(),id1,id2,color,id);
       gd.tp=typ;
       GFE.add(gd);
       }
       else
       { 
       GraphEdge gd=new GraphEdge(g1.getBottomX(),g1.getBottomY(),g2.getTopX(),g2.getTopY(),id1,id2,color,id);
       gd.tp=typ;
       GFE.add(gd);
       }
     }
     query.Close();
     }
catch(Exception e)
    {
      e.printStackTrace();
    }
    repaint();
 
        }
          public void paint(Graphics g) {
          g.clearRect(0,0,this.getWidth(),this.getHeight());
          g.setColor(Color.white);
          g.fillRect(0,0,this.getWidth(),this.getHeight());
          Synthesys syn=new Synthesys();
          syn.as=app;
          syn.con=con;
           syn.pr=((PTree)((IISFrameMain)owner).desktop.getSelectedFrame()).ID; 
           Graphics2D g2d = (Graphics2D)g;
            paintComponent(g2d); 
          Iterator it=GFE.iterator();
          while(it.hasNext())
          {GraphEdge gn=(GraphEdge)it.next();
           int tp=gn.tp;
          g2d.setColor(gn.color);
          int br=0;
          if(gn.color.equals(new Color(255,0,0)))
          {br=-10;
          if(gn.y1>gn.y2)
          {g2d.drawLine(gn.x1+br,gn.y1,gn.x2+br,gn.y2);
           GFEID.add(new GraphEdge(gn.x1+br,gn.y1,gn.x2+br,gn.y2,gn.id1,gn.id2,gn.color,gn.id));
           strelica(gn.x2+br,gn.y2,gn.x1+br,gn.y1,g2d,0);
          }
          else if(gn.y1==gn.y2-50 && gn.x1!=gn.x2)
          {
           if(gn.x2>gn.x1)
           {g2d.drawLine(gn.x1+br+65,gn.y1+25,gn.x2+br-65,gn.y2-25);
           GFEID.add(new GraphEdge(gn.x1+br+65,gn.y1+25,gn.x2+br-65,gn.y2-25,gn.id1,gn.id2,gn.color,gn.id));
           strelica(gn.x2+br-65,gn.y2-25,gn.x1+br+65,gn.y1+25,g2d,0);
           }
           else
           {g2d.drawLine(gn.x1+br-65,gn.y1+25,gn.x2+br+65,gn.y2-25);
           GFEID.add(new GraphEdge(gn.x1+br+65,gn.y1+25,gn.x2+br-65,gn.y2-25,gn.id1,gn.id2,gn.color,gn.id));
           strelica(gn.x2+br+65,gn.y2-25,gn.x1+br-65,gn.y1+25,g2d,0);
           }
           
          }
          else
          {
          if(gn.id1==gn.id2){
          g2d.drawLine(gn.x1+br,gn.y1,gn.x1+br ,gn.y1-10);  
          g2d.drawLine(gn.x1+br ,gn.y1-10,gn.x1+br +(gn.x2-gn.x1 +70) ,gn.y1-10); 
          g2d.drawLine(gn.x1+br +(gn.x2-gn.x1 +70),gn.y1-10,gn.x2+br +70 ,gn.y2+10); 
          g2d.drawLine( gn.x2+br +70 ,gn.y2+10,gn.x2+br  ,gn.y2+10); 
          g2d.drawLine( gn.x2+br  ,gn.y2+10,gn.x2+br,gn.y2); 
          strelica(gn.x1+br,gn.y1,gn.x1+br ,gn.y1-10,g2d,1);
          GFEID.add(new GraphEdge(gn.x1+br,gn.y1,gn.x1+br ,gn.y1-10,gn.id1,gn.id2,gn.color,gn.id));
          GFEID.add(new GraphEdge(gn.x1+br ,gn.y1-10,gn.x1+br +(gn.x2-gn.x1 +70) ,gn.y1-10,gn.id1,gn.id2,gn.color,gn.id));
          GFEID.add(new GraphEdge(gn.x1+br +(gn.x2-gn.x1 +70),gn.y1-10,gn.x2+br +70 ,gn.y2+10,gn.id1,gn.id2,gn.color,gn.id));
          GFEID.add(new GraphEdge(gn.x2+br +70 ,gn.y2+10,gn.x2+br  ,gn.y2+10,gn.id1,gn.id2,gn.color,gn.id));
          GFEID.add(new GraphEdge(gn.x2+br  ,gn.y2+10,gn.x2+br,gn.y2,gn.id1,gn.id2,gn.color,gn.id));}
          else 
          {/*g2d.drawLine(gn.x1+br,gn.y1,gn.x2+br,gn.y2);
           GFEID.add(new GraphEdge(gn.x1+br,gn.y1,gn.x2+br,gn.y2,gn.id1,gn.id2,gn.color,gn.id));
          strelica(gn.x1+br ,gn.y1-10,gn.x1+br,gn.y1,g2d,0);*/
          g2d.drawLine(gn.x1+br,gn.y1+50,gn.x2+br,gn.y2-50);
          GFEID.add(new GraphEdge(gn.x1+br,gn.y1+50,gn.x2+br,gn.y2-50,gn.id1,gn.id2,gn.color,gn.id));
          strelica(gn.x2+br,gn.y2-50,gn.x1+br,gn.y1+50,g2d,1);
           } 
          }
          }
          else
          { if(tp==0)
          {g2d.drawLine(gn.x1+br,gn.y1,gn.x2+br,gn.y2); 
          GFEID.add(new GraphEdge(gn.x1+br,gn.y1,gn.x2+br,gn.y2,gn.id1,gn.id2,gn.color,gn.id));
          strelica(gn.x2+br,gn.y2,gn.x1+br,gn.y1,g2d,1);}
          else
          {
          br=20;
         if(gn.y1==gn.y2-50 && gn.x1!=gn.x2)
          {
           if(gn.x2>gn.x1)
           {g2d.drawLine(gn.x1+55,gn.y1+25+br,gn.x2-75,gn.y2-25+br);
           GFEID.add(new GraphEdge(gn.x1+55,gn.y1+25+br,gn.x2-75,gn.y2-25+br,gn.id1,gn.id2,gn.color,gn.id));
           strelica(gn.x1+55,gn.y1+25+br,gn.x2-75,gn.y2-25+br,g2d,1);
           }
           else
           {g2d.drawLine(gn.x1-75,gn.y1+25+br,gn.x2+55,gn.y2-25+br);
           GFEID.add(new GraphEdge(gn.x1-75,gn.y1+25+br,gn.x2+55,gn.y2-25+br,gn.id1,gn.id2,gn.color,gn.id));
           strelica(gn.x1-75,gn.y1+25+br,gn.x2+55,gn.y2-25+br,g2d,1);
           }
          }
          else if(gn.y1==gn.y2+50 && gn.x1!=gn.x2)
          {
           if(gn.x2>gn.x1)
           {g2d.drawLine(gn.x1+55,gn.y1-25+br,gn.x2-75,gn.y2+25+br);
           GFEID.add(new GraphEdge(gn.x1+55,gn.y1-25+br,gn.x2-75,gn.y2+25+br,gn.id1,gn.id2,gn.color,gn.id));
           strelica(gn.x1+55,gn.y1-25+br,gn.x2-75,gn.y2+25+br,g2d,1);
           }
           else
           {g2d.drawLine(gn.x1-75,gn.y1-25+br,gn.x2+55,gn.y2+25+br);
           GFEID.add(new GraphEdge(gn.x1-75,gn.y1-25+br,gn.x2+55,gn.y2+25+br,gn.id1,gn.id2,gn.color,gn.id));
           strelica(gn.x1-75,gn.y1-25+br,gn.x2+55,gn.y2+25+br,g2d,1);
           }}
          else
          {g2d.drawLine(gn.x1+br,gn.y1-50,gn.x2+br,gn.y2+50);
          GFEID.add(new GraphEdge(gn.x1+br,gn.y1-50,gn.x2+br,gn.y2+50,gn.id1,gn.id2,gn.color,gn.id));
          strelica(gn.x2+br,gn.y2+50,gn.x1+br,gn.y1-50,g2d,0);}
          }
          }
          }
          it=GF.iterator();
         
          while(it.hasNext())
          {GraphNode gn=(GraphNode)it.next();
                  g2d.setColor(Color.black);
                  g2d.setColor(new Color(248,218,222));
                  g2d.fill3DRect(gn.x, gn.y, 130, 50,false);
                  g2d.setColor(Color.black);
                  String nm;
                  
                  if(gn.name.length()>21)nm=gn.name.substring(0,20)+"...";
                  else nm=gn.name;
                  g2d.setFont(new Font("SansSerif",0,11));
                  g2d.drawString(nm,gn.x+10, gn.y+30); 
                  Image img=Toolkit.getDefaultToolkit().getImage("help1.gif");
              
          }
        }
     
        }
 public void strelica(int x1,int y1, int x2,int y2,Graphics2D g, int t  )
 {int[] x=new int[3];
  int[] y=new int[3];
   x[0]=x1+1;y[0]=y1+1;
 if(t==0)
 {
 double a=Math.asin(3/Math.sqrt(58));
 double f=(x2-x1)/Math.sqrt((x2-x1)*(x2-x1)+ (y2-y1)*(y2-y1));
  double b=Math.asin((x2-x1)/Math.sqrt((x2-x1)*(x2-x1)+ (y2-y1)*(y2-y1)));
  double c=Math.sin(b-a);
  double d=Math.cos(b-a);
  x[1]=(int)Math.round(c*Math.sqrt(58)) +x1;
  y[1]=(int)Math.round(d*Math.sqrt(58)) +y1;
  c=Math.sin(b+a);
  d=Math.cos(b+a);
  x[2]=(int)Math.round(c*Math.sqrt(58)) +x1;
  y[2]=(int)Math.round(d*Math.sqrt(58)) +y1;
  
 }
 else
 {
  double a=Math.asin(3/Math.sqrt(58));
  double f=(x2-x1)/Math.sqrt((x2-x1)*(x2-x1)+ (y2-y1)*(y2-y1));
  double b=Math.asin((x2-x1)/Math.sqrt((x2-x1)*(x2-x1)+ (y2-y1)*(y2-y1)));
  double c=Math.sin(b-a);
  double d=Math.cos(b-a);
  x[1]=(int)Math.round(c*Math.sqrt(58) +x1);
  y[1]=(int)Math.round(-d*Math.sqrt(58) +y1);
  c=Math.sin(b+a);
  d=Math.cos(b+a);
  x[2]=(int)Math.round(c*Math.sqrt(58) +x1);
  y[2]=(int)Math.round(-d*Math.sqrt(58) +y1);
 }
   g.fillPolygon(x,y,3);
  //g.drawLine(x[0],y[0],x[1],y[1]);  
 // g.drawLine(x[0],y[0],x[2],y[2]);  
 }
  private void jbInit() throws Exception
  { 
//  this.setResizable(false);
    my=new MyComponent();
    my.setEnabled(false);
    this.setBackground(Color.white);
    my.setBackground(Color.white);
    this.setSize(my.getSize());
    jScrollPane1= new JScrollPane();
    this.addFocusListener(new java.awt.event.FocusAdapter()
      {
        public void focusGained(FocusEvent e)
        {
          this_focusGained(e);
        }
      });
    this.addWindowListener(new java.awt.event.WindowAdapter()
      {
        public void windowActivated(WindowEvent e)
        {
          this_windowActivated(e);
        }
      });
    jScrollPane1.getViewport().setBackground(Color.white);
    jScrollPane1.setAutoscrolls(true);
    jScrollPane1.getViewport().setLayout(null);
    btnClose.setMaximumSize(new Dimension(50, 30));
    btnClose.setPreferredSize(new Dimension(70, 30));
    btnClose.setText("Close");
    btnClose.setMinimumSize(new Dimension(50, 30));
    btnClose.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          close_ActionPerformed(ae);
        }
      });
    btnClose.setFont(new Font("SansSerif", 0, 11));
    btnHelp.setIcon(imageHelp);
    btnHelp.setPreferredSize(new Dimension(49, 30));
    btnHelp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnHelp_actionPerformed(e);
        }
      });
      jScrollPane1.getViewport().add(my,null);
    
     my.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          my_mouseClicked(e);
        }
      });
      jPanel1.add(btnClose, null);
    jPanel1.add(btnHelp, null);
   this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
     this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
   
    
  }
 private String getConstraint(int i)
  {     try{
     JDBCQuery query=new JDBCQuery(con);
     ResultSet rs;
     rs=query.select("select *  from IISC_GRAPH_CLOSURE where PR_id=" + ((PTree)(owner.desktop.getSelectedFrame())).ID + " and GC_id=" + i + " and  AS_id=" + app);
     if(rs.next())
     {
       return rs.getString("RSC_id");
        }
     query.Close();
     }
catch(Exception e)
    {
      e.printStackTrace();
    
    }
      return null;
  }
  private void my_mouseClicked(MouseEvent e)
  {
  GraphEdge edge=matched(e.getX(),e.getY());
  if(getNode(e.getX(),e.getY())!=null)
  {
  
    RScheme rsh=new RScheme(owner,this.getTitle(),true,con,(getNode(e.getX(),e.getY())).id,((PTree)(owner.desktop.getSelectedFrame())),appsys);
    rsh.appsys=appsys;
    ((PTree)owner.desktop.getSelectedFrame()).select_node(rsh.Mnem,"Relation Schemes",appsys); 
    Settings.Center(rsh);
    rsh.graph=this;
    rsh.setVisible(true);
  }
  else if(edge!=null)
  {String j= getConstraint(edge.id);
  if(j!=null)
  {Constraint cons=new Constraint((IISFrameMain)getParent(),"",(new Integer(j)).intValue(),true,con,((PTree)((IISFrameMain)owner).desktop.getSelectedFrame()).ID,app);
  Settings.Center(cons);
  cons.setVisible(true);}
  }
     }
    class GraphEdge
    {
    public int x1;
    public int y1;
    public int x2;
    public int y2;
    public int id;
    public int id1;  
    public int id2; 
     public int tp; 
    public Color color;
   public GraphEdge(int xx1,int yx1,int xx2,int yx2,int idx1,int idx2, Color col,int idd)
  {
  x1=xx1;
  y1=yx1;
  id1=idx1;
  x2=xx2;
  y2=yx2;
  id2=idx2;
  color=col;
  id=idd;
  }
    }
    
        class GraphNode
    {
    public int x;
    public int y;
   public int id;  
   public String name;
     public GraphNode(int x1,int y1,int id1)
  {
  x=x1;
  y=y1;
  id=id1;
  Synthesys syn=new Synthesys();
          syn.as=app;
          syn.con=con;
           syn.pr=((PTree)((IISFrameMain)owner).desktop.getSelectedFrame()).ID;
          Iterator it=GF.iterator();
  name=(syn.getRelationScheme(id)).name;
  }
   public int getTopX()
  {
  return (x+75);
  }
    public  int  getTopY()
  {
  return y;
  }
   public  int getBottomX()
  {
   return (x+75);
  }
   public  int getBottomY()
  {
  return (y+50);
  }
    }
  public GraphEdge matched(int x,int y)
  {
  Iterator it =GFEID.iterator();
  while(it.hasNext())
  {GraphEdge edge=(GraphEdge)it.next();
  int br=-10;
  for (int k=-5;k <= 5;k++)
      for (int j=-5;j <=5;j++)
   if(check(edge.x1,edge.y1,edge.x2,edge.y2,x+k,y+j))return edge;
 
  }
 return null;  
  }

public boolean check(int x1, int y1,int x2, int y2,int x, int y)
{
  if(x1!=x2 && y1!=y2 )
  {if(((y2-y1)*x -(x2-x1)*y - x1*(y2-y1)+ y1*(x2-x1)>-4) && ((y2-y1)*x -(x2-x1)*y - x1*(y2-y1)+ y1*(x2-x1)<4) &&(( y1<y2 && x1<x2 && y<=y2 && y>=y1 &&  x<=x2 && x>=x1) || ( y1>y2 && x1<x2 && y>=y2 && y<=y1 &&  x<=x2 && x>=x1) || ( y1<y2 && x1>x2 && y<=y2 && y>=y1 &&  x>=x2 && x<=x1)|| ( y1>y2 && x1>x2 && y>=y2 && y<=y1 &&  x>=x2 && x<=x1)) ) return true;
   else
   return false;
   }
    else if(y1!=y2)
  {
   if (y>=y1 && y<=y2  && x==x2 ) return true; 
   if (y<=y1 && y>=y2  && x==x2 ) return true; 
  }
     else  
  {
   if (x>=x1 && x<=x2  && y==y2 ) return true; 
    if (x<=x1 && x>=x2  && y==y2 ) return true; 
  }
  return false;
}
  private void this_windowActivated(WindowEvent e)
  {
  }

  private void close_ActionPerformed(ActionEvent e)
  {
  dispose();
  }
public void actionPerformed(ActionEvent e)
  {
  }
  private void btnHelp_actionPerformed(ActionEvent e)
  {Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, con );
 Settings.Center(hlp);
 hlp.setVisible(true);
  }

  private void this_focusGained(FocusEvent e)
  { 
  }
  public void refreshpaint() 
  {
  this.setVisible(false);
  jScrollPane1.getViewport().remove(my);
   my=new MyComponent();
    my.setEnabled(false);
    my.setBackground(Color.white);
     my.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          my_mouseClicked(e);
        }
      });
  ((PTree)owner.desktop.getSelectedFrame()).select_node("Closure Graph",appsys);
  jScrollPane1.getViewport().add(my,null);
  this.setVisible(true);
  } 
  }

      
 
    
 
