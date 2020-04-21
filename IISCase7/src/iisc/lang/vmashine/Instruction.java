package iisc.lang.vmashine;

public class Instruction 
{
    public int insID; 
    public String type = "";
    public String val = "";
    public String name = "";
    public int index = 0;    
    public int toIndex = 0;    
    
    public static int PUSH_OC    = 0;
    public static int VAL_OC     = 1;
    public static int MEMBER_OC  = 2;
    public static int TUPPLE_OC  = 3;
    public static int VAR_OC     = 4;
    public static int ARR_OC     = 5;
    public static int POP_OC     = 6;
    public static int INIT_OC    = 7;
    public static int LOADM_OC   = 8;
    public static int LOADARR_OC = 9;
    public static int LAST_VAR_OC = 10;
    public static int SET_OC  = 11;
    public static int LOADSET_OC  = 12;
    
    public static int ST_OC = 0;
    public static int STA_OC = 1;
    public static int LDC_OC = 2;
    public static int LDV_OC = 3;
    public static int LDVA_OC = 4;
    public static int LDI_OC = 5;
    public static int LDIA_OC = 6;
    public static int LDM_OC = 7;
    public static int LDMA_OC = 8;
    public static int FUNC_OC = 9;
    public static int JMPC_OC = 10;
    public static int JMP_OC = 11;
    public static int JMPCN_OC = 12;
    public static int IMPL_OC = 13;
    public static int XOR_OC = 14;
    public static int OR_OC = 15;
    public static int AND_OC = 16;
    public static int EQ_OC = 17;
    public static int NE_OC = 18;
    public static int LT_OC = 19;
    public static int LE_OC = 20;
    public static int GT_OC = 21;
    public static int GE_OC = 22;
    public static int ADD_OC = 23;
    public static int SUB_OC = 24;
    public static int MUL_OC = 25;
    public static int DIV_OC = 26;
    public static int MOD_OC = 27;
    public static int INTERSECT_OC = 28;
    public static int UNION_OC = 29;
    public static int CONCAT_OC = 30;    
    
    public static int NOT_OC = 40;
    public static int UNMINUS_OC = 41;
    public static int LIKE_OC = 42;
    public static int IN_OC = 43;
    public static int PRINT_OC = 44;
    public static int BREAKPOINT_OC = 45;
    public static int RETURN_OC = 46;
    public static int SELECT_OC = 47;
    public static int SELECTA_OC = 48;
    public static int SIGNAL_OC = 49;
    public static int UPDATE_OC = 50;
    public static int FETCH_OC = 51;
    
    public Instruction() 
    {
        
    }
}
