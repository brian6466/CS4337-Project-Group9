package cs4337.group9.contentapi.Controller;

import cs4337.group9.contentapi.Entity.ArticleEntity;
import cs4337.group9.contentapi.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleEntity> getArticle(@PathVariable UUID articleId) {
        ArticleEntity user = articleService.getArticleById(articleId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ArticleEntity>> getAllArticles() {
        List<ArticleEntity> articles = articleService.getAllArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> createArticle(@RequestBody ArticleEntity article) {
        articleService.createArticle(article);
        return new ResponseEntity<>("Article Successfully created!", HttpStatus.CREATED);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<String> deleteArticle(@PathVariable UUID articleId, @RequestParam UUID userId) {
        articleService.deleteArticle(articleId, userId);
        return new ResponseEntity<>("Article Successfully deleted!", HttpStatus.OK);
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<String> updateArticle(@PathVariable UUID articleId, @RequestBody ArticleEntity article) {
        articleService.updateArticle(articleId, article);
        return new ResponseEntity<>("Article Successfully updated!", HttpStatus.OK);
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<ArticleEntity>> getArticlesByAuthorId(@PathVariable UUID authorId) {
        List<ArticleEntity> articles = articleService.getArticlesByAuthorId(authorId);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }







}
