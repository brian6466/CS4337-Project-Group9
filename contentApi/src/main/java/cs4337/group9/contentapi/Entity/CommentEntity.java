package cs4337.group9.contentapi.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "article_id", nullable = false)
    private Long article_id;

    @Column(name = "user_id", nullable = false)
    private UUID user_id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at = LocalDateTime.now();
}
