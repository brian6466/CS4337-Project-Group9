package cs4337.group9.mediumwebsite.Service;

import cs4337.group9.mediumwebsite.Entity.UserEntity;
import cs4337.group9.mediumwebsite.Entity.AdminActionEntity;
import cs4337.group9.mediumwebsite.Exceptions.UserAlreadyExistsException;
import cs4337.group9.mediumwebsite.Exceptions.UserNotFoundException;
import cs4337.group9.mediumwebsite.Repostiory.UserRepository;
import cs4337.group9.mediumwebsite.Repostiory.AdminActionRepository;
import cs4337.group9.mediumwebsite.enums.Status;
import cs4337.group9.mediumwebsite.enums.Action;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AdminActionRepository adminActionRepository;

    @Autowired
    public UserService(UserRepository userRepository, AdminActionRepository adminActionRepository) {
        this.userRepository = userRepository;
        this.adminActionRepository = adminActionRepository;
    }

    public void checkIfUserExists(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId.toString());
        }
    }

    public boolean userExistsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public void createUser(UserEntity user) {
        if (userExistsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        UUID generatedId = UUID.randomUUID();
        user.setId(generatedId);
        userRepository.save(user);
    }

    public UserEntity getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));
    }

    @Transactional
    public void updateUser(UUID userId, UserEntity updatedUser) {
        UserEntity existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());

        userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(UUID userId) {
        checkIfUserExists(userId);
        userRepository.deleteById(userId);
    }

    @Transactional
    public void banUser(UUID userId, UUID adminId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));
        user.setStatus(Status.BANNED);
        userRepository.save(user);
        logAdminAction(adminId, userId, Action.BAN);
    }

    @Transactional
    public void unbanUser(UUID userId, UUID adminId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
        logAdminAction(adminId, userId, Action.UNBAN);
    }

    private void logAdminAction(UUID adminId, UUID userId, Action action) {
        AdminActionEntity adminAction = new AdminActionEntity();
        adminAction.setId(UUID.randomUUID());
        adminAction.setAdminId(adminId);
        adminAction.setUserId(userId);
        adminAction.setAction(action);
        adminActionRepository.save(adminAction);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}