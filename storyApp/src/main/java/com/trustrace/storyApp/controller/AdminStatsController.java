package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.service.AdminStatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/stats")
@CrossOrigin(origins = "*")
public class AdminStatsController {

    private final AdminStatsService adminStatsService;
    private static final Logger logger = LoggerFactory.getLogger(AdminStatsController.class);

    public AdminStatsController(AdminStatsService adminStatsService) {
        this.adminStatsService = adminStatsService;
    }

    @GetMapping("/total-reads")
    public ResponseEntity<Object> getTotalReads(@RequestParam int month, @RequestParam int year) {
        try {
            logger.info("Getting total reads by month {} and year {}", month, year);
            return ResponseEntity.ok(adminStatsService.getTotalReads(month, year));
        } catch (Exception e) {
            logger.error("Error getting total reads: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error fetching total reads");
        }
    }

    @GetMapping("/reads/paid-reads")
    public ResponseEntity<Object> getPaidReads(@RequestParam int month, @RequestParam int year) {
        try {
            logger.info("Getting paid reads by month {} and year {}", month, year);
            return ResponseEntity.ok(adminStatsService.getPaidReads(month, year));
        } catch (Exception e) {
            logger.error("Error getting paid reads: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error fetching paid reads");
        }
    }

    @GetMapping("/reads/unpaid-reads")
    public ResponseEntity<Object> getUnpaidReads(@RequestParam int month, @RequestParam int year) {
        try {
            logger.info("Getting unpaid reads by month {} and year {}", month, year);
            return ResponseEntity.ok(adminStatsService.getUnpaidReads(month, year));
        } catch (Exception e) {
            logger.error("Error getting unpaid reads: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error fetching unpaid reads");
        }
    }

    @GetMapping("/total-revenue")
    public ResponseEntity<Object> getTotalRevenue(@RequestParam int month, @RequestParam int year) {
        try {
            logger.info("Getting total revenue on month {} and year {}", month, year);
            return ResponseEntity.ok(adminStatsService.getTotalRevenue(month, year));
        } catch (Exception e) {
            logger.error("Error getting total revenue: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error fetching total revenue");
        }
    }

    @GetMapping("/writers-earnings")
    public ResponseEntity<Object> getWriterEarnings(@RequestParam int month, @RequestParam int year) {
        try {
            logger.info("Getting each writer earnings on month {} and year {}", month, year);
            return ResponseEntity.ok(adminStatsService.calculateWriterEarnings(month, year));
        } catch (Exception e) {
            logger.error("Error getting writer earnings: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error fetching writer earnings");
        }
    }
}
