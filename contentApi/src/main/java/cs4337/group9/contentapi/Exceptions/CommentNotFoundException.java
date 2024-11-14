package cs4337.group9.contentapi.Exceptions;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String message) {
        super(message);
    }
}
