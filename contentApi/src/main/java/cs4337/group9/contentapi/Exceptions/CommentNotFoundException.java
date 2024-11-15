package cs4337.group9.contentapi.Exceptions;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String commentId) {
        super(String.format("Comment with id (%s) not found", commentId));
    }
}
