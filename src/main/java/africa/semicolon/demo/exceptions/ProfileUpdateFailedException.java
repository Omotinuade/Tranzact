package africa.semicolon.demo.exceptions;

import com.github.fge.jsonpatch.JsonPatchException;

public class ProfileUpdateFailedException extends TranzactExceptions{
    public ProfileUpdateFailedException(String message) {
        super(message);
    }
}
