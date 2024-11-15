package cs4337.group9.userapi.Controller;

import cs4337.group9.mediumwebsite.enums.Role;
import cs4337.group9.mediumwebsite.Controller.UserController;
import cs4337.group9.mediumwebsite.Entity.UserEntity;
import cs4337.group9.mediumwebsite.Service.UserService;
import cs4337.group9.mediumwebsite.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController; // Inject the controller

    @Mock
    private UserService userService; // Mock the UserService

    private MockMvc mockMvc;
    private UserEntity user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize the mocks
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build(); // Setup MockMvc

        userId = UUID.randomUUID(); // Generate a test UUID for user
        UserEntity user =
                new UserEntity(UUID.randomUUID(), "testuser", "test@example.com", "password123", Role.USER, Status.ACTIVE, "About me", "profilePicUrl", null, null);        user.setPassword("password123");
        user.setRole(cs4337.group9.mediumwebsite.enums.Role.USER);  // Set role for the user
        user.setStatus(cs4337.group9.mediumwebsite.enums.Status.ACTIVE); // Set status for the user
    }

    @Test
    public void testGetUser() throws Exception {
        // Mock the service call
        when(userService.getUserById(userId)).thenReturn(user);

        // Perform GET request and verify response
        mockMvc.perform(get("/user/{userId}", userId))
                .andExpect(status().isOk()) // Expect OK status
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        // Verify that the service method was called
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    public void testGetUsers() throws Exception {
        // Mock the service call to return a list of users
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user));

        // Perform GET request to fetch all users
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(userId.toString()))
                .andExpect(jsonPath("$[0].username").value("testuser"))
                .andExpect(jsonPath("$[0].email").value("test@example.com"));

        // Verify that the service method was called
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void testCreateUser() throws Exception {
        // Create a JSON string for the user creation request
        String userJson = "{ \"username\": \"testuser\", \"email\": \"test@example.com\", \"password\": \"password123\" }";

        // Perform POST request to create the user
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("User created successfully!"));

        // Verify that the service method was called
        verify(userService, times(1)).createUser(any(UserEntity.class));
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Create a JSON string for the updated user
        String updatedUserJson = "{ \"username\": \"updateduser\", \"email\": \"updated@example.com\", \"password\": \"newpassword123\" }";

        // Perform PUT request to update the user
        mockMvc.perform(put("/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserJson))
                .andExpect(status().isOk())
                .andExpect(content().string("User updated successfully!"));

        // Verify that the service method was called
        verify(userService, times(1)).updateUser(eq(userId), any(UserEntity.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Perform DELETE request to delete the user
        mockMvc.perform(delete("/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully!"));

        // Verify that the service method was called
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    public void testBanUser() throws Exception {
        UUID adminId = UUID.randomUUID(); // Admin ID to ban user

        // Mock the service call
        doNothing().when(userService).banUser(eq(userId), eq(adminId));

        // Perform PUT request to ban the user
        mockMvc.perform(put("/user/ban/{userId}", userId)
                        .param("adminId", adminId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("User banned successfully!"));

        // Verify that the service method was called
        verify(userService, times(1)).banUser(eq(userId), eq(adminId));
    }

    @Test
    public void testUnbanUser() throws Exception {
        UUID adminId = UUID.randomUUID(); // Admin ID to unban user

        // Mock the service call
        doNothing().when(userService).unbanUser(eq(userId), eq(adminId));

        // Perform PUT request to unban the user
        mockMvc.perform(put("/user/unban/{userId}", userId)
                        .param("adminId", adminId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("User unbanned successfully!"));

        // Verify that the service method was called
        verify(userService, times(1)).unbanUser(eq(userId), eq(adminId));
    }
}