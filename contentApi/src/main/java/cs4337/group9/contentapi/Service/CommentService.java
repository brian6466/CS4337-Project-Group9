package cs4337.group9.contentapi.Service;

import cs4337.group9.contentapi.Entity.CommentEntity;
import cs4337.group9.contentapi.Exceptions.CommentNotFoundException;
import cs4337.group9.contentapi.Exceptions.UnauthorizedAccessException;
import cs4337.group9.contentapi.Repository.ArticleRepository;
import cs4337.group9.contentapi.Repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public CommentEntity createComment(UUID articleId, UUID userid, String content) {
        CommentEntity comment = new CommentEntity();
        comment.setArticle_id(articleId);
        comment.setUser_id(userid);
        comment.setContent(content);
        return commentRepository.save(comment);
    }

    public CommentEntity getComment(Long commentId, UUID userid) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId.toString()));

        if (comment.getUser_id().equals(userid))
            return comment;
        else throw new UnauthorizedAccessException(userid.toString());
    }

    @Transactional
    public Optional<CommentEntity> updateComment(Long commentId, UUID userid, String content) {
        CommentEntity comment = getComment(commentId, userid);
        comment.setContent(content);
        commentRepository.save(comment);
        return Optional.of(comment);
    }

    @Transactional
    public String deleteComment(Long commentId, UUID userid) {
        CommentEntity comment = getComment(commentId, userid);
        commentRepository.delete(comment);
        return String.format("Comment was successfully deleted, id (%s)", commentId);
    }

    public Page<CommentEntity> getAllComments(UUID articleId, Pageable pageable) {
        return commentRepository.findByArticleid(articleId, pageable);
    }
}
