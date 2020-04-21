 package iisc.lang;
 
public class MismatchedRangeException extends RecognitionException {
	public int a,b;

	public MismatchedRangeException(int a, int b, IntStream input) {
		super(input);
		this.a = a;
		this.b = b;
	}

	public String toString() {
		return "MismatchedNotSetException("+getUnexpectedType()+" not in ["+a+","+b+"])";
	}
}
