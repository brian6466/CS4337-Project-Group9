package cs4337.group9.mediumwebsite.Controller;

import cs4337.group9.mediumwebsite.Entity.UserEntity;
import cs4337.group9.mediumwebsite.Service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/{followerId}/follow/{followingId}")
    public ResponseEntity<String> followUser(@PathVariable UUID followerId, @PathVariable UUID followingId) {
        String message = followService.followUser(followerId,followingId);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{followerId}/unfollow/{followingId}")
    public ResponseEntity<String> unfollowUser(@PathVariable UUID followerId, @PathVariable UUID followingId) {
        String message = followService.unfollowUser(followerId,followingId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<UserEntity>> getFollowers(@PathVariable UUID userId) {
        List<UserEntity> followers = followService.getFollowers(userId);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<List<UserEntity>> getFollowing(@PathVariable UUID userId) {
        List<UserEntity> following = followService.getFollowing(userId);
        return ResponseEntity.ok(following);
    }
}
