package com.trustrace.storyApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @Id
    private String id;

    private String userId;
    private String storyId;

    private LocalDateTime likedAt = LocalDateTime.now();
}
