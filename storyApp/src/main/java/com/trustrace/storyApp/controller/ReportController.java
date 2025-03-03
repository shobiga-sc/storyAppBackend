package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.model.Report;
import com.trustrace.storyApp.repository.ReportRepository;
import com.trustrace.storyApp.repository.StoryRepository;
import com.trustrace.storyApp.repository.UserRepository;
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

    @GetMapping("/all")
    public ResponseEntity<List<Report>> getAllReports() {
        return ResponseEntity.ok(reportRepository.findAll());
    }


    @PostMapping("/create")
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        report.setReportedAt(LocalDateTime.now());
        Report savedReport = reportRepository.save(report);
        return ResponseEntity.ok(savedReport);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReportStatus(@PathVariable String id, @RequestBody Map<String, Boolean> update) {
        return reportRepository.findById(id).map(report -> {
            report.setReportAccepted(update.get("isReportAccepted"));
            reportRepository.save(report);
            return ResponseEntity.ok(report);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
