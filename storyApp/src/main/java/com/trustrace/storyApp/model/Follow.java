package com.trustrace.storyApp.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_follows")
public class Follow {

    @Id
    private String id;
    private String followerId;
    private String authorId;

    public Follow() {}

    public Follow(String followerId, String authorId) {
        this.followerId = followerId;
        this.authorId = authorId;
    }

    public String getId() {
        return id;
    }

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}
