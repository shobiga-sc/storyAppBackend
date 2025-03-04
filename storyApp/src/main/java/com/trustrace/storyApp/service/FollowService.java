package com.trustrace.storyApp.service;

import com.trustrace.storyApp.repository.FollowRepository;
import com.trustrace.storyApp.model.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    public void followAuthor(String followerId, String authorId) {
        try {
            if (!followRepository.existsByFollowerIdAndAuthorId(followerId, authorId)) {
                followRepository.save(new Follow(followerId, authorId));
            }
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while following author", e);
        }
    }

    public void unfollowAuthor(String followerId, String authorId) {
        try {
            followRepository.deleteByFollowerIdAndAuthorId(followerId, authorId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while unfollowing author", e);
        }
    }

    public List<String> getFollowedAuthors(String followerId) {
        try {
            return followRepository.findByFollowerId(followerId)
                    .stream()
                    .map(Follow::getAuthorId)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while retrieving followed authors", e);
        }
    }

    public List<String> getFollowersOfAuthor(String authorId) {
        try {
            return followRepository.findByAuthorId(authorId)
                    .stream()
                    .map(Follow::getFollowerId)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while retrieving followers", e);
        }
    }

    public boolean isFollowing(String userId, String authorId) {
        try {
            return followRepository.existsByFollowerIdAndAuthorId(userId, authorId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while checking following status", e);
        }
    }

    public int getFollowingCount(String userId) {
        try {
            return followRepository.countByFollowerId(userId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while retrieving following count", e);
        }
    }

    public int getFollowersCount(String userId) {
        try {
            return followRepository.countByAuthorId(userId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while retrieving followers count", e);
        }
    }
}
