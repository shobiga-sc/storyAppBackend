package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.model.Story;
import com.trustrace.storyApp.repository.StoryRepository;
import com.trustrace.storyApp.service.StoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping
    public ResponseEntity<List<Story>> getStories() {
        List<Story> stories = storyRepository.findAll();
        return ResponseEntity.ok(stories);
    }
}
