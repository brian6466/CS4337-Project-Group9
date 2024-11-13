package cs4337.group9.mediumwebsite.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "followers")
public class FollowEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "follower_id", nullable = false)
    private UUID followerId;

    @Column(name = "following_id", nullable = false)
    private UUID followingId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public FollowEntity(UUID followerId, UUID followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }

}
