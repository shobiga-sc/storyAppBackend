package com.trustrace.storyApp.service;

import com.trustrace.storyApp.model.Story;
import com.trustrace.storyApp.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;

    public Story createStory(Story story){
        return storyRepository.save(story);
    }
}
