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
    private String reportedStoryId;

    @NotBlank
    private String reason;

    private boolean isReportAccepted;
    private boolean isStoryDeleted;
    private boolean isUserDeleted;

    private LocalDateTime reportedAt = LocalDateTime.now();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportedByUserId() {
        return reportedByUserId;
    }

    public void setReportedByUserId(String reportedByUserId) {
        this.reportedByUserId = reportedByUserId;
    }

    public String getReportedAuthorId() {
        return reportedAuthorId;
    }

    public void setReportedAuthorId(String reportedAuthorId) {
        this.reportedAuthorId = reportedAuthorId;
    }

    public @NotBlank String getReason() {
        return reason;
    }

    public void setReason(@NotBlank String reason) {
        this.reason = reason;
    }

    public boolean isReportAccepted() {
        return isReportAccepted;
    }

    public void setReportAccepted(boolean reportAccepted) {
        isReportAccepted = reportAccepted;
    }

    public LocalDateTime getReportedAt() {
        return reportedAt;
    }

    public void setReportedAt(LocalDateTime reportedAt) {
        this.reportedAt = reportedAt;
    }

    public String getReportedStoryId() {
        return reportedStoryId;
    }

    public void setReportedStoryId(String reportedStoryId) {
        this.reportedStoryId = reportedStoryId;
    }

    public boolean isStoryDeleted() {
        return isStoryDeleted;
    }

    public void setStoryDeleted(boolean storyDeleted) {
        isStoryDeleted = storyDeleted;
    }

    public boolean isIsUserDeleted() {
        return isUserDeleted;
    }

    public void setIsUserDeleted(boolean isUserDeleted) {
        this.isUserDeleted = isUserDeleted;
    }
}
