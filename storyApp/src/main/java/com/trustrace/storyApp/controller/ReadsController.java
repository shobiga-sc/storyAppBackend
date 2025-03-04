package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.service.AdminStatsService;
import com.trustrace.storyApp.service.ReadsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reads")
@CrossOrigin(origins = "*")
public class ReadsController {

    @Autowired
    private ReadsService readsService;

    @Autowired
    private AdminStatsService adminStatsService;
    private static final Logger logger = LoggerFactory.getLogger(ReadsController.class);

    @PostMapping("/track")
    public ResponseEntity<String> trackStoryRead(@RequestBody Map<String, Object> payload) {
        try {
            String userId = (String) payload.get("userId");
            String authorId = (String) payload.get("authorId");
            String storyId = (String) payload.get("storyId");
            boolean isPaid = (boolean) payload.get("isPaid");

            readsService.trackStoryRead(userId, authorId, storyId, isPaid);
            return ResponseEntity.ok("Read tracked successfully!");
        } catch (Exception e) {
            logger.error("Error tracking story read", e);
            return ResponseEntity.internalServerError().body("Failed to track read");
        }
    }

    @GetMapping("/{storyId}")
    public ResponseEntity<Long> getTotalReads(@PathVariable String storyId) {
        try {
            long totalReads = readsService.getTotalReads(storyId);
            logger.info("Tracking read for story with id {}", storyId);
            return ResponseEntity.ok(totalReads);
        } catch (Exception e) {
            logger.error("Error retrieving total reads for story {}", storyId, e);
            return ResponseEntity.internalServerError().body(0L);
        }
    }

    @GetMapping("/author/{authorId}/monthly/{year}/{month}")
    public ResponseEntity<Map<String, Long>> getAuthorMonthlyReads(
            @PathVariable String authorId,
            @PathVariable int year,
            @PathVariable int month,
            Authentication authentication) {
        try {
            logger.info("Tracking read for author with id {} on month {} and year {}", authorId, month, year);
            Map<String, Long> reads = readsService.getMonthlyReadsByAuthor(authorId, year, month);
            return ResponseEntity.ok(reads);
        } catch (Exception e) {
            logger.error("Error retrieving monthly reads for author {}", authorId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/one-writers-earnings/{authorId}")
    public ResponseEntity<Map<String, Object>> getWriterEarnings(
            @PathVariable String authorId,
            @RequestParam int month,
            @RequestParam int year) {
        try {
            Map<String, Object> earnings = adminStatsService.calculateOneWriterEarnings(month, year, authorId);
            logger.info("Tracking earning for author with id {} on month {} and year {}", authorId, month, year);
            return ResponseEntity.ok(earnings);
        } catch (Exception e) {
            logger.error("Error retrieving earnings for author {}", authorId, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}