package cs4337.group9.contentapi.Controller;

import cs4337.group9.contentapi.Entity.CommentEntity;
import cs4337.group9.contentapi.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/article/{articleId}/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentEntity> createComment(
            @PathVariable Long articleId,
            @RequestParam UUID userId,
            @RequestBody String content) {
        CommentEntity comment = commentService.createComment(articleId, userId, content);
        return ResponseEntity.ok(comment);
    }


}
