package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.model.SavedStory;
import com.trustrace.storyApp.model.Story;
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

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);


    @PostMapping("/save")
    public ResponseEntity<?> saveStory(@RequestBody SavedStory saveStory) {

        return ResponseEntity.ok(savedStoryService.saveStory(saveStory));
    }

    @DeleteMapping("/delete/{userId}/{storyId}")
    public ResponseEntity<?> deleteSavedStory(@PathVariable String userId, @PathVariable String storyId) {
        savedStoryService.deleteSavedStory(userId, storyId);
        logger.info("user with id {} unsaved story with id {}", userId, storyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/isSaved/{userId}/{storyId}")
    public ResponseEntity<Boolean> isStorySaved(@PathVariable String userId, @PathVariable String storyId) {
        logger.info("Checking whether user with id {} saved story with id {}", userId, storyId);
        return ResponseEntity.ok(savedStoryService.isStorySaved(userId, storyId));
    }


    @GetMapping("/{userId}")
    public ResponseEntity<Optional<List<SavedStory>>> storySaved(@PathVariable String userId) {
        logger.info("user with id {} saved story with id {}", userId);
        return ResponseEntity.ok(savedStoryService.getStorySavedForUSer(userId));
    }


}
