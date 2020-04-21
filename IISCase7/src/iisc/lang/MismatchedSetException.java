 package iisc.lang;
 
public class MismatchedSetException extends RecognitionException {
	public BitSet expecting;

	/** Used for remote debugger deserialization */
	public MismatchedSetException() {;}

	public MismatchedSetException(BitSet expecting, IntStream input) {
		super(input);
		this.expecting = expecting;
	}

	public String toString() {
		return "MismatchedSetException("+getUnexpectedType()+"!="+expecting+")";
	}
}
