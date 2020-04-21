package iisc.lang.vmashine;

public interface ValElem 
{
    public ValElem Clone();
    public String ToString();
    public void Copy(ValElem elem, int typeCode);
    public int Compare(ValElem elem, int typeCode);
}
