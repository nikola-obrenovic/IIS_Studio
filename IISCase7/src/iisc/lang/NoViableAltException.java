package iisc.lang;
 
import iisc.lang.IntStream;

public class NoViableAltException extends RecognitionException {
	public String grammarDecisionDescription;
	public int decisionNumber;
	public int stateNumber;

	/** Used for remote debugger deserialization */
	public NoViableAltException() {;}
	
	public NoViableAltException(String grammarDecisionDescription,
								int decisionNumber,
								int stateNumber,
								IntStream input)
	{
		super(input);
		this.grammarDecisionDescription = grammarDecisionDescription;
		this.decisionNumber = decisionNumber;
		this.stateNumber = stateNumber;
	}

	public String toString() {
		return "NoViableAltException("+getUnexpectedType()+"!=["+grammarDecisionDescription+"])";
	}
}
