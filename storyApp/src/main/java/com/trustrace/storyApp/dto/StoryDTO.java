package com.trustrace.storyApp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trustrace.storyApp.model.Story;
import lombok.Getter;

import java.util.Arrays;
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoryDTO {
    private String id;
    private String title;
    private String genre;
    private String authorId;
    private Integer likeCount;
    private Integer viewCount;
    private String content;
    private Boolean paid;

    public StoryDTO(Story story, boolean shouldBlur,  boolean hasFullAccess) {
        this.id = story.getId();
        this.title = story.getTitle();
        this.genre=story.getGenre();
        this.authorId = story.getAuthorId();
        this.likeCount = story.getLikeCount();
        this.viewCount = story.getViewCount();
        this.paid = story.isPaid();
        if (hasFullAccess) {
            this.content = story.getContent();
        } else if (shouldBlur) {
            this.content = blurContent(story.getContent(), 25);
        } else {
            this.content = story.getContent();
        }
    }

    private String blurContent(String content, int wordLimit) {
        if (content == null || content.isEmpty()) return "";
        String[] words = content.split(" ");
        return String.join(" ", Arrays.copyOfRange(words, 0, Math.min(wordLimit, words.length))) + "...";
    }


    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthorId() { return authorId; }
    public Integer getLikeCount() { return likeCount; }
    public Integer getViewCount() { return viewCount; }
    public String getContent() { return content; }
    public String getGenre() { return genre; }
    public Boolean getPaid() { return paid; }

}
