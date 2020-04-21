package iisc.lang;

public class MismatchedNotSetException extends MismatchedSetException {
	/** Used for remote debugger deserialization */
	public MismatchedNotSetException() {;}

	public MismatchedNotSetException(BitSet expecting, IntStream input) {
		super(expecting, input);
	}

	public String toString() {
		return "MismatchedNotSetException("+getUnexpectedType()+"!="+expecting+")";
	}
}
