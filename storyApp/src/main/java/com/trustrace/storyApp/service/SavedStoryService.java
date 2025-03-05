package com.trustrace.storyApp.service;

import com.trustrace.storyApp.model.SavedStory;
import com.trustrace.storyApp.repository.SavedStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SavedStoryService {
    @Autowired
    private SavedStoryRepository savedStoryRepository;

    public SavedStory saveStory(SavedStory savedStory) {
        try {
            Optional<SavedStory> existingSave = savedStoryRepository.findByUserIdAndStoryId(savedStory.getUserId(), savedStory.getStoryId());
            return existingSave.orElseGet(() -> savedStoryRepository.save(savedStory));
        } catch (Exception e) {
            throw new RuntimeException("Error saving story", e);
        }
    }

    public void deleteSavedStory(String userId, String storyId) {
        try {
            savedStoryRepository.deleteByUserIdAndStoryId(userId, storyId);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting saved story", e);
        }
    }

    public boolean isStorySaved(String userId, String storyId) {
        try {
            return savedStoryRepository.findByUserIdAndStoryId(userId, storyId).isPresent();
        } catch (Exception e) {
            throw new RuntimeException("Error checking if story is saved", e);
        }
    }

    public Optional<List<SavedStory>> getStorySavedForUser(String userId) {
        try {
            return savedStoryRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving saved stories for user", e);
        }
    }
}
