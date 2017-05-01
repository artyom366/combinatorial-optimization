package opt.gen.alg.error;

public class EmptyIntervalException extends GAException {

    private static final String MESSAGE = "Empty interval error";

    public EmptyIntervalException() {
        super(MESSAGE);
    }
}
