package com.trustrace.storyApp.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    private String id;

    private String reportedByUserId;
    private String reportedAuthorId;

    @NotBlank
    private String reason; // Reason for reporting

    private LocalDateTime reportedAt = LocalDateTime.now();
}
