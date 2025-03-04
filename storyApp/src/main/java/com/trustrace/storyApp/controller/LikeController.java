package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.model.Like;
import com.trustrace.storyApp.service.LikeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@CrossOrigin(origins = "*")
public class LikeController {

    @Autowired
    private LikeService likeService;

    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);

    @PostMapping("/like")
    public ResponseEntity<?> likeStory(@RequestBody Like like) {
        try {
            Like savedLike = likeService.likeStory(like);
            return ResponseEntity.ok(savedLike);
        } catch (Exception e) {
            logger.error("Error liking story: {}", e.getMessage());
            return ResponseEntity.status(500).body("An error occurred while liking the story.");
        }
    }

    @DeleteMapping("/unlike/{userId}/{storyId}")
    public ResponseEntity<?> unlikeStory(@PathVariable String userId, @PathVariable String storyId) {
        try {
            likeService.unlikeStory(userId, storyId);
            logger.info("User with id {} unliked story with id {}", userId, storyId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error unliking story: {}", e.getMessage());
            return ResponseEntity.status(500).body("An error occurred while unliking the story.");
        }
    }

    @GetMapping("/isLiked/{userId}/{storyId}")
    public ResponseEntity<?> isStoryLiked(@PathVariable String userId, @PathVariable String storyId) {
        try {
            boolean isLiked = likeService.isStoryLiked(userId, storyId);
            logger.info("Checked if user with id {} liked story with id {}", userId, storyId);
            return ResponseEntity.ok(isLiked);
        } catch (Exception e) {
            logger.error("Error checking if story is liked: {}", e.getMessage());
            return ResponseEntity.status(500).body("An error occurred while checking the like status.");
        }
    }
}