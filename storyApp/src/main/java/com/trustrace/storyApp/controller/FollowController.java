package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.service.FollowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/follow")
@CrossOrigin(origins = "*")
public class FollowController {

    @Autowired
    private FollowService followService;
    private static final Logger logger = LoggerFactory.getLogger(FollowController.class);

    @DeleteMapping("/{authorId}")
    public ResponseEntity<Object> unfollowAuthor(@PathVariable String authorId, @RequestParam String userId) {
        try {
            followService.unfollowAuthor(userId, authorId);
            logger.info("User with id {} unfollowed author with id {}", userId, authorId);
            return ResponseEntity.ok().body("{\"message\": \"You have unfollowed this author.\"}");
        } catch (DataAccessException e) {
            logger.error("Error unfollowing author: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Failed to unfollow author.\"}");
        }
    }

    @PostMapping("/{authorId}")
    public ResponseEntity<Object> followAuthor(@PathVariable String authorId, @RequestParam String userId) {
        try {
            followService.followAuthor(userId, authorId);
            logger.info("User with id {} started following author with id {}", userId, authorId);
            return ResponseEntity.ok().body("{\"message\": \"You are now following this author.\"}");
        } catch (DataAccessException e) {
            logger.error("Error following author: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Failed to follow author.\"}");
        }
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<Object> getFollowedAuthors(@PathVariable String userId) {
        try {
            logger.info("Getting the following list of user with id {}", userId);
            return ResponseEntity.ok(followService.getFollowedAuthors(userId));
        } catch (DataAccessException e) {
            logger.error("Error retrieving followed authors: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Failed to retrieve followed authors.\"}");
        }
    }

    @GetMapping("/{userId}/isFollowing/{authorId}")
    public ResponseEntity<Object> isFollowing(@PathVariable String userId, @PathVariable String authorId) {
        try {
            logger.info("Checking whether User with id {} is following author with id {}", userId, authorId);
            boolean isFollowing = followService.isFollowing(userId, authorId);
            return ResponseEntity.ok(isFollowing);
        } catch (DataAccessException e) {
            logger.error("Error checking following status: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Failed to check following status.\"}");
        }
    }

    @GetMapping("/{userId}/count")
    public ResponseEntity<Object> getFollowCount(@PathVariable String userId) {
        try {
            int followingCount = followService.getFollowingCount(userId);
            int followersCount = followService.getFollowersCount(userId);
            logger.info("Getting following and followers count of user with id {} ", userId);
            return ResponseEntity.ok().body(Map.of(
                    "followingCount", followingCount,
                    "followersCount", followersCount
            ));
        } catch (DataAccessException e) {
            logger.error("Error retrieving follow counts: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Failed to retrieve follow counts.\"}");
        }
    }
}
