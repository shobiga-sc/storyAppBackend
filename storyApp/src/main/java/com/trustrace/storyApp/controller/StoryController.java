package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.model.Story;
import com.trustrace.storyApp.model.StoryStatus;
import com.trustrace.storyApp.repository.StoryRepository;
import com.trustrace.storyApp.service.StoryService;
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

   @PostMapping("/create")
    public ResponseEntity<Story> createStory(@RequestBody Story story){
       Story savedStory = storyService.createStory(story);
       return ResponseEntity.ok(savedStory);
   }

    @GetMapping("/{userId}/{status}")
    public List<Story> getStories(@PathVariable String userId, @PathVariable StoryStatus status) {
        return storyService.getStoriesByUserIdAndStatus(userId, status);
    }

    @GetMapping("/{storyId}")
    public ResponseEntity<?> getStoryById(@PathVariable String storyId){
       return storyService.getStoryById(storyId)
               .map(story -> ResponseEntity.ok(story))
               .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/published/all")
    public List<Story> getAllPublishedStories(){
       return storyService.getAllPublishedStories();
    }









}
