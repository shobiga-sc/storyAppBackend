package com.trustrace.storyApp.service;



import com.trustrace.storyApp.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import com.trustrace.storyApp.model.Follow;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    public void followAuthor(String followerId, String authorId) {
        if (!followRepository.existsByFollowerIdAndAuthorId(followerId, authorId)) {
            followRepository.save(new Follow(followerId, authorId));
        }
    }

    public void unfollowAuthor(String followerId, String authorId) {
        followRepository.deleteByFollowerIdAndAuthorId(followerId, authorId);
    }

    public List<String> getFollowedAuthors(String followerId) {
        return followRepository.findByFollowerId(followerId)
                .stream()
                .map(Follow::getAuthorId)
                .collect(Collectors.toList());
    }

    public List<String> getFollowersOfAuthor(String authorId) {
        return followRepository.findByAuthorId(authorId)
                .stream()
                .map(Follow::getFollowerId)
                .collect(Collectors.toList());
    }

    public boolean isFollowing(String userId, String authorId) {
        return followRepository.existsByFollowerIdAndAuthorId(userId, authorId);
    }

    public int getFollowingCount(String userId) {
        return followRepository.countByFollowerId(userId);
    }

    public int getFollowersCount(String userId) {
        return followRepository.countByAuthorId(userId);
    }

}
