package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.service.SubscriptionService;
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

    @GetMapping("/amount")
    public ResponseEntity<Double> getSubscriptionAmount() {
        return ResponseEntity.ok(subscriptionService.getCurrentAmount());
    }

    @PutMapping("/amount")
    public ResponseEntity<String> updateSubscriptionAmount(@RequestBody Map<String, Double> request) {
        subscriptionService.updateAmount(request.get("amount"));
        return ResponseEntity.ok("Subscription amount updated successfully!");
    }
}
