package cs4337.group9.userapi.Controller;

import cs4337.group9.mediumwebsite.Controller.FollowController;
import cs4337.group9.mediumwebsite.Entity.UserEntity;
import cs4337.group9.mediumwebsite.Service.FollowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FollowControllerTest {

    @InjectMocks
    private FollowController followController;

    @Mock
    private FollowService followService;

    private UUID followerId;
    private UUID followingId;
    private UUID userId;
    private List<UserEntity> mockUsers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        followerId = UUID.randomUUID();
        followingId = UUID.randomUUID();
        userId = UUID.randomUUID();

        // Mock list of users
        UserEntity user1 = new UserEntity(UUID.randomUUID(), "user1", "user1@example.com", "password", null, null, null, null, null, null);
        UserEntity user2 = new UserEntity(UUID.randomUUID(), "user2", "user2@example.com", "password", null, null, null, null, null, null);
        mockUsers = List.of(user1, user2);
    }

    @Test
    void testFollowUser_Success() {
        // Arrange
        String successMessage = "User followed successfully!";
        when(followService.followUser(followerId, followingId)).thenReturn(successMessage);

        // Act
        ResponseEntity<String> response = followController.followUser(followerId, followingId);

        // Assert
        assertNotNull(response);
        assertEquals(successMessage, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(followService, times(1)).followUser(followerId, followingId);
    }

    @Test
    void testUnfollowUser_Success() {
        // Arrange
        String successMessage = "User unfollowed successfully!";
        when(followService.unfollowUser(followerId, followingId)).thenReturn(successMessage);

        // Act
        ResponseEntity<String> response = followController.unfollowUser(followerId, followingId);

        // Assert
        assertNotNull(response);
        assertEquals(successMessage, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(followService, times(1)).unfollowUser(followerId, followingId);
    }

    @Test
    void testGetFollowers_Success() {
        // Arrange
        when(followService.getFollowers(userId)).thenReturn(mockUsers);

        // Act
        ResponseEntity<List<UserEntity>> response = followController.getFollowers(userId);

        // Assert
        assertNotNull(response);
        assertEquals(mockUsers, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(followService, times(1)).getFollowers(userId);
    }

    @Test
    void testGetFollowing_Success() {
        // Arrange
        when(followService.getFollowing(userId)).thenReturn(mockUsers);

        // Act
        ResponseEntity<List<UserEntity>> response = followController.getFollowing(userId);

        // Assert
        assertNotNull(response);
        assertEquals(mockUsers, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(followService, times(1)).getFollowing(userId);
    }
}