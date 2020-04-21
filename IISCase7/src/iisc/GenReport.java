package iisc;
import java.sql.*;
import javax.swing.JOptionPane;

public class GenReport 
{
  private Connection con;
  private int pr_id, as_id, clt_id, rep_id;
  private IISFrameMain owner;
  PTree tree;
  public GenReport(PTree pt)
  {
  tree=pt;
  }
  public void generate(Connection conn, int p_id, int a_id, int t_id)
    { 
    con=conn;
    pr_id=p_id;
    as_id=a_id;
    clt_id=t_id;
 /* JDBCQuery query=new JDBCQuery(con);
    JDBCQuery query1=new JDBCQuery(con);
    JDBCQuery query2=new JDBCQuery(con);
    JDBCQuery query3=new JDBCQuery(con);
    ResultSet rs, rs1, rs2, rs3;  
    String str="",str1="",str2="",str3="",str4="",fus="",fun="",superord="";*/
    
    RepFilter rf= new RepFilter(owner,"",false,con,clt_id,pr_id,as_id,tree);
    Settings.Center(rf);
    rf.setVisible(true);

    }//end generate
    
    public void delSpecific(Connection conn, int r_id) throws SQLException
  {
    rep_id = r_id;
    con = conn;
    JDBCQuery query = new JDBCQuery(con);
    query.update("delete * from IISC_COLLISION_LOG where CL_id=" + rep_id);    
  }
    
    public void delAll(Connection conn, int p_id, int a_id, int t_id) throws SQLException
  {
    pr_id = p_id;
    as_id = a_id;
    clt_id = t_id;
    con = conn;
    JDBCQuery query = new JDBCQuery(con);
    query.update("delete * from IISC_COLLISION_LOG where PR_id=" + pr_id + " AND AS_id=" + as_id + " AND CL_type=" + clt_id + "");
    }
      
    public void delAll(Connection conn, int p_id, int a_id) throws SQLException
  {
    pr_id = p_id;
    as_id = a_id;
    con = conn;
    JDBCQuery query = new JDBCQuery(con);
    query.update("delete * from IISC_COLLISION_LOG where PR_id=" + pr_id + "AND AS_id=" + as_id + "");
    }
      
    public void delObs(Connection conn, int p_id, int a_id, int t_id) throws SQLException
    {
    pr_id = p_id;
    as_id = a_id;
    clt_id = t_id;
    con = conn;
    JDBCQuery query = new JDBCQuery(con);
    query.update("delete * from IISC_COLLISION_LOG where PR_id=" + pr_id + " AND AS_id=" + as_id + " AND CL_type=" + clt_id + " AND CL_obsolete=1");
    }
      
    
    public void delObs(Connection conn, int p_id, int a_id) throws SQLException
    {
    pr_id = p_id;
    as_id = a_id;
    con = conn;
    JDBCQuery query = new JDBCQuery(con);
    query.update("delete from IISC_COLLISION_LOG where PR_id=" + pr_id + " AND AS_id=" + as_id + " AND CL_obsolete=1");
    }
      
    public void view(Connection conn, int p_id, int a_id)
      {
      pr_id=p_id;
      as_id=a_id;
      con=conn;
      Collision rep=new Collision(owner,"",false,con,as_id,tree);
      Settings.Center(rep);
      rep.filter();
      rep.setVisible(true); 
      }
      
    public void view(Connection conn, int p_id, int a_id, int t_id)
      {
      
      clt_id=t_id;
      pr_id=p_id;
      as_id=a_id;
      con=conn;
      JDBCQuery query=new JDBCQuery(con);
      ResultSet rs;  
      String ime = "";
      try
      {
        rs=query.select("select CLT_name from IISC_LOG_TYPE where CLT_id="+ clt_id+"");
        if(rs.next())
        ime=rs.getString(1);
      }
      catch(SQLException e)
      {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
      Collision rep=new Collision(owner,"",false,con,as_id,tree);
      Settings.Center(rep);
      rep.cmbReportType.setSelectedItem(ime);
      rep.filter();
      rep.setVisible(true); 
      }    
      
    
}