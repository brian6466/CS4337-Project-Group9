package cs4337.group9.userapi.Service;

import cs4337.group9.mediumwebsite.Service.UserService;
import cs4337.group9.mediumwebsite.Entity.UserEntity;
import cs4337.group9.mediumwebsite.Entity.AdminActionEntity;
import cs4337.group9.mediumwebsite.Exceptions.UserAlreadyExistsException;
import cs4337.group9.mediumwebsite.Exceptions.UserNotFoundException;
import cs4337.group9.mediumwebsite.Repostiory.UserRepository;
import cs4337.group9.mediumwebsite.Repostiory.AdminActionRepository;
import cs4337.group9.mediumwebsite.enums.Status;
import cs4337.group9.mediumwebsite.enums.Role;
import cs4337.group9.mediumwebsite.enums.Action;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdminActionRepository adminActionRepository;

    @InjectMocks
    private UserService userService;

    private UserEntity user;
    private UUID userId;
    private UUID adminId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        adminId = UUID.randomUUID();
        user = new UserEntity(userId, "testUser", "test@example.com", "password", Role.USER, Status.ACTIVE, "Test about", "profile.jpg", null, null);
    }

    @Test
    void testCreateUser_UserAlreadyExists_ThrowsException() {
        // Arrange
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Act & Assert
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            userService.createUser(user);
        });
        assertEquals("User already exists with email: test@example.com", exception.getMessage());
    }

    @Test
    void testCreateUser_Success() {
        // Arrange
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        // Act
        userService.createUser(user);

        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetUserById_UserNotFound_ThrowsException() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(userId);
        });
        assertEquals("User not found with id: " + userId, exception.getMessage());
    }

    @Test
    void testGetUserById_Success() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        UserEntity result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    void testUpdateUser_UserNotFound_ThrowsException() {
        // Arrange
        UserEntity updatedUser = new UserEntity(userId, "updatedUser", "updated@example.com", "newpassword", Role.USER, Status.ACTIVE, "Updated about", "updated.jpg", null, null);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(userId, updatedUser);
        });
        assertEquals("User not found with id: " + userId, exception.getMessage());
    }

    @Test
    void testUpdateUser_Success() {
        // Arrange
        UserEntity updatedUser = new UserEntity(userId, "updatedUser", "updated@example.com", "newpassword", Role.USER, Status.ACTIVE, "Updated about", "updated.jpg", null, null);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUser);

        // Act
        userService.updateUser(userId, updatedUser);

        // Assert
        verify(userRepository, times(1)).save(updatedUser);
        assertEquals(updatedUser.getUsername(), user.getUsername());
    }

    @Test
    void testDeleteUser_UserNotFound_ThrowsException() {
        // Arrange
        when(userRepository.existsById(userId)).thenReturn(false);

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(userId);
        });
        assertEquals("User not found with id: " + userId, exception.getMessage());
    }

    @Test
    void testDeleteUser_Success() {
        // Arrange
        when(userRepository.existsById(userId)).thenReturn(true);

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testBanUser_UserNotFound_ThrowsException() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.banUser(userId, adminId);
        });
        assertEquals("User not found with id: " + userId, exception.getMessage());
    }

    @Test
    void testBanUser_Success() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        // Act
        userService.banUser(userId, adminId);

        // Assert
        assertEquals(Status.BANNED, user.getStatus());
        verify(adminActionRepository, times(1)).save(any(AdminActionEntity.class));
    }

    @Test
    void testUnbanUser_UserNotFound_ThrowsException() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.unbanUser(userId, adminId);
        });
        assertEquals("User not found with id: " + userId, exception.getMessage());
    }

    @Test
    void testUnbanUser_Success() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        // Act
        userService.unbanUser(userId, adminId);

        // Assert
        assertEquals(Status.ACTIVE, user.getStatus());
        verify(adminActionRepository, times(1)).save(any(AdminActionEntity.class));
    }
}