package com.trustrace.storyApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

import com.trustrace.storyApp.model.StoryStatus;

@Document(collection = "stories")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Story {
    @Id
    private String id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String title;

    @NotBlank
    private String posterUrl;

    @NotBlank
    @Size(min = 20, max = 500)
    private String summary;

    @NotBlank
    @Size(min = 100, max = 10000)
    private String content;

    @NotBlank
    private String authorId;

    private  String authorName;

    @NotBlank
    private String genre;

    @NotBlank
    private List<String> tags;

    @NotBlank
    private boolean isPaid;

    private StoryStatus status = StoryStatus.DRAFT;

    private LocalDateTime publishedDate;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private int likeCount = 0;
    private int viewCount = 0;
    private int subscriptions = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
