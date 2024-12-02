package cs4337.group9.contentapi.Repository;

import cs4337.group9.contentapi.Entity.ShareEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ShareRepository extends JpaRepository<ShareEntity, UUID> {
    List<ShareEntity> findByArticleId(UUID articleId);
}
