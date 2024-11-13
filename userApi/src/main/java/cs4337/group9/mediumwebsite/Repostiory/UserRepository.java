package cs4337.group9.mediumwebsite.Repostiory;

import cs4337.group9.mediumwebsite.Entity.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    @Override
    Optional<UserEntity> findById(UUID userId);

    Optional<UserEntity> findByEmail(String email);

}