package cs4337.group9.contentapi.Service;
import cs4337.group9.contentapi.Entity.CommentEntity;
import cs4337.group9.contentapi.Repository.CommentRepository;
import cs4337.group9.contentapi.Exceptions.CommentNotFoundException;
import cs4337.group9.contentapi.Exceptions.UnauthorizedAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

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
        when(commentRepository.findByArticleId(articleId, pageable)).thenReturn(page);

        Page<CommentEntity> result = commentService.getAllComments(articleId, pageable);

        assertEquals(page, result);
    }

    @Test
    public void testGetCommentById() {
        UUID commentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        CommentEntity comment = new CommentEntity();
        comment.setUserId(userId);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        CommentEntity result = commentService.getComment(commentId, userId);

        assertEquals(comment, result);
    }

    @Test
    public void testGetCommentByIdNotFound() {
        UUID commentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () -> commentService.getComment(commentId, userId));
    }

    @Test
    public void testGetCommentByIdUnauthorized() {
        UUID commentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID differentUserId = UUID.randomUUID();
        CommentEntity comment = new CommentEntity();
        comment.setUserId(differentUserId);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        assertThrows(UnauthorizedAccessException.class, () -> commentService.getComment(commentId, userId));
    }

    @Test
    public void testCreateComment() {
        UUID articleId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "Test comment";
        CommentEntity comment = new CommentEntity();
        when(commentRepository.save(comment)).thenReturn(comment);

        CommentEntity result = commentService.createComment(articleId, userId, content);

        assertEquals(comment, result);
    }

    @Test
    public void testUpdateComment() {
        UUID commentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "Updated comment";
        CommentEntity comment = new CommentEntity();
        comment.setUserId(userId);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(commentRepository.save(comment)).thenReturn(comment);

        Optional<CommentEntity> result = commentService.updateComment(commentId, userId, content);

        assertEquals(Optional.of(comment), result);
    }

    @Test
    public void testDeleteComment() {
        UUID commentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        doNothing().when(commentRepository).deleteById(commentId);

        commentService.deleteComment(commentId, userId);

        verify(commentRepository, times(1)).deleteById(commentId);
    }
}