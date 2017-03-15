package opt.gen.alg.error;

public class GAException extends RuntimeException {

	private static final String MESSAGE = "Genetic algorithm error: %s";

	public GAException(final String message) {
		super(String.format(MESSAGE, message));
	}
}
