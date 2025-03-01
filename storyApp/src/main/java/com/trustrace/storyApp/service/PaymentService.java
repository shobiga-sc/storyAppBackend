package com.trustrace.storyApp.service;


import com.trustrace.storyApp.model.Payment;
import com.trustrace.storyApp.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trustrace.storyApp.repository.UserRepository;

import java.time.LocalDateTime;


@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    public void savePayment(Payment payment) {

        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDateTime.now());
        }
        paymentRepository.save(payment);


        String userEmail = userRepository.findEmailById(payment.getUserId());


        String receipt = "🎉 Congratulations on Your Prime Subscription! 🎉\n\n" +
                "Thank you for subscribing to our premium service. You now have access to exclusive features!\n\n" +
                "🔹 **Payment Details** 🔹\n" +
                "🆔 Payment ID: " + payment.getPaymentId() + "\n" +
                "📦 Order ID: " + payment.getOrderId() + "\n" +
                "👤 User ID: " + payment.getUserId() + "\n" +
                "💰 Amount Paid: ₹" + payment.getAmount() + "\n" +
                "💱 Currency: " + payment.getCurrency() + "\n" +
                "📅 Payment Date: " + payment.getPaymentDate() + "\n\n" +
                "✨ Enjoy uninterrupted access to premium content on **Trustrace StoryApp**!\n" +
                "📩 For any queries, contact us at support@storyapp.com\n\n" +
                "Happy Reading! 📖✨\n\n" +
                "--\nTrustrace StoryApp Team";


        emailService.sendEmail(userEmail, "🎉 Payment Receipt - Prime Subscription", receipt);
    }
}
