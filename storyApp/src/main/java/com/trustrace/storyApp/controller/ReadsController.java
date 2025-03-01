package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.service.ReadsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reads")
@CrossOrigin(origins = "*")
public class ReadsController {

    @Autowired
    private ReadsService readsService;

    @PostMapping("/track")
    public ResponseEntity<String> trackStoryRead(@RequestBody Map<String, Object> payload) {
        String userId = (String) payload.get("userId");
        String authorId = (String) payload.get("authorId");
        String storyId = (String) payload.get("storyId");
        boolean isPaid = (boolean) payload.get("isPaid");

        readsService.trackStoryRead(userId, authorId, storyId, isPaid);
        return ResponseEntity.ok("Read tracked successfully!");
    }

    @GetMapping("/{storyId}")
    public ResponseEntity<Long> getTotalReads(@PathVariable String storyId) {
        long totalReads = readsService.getTotalReads(storyId);
        return ResponseEntity.ok(totalReads);
    }

    @GetMapping("/author/{authorId}/monthly")
    public ResponseEntity<Map<String, Long>> getAuthorMonthlyReads(
            @PathVariable String authorId,
            @RequestParam int year,
            @RequestParam int month) {

        Map<String, Long> reads = readsService.getMonthlyReadsByAuthor(authorId, year, month);
        return ResponseEntity.ok(reads);
    }
}
