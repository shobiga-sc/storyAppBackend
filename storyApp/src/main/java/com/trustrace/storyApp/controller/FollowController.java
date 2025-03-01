package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follow")
@CrossOrigin(origins = "*")
public class FollowController {

    @Autowired
    private FollowService followService;

    @DeleteMapping("/{authorId}")
    public ResponseEntity<Object> unfollowAuthor(@PathVariable String authorId, @RequestParam String userId) {
        followService.unfollowAuthor(userId, authorId);
        return ResponseEntity.ok().body("{\"message\": \"You have unfollowed this author.\"}");
    }

    @PostMapping("/{authorId}")
    public ResponseEntity<Object> followAuthor(@PathVariable String authorId, @RequestParam String userId) {
        followService.followAuthor(userId, authorId);
        return ResponseEntity.ok().body("{\"message\": \"You are now following this author.\"}");
    }


    @GetMapping("/{userId}/following")
    public ResponseEntity<List<String>> getFollowedAuthors(@PathVariable String userId) {
        return ResponseEntity.ok(followService.getFollowedAuthors(userId));
    }

    @GetMapping("/{userId}/isFollowing/{authorId}")
    public ResponseEntity<Boolean> isFollowing(@PathVariable String userId, @PathVariable String authorId) {
        boolean isFollowing = followService.isFollowing(userId, authorId);
        return ResponseEntity.ok(isFollowing);
    }
}

