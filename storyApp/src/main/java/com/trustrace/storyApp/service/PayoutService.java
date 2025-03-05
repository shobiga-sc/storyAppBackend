package com.trustrace.storyApp.service;
import com.trustrace.storyApp.model.Payout;
import com.trustrace.storyApp.repository.PayoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PayoutService {

    @Autowired
    private PayoutRepository payoutRepository;

    @Autowired
    private EmailService emailService;

    public void savePayout(String writerId, String writerEmail, double amount, int month, int year, String razorpayPayoutId) {
        Payout payout = new Payout(writerId, writerEmail, amount, month, year, razorpayPayoutId);
        payoutRepository.save(payout);


        String emailContent = "🎉 Payment Sent!\n\n" +
                "Dear Writer,\n\n" +
                "Your earnings for " + month + "/" + year + " have been processed.\n\n" +
                "💰 Amount Paid: ₹" + amount + "\n" +
                "📅 Payout Date: " + LocalDateTime.now() + "\n" +
                "🔗 Transaction ID: " + razorpayPayoutId + "\n\n" +
                "Thank you for your contributions!\n\n" +
                "--\nTrustrace StoryApp Team";

        emailService.sendEmail(writerEmail, "💰 Payout Processed - StoryApp", emailContent);
    }

    public List<Payout> getPayoutsByMonthAndYear(int month, int year) {
        return payoutRepository.findByMonthAndYear(month, year);
    }

    public boolean isPayoutDone(String writerId, int month, int year) {
        return payoutRepository.findByWriterIdAndMonthAndYear(writerId, month, year).isPresent();
    }
}
