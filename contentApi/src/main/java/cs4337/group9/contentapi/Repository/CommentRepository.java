package cs4337.group9.contentapi.Repository;

import cs4337.group9.contentapi.Entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByArticleid(Long article_id);
}
