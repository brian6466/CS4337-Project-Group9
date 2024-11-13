package cs4337.group9.mediumwebsite.Repository;

import cs4337.group9.mediumwebsite.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}