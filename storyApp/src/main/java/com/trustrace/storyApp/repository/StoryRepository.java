package com.trustrace.storyApp.repository;

import com.trustrace.storyApp.model.StoryStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.trustrace.storyApp.model.Story;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface StoryRepository extends MongoRepository<Story, String> {
     Optional<Story> findById(String storyId);
     List<Story> findAllByStatus(StoryStatus status);
}
