package com.trustrace.storyApp.service;

import com.trustrace.storyApp.dao.StoryDao;
import com.trustrace.storyApp.model.Story;
import com.trustrace.storyApp.model.Follow;
import com.trustrace.storyApp.model.StoryStatus;
import com.trustrace.storyApp.repository.FollowRepository;
import com.trustrace.storyApp.repository.StoryRepository;
import com.trustrace.storyApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoryService {
    private static final Logger logger = LoggerFactory.getLogger(StoryService.class);

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private StoryDao storyDAO;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public Story createStory(Story story) {
        try {
            Story savedStory = storyRepository.save(story);
            List<String> followers = followRepository.findByAuthorId(savedStory.getAuthorId())
                    .stream()
                    .map(Follow::getFollowerId)
                    .collect(Collectors.toList());

            for (String followerId : followers) {
                userRepository.findById(followerId).ifPresent(user -> {
                    String followerEmail = user.getEmail();
                    storyRepository.findById(savedStory.getId()).ifPresent(storyData -> {
                        String authorName = storyData.getAuthorName();
                        String subject = "New Story by " + authorName + "! Don't Miss It!";
                        String body = "Hi there,\n\n" +
                                "Exciting news! Your favorite author, *" + authorName + "*, has just published a new story: *" + storyData.getTitle() + "*.\n\n" +
                                "Here's a short preview:\n\n" +
                                "\"" + storyData.getSummary() + "\"\n\n" +
                                "Happy reading!\n\n" +
                                "— The StoryApp Team";
                        emailService.sendEmail(followerEmail, subject, body);
                    });
                });
            }
            return savedStory;
        } catch (Exception e) {
            logger.error("Error creating story", e);
            throw new RuntimeException("Error creating story", e);
        }
    }

    public List<Story> getStoriesByUserIdAndStatus(String userId, StoryStatus status) {
        try {
            return storyDAO.getStoriesByUserIdAndStatus(userId, status);
        } catch (Exception e) {
            logger.error("Error fetching stories by user and status", e);
            throw new RuntimeException("Error fetching stories", e);
        }
    }

    public Optional<Story> getStoryById(String storyId) {
        try {
            return storyRepository.findById(storyId);
        } catch (Exception e) {
            logger.error("Error fetching story by ID", e);
            throw new RuntimeException("Error fetching story", e);
        }
    }

    public List<Story> getAllPublishedStories() {
        try {
            return storyRepository.findAllByStatus(StoryStatus.PUBLISHED);
        } catch (Exception e) {
            logger.error("Error fetching published stories", e);
            throw new RuntimeException("Error fetching published stories", e);
        }
    }

    public Optional<Story> updateStory(String storyId, Story updatedStory) {
        try {
            return storyRepository.findById(storyId).map(existingStory -> {
                if (updatedStory.getContent() != null && !updatedStory.getContent().isEmpty()) {
                    existingStory.setContent(updatedStory.getContent());
                }
                if (updatedStory.getStatus() != null) {
                    existingStory.setStatus(updatedStory.getStatus());
                }
                return Optional.of(storyRepository.save(existingStory));
            }).orElse(Optional.empty());
        } catch (Exception e) {
            logger.error("Error updating story", e);
            throw new RuntimeException("Error updating story", e);
        }
    }

    public boolean deleteStory(String storyId) {
        try {
            if (storyRepository.existsById(storyId)) {
                storyRepository.deleteById(storyId);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("Error deleting story", e);
            throw new RuntimeException("Error deleting story", e);
        }
    }
}
