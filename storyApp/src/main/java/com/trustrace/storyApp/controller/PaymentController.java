package com.trustrace.storyApp.controller;


import com.trustrace.storyApp.model.Payment;
import com.trustrace.storyApp.model.User;
import com.trustrace.storyApp.service.PaymentService;
import com.trustrace.storyApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @PostMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyPayment(@RequestBody Map<String, String> paymentData) {
        String userId = paymentData.get("userId");
        String orderId = paymentData.get("orderId");
        String paymentId = paymentData.get("paymentId");
        double amount = 499.00;

        // Store payment details
        Payment payment = new Payment(null, userId, orderId, paymentId, amount, "INR", null);
        paymentService.savePayment(payment);

        // Update user to Prime Subscriber
        Optional<User> optionalUser = userService.getUserById(userId);
        Map<String, String> response = new HashMap<>();

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPrimeSubscriber(true);
            user.setPrimeSubscriptionExpiry(LocalDate.now().plusMonths(1)); // 1-month subscription
            userService.updateUser(user);
            response.put("message", "Payment verified and user updated!");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "User not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


}
