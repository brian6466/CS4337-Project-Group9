package cs4337.group9.mediumwebsite.Exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String email) {
        super("Account already created with the following email:  " + email);
    }
}