package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.model.SavedStory;
import com.trustrace.storyApp.service.SavedStoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/savedStory")
@PreAuthorize("hasRole('USER') or  hasRole('ADMIN')")
@CrossOrigin(origins = "*")
public class SavedStoryController {
    @Autowired
    private SavedStoryService savedStoryService;

    private static final Logger logger = LoggerFactory.getLogger(SavedStoryController.class);

    @PostMapping("/save")
    public ResponseEntity<?> saveStory(@RequestBody SavedStory saveStory) {
        try {
            return ResponseEntity.ok(savedStoryService.saveStory(saveStory));
        } catch (Exception e) {
            logger.error("Error saving story", e);
            return ResponseEntity.internalServerError().body("Error saving story");
        }
    }

    @DeleteMapping("/delete/{userId}/{storyId}")
    public ResponseEntity<?> deleteSavedStory(@PathVariable String userId, @PathVariable String storyId) {
        try {
            savedStoryService.deleteSavedStory(userId, storyId);
            logger.info("User with id {} unsaved story with id {}", userId, storyId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting saved story", e);
            return ResponseEntity.internalServerError().body("Error deleting saved story");
        }
    }

    @GetMapping("/isSaved/{userId}/{storyId}")
    public ResponseEntity<Boolean> isStorySaved(@PathVariable String userId, @PathVariable String storyId) {
        try {
            logger.info("Checking whether user with id {} saved story with id {}", userId, storyId);
            return ResponseEntity.ok(savedStoryService.isStorySaved(userId, storyId));
        } catch (Exception e) {
            logger.error("Error checking if story is saved", e);
            return ResponseEntity.internalServerError().body(false);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> storySaved(@PathVariable String userId) {
        try {
            logger.info("Fetching saved stories for user with id {}", userId);
            return ResponseEntity.ok(savedStoryService.getStorySavedForUser(userId));
        } catch (Exception e) {
            logger.error("Error retrieving saved stories for user", e);
            return ResponseEntity.internalServerError().body("Error retrieving saved stories");
        }
    }
}