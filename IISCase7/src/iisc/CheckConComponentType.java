package iisc;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * This class is the object representation of the component type (object type)
 * used in the tranformation of check constraints.
 * 
 * @author      Nikola Obrenovic (nikob at sbb.rs)
 * @version     0.1 20/05/10
 */
public class CheckConComponentType {
    private Connection connection;
    private int formID;
    private int projID;
    private int compID;
    String name;
    private String superord;
    
    Set attributes=new HashSet();
    Set keys=new HashSet();
    Set[] key=new HashSet[0];
    Set descedents=new HashSet();
    Set ascedents=new HashSet();
    
    public CheckConComponentType(){
    }
    
    public CheckConComponentType(int projID, int formID, int compTypeID, Connection conn){
        compID=compTypeID;
        this.formID = formID;
        this.projID = projID;
        connection = conn;
        loadCompType(projID, formID, compID);
    }
     
    public void loadCompType(int projID, int formID, int compID)
    {
        try
        {
            ResultSet rs;
            JDBCQuery query=new JDBCQuery(connection);
            rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TOB_id=" + compID + " AND TF_id=" + formID + " AND PR_id=" + projID);
            if(rs.next()) {
                superord=rs.getString("TOB_superord");
                if(superord==null )superord="-1";
                name = rs.getString("TOB_name");
                getKeys(projID, formID, compID);
                getAttributes(projID, formID, compID);
            }
            query.Close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    private void getKeys(int projID, int formID, int compID)
    {
        try
        {
            ResultSet rs;
            JDBCQuery query=new JDBCQuery(connection);
            rs=query.select("select count(*) from IISC_KEY_TOB where TOB_ID=" + compID + " and TF_id=" + formID + " and PR_id=" + projID );
            rs.next();
            int numKeys=rs.getInt(1);
            query.Close();
            
            key = new Set[numKeys];
            String[] tobKeys = query.selectArray1("select TOB_rbrk from IISC_KEY_TOB where TOB_ID=" + compID + " and TF_id=" + formID 
                                            + " and PR_id=" + projID +" order by TOB_rbrk" ,numKeys,1);
            
            for (int k=0;k<tobKeys.length;k++){
                rs=query.select("select Att_id from IISC_ATT_KTO  where TOB_id=" + compID + " and TF_id=" + formID 
                                            + " and PR_id=" + projID +" and TOB_rbrk="+ tobKeys[k]+" order by Att_rbrk" );
                Set aKey = new HashSet();
                while(rs.next())
                {
                    aKey.add(rs.getString("Att_id"));
                }
                query.Close();
                key[k] = aKey;
                keys.add(aKey);
                
            }
        }
        catch(SQLException e)
        {
         e.printStackTrace();
        }
    }
    
    private void getAttributes(int projID, int formID, int compID)
    {
        try
        {
            ResultSet rs;
            JDBCQuery query=new JDBCQuery(connection);
            rs=query.select("select * from IISC_ATT_TOB  where TOB_id=" + compID + " and TF_id=" + formID 
                                            + " and PR_id=" + projID );
            while(rs.next())
            {
                attributes.add(rs.getString("Att_id"));
            }
            query.Close();
        }
        catch(SQLException e)
        {
         e.printStackTrace();
        }
    }
    
    public void setDescedent(CheckConComponentType desc) {
        descedents.add(desc);
    }
    public void setAscedent(CheckConComponentType asc) {
        ascedents.add(asc);
    }
    public void print()
    {
        print(this);
    }
    public void print(CheckConComponentType comp)
    {
        /*System.out.print("("+comp.name);
        System.out.print(comp.attributes);
        System.out.print(",");
        System.out.print(comp.keys);
        System.out.println(")");
        Iterator it=comp.descedents.iterator();
        Set at=new HashSet();
        while(it.hasNext()){
            CheckConComponentType desc=(CheckConComponentType)it.next();
            desc.print();
        }    */
    }

    public void setFormID(int formID) {
        this.formID = formID;
    }

    public int getFormID() {
        return formID;
    }

    public void setProjID(int projID) {
        this.projID = projID;
    }

    public int getProjID() {
        return projID;
    }

    public void setCompID(int compID) {
        this.compID = compID;
    }

    public int getCompID() {
        return compID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSuperord(String superord) {
        this.superord = superord;
    }

    public String getSuperord() {
        return superord;
    }
}
