package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.model.Story;
import com.trustrace.storyApp.model.StoryStatus;
import com.trustrace.storyApp.repository.StoryRepository;
import com.trustrace.storyApp.service.StoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/story")
@PreAuthorize("hasRole('USER') or  hasRole('ADMIN')")
@CrossOrigin(origins = "*")
public class StoryController {
    @Autowired
    private StoryService storyService;

    @Autowired
    private StoryRepository storyRepository;

    private static final Logger logger = LoggerFactory.getLogger(StoryController.class);

    @PostMapping("/create")
    public ResponseEntity<?> createStory(@RequestBody Story story) {
        try {
            logger.info("Creating a new story with id {}", story.getId());
            Story savedStory = storyService.createStory(story);
            return ResponseEntity.ok(savedStory);
        } catch (Exception e) {
            logger.error("Error creating story", e);
            return ResponseEntity.internalServerError().body("Error creating story");
        }
    }

    @GetMapping("/{userId}/{status}")
    public ResponseEntity<?> getStories(@PathVariable String userId, @PathVariable StoryStatus status) {
        try {
            logger.info("Fetching stories with status {} of user with id {}", status, userId);
            return ResponseEntity.ok(storyService.getStoriesByUserIdAndStatus(userId, status));
        } catch (Exception e) {
            logger.error("Error fetching stories", e);
            return ResponseEntity.internalServerError().body("Error fetching stories");
        }
    }

    @GetMapping("/{storyId}")
    public ResponseEntity<?> getStoryById(@PathVariable String storyId) {
        try {
            logger.info("Fetching story with id {}", storyId);
            return storyService.getStoryById(storyId)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error fetching story", e);
            return ResponseEntity.internalServerError().body("Error fetching story");
        }
    }

    @GetMapping("/published/all")
    public ResponseEntity<?> getAllPublishedStories() {
        try {
            logger.info("Fetching all published stories");
            return ResponseEntity.ok(storyService.getAllPublishedStories());
        } catch (Exception e) {
            logger.error("Error fetching published stories", e);
            return ResponseEntity.internalServerError().body("Error fetching published stories");
        }
    }

    @PatchMapping("/{storyId}")
    public ResponseEntity<?> updateStory(@PathVariable String storyId, @RequestBody Story updatedStory) {
        try {
            logger.info("Updating story with id {}", storyId);
            return storyService.updateStory(storyId, updatedStory)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error updating story", e);
            return ResponseEntity.internalServerError().body("Error updating story");
        }
    }

    @DeleteMapping("/{storyId}")
    public ResponseEntity<?> deleteStory(@PathVariable String storyId) {
        try {
            logger.info("Deleting story with id {}", storyId);
            return storyService.deleteStory(storyId) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error deleting story", e);
            return ResponseEntity.internalServerError().body("Error deleting story");
        }
    }
}