package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.model.Like;
import com.trustrace.storyApp.service.LikeService;
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

    @PostMapping("/like")
    public ResponseEntity<Like> likeStory(@RequestBody Like like) {
        Like savedLike = likeService.likeStory(like);
        return ResponseEntity.ok(savedLike);
    }

    @DeleteMapping("/unlike/{userId}/{storyId}")
    public ResponseEntity<Void> unlikeStory(@PathVariable String userId, @PathVariable String storyId) {
        likeService.unlikeStory(userId, storyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/isLiked/{userId}/{storyId}")
    public ResponseEntity<Boolean> isStoryLiked(@PathVariable String userId, @PathVariable String storyId) {
        boolean isLiked = likeService.isStoryLiked(userId, storyId);
        return ResponseEntity.ok(isLiked);
    }
}