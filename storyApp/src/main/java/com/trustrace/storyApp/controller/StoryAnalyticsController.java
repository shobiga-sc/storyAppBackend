package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.service.StoryAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/analytics")
@CrossOrigin(origins = "*")
public class StoryAnalyticsController {

    @Autowired
    private StoryAnalyticsService analyticsService;

    @GetMapping("/author/{authorId}/monthly-views")
    public ResponseEntity<Map<String, Long>> getMonthlyViews(@PathVariable String authorId,
                                                             @RequestParam int year,
                                                             @RequestParam int month) {
        Map<String, Long> views = analyticsService.getMonthlyViewsByAuthor(authorId, year, month);
        return ResponseEntity.ok(views);
    }
}
