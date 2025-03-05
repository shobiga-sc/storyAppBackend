package com.trustrace.storyApp.controller;


import com.razorpay.RazorpayException;
import com.trustrace.storyApp.service.PayoutService;
import com.trustrace.storyApp.service.RazorpayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/payment")
public class RazorpayController {

    @Autowired
    private RazorpayService razorpayService;

    @Autowired
    private PayoutService payoutService;

    private static final Logger logger = LoggerFactory.getLogger(RazorpayController.class);

    @PostMapping("/create-order/{userId}")
    public ResponseEntity<String> createOrder(@PathVariable String userId) {
        logger.info("Creating order in razorpay for user with id {}", userId);
        try {
            String order = razorpayService.createOrder(userId);
            return ResponseEntity.ok(order);
        } catch (RazorpayException e) {
            return ResponseEntity.badRequest().body("Error creating order: " + e.getMessage());
        }
    }


    @PostMapping("/create-payout")
    public ResponseEntity<String> createPayout(@RequestParam String writerId,
                                               @RequestParam String writerEmail,
                                               @RequestParam double amount,
                                               @RequestParam int month,
                                               @RequestParam int year) {
        try {

            String razorpayPayoutId = razorpayService.createPayout(writerId, amount);


            payoutService.savePayout(writerId, writerEmail, amount, month, year, razorpayPayoutId);

            return ResponseEntity.ok("Payout created and stored successfully!");
        } catch (Exception e) {
            logger.error("Error creating payout: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error creating payout: " + e.getMessage());
        }
    }
}
