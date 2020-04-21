package iisc.lang.vmashine;

public class MemberVal implements ValElem {
    int type;
    ValElem val;
    String name;

    public MemberVal() {
    }

    public ValElem Clone() {
        MemberVal temp = new MemberVal();
        temp.type = this.type;
        temp.val = this.val.Clone();
        return temp;
    }

    public String ToString() {
        return val.ToString();
    }

    public void Copy(ValElem memelem, int typeCode) {
        MemberVal mem = (MemberVal)memelem;
        this.val.Copy(mem.val, mem.type);
    }

    public int Compare(ValElem elem, int typeCode) {
        MemberVal mem = (MemberVal)elem;
        return this.val.Compare(mem.val, mem.type);
        
    }
}
