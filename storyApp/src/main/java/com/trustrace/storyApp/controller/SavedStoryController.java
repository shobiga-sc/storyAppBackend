package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.model.SavedStory;
import com.trustrace.storyApp.model.Story;
import com.trustrace.storyApp.service.SavedStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/savedStory")
@PreAuthorize("hasRole('USER') or  hasRole('ADMIN')")
@CrossOrigin(origins = "*")
public class SavedStoryController {
  @Autowired
    private SavedStoryService savedStoryService;

    @PostMapping("/save")
    public ResponseEntity<?> saveStory(@RequestBody SavedStory saveStory) {
        return ResponseEntity.ok(savedStoryService.saveStory(saveStory));
    }

    @DeleteMapping("/delete/{userId}/{storyId}")
    public ResponseEntity<?> deleteSavedStory(@PathVariable String userId, @PathVariable String storyId) {
        savedStoryService.deleteSavedStory(userId, storyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/isSaved/{userId}/{storyId}")
    public ResponseEntity<Boolean> isStorySaved(@PathVariable String userId, @PathVariable String storyId) {
        return ResponseEntity.ok(savedStoryService.isStorySaved(userId, storyId));
    }

}
