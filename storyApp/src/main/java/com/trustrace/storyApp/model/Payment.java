package com.trustrace.storyApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    private String id;

    private String userId;
    private String orderId;
    private String paymentId;

    private Double amount;
    private String currency;

    private LocalDateTime paymentDate = LocalDateTime.now();
}
