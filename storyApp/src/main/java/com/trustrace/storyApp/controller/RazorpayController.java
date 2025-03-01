package com.trustrace.storyApp.controller;


import com.razorpay.RazorpayException;
import com.trustrace.storyApp.service.RazorpayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/payment")
public class RazorpayController {

    @Autowired
    private RazorpayService razorpayService;

    @PostMapping("/create-order/{userId}")
    public ResponseEntity<String> createOrder(@PathVariable String userId) {
        try {
            String order = razorpayService.createOrder(userId);
            return ResponseEntity.ok(order);
        } catch (RazorpayException e) {
            return ResponseEntity.badRequest().body("Error creating order: " + e.getMessage());
        }
    }
}
