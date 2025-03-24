package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.model.Report;
import com.trustrace.storyApp.repository.ReportRepository;
import com.trustrace.storyApp.repository.StoryRepository;
import com.trustrace.storyApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin( origins = "*")
public class ReportController {

    @Autowired
    private ReportRepository reportRepository;


    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);


    @GetMapping("/all")
    public ResponseEntity<List<Report>> getAllReports() {
        logger.info("Fetching all reports");
        return ResponseEntity.ok(reportRepository.findAll());
    }


    @PostMapping("/create")
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        userRepository.findById(report.getReportedByUserId()).ifPresent(user ->
                report.setReportedByName(user.getUsername()));

        userRepository.findById(report.getReportedAuthorId()).ifPresent(user ->
                report.setReportedAuthorName(user.getUsername()));

        report.setReportedAt(LocalDateTime.now());
        Report savedReport = reportRepository.save(report);
        return ResponseEntity.ok(savedReport);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReportStatus(@PathVariable String id, @RequestBody Map<String, Boolean> update) {
        logger.info("Updating report with id {}", id);

        return reportRepository.findById(id).map(report -> {
            if (update.containsKey("isReportAccepted")) {
                report.setReportAccepted(update.get("isReportAccepted"));
            }
            if (update.containsKey("isStoryDeleted")) {
                report.setStoryDeleted(update.get("isStoryDeleted"));
            }
            if (update.containsKey("isUserDeleted")) {
                report.setIsUserDeleted(update.get("isUserDeleted"));
            }

            reportRepository.save(report);
            return ResponseEntity.ok(report);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
