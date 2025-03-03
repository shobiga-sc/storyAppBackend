package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.service.AdminStatsService;
import com.trustrace.storyApp.service.ReadsService;
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

    @GetMapping("/author/{authorId}/monthly/{year}/{month}")
    public ResponseEntity<Map<String, Long>> getAuthorMonthlyReads(
            @PathVariable String authorId,
            @PathVariable int year,
            @PathVariable int month,
            Authentication authentication) {


        Map<String, Long> reads = readsService.getMonthlyReadsByAuthor(authorId, year, month);
        return ResponseEntity.ok(reads);
    }


    @GetMapping("/one-writers-earnings/{authorId}")
    public ResponseEntity<Map<String, Object>>getWriterEarnings(
            @PathVariable String authorId,
            @RequestParam int month,
            @RequestParam int year) {
       Map<String, Object> earnings = adminStatsService.calculateOneWriterEarnings(month, year, authorId);
        return ResponseEntity.ok(earnings);
    }




}
