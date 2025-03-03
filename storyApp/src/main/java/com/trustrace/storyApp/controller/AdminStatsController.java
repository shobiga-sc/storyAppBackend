package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.service.AdminStatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/stats")
@CrossOrigin(origins = "*")
public class AdminStatsController {

    private final AdminStatsService adminStatsService;

    public AdminStatsController(AdminStatsService adminStatsService) {
        this.adminStatsService = adminStatsService;
    }

    @GetMapping("/total-reads")
    public long getTotalReads(@RequestParam int month, @RequestParam int year) {
        return adminStatsService.getTotalReads(month, year);
    }

    @GetMapping("/reads/paid-reads")
    public long getPaidReads(@RequestParam int month, @RequestParam int year) {
        return adminStatsService.getPaidReads(month, year);
    }

    @GetMapping("/reads/unpaid-reads")
    public long getUnpaidReads(@RequestParam int month, @RequestParam int year) {
        return adminStatsService.getUnpaidReads(month, year);
    }

    @GetMapping("/total-revenue")
    public double getTotalRevenue(@RequestParam int month, @RequestParam int year) {
        return adminStatsService.getTotalRevenue(month, year);
    }

    @GetMapping("/writers-earnings")
    public ResponseEntity<List<Map<String, Object>>> getWriterEarnings(
            @RequestParam int month,
            @RequestParam int year) {
        List<Map<String, Object>> earnings = adminStatsService.calculateWriterEarnings(month, year);
        return ResponseEntity.ok(earnings);
    }
}
