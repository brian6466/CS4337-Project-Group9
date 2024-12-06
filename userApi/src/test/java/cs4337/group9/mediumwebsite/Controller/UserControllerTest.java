package cs4337.group9.mediumwebsite.Controller;

import cs4337.group9.mediumwebsite.DTO.UserDTO;
import cs4337.group9.mediumwebsite.DTO.ValidationResponse;
import cs4337.group9.mediumwebsite.Entity.UserEntity;
import cs4337.group9.mediumwebsite.Exceptions.UserAlreadyExistsException;
import cs4337.group9.mediumwebsite.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTO mockUserDTO;
    private UserEntity mockUser;
    private UUID userId;

    @BeforeEach
    public void setUp() {
        userId = UUID.randomUUID();
        mockUserDTO = new UserDTO();
        mockUserDTO.setUserId(userId);
        mockUserDTO.setEmail("test@example.com");

        mockUser = new UserEntity();
        mockUser.setId(userId);
        mockUser.setEmail("test@example.com");
    }

    @Test
    public void testGetUser_Success() {
        when(userService.getUserDtoById(userId)).thenReturn(mockUserDTO);

        ResponseEntity<UserDTO> response = userController.getUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUserDTO, response.getBody());
        verify(userService).getUserDtoById(userId);
    }

    @Test
    public void testGetUsers_Success() {
        List<UserDTO> mockUsers = Arrays.asList(mockUserDTO);
        when(userService.getAllUsers()).thenReturn(mockUsers);

        ResponseEntity<List<UserDTO>> response = userController.getUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUsers, response.getBody());
        verify(userService).getAllUsers();
    }

    @Test
    public void testCreateUser_Success() {
        doNothing().when(userService).createUser(mockUser);

        ResponseEntity<String> response = userController.createUser(mockUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User created successfully!", response.getBody());
        verify(userService).createUser(mockUser);
    }

    @Test
    public void testCreateUser_AlreadyExists() {
        doThrow(new UserAlreadyExistsException(mockUser.getEmail()))
                .when(userService).createUser(mockUser);

        assertThrows(UserAlreadyExistsException.class, () -> {
            userController.createUser(mockUser);
        });
    }

    @Test
    public void testUpdateUser_Success() {
        doNothing().when(userService).updateUser(userId, mockUser);

        ResponseEntity<String> response = userController.updateUser(userId, mockUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User updated successfully!", response.getBody());
        verify(userService).updateUser(userId, mockUser);
    }

    @Test
    public void testDeleteUser_Success() {
        doNothing().when(userService).deleteUser(userId);

        ResponseEntity<String> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully!", response.getBody());
        verify(userService).deleteUser(userId);
    }

    @Test
    public void testBanUser_Success() {
        UUID adminId = UUID.randomUUID();
        doNothing().when(userService).banUser(userId, adminId);

        ResponseEntity<String> response = userController.banUser(userId, adminId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User banned successfully!", response.getBody());
        verify(userService).banUser(userId, adminId);
    }

    @Test
    public void testUnbanUser_Success() {
        UUID adminId = UUID.randomUUID();
        doNothing().when(userService).unbanUser(userId, adminId);

        ResponseEntity<String> response = userController.unbanUser(userId, adminId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User unbanned successfully!", response.getBody());
        verify(userService).unbanUser(userId, adminId);
    }


    @Test
    public void testValidateUser_Failure() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("password", "wrongpassword");

        ValidationResponse invalidResponse = new ValidationResponse();
        invalidResponse.setValid(false);

        when(userService.validateCredentials("test@example.com", "wrongpassword"))
                .thenReturn(invalidResponse);

        ResponseEntity<ValidationResponse> response = userController.validateUser(credentials);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertFalse(response.getBody().isValid());
        verify(userService).validateCredentials("test@example.com", "wrongpassword");
    }
}