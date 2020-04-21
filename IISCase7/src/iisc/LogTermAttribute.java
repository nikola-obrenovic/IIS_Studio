package iisc;

public class LogTermAttribute extends LogTermOperand {
    
    private DataTypesEnum type;
    private String name;
    
    public LogTermAttribute() {
    }


    public void setType(DataTypesEnum type) {
        this.type = type;
    }

    public DataTypesEnum getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
