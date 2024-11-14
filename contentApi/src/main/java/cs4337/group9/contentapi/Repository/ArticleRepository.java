package cs4337.group9.contentapi.Repository;

import cs4337.group9.contentapi.Entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {

    List<ArticleEntity> findByAuthorId(UUID authorId);
}
