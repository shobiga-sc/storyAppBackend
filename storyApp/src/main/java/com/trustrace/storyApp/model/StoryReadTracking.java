package com.trustrace.storyApp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "storyReadTracking")
public class StoryReadTracking {

    @Id
    private String id;
    private String userId;
    private String storyId;
    private StoryDetails story;
    private Date startTime;
    private Date endTime;


    public StoryReadTracking() {}

    public StoryReadTracking(String userId, String storyId, StoryDetails story, Date startTime) {
        this.userId = userId;
        this.storyId = storyId;
        this.story = story;
        this.startTime = startTime;

    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getStoryId() {
        return storyId;
    }

    public StoryDetails getStory() {
        return story;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public void setStory(StoryDetails story) {
        this.story = story;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    // Inner class to hold story details
    public static class StoryDetails {
        private String title;
        private String authorId;
        private String type; // "PAID" or "UNPAID"

        public StoryDetails() {}

        public StoryDetails(String title, String authorId, String type) {
            this.title = title;
            this.authorId = authorId;
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthorId() {
            return authorId;
        }

        public String getType() {
            return type;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setAuthorId(String authorId) {
            this.authorId = authorId;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
