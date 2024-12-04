package cs4337.group9.contentapi.Controller;
import cs4337.group9.contentapi.Entity.ArticleEntity;
import cs4337.group9.contentapi.Exceptions.UnauthorizedAccessException;
import cs4337.group9.contentapi.Service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ArticleControllerTest {

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private ArticleController articleController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllArticles() {
        List<ArticleEntity> articles = Arrays.asList(new ArticleEntity(), new ArticleEntity());
        when(articleService.getAllArticles()).thenReturn(articles);

        ResponseEntity<List<ArticleEntity>> response = articleController.getAllArticles();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(articles, response.getBody());
    }

    @Test
    public void testGetArticleById() {
        UUID articleId = UUID.randomUUID();
        ArticleEntity article = new ArticleEntity();
        when(articleService.getArticleById(articleId)).thenReturn(article);

        ResponseEntity<ArticleEntity> response = articleController.getArticle(articleId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(article, response.getBody());
    }

    @Test
    public void testCreateArticle() {
        ArticleEntity article = new ArticleEntity();
        doNothing().when(articleService).createArticle(article);

        ResponseEntity<String> response = articleController.createArticle(article);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Article Successfully created!", response.getBody());
    }

    @Test
    public void testUpdateArticle() {
        UUID articleId = UUID.randomUUID();
        ArticleEntity article = new ArticleEntity();
        doNothing().when(articleService).updateArticle(articleId, article);

        ResponseEntity<String> response = articleController.updateArticle(articleId, article);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Article Successfully updated!", response.getBody());
    }

    @Test
    public void testDeleteArticle() {
        UUID articleId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        doNothing().when(articleService).deleteArticle(articleId, userId);

        ResponseEntity<String> response = articleController.deleteArticle(articleId, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Article Successfully deleted!", response.getBody());
    }

    @Test
    public void testDeleteArticleUnauthorized() {
        UUID articleId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        doThrow(new UnauthorizedAccessException("You are not authorized to delete this article."))
                .when(articleService).deleteArticle(articleId, userId);

        UnauthorizedAccessException exception = assertThrows(UnauthorizedAccessException.class, () -> {
            articleController.deleteArticle(articleId, userId);
        });

        assertEquals("You are not authorized to delete this article.", exception.getMessage());
    }
}