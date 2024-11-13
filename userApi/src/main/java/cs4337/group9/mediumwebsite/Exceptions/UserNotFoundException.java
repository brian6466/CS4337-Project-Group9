package cs4337.group9.mediumwebsite.Exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("User with ID " + userId + " not found");
    }
}