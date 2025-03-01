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
        Optional<SavedStory> existingSave = savedStoryRepository.findByUserIdAndStoryId(savedStory.getUserId(), savedStory.getStoryId());
        return existingSave.orElseGet(() -> savedStoryRepository.save(savedStory));
    }

    public void deleteSavedStory(String userId, String storyId) {
        savedStoryRepository.deleteByUserIdAndStoryId(userId, storyId);
    }

    public boolean isStorySaved(String userId, String storyId) {
        return savedStoryRepository.findByUserIdAndStoryId(userId, storyId).isPresent();
    }

    public Optional<List<SavedStory>> getStorySavedForUSer(String userId) {
        return savedStoryRepository.findByUserId(userId);
    }


}
