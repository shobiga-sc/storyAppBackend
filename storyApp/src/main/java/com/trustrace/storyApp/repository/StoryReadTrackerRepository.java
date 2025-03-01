package com.trustrace.storyApp.repository;

import com.trustrace.storyApp.model.StoryReadTracker;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StoryReadTrackerRepository extends MongoRepository<StoryReadTracker, String> {
    Optional<StoryReadTracker> findByUserIdAndStoryId(String userId, String storyId);
    long countByStoryId(String storyId);
}
