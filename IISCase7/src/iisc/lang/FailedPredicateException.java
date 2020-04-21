 package iisc.lang;
 
 public class FailedPredicateException extends RecognitionException {
	public String ruleName;
	public String predicateText;

	/** Used for remote debugger deserialization */
	public FailedPredicateException() {;}

	public FailedPredicateException(IntStream input,
									String ruleName,
									String predicateText)
	{
		super(input);
		this.ruleName = ruleName;
		this.predicateText = predicateText;
	}

	public String toString() {
		return "FailedPredicateException("+ruleName+",{"+predicateText+"}?)";
	}
}
