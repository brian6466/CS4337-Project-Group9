package cs4337.group9.mediumwebsite.Service;

import cs4337.group9.mediumwebsite.Entity.Follow;
import cs4337.group9.mediumwebsite.Entity.User;
import cs4337.group9.mediumwebsite.Repostiory.FollowRepository;
import cs4337.group9.mediumwebsite.Repostiory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    public String followUser(UUID followerId, UUID followingId) {
        if (followerId.equals(followingId))
            throw new IllegalArgumentException("Cannot follow themself");

        if (followRepository.existsByFollowerIdAndFollowingId(followerId, followingId))
            return getFollowMessage(followerId, followingId, "is already following");

        User follower = userRepository.findById(followerId).orElseThrow (() -> new IllegalArgumentException("FollowerId not found"));
        User following = userRepository.findById(followingId).orElseThrow (() -> new IllegalArgumentException("FollowingId not found"));

        Follow followEntity = new Follow(followerId,followingId);
        followRepository.save(followEntity);

        return String.format("%s (%s) has successfully followed %s (%s)",
                follower.getUsername(), follower.getId(), following.getUsername(), following.getId());
    }

    public String unfollowUser(UUID followerId, UUID followingId) {
        if (!followRepository.existsByFollowerIdAndFollowingId(followerId, followingId))
            return getFollowMessage(followerId, followingId, "is already not following");

        User follower = userRepository.findById(followerId).orElseThrow (() -> new IllegalArgumentException("FollowerId not found"));
        User following = userRepository.findById(followingId).orElseThrow (() -> new IllegalArgumentException("FollowingId not found"));

        followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);

        return String.format("%s (%s) has successfully unfollowed %s (%s)",
                follower.getUsername(), follower.getId(), following.getUsername(), following.getId());
    }

    private String getFollowMessage(UUID followerId, UUID followingId, String response) {
        User follower = userRepository.findById(followerId).orElseThrow (() -> new IllegalArgumentException("FollowerId not found"));
        User following = userRepository.findById(followingId).orElseThrow (() -> new IllegalArgumentException("FollowingId not found"));

        return String.format("%s (%s) %s %s (%s)",
                follower.getUsername(), follower.getId(), response, following.getUsername(), following.getId());
    }


    public List<User> getFollowers(UUID userId) {
        List<Follow> relationship = followRepository.findByFollowingId(userId);

        return relationship.stream().map(follow -> userRepository.findById(follow.getFollowerId()).orElse(null)).
                collect(Collectors.toList());
    }

    public List<User> getFollowing(UUID userId) {
        List<Follow> relationship = followRepository.findByFollowerId(userId);

        return relationship.stream().map(follow -> userRepository.findById(follow.getFollowingId()).orElse(null)).
                collect(Collectors.toList());
    }
}
