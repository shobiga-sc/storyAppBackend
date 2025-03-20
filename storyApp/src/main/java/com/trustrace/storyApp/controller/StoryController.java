package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.dto.StoryDTO;
import com.trustrace.storyApp.model.Story;
import com.trustrace.storyApp.model.StoryStatus;
import com.trustrace.storyApp.model.User;
import com.trustrace.storyApp.repository.StoryRepository;
import com.trustrace.storyApp.service.StoryService;
import com.trustrace.storyApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private UserService userService;

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

    @GetMapping(value = "/user/{storyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStoryById(@PathVariable String storyId, @RequestParam String userId) {
        try {
            Story story = storyService.getStoryById(storyId)
                    .orElseThrow(() -> new RuntimeException("Story not found"));

            User user = userService.getUserById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            boolean isAdmin = user.getRoles().stream()
                    .anyMatch(role -> role.getName().name().equalsIgnoreCase("ROLE_ADMIN"));

            boolean isAuthor = user.getId().equals(story.getAuthorId());

            boolean isPaidStory = story.isPaid() && !isAdmin && !isAuthor;
            boolean isEligibleForFreeRead = (user.getPrimeSubscriptionExpiry() == null ||
                    user.getSignUpDate().isBefore(user.getPrimeSubscriptionExpiry().atStartOfDay()))
                    && user.getFreeRead().size() < 3
                    && !user.isPrimeSubscriber();
            boolean hasAccess = user.isPrimeSubscriber() || user.getFreeRead().contains(storyId);

            boolean shouldBlur = isPaidStory && !hasAccess && !isEligibleForFreeRead;


            if (!isAdmin && !isAuthor && !shouldBlur && isEligibleForFreeRead && !user.getFreeRead().contains(storyId)) {
                user.getFreeRead().add(storyId);
                userService.updateFreeRead(user.getId(), user.getFreeRead());
            }

            StoryDTO response = new StoryDTO(story, shouldBlur, isAdmin || isAuthor);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("story", response);
            responseBody.put("shouldBlur", shouldBlur);

            return ResponseEntity.ok(responseBody);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred: " + e.getMessage()));
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

    @DeleteMapping("/deleteByUser/{userId}")
    public ResponseEntity<?> deleteStoriesByUserId(@PathVariable String userId) {
        try {
            logger.info("Deleting all stories for user with id {}", userId);
            boolean deleted = storyService.deleteStoriesByUserId(userId);
            return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error deleting stories", e);
            return ResponseEntity.internalServerError().body("Error deleting stories");
        }
    }

}