package iisc;

public class LogTermConstant extends LogTermOperand {
    
    protected DataTypesEnum type;
    protected String value;
   
    public LogTermConstant() {
    }

    public void setType(DataTypesEnum type) {
        this.type = type;
    }

    public DataTypesEnum getType() {
        return type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
