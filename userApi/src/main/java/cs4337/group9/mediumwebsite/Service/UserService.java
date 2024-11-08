package cs4337.group9.mediumwebsite.Service;

import cs4337.group9.mediumwebsite.Entity.User;
import cs4337.group9.mediumwebsite.Exceptions.UserAlreadyExistsException;
import cs4337.group9.mediumwebsite.Exceptions.UserNotFoundException;
import cs4337.group9.mediumwebsite.Repostiory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean userExistsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public void createUser(User user) {
        if (userExistsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }

        userRepository.save(user);
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));
    }
}