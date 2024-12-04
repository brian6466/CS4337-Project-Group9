package cs4337.group9.contentapi.Controller;
import cs4337.group9.contentapi.Entity.CommentEntity;
import cs4337.group9.contentapi.Exceptions.UnauthorizedAccessException;
import cs4337.group9.contentapi.Entity.ArticleEntity;
import cs4337.group9.contentapi.Service.CommentService;
import cs4337.group9.contentapi.Service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllComments() {
        UUID articleId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);
        List<CommentEntity> comments = Arrays.asList(new CommentEntity(), new CommentEntity());
        Page<CommentEntity> page = new PageImpl<>(comments);
        when(commentService.getAllComments(articleId, pageable)).thenReturn(page);
        when(articleService.getArticleById(articleId)).thenReturn(new ArticleEntity());

        ResponseEntity<Page<CommentEntity>> response = commentController.getAllComments(articleId, 0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
    }

    @Test
    public void testGetCommentById() {
        UUID articleId = UUID.randomUUID();
        UUID commentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        CommentEntity comment = new CommentEntity();
        when(commentService.getComment(commentId, userId)).thenReturn(comment);
        when(articleService.getArticleById(articleId)).thenReturn(new ArticleEntity());

        ResponseEntity<CommentEntity> response = commentController.getComment(articleId, commentId, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comment, response.getBody());
    }

    @Test
    public void testCreateComment() {
        UUID articleId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "Test comment";
        CommentEntity comment = new CommentEntity();
        when(commentService.createComment(articleId, userId, content)).thenReturn(comment);
        when(articleService.getArticleById(articleId)).thenReturn(new ArticleEntity());

        ResponseEntity<CommentEntity> response = commentController.createComment(articleId, userId, content);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(comment, response.getBody());
    }

    @Test
    public void testUpdateComment() {
        UUID articleId = UUID.randomUUID();
        UUID commentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "Updated comment";
        CommentEntity comment = new CommentEntity();
        when(commentService.updateComment(commentId, userId, content)).thenReturn(Optional.of(comment));
        when(articleService.getArticleById(articleId)).thenReturn(new ArticleEntity());

        ResponseEntity<Optional<CommentEntity>> response = commentController.updateComment(articleId, commentId, userId, content);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.of(comment), response.getBody());
    }

    @Test
    public void testDeleteComment() {
        UUID articleId = UUID.randomUUID();
        UUID commentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String message = "Comment deleted";
        when(commentService.deleteComment(commentId, userId)).thenReturn(message);
        when(articleService.getArticleById(articleId)).thenReturn(new ArticleEntity());

        ResponseEntity<String> response = commentController.deleteComment(articleId, commentId, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(message, response.getBody());
    }

    @Test
    public void testDeleteCommentUnauthorized() {
        UUID articleId = UUID.randomUUID();
        UUID commentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        doThrow(new UnauthorizedAccessException("You are not authorized to delete this comment."))
                .when(commentService).deleteComment(commentId, userId);

        UnauthorizedAccessException exception = assertThrows(UnauthorizedAccessException.class, () -> {
            commentController.deleteComment(articleId, commentId, userId);
        });

        assertEquals("You are not authorized to delete this comment.", exception.getMessage());
    }
}