package opt.gen.error;

public class IllegalChromosomeSizeException extends GAException {

	private static final String MESSAGE = "Illegal chromosome size of:%d, with respect to min:%d and max:%d";

	public IllegalChromosomeSizeException(final int size, final int min, final int max) {
		super(String.format(MESSAGE, size, min, max));
	}
}
