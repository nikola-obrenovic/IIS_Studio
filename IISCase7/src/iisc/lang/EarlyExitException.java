 package iisc.lang;
 
/**  The recognizer did not match anything for a (..)+ loop. */
public class EarlyExitException extends RecognitionException {
	public int decisionNumber;

	/** Used for remote debugger deserialization */
	public EarlyExitException() {;}
	
	public EarlyExitException(int decisionNumber, IntStream input) {
		super(input);
		this.decisionNumber = decisionNumber;
	}
}
