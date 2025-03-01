package com.trustrace.storyApp.service;

import com.trustrace.storyApp.model.Like;
import com.trustrace.storyApp.model.Story;
import com.trustrace.storyApp.repository.LikeRepository;
import com.trustrace.storyApp.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private StoryRepository storyRepository; // FIXED: Properly autowired

    public Like likeStory(Like like) {
        Like savedLike = likeRepository.save(like);
        updateLikeCount(like.getStoryId(), 1);
        return savedLike;
    }

    public void unlikeStory(String userId, String storyId) {
        likeRepository.deleteByUserIdAndStoryId(userId, storyId);
        updateLikeCount(storyId, -1);
    }

    public boolean isStoryLiked(String userId, String storyId) {
        return likeRepository.existsByUserIdAndStoryId(userId, storyId);
    }



    private void updateLikeCount(String storyId, int change) {
        Optional<Story> optionalStory = storyRepository.findById(storyId);
        if (optionalStory.isPresent()) {
            Story story = optionalStory.get();
            int currentLikes = Objects.nonNull(story.getLikeCount()) ? story.getLikeCount() : 0; // FIXED
            story.setLikeCount(Math.max(0, currentLikes + change));
            storyRepository.save(story);
        }
    }

}