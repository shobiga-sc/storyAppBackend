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
    public ResponseEntity<Story> createStory(@RequestBody Story story){
       logger.info("Creating a new story with id {}", story.getId());
       Story savedStory = storyService.createStory(story);
       return ResponseEntity.ok(savedStory);
   }

    @GetMapping("/{userId}/{status}")
    public List<Story> getStories(@PathVariable String userId, @PathVariable StoryStatus status) {
        logger.info("Fetching stories with status {} of user with id {}", status, userId);
        return storyService.getStoriesByUserIdAndStatus(userId, status);
    }

    @GetMapping("/{storyId}")
    public ResponseEntity<?> getStoryById(@PathVariable String storyId){
        logger.info("Fetching story with id {} ", storyId);
       return storyService.getStoryById(storyId)
               .map(story -> ResponseEntity.ok(story))
               .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/published/all")
    public List<Story> getAllPublishedStories(){
        logger.info("Fetching all published stories");
       return storyService.getAllPublishedStories();
    }

    @PatchMapping("/{storyId}")
    public ResponseEntity<?> updateStory(@PathVariable String storyId, @RequestBody Story updatedStory) {
        Optional<Story> updated = storyService.updateStory(storyId, updatedStory);
        logger.info("Updating story with id {} ", storyId);
        return updated
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{storyId}")
    public ResponseEntity<?> deleteStory(@PathVariable String storyId) {
        boolean deleted = storyService.deleteStory(storyId);
        logger.info("Deleting story with id {} ", storyId);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }









}
