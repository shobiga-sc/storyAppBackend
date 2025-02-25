package com.trustrace.storyApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "saved_stories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavedStory {
    @Id
    private String id;

    private String userId;
    private String storyId;

    private LocalDateTime savedAt = LocalDateTime.now();
}
