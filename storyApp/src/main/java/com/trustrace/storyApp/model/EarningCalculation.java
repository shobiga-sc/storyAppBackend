package com.trustrace.storyApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "earning_calculation")
@NoArgsConstructor
@AllArgsConstructor
public class EarningCalculation {
    @Id
    private String id;

    private String authorId;
    private Double totalEarnings = 0.0;
    private Double paidStoryEarnings = 0.0;
    private Double unpaidStoryEarnings = 0.0;

    private Double popularityScore;
    private Long paidReads;
    private Long unpaidReads;

    private LocalDateTime updatedAt = LocalDateTime.now();
}
