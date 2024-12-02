package cs4337.group9.mediumwebsite.Service;

import cs4337.group9.mediumwebsite.DTO.UserDTO;
import cs4337.group9.mediumwebsite.Entity.UserEntity;
import cs4337.group9.mediumwebsite.Entity.AdminActionEntity;
import cs4337.group9.mediumwebsite.Exceptions.UserAlreadyExistsException;
import cs4337.group9.mediumwebsite.Exceptions.UserNotFoundException;
import cs4337.group9.mediumwebsite.Repostiory.UserRepository;
import cs4337.group9.mediumwebsite.Repostiory.AdminActionRepository;
import cs4337.group9.mediumwebsite.Enum.Status;
import cs4337.group9.mediumwebsite.Enum.Action;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AdminActionRepository adminActionRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, AdminActionRepository adminActionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.adminActionRepository = adminActionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO userEntityToUserDto (UserEntity userEntity){
        UserDTO dto = new UserDTO();
        dto.setUserId(userEntity.getId());
        dto.setUsername(userEntity.getUsername());
        dto.setEmail(userEntity.getEmail());
        dto.setAbout(userEntity.getAbout());
        dto.setProfilePicture(userEntity.getProfilePicture());
        dto.setRole(userEntity.getRole());
        dto.setStatus(userEntity.getStatus());
        return dto;
    }

    public List<UserDTO> userEntitiesToUserDtos(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(this::userEntityToUserDto)
                .collect(Collectors.toList());
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public UserDTO getUserDtoById(UUID userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));
        return userEntityToUserDto(userEntity);
    }

    public UserEntity getUserEntityByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
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

    public List<UserDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return userEntitiesToUserDtos(users);
    }
}