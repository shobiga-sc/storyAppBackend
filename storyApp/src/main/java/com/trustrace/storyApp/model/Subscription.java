package com.trustrace.storyApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "subscriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    @Id
    private String id;

    private String userId;
    private String razorpayPaymentId;

    private LocalDate startDate;
    private LocalDate endDate;

    private boolean isActive;
}
