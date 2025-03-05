package com.trustrace.storyApp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "payouts")
public class Payout {
    @Id
    private String id;
    private String writerId;
    private String writerEmail;
    private double amount;
    private int month;
    private int year;
    private LocalDateTime payoutDate;
    private String razorpayPayoutId;

    // Constructor
    public Payout(String writerId, String writerEmail, double amount, int month, int year, String razorpayPayoutId) {
        this.writerId = writerId;
        this.writerEmail = writerEmail;
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.payoutDate = LocalDateTime.now();
        this.razorpayPayoutId = razorpayPayoutId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    public String getWriterEmail() {
        return writerEmail;
    }

    public void setWriterEmail(String writerEmail) {
        this.writerEmail = writerEmail;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public LocalDateTime getPayoutDate() {
        return payoutDate;
    }

    public void setPayoutDate(LocalDateTime payoutDate) {
        this.payoutDate = payoutDate;
    }

    public String getRazorpayPayoutId() {
        return razorpayPayoutId;
    }

    public void setRazorpayPayoutId(String razorpayPayoutId) {
        this.razorpayPayoutId = razorpayPayoutId;
    }

    // Optional: Override toString() for debugging
    @Override
    public String toString() {
        return "Payout{" +
                "id='" + id + '\'' +
                ", writerId='" + writerId + '\'' +
                ", writerEmail='" + writerEmail + '\'' +
                ", amount=" + amount +
                ", month=" + month +
                ", year=" + year +
                ", payoutDate=" + payoutDate +
                ", razorpayPayoutId='" + razorpayPayoutId + '\'' +
                '}';
    }
}
