package cs4337.group9.mediumwebsite.Repostiory;

import cs4337.group9.mediumwebsite.Entity.AdminActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminActionRepository extends JpaRepository<AdminActionEntity, UUID> {
}