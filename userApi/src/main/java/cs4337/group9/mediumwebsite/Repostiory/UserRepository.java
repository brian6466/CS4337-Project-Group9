package cs4337.group9.mediumwebsite.Repostiory;

import cs4337.group9.mediumwebsite.Entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}