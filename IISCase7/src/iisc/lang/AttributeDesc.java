package iisc.lang;

    public class AttributeDesc
    {
        String name = "";
        String id = "";        
        String domName;
        String domId = "";
        int rbr = 0;
        
        public AttributeDesc(String name, String id, String domId, int rbr)
        {
            this.name = name;
            this.id = id;
            this.domId = domId;    
            this.rbr = rbr;
        }
        
        public void setName(String name)
        {
            this.name = name;
        }
        
        public String getName()
        {
            return this.name;
        }
        
        public void setId(String id)
        {
            this.id = id;
        }
        
        public String getId()
        {
            return this.id;
        }
        
        public void setDomName(String domName)
        {
            this.domName = domName;
        }
        
        public String getDomName()
        {
            return this.domName;
        }
        
        public String getDomId()
        {
            return this.domId;
        }
        
        public int getRbr()
        {
            return this.rbr;
        }
        
        public void setRbr(int rbr)
        {
            this.rbr = rbr;
        }
    }
