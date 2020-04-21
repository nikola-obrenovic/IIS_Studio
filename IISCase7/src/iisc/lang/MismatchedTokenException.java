package iisc.lang;

public class MismatchedTokenException extends RecognitionException {
	public int expecting;

	public MismatchedTokenException() {
	}

	public MismatchedTokenException(int expecting, IntStream input) {
		super(input);
		this.expecting = expecting;
	}

	public String toString() {
		return "MismatchedTokenException("+getUnexpectedType()+"!="+expecting+")";
	}
}
