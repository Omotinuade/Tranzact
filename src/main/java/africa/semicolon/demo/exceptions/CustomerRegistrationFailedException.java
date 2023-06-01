package africa.semicolon.demo.exceptions;

public class CustomerRegistrationFailedException extends TranzactExceptions {
    public CustomerRegistrationFailedException(String message) {
        super(message);
    }
}
