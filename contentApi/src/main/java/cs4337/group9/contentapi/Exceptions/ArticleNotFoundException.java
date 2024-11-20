package cs4337.group9.contentapi.Exceptions;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException(String articleId) {
        super("Article not found with the following Id:  " + articleId);
    }
}