package cs4337.group9.mediumwebsite.Repostiory;

import cs4337.group9.mediumwebsite.Entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    boolean existsByFollowerIdAndFollowingId(UUID followerId, UUID followingId);
    List<FollowEntity> findByFollowerId(UUID followerId);
    List<FollowEntity> findByFollowingId(UUID followingId);
    void deleteByFollowerIdAndFollowingId(UUID followerId, UUID followingId);
}
