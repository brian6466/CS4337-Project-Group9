package cs4337.group9.contentapi.Exceptions;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String userid) {
        super(String.format("This comment was not made by this user, id (%s)", userid));
    }
}
