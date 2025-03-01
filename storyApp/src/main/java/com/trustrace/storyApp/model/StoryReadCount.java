package com.trustrace.storyApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "storyreadcount")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryReadCount {
    @Id
    private String id;

    private String storyId;
    private Set<String> userIds = new HashSet<>();

    public int getReadCount() {
        return userIds.size();
    }
}
