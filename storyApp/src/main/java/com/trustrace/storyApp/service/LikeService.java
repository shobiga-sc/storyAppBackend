package com.trustrace.storyApp.service;

import com.trustrace.storyApp.model.Like;
import com.trustrace.storyApp.model.Story;
import com.trustrace.storyApp.repository.LikeRepository;
import com.trustrace.storyApp.repository.StoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private StoryRepository storyRepository;

    private static final Logger logger = LoggerFactory.getLogger(LikeService.class);

    public Like likeStory(Like like) {
        try {
            Like savedLike = likeRepository.save(like);
            updateLikeCount(like.getStoryId(), 1);
            return savedLike;
        } catch (Exception e) {
            logger.error("Error while liking story: {}", e.getMessage());
            throw new RuntimeException("Error liking story", e);
        }
    }

    public void unlikeStory(String userId, String storyId) {
        try {
            likeRepository.deleteByUserIdAndStoryId(userId, storyId);
            updateLikeCount(storyId, -1);
        } catch (Exception e) {
            logger.error("Error while unliking story: {}", e.getMessage());
            throw new RuntimeException("Error unliking story", e);
        }
    }

    public boolean isStoryLiked(String userId, String storyId) {
        try {
            return likeRepository.existsByUserIdAndStoryId(userId, storyId);
        } catch (Exception e) {
            logger.error("Error checking if story is liked: {}", e.getMessage());
            throw new RuntimeException("Error checking story like status", e);
        }
    }

    private void updateLikeCount(String storyId, int change) {
        try {
            Optional<Story> optionalStory = storyRepository.findById(storyId);
            if (optionalStory.isPresent()) {
                Story story = optionalStory.get();
                int currentLikes = Objects.nonNull(story.getLikeCount()) ? story.getLikeCount() : 0;
                story.setLikeCount(Math.max(0, currentLikes + change));
                storyRepository.save(story);
            }
        } catch (Exception e) {
            logger.error("Error updating like count: {}", e.getMessage());
            throw new RuntimeException("Error updating like count", e);
        }
    }
}