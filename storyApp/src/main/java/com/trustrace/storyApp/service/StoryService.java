package com.trustrace.storyApp.service;

import com.trustrace.storyApp.dao.StoryDao;
import com.trustrace.storyApp.model.Story;
import com.trustrace.storyApp.model.StoryStatus;
import com.trustrace.storyApp.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private StoryDao storyDAO;

    public Story createStory(Story story){
        return storyRepository.save(story);
    }



    public List<Story> getStoriesByUserIdAndStatus(String userId, StoryStatus status) {
        return storyDAO.getStoriesByUserIdAndStatus(userId, status);
    }

    public Optional<Story> getStoryById(String storyId){
        return storyRepository.findById(storyId);
    }

    public List<Story> getAllPublishedStories(){
        return storyRepository.findAllByStatus(StoryStatus.PUBLISHED);
    }
}
