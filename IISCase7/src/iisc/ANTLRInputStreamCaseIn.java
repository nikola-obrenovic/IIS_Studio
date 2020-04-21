package iisc;

import java.io.*;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CharStream;

public class ANTLRInputStreamCaseIn  extends ANTLRInputStream {
    public ANTLRInputStreamCaseIn(InputStream input) throws Exception {
        super(input,null);
    }

    public int LA(int i) {
        if ( i==0 ) {
            return 0; // undefined
        }
        if ( i<0 ) {
            i++; // e.g., translate LA(-1) to use offset 0
        }

        if ( (p+i-1) >= n ) {

            return CharStream.EOF;
        }
        //System.out.print("*" + Character.toUpperCase(data[p+i-1]));
        return Character.toUpperCase(data[p+i-1]);
    }
}