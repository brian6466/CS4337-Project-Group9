package cs4337.group9.userapi.Service;

import cs4337.group9.mediumwebsite.Service.FollowService;
import cs4337.group9.mediumwebsite.Entity.FollowEntity;
import cs4337.group9.mediumwebsite.Entity.UserEntity;
import cs4337.group9.mediumwebsite.Exceptions.UserNotFoundException;
import cs4337.group9.mediumwebsite.Repostiory.FollowRepository;
import cs4337.group9.mediumwebsite.Repostiory.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FollowServiceTest {

    @Mock
    private FollowRepository followRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FollowService followService;

    private UUID followerId;
    private UUID followingId;
    private UserEntity follower;
    private UserEntity following;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        followerId = UUID.randomUUID();
        followingId = UUID.randomUUID();

        follower = new UserEntity(followerId, "follower", "follower@example.com", "password", null, null, null, null, null, null);
        following = new UserEntity(followingId, "following", "following@example.com", "password", null, null, null, null, null, null);
    }

    @Test
    void testFollowUser_UserNotFound_ThrowsException() {
        // Arrange
        when(userRepository.findById(followerId)).thenReturn(Optional.empty());
        when(userRepository.findById(followingId)).thenReturn(Optional.of(following));

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            followService.followUser(followerId, followingId);
        });

        assertEquals("User not found with ID: " + followerId, exception.getMessage());
        verify(followRepository, never()).save(any(FollowEntity.class));
    }

    @Test
    void testFollowUser_Success() {
        // Arrange
        when(userRepository.findById(followerId)).thenReturn(Optional.of(follower));
        when(userRepository.findById(followingId)).thenReturn(Optional.of(following));
        when(followRepository.save(any(FollowEntity.class))).thenReturn(new FollowEntity(followerId, followingId));

        // Act
        String result = followService.followUser(followerId, followingId);

        // Assert
        assertEquals("User followed successfully", result);
        verify(followRepository, times(1)).save(any(FollowEntity.class));
    }

    @Test
    void testUnfollowUser_UserNotFound_ThrowsException() {
        // Arrange
        when(userRepository.findById(followerId)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            followService.unfollowUser(followerId, followingId);
        });

        assertEquals("User not found with ID: " + followerId, exception.getMessage());
        verify(followRepository, never()).deleteByFollowerIdAndFollowingId(any(UUID.class), any(UUID.class));
    }

    @Test
    void testUnfollowUser_Success() {
        // Arrange
        when(userRepository.findById(followerId)).thenReturn(Optional.of(follower));
        when(userRepository.findById(followingId)).thenReturn(Optional.of(following));

        // Act
        String result = followService.unfollowUser(followerId, followingId);

        // Assert
        assertEquals("User unfollowed successfully", result);
        verify(followRepository, times(1)).deleteByFollowerIdAndFollowingId(followerId, followingId);
    }

    @Test
    void testGetFollowers_UserNotFound_ThrowsException() {
        // Arrange
        when(userRepository.findById(followingId)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            followService.getFollowers(followingId);
        });

        assertEquals("User not found with ID: " + followingId, exception.getMessage());
        verify(followRepository, never()).findByFollowingId(any(UUID.class));
    }

    @Test
    void testGetFollowers_Success() {
        // Arrange
        FollowEntity follow1 = new FollowEntity(UUID.randomUUID(), followingId);
        FollowEntity follow2 = new FollowEntity(UUID.randomUUID(), followingId);

        when(userRepository.findById(followingId)).thenReturn(Optional.of(following));
        when(followRepository.findByFollowingId(followingId)).thenReturn(List.of(follow1, follow2));

        // Act
        List<UserEntity> followers = followService.getFollowers(followingId);

        // Assert
        assertNotNull(followers);
        assertEquals(2, followers.size());
    }

    @Test
    void testGetFollowing_UserNotFound_ThrowsException() {
        // Arrange
        when(userRepository.findById(followerId)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            followService.getFollowing(followerId);
        });

        assertEquals("User not found with ID: " + followerId, exception.getMessage());
        verify(followRepository, never()).findByFollowerId(any(UUID.class));
    }

    @Test
    void testGetFollowing_Success() {
        // Arrange
        FollowEntity follow1 = new FollowEntity(followerId, UUID.randomUUID());
        FollowEntity follow2 = new FollowEntity(followerId, UUID.randomUUID());

        when(userRepository.findById(followerId)).thenReturn(Optional.of(follower));
        when(followRepository.findByFollowerId(followerId)).thenReturn(List.of(follow1, follow2));

        // Act
        List<UserEntity> following = followService.getFollowing(followerId);

        // Assert
        assertNotNull(following);
        assertEquals(2, following.size());
    }
}