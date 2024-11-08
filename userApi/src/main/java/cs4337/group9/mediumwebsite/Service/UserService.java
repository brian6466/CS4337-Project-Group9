package cs4337.group9.mediumwebsite.Service;

import cs4337.group9.mediumwebsite.Entity.User;
import cs4337.group9.mediumwebsite.Repostiory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User createUser(User user) {
        //Check if user exists first
        userRepository.save(user);
        return user;
    }
}