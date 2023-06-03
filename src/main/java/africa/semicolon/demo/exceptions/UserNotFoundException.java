package africa.semicolon.demo.exceptions;

public class UserNotFoundException extends TranzactExceptions{
    public UserNotFoundException(String message) {
        super(message);
    }
}
