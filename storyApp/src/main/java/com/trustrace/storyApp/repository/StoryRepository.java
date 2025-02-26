package com.trustrace.storyApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.trustrace.storyApp.model.Story;
import org.springframework.stereotype.Repository;


@Repository
public interface StoryRepository extends MongoRepository<Story, String> {
}
