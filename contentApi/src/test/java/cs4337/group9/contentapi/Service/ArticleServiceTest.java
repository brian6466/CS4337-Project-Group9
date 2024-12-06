package cs4337.group9.contentapi.Service;
import cs4337.group9.contentapi.Entity.ArticleEntity;
import cs4337.group9.contentapi.Repository.ArticleRepository;
import cs4337.group9.contentapi.Exceptions.ArticleNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllArticles() {
        List<ArticleEntity> articles = Arrays.asList(new ArticleEntity(), new ArticleEntity());
        when(articleRepository.findAll()).thenReturn(articles);

        List<ArticleEntity> result = articleService.getAllArticles();

        assertEquals(articles, result);
    }

    @Test
    public void testGetArticleById() {
        UUID articleId = UUID.randomUUID();
        ArticleEntity article = new ArticleEntity();
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));

        ArticleEntity result = articleService.getArticleById(articleId);

        assertEquals(article, result);
    }

    @Test
    public void testGetArticleByIdNotFound() {
        UUID articleId = UUID.randomUUID();
        when(articleRepository.findById(articleId)).thenReturn(Optional.empty());

        assertThrows(ArticleNotFoundException.class, () -> articleService.getArticleById(articleId));
    }

    @Test
    public void testCreateArticle() {
        ArticleEntity article = new ArticleEntity();
        when(articleRepository.save(article)).thenReturn(article);

        articleService.createArticle(article);

        verify(articleRepository, times(1)).save(article);
    }

    @Test
    public void testUpdateArticle() {
        UUID articleId = UUID.randomUUID();
        ArticleEntity article = new ArticleEntity();
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));
        when(articleRepository.save(article)).thenReturn(article);

        articleService.updateArticle(articleId, article);

        verify(articleRepository, times(1)).save(article);
    }

    @Test
    public void testDeleteArticle() {
        UUID articleId = UUID.randomUUID();
        doNothing().when(articleRepository).deleteById(articleId);

        articleService.deleteArticle(articleId);

        verify(articleRepository, times(1)).deleteById(articleId);
    }
}