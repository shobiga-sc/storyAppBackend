package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.model.Payout;
import com.trustrace.storyApp.service.PayoutService;
import com.trustrace.storyApp.service.RazorpayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payouts")
@CrossOrigin(origins = "*")
public class PayoutController {

    @Autowired
    private PayoutService payoutService;

    @Autowired
    private RazorpayService razorpayService;


    @PostMapping("/process")
    public ResponseEntity<String> processPayout(@RequestBody Payout payout) {
        try {
            System.out.println("Processing payout for writer Id: " + payout.getWriterId());
            String razorpayPayoutId = razorpayService.createPayout(payout.getWriterId(), payout.getAmount());

            payoutService.savePayout(payout.getWriterId(), payout.getWriterEmail(), payout.getAmount(), payout.getMonth(), payout.getYear(), razorpayPayoutId);

            return ResponseEntity.ok("Payout processed and stored successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing payout: " + e.getMessage());
        }
    }



    @GetMapping("/reports")
    public ResponseEntity<List<Payout>> getPayoutReport(@RequestParam int month, @RequestParam int year) {
        return ResponseEntity.ok(payoutService.getPayoutsByMonthAndYear(month, year));
    }

    @GetMapping("/status")
    public boolean checkPayoutStatus(@RequestParam String writerId, @RequestParam int month, @RequestParam int year) {
        return payoutService.isPayoutDone(writerId, month, year);
    }
}
