package com.trustrace.storyApp.repository;

import com.trustrace.storyApp.model.SavedStory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedStoryRepository extends MongoRepository<SavedStory, String> {

    Optional<SavedStory> findByUserIdAndStoryId(String userId, String storyId);
    void deleteByUserIdAndStoryId(String userId, String storyId);
    Optional<List<SavedStory>> findByUserId(String userId);
}
