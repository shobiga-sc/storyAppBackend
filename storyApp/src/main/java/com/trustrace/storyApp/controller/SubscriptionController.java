package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/subscription")
@CrossOrigin(origins = "*")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

    @GetMapping("/amount")
    public ResponseEntity<Double> getSubscriptionAmount() {
        logger.info("Fetching subscription amount");

        return ResponseEntity.ok(subscriptionService.getCurrentAmount());
    }

    @PutMapping("/amount")
    public ResponseEntity<String> updateSubscriptionAmount(@RequestBody Map<String, Double> request) {
        subscriptionService.updateAmount(request.get("amount"));
        logger.info("Updating subscription amount");
        return ResponseEntity.ok("Subscription amount updated successfully!");
    }
}
