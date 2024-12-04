package cs4337.group9.contentapi.Controller;

import cs4337.group9.contentapi.Entity.CommentEntity;
import cs4337.group9.contentapi.Exceptions.ArticleNotFoundException;
import cs4337.group9.contentapi.Service.ArticleService;
import cs4337.group9.contentapi.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/article/{articleId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    //Check if articleId exists
    private void validArticle(UUID articleId){
        if (articleService.getArticleById(articleId) == null)
            throw new ArticleNotFoundException(articleId.toString());
    }

    @PostMapping
    public ResponseEntity<CommentEntity> createComment(
            @PathVariable UUID articleId,
            @RequestParam UUID userId,
            @RequestBody String content) {
        validArticle(articleId);
        CommentEntity comment = commentService.createComment(articleId, userId, content);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable UUID articleId,
            @PathVariable UUID commentId,
            @RequestParam UUID userId) {
        validArticle(articleId);
        String message = commentService.deleteComment(commentId, userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Optional<CommentEntity>> updateComment(
            @PathVariable UUID articleId,
            @PathVariable UUID commentId,
            @RequestParam UUID userId,
            @RequestBody String content) {
        validArticle(articleId);
        Optional<CommentEntity> update = commentService.updateComment(commentId, userId, content);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentEntity> getComment(
            @PathVariable UUID articleId,
            @PathVariable UUID commentId,
            @RequestParam UUID userId ) {
        validArticle(articleId);
        CommentEntity comment = commentService.getComment(commentId, userId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<CommentEntity>> getAllComments(
            @PathVariable UUID articleId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        validArticle(articleId);
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentEntity> comments = commentService.getAllComments(articleId, pageable);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}
