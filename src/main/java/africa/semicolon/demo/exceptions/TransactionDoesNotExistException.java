package africa.semicolon.demo.exceptions;

public class TransactionDoesNotExistException extends TranzactExceptions {
    public TransactionDoesNotExistException(String message) {
        super(message);
    }
}
