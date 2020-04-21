package iisc.lang;

import java.util.ArrayList;

public class DomainDesc
    {
        public static final int PRIMITIVE = 0;
        public static final int INHERITED_PRIMITIVE = 1;
        public static final int INHERITED_USER_DEF = 2;
        public static final int TUPPLE = 3;
        public static final int CHOICE = 4;
        public static final int SET = 5;
        
        String name = "";
        String id = "";
        String dom_len;
        int type;
        String inheritedDomName;
        int complexDomainType;
        int primitiveDomainType;
        String primitiveTypeId = "";
        String primitiveTypeName = "";
        String parentName = "";
        ArrayList members = new ArrayList();
        
        public DomainDesc(String name, String id, int type)
        {
            this.name = name;
            this.id = id;
            this.type = type;    
        }
        
        public String getFullName()
        {
            if (this.type==INHERITED_PRIMITIVE )
            {
                return this.getName()+ " (Inherited from primitive: " + this.getPrimitiveTypeName() + ")";
            }
            
            if ( this.type == INHERITED_USER_DEF)
            {
                return this.getName()+ " (Inherited from user defined: " + this.getParentName() + ")";
            }
            
            if (this.type == TUPPLE)
            {
                return this.getName()+ " (Complex: Tupple)";
            }
            
            if (this.type == CHOICE)
            {
                return this.getName()+ " (Complex: Choice)";
            }
            
            if (this.type == SET)
            {
                return this.getName()+ " (Complex: Set: " + this.getParentName() + ")";
            }
            
            return this.getName();
        }
        
        public void setPrimitiveDomainType(int primitiveDomainType)
        {
            this.primitiveDomainType = primitiveDomainType;
        }
        
        public int getPrimitiveDomainType()
        {
            return this.primitiveDomainType;
        }
        
        public void setPrimitiveTypeId(String primitiveTypeId)
        {
            this.primitiveTypeId = primitiveTypeId;
        }
        
        public String getPrimitiveTypeId()
        {
            return this.primitiveTypeId;
        }
        
        public void setPrimitiveTypeName(String primitiveTypeName)
        {
            this.primitiveTypeName = primitiveTypeName;
        }
        
        public String getPrimitiveTypeName()
        {
            return this.primitiveTypeName;
        }
        
        public void setParentName(String parentName)
        {
            this.parentName = parentName;
        }
        
        public String getParentName()
        {
            return this.parentName;
        }
        
        public void setName(String name)
        {
            this.name = name;
        }
        
        public String getName()
        {
            return this.name;
        }
        
        public String getLength()
        {
            return this.dom_len;
        }
        
        public void setId(String id)
        {
            this.id = id;
        }
        
        public String getId()
        {
            return this.id;
        }
        
        public void setType(int type)
        {
            this.type = type;
        }
        
        public int getType()
        {
            return this.type;
        }
        
        public ArrayList getMembers()
        {
            return this.members;
        }
    }
    
    