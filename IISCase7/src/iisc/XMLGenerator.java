package iisc;

import java.sql.*;
import java.io.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.XMLReader;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.XMLReaderFactory;
import java.util.*;
import org.apache.xerces.parsers.XML11Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.transform.stream.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.WindowEvent;

public class XMLGenerator 
{
    static final String JAXP_SCHEMA_LANGUAGE =
        "http://java.sun.com/xml/jaxp/properties/schemaLanguage";	
    static final String W3C_XML_SCHEMA =
        "http://www.w3.org/2001/XMLSchema"; 
    static final String JAXP_SCHEMA_SOURCE =
        "http://java.sun.com/xml/jaxp/properties/schemaSource";
        
    private Connection conn;
    private Document doc;
    
    public XMLGenerator(Connection con)
    {
        try
        {
            conn = con;
        }
        catch (Exception e) 
        {
            System.out.print(e.toString());
        }
    }
  
    //This procedure format data about form type into XML document, based on given primary id 
    //and repository
    public String GenerateFormTypeXMLDesc(int[] formTypeId)
    { 
        try 
        {
            //create document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);   
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.newDocument();
    
            CreateFormTypeSpecNode(formTypeId, 1);
            
            return WriteXml(doc);
        }
      
        catch (Exception e) 
        {
            System.out.print(e.toString());
            return WriteXml(doc);
        }
    }
    
    public String GenerateAppSysXMLDesc(int As_id)
    { 
        try 
        {
            //create document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);   
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.newDocument();
    
            CreateAppSysSpecNode(As_id, 1);
            
            return WriteXml(doc);
        }
        catch (Exception e) 
        {
            System.out.print(e.toString());
            return WriteXml(doc);
        }
    }
    
    public String GenerateAppSysXMLDesc(int As_id, int ownedTF[], int referencedTF[])
    { 
        try 
        {
            //create document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);   
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.newDocument();
    
            CreateAppSysSpecNode(As_id, 1, ownedTF, referencedTF);
            
            return WriteXml(doc);
        }
        catch (Exception e) 
        {
            System.out.print(e.toString());
            return WriteXml(doc);
        }
    }
    
    private void CreateAppSysSpecNode(int As_id,int tabCount)
    {
        try
        {
            Statement statement;
            ResultSet resultSet;
            
            Element appSysSpecNode = (Element) doc.createElement("app_system_spec"); 
            appSysSpecNode.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
            doc.appendChild(appSysSpecNode);
            
            CreateAppSystemNode(appSysSpecNode, As_id, tabCount + 1);
            
            statement = conn.createStatement();
            //resultSet = statement.executeQuery("select distinct Att_id from IISC_TF_APPSYS,IISC_ATT_TOB where IISC_TF_APPSYS.Tf_id=IISC_ATT_TOB.Tf_id and IISC_TF_APPSYS.AS_id" + As_id);
            
            resultSet = statement.executeQuery("select distinct Att_id from IISC_ATT_TOB where Tf_id in (select Tf_id from IISC_TF_APPSYS where AS_id=" + As_id + ") or Tf_id in (select Tf_id from IISC_APP_SYS_REFERENCE where AS_id=" + As_id + ")");
            
            while (resultSet.next())
            {
                AddAttributeNode(appSysSpecNode, resultSet.getInt("Att_id"), tabCount + 1);
            }
            resultSet.close();
            statement.close();
            
            appSysSpecNode.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
            
            
        }
        catch (Exception e) 
        {
             System.out.print(e.toString());
        }
    }
    
    
    private void CreateAppSysSpecNode(int As_id,int tabCount, int ownedTF[], int referencedTF[])
    {
        try
        {
            Statement statement;
            ResultSet resultSet;
            
            Element appSysSpecNode = (Element) doc.createElement("app_system_spec"); 
            appSysSpecNode.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
            doc.appendChild(appSysSpecNode);
            
            CreateAppSystemNode(appSysSpecNode, As_id, tabCount + 1,ownedTF, referencedTF);
            
            statement = conn.createStatement();
            //resultSet = statement.executeQuery("select distinct Att_id from IISC_TF_APPSYS,IISC_ATT_TOB where IISC_TF_APPSYS.Tf_id=IISC_ATT_TOB.Tf_id and IISC_TF_APPSYS.AS_id" + As_id);
            
            resultSet = statement.executeQuery("select distinct Att_id from IISC_ATT_TOB where Tf_id in (select Tf_id from IISC_TF_APPSYS where AS_id=" + As_id + ") or Tf_id in (select Tf_id from IISC_APP_SYS_REFERENCE where AS_id=" + As_id + ")");
            
            while (resultSet.next())
            {
                AddAttributeNode(appSysSpecNode, resultSet.getInt("Att_id"), tabCount + 1);
            }
            resultSet.close();
            statement.close();
            
            appSysSpecNode.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
            
            
        }
        catch (Exception e) 
        {
             System.out.print(e.toString());
        }
    }
    
    
    private void CreateAppSysSpecNode(Element parent, int As_id,int tabCount)
    {
        try
        {
            Statement statement;
            ResultSet resultSet;
            
            Element appSysSpecNode = (Element) doc.createElement("app_system_spec"); 
            parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
            parent.appendChild(appSysSpecNode);
            
            CreateAppSystemNode(appSysSpecNode, As_id, tabCount + 1);
            
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select distinct Att_id from IISC_ATT_TOB where Tf_id=" + As_id);
            
            while (resultSet.next())
            {
                AddAttributeNode(appSysSpecNode, resultSet.getInt("Att_id"), tabCount + 1);
            }
            
            appSysSpecNode.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
            resultSet.close();
            statement.close();
            
        }
        catch (Exception e) 
        {
             System.out.print(e.toString());
        }
    }
    
    private void CreateFormTypeSpecNode(int[] formTypeId,int tabCount)
    {
        try
        {
            int leng = formTypeId.length;
            Statement statement;
            ResultSet resultSet;
            
            Element formTypeSpecNode = (Element) doc.createElement("form_type_spec"); 
            formTypeSpecNode.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
            doc.appendChild(formTypeSpecNode);
            
            for(int i = 0; i < leng; i++)
            {
                CreateFormTypeNode(formTypeSpecNode, formTypeId[i], tabCount + 1);
            } 
            
            String query = "select distinct Att_id from IISC_ATT_TOB where  Tf_id=" + formTypeId[0];
            
            for(int i = 1; i < leng; i++)
            {
                query = query + " or Tf_id=" + formTypeId[i];
            } 
            
            statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next())
            {
                AddAttributeNode(formTypeSpecNode, resultSet.getInt("Att_id"), tabCount + 1);
            }
            
            formTypeSpecNode.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
            resultSet.close();
            statement.close();
        }
        catch (Exception e) 
        {
             System.out.print(e.toString());
        }
    }
    
    
    
    
    private void CreateAppSystemNode(Element parent,int As_id, int tabCount)
    {
        try
        {    
            //use constants
            Statement statement, statement2;
            ResultSet resultSet, resultSet2;
            Element appSystemNode = (Element) doc.createElement("application_system");
            parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
            parent.appendChild(appSystemNode);
            
            //execute query in order to provide information about attributes
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select * from IISC_APP_SYSTEM where As_id=" + As_id);
            
            if (resultSet.next())
            {
                appSystemNode.setAttribute("name", resultSet.getString("AS_name"));
                appSystemNode.setAttribute("description", resultSet.getString("AS_desc"));
                
                int As_type = resultSet.getInt("AS_type_id");
               
                statement2 = conn.createStatement();
                resultSet2 = statement2.executeQuery("select AS_type from IISC_APP_SYSTEM_TYPE where AS_type_id=" + As_type);
                
                if (resultSet2.next())
                {
                    appSystemNode.setAttribute("type", resultSet2.getString("AS_type"));
                }
                
                int childCount = 0;
                
                resultSet2.close();
                statement2.close();
                
                statement2 = conn.createStatement();
                resultSet2 = statement2.executeQuery("select AS_id_con from IISC_APP_SYSTEM_CONTAIN where AS_id=" + As_id);
                
                
                Element childAppSysNode = (Element) doc.createElement("child_app_systems"); 
                appSystemNode.appendChild(doc.createTextNode("\n" + tab(tabCount)));
                appSystemNode.appendChild(childAppSysNode);
                
                while (resultSet2.next())
                {
                    CreateAppSysSpecNode(childAppSysNode, resultSet2.getInt("AS_id_con"), tabCount + 2);
                    childCount = childCount + 1;
                }
              
                resultSet2.close();
                statement2.close();
                statement2 = conn.createStatement();
                resultSet2 = statement2.executeQuery("select Tf_id from IISC_TF_APPSYS where AS_id=" + As_id);
                
                Element formTypesNode = (Element) doc.createElement("form_types"); 
                appSystemNode.appendChild(doc.createTextNode("\n" + tab(tabCount)));
                appSystemNode.appendChild(formTypesNode);
                
                Element ownedFormTypesNode = (Element) doc.createElement("owned_form_types"); 
                formTypesNode.appendChild(doc.createTextNode("\n" + tab(tabCount + 1)));
                formTypesNode.appendChild(ownedFormTypesNode);
                
                childCount = 0;
                
                while (resultSet2.next())
                {
                    CreateFormTypeNode(ownedFormTypesNode, resultSet2.getInt("Tf_id"), tabCount + 3);
                    childCount = childCount + 1;
                }
               
                ownedFormTypesNode.appendChild(doc.createTextNode("\n" + tab(tabCount + 1)));
                
                resultSet2.close();
                statement2.close();
                statement2 = conn.createStatement();
                resultSet2 = statement2.executeQuery("select Tf_id from IISC_APP_SYS_REFERENCE where AS_id=" + As_id);
                
                Element referencedFormTypesNode = (Element) doc.createElement("referenced_form_types"); 
                formTypesNode.appendChild(doc.createTextNode("\n" + tab(tabCount + 1)));
                formTypesNode.appendChild(referencedFormTypesNode);
                    
                childCount = 0;
                
                while (resultSet.next())
                {
                    CreateFormTypeNode(referencedFormTypesNode, resultSet2.getInt("Tf_id"), tabCount + 3);
                    childCount = childCount + 1;
                }
                resultSet2.close();
                statement2.close();
                referencedFormTypesNode.appendChild(doc.createTextNode("\n" + tab(tabCount + 1)));
                formTypesNode.appendChild(doc.createTextNode("\n" + tab(tabCount)));
            }
            resultSet.close();
            statement.close();
            
            appSystemNode.appendChild(doc.createTextNode("\n" + tab(tabCount -1)));
        }
        catch (Exception e) 
        {
            System.out.println(e.toString());
        }
    }
    
    private void CreateAppSystemNode(Element parent,int As_id, int tabCount, int ownedTF[], int referencedTF[])
    {
        try
        {    
            //use constants
            Statement statement, statement2;
            ResultSet resultSet, resultSet2;
            Element appSystemNode = (Element) doc.createElement("application_system");
            parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
            parent.appendChild(appSystemNode);
            
            //execute query in order to provide information about attributes
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select * from IISC_APP_SYSTEM where As_id=" + As_id);
            
            if (resultSet.next())
            {
                appSystemNode.setAttribute("name", resultSet.getString("AS_name"));
                appSystemNode.setAttribute("description", resultSet.getString("AS_desc"));
                
                int As_type = resultSet.getInt("AS_type_id");
               
                statement2 = conn.createStatement();
                resultSet2 = statement2.executeQuery("select AS_type from IISC_APP_SYSTEM_TYPE where AS_type_id=" + As_type);
                
                if (resultSet2.next())
                {
                    appSystemNode.setAttribute("type", resultSet2.getString("AS_type"));
                }
                
                int childCount = 0;
                
                resultSet2.close();
                statement2.close();
                
                statement2 = conn.createStatement();
                resultSet2 = statement2.executeQuery("select AS_id_con from IISC_APP_SYSTEM_CONTAIN where AS_id=" + As_id);
                
                
                Element childAppSysNode = (Element) doc.createElement("child_app_systems"); 
                appSystemNode.appendChild(doc.createTextNode("\n" + tab(tabCount)));
                appSystemNode.appendChild(childAppSysNode);
                
                while (resultSet2.next())
                {
                    CreateAppSysSpecNode(childAppSysNode, resultSet2.getInt("AS_id_con"), tabCount + 2);
                    childCount = childCount + 1;
                }
                childAppSysNode.appendChild(doc.createTextNode("\n" + tab(tabCount)));
                resultSet2.close();
                statement2.close();
                
                Element formTypesNode = (Element) doc.createElement("form_types"); 
                appSystemNode.appendChild(doc.createTextNode("\n" + tab(tabCount)));
                appSystemNode.appendChild(formTypesNode);
                
                Element ownedFormTypesNode = (Element) doc.createElement("owned_form_types"); 
                formTypesNode.appendChild(doc.createTextNode("\n" + tab(tabCount + 1)));
                formTypesNode.appendChild(ownedFormTypesNode);
                
                int len = ownedTF.length;
                int i = 0;
                
                for (i = 0; i < len; i++)
                {
                    CreateFormTypeNode(ownedFormTypesNode, ownedTF[i], tabCount + 3);
                }
               
                ownedFormTypesNode.appendChild(doc.createTextNode("\n" + tab(tabCount + 1)));
                
                Element referencedFormTypesNode = (Element) doc.createElement("referenced_form_types"); 
                formTypesNode.appendChild(doc.createTextNode("\n" + tab(tabCount + 1)));
                formTypesNode.appendChild(referencedFormTypesNode);
                
                len = referencedTF.length;
                
                for (i = 0; i < len; i++)
                {
                    CreateFormTypeNode(referencedFormTypesNode, referencedTF[i], tabCount + 3);
                    childCount = childCount + 1;
                }
                referencedFormTypesNode.appendChild(doc.createTextNode("\n" + tab(tabCount + 1)));
                formTypesNode.appendChild(doc.createTextNode("\n" + tab(tabCount)));
            }
            resultSet.close();
            statement.close();
            
            appSystemNode.appendChild(doc.createTextNode("\n" + tab(tabCount -1)));
        }
        catch (Exception e) 
        {
            System.out.println(e.toString());
        }
    }
    
    
    private void CreateFormTypeNode(Element parent, int formTypeId, int tabCount)
    {
        try
        {
            Statement statement, statement2;
            ResultSet resultSet, resultSet2;
            //use constants 
            String[] useConstants = {"CONSIDERED_IN_DB_DESIGN", "NOT_CONSIDERED_IN_DB_DESIGN", "MENU" };
            //create root element
            Element formTypeNode = (Element) doc.createElement("form_type"); 
            parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
            parent.appendChild(formTypeNode);
            
            //execute query in order to provide information about attributes
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select * from IISC_FORM_TYPE where Tf_id=" + formTypeId);
            
            if (resultSet.next())
            {
                formTypeNode.setAttribute("name", resultSet.getString("Tf_mnem"));
                formTypeNode.setAttribute("title", resultSet.getString("Tf_title"));
                formTypeNode.setAttribute("type", useConstants[resultSet.getInt("Tf_use")]);
                formTypeNode.setAttribute("frequency", resultSet.getString("Tf_freq"));
                formTypeNode.setAttribute("frequency_unit", resultSet.getString("Tf_freq_unit"));
                formTypeNode.setAttribute("response", resultSet.getString("Tf_rest"));
                formTypeNode.setAttribute("response_unit", resultSet.getString("Tf_rest_unit"));
             }
             
             resultSet.close();
             statement.close();
             statement = conn.createStatement();
             resultSet = statement.executeQuery("select Tob_id,Tob_superord from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tf_id=" + formTypeId);
             
             while (resultSet.next())
             {
                String superOrd = resultSet.getString("Tob_superord");
                
                if (superOrd == null)
                {
                    AddComponentTypeNodes(formTypeNode, formTypeId,resultSet.getInt("Tob_id"), tabCount + 1, true);
                    formTypeNode.appendChild(doc.createTextNode("\n"));
                    break;
                }
             }
             resultSet.close();
             statement.close();
              
             formTypeNode.appendChild(doc.createTextNode(tab(tabCount -1)));
        }
        catch (Exception e) 
        {
            System.out.println(e.toString());
        }
    }
    
    private void AddComponentTypeNodes(Element parent, int formTypeId, int Tob_id, int tabCount, boolean first)
    {
        try
        {
            int childCount = 0;
            //declate resultsets
            Statement statement;
            ResultSet resultSet;
            
            Element componentTypeNode = doc.createElement("component_type");
            parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
            parent.appendChild(componentTypeNode);
            
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tob_id=" + Tob_id);
            
            if (resultSet.next())
            {
                componentTypeNode.setAttribute("name", resultSet.getString("Tob_mnem"));
                componentTypeNode.setAttribute("title", resultSet.getString("Tob_name"));
                if (first)
                {
                    componentTypeNode.setAttribute("mandatory", "yes");
                }
                else
                {
                    if (resultSet.getInt("Tob_mandatory") == 0)
                    {
                        componentTypeNode.setAttribute("mandatory", "no");
                    }

                    else
                    {   
                        componentTypeNode.setAttribute("mandatory", "yes");
                    }
                }
            }
            
            componentTypeNode.appendChild(doc.createTextNode(tab(tabCount -1)));
             
            String value = resultSet.getString("Tob_check");
           
            resultSet.close();
            statement.close();
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select Tob_id from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tf_id=" + formTypeId + " and Tob_superord=" + Tob_id);
            
            while (resultSet.next())
            {
                AddComponentTypeNodes(componentTypeNode, formTypeId, resultSet.getInt("Tob_id"), tabCount + 1, false);
                childCount = childCount + 1;
            }
            resultSet.close();
            statement.close();
            
            componentTypeNode.appendChild(doc.createTextNode("\n" + tab(tabCount)));
            
            CreateBehaviourNodeForTob(componentTypeNode, Tob_id, tabCount + 1);
            //componentTypeNode.appendChild(doc.createTextNode("\n" + tab(tabCount)));
            
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select Att_id from IISC_ATT_TOB where  Tob_id=" + Tob_id + " order by W_order");
            
            childCount = 0;
           
            while (resultSet.next())
            {
                AddCtAttribNodes(componentTypeNode , resultSet.getInt("Att_id"), Tob_id, tabCount + 1);
                childCount = childCount + 1;
            }
            resultSet.close();
            statement.close();
            //componentTypeNode.appendChild(doc.createTextNode("\n" + tab(tabCount)));
            
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select Tob_rbrk from IISC_KEY_TOB where  Tob_id=" + Tob_id);
            
            childCount = 0;
           
            while (resultSet.next())
            {
                AddKeyNode(componentTypeNode , resultSet.getInt("Tob_rbrk"), Tob_id, tabCount + 1);
                childCount = childCount + 1;
            }
            resultSet.close();
            statement.close();
            
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select Tob_rbrk from IISC_UNIQUE_TOB where  Tob_id=" + Tob_id);
            
            childCount = 0;
           
            while (resultSet.next())
            {
                AddUniqueNode(componentTypeNode , resultSet.getInt("Tob_rbrk"), Tob_id, tabCount + 1);
                childCount = childCount + 1;
            }
            resultSet.close();
            statement.close();
            
             if (value == null)
            {
                CreateCheckConstraintNode(componentTypeNode, tabCount + 1, "");
            }
            else
            {
                CreateCheckConstraintNode(componentTypeNode, tabCount + 1, value);
            }
            
            componentTypeNode.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
            
        }
        catch (Exception e) 
        {
             System.out.print(e.toString());
        }
    }
    
    private void AddAttributeNode(Element parent, int Att_id, int tabCount)
    {
        try
        {
            int childCount = 0;
            Statement statement, statement2;
            ResultSet resultSet, resultSet2;
            //declate resultsets
            Element attributeNode = doc.createElement("attribute");
            parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
            parent.appendChild(attributeNode);
            
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select * from IISC_ATTRIBUTE where Att_id =" + Att_id);
            
            if (resultSet.next())
            {
                AddDomainNode(attributeNode, resultSet.getInt("Dom_id"), tabCount + 1);
                
                int renamedID = resultSet.getInt("Att_elem");
                if (renamedID > 0)
                {
                    CreateRenamedNode(attributeNode, renamedID, tabCount + 1);  
                }
                
                attributeNode.setAttribute("name", resultSet.getString("Att_mnem"));
                attributeNode.setAttribute("description", resultSet.getString("Att_name"));
                attributeNode.setAttribute("default", resultSet.getString("Att_default"));
                attributeNode.setAttribute("expression", resultSet.getString("Att_expr"));
                
                String fun = resultSet.getString("Fun_id");
                
                if (fun != null)
                {
                    statement2 = conn.createStatement();
                    resultSet2 = statement2.executeQuery("select Fun_name from IISC_FUNCTION  where Fun_id =" + fun);
                    
                    if (resultSet2.next())
                        attributeNode.setAttribute("query_function", resultSet2.getString("Fun_name"));
                        
                    resultSet2.close();
                    statement2.close();
                }
                
                if (resultSet.getInt("Att_sbp") == 0)
                {
                    attributeNode.setAttribute("exists_in_DB", "no");
                }
                else
                {
                    attributeNode.setAttribute("exists_in_DB", "yes");
                }
                
                if (resultSet.getInt("Att_der") == 0)
                {
                    attributeNode.setAttribute("derived", "no");
                }
                else
                {
                    attributeNode.setAttribute("derived", "yes");
                    
                    statement2 = conn.createStatement();
                    resultSet2 = statement2.executeQuery("select DA_id,Att_id_derived from IISC_DERIVED_ATTRIBUTE where Att_id =" + Att_id);
                    
                    while(resultSet2.next())
                    {
                        AddDerivedNode(attributeNode, resultSet2.getInt("DA_id"), resultSet.getInt("Att_id_derived"), tabCount + 1);
                    }
                    resultSet2.close();
                    statement2.close();
                }
            }
            resultSet.close();
            statement.close();
            
            attributeNode.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
        }
        catch (Exception e) 
        {
            System.out.print(e.toString());
        }
    }
    
    private void AddDerivedNode(Element parent,int DA_id, int Att_id, int tabCount)
    {
       try
        {
            int childCount = 0;
            Statement statement, statement2;
            ResultSet resultSet, resultSet2;
            //declate resultsets
            statement = conn.createStatement();
            
            Element derivedNode = doc.createElement("derived");
            //derivedNode.appendChild(doc.createTextNode("\n" + tab(tabCount)));
            parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
            parent.appendChild(derivedNode);
            
            resultSet = statement.executeQuery("select Att_mnem from IISC_ATTRIBUTE where Att_id=" + Att_id);
            
            if (resultSet.next())
            {
                derivedNode.setAttribute("from_attribute", resultSet.getString("Att_mnem"));
            }
            resultSet.close();
            statement.close();
            
            AddModeNodes(derivedNode, DA_id, tabCount + 1);
            
        }
        catch (Exception e) 
        {
            System.out.print(e.toString());
        }
    }
    
    private void AddModeNodes(Element parent,int DA_id, int tabCount)
    {
       try
        {
            int childCount = 0;
            Statement statement, statement2;
            ResultSet resultSet, resultSet2;
            //Declare result seet
            //declate resultsets
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select Fun_id, DAF_MODE from IISC_DER_ATT_FUN where DA_id=" + DA_id);
            
            while (resultSet.next())
            {
                Element modeNode = doc.createElement("mode");
                parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
                parent.appendChild(modeNode);
                modeNode.setAttribute("type", resultSet.getString("DAF_mode"));
                CreateFunctionNode(modeNode, resultSet.getInt("Fun_id"), tabCount);
                modeNode.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
                childCount = childCount + 1;
            }
            resultSet.close();
            statement.close();
            
            if (childCount > 0)
            {
                parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 2)));
            }
            
        }
        catch (Exception e) 
        {
            System.out.print(e.toString());
        }
    }
    private void AddUniqueNode(Element parent, int Tob_rbrk, int Tob_id, int tabCount)
    {
        try
        {
            int childCount = 0;
            Statement statement, statement2;
            ResultSet resultSet, resultSet2;
            //Declare result seet
            statement = conn.createStatement();
            
            Element keyNode = doc.createElement("unique");
            keyNode.setAttribute("unique_no",Integer.toString(Tob_rbrk));
            resultSet = statement.executeQuery("select Att_id from IISC_ATT_UTO where  Tob_id=" + Tob_id + " and Tob_rbrk=" + Tob_rbrk);
            
            while (resultSet.next())
            {
                AddCtAttribNodes(keyNode, resultSet.getInt("Att_id"), Tob_id, tabCount + 1);
                childCount = childCount + 1;
            }
            
            resultSet.close();
            statement.close();
            
            if (childCount  > 0) 
            {
                parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
                parent.appendChild(keyNode);
            }
            
            keyNode.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
          
        }
        catch (Exception e) 
        {
            System.out.print(e.toString());
        }
    }
    
    
    private void AddDomainNode(Element parent, int Dom_id, int tabCount)
    {
        try
        {
            //Declare result seet
            Statement statement, statement2;
            ResultSet resultSet, resultSet2;
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select * from IISC_DOMAIN where  Dom_id=" + Dom_id);
            
            if (resultSet.next())
            {
                Element domainNode = doc.createElement("domain");
                parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
                parent.appendChild(domainNode);
                
                //set domain atributes
                domainNode.setAttribute("name", resultSet.getString("Dom_mnem"));
                domainNode.setAttribute("description", resultSet.getString("Dom_name"));
                domainNode.setAttribute("default", resultSet.getString("Dom_default"));
                domainNode.setAttribute("expression", resultSet.getString("Dom_reg_exp_str"));
                Element userDefDomainNode = doc.createElement("user_defined_domain");
                
                int domainType = resultSet.getInt("Dom_type");
                domainNode.appendChild(doc.createTextNode("\n" + tab(tabCount)));
                domainNode.appendChild(userDefDomainNode);
                   
                switch(domainType)
                {
                  case 0:
                      userDefDomainNode.appendChild(doc.createTextNode("\n" + tab(tabCount + 1)));
                      Element inheritanceDomainNode = doc.createElement("inheritance_domain");
                      userDefDomainNode.appendChild(inheritanceDomainNode);
                      inheritanceDomainNode.setAttribute("from","primitive");
                      
                      String length = "" ,places = "";
                
                      places = resultSet.getString("Dom_decimal");
                      length = resultSet.getString("Dom_length");
                      
                      statement2 = conn.createStatement();
                      resultSet2 = statement2.executeQuery("select PT_mnemonic from IISC_PRIMITIVE_TYPE where  PT_id=" + resultSet.getInt("Dom_data_type"));
                      
                      if (resultSet2.next())
                      {
                          inheritanceDomainNode.setAttribute("parent", resultSet2.getString("PT_mnemonic"));
                          if (length != null)
                          {
                              inheritanceDomainNode.setAttribute("length", length);
                          }
                          
                          if (places != null)
                          {
                              inheritanceDomainNode.setAttribute("decimal_places", places);
                          }
                      }
                      resultSet2.close();
                      statement2.close();
                      userDefDomainNode.appendChild(doc.createTextNode("\n" + tab(tabCount)));
                      
                      break;
                  case 1:
                      userDefDomainNode.appendChild(doc.createTextNode("\n" + tab(tabCount + 1)));
                      inheritanceDomainNode = doc.createElement("inheritance_domain");
                      userDefDomainNode.appendChild(inheritanceDomainNode);
                      inheritanceDomainNode.setAttribute("from","user_defined");
                      
                      statement2 = conn.createStatement();
                      resultSet2 = statement2.executeQuery("select Dom_mnem from IISC_DOMAIN where  Dom_id=" + resultSet.getInt("Dom_parent"));
                      
                      if (resultSet2.next())
                      {
                          inheritanceDomainNode.setAttribute("parent", resultSet2.getString("Dom_mnem"));
                      }
                      resultSet2.close();
                      statement2.close();
                      userDefDomainNode.appendChild(doc.createTextNode("\n" + tab(tabCount)));
                      
                      break;
                  case 2:
                      userDefDomainNode.appendChild(doc.createTextNode("\n" + tab(tabCount + 1)));
                      Element complexDomainNode = doc.createElement("complex_domain");
                      userDefDomainNode.appendChild(complexDomainNode);
                      CreateTupleDomainNode(complexDomainNode, Dom_id, tabCount + 3);
                      complexDomainNode.appendChild(doc.createTextNode("\n" + tab(tabCount + 1)));
                      userDefDomainNode.appendChild(doc.createTextNode("\n" + tab(tabCount )));
                      break;
                  case 3:
                      userDefDomainNode.appendChild(doc.createTextNode("\n" + tab(tabCount + 1)));
                      complexDomainNode = doc.createElement("complex_domain");
                      userDefDomainNode.appendChild(complexDomainNode);
                      CreateChoiceDomainNode(complexDomainNode, Dom_id, tabCount + 3);
                      complexDomainNode.appendChild(doc.createTextNode("\n" + tab(tabCount + 1)));
                      userDefDomainNode.appendChild(doc.createTextNode("\n" + tab(tabCount)));
                      break;
                  case 4:
                      userDefDomainNode.appendChild(doc.createTextNode("\n" + tab(tabCount + 1)));
                      complexDomainNode = doc.createElement("complex_domain");
                      userDefDomainNode.appendChild(complexDomainNode);
                      
                      String memberDom = resultSet.getString("Dom_parent");
                      String memberType = "user_defined";
                      if (memberDom == null)
                      {
                          memberType = "primitive";
                          memberDom = resultSet.getString("Dom_data_type");
                      }
                      
                      CreateSetDomainNode(complexDomainNode, memberType, memberDom, tabCount + 3);
                      complexDomainNode.appendChild(doc.createTextNode("\n" + tab(tabCount + 1)));
                      userDefDomainNode.appendChild(doc.createTextNode("\n" + tab(tabCount)));
                      break;
                }
                domainNode.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
            }
            resultSet.close();
            statement.close();
        }
        catch (Exception e) 
        {
            System.out.print( e.toString());
        }
    }
    private void CreatePrimitiveDomainNode(Element parent, int PT_id, int tabCount)
    {
        try
        { 
            Statement statement;
            ResultSet resultSet;
             //Declare result seet
            statement = conn.createStatement();
            int childCount = 0;
            
            resultSet = statement.executeQuery("select PT_mnemonic from IISC_PRIMITIVE_TYPE where  PT_id=" + PT_id);
            
            if (resultSet.next())
            {
                Element primitiveTypeNode = doc.createElement("primitive_domain");
                parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
                parent.appendChild(primitiveTypeNode);
                primitiveTypeNode.setAttribute("type", resultSet.getString("PT_mnemonic"));
            }
            resultSet.close();
            statement.close();
        }
        catch (Exception e) 
        {
            System.out.print(e.toString());
        }
    }
    
    private void CreateRenamedNode(Element parent, int Att_id, int tabCount)
    {
        try
        {
            int childCount = 0;
            //Declare result seet
            Statement statement;
            ResultSet resultSet;
            
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select Att_mnem from IISC_ATTRIBUTE where  Att_id=" + Att_id);
            
            if (resultSet.next())
            {
                Element renamedNode = doc.createElement("renamed");
                parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
                parent.appendChild(renamedNode);
                renamedNode.setAttribute("from_attribute", resultSet.getString("Att_mnem"));
            }
            resultSet.close();
            statement.close();
        }
        catch (Exception e) 
        {
            System.out.print(e.toString());
        }
    }
    
    private void CreateUserDefinedDomainNode(Element parent, int parentId, int tabCount)
    {
        try
        {
            int childCount = 0;
            //Declare result seet
            Statement statement;
            ResultSet resultSet;
            
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select Dom_mnem from IISC_DOMAIN where  Dom_id=" + parentId);
            
            if (resultSet.next())
            {
                Element userDefinedTypeNode = doc.createElement("user_defined_domain");
                parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
                parent.appendChild(userDefinedTypeNode);
                userDefinedTypeNode.setAttribute("parent", resultSet.getString("Dom_mnem")); 
                userDefinedTypeNode.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
            }
            resultSet.close();
            statement.close();
        }
        catch (Exception e) 
        {
            System.out.print(e.toString());
        }
    }
    
    private void CreateTupleDomainNode(Element parent, int Dom_id, int tabCount)
    {
        try
        {
            int childCount = 0;
            Statement statement, statement2;
            ResultSet resultSet, resultSet2;
            Element tupleDomainNode = doc.createElement("tuple_domain");
            parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
            parent.appendChild(tupleDomainNode);
            
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select * from IISC_DOM_ATT where  Dom_id=" + Dom_id);
            
            Element attribNode;
            
            while (resultSet.next())
            {
                attribNode = doc.createElement("attrib");
                tupleDomainNode.appendChild(doc.createTextNode("\n" + tab(tabCount)));
                tupleDomainNode.appendChild(attribNode);
                
                statement2 = conn.createStatement();
                resultSet2 = statement2.executeQuery("select Att_mnem from IISC_ATTRIBUTE where Att_id=" + resultSet.getInt("Att_id"));   
                
                if (resultSet2.next())
                {
                    attribNode.setAttribute("name", resultSet2.getString("Att_mnem"));  
                }
                resultSet2.close();
                statement2.close();
            }
          
            tupleDomainNode.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
            
            resultSet.close();
            statement.close();
            
        }
        catch (Exception e) 
        {
            System.out.print(e.toString());
        }
    }
    
    private void CreateChoiceDomainNode(Element parent, int Dom_id, int tabCount)
    {
        try
        {
            int childCount = 0;
            Statement statement, statement2;
            ResultSet resultSet;
            ResultSet resultSet2;
            
            Element choiceDomainNode = doc.createElement("choice_domain");
            parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
            parent.appendChild(choiceDomainNode);
            
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select * from IISC_DOM_ATT where  Dom_id=" + Dom_id);
            
            Element attribNode;
            
            while (resultSet.next())
            {
                attribNode = doc.createElement("attrib");
                choiceDomainNode.appendChild(doc.createTextNode("\n" + tab(tabCount)));
                //choiceDomainNode.appendChild(attribNode);
                statement2 = conn.createStatement();
                resultSet2 = statement2.executeQuery("select Att_mnem from IISC_ATTRIBUTE where Att_id=" + resultSet.getInt("Att_id"));   
                
                if (resultSet2.next())
                {
                    choiceDomainNode.appendChild(attribNode);
                    attribNode.setAttribute("name", resultSet2.getString("Att_mnem"));  
                }   
                resultSet2.close();
                statement2.close();
            }
            resultSet.close();
            statement.close();
            
            choiceDomainNode.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
        }
        catch (Exception e) 
        {
            System.out.print(e.toString());
        }
    }
    
    private void CreateSetDomainNode(Element parent, String memberType, String memberDom, int tabCount)
    {
        try
        {
            //Declare result seet
            Statement statement;
            ResultSet resultSet;
            statement = conn.createStatement();
            int childCount = 0;
            
            if (memberType.equals("user_defined"))
            { 
                resultSet = statement.executeQuery("select Dom_mnem from IISC_DOMAIN where  Dom_id=" + memberDom);
                
                if (resultSet.next())
                {
                    Element setDomainNode = doc.createElement("set_domain");
                    parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
                    parent.appendChild(setDomainNode);
                    setDomainNode.setAttribute("member_type", "user_defined");
                    setDomainNode.setAttribute("member_name", resultSet.getString("Dom_mnem"));
                    
                }
            }
            else
            {
                resultSet = statement.executeQuery("select PT_mnemonic from IISC_PRIMITIVE_TYPE where  PT_id=" + memberDom);
                
                if (resultSet.next())
                {
                    Element setDomainNode = doc.createElement("set_domain");
                    parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
                    parent.appendChild(setDomainNode);
                    setDomainNode.setAttribute("member_type", "primitive");
                    setDomainNode.setAttribute("member_name", resultSet.getString("PT_mnemonic"));
                    
                }
            }
            resultSet.close();
            statement.close();
        }
        catch (Exception e) 
        {
            System.out.print(e.toString());
        }
    }
    
    
    private void AddKeyNode(Element parent, int Tob_rbrk, int Tob_id, int tabCount)
    {
        try
        {
            int childCount = 0;
            Statement statement;
            ResultSet resultSet;
            
            Element keyNode = doc.createElement("key");
            keyNode.setAttribute("key_no",Integer.toString(Tob_rbrk));
            
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select Att_id from IISC_ATT_KTO where  Tob_id=" + Tob_id + " and Tob_rbrk=" + Tob_rbrk);
             
            while (resultSet.next())
            {
                AddCtAttribNodes(keyNode , resultSet.getInt("Att_id"), Tob_id, tabCount + 1);
                childCount = childCount + 1;
            }
            
            if (childCount > 0)
            {
                parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
                parent.appendChild(keyNode);
            }
            
            keyNode.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
            
            resultSet.close();
            statement.close();
        }
        catch (Exception e) 
        {
            System.out.print(e.toString());
        }
    }
    
    private void AddCtAttribNodes(Element parent, int Att_id, int Tob_id, int tabCount)
    {
        try
        { 
            int childCount = 0;
            int behaviour;
            Statement statement;
            ResultSet resultSet;
            
            Element ctAttributeNode = doc.createElement("ct_attribute");
            ctAttributeNode.appendChild(doc.createTextNode("\n" + tab(tabCount)));
            parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
            parent.appendChild(ctAttributeNode);
            
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select Att_mnem from IISC_ATTRIBUTE where Att_id=" + Att_id);
            
            if (resultSet.next())
            {
                ctAttributeNode.setAttribute("name", resultSet.getString("Att_mnem"));
            }
            resultSet.close();
            statement.close();
            
            CreateBehaviourNodeForCtAttrib(ctAttributeNode, Att_id ,Tob_id);
            
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select * from IISC_ATT_TOB where  Tob_id=" + Tob_id + " and Att_id=" + Att_id);
            
            if (resultSet.next())
            {
                String fun_id = resultSet.getString("Fun_id");
                if (fun_id != null)
                {
                    CreateFunctionNode(ctAttributeNode, Integer.parseInt(fun_id), tabCount);   
                }
                ctAttributeNode.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
                 
                ctAttributeNode.setAttribute("title", resultSet.getString("W_tittle"));
                ctAttributeNode.setAttribute("attribute_no", resultSet.getString("W_order"));
                
                if (resultSet.getString("W_mand").equals("Y"))
                {
                    ctAttributeNode.setAttribute("mandatory", "yes");    
                }
                else
                {
                    ctAttributeNode.setAttribute("mandatory", "no");
                }
                behaviour = resultSet.getInt("W_behav");
                if (behaviour == 0)
                {
                    ctAttributeNode.setAttribute("attribute_behaviour", "modifiable");
                }
                else
                {
                    if (behaviour == 1)
                    {
                        ctAttributeNode.setAttribute("attribute_behaviour", "query_only"); 
                    }
                    else
                    {
                        ctAttributeNode.setAttribute("attribute_behaviour", "display_only");
                    }    
                }
                String ctaDefault = resultSet.getString("W_default");
                
                if (ctaDefault != null)
                {
                    ctAttributeNode.setAttribute("default", ctaDefault);   
                }
            }
            resultSet.close();
            statement.close();
        }
        catch (Exception e) 
        {
            System.out.print(e.toString());
        }
    }
    
    private void CreateBehaviourNodeForTob(Element parent, int Tob_id, int tabCount)
    {
        try
        {
            //Declare result seet
            Statement statement;
            ResultSet resultSet;
            
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select Tob_queallow, Tob_insallow, Tob_updallow,Tob_deleteallow from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tob_id=" + Tob_id);
            
            Element behaviourNode = doc.createElement("behaviour");
            //parent.appendChild(doc.createTextNode(tab(tabCount - 1)));
            parent.appendChild(behaviourNode);
            
            if (resultSet.next())
            {
                if (resultSet.getString(1).equals("Y"))
                {
                    behaviourNode.setAttribute("query_allowed", "yes");
                }
                else
                {
                    behaviourNode.setAttribute("query_allowed", "no");
                }
                
                if (resultSet.getString(2).equals("Y"))
                {
                    behaviourNode.setAttribute("insert_allowed", "yes");
                }
                else
                {
                    behaviourNode.setAttribute("insert_allowed", "no");
                }
                
                if (resultSet.getString(3).equals("Y"))
                {
                    behaviourNode.setAttribute("update_allowed", "yes");
                }
                else
                {
                    behaviourNode.setAttribute("update_allowed", "no");
                }
                
                if (resultSet.getString(4).equals("Y"))
                {
                    behaviourNode.setAttribute("delete_allowed", "yes");
                }
                else
                {
                    behaviourNode.setAttribute("delete_allowed", "no");
                }
            }
            resultSet.close();
            statement.close();
        }
        catch (Exception e) 
        {
             System.out.print(e.toString());
        }
    }
    
     private void CreateFunctionNode(Element parent, int fun_id, int tabCount)
    {
        try
        {
            //Declare result seet
            Statement statement;
            ResultSet resultSet;
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select Fun_name,Fun_desc from IISC_FUNCTION where Fun_id=" + fun_id);
            
            if (resultSet.next())
            {
                parent.appendChild(doc.createTextNode("\n" + tab(tabCount)));
                Element functionNode = doc.createElement("function");
                parent.appendChild(functionNode);
                functionNode.setAttribute("name", resultSet.getString("Fun_name"));
                functionNode.setAttribute("description", resultSet.getString("Fun_desc"));
            }
            else
            {
            }
            resultSet.close();
            statement.close();
        }
        catch (Exception e) 
        {
            System.out.print(e.toString());
        }
    }
    
    private void CreateBehaviourNodeForCtAttrib(Element parent, int Att_id, int Tob_id)
    {
        try
        {
            //Declare result seet
            Statement statement;
            ResultSet resultSet;
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select W_queallow, W_insallow, W_updallow,W_nullallow from IISC_ATT_TOB where Tob_id=" + Tob_id + " and Att_id=" + Att_id);
            
            Element behaviourNode = doc.createElement("behaviour");
            parent.appendChild(behaviourNode);
            
            if (resultSet.next())
            {
                if (resultSet.getString(1).equals("Y"))
                {
                    behaviourNode.setAttribute("query_allowed", "yes");
                }
                else
                {
                    behaviourNode.setAttribute("query_allowed", "no");
                }
                
                if (resultSet.getString(2).equals("Y"))
                {
                    behaviourNode.setAttribute("insert_allowed", "yes");
                }
                else
                {
                    behaviourNode.setAttribute("insert_allowed", "no");
                }
                
                if (resultSet.getString(3).equals("Y"))
                {
                    behaviourNode.setAttribute("update_allowed", "yes");
                }
                else
                {
                    behaviourNode.setAttribute("update_allowed", "no");
                }
                
                if (resultSet.getString(4).equals("Y"))
                {
                    behaviourNode.setAttribute("nullify_allowed", "yes");
                }
                else
                {
                    behaviourNode.setAttribute("nullify_allowed", "no");
                }
            }
            resultSet.close();
            statement.close();
            
        }
        catch (Exception e) 
        {
             System.out.print(e.toString());
        }
    }
    
    private static String WriteXml(Document doc) 
    {
        try 
        { 
        
            Source source = new DOMSource(doc);
            Writer s=new StringWriter( );
            StreamResult result = new StreamResult(s);  
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, result);
            return  s.toString() ;

        } 
        catch (TransformerConfigurationException e) 
        {
            System.out.println("TransformerConfigurationException: " + e);
        }
        catch (TransformerException e) 
        {
            System.out.println("TransformerException: " + e);
        }
      
        return null;
    }
    
    private static String tab(int i)
    { 
        String str=new String();
        
        for(int j=1;j<=i;j++)
            str=str+"          ";
            
        return str;
    }
    
     private void CreateCheckConstraintNode(Element parent, int tabCount, String value)
     {
        try
        {
            int childCount = 0;
           
            Element CheckConstarintNode = doc.createElement("check_constraint");
            parent.appendChild(doc.createTextNode("\n" + tab(tabCount - 1)));
            parent.appendChild(CheckConstarintNode);
            CheckConstarintNode.appendChild(doc.createTextNode(value));
        
        }
        catch (Exception e) 
        {
            System.out.print(e.toString());
        }
    }
    
    public String GetFormTypeXmlShema()
    {
        String xmlSchema;
        
        xmlSchema = "";
        xmlSchema += "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n";
        xmlSchema += "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" elementFormDefault=\"qualified\">\n";
       
        xmlSchema += tab(1) + "<xs:element name=\"behaviour\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"query_allowed\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"yes\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"no\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"insert_allowed\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"yes\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"no\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";       
        xmlSchema += tab(3) + "<xs:attribute name=\"delete_allowed\" use=\"optional\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"yes\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"no\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"nullify_allowed\" use=\"optional\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"yes\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"no\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"update_allowed\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"yes\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"no\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"component_type\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"component_type\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"behaviour\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"ct_attribute\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"key\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"unique\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"check_constraint\" minOccurs=\"0\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"name\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"title\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"mandatory\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"yes\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"no\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"ct_attribute\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"behaviour\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"function\" minOccurs=\"0\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"name\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"title\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"mandatory\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"yes\"/>\n";
        xmlSchema += tab(6) + " <xs:enumeration value=\"no\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"default\" use=\"optional\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"attribute_behaviour\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"modifiable\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"query_only\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"display_only\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"attribute_no\" use=\"optional\" type=\"xs:integer\"/>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"key\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"ct_attribute\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"key_no\" use=\"required\" type=\"xs:integer\"/>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"unique\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"ct_attribute\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"unique_no\" use=\"required\" type=\"xs:integer\"/>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"check_constraint\" type=\"xs:string\"/>\n";
        
        xmlSchema += tab(1) + "<xs:element  name=\"attribute\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"domain\" minOccurs=\"1\" maxOccurs=\"1\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"renamed\" minOccurs=\"0\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"derived\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"name\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"description\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"expression\" use=\"optional\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"query_function\" use=\"optional\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"exists_in_DB\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"yes\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"no\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"derived\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"yes\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"no\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"default\" use=\"optional\" type=\"xs:string\"/>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
         
        xmlSchema += tab(1) + "<xs:element name=\"domain\">\n"; 
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:choice>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"user_defined_domain\"/>\n";
        xmlSchema += tab(3) + "</xs:choice>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"name\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"description\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"default\" use=\"optional\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"expression\" use=\"optional\" type=\"xs:string\"/>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"primitive_domain\">\n"; 
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:complexContent>\n";
        xmlSchema += tab(4) + "<xs:restriction base=\"xs:anyType\">\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"type\" type=\"xs:string\"/>\n";
        xmlSchema += tab(4) + "</xs:restriction>\n";
        xmlSchema += tab(3) + "</xs:complexContent>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
         
        xmlSchema += tab(1) + "<xs:element name=\"user_defined_domain\">\n"; 
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:choice>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"inheritance_domain\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"complex_domain\"/>\n";
        xmlSchema += tab(3) + "</xs:choice>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"inheritance_domain\">\n"; 
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:complexContent>\n";
        xmlSchema += tab(4) + "<xs:restriction base=\"xs:anyType\">\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"from\" use=\"required\">\n";
        xmlSchema += tab(6) + "<xs:simpleType>\n";
        xmlSchema += tab(7) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(8) + "<xs:enumeration value=\"primitive\"/>\n";
        xmlSchema += tab(8) + "<xs:enumeration value=\"user_defined\"/>\n";
        xmlSchema += tab(7) + "</xs:restriction>\n";
        xmlSchema += tab(6) + "</xs:simpleType>\n";
        xmlSchema += tab(5) + "</xs:attribute>\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"parent\" type=\"xs:string\"/>\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"length\" use=\"optional\" type=\"xs:integer\"/>\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"decimal_places\" use=\"optional\" type=\"xs:integer\"/>\n";
        xmlSchema += tab(4) + "</xs:restriction>\n";
        xmlSchema += tab(3) + "</xs:complexContent>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"complex_domain\">\n"; 
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:choice>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"tuple_domain\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"choice_domain\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"set_domain\"/>\n";
        xmlSchema += tab(3) + "</xs:choice>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        
        xmlSchema += tab(1) + "<xs:element name=\"tuple_domain\">\n"; 
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"attrib\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"choice_domain\">\n"; 
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"attrib\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
       
        xmlSchema += tab(1) + "<xs:element name=\"set_domain\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:complexContent>\n";
        xmlSchema += tab(4) + "<xs:restriction base=\"xs:anyType\">\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"member_type\" use=\"required\">\n";
        xmlSchema += tab(6) + "<xs:simpleType>\n";
        xmlSchema += tab(7) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(8) + "<xs:enumeration value=\"primitive\"/>\n";
        xmlSchema += tab(8) + "<xs:enumeration value=\"user_defined\"/>\n";
        xmlSchema += tab(7) + "</xs:restriction>\n";
        xmlSchema += tab(6) + "</xs:simpleType>\n";
        xmlSchema += tab(5) + "</xs:attribute>\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"member_name\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(4) + "</xs:restriction>\n";
        xmlSchema += tab(3) + "</xs:complexContent>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"function\">\n"; 
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:complexContent>\n";
        xmlSchema += tab(4) + "<xs:restriction base=\"xs:anyType\">\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"name\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"description\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(4) + "</xs:restriction>\n";
        xmlSchema += tab(3) + "</xs:complexContent>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
          
        xmlSchema += tab(1) + "<xs:element name=\"renamed\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:complexContent>\n";
        xmlSchema += tab(4) + "<xs:restriction base=\"xs:anyType\">\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"from_attribute\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(4) + "</xs:restriction>\n";
        xmlSchema += tab(3) + "</xs:complexContent>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
          
        xmlSchema += tab(1) + "<xs:element name=\"attrib\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:complexContent>\n";
        xmlSchema += tab(4) + "<xs:restriction base=\"xs:anyType\">\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"name\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(4) + "</xs:restriction>\n";
        xmlSchema += tab(3) + "</xs:complexContent>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
          
        xmlSchema += tab(1) + "<xs:element name=\"derived\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"mode\" minOccurs=\"1\" />\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"from_attribute\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"mode\">\n"; 
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"function\" minOccurs=\"1\" />\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"type\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"update\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"insert\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"delete\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
         
        xmlSchema += tab(1) + "<xs:element name=\"form_type\">\n";
        xmlSchema += tab(2) + "<xs:complexType mixed=\"true\">\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"component_type\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"name\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"title\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"type\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"MENU\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"NOT_CONSIDERED_IN_DB_DESIGN\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"CONSIDERED_IN_DB_DESIGN\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"frequency\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"frequency_unit\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"min.\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"sec.\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"hour\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"day\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"week\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"month\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"year\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"response\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"response_unit\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"min.\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"sec.\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"hour\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"day\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"week\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"month\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"year\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"form_type_spec\">\n";
        xmlSchema += tab(2) + "<xs:complexType mixed=\"true\">\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"form_type\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"attribute\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += "</xs:schema>";
        
        return xmlSchema;
    }
    
    
    public String GetAppSysXmlShema()
    {
        String xmlSchema;
        
        xmlSchema = "";
        xmlSchema += "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n";
        xmlSchema += "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" elementFormDefault=\"qualified\">\n";
       
        xmlSchema += tab(1) + "<xs:element name=\"behaviour\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"query_allowed\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"yes\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"no\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"insert_allowed\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"yes\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"no\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";       
        xmlSchema += tab(3) + "<xs:attribute name=\"delete_allowed\" use=\"optional\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"yes\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"no\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"nullify_allowed\" use=\"optional\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"yes\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"no\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"update_allowed\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"yes\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"no\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"component_type\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"component_type\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"behaviour\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"ct_attribute\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"key\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"unique\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"check_constraint\" minOccurs=\"0\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"name\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"title\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"mandatory\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"yes\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"no\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"ct_attribute\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"behaviour\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"function\" minOccurs=\"0\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"name\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"title\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"mandatory\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"yes\"/>\n";
        xmlSchema += tab(6) + " <xs:enumeration value=\"no\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"default\" use=\"optional\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"attribute_behaviour\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"modifiable\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"query_only\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"display_only\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"attribute_no\" use=\"optional\" type=\"xs:integer\"/>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"key\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"ct_attribute\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"key_no\" use=\"required\" type=\"xs:integer\"/>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"unique\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"ct_attribute\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"unique_no\" use=\"required\" type=\"xs:integer\"/>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"check_constraint\" type=\"xs:string\"/>\n";
        
        xmlSchema += tab(1) + "<xs:element  name=\"attribute\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"domain\" minOccurs=\"1\" maxOccurs=\"1\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"renamed\" minOccurs=\"0\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"derived\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"name\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"description\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"expression\" use=\"optional\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"query_function\" use=\"optional\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"exists_in_DB\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"yes\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"no\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"derived\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"yes\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"no\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"default\" use=\"optional\" type=\"xs:string\"/>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
         
        xmlSchema += tab(1) + "<xs:element name=\"domain\">\n"; 
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:choice>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"user_defined_domain\"/>\n";
        xmlSchema += tab(3) + "</xs:choice>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"name\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"description\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"default\" use=\"optional\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"expression\" use=\"optional\" type=\"xs:string\"/>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"primitive_domain\">\n"; 
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:complexContent>\n";
        xmlSchema += tab(4) + "<xs:restriction base=\"xs:anyType\">\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"type\" type=\"xs:string\"/>\n";
        xmlSchema += tab(4) + "</xs:restriction>\n";
        xmlSchema += tab(3) + "</xs:complexContent>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
         
        xmlSchema += tab(1) + "<xs:element name=\"user_defined_domain\">\n"; 
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:choice>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"inheritance_domain\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"complex_domain\"/>\n";
        xmlSchema += tab(3) + "</xs:choice>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"inheritance_domain\">\n"; 
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:complexContent>\n";
        xmlSchema += tab(4) + "<xs:restriction base=\"xs:anyType\">\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"from\" use=\"required\">\n";
        xmlSchema += tab(6) + "<xs:simpleType>\n";
        xmlSchema += tab(7) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(8) + "<xs:enumeration value=\"primitive\"/>\n";
        xmlSchema += tab(8) + "<xs:enumeration value=\"user_defined\"/>\n";
        xmlSchema += tab(7) + "</xs:restriction>\n";
        xmlSchema += tab(6) + "</xs:simpleType>\n";
        xmlSchema += tab(5) + "</xs:attribute>\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"parent\" type=\"xs:string\"/>\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"length\" use=\"optional\" type=\"xs:integer\"/>\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"decimal_places\" use=\"optional\" type=\"xs:integer\"/>\n";
        xmlSchema += tab(4) + "</xs:restriction>\n";
        xmlSchema += tab(3) + "</xs:complexContent>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"complex_domain\">\n"; 
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:choice>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"tuple_domain\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"choice_domain\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"set_domain\"/>\n";
        xmlSchema += tab(3) + "</xs:choice>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        
        xmlSchema += tab(1) + "<xs:element name=\"tuple_domain\">\n"; 
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"attrib\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"choice_domain\">\n"; 
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"attrib\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
       
        xmlSchema += tab(1) + "<xs:element name=\"set_domain\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:complexContent>\n";
        xmlSchema += tab(4) + "<xs:restriction base=\"xs:anyType\">\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"member_type\" use=\"required\">\n";
        xmlSchema += tab(6) + "<xs:simpleType>\n";
        xmlSchema += tab(7) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(8) + "<xs:enumeration value=\"primitive\"/>\n";
        xmlSchema += tab(8) + "<xs:enumeration value=\"user_defined\"/>\n";
        xmlSchema += tab(7) + "</xs:restriction>\n";
        xmlSchema += tab(6) + "</xs:simpleType>\n";
        xmlSchema += tab(5) + "</xs:attribute>\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"member_name\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(4) + "</xs:restriction>\n";
        xmlSchema += tab(3) + "</xs:complexContent>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"function\">\n"; 
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:complexContent>\n";
        xmlSchema += tab(4) + "<xs:restriction base=\"xs:anyType\">\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"name\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"description\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(4) + "</xs:restriction>\n";
        xmlSchema += tab(3) + "</xs:complexContent>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
          
        xmlSchema += tab(1) + "<xs:element name=\"renamed\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:complexContent>\n";
        xmlSchema += tab(4) + "<xs:restriction base=\"xs:anyType\">\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"from_attribute\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(4) + "</xs:restriction>\n";
        xmlSchema += tab(3) + "</xs:complexContent>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
          
        xmlSchema += tab(1) + "<xs:element name=\"attrib\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:complexContent>\n";
        xmlSchema += tab(4) + "<xs:restriction base=\"xs:anyType\">\n";
        xmlSchema += tab(5) + "<xs:attribute name=\"name\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(4) + "</xs:restriction>\n";
        xmlSchema += tab(3) + "</xs:complexContent>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
          
        xmlSchema += tab(1) + "<xs:element name=\"derived\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"mode\" minOccurs=\"1\" />\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"from_attribute\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"mode\">\n"; 
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"function\" minOccurs=\"1\" />\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"type\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"update\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"insert\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"delete\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
         
        xmlSchema += tab(1) + "<xs:element name=\"form_type\">\n";
        xmlSchema += tab(2) + "<xs:complexType mixed=\"true\">\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"component_type\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"name\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"title\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"type\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"MENU\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"NOT_CONSIDERED_IN_DB_DESIGN\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"CONSIDERED_IN_DB_DESIGN\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"frequency\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"frequency_unit\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"min.\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"sec.\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"hour\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"day\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"week\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"month\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"year\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"response\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"response_unit\" use=\"required\">\n";
        xmlSchema += tab(4) + "<xs:simpleType>\n";
        xmlSchema += tab(5) + "<xs:restriction base=\"xs:NMTOKEN\">\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"min.\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"sec.\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"hour\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"day\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"week\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"month\"/>\n";
        xmlSchema += tab(6) + "<xs:enumeration value=\"year\"/>\n";
        xmlSchema += tab(5) + "</xs:restriction>\n";
        xmlSchema += tab(4) + "</xs:simpleType>\n";
        xmlSchema += tab(3) + "</xs:attribute>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"child_app_systems\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"app_system_spec\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"form_types\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"owned_form_types\" minOccurs=\"1\" maxOccurs=\"1\" />\n";
        xmlSchema += tab(4) + "<xs:element ref=\"referenced_form_types\" minOccurs=\"1\" maxOccurs=\"1\" />\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"referenced_form_types\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"form_type\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"owned_form_types\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"form_type\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"application_system\">\n";
        xmlSchema += tab(2) + "<xs:complexType mixed=\"true\">\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"child_app_systems\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"form_types\" minOccurs=\"1\" maxOccurs=\"1\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"name\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"description\" use=\"required\" type=\"xs:string\"/>\n";
        xmlSchema += tab(3) + "<xs:attribute name=\"type\" use=\"required\"/>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += tab(1) + "<xs:element name=\"app_system_spec\">\n";
        xmlSchema += tab(2) + "<xs:complexType>\n";
        xmlSchema += tab(3) + "<xs:sequence>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"application_system\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(4) + "<xs:element ref=\"attribute\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n";
        xmlSchema += tab(3) + "</xs:sequence>\n";
        xmlSchema += tab(2) + "</xs:complexType>\n";
        xmlSchema += tab(1) + "</xs:element>\n";
        
        xmlSchema += "</xs:schema>";
        
        return xmlSchema;
    }
    
}